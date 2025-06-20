package fr.dauphine.information.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

@Configuration
public class FlywayConfig {

    /**
     * Clean then migrate on each startup.
     */
    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            // Drop all objects
//            flyway.clean();
            // Recreate schema
            flyway.migrate();
        };
    }
//
//    /**
//     * Clean the database when the application context is closed.
//     */
//    @Bean
//    public ApplicationListener<ContextClosedEvent> cleanOnShutdown(@Autowired Flyway flyway) {
//        return event -> {
//            flyway.clean();
//        };
//    }
}