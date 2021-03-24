package com.epolsoft.wtr.service;

import java.util.List;
import com.epolsoft.wtr.excepcion.NotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(Integer id) {
        User user = userRepository.findByUserId(id);
        if (user == null) {
            throw new NotFoundException("Error user not found");
        }
        return  user;
    }

    @Transactional
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        
        //users.forEach(e -> Hibernate.initialize(e.getReports()));
        //users.forEach(e -> Hibernate.initialize(e.getLikedProjects()));

        if (users == null) {
            throw new ValidationException("Error users not exist");
        }

        return users;
    }

    public User createOrUpdate(User user) {
    	if (user.equals(null)) {
            throw new NotFoundException("User was not received");
        } else if (userRepository.findByName(user.getUserName())!=null) {
        	throw new NotFoundException("This name used");
        } else if (user.getUserPassword().equals(null)){
        	throw new NotFoundException("Enter password!");
        }
        return userRepository.save(user);
    }
    
    public User findByName(String userName){
        if (userName == null) {
            throw new NotFoundException("Error name was not imputed");
        }
        User user = userRepository.findByName(userName);
        return user;
    }

}
