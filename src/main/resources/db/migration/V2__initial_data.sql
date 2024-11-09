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

-- Initial FlowNode data with composite unique key on cc, slic, and sort
INSERT INTO FLOWNODE (CC, SLIC, SORT) VALUES
                                          ('US', '9449 ', 'L'),
                                          ('US', '0871 ', 'L'),
                                          ('US', '3039 ', 'T');
