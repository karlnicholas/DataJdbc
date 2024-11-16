-- COUNTRY table creation
CREATE TABLE COUNTRY (
                         id BIGINT IDENTITY PRIMARY KEY,
                         cc CHAR(2) NOT NULL,           -- 2-character country code
                         description NVARCHAR(255)      -- Country description
);

-- SLIC table creation
CREATE TABLE SLIC (
                      id BIGINT IDENTITY PRIMARY KEY,
                      slic CHAR(5) NOT NULL,         -- 5-character Slic code
                      description NVARCHAR(255)      -- Slic description
);

-- SORT table creation
CREATE TABLE SORT (
                      id BIGINT IDENTITY PRIMARY KEY,
                      sort CHAR(1) NOT NULL,         -- 1-character Sort code
                      description NVARCHAR(255)      -- Sort description
);

-- FLOWNODE table creation
CREATE TABLE FLOWNODE (
                          id BIGINT IDENTITY PRIMARY KEY,                -- Primary key for FlowNode
                          countryId BIGINT NOT NULL,                     -- Reference to Country primary key
                          slicId BIGINT NOT NULL,                        -- Reference to Slic primary key
                          sortId BIGINT NOT NULL,                        -- Reference to Sort primary key
                          FOREIGN KEY (countryId) REFERENCES COUNTRY(id),
                          FOREIGN KEY (slicId) REFERENCES SLIC(id),
                          FOREIGN KEY (sortId) REFERENCES SORT(id)
);

-- BAGDEFINITION table creation
CREATE TABLE BAGDEFINITION (
                               id BIGINT IDENTITY PRIMARY KEY,                -- Primary key for BagDefinition
                               originId BIGINT NOT NULL,                      -- Reference to FlowNode ID for origin
                               destinationId BIGINT NOT NULL,                 -- Reference to FlowNode ID for destination
                               startDate DATE NOT NULL,
                               endDate DATE NOT NULL,
                               FOREIGN KEY (originId) REFERENCES FLOWNODE(id),
                               FOREIGN KEY (destinationId) REFERENCES FLOWNODE(id)
);

-- Add unique constraint on originId, destinationId, startDate, and endDate
ALTER TABLE BAGDEFINITION
    ADD CONSTRAINT UQ_BAGDEFINITION_OriginDestDates
        UNIQUE (originId, destinationId, startDate, endDate);

-- BAGDEFINITION table creation
CREATE TABLE BAGDEFINITIONLEGACY (
                                     id BIGINT IDENTITY PRIMARY KEY,                -- Primary key for BagDefinition
                                     originCc CHAR(2) NOT NULL,
                                     originSlic CHAR(5) NOT NULL,
                                     originSort CHAR(1) NOT NULL,
                                     destinationCc CHAR(2) NOT NULL,
                                     destinationSlic CHAR(5) NOT NULL,
                                     destinationSort CHAR(1) NOT NULL,
                                     startDate DATE NOT NULL,
                                     endDate DATE NOT NULL
);

-- Add unique constraint on all fields except id
ALTER TABLE BAGDEFINITIONLEGACY
    ADD CONSTRAINT UQ_BAGDEFINITIONLEGACY_UniqueFields
        UNIQUE (originCc, originSlic, originSort, destinationCc, destinationSlic, destinationSort, startDate, endDate);