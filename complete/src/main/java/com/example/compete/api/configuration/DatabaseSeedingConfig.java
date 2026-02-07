package com.example.compete.api.configuration;


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
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void createProcedureCreateOrder() {
        String dropSp = "DROP PROCEDURE IF EXISTS sp_create_order";
        jdbcTemplate.execute(dropSp);
        String createSp =
                "CREATE PROCEDURE sp_create_order( " +
                        "    IN p_email VARCHAR(255), " +
                        "    IN p_name VARCHAR(255), " +
                        "    IN p_document_type VARCHAR(10), " +
                        "    IN p_document_number VARCHAR(20), " +
                        "    IN p_transaction_id VARCHAR(100), " +
                        "    IN p_operation_date DATETIME, " +
                        "    IN p_items JSON " +
                        ") " +
                        "BEGIN " +
                        "    DECLARE v_order_id BIGINT; " +
                        "    DECLARE i INT DEFAULT 0; " +
                        "    DECLARE v_length INT; " +
                        "    DECLARE EXIT HANDLER FOR SQLEXCEPTION " +
                        "    BEGIN " +
                        "        ROLLBACK; " +
                        "        BEGIN " +
                        "            DECLARE v_code CHAR(5) DEFAULT '00000'; " +
                        "            DECLARE v_msg TEXT; " +
                        "            DECLARE error_msg TEXT; " +
                        "            GET DIAGNOSTICS CONDITION 1 " +
                        "                v_code = RETURNED_SQLSTATE, " +
                        "                v_msg = MESSAGE_TEXT; " +
                        "            SET error_msg = IFNULL(v_msg, CONCAT('Error SQLSTATE: ', v_code)); " +
                        "            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_msg; " +
                        "        END; " +
                        "    END; " +
                        "    START TRANSACTION; " +
                        "    INSERT INTO orders (email, name, document_type, document_number, transaction_id, operation_date, created_at, updated_at) " +
                        "    VALUES (p_email, p_name, p_document_type, p_document_number, p_transaction_id, p_operation_date, NOW(), NOW()); " +
                        "    SET v_order_id = LAST_INSERT_ID(); " +
                        "    SET v_length = JSON_LENGTH(p_items); " +
                        "    WHILE i < v_length DO " +
                        "        INSERT INTO order_details (order_id, product_id, quantity, created_at, updated_at) " +
                        "        VALUES (v_order_id, JSON_UNQUOTE(JSON_EXTRACT(p_items, CONCAT('$[', i, '].productId'))), JSON_EXTRACT(p_items, CONCAT('$[', i, '].quantity')), NOW(), NOW()); " +
                        "        SET i = i + 1; " +
                        "    END WHILE; " +
                        "    COMMIT; " +
                        "END";
        jdbcTemplate.execute(createSp);
    }
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event){
        String name = event.getApplicationContext().getId();
        logger.info("Starting Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
        createProcedureCreateOrder();
        logger.info("Finished Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }

}
