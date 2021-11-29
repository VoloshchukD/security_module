package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public boolean add(User user) {
        if (user.getRole() == User.Role.ADMINISTRATOR) {
            return false;
        }
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user) != null;
    }

    @Override
    public User find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new DataNotFoundException(ExceptionMessageHandler.USER_CODE,
                ExceptionMessageHandler.USER_NOT_FOUND_MESSAGE_NAME));
    }

    @Override
    public List<User> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        int convertedPageNumber = PaginationLogics.convertPage(page, itemCount);
        return userRepository.findAll(PageRequest.of(convertedPageNumber, itemCount)).getContent();
    }

    @Override
    public User update(User user) throws ParameterNotPresentException, DataNotFoundException {
        User forUpdate = find(user.getId());
        setUpdateData(user, forUpdate);
        return userRepository.save(forUpdate);
    }

    private void setUpdateData(User data, User target) {
        if (data.getForename() != null) {
            target.setForename(data.getForename());
        }
        if (data.getSurname() != null) {
            target.setSurname(data.getSurname());
        }
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException, DataNotFoundException {
        User user = find(id);
        userRepository.delete(user);
        return true;
    }

}
