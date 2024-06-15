package com.project1.project.service;

import com.project1.project.model.ClientDocument;
import com.project1.project.model.Review;
import com.project1.project.repository.DocumentRepository;
import com.project1.project.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;
import java.util.logging.Logger;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MongoTemplate mongoTemplate;
    private final ReviewRepository reviewRepository;
    private static final Logger LOGGER = Logger.getLogger(DocumentService.class.getName());

    @Autowired
    public DocumentService(DocumentRepository documentRepository, MongoTemplate mongoTemplate, ReviewRepository reviewRepository) {
        this.documentRepository = documentRepository;
        this.mongoTemplate = mongoTemplate;
        this.reviewRepository = reviewRepository;
    }

    public UUID saveDocument(ClientDocument document) {
        document.setDocument_id(UUID.randomUUID());

        Date date = new Date();
        document.setCreated_on(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        Date expiryDate = calendar.getTime();
        document.setExpiry_on(expiryDate);

        documentRepository.save(document);
        return document.getDocument_id();
    }

    public ResponseEntity<ClientDocument> getDocumentById(UUID documentId) {
        System.out.println("searching for document id: " + documentId);
        ClientDocument document = documentRepository.findById(documentId).orElse(null);

        System.out.println("Document found: " + document);
        return ResponseEntity.ok(document);
    }

    public List<ClientDocument> getDocumentsByPersonId(int personId) {
        return documentRepository.findByPersonId(personId);
    }

    public Review saveOrUpdateReview(Review review){
        Optional<Review> existingReview = reviewRepository.findByDocumentId(review.getDocumentId());

        if(existingReview.isPresent()){
            Review existing = existingReview.get();
            existing.setFeedback(review.getFeedback());

            return reviewRepository.save(existing);
        }

        return reviewRepository.save(review);
    }

    public String deleteDocument(UUID documentId) {
        ClientDocument document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        mongoTemplate.save(document, "archive_documents");
        LOGGER.info("Document archived successfully with ID: " + documentId);

        documentRepository.deleteById(documentId);
        LOGGER.info("Document deleted successfully with ID: " + documentId);

        return "Document archived successfully";
    }

    public Optional<Review> getReviewByDocumentId(String documentId) {
        return reviewRepository.findByDocumentId(documentId);
    }
}
