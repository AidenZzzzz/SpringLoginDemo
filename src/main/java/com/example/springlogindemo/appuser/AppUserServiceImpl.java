package com.example.springlogindemo.appuser;

import com.example.springlogindemo.registration.token.ConfirmationToken;
import com.example.springlogindemo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author aiden
 */

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(AppUser appUser)
    {

        boolean userExist = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if(userExist) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        // TODO: 6/15/22 Send confirmation token

        //save user
        appUserRepository.save(appUser);


        // send confirm token
        // create a token then save use the repo
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
                );

        service.saveConfirmationToken(confirmationToken);

        // TODO: 6/16/22 Send email to confirm 

        return token;
    }

}
