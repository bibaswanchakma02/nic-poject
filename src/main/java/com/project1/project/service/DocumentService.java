package com.project1.project.service;

import com.project1.project.model.ArchiveDocument;
import com.project1.project.model.ClientDocument;

import com.project1.project.model.Review;
import com.project1.project.repository.ArchiveRepository;
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

    @Autowired
    private final DocumentRepository documentRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    private ReviewRepository reviewRepository;
    private static final Logger LOGGER = Logger.getLogger(DocumentService.class.getName());
    @Autowired
    public DocumentService(DocumentRepository documentRepository, MongoTemplate mongoTemplate) {
        this.documentRepository = documentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public ArchiveRepository archiveRepository;

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

        System.out.println("Document found : " + document);
        return ResponseEntity.ok(document);

    }
    public List<ClientDocument> getDocumentsByPersonId(int personId) {
        System.out.println("searching for document with personId: " + personId);
        return documentRepository.findByPersonId(personId);

    }

    public Review saveOrUpdateReview(Review review) {
        Optional<Review> existingReview = reviewRepository.findByApplicationTransactionId(review.getApplicationTransactionId());

        if (existingReview.isPresent()) {
            Review existing = existingReview.get();
            existing.setReview(review.getReview());

            return reviewRepository.save(existing);
        }

        return reviewRepository.save(review);
    }

    public ArchiveDocument archiveDocument(ArchiveDocument archiveDocument) {
        Optional<ArchiveDocument> existingArhcive = archiveRepository.findByApplicationTransactionId(Long.valueOf(archiveDocument.getApplicationTransactionId()));

        if(existingArhcive.isPresent()){
            ArchiveDocument archivedoc = existingArhcive.get();
            archivedoc.setArchival_comments(archiveDocument.getArchival_comments());

            return archiveRepository.save(archivedoc);

        }

        return archiveRepository.save(archiveDocument);
    }




    public Optional<Review> getReviewByApplicationTransactionId(long applicationTransactionId) {
        return reviewRepository.findByApplicationTransactionId(applicationTransactionId);
    }








}
