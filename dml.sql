USE aviation_index;

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
(4, 5, 4, "What are weather sources?", "NWS, AWC, PIREPS"),
(5, 6, 5, "What are weather products?", "TAF, METAR, ATIS, AWOS, ASOS"),
(6, 8, 6, "What is TAF?", "TAF is TAF"),
(7, 8, 7, "When is TAF?", "Every 4 hours"),
(8, 8, 8, "Where is TAF?", "Near an aerodrome"),
(9, 12, 9, "What are the Required Documents?", "Pilot Cert, Medical Cert, ID"),
(10, 14, 10, "Where do you get a Medical Certificate?", "AME"),
(11, 14, 11, "What are the classes of Medical Certificates?", "First, Second, Third"),
(12, 14, 12, "What priveleges for a First Class?", "All"),
(13, 14, 13, "What priveleges for a Second Class?", "FO ATP"),
(14, 14, 14, "What priveleges for a Third Class?", "Private"),
(15, 14, 15, "When does a First Class Medical expire if you are over 40?", "6 months"),
(16, 14, 16, "When does a First Class Medical expire if you are under 40?", "12 months"),
(17, 14, 17, "When does a Second Class Medical expire if you are over 40?", "6 months"),
(18, 14, 18, "When does a Second Class Medical expire if you are under 40?", "12 months"),
(19, 14, 19, "When does a Third Class Medical expire if you are over 40?", "60 months"),
(20, 14, 20, "When does a Third Class Medical expire if you are under 40?", "60 months");
