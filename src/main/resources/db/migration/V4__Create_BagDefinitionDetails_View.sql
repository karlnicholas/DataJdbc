-- V4__Create_BagDefinitionDetails_View.sql

CREATE VIEW vw_BagDefinitionDetails AS
SELECT
    b.id AS bagDefinitionId,
    b.start_date AS startDate,
    b.end_date AS endDate,
    o.cc AS originCc,
    o.slic AS originSlic,
    o.sort AS originSort,
    d.cc AS destinationCc,
    d.slic AS destinationSlic,
    d.sort AS destinationSort
FROM BAGDEFINITION b
         JOIN FLOWNODE o ON b.origin_id = o.id
         JOIN FLOWNODE d ON b.destination_id = d.id;
