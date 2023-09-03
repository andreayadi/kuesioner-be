package com.example.kuesioner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KuesionerRepository extends MongoRepository<Kuesioner, String> {
  
}
