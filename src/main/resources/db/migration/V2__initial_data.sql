-- V2__initial_data.sql

-- Initial Sort data
INSERT INTO SORT (SORT, DESCRIPTION) VALUES
                                         ('S', 'Sunrise'),
                                         ('D', 'Day'),
                                         ('L', 'Local'),
                                         ('T', 'Twilight'),
                                         ('N', 'Night'),
                                         ('P', 'Preload'),
                                         ('-', 'No Sort');

-- Initial Slic data
INSERT INTO SLIC (SLIC, DESCRIPTION) VALUES
                                         ('9449 ', 'PHXAZ'),
                                         ('0871 ', 'LAKNJ'),
                                         ('3039 ', 'ATLGA');

-- Initial Country data
INSERT INTO COUNTRY (CC, DESCRIPTION) VALUES
                                          ('US', 'United States'),
                                          ('CA', 'Canada'),
                                          ('PR', 'Puerto Rico'),
                                          ('MX', 'Mexico');

-- Initial FlowNode data with composite unique key on countryId, slicId, and sortId
INSERT INTO FLOWNODE (countryId, slicId, sortId)
SELECT
    (SELECT id FROM COUNTRY WHERE code = 'US'),
    (SELECT id FROM SLIC WHERE code = '9449'),
    (SELECT id FROM SORT WHERE code = 'L')
UNION ALL
SELECT
    (SELECT id FROM COUNTRY WHERE code = 'US'),
    (SELECT id FROM SLIC WHERE code = '0871'),
    (SELECT id FROM SORT WHERE code = 'L')
UNION ALL
SELECT
    (SELECT id FROM COUNTRY WHERE code = 'US'),
    (SELECT id FROM SLIC WHERE code = '3039'),
    (SELECT id FROM SORT WHERE code = 'T');
