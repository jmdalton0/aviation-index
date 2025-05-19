USE aviation_index;

DELETE FROM question;
DELETE FROM topic;
DELETE FROM user;

INSERT INTO user VALUES
(null, "jesse", "$2a$10$N6oLJCgM2wSv76F9gss39e7H2rFh2wiHn2qssaOidBotJ.Y76DqRq", "ROLE_ADMIN", null, null, null);

INSERT INTO topic VALUES
(1, null, "Pilot Qualifications"),
(2, null, "Weather"),
(3, null, "National Airspace System"),
(4, null, "Aircraft Systems"),
(5, 2, "Weather Sources"),
(6, 2, "Weather Products"),
(7, 2, "Weather Principles"),
(8, 6, "TAF"),
(9, 6, "METAR"),
(10, 6, "AWOS"),
(11, 6, "ASOS"),
(12, 1, "Required Documents"),
(13, 12, "Pilot Certificate"),
(14, 12, "Medical Certificate"),
(15, 12, "Photo ID");



INSERT INTO question VALUES
(1, 2, 1, "What is weather?", "Weather is weather."),
(2, 2, 2, "Where is weather?", "Troposphere."),
(3, 2, 3, "Why is weather?", "Heating of surface."),
(4, 5, 4, "What are weather sources?", "Source 1, Source 2, Source 3"),
(5, 5, 5, "What are weather products?", "Product 1, Product 2, Product 3"),
(6, 8, 6, "What is TAF", "TAF is TAF"),
(7, 8, 7, "When is TAF", "Every 4 hours"),
(8, 8, 8, "Where is TAF", "Near an aerodrome");