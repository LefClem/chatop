--CREATE DATABASE IF NOT EXISTS chatOp;
--
--USE chatOp;
--
--CREATE TABLE IF NOT EXISTS `USERS` (
--  `id` integer PRIMARY KEY AUTO_INCREMENT,
--  `email` varchar(255),
--  `name` varchar(255),
--  `password` varchar(255),
--  `created_at` timestamp,
--  `updated_at` timestamp
--);
--
--CREATE TABLE IF NOT EXISTS `RENTALS`(
--  `id` integer PRIMARY KEY AUTO_INCREMENT,
--  `name` varchar(255),
--  `surface` numeric,
--  `price` numeric,
--  `picture` varchar(255),
--  `description` varchar(2000),
--  `owner_id` integer NOT NULL,
--  `created_at` timestamp,
--  `updated_at` timestamp
--);
--
--CREATE TABLE IF NOT EXISTS `MESSAGES` (
--  `id` integer PRIMARY KEY AUTO_INCREMENT,
--  `rental_id` integer,
--  `user_id` integer,
--  `message` varchar(2000),
--  `created_at` timestamp,
--  `updated_at` timestamp
--);
--
--CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);
--
--ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);
--
--ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
--
--ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);

CREATE DATABASE IF NOT EXISTS chatOp;

USE chatOp;

CREATE TABLE IF NOT EXISTS `USERS` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE (`email`)
);

CREATE TABLE IF NOT EXISTS `RENTALS` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255),
  `surface` DECIMAL(10, 2),
  `price` DECIMAL(10, 2),
  `picture` VARCHAR(255),
  `description` VARCHAR(2000),
  `owner_id` INTEGER NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`)
);

CREATE TABLE IF NOT EXISTS `MESSAGES` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `rental_id` INTEGER,
  `user_id` INTEGER,
  `message` VARCHAR(2000),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`),
  FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`)
);
