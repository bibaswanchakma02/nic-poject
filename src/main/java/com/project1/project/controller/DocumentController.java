package com.project1.project.controller;


import com.project1.project.model.ArchiveDocument;
import com.project1.project.model.ClientDocument;
import com.project1.project.model.Review;
import com.project1.project.model.WatermarkRequest;
import com.project1.project.repository.DocumentRepository;
import com.project1.project.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.Option;
import java.io.IOException;
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
    public ResponseEntity<ClientDocument> getDocument(@PathVariable("id") UUID document_id){
        System.out.println("received request for document ID: " + document_id);
        ResponseEntity<ClientDocument> document = documentService.getDocumentById(document_id);

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
    public ResponseEntity<?> archiveDocument(@RequestBody ArchiveDocument archiveDocument){
        Optional<ClientDocument> clientdocumentOptional = documentRepository.findByApplicationTransactionId(archiveDocument.getApplicationTransactionId());

        if (clientdocumentOptional.isPresent()) {
            archiveDocument.setApplicationTransactionId(clientdocumentOptional.get().getFile_information().getApplication_transaction_id());
            ArchiveDocument saveArchiveDocument = documentService.archiveDocument(archiveDocument);

            return ResponseEntity.ok(saveArchiveDocument);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addwatermarktodocument")
    public ResponseEntity<?> addWatermarkToDocument(@RequestBody WatermarkRequest watermarkRequest){
        try {
            ClientDocument updatedDocument = documentService.addWatermarkToDocument(watermarkRequest.getApplicationTransactionId(),
                    watermarkRequest.getWatermark());
            return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/editdocumentinfo/{id}")
//    public ResponseEntity<Document> updateDocument(@PathVariable("id") UUID documentId, @RequestBody Document updatedDocument) {
//        System.out.println("Received request to update document ID: " + documentId);
//        Document document = documentService.updateDocument(documentId, updatedDocument);
//        return ResponseEntity.ok(document);
//    }



}
