-- V1__Init_Schema.sql

-- Create COUNTRY table
CREATE TABLE COUNTRY (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         code CHAR(2) NOT NULL UNIQUE
);

-- Create SLIC table
CREATE TABLE SLIC (
                      id BIGINT IDENTITY(1,1) PRIMARY KEY,
                      code CHAR(5) NOT NULL UNIQUE
);

-- Create SORT table
CREATE TABLE SORT (
                      id BIGINT IDENTITY(1,1) PRIMARY KEY,
                      code CHAR(1) NOT NULL UNIQUE
);

-- Create FLOWNODE table
CREATE TABLE FLOWNODE (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          countryId BIGINT NOT NULL,
                          slicId BIGINT NOT NULL,
                          sortId BIGINT NOT NULL,
                          CONSTRAINT FK_FLOWNODE_COUNTRY FOREIGN KEY (countryId) REFERENCES COUNTRY(id),
                          CONSTRAINT FK_FLOWNODE_SLIC FOREIGN KEY (slicId) REFERENCES SLIC(id),
                          CONSTRAINT FK_FLOWNODE_SORT FOREIGN KEY (sortId) REFERENCES SORT(id),
                          CONSTRAINT UQ_FlowNode_country_slic_sort UNIQUE (countryId, slicId, sortId)
);

-- Create BAGDEFINITION table
CREATE TABLE BAGDEFINITION (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,
                               origin_id BIGINT NOT NULL, -- Foreign key reference to FlowNode
                               destination_id BIGINT NOT NULL, -- Foreign key reference to FlowNode
                               start_date DATE NOT NULL,
                               end_date DATE NOT NULL,
                               CONSTRAINT FK_BAGDEFINITION_ORIGIN FOREIGN KEY (origin_id) REFERENCES FLOWNODE(id),
                               CONSTRAINT FK_BAGDEFINITION_DESTINATION FOREIGN KEY (destination_id) REFERENCES FLOWNODE(id)
);
