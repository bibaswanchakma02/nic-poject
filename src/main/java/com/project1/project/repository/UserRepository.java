package com.project1.project.repository;

import com.project1.project.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<Client, String> {
    @Query("{'client_id': ?0}")
    Optional<Client> findByClientId(String client_id);

    @Query("{'mobile_no' :  ?0}")
    Optional<Client> findByMobileNo(long mobile_no);
}