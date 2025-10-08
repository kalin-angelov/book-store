package app.user.service;

import app.user.model.User;
import app.user.model.UserPrincipal;
import app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with the provided email is not found."));

        return new UserPrincipal(user);
    }
}
