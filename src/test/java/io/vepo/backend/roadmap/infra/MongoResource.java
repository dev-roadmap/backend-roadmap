package io.vepo.backend.roadmap.infra;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.MongoDBContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class MongoResource implements QuarkusTestResourceLifecycleManager {

    static MongoDBContainer db = new MongoDBContainer("mongo:5");

    @Override
    public Map<String, String> start() {
        db.start();
        return Collections.singletonMap("quarkus.mongodb.connection-string", db.getReplicaSetUrl("tickets"));
    }

    @Override
    public void stop() {
        db.stop();
    }

}
