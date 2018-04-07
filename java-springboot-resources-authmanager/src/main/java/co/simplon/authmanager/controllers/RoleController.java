package co.simplon.authmanager.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.authmanager.model.Access;
import co.simplon.authmanager.model.Resource;
import co.simplon.authmanager.model.Role;
import co.simplon.authmanager.repository.ResourceRepository;
import co.simplon.authmanager.repository.RoleRepository;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	RoleRepository repository;
	
	@Autowired
	ResourceRepository resourceRepository;
	
	@CrossOrigin		// TODO à supprimer
	@GetMapping("/role")
	List<Role> getAllRole(){
		return repository.findAll();
	}
	
	@CrossOrigin		// TODO à supprimer
	@GetMapping("/role/{id}")
	ResponseEntity<Role> getRoleById(@PathVariable(value="id") long id) {
	    Role role = repository.findOne(id);
	    if(role == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(role);
	}
	
	@CrossOrigin		// TODO à supprimer
	@PostMapping("/role")
	Role addRole(@Valid @RequestBody Role role){
		return repository.save(role);
	}
	
	@CrossOrigin		// TODO à supprimer
	@PutMapping("/role/{id}")
	ResponseEntity<Role> updateRole(@PathVariable(value="id") long id, @Valid @RequestBody Role role){
		Role roleToUpdate = repository.findOne(id);
		if(roleToUpdate == null)
			return ResponseEntity.notFound().build();
		
		if(role.getName() != null)
			roleToUpdate.setName(role.getName());
		
		if(role.getAccess() != null){
			// Extract all the resource ids
			List<Long> listOfResourceIds = new ArrayList<Long>();
			List<Access> listOfAccess = role.getAccess();
			for(int i=0 ; i<listOfAccess.size() ; i++){
				long associatedResourceId = listOfAccess.get(i).getResource().getId();
				if(!listOfResourceIds.contains(associatedResourceId))
					listOfResourceIds.add(associatedResourceId);
			}
			
			// Get the associated resources in the database
			List<Resource> listOfResources = resourceRepository.findByIdIn(listOfResourceIds);
			System.out.println("listOfResources : " + listOfResources);
		}
		
		// Update the mandatory attributes
		/*roleToUpdate.setFirstname(role.getFirstname());
		roleToUpdate.setName(role.getName());
		
		// Update all other not null attributes
		if(role.getAddress() != null)
			roleToUpdate.setAddress(role.getAddress());
		
		if(role.getPhone() != null)
			roleToUpdate.setPhone(role.getPhone());
		
		if(role.getEmail() != null)
			roleToUpdate.setEmail(role.getEmail());*/
		
		// TODO 
		
		Role updatedRole = repository.save(roleToUpdate);
		return ResponseEntity.ok(updatedRole);
	}
	
	@CrossOrigin		// TODO à supprimer
	@DeleteMapping("/role/{id}")
	ResponseEntity<Role> deleteRole(@PathVariable(value="id") long id){
		Role role = repository.findOne(id);
		if(role == null)
			return ResponseEntity.notFound().build();
		
		repository.delete(role);
		return ResponseEntity.ok().build();
	}
}
