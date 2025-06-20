package fr.dauphine.activities.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoInitializer {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void createCollectionIfNotExists() {
        List<String> collectionNames = List.of("activities", "pois");

        for (String collectionName : collectionNames) {
            if (!mongoTemplate.collectionExists(collectionName)) {
                mongoTemplate.createCollection(collectionName);
                System.out.println("✅ Collection " + collectionName + "created.");
            } else {
                System.out.println("ℹ️ Collection " + collectionName + " already exists.");
            }
        }
    }
}
