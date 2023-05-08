package com.william.authentication.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.william.authentication.models.LoginUser;
import com.william.authentication.models.User;
import com.william.authentication.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public User createUser(User u) {
    	return userRepo.save(u);
    }
    
    public User updateUser(User u) {
    	return userRepo.save(u);
    }
    
    public void deleteUser(Long id) {
    	userRepo.deleteById(id);
    }
    
    public User findById(Long id) {
    	Optional<User> optionalUser = userRepo.findById(id);
    	if (optionalUser.isPresent()) {
    		return optionalUser.get();
    	} else {
    		return null;
    	}
    }
    
    // TO-DO: Write register and login methods!
    public User register(User newUser, BindingResult result) {
    	// TO-DO - Reject values or register if no errors:
        
        // Reject if email is taken (present in database)
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	if (potentialUser.isPresent()) {
    		result.rejectValue("email", "Exists", "The specified email is already in use.");
    	}    	
        // Reject if password doesn't match confirmation
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    	    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}
        // Return null if result has errors
    	if(result.hasErrors()) {
    	    return null;
    	}
        // Hash and set password, save user to database
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	return this.createUser(newUser);
    }
    
    public User login(LoginUser newLoginObject, BindingResult result) {
        // TO-DO - Reject values:
        
    	// Find user in the DB by email
        // Reject if NOT present
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
    	if (!potentialUser.isPresent()) {
    		result.rejectValue("email", "Exists", "The email provided does not exist in our database.");
    		return null;
    	}        
        // Reject if BCrypt password match fails
    	User user = potentialUser.get();
    	if(!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	    return null;
    	}    
        // Return null if result has errors
    	if(result.hasErrors()) {
    	    return null;
    	}    	
        // Otherwise, return the user object
        return user;
    }
}