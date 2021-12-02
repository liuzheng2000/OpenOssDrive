package edu.sdut.Service.ServiceImpl;

import edu.sdut.Entity.JwtUser;
import edu.sdut.Entity.User;
import edu.sdut.Service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 22:35
 */
@Service
public class UserRepositoryImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new JwtUser(user);
    }


}
