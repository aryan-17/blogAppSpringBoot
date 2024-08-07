package com.example.practice.repository;

import com.example.practice.entity.JournalEntry;
import com.example.practice.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
    void deleteUserByUsername(String username);
}
