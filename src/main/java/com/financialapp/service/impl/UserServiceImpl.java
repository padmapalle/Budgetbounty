package com.financialapp.service.impl;

import com.financialapp.dto.UserDTO;
import com.financialapp.model.User;
import com.financialapp.repository.UserRepository;
import com.financialapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());

        User saved = userRepository.save(user);
        return new UserDTO(saved.getUserId(), saved.getEmail());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getUserId(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
