package com.project1.project.repository;

import com.project1.project.model.ArchiveDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ArchiveRepository extends MongoRepository<ArchiveDocument, Long> {
    @Query("{'application_transaction_id' :  ?0 }")
    Optional<ArchiveDocument> findByApplicationTransactionId(Long applicationTransactionId);
}
