package com.project1.project.controller;


import com.project1.project.model.ClientDocument;
import com.project1.project.model.Review;
import com.project1.project.repository.DocumentRepository;
import com.project1.project.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/savedocument")
    public ResponseEntity<UUID> saveDocument(@RequestBody ClientDocument document) {
        UUID documentId = documentService.saveDocument(document);
        return ResponseEntity.ok(documentId);
    }


    @GetMapping("/getadocument/{id}")
    public ResponseEntity<ClientDocument> getDocument(@PathVariable("id") UUID document_id){
        System.out.println("received request for document ID: " + document_id);
        ResponseEntity<ClientDocument> document = documentService.getDocumentById(document_id);

       return ResponseEntity.ok(document.getBody());
    }

    @GetMapping("/documentofaperson/{personId}")
    public ResponseEntity<List<ClientDocument>> getDocumentsByPersonId(@PathVariable int personId) {
        List<ClientDocument> documents = documentService.getDocumentsByPersonId(personId);

        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(documents);
        }
    }

//    @PostMapping("/reviewdocument")
//    public  ResponseEntity<Review> saveOrUpdateReview(@RequestBody Review review){
//        Optional<Document> documentOptional = documentRepository.findById(UUID.fromString(review.getDocumentId()));
//
//        if(documentOptional.isPresent()){
//            review.setDocumentId(String.valueOf(documentOptional.get().getDocument_id()));
//            Review savedReview = documentService.saveOrUpdateReview(review);
//            return ResponseEntity.ok(savedReview);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("archivedocument/{documentId}")
    public ResponseEntity<String> deleteDocument(@PathVariable UUID documentId) {
        String message = documentService.deleteDocument(documentId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

//    @PutMapping("/editdocumentinfo/{id}")
//    public ResponseEntity<Document> updateDocument(@PathVariable("id") UUID documentId, @RequestBody Document updatedDocument) {
//        System.out.println("Received request to update document ID: " + documentId);
//        Document document = documentService.updateDocument(documentId, updatedDocument);
//        return ResponseEntity.ok(document);
//    }



}
