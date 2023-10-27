package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.PersistentLogin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersistentLoginRepository extends MongoRepository<PersistentLogin, String> {
    Optional<PersistentLogin> findBySeries(String series);
    boolean existsBySeries(String series);
    void deleteAllByUsername(String username);
}
