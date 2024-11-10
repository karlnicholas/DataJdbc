-- Initial data for SORT table
INSERT INTO SORT (sort, description) VALUES
                                         ('S', 'Sunrise'),
                                         ('D', 'Day'),
                                         ('L', 'Local'),
                                         ('T', 'Twilight'),
                                         ('N', 'Night'),
                                         ('P', 'Preload');

-- Initial data for SLIC table
INSERT INTO SLIC (slic, description) VALUES
                                         ('9449', 'PHXAZ'),
                                         ('0871', 'LAKNJ'),
                                         ('3039', 'ATLGA');

-- Initial data for COUNTRY table
INSERT INTO COUNTRY (cc, description) VALUES
    ('US', 'United States');

-- Insert FlowNodes with all combinations of Country, SLIC, and Sort codes
INSERT INTO FlowNode (countryId, slicId, sortId)
SELECT country.id, slic.id, sort.id
FROM
    (SELECT id FROM COUNTRY WHERE cc = 'US') AS country
        CROSS JOIN
    (SELECT id FROM SLIC WHERE slic IN ('9449', '0871', '3039')) AS slic
        CROSS JOIN
    (SELECT id FROM SORT WHERE sort IN ('S', 'D', 'L', 'T', 'N', 'P')) AS sort;
