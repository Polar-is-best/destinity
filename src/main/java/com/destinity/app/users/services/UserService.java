package com.destinity.app.users.services;

import com.destinity.app.users.dto.UserDTO;
import com.destinity.app.users.model.User;
import com.destinity.app.users.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

@ApplicationScoped
public class UserService {

    private UserRepository userRepository;
    
    public UserService(){
    }
    
    @Inject
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(ObjectId id) {
        return userRepository.findById(id)
                .map(UserDTO::new);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new);
    }

    public void updateUser(User user) {
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.update(user);
    }

    public void deleteUser(ObjectId userId) {
        userRepository.deleteById(userId);
    }
}
