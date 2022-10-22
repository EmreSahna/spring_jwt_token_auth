package com.emresahna.demo.Model;

import lombok.Data;

@Data
public class IsLoggedInResponse {
    private String username;
    private String token;
}
