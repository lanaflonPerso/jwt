package co.simplon.authmanager.controllers;

import java.util.List;

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

import co.simplon.authmanager.model.User;
import co.simplon.authmanager.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@CrossOrigin		// TODO à supprimer
	@GetMapping("/user")
	List<User> getAllUser(){
		return repository.findAll();
	}
	
	@CrossOrigin		// TODO à supprimer
	@GetMapping("/user/{id}")
	ResponseEntity<User> getUserById(@PathVariable(value="id") long id) {
	    User user = repository.findOne(id);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(user);
	}
	
	@CrossOrigin		// TODO à supprimer
	@PostMapping("/user")
	User addUser(@RequestBody User user){
		user.setPassword("password");
		user.setStatus(User.UserStatus.ACTIVE);
		return repository.save(user);
	}
	
	// TODO : fonction à revoir
	@CrossOrigin		// TODO à supprimer
	@PutMapping("/user/{id}")
	ResponseEntity<User> updateUser(@PathVariable(value="id") long id, @RequestBody User user){
		User userToUpdate = repository.findOne(id);
		if(userToUpdate == null)
			return ResponseEntity.notFound().build();
		
		if(user.getUsername() != null)
			userToUpdate.setUsername(user.getUsername());
		
		if(user.getEmail() != null)
			userToUpdate.setEmail(user.getEmail());
		
		if(user.getStatus() != null)
			userToUpdate.setStatus(user.getStatus());
		
		if(user.getRole() != null)
			userToUpdate.setRole(user.getRole());
		
		User updatedUser = repository.save(userToUpdate);
		return ResponseEntity.ok(updatedUser);
	}
	
	@CrossOrigin		// TODO à supprimer
	@DeleteMapping("/user/{id}")
	ResponseEntity<User> deleteUser(@PathVariable(value="id") long id){
		User user = repository.findOne(id);
		if(user == null)
			return ResponseEntity.notFound().build();
		
		repository.delete(user);
		return ResponseEntity.ok().build();
	}
}
