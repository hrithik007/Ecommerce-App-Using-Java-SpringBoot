package com.ecommerce.webapp.service.impl;

import com.ecommerce.webapp.model.UserDtls;
import com.ecommerce.webapp.repository.UserRepository;
import com.ecommerce.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDtls saveUser(UserDtls user) {
        user.setRole("ROLE_USER");
        user.setIsEnable(true);
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        UserDtls saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public List<UserDtls> getUsers(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDtls> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        Optional<UserDtls> findByuser = userRepository.findById(id);

        if (findByuser.isPresent()) {
            UserDtls userDtls = findByuser.get();
            userDtls.setIsEnable(status);
            userRepository.save(userDtls);
            return true;
        }

        return false;
    }

    @Override
    public void increaseFailedAttempt(UserDtls user) {

    }

    @Override
    public void userAccountLock(UserDtls user) {

    }

    @Override
    public boolean unlockAccountTimeExpired(UserDtls user) {
        return false;
    }

    @Override
    public void resetAttempt(int userId) {

    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {
        UserDtls userDtls = userRepository.findByEmail(email);
        if(userDtls!=null){
            userDtls.setResetToken(resetToken);
            userRepository.save(userDtls);
        }
    }

    @Override
    public UserDtls getUserByToken(String token) {
        UserDtls userDtls = userRepository.findByResetToken(token);
        return userDtls;
    }

    @Override
    public UserDtls updateUser(UserDtls user) {
        return userRepository.save(user);
    }

    @Override
    public UserDtls updateUserProfile(UserDtls user, MultipartFile img) {
        return null;
    }

    @Override
    public UserDtls saveAdmin(UserDtls user) {
        return null;
    }

    @Override
    public Boolean existsEmail(String email) {
        return userRepository.existsByEmail(email) ;
    }

}
