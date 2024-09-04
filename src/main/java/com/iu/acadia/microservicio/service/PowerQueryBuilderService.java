package com.iu.acadia.microservicio.service;

import com.iu.acadia.microservicio.utils.MongoUtils;
import com.mongodb.client.MongoClient;

public class PowerQueryBuilderService {
    private final MongoUtils mongoUtils;

    public PowerQueryBuilderService(MongoClient mongoClient, String databaseName) {
        this.mongoUtils = new MongoUtils(mongoClient, databaseName);
    }
}
