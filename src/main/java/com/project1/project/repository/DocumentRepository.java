package com.project1.project.repository;


import com.project1.project.model.DocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends MongoRepository<DocumentEntity, UUID> {

}
