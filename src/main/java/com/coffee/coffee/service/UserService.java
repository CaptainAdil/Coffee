package com.coffee.coffee.service;

import com.coffee.coffee.dto.UserDto;
import com.coffee.coffee.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findByUsername(String username);

    List<User> findAllUsers();

    Page<User> findPaginated(int pageNo, int pageSize);

    void update(Long id,UserDto userDto);

    void delete(long id);

    User getById(Long id);
}
