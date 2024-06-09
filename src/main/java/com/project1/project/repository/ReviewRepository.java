package com.project1.project.repository;

import com.project1.project.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByDocumentId(String document_id);
}
