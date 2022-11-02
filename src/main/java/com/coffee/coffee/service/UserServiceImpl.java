package com.coffee.coffee.service;

import com.coffee.coffee.dto.UserDto;
import com.coffee.coffee.exception.CoffeeException;
import com.coffee.coffee.exception.error.CoffeeResponseError;
import com.coffee.coffee.mapper.UserMapper;
import com.coffee.coffee.model.Role;
import com.coffee.coffee.model.User;
import com.coffee.coffee.repository.RoleRepository;
import com.coffee.coffee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;



    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();

        user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public void update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new CoffeeException(CoffeeResponseError.USER_NOT_FOUND_ID, Map.of("id",id)));

        user = userMapper.update(user,userDto);


        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CoffeeException(CoffeeResponseError.USER_NOT_FOUND_ID, Map.of("id",id)));
        userRepository.delete(user);
    }

    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CoffeeException(CoffeeResponseError.USER_NOT_FOUND_ID, Map.of("id",id)));
        return user;
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
}
