package com.example.practice.repository;

import com.example.practice.entity.ConfigJournalAppEntity;
import com.example.practice.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
