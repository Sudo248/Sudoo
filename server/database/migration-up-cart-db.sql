USE `cart-db`;

DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`
(
    `cart_id`      CHAR(32) NOT NULL PRIMARY KEY,
    `user_id`      CHAR(32) NOT NULL,
    `total_price`  FLOAT8 DEFAULT 0.0,
    `total_amount` FLOAT8 DEFAULT 0.0,
    `status`       VARCHAR(255)
) CHARACTER SET = utf8mb4
    COMMENT = 'Store all carts';



DROP TABLE IF EXISTS `cart_product`;
CREATE TABLE `cart_product`
(
    `cart_product_id` CHAR(32) NOT NULL PRIMARY KEY,
    `cart_id`         CHAR(32) NOT NULL,
    `product_id`      CHAR(32) NOT NULL,
    `total_price`     FLOAT8 DEFAULT 0.0,
    `total_amount`    FLOAT8 DEFAULT 0.0,
    `status`          VARCHAR(255),

    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE
) CHARACTER SET = utf8mb4
    COMMENT = 'Store all products of cart';