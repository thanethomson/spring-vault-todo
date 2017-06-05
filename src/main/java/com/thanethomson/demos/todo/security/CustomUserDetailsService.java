package com.thanethomson.demos.todo.security;

import com.thanethomson.demos.todo.repos.UserRepo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Allows us to look up users by way of their e-mail address.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final Boolean adminUserEnabled;
    private final String adminPassword;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(
            UserRepo userRepo,
            Boolean adminUserEnabled,
            String adminPassword,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.adminUserEnabled = adminUserEnabled;
        this.adminPassword = adminPassword;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (adminUserEnabled && username.equals("admin")) {
            return new User(
                    "admin",
                    passwordEncoder.encode(adminPassword),
                    AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")
            );
        }
        // otherwise we try to look up our user from the database, by e-mail address
        com.thanethomson.demos.todo.models.User user = userRepo.findFirstByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No such user: %s", username));
        }
        return new User(username, user.password, AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

}
