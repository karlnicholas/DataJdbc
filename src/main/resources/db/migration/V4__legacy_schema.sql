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
