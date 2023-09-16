SET GLOBAL log_bin_trust_function_creators = 1;
# CREATE PROCEDURE
DELIMITER $
DROP FUNCTION IF EXISTS is_exists_database $
CREATE FUNCTION is_exists_database(`db_name` VARCHAR(255))
    RETURNS BOOLEAN DETERMINISTIC
BEGIN
    DECLARE `db_count` INT DEFAULT 0;
    SELECT COUNT(*) INTO `db_count` FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = `db_name`;
    IF `db_count` > 0 THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END $

DROP PROCEDURE IF EXISTS `create_database` $
CREATE PROCEDURE `create_database`(IN `db_name` VARCHAR(255))
BEGIN
    #CREATE DATABASE
    SET @`sql` := CONCAT('CREATE DATABASE \`', `db_name`,'\`');
    PREPARE `stmt` FROM @`sql`;
    EXECUTE `stmt`;
    DEALLOCATE PREPARE `stmt`;
END $

DROP PROCEDURE IF EXISTS `create_user_and_grant` $
CREATE PROCEDURE `create_user_and_grant`(
    IN `service_name` VARCHAR(255),
    IN `password` VARCHAR(255),
    IN `db_name` VARCHAR(255)
)
BEGIN
    #CREATE USER
    SET @`user_host` = CONCAT('\"',`service_name`,'\"','@\'%\'');
    SET @`sql` := CONCAT('CREATE USER IF NOT EXISTS ', @`user_host`, ' IDENTIFIED BY \'', `password`,'\'');
    PREPARE `stmt` FROM @`sql`;
    EXECUTE `stmt`;

    #GRANT ALL PRIVILEGES FOR USER
    SET @`sql` := CONCAT('GRANT ALL PRIVILEGES ON \`', `db_name`, '\`.* TO ', @`user_host`);
    PREPARE `stmt` FROM @`sql`;
    EXECUTE `stmt`;
    DEALLOCATE PREPARE `stmt`;
END $

DROP PROCEDURE IF EXISTS `create_database_and_user` $
CREATE PROCEDURE `create_database_and_user`(
    IN `db_name` VARCHAR(255),
    IN `user_name` VARCHAR(255),
    IN `password` VARCHAR(255)
)
BEGIN
    SET @is_exists_database=(SELECT is_exists_database(`db_name`));
    IF NOT @is_exists_database THEN
        CALL create_database(`db_name`);
        CALL create_user_and_grant(`user_name`, `password`, `db_name`);
        FLUSH PRIVILEGES;
    END IF;
END $

DELIMITER ;

# Main
SET @default_password = '03092001';

CALL create_database_and_user('auth-db', 'auth-service', @default_password);
CALL create_database_and_user('discovery-db', 'discovery-service', @default_password);
CALL create_database_and_user('payment-db', 'payment-service', @default_password);
CALL create_database_and_user('user-db', 'user-service', @default_password);
CALL create_database_and_user('cart-db', 'cart-service', @default_password);
CALL create_database_and_user('invoice-db', 'invoice-service', @default_password);
CALL create_database_and_user('promotion-db', 'promotion-service', @default_password);
