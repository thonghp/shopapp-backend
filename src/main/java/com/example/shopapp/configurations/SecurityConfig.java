package com.example.shopapp.configurations;

import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    //user's detail object
    @Bean // chạy thứ 3
    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
//                User existingUser = userRepository.findByPhoneNumber(phoneNumber)
//                        .orElseThrow(new Supplier<UsernameNotFoundException>() {
//                    @Override
//                    public UsernameNotFoundException get() {
//                        return new UsernameNotFoundException("Cannot find user with phone number = " + phoneNumber);
//                    }
//                });
//                return existingUser;
//            }
//        };

//        lambda
//        return phoneNumber -> {
//            User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
//                    -> new UsernameNotFoundException("Cannot find user with phone number = " + phoneNumber));
//            return existingUser;
//        };

        // login via phone number
        return phoneNumber -> userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with phone number = " + phoneNumber));
    }

    @Bean // khi chạy ứng dụng này sẽ chạy đầu tiên
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // chạy thứ 4
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean // chạy thứ 2
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
