package com.financialapp.service.impl;

import com.financialapp.dto.UserDTO;
<<<<<<< HEAD
import com.financialapp.entity.User;
=======
import com.financialapp.model.User;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
import com.financialapp.repository.UserRepository;
import com.financialapp.service.UserService;

import jakarta.transaction.Transactional;
<<<<<<< HEAD
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

=======

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepository.findById(userId)
<<<<<<< HEAD
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + userId));
=======
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Integer userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
<<<<<<< HEAD
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setAppAdmin(userDTO.getAppAdmin());
        existingUser.setPoints(userDTO.getPoints());
=======
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setAppAdmin(userDTO.getAppAdmin());
        existingUser.setPoints(userDTO.getPoints()); //
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
        return modelMapper.map(userRepository.save(existingUser), UserDTO.class);
    }

    @Override
    public void deleteUser(Integer userId) {
<<<<<<< HEAD
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
=======
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserDTO updatePoints(Integer id, Integer points) {
<<<<<<< HEAD
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));
=======
        User user = userRepository.findById(id).orElseThrow();
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
        user.setPoints(points);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }
}
