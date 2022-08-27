package com.foodei.project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String email;
    private String phone;
    private List<String> role;
    private Boolean enabled;
    private String password;
    private String avatar;
}
