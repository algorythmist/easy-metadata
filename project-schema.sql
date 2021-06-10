
# -----------------------------------------------------------------------
# ACCOUNTING_CATEGORY
# -----------------------------------------------------------------------
DROP table if exists ACCOUNTING_CATEGORY;

CREATE TABLE ACCOUNTING_CATEGORY
(
    ACCOUNTING_CATEGORY_ID INTEGER NOT NULL AUTO_INCREMENT,
    NAME VARCHAR (50) NOT NULL,
    DESCRIPTION VARCHAR (100),
    PRIMARY KEY(ACCOUNTING_CATEGORY_ID),
    UNIQUE (NAME)
);

# -----------------------------------------------------------------------
# SUPPLIER
# -----------------------------------------------------------------------
DROP table if exists SUPPLIER;

CREATE TABLE SUPPLIER
(
    SUPPLIER_ID INTEGER NOT NULL AUTO_INCREMENT,
    DESCRIPTION VARCHAR (50) NOT NULL,
    LAST_NAME VARCHAR (50),
    FIRST_NAME VARCHAR (50),
    ADDRESS VARCHAR (100),
    PHONE VARCHAR (15),
    FAX VARCHAR (15),
    EMAIL VARCHAR (25),
    PRIMARY KEY(SUPPLIER_ID),
    UNIQUE (DESCRIPTION)
);

# -----------------------------------------------------------------------
# ACCOUNTING_ENTRY
# -----------------------------------------------------------------------
DROP table if exists ACCOUNTING_ENTRY;

CREATE TABLE ACCOUNTING_ENTRY
(
    ACCOUNTING_ENTRY_ID INTEGER NOT NULL AUTO_INCREMENT,
    CATEGORY_ID INTEGER NOT NULL,
    SUPPLIER_ID INTEGER NOT NULL,
    DESCRIPTION VARCHAR (50),
    DATE DATETIME,
    DEBIT DOUBLE default 0.0,
    CREDIT DOUBLE default 0.0,
    CURRENCY_CODE VARCHAR (5) default 'EUR',
    PRIMARY KEY(ACCOUNTING_ENTRY_ID),
    FOREIGN KEY (CATEGORY_ID) REFERENCES ACCOUNTING_CATEGORY (ACCOUNTING_CATEGORY_ID),
    FOREIGN KEY (SUPPLIER_ID) REFERENCES SUPPLIER (SUPPLIER_ID)
);
  
