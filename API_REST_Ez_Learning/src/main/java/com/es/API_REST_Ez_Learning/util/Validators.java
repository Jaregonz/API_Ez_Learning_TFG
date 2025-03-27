package com.es.API_REST_Ez_Learning.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";

    private static final Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern pattern_password = Pattern.compile(PASSWORD_PATTERN);


        public static boolean isValidEmail(String email) {
            if (email == null) {
                return false;
            }
                Matcher matcher = pattern_email.matcher(email);
                return matcher.matches();
        }

        public static boolean isValidPassword(String input) {
            if (input == null) {
                return false;
            }
            Matcher matcher = pattern_password.matcher(input);
            return matcher.matches();
        }



}
