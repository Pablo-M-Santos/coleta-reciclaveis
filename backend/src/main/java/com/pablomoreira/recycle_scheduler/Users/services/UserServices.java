package com.pablomoreira.recycle_scheduler.Users.services;

import com.pablomoreira.recycle_scheduler.Exceptions.ModelNotFoundException;
import com.pablomoreira.recycle_scheduler.Users.DTOs.CreateUserRequestDTO;
import com.pablomoreira.recycle_scheduler.Users.DTOs.UpdateUserRequestDTO;
import com.pablomoreira.recycle_scheduler.Users.Validation.UserValidation;
import com.pablomoreira.recycle_scheduler.Users.models.UserModel;
import com.pablomoreira.recycle_scheduler.Users.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserValidation userValidation;


    public ResponseEntity<Void> create(@Valid CreateUserRequestDTO data) {
        userValidation.create(data);

        String encryptedPassword = passwordEncoder.encode(data.password());
        UserModel newUser = new UserModel(data.name(), data.email(), encryptedPassword, data.role());
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public Page<UserModel> findAll(String search, int page) {
        int size = 8;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (search == null || search.trim().isEmpty()) {
            Page<UserModel> users = userRepository.findAll(pageable);
            if (users.isEmpty()) throw new ModelNotFoundException();
            return users;

        } else {
            return userRepository.findAllBySearch(search, pageable);
        }
    }

    public List<UserModel> findAllWithoutPagination(String search) {
        if (search == null || search.trim().isEmpty()) {
            return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        } else {

            return userRepository.findAllBySearch(search, Sort.by(Sort.Direction.DESC, "id"));
        }
    }

    public Optional<UserModel> findById(int id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<Object> update(int id, @Valid UpdateUserRequestDTO updateUserRequestDTO) {
        Optional<UserModel> response = userRepository.findById(id);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var userModel = response.get();

        userValidation.update(updateUserRequestDTO, id);

        userModel.setName(updateUserRequestDTO.name());
        userModel.setEmail(updateUserRequestDTO.email());
        userModel.setRole(updateUserRequestDTO.role());

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    public ResponseEntity<Object> delete(int id) {
        Optional<UserModel> response = userRepository.findById(id);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepository.delete(response.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }


    public String getUserNameByEmail(String email) {
        UserModel user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getName();
        }
        return null;
    }
}
