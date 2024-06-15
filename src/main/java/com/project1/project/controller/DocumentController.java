package com.project1.project.controller;



import com.project1.project.model.ArchiveDocument;

import com.project1.project.model.ClientDocument;
import com.project1.project.model.Review;
import com.project1.project.repository.DocumentRepository;
import com.project1.project.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import javax.swing.text.html.Option;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentController(DocumentService documentService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/savedocument")
    public ResponseEntity<UUID> saveDocument(@RequestBody ClientDocument document) {
        UUID documentId = documentService.saveDocument(document);
        return ResponseEntity.ok(documentId);
    }

    @GetMapping("/getadocument/{id}")
    public ResponseEntity<ClientDocument> getDocument(@PathVariable("id") UUID documentId) {
        ResponseEntity<ClientDocument> document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document.getBody());
    }

    @GetMapping("/documentofaperson/{personId}")
    public ResponseEntity<List<ClientDocument>> getDocumentsByPersonId(@PathVariable("personId") int personId) {
        System.out.println("received request for personId: " + personId);
        List<ClientDocument> documents = documentService.getDocumentsByPersonId(personId);

        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(documents);
        }
    }

    @PostMapping("/reviewdocument")

    public ResponseEntity<?> saveOrUpdateReview(@RequestBody Review review) {
        Optional<ClientDocument> clientdocumentOptional = documentRepository.findByApplicationTransactionId(review.getApplicationTransactionId());

        if (clientdocumentOptional.isPresent()) {
            review.setApplicationTransactionId(clientdocumentOptional.get().getFile_information().getApplication_transaction_id());
            Review savedReview = documentService.saveOrUpdateReview(review);

            return ResponseEntity.ok(savedReview);
        }else{

            return ResponseEntity.notFound().build();
        }
    }





    @PostMapping("/archivedocument")
    public ResponseEntity<?> archiveDocument(@RequestBody ArchiveDocument archiveDocument) {
        Optional<ClientDocument> clientDocumentOptional = documentRepository.findByApplicationTransactionId(archiveDocument.getApplicationTransactionId());

        if (clientDocumentOptional.isPresent()) {
            ArchiveDocument savedArchiveDocument = documentService.archiveDocument(archiveDocument);
            return ResponseEntity.ok(savedArchiveDocument);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/viewreviewlog/{applicationTransactionId}")
    public ResponseEntity<Review> getReviewByApplicationId(@PathVariable long applicationTransactionId) {
        Optional<Review> reviewOptional = documentService.getReviewByApplicationTransactionId(applicationTransactionId);

        return reviewOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vieweditlog/{applicationTransactionId}")
    public ResponseEntity<ArchiveDocument> getArchiveDocumentByApplicationTransactionId(@PathVariable long applicationTransactionId) {
        Optional<ArchiveDocument> archiveDocumentOptional = documentService.getArchiveDocumentByApplicationTransactionId(applicationTransactionId);

        if (archiveDocumentOptional.isPresent()) {
            return ResponseEntity.ok(archiveDocumentOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
