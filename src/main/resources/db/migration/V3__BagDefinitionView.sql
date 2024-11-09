-- Create vw_BagDefinitionDetails view for querying BagDefinition details
CREATE VIEW vw_BagDefinitionDetails AS
SELECT
    bd.id AS bagDefinitionId,
    bd.start_date AS startDate,
    bd.end_date AS endDate,
    originCountry.code AS originCc,
    slic_origin.code AS originSlic,
    sort_origin.code AS originSort,
    destinationCountry.code AS destinationCc,
    slic_destination.code AS destinationSlic,
    sort_destination.code AS destinationSort
FROM BAGDEFINITION bd
         JOIN FLOWNODE origin ON bd.origin_id = origin.id
         JOIN FLOWNODE destination ON bd.destination_id = destination.id
    -- Join with COUNTRY table for origin and destination
         JOIN COUNTRY originCountry ON origin.countryId = originCountry.id
         JOIN COUNTRY destinationCountry ON destination.countryId = destinationCountry.id
    -- Join with SLIC table for origin and destination
         JOIN SLIC slic_origin ON origin.slicId = slic_origin.id
         JOIN SLIC slic_destination ON destination.slicId = slic_destination.id
    -- Join with SORT table for origin and destination
         JOIN SORT sort_origin ON origin.sortId = sort_origin.id
         JOIN SORT sort_destination ON destination.sortId = sort_destination.id;
