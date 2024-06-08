package com.project1.project.service;

import com.project1.project.model.Document;
import com.project1.project.model.Review;
import com.project1.project.repository.DocumentRepository;
import com.project1.project.repository.ReviewRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public UUID saveDocument(Document document) {
        document.setDocument_id(UUID.randomUUID());

        Date date = new Date();
        document.setCreated_on(date);

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.YEAR, 1);
        Date expiryDate = calender.getTime();
        document.setExpiry_on(expiryDate);

        documentRepository.save(document);
        return UUID.fromString(document.getDocument_id().toString());
    }

    public ResponseEntity<Document> getDocumentById(UUID documentId) {
        System.out.println("searching for document id: " + documentId);
        Document document = documentRepository.findById(documentId).orElse(null);

        System.out.println("Document found : " + document);
        return ResponseEntity.ok(document);

    }
    public ResponseEntity<?> getDocumentByClientId(String clientId) {
        System.out.println("Searching for document with clientId: " + clientId);
        List<Document> documents = documentRepository.findByClientId(clientId);

        if (documents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Documents with clientId " + clientId + " not found");
        } else {
            return ResponseEntity.ok(documents);
        }
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


    @Transactional
    public Document updateDocument(UUID documentId, Document updatedDocument) {
        System.out.println("Updating document ID: " + documentId);

        // Retrieve the document from the database
        Document documentToUpdate = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id " + documentId));

        // Update each field manually
        documentToUpdate.setClient_id(updatedDocument.getClient_id());
        documentToUpdate.setClient_secret(updatedDocument.getClient_secret());
//        documentToUpdate.setCreated_on(updatedDocument.getCreated_on());
//        documentToUpdate.setExpiry_on(updatedDocument.getExpiry_on());
        documentToUpdate.setState_code(updatedDocument.getState_code());
        documentToUpdate.setState_name(updatedDocument.getState_name());
        documentToUpdate.setDepartment_code(updatedDocument.getDepartment_code());
        documentToUpdate.setDepartment_name(updatedDocument.getDepartment_name());
        documentToUpdate.setGovt(updatedDocument.getGovt());
        documentToUpdate.setNodal_officer_name(updatedDocument.getNodal_officer_name());
        documentToUpdate.setNodal_officer_mobile(updatedDocument.getNodal_officer_mobile());
        documentToUpdate.setNodal_officer_email(updatedDocument.getNodal_officer_email());
        documentToUpdate.setNodal_officer_designation(updatedDocument.getNodal_officer_designation());

        // Save the updated document
        Document savedDocument = documentRepository.save(documentToUpdate);
        System.out.println("Document updated: " + savedDocument);

        return savedDocument;
    }


}
