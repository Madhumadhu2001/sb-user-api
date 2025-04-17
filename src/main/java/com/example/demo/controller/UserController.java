package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create
	/*
	 * @PostMapping public User createUser(@RequestBody User user) { return
	 * userRepository.save(user); }
	 */
    
    
    //create
    //unique mails
    @PostMapping
    public User createUser(@RequestBody User user) {
        // Check if the email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // If not, save the new user
        return userRepository.save(user);
    }

    // Read
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update
	/*
	 * @PutMapping("/{id}") public User updateUser(@PathVariable Long
	 * id, @RequestBody User userDetails) { return
	 * userRepository.findById(id).map(user -> {
	 * user.setName(userDetails.getName()); user.setEmail(userDetails.getEmail());
	 * return userRepository.save(user); }).orElseThrow(() -> new
	 * RuntimeException("User not found")); }
	 */

    // Delete
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
    
    //bulk data updating
    @PostMapping("/bulk")
    public List<User> createUsers(@RequestBody List<User> users) {
        return userRepository.saveAll(users);
    }
    
    //id updating
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user); // ID is managed automatically
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    
    //to check git updation
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
