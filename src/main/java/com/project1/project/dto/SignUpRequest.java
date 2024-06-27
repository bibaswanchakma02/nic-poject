package com.project1.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private long mobile_no;
    private String email_id;
    private String name;
    private String gender;
    private String dob;
    private String address;
}
