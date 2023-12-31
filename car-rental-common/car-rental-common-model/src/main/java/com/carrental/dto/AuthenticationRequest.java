package com.carrental.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(@NotEmpty(message = "Username cannot be empty")
                                    String username,
                                    @NotEmpty(message = "Password cannot be empty")
                                    String password) {

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + username  + "\n" +
                "password='" + password  + "\n" +
                "}";
    }

}
