DROP DATABASE IF EXISTS Wholesale;
CREATE DATABASE IF NOT EXISTS Wholesale;
SHOW DATABASES ;
USE Wholesale;
#-------------------


DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    custID VARCHAR(6) ,
    custTitle VARCHAR (30),
    custName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    custNIC VARCHAR (15) NOT NULL PRIMARY KEY,
    custAddress VARCHAR (30),
    city VARCHAR (20),
    province VARCHAR (20),
    postalCode VARCHAR (9)
    );
SHOW TABLES ;
DESCRIBE Customer;
#---------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    orderID VARCHAR(6),
    orderDate DATE,
    orderTime VARCHAR (15),
    cNIC VARCHAR (15),
    discount DECIMAL (20,2),
    cost DECIMAL (20,2),
    CONSTRAINT PRIMARY KEY (orderID),
    CONSTRAINT FOREIGN KEY (cNIC) REFERENCES Customer(custNIC) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order`;
#-----------------------
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR(6) PRIMARY KEY,
    description VARCHAR (50),
    packSize VARCHAR (20),
    unitPrice DECIMAL(20,2) DEFAULT 0.00,
    qtyOnHand INT(5) DEFAULT 0,
    discount DECIMAL (20,2) DEFAULT 0.00
    );
SHOW TABLES ;
DESCRIBE Item;
#------------------------
DROP TABLE IF EXISTS `Order Detail`;
CREATE TABLE IF NOT EXISTS `Order Detail`(
    oID VARCHAR (6),
    iCode VARCHAR (6),
    orderQty INT (11),
    discount DECIMAL (20,2),
    price DECIMAL (20,2),
    CONSTRAINT PRIMARY KEY(iCode, oID),
    CONSTRAINT FOREIGN KEY(iCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY(oID) REFERENCES `Order`(orderID) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order Detail`;
#------------------------
DROP TABLE IF EXISTS `UserDetail`;
CREATE TABLE IF NOT EXISTS `UserDetail`(
    firstName VARCHAR (15),
    lastName VARCHAR (15),
    userType VARCHAR (15),
    userName VARCHAR (10),
    password VARCHAR (15)
);
SHOW TABLES;
DESCRIBE `UserDetail`;

#------------------------
DROP TABLE IF EXISTS `SavedOrder`;
CREATE TABLE IF NOT EXISTS `SavedOrder`(
    oId VARCHAR (6),
    custNIC VARCHAR (15),
    itemCode VARCHAR (6),
    itemDescription VARCHAR (50),
    packSize VARCHAR (20),
    quantity INT (11),
    unitPrice DECIMAL (20,2),
    discount DECIMAL (20,2),
    total DECIMAL (20,2)
);
SHOW TABLES;
DESCRIBE `SavedOrder`;
#--------------------------
DROP TABLE IF EXISTS OrderId;
CREATE TABLE IF NOT EXISTS OrderId(
    oId VARCHAR (6),
    nic VARCHAR (15)
);
SHOW TABLES;
DESCRIBE OrderId;
#--------------------------