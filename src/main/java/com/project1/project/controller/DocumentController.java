package com.project1.project.controller;


import com.project1.project.model.Document;
import com.project1.project.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;


    @PostMapping("/savedocument")
    public String saveDocument(@RequestBody Document document) {
        UUID document_id = documentService.saveDocument(document);
        return "Document saved successfully with id: " + document_id;
    }


    @GetMapping("/getadocument/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable("id") UUID document_id){
        System.out.println("received request for document ID: " + document_id);
        ResponseEntity<Document> document = documentService.getDocumentById(document_id);

       return ResponseEntity.ok(document.getBody());
    }


}
