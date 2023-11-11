USE `auth-db`;
CREATE TABLE `accounts`
(
    `user_id`               CHAR(32) NOT NULL PRIMARY KEY,
    `email_or_phone_number` VARCHAR(255) NOT NULL UNIQUE,
    `password`              VARCHAR(255) NOT NULL,
    `role`                  ENUM ('CONSUMER', 'STAFF', 'ADMIN'),
    `provider`              ENUM ('AUTH_SERVICE', 'GOOGLE', 'FACEBOOK'),
    `is_validated`          BOOLEAN      NOT NULL,
    `create_at`             DATETIME
) CHARACTER SET = utf8mb4;