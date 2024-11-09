-- V3 Flyway script to create the BagDefinitionView

CREATE VIEW BagDefinitionView AS
SELECT
    bd.id AS bagDefinitionId,
    bd.startDate,
    bd.endDate,
    originCountry.cc AS originCc,
    originSlic.slic AS originSlic,
    originSort.sort AS originSort,
    destCountry.cc AS destinationCc,
    destSlic.slic AS destinationSlic,
    destSort.sort AS destinationSort
FROM
    BAGDEFINITION bd
        JOIN
    FLOWNODE originNode ON bd.originId = originNode.id
        JOIN
    COUNTRY originCountry ON originNode.countryId = originCountry.id
        JOIN
    SLIC originSlic ON originNode.slicId = originSlic.id
        JOIN
    SORT originSort ON originNode.sortId = originSort.id
        JOIN
    FLOWNODE destNode ON bd.destinationId = destNode.id
        JOIN
    COUNTRY destCountry ON destNode.countryId = destCountry.id
        JOIN
    SLIC destSlic ON destNode.slicId = destSlic.id
        JOIN
    SORT destSort ON destNode.sortId = destSort.id;
