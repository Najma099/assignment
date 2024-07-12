create database assignment;
use assignment;
create table productline (
productLine VARCHAR(50) PRIMARY KEY,
textDescription VARCHAR(4000),
htmlDescription MEDIUMTEXT,
image MEDIUMBLOB);

INSERT INTO productline (productLine, textDescription)
VALUES
('Classic Cars', 'Cars from the 1950s and 1960s'),
('Motorcycles', 'A range of motorcycles'),
('Planes', 'A variety of vintage and modern planes'),
('Ships', 'Ships and boats of various sizes and types'),
('Trains', 'Model trains from different eras'),
('Trucks and Buses', 'A collection of trucks and buses'),
('Vintage Cars', 'Classic vintage cars from different decades');

drop table products;
create table products (
productCode VARCHAR(15) PRIMARY KEY,
productName VARCHAR(70) NOT NULL,
productLine VARCHAR(50),
productScale VARCHAR(10) NOT NULL,
productVendor VARCHAR(50) NOT NULL,
productDescription TEXT NOT NULL,
quantityInStock SMALLINT NOT NULL,
buyPrice DECIMAL(10, 2) NOT NULL,
MSRP DECIMAL(10, 2) NOT NULL,
constraint products_constr foreign key ( productLine)
							references productline(productLine)
				ON update cascade
				On delete cascade
);

INSERT INTO products (
    productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP
)
VALUES
('S10_1678', '1969 Harley Davidson Ultimate Chopper', 'Motorcycles', '1:10', 'Min Lin Diecast', 'This replica features working kickstand, front suspension, gear-shift lever.', 7933, 48.81, 95.70),
('S10_1949', '1952 Alpine Renault 1300', 'Classic Cars', '1:10', 'Classic Metal Creations', 'Turnable front wheels; steering function; detailed interior.', 7305, 98.58, 214.30),
('S10_2016', '1996 Moto Guzzi 1100i', 'Motorcycles', '1:10', 'Highway 66 Mini Classics', 'Features rotating wheels, detailed engine, removable kickstand.', 6625, 68.99, 118.94),
('S12_1099', '1958 Setra Bus', 'Trucks and Buses', '1:18', 'Red Start Diecast', 'Highly detailed bus model with opening doors and hood.', 6924, 77.63, 145.00),
('S18_3232', '1980s Ferrari 308 GTS', 'Vintage Cars', '1:18', 'Second Gear Diecast', 'Diecast metal model with authentic paint and rubber tires.', 1578, 53.90, 103.42),
('S24_1046', '1936 Mercedes Benz 500k Roadster', 'Classic Cars', '1:12', 'Highway 66 Mini Classics', 'This diecast model features a detailed engine and interior.', 2722, 83.20, 149.99);

CREATE TABLE employees (
    employeeNumber INT PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    extension VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    officeCode VARCHAR(10),
    reportsTo INT,
    jobTitle VARCHAR(50) NOT NULL,
    CONSTRAINT fk_office FOREIGN KEY (officeCode)
        REFERENCES offices(officeCode),
    CONSTRAINT fk_reports_to FOREIGN KEY (reportsTo)
        REFERENCES employees(employeeNumber)
        
        ON update cascade
		On delete cascade
);

CREATE TABLE offices (
    officeCode VARCHAR(10) PRIMARY KEY,
    city VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    addressLine1 VARCHAR(50) NOT NULL,
    addressLine2 VARCHAR(50),
    state VARCHAR(50),
    country VARCHAR(50) NOT NULL,
    postalCode VARCHAR(15) NOT NULL,
    territory VARCHAR(10) NOT NULL
);
 
CREATE TABLE orderdetails (
    orderNumber INT,
    productCode VARCHAR(15),
    quantityOrdered INT NOT NULL,
    priceEach DECIMAL(10, 2) NOT NULL,
    orderLineNumber SMALLINT NOT NULL,
    PRIMARY KEY (orderNumber, productCode),
    FOREIGN KEY (orderNumber) REFERENCES orders(orderNumber),
    FOREIGN KEY (productCode) REFERENCES products(productCode)
);

CREATE TABLE customers (
    customerNumber INT PRIMARY KEY,
    customerName VARCHAR(50) NOT NULL,
    contactLastName VARCHAR(50) NOT NULL,
    contactFirstName VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    addressLine1 VARCHAR(50) NOT NULL,
    addressLine2 VARCHAR(50),
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50),
    postalCode VARCHAR(15),
    country VARCHAR(50) NOT NULL,
    salesRepEmployeeNumber INT,
    creditLimit DECIMAL(10, 2),
    FOREIGN KEY (salesRepEmployeeNumber) REFERENCES employees(employeeNumber)
		ON update cascade
		On delete cascade
);

CREATE TABLE orders (
    orderNumber INT PRIMARY KEY,
    orderDate DATE NOT NULL,
    requiredDate DATE NOT NULL,
    shippedDate DATE,
    status VARCHAR(15) NOT NULL,
    comments TEXT,
    customerNumber INT,
    FOREIGN KEY (customerNumber) REFERENCES customers(customerNumber)
);

CREATE TABLE payments (
    customerNumber INT,
    checkNumber VARCHAR(50) PRIMARY KEY,
    paymentDate DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customerNumber) REFERENCES customers(customerNumber)
);


