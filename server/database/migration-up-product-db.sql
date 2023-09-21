USE
    `product-db`;

DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE `suppliers`
(
    `supplier_id`  VARCHAR(255) NOT NULL PRIMARY KEY,
    `user_id`      VARCHAR(255) NOT NULL,
    `name`         TEXT         NOT NULL,
    `avatar`       VARCHAR(255),
    `brand`        VARCHAR(255),
    `contact_url`  VARCHAR(255),
    `longitude`    FLOAT8   DEFAULT 0.0,
    `latitude`     FLOAT8   DEFAULT 0.0,
    `locationName` VARCHAR(255),
    `rate`         FLOAT4   DEFAULT 0.0,
    `create_at`    DATETIME DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET = utf8mb4
    COMMENT = 'Store all supplier';

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`
(
    `product_id`          VARCHAR(255) NOT NULL PRIMARY KEY,
    `supplier_id`         VARCHAR(255) NOT NULL,
    `sku`                 VARCHAR(255) NOT NULL UNIQUE,
    `name`                TEXT         NOT NULL,
    `price`               FLOAT4,
    `listed_price`        FLOAT4,
    `amount`              INT4         NOT NULL,
    `sold_amount`         INT4         NOT NULL DEFAULT 0,
    `rate`                FLOAT4                DEFAULT 0.0,
    `total_rate_amount`   INT4,
    `discount`            INT4                  DEFAULT 0,
    `sellable`            BOOLEAN               DEFAULT TRUE,
    `start_date_discount` DATETIME              DEFAULT CURRENT_TIMESTAMP,
    `end_date_discount`   DATETIME              DEFAULT CURRENT_TIMESTAMP,

    INDEX id_index (product_id),
    INDEX sku_index (sku),

    FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id) ON DELETE CASCADE

) CHARACTER SET = utf8mb4
    COMMENT = 'Store all product';

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`
(
    `category_id` VARCHAR(255) NOT NULL PRIMARY KEY,
    `name`        TEXT         NOT NULL,
    `image`       VARCHAR(255) NOT NULL
) CHARACTER SET = utf8mb4
    COMMENT = 'Store all categories';

DROP TABLE IF EXISTS `categories_products`;
CREATE TABLE `categories_products`
(
    `product_id`  VARCHAR(255) NOT NULL,
    `category_id` VARCHAR(255) NOT NULL,

    PRIMARY KEY (product_id, category_id),

    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE
) CHARACTER SET = utf8mb4
    COMMENT = 'Store all categories and products relationship';

DROP TABLE IF EXISTS `users_products`;
CREATE TABLE `users_products`
(
    `user_product_id` VARCHAR(255) NOT NULL PRIMARY KEY,
    `product_id`      VARCHAR(255) NOT NULL,
    `userId`          VARCHAR(255) NOT NULL,
    `rate`            FLOAT4   DEFAULT 0.0,
    `is_like`         BOOLEAN  DEFAULT FALSE,
    `comment`         TEXT,
    `create_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE

) CHARACTER SET = utf8mb4
    COMMENT = 'Store all users and products relationship';

DROP TABLE IF EXISTS `images`;
CREATE TABLE `images`
(
    `image_id` VARCHAR(255) NOT NULL PRIMARY KEY,
    `ownerId`  VARCHAR(255) NOT NULL,
    `url`      VARCHAR(255) NOT NULL
) CHARACTER SET = utf8mb4
    COMMENT = 'Store all images';