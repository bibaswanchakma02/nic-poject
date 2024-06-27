package com.project1.project.service;

import com.project1.project.config.DateConfig;
import com.project1.project.dto.SignUpRequest;
import com.project1.project.model.Client;
import com.project1.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ClientService {

    @Autowired
    private UserRepository clientRepository;


    public Optional<String> login(long mobileNo){
        Optional<Client> optionalClient = clientRepository.findByMobileNo(mobileNo);
        return optionalClient.isPresent() ? Optional.of(generateOtp()) : Optional.empty();
    }

    private String generateOtp(){
        return String.format("%04d", new Random().nextInt(10000));
    }

    public Optional<Client> signup(String clientId, SignUpRequest signUpRequest){
        if(!DateConfig.isValidDate(signUpRequest.getDob())){
            throw new IllegalArgumentException("Invalid Date format. Please use dd-MM-yyyy");
        }

        return clientRepository.findByClientId((clientId))
                .map(client -> {
                    client.setMobile_no(signUpRequest.getMobile_no());
                    client.setEmail_id(signUpRequest.getEmail_id());
                    client.setName(signUpRequest.getName());
                    client.setGender(signUpRequest.getGender());
                    client.setDob(signUpRequest.getDob());
                    client.setAddress(signUpRequest.getAddress());

                    return clientRepository.save(client);
                });
    }


}
