
-- Creating databases
CREATE DATABASE conveniosDB;
CREATE DATABASE notificacionesBD;
CREATE DATABASE pagoDispatcherBD;
CREATE DATABASE pagosBD;
CREATE DATABASE usuariosDB;

show databases ;

use conveniosDB;

CREATE TABLE IF NOT EXISTS convenios (
    partner_id INT AUTO_INCREMENT PRIMARY KEY,
    partner_name VARCHAR(255) NOT NULL,
    status TINYINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW()
)  ENGINE=INNODB;


use notificacionesBD;
CREATE TABLE IF NOT EXISTS notificaciones (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    partner_name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    status TINYINT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    invoice_ref INT NOT NULL,
    message VARCHAR(255) NOT NULL,
    paid_invoice DECIMAL(15,2),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    created_at TIMESTAMP NOT NULL
)  ENGINE=INNODB;



use pagoDispatcherBD;
CREATE TABLE IF NOT EXISTS pagoDispatcher (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    partner_id VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    invoice_ref INT NOT NULL,
    status TINYINT NOT NULL,
    invoice DECIMAL(15,2),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    created_at TIMESTAMP NOT NULL,
    error_message VARCHAR(255) NOT NULL
)  ENGINE=INNODB;


use usuariosDB;
CREATE TABLE IF NOT EXISTS usuarios (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    status TINYINT NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    created_at TIMESTAMP NOT NULL,
    password CHAR(64) NOT NULL
)  ENGINE=INNODB;


-- Show tables
use conveniosDB;
show tables;

use notificacionesBD;
show tables;

use pagoDispatcherBD;
show tables;
use usuariosDB;
show tables;
