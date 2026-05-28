CREATE DATABASE IF NOT EXISTS micro_market;
USE micro_market;

CREATE TABLE IF NOT EXISTS categorias(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS productos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo_barras VARCHAR(100) UNIQUE NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    categoria_id INT,
    FOREIGN KEY(categoria_id) REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS proveedores(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nit VARCHAR(50) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    direccion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS productos_proveedores(
    producto_id INT,
    proveedor_id INT,
    PRIMARY KEY(producto_id, proveedor_id),
    FOREIGN KEY(producto_id) REFERENCES productos(id),
    FOREIGN KEY(proveedor_id) REFERENCES proveedores(id)
);

CREATE TABLE IF NOT EXISTS empleados(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha_ingreso DATE,
    salario DECIMAL(10,2),
    cargo ENUM('Administrador', 'Cajero', 'Auxiliar')
);

CREATE TABLE IF NOT EXISTS ventas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2),
    iva DECIMAL(10,2),
    total DECIMAL(10,2),
    empleado_id INT NOT NULL,
    FOREIGN KEY(empleado_id) REFERENCES empleados(id)
);

CREATE TABLE IF NOT EXISTS detalle_venta(
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    producto_id INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY(venta_id) REFERENCES ventas(id),
    FOREIGN KEY(producto_id) REFERENCES productos(id)
);

-- =============================================
-- DATOS DE PRUEBA
-- =============================================

-- Categorías
INSERT INTO categorias (nombre) VALUES
('Lácteos'),
('Bebidas'),
('Snacks'),
('Carnes');

-- Productos
INSERT INTO productos (nombre, codigo_barras, precio, stock, estado, categoria_id) VALUES
('Leche Entera 1L',    '7702001001001', 3200.00, 50,  TRUE,  1),
('Yogur Natural 200g', '7702001001002', 1800.00, 30,  TRUE,  1),
('Agua Mineral 600ml', '7702001002001', 1500.00, 100, TRUE,  2),
('Jugo de Naranja 1L', '7702001002002', 4500.00, 40,  TRUE,  2),
('Papas Fritas 150g',  '7702001003001', 2800.00, 60,  TRUE,  3),
('Chorizos x500g',     '7702001004001', 8500.00, 20,  TRUE,  4),
('Producto Inactivo',  '7702001099001', 1000.00, 0,   FALSE, 3);

-- Proveedores
INSERT INTO proveedores (nombre, nit, telefono, correo, direccion) VALUES
('Lácteos del Valle S.A.',   '900123456-1', '3001234567', 'ventas@lacteosvalle.com',  'Cra 10 #20-30, Bogotá'),
('Distribuidora Bebidas XYZ','900654321-2', '3109876543', 'contacto@bebidasxyz.com',  'Cll 50 #15-20, Medellín'),
('Snacks Colombia Ltda',     '800111222-3', '3205556677', 'info@snackscol.com',        'Av 30 #45-10, Cali'),
('Carnes del Llano S.A.S',   '700333444-4', '3154443322', 'pedidos@carnesllano.com',  'Cll 80 #23-15, Villavicencio');

-- Relación Productos - Proveedores
INSERT INTO productos_proveedores (producto_id, proveedor_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 4);

-- Empleados
INSERT INTO empleados (cedula, nombre, cargo, fecha_ingreso, salario) VALUES
('1001234567', 'Carlos Pérez',  'Administrador', '2022-01-15', 3500000.00),
('1009876543', 'María López',   'Cajero',        '2023-03-01', 1800000.00),
('1005555555', 'Juan Torres',   'Auxiliar',      '2024-06-10', 1300000.00),
('1007777777', 'Ana Gómez',     'Cajero',        '2023-08-20', 1800000.00),
('1003333333', 'Luis Herrera',  'Auxiliar',      '2024-01-05', 1300000.00);

-- Ventas
INSERT INTO ventas (fecha, subtotal, iva, total, empleado_id) VALUES
('2025-05-01 10:30:00', 9400.00,  1786.00, 11186.00, 2),
('2025-05-02 14:00:00', 4500.00,  855.00,  5355.00,  4),
('2025-05-03 09:15:00', 11300.00, 2147.00, 13447.00, 2);

-- Detalle de Ventas
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 2, 3200.00, 6400.00),
(1, 3, 2, 1500.00, 3000.00),
(2, 4, 1, 4500.00, 4500.00),
(3, 6, 1, 8500.00, 8500.00),
(3, 5, 1, 2800.00, 2800.00);