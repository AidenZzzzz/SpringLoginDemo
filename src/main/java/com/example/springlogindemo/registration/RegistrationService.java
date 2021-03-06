package com.example.springlogindemo.registration;

import com.example.springlogindemo.appuser.AppUser;
import com.example.springlogindemo.appuser.AppUserRole;
import com.example.springlogindemo.appuser.AppUserServiceImpl;
import com.example.springlogindemo.registration.token.ConfirmationToken;
import com.example.springlogindemo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author aiden
 */
@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserServiceImpl appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {


        //check email valid
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException("email not valid");
        }

        //use a method to register new user
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

//    public String confirmToken()
//    {
//        ConfirmationToken confirmationToken = confirmationTokenService.getToken()
//    }
}
