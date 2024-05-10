package com.example.parking.user.service;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserShortDto;
import com.example.parking.user.dto.UserUpdateDto;
import com.example.parking.user.mapper.UserMapper;
import com.example.parking.user.model.Role;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.RoleRepository;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.parking.enums.RoleName.USER;
import static com.example.parking.enums.State.ACTIVE;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserFullDto update(Long userId, UserUpdateDto user) {
        User found = getUserByIdFromRepository(userId);
        if (userRepository.existsByPhoneOrEmail(user.getPhone(), user.getEmail())) {
            throw new AlreadyExistsException("A user with such a phone or email has already been registered");
        }
        found.setName(user.getName() != null ? user.getName() : found.getName());
        found.setSurname(user.getSurname() != null ? user.getSurname() : found.getSurname());
        found.setBirthdate(user.getBirthdate() != null ? user.getBirthdate() : found.getBirthdate());
        found.setEmail(user.getEmail() != null ? user.getEmail() : found.getEmail());
        found.setPhone(user.getPhone() != null ? user.getPhone() : found.getPhone());
        found.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : found.getPassword());
        User saved = userRepository.save(found);
        log.info("The user has updated their profile information: {}", saved);
        return userMapper.toUserFullDto(saved);
    }

    public UserFullDto getData(Long userId) {
        User user = getUserByIdFromRepository(userId);
        log.info("The user has requested their data: {}", user);
        return userMapper.toUserFullDto(user);
    }

    private User getUserByIdFromRepository(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("The user wasn't found"));
    }

    @Override
    public UserShortDto register(UserFullDto userFullDto) {
        if (userRepository.existsByPhone(userFullDto.getPhone())) {
            throw new AlreadyExistsException("Phone number has been taken");
        }

        if (userFullDto.getEmail() != null) {
            if (userRepository.existsByEmail(userFullDto.getEmail())) {
                throw new AlreadyExistsException("Email has been taken");
            }
        }

        Role role = roleRepository.findByName(USER).get();
        User newUser = userMapper.toUser(userFullDto);
        newUser.setRegistrationDate(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(userFullDto.getPassword()));
        newUser.setState(ACTIVE);
        newUser.setRole(role);
        User saved = userRepository.save(newUser);
        return userMapper.toUserShortDto(saved);
    }
}