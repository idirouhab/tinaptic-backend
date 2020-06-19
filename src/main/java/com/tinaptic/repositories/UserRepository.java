package com.tinaptic.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.tinaptic.models.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class UserRepository {

  @Inject MongoClient mongoClient;
  protected static final String COLLECTION_NAME = "users";

  public Optional<User> findByUsername(String username) {
    User user = getCollection().find(eq("username", username)).limit(1).first();
    if (user != null) {
      return Optional.of(user);
    }

    return Optional.empty();
  }

  public Optional<User> findById(String id) {
    User user = getCollection().find(eq("_id", id)).limit(1).first();
    if (user != null) {
      return Optional.of(user);
    }

    return Optional.empty();
  }

  private MongoCollection<User> getCollection() {
    return mongoClient.getDatabase("school_manager_db").getCollection(UserRepository.COLLECTION_NAME, User.class);
  }
}
