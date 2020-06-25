package br.com.marcia.error.security;

import br.com.marcia.error.entity.UserEntity;
import br.com.marcia.error.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getLoggedUser() {
        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username);

    }
}
