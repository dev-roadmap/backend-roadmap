package io.vepo.backend.roadmap.usuarios;

import java.util.stream.Stream;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback;
import io.quarkus.test.junit.callback.QuarkusTestMethodContext;

public class MongoCleanup implements QuarkusTestBeforeEachCallback {

    @Override
    public void beforeEach(QuarkusTestMethodContext context) {

        if (Stream.of(context.getTestInstance().getClass().getAnnotationsByType(QuarkusTestResource.class))
                  .anyMatch(resource -> resource.value() == MongoResource.class)) {
            try (MongoClient client = MongoClients.create(MongoResource.db.getReplicaSetUrl("tickets"))) {
                MongoDatabase database = client.getDatabase("tickets");
                database.listCollections().forEach(document -> {
                    database.getCollection(document.getString("name")).deleteMany(new Document());
                });
            }
        }
    }

}
