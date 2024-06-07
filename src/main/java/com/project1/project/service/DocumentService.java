package com.project1.project.service;

import com.project1.project.model.DocumentEntity;
import com.project1.project.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public UUID saveDocument(DocumentEntity document) {
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

    public ResponseEntity<DocumentEntity> getDocumentById(UUID documentId) {
        System.out.println("searching for document id: " + documentId);
        DocumentEntity document = documentRepository.findById(documentId).orElse(null);

        System.out.println("Document found : " + document);
        return ResponseEntity.ok(document);

    }
}
