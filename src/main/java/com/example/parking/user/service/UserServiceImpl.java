package com.example.parking.user.service;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserUpdateDto;
import com.example.parking.user.mapper.UserMapper;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.parking.enums.State.ACTIVE;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserFullDto create(UserFullDto user) {
        if (userRepository.existsByPhoneOrEmail(user.getPhone(), user.getEmail())) {
            throw new AlreadyExistsException("Пользователь с таким телефоном или почтой уже зарегистрирован");
        }
        User newUser = userMapper.toUser(user);
        newUser.setRegistrationDate(LocalDateTime.now());
        newUser.setState(ACTIVE);
        User saved = userRepository.save(newUser);
        log.info("Пользователь зарегистрирован: {}", saved);
        return userMapper.toUserFullDto(saved);
    }

    public UserFullDto update(Long userId, UserUpdateDto user) {
        User found = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (userRepository.existsByPhoneOrEmail(user.getPhone(), user.getEmail())) {
            throw new AlreadyExistsException("Пользователь с таким телефоном или почтой уже зарегистрирован");
        }
        found.setName(user.getName() != null ? user.getName() : found.getName());
        found.setSurname(user.getSurname() != null ? user.getSurname() : found.getSurname());
        found.setBirthdate(user.getBirthdate() != null ? user.getBirthdate() : found.getBirthdate());
        found.setEmail(user.getEmail() != null ? user.getEmail() : found.getEmail());
        found.setPhone(user.getPhone() != null ? user.getPhone() : found.getPhone());
        found.setPassword(user.getPassword() != null ? user.getPassword() : found.getPassword());
        User saved = userRepository.save(found);
        log.info("Пользователь обновил информацию профиля: {}", saved);
        return userMapper.toUserFullDto(saved);
    }

    public UserFullDto getData(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Данные о пользователе отсутствуют"));
        log.info("Пользователь запросил свои данные {}", user);
        return userMapper.toUserFullDto(user);
    }
}
