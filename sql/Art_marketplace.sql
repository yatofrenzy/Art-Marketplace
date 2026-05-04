-- Art Marketplace Database Schema
-- Run this file first

CREATE DATABASE IF NOT EXISTS art_marketplace;
USE art_marketplace;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS artworks;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('admin','customer') DEFAULT 'customer',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  contact_number VARCHAR(20) DEFAULT NULL,
  profile_image VARCHAR(255) DEFAULT NULL,
  account_status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending',
  phone VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE categories (
  category_id INT(11) NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (category_id),
  UNIQUE KEY category_name (category_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE artworks (
  artwork_id INT(11) NOT NULL AUTO_INCREMENT,
  category_id INT(11) NOT NULL,
  title VARCHAR(150) NOT NULL,
  description TEXT DEFAULT NULL,
  price DECIMAL(10,2) DEFAULT NULL CHECK (price >= 0),
  image_path VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (artwork_id),
  KEY category_id (category_id),
  CONSTRAINT fk_artworks_category FOREIGN KEY (category_id) REFERENCES categories (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cart_items (
  cart_item_id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  artwork_id INT(11) NOT NULL,
  quantity INT(11) NOT NULL CHECK (quantity > 0),
  PRIMARY KEY (cart_item_id),
  UNIQUE KEY unique_user_artwork (user_id, artwork_id),
  KEY artwork_id (artwork_id),
  CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
  CONSTRAINT fk_cart_artwork FOREIGN KEY (artwork_id) REFERENCES artworks (artwork_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE orders (
  order_id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  total_amount DECIMAL(10,2) DEFAULT 0.00,
  order_status ENUM('Pending','Completed','Cancelled') DEFAULT 'Pending',
  payment_method VARCHAR(50) DEFAULT NULL,
  payment_status ENUM('Pending','Paid','Failed') DEFAULT 'Pending',
  order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (order_id),
  KEY user_id (user_id),
  CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE order_items (
  order_item_id INT(11) NOT NULL AUTO_INCREMENT,
  order_id INT(11) NOT NULL,
  artwork_id INT(11) NOT NULL,
  quantity INT(11) DEFAULT NULL CHECK (quantity > 0),
  price DECIMAL(10,2) DEFAULT NULL,
  PRIMARY KEY (order_item_id),
  KEY order_id (order_id),
  KEY artwork_id (artwork_id),
  CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
  CONSTRAINT fk_order_items_artwork FOREIGN KEY (artwork_id) REFERENCES artworks (artwork_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
