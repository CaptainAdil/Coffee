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

//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        user.setUsername(userDto.getUsername());
//        user.setEmail(userDto.getEmail());
        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());


        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
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
        User user = userRepository.findById(id).get();

        user = userMapper.update(user,userDto);


        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

//    private UserDto convertEntityToDto(User user){
//        UserDto userDto = new UserDto();
//        String[] name = user.getFirstName().split(" ");
//        userDto.setFirstName(name[0]);
//        userDto.setLastName(name[1]);
//        userDto.setUsername(user.getUsername());
//        return userDto;
//    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
