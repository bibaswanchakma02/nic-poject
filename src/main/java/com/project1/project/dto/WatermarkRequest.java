package com.project1.project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatermarkRequest {

    private long application_transaction_id;
    private String watermark;
}
