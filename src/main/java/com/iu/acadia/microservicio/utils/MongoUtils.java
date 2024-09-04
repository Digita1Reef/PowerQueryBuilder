package com.iu.acadia.microservicio.utils;

import com.iu.acadia.microservicio.exceptions.DocumentNotFound;
import com.iu.acadia.microservicio.exceptions.QueryBuilderUnprocessableEntityException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

@Slf4j
public class MongoUtils {

    private static MongoDatabase mongoDatabase;

    public MongoUtils(MongoClient mongoClientArg, String databaseName) {
        mongoDatabase = mongoClientArg.getDatabase(databaseName);
    }

    /**
     * Function to get a document from MongoDB collection using the query and collection passed.
     *
     * @param query query to fetch
     * @param collectionName collection to fetch
     * @return MongoDBDocument matches query and collection
     */
    private Document getDocumentByQuery(BasicDBObject query, String collectionName) throws DocumentNotFound
    {
        Document document = null;

        /*
        Getting a list of documents which matches the condition defined by the query.
         */
        FindIterable<Document> iterableDocuments = mongoDatabase
                .getCollection(collectionName)
                .find(query);

        /*
        Iterating to see how many documents were fetched.
         */
        for(Document doc: iterableDocuments) {
            if(doc != null) {
                /*
                There should be only one document fetched,
                as the condition is on the ID field.
                If the document is not null,
                assigning it to the campaign document object.
                 */
                document = doc;
            }
        }

        /*
        Throw Exception if matching document not found in the MongoDB
         */
        if(document == null) {
            throw new DocumentNotFound("Document not found!");
        }

        return document;
    }

    /**
     * Function to read Campaign as the parameter.
     *
     * @param campaignId The ID of the Campaign
     * @throws QueryBuilderUnprocessableEntityException Thrown if the Campaign is not found.
     */
    public Document getCampaignObject(String campaignId)
            throws QueryBuilderUnprocessableEntityException {

        if(campaignId.isBlank()) {
            throw new QueryBuilderUnprocessableEntityException("Campaign Id is either null or empty");
        }

        /*
        Defining the query to get the Campaign object.
         */
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(campaignId));
        log.info("Fetching Campaign with ID: {}", campaignId);
        try {
            return getDocumentByQuery(query, Constants.CAMPAIGN_COLLECTION_NAME);
        } catch (DocumentNotFound e) {
            log.info("Campaign document with ID {} not found!", campaignId);
            return null;
        }
    }

    /**
     * Function to read AudienceCluster as the parameter.
     *
     * @param audienceClusterId The ID of the audience cluster
     */
    public Document getAudienceClusterObject(String audienceClusterId)
            throws QueryBuilderUnprocessableEntityException {

        if(audienceClusterId.isBlank()) {
            throw new QueryBuilderUnprocessableEntityException("Audience Cluster Id is either null or empty");
        }

        /*
        Defining the query to get the AudienceCluster object.
         */
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(audienceClusterId));
        log.info("Fetching AudienceCluster with ID: {}", audienceClusterId);

        try {
            return getDocumentByQuery(query, Constants.AUDIENCE_CLUSTER_COLLECTION_NAME);
        } catch (DocumentNotFound e) {
            log.info("AudienceCluster document with ID {} not found!", audienceClusterId);
            return null;
        }
    }

    /**
     * Function to get the InstalledApps object from MongoDB collection.
     *
     * @param installedAppsId ID of the InstalledApps to be fetched
     * @return InstalledAppsDocument for the given ID
     */
    public Document getInstalledAppsObject(String installedAppsId)
            throws QueryBuilderUnprocessableEntityException {

        if(installedAppsId.isBlank()) {
            throw new QueryBuilderUnprocessableEntityException("Installed Apps Id is either null or empty");
        }

        /*
        Defining the query to fetch the InstalledApps object.
         */
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(installedAppsId));
        log.info("Fetching InstalledApps with ID: {}", installedAppsId);
        try {
            return getDocumentByQuery(query, Constants.INSTALLED_APPS_COLLECTION_NAME);

        } catch (DocumentNotFound e) {

            log.info("InstalledApps document with ID {} not found!", installedAppsId);
            return null;
        }
    }

    /**
     * Function to get the organization object from MongoDB collection.
     *
     * @param organizationId ID of the Organization to be fetched
     * @return OrganizationDocument for the given ID
     */
    public Document getOrganizationObject(String organizationId)
            throws QueryBuilderUnprocessableEntityException {

        if(organizationId.isBlank()) {
            throw new QueryBuilderUnprocessableEntityException("Organization Id is either null or empty");
        }

        /*
        Defining the query to fetch the organization object on organization ID.
         */
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(organizationId));
        log.info("Fetching organization with ID: {}", organizationId);
        try {
            return getDocumentByQuery(query, Constants.ORGANIZATION_COLLECTION_NAME);
        } catch (DocumentNotFound e) {
            log.info("Organization document with ID {} not found!", organizationId);
            return null;
        }
    }

    /**
     * Function to get the configuration object from MongoDB collection.
     *
     * @param scope scope of Configuration to be fetched
     * @return ConfigurationDocument for the given scope
     */
    public Document getConfigurationObjectByScope(String scope)
            throws QueryBuilderUnprocessableEntityException {

        if(scope.isBlank()) {
            throw new QueryBuilderUnprocessableEntityException("Scope is either null or empty");
        }

        /*
        Defining the query to fetch the Configuration object on scope.
         */
        BasicDBObject query = new BasicDBObject();
        query.put("scope", scope);
        log.info("Fetching Configuration for scope: {}", scope);
        try {
            return getDocumentByQuery(query, Constants.CONFIGURATION_COLLECTION_NAME);

        } catch (DocumentNotFound e) {
            log.error("Configuration document for scope {} not found!", scope);
            return null;
        }
    }


    /**
     * Function to get the configuration object from MongoDB collection.
     *
     * @param scope scope of Configuration to be fetched
     * @param  tag tag of Configuration to be fetched
     * @return ConfigurationDocument for the given scope
     */
    public Document getConfigurationObjectByScopeAndTag(String scope, String tag)
            throws QueryBuilderUnprocessableEntityException {

        if (scope.isBlank()) {
            throw new QueryBuilderUnprocessableEntityException("Scope is either null or empty");
        }

        /*
        Defining the query to fetch the Configuration object on scope.
         */
        BasicDBObject query = new BasicDBObject();
        query.put("scope", scope);
        query.put("tag", tag);
        log.info("Fetching Configuration for scope: {}  and  tag: {}", scope, tag);
        try {
            return getDocumentByQuery(query, Constants.CONFIGURATION_COLLECTION_NAME);

        } catch (DocumentNotFound e) {
            log.error("Configuration document for scope {} : and  tag: {} not found! ", scope, tag);
            return null;
        }
    }
}
