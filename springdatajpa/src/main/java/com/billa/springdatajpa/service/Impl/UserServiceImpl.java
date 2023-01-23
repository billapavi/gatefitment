package com.billa.springdatajpa.service.Impl;

import com.billa.springdatajpa.authentication.UserPrinciple;
import com.billa.springdatajpa.domaine.User;
import com.billa.springdatajpa.repository.UserRepository;
import com.billa.springdatajpa.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);

        if(user == null) {
            logger.error("user not found by user name "+ username);
            throw new UsernameNotFoundException("user not found by username "+ username);
        }else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrinciple userPrinciple = new UserPrinciple(user);
            logger.info("returned by username and pass "+userPrinciple);
            return userPrinciple;
        }
//        return null;
    }
}
