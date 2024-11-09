-- Initial data for SORT table
INSERT INTO SORT (sort, description) VALUES
                                         ('S', 'Sunrise'),
                                         ('D', 'Day'),
                                         ('L', 'Local'),
                                         ('T', 'Twilight'),
                                         ('N', 'Night'),
                                         ('P', 'Preload'),
                                         ('-', 'No Sort');

-- Initial data for SLIC table
INSERT INTO SLIC (slic, description) VALUES
                                         ('9449 ', 'PHXAZ'),
                                         ('0871 ', 'LAKNJ'),
                                         ('3039 ', 'ATLGA');

-- Initial data for COUNTRY table
INSERT INTO COUNTRY (cc, description) VALUES
                                          ('US', 'United States'),
                                          ('CA', 'Canada'),
                                          ('PR', 'Puerto Rico'),
                                          ('MX', 'Mexico');

-- Insert initial data into FLOWNODE table without a description field

-- First entry
INSERT INTO FLOWNODE (countryId, slicId, sortId)
VALUES (
           (SELECT id FROM COUNTRY WHERE cc = 'US'),
           (SELECT id FROM SLIC WHERE slic = '0871 '),
           (SELECT id FROM SORT WHERE sort = 'L')
       );

-- Second entry
INSERT INTO FLOWNODE (countryId, slicId, sortId)
VALUES (
           (SELECT id FROM COUNTRY WHERE cc = 'US'),
           (SELECT id FROM SLIC WHERE slic = '9449 '),
           (SELECT id FROM SORT WHERE sort = 'L')
       );

-- Third entry
INSERT INTO FLOWNODE (countryId, slicId, sortId)
VALUES (
           (SELECT id FROM COUNTRY WHERE cc = 'US'),
           (SELECT id FROM SLIC WHERE slic = '3039 '),
           (SELECT id FROM SORT WHERE sort = 'T')
       );
