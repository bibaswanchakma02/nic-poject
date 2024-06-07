package com.project1.project.service;

import com.project1.project.model.Document;
import com.project1.project.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

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
        Optional<Document> documentOptional = documentRepository.findByClientId(clientId);

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Document with clientId " + clientId + " not found");
        }
    }


}
