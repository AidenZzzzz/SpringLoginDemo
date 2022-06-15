package com.example.springlogindemo.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * @author aiden
 */

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        // TODO: 6/15/22 Regex validate email
        return true;
    }
}
