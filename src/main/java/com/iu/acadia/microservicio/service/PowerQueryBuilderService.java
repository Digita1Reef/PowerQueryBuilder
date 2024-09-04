package com.iu.acadia.microservicio.service;

import com.iu.acadia.microservicio.utils.MongoUtils;
import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PowerQueryBuilderService {
    private final MongoUtils mongoUtils;

    public PowerQueryBuilderService(MongoClient mongoClient, String databaseName) {
        this.mongoUtils = new MongoUtils(mongoClient, databaseName);
    }
}
