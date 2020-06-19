package com.tinaptic.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.tinaptic.models.RefreshToken;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class RefreshTokenRepository {

  @Inject MongoClient mongoClient;
  protected static final String COLLECTION_NAME = "refreshtokens";

  public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
    RefreshToken rT = getCollection().find(eq("refreshToken", refreshToken)).limit(1).first();
    if (rT != null) {
      return Optional.of(rT);
    }

    return Optional.empty();
  }

  public void save(RefreshToken refreshToken) {
    getCollection().insertOne(refreshToken);
  }

  private MongoCollection<RefreshToken> getCollection() {
    return mongoClient
        .getDatabase("school_manager_db")
        .getCollection(RefreshTokenRepository.COLLECTION_NAME, RefreshToken.class);
  }
}
