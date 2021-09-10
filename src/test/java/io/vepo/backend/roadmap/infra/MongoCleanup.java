package io.vepo.backend.roadmap.infra;

import java.util.Objects;
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
        if (isMongoTest(context.getTestInstance().getClass())) {
            try (MongoClient client = MongoClients.create(MongoResource.db.getReplicaSetUrl("tickets"))) {
                MongoDatabase database = client.getDatabase("tickets");
                database.listCollections().forEach(document -> {
                    database.getCollection(document.getString("name")).deleteMany(new Document());
                });
            }
        }
    }

    private boolean isMongoTest(Class<?> testClass) {
        return (Objects.nonNull(testClass.getEnclosingClass()) && isMongoTest(testClass.getEnclosingClass()))
                || Stream.of(testClass.getAnnotationsByType(QuarkusTestResource.class))
                         .anyMatch(resource -> resource.value() == MongoResource.class);
    }

}
