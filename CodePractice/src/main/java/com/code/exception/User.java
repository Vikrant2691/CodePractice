package com.code.exception;

import java.io.IOException;
import java.util.Optional;

public class User {

int id;
String name;





Optional<String> getName(){

    return Optional.of("Vikrant");

}

    void setName(String name) throws UserException {

    if(name.charAt(0)=='A')
        throw new UserException("Invalid user name");
    }


    public static void main(String[] args) {

    User user = new User();



    }
}
