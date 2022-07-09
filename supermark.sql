/*DROP DATABASE IF EXISTS Supermark;*/
CREATE DATABASE Supermark CHARACTER SET utf8mb3;
USE Supermark;

CREATE TABLE Domicilio(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(30),
    numero INT, 
    depNumero INT,
    piso INT
);
CREATE TABLE Usuario(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30),
    apellido VARCHAR(30),
    email VARCHAR(30),
    dni INT(8) UNSIGNED NOT NULL,
    contrasenia VARCHAR(16),
    id_domicilio INT NOT NULL,
    CONSTRAINT domicilio_fk
    FOREIGN KEY Usuario(id_domicilio)
    REFERENCES Domicilio(id)
);
CREATE TABLE Producto(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    marca VARCHAR(20),
    fecha_venc DATE,
    precio FLOAT NOT NULL,
    stock INT,
    descripcion VARCHAR(20),
	Descuento FLOAT 
);
CREATE TABLE Carrito (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_usuario INT NOT NULL,
    id_producto INT NOT NULL,
    Cantidad INT NOT NULL,
    CONSTRAINT usuario_Carrito_fk
    FOREIGN KEY Carrito(id_usuario)
    REFERENCES Usuario(id),
    CONSTRAINT pr_Carrito_fk
    FOREIGN KEY Carrito(id_producto)
    REFERENCES Producto(id)
);
CREATE TABLE TarjetaCredito(
	numero BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    banco VARCHAR(20),
    titular VARCHAR(60),
    fecha_caducidad DATE,
    id_usuario INT NOT NULL,
    CONSTRAINT usuario_tc_fk
    FOREIGN KEY TarjetaCredito(id_usuario)
    REFERENCES Usuario(id)
);
CREATE TABLE Comprobante(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(1),
    fecha TIMESTAMP,
    total float,
    id_usuario INT NOT NULL,
    id_tc BIGINT UNSIGNED NOT NULL,
    CONSTRAINT usuario_comprobante_fk
    FOREIGN KEY Comprobante(id_usuario)
    REFERENCES Usuario(id),
    CONSTRAINT tc_comprobante_fk
    FOREIGN KEY Comprobante(id_tc)
    REFERENCES TarjetaCredito(numero)
);
CREATE TABLE Detalle(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_comprobante INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT UNSIGNED NOT NULL,
    CONSTRAINT comprobante_fk
    FOREIGN KEY Detalle(id_comprobante)
    REFERENCES Comprobante(id),
    CONSTRAINT producto_fk
    FOREIGN KEY Producto(id_producto)
    REFERENCES Producto(id)
);