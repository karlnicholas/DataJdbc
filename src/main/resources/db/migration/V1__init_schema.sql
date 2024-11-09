-- V1__Initial_Setup.sql

-- Table for FlowNode (must be created first)
CREATE TABLE FLOWNODE (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          cc CHAR(2), -- 2-character country code
                          slic CHAR(5), -- Slic code, 5 characters
                          sort CHAR(1), -- Sort code, 1 character
                          CONSTRAINT UQ_FlowNode_cc_slic_sort UNIQUE (cc, slic, sort) -- Unique constraint on cc, slic, and sort fields
);

-- Table for BagDefinition with foreign keys to FlowNode
CREATE TABLE BAGDEFINITION (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,
                               origin_id BIGINT, -- Foreign key reference to FlowNode
                               destination_id BIGINT, -- Foreign key reference to FlowNode
                               description VARCHAR(255),
                               start_date DATE,
                               end_date DATE,
                               FOREIGN KEY (origin_id) REFERENCES FLOWNODE(id),
                               FOREIGN KEY (destination_id) REFERENCES FLOWNODE(id)
);

-- Table for Country
CREATE TABLE COUNTRY (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         cc CHAR(2) UNIQUE, -- 2-character country code with a unique constraint
                         description VARCHAR(255)
);

-- Table for Slic
CREATE TABLE SLIC (
                      id BIGINT IDENTITY(1,1) PRIMARY KEY,
                      slic CHAR(5) UNIQUE, -- Slic code with unique constraint
                      description VARCHAR(255)
);

-- Table for Sort
CREATE TABLE SORT (
                      id BIGINT IDENTITY(1,1) PRIMARY KEY,
                      sort CHAR(1) UNIQUE, -- Sort code with unique constraint
                      description VARCHAR(255)
);
