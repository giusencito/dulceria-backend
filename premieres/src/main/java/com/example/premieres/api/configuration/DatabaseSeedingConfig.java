package com.example.premieres.api.configuration;
import com.example.premieres.domain.service.PremiereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;

@Component
public class DatabaseSeedingConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeedingConfig.class);

    @Autowired
    private PremiereService premiereService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    public void createProcedure() {
        String dropSp = "DROP PROCEDURE IF EXISTS sp_get_premieres";
        jdbcTemplate.execute(dropSp);

        String createSp =
                "CREATE PROCEDURE sp_get_premieres() " +
                        "BEGIN " +
                        "    SELECT id, image_url, description, created_at, updated_at " +
                        "    FROM premieres; " +
                        "END";

        jdbcTemplate.execute(createSp);
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event){
        String name = event.getApplicationContext().getId();
        logger.info("Starting Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
        premiereService.generatePremieres();
        createProcedure();
        logger.info("Porcedures", name, new Timestamp(System.currentTimeMillis()));
        logger.info("Finished Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }
}
