CREATE TABLE platos {
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DOUBLE NOT NULL,
    imagen VARCHAR(255) NOT NULL
}

CREATE TABLE clientes {
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE
}

CREATE TABLE facturas {
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fecha_compra DATE NOT NULL,
    total DOUBLE NOT NULL,
    tipo_pago VARCHAR(255) NOT NULL,
    plato_id INT NOT NULL,
    cliente_id INT NOT NULL
}