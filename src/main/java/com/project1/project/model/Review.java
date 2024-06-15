package com.project1.project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Review_document")
public class Review {

    @Id
    private String id;
    private long applicationTransactionId;
    private String review;


}
