package co.simplon.authmanager.controllers;

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

import co.simplon.authmanager.model.Resource;
import co.simplon.authmanager.repository.ResourceRepository;

@RestController
@RequestMapping("/api")
public class ResourceController {
	
	@Autowired
	ResourceRepository repository;
	
	@CrossOrigin		// TODO à supprimer
	@GetMapping("/resource")
	List<Resource> getAllResource(){
		return repository.findAll();
	}
	
	@CrossOrigin		// TODO à supprimer
	@GetMapping("/resource/{id}")
	ResponseEntity<Resource> getResourceById(@PathVariable(value="id") long id) {
	    Resource resource = repository.findOne(id);
	    if(resource == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(resource);
	}
	
	@CrossOrigin		// TODO à supprimer
	@PostMapping("/resource")
	Resource addResource(@Valid @RequestBody Resource resource){
		return repository.save(resource);
	}
	
	@CrossOrigin		// TODO à supprimer
	@PutMapping("/resource/{id}")
	ResponseEntity<Resource> updateResource(@PathVariable(value="id") long id, @Valid @RequestBody Resource resource){
		Resource resourceToUpdate = repository.findOne(id);
		if(resourceToUpdate == null)
			return ResponseEntity.notFound().build();
		
		if(resource.getName() != null)
			resourceToUpdate.setName(resource.getName());
		
		if(resource.getDescription() != null)
			resourceToUpdate.setDescription(resource.getDescription());
		
		Resource updatedResource = repository.save(resourceToUpdate);
		return ResponseEntity.ok(updatedResource);
	}
	
	@CrossOrigin		// TODO à supprimer
	@DeleteMapping("/resource/{id}")
	ResponseEntity<Resource> deleteResource(@PathVariable(value="id") long id){
		Resource resource = repository.findOne(id);
		if(resource == null)
			return ResponseEntity.notFound().build();
		
		repository.delete(resource);
		return ResponseEntity.ok().build();
	}
}
