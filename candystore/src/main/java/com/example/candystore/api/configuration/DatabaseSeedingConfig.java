package com.example.candystore.api.configuration;
import com.example.candystore.domain.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Component
public class DatabaseSeedingConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeedingConfig.class);

    @Autowired
    private  ProductService productService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    public void createProcedureProducts() {
        String dropSp = "DROP PROCEDURE IF EXISTS sp_get_products";
        jdbcTemplate.execute(dropSp);
        String createSp =
                "CREATE PROCEDURE sp_get_products() " +
                        "BEGIN " +
                        "    SELECT id, name, description, price, created_at, updated_at " +
                        "    FROM products; " +
                        "END";

        jdbcTemplate.execute(createSp);
    }


    @Transactional
    public void createProcedureProductsByIds() {
        String dropSp = "DROP PROCEDURE IF EXISTS sp_get_products_by_ids";
        jdbcTemplate.execute(dropSp);
        String sql =
                "CREATE PROCEDURE sp_get_products_by_ids(IN p_ids JSON) " +
                        "BEGIN " +
                        "    SELECT id, name, price " +
                        "    FROM products " +
                        "    WHERE id IN ( " +
                        "        SELECT CAST(jt.value AS UNSIGNED) " +
                        "        FROM JSON_TABLE( " +
                        "            p_ids, '$[*]' COLUMNS (value VARCHAR(20) PATH '$') " +
                        "        ) AS jt " +
                        "    ); " +
                        "END";

        jdbcTemplate.execute(sql);
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event){
        String name = event.getApplicationContext().getId();
        logger.info("Starting Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
        productService.generateProducts();
        createProcedureProductsByIds();
        createProcedureProducts();
        logger.info("Finished Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }
}
