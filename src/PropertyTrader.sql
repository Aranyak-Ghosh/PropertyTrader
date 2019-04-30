DROP TABLE USERS;
DROP TABLE PROPERTY;
DROP TABLE TRANSACTIONS;
DROP TABLE PROPERTYTYPES;
DROP TABLE REVIEWS;

CREATE TABLE USERS{
    USERNAME VARCHAR(20) PRIMARY KEY,
    HASHPASS VARCHAR(50) NOT NULL,
    FULL_NAME VARCHAR(50) NOT NULL,
    EID VARCHAR(20),
    CONTACTNO VARCHAR(12) NOT NULL,
    P_ADDRESS VARCHAR(100),
    EMAIL VARCHAR(20) NOT NULL,
    BANKCARD_INFO VARCHAR(20)
};

CREATE TABLE PROPERTY{
    TYPEID INT NOT NULL,
    AREA VARCHAR(50) NOT NULL,
    YEAR INT NOT NULL,
    BATHROOMS INT NOT NULL,
    BEDROOMS INT NOT NULL,
    PROPERTY_ID INT PRIMARY KEY,
    PRICE INT NOT NULL,
    PICTURES IMAGE NOT NULL,
    FOREIGN KEY (TYPEID) REFERENCES PROPERTYTYPES(TYPEID) 
}

CREATE TABLE PROPERTYTYPES{
    TYPEID INT PRIMARY KEY,
    TYPENAME VARCHAR(50) 
}

CREATE TABLE TRANSACTIONS{
	PROPERTY_ID INT NOT NULL,
	TRANSACTION_ID INT NOT NULL PRIMARY KEY,
	BUYER_NAME VARCHAR(50) NOT NULL,
	SELLER_NAME VARCHAR(50) NOT NULL,
	FOREIGN KEY (PROPERTY_ID) REFERENCES PORPERTY(PROPERTY_ID),
	FOREIGN KEY (BUYER_NAME) REFERENCES USERS(USERNAME),
	FOREIGN KEY (SELLER_NAME) REFERENCES USERS(USERNAME)
}

CREATE TABLE REVIEWS{
	REVIEW VARCHAR(200) NOT NULL,
	RATING INT NOT NULL,
	AUTHOR VARCHAR(20) NOT NULL,
	REVIEW_ID INT PRIMARY KEY,
	PROPERTY_ID INT NOT NULL,
    FOREIGN KEY (AUTHOR) REFERENCES USER(USERNAME),
    FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY(PROPERTY_ID)
}