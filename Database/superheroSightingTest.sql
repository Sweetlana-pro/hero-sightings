DROP DATABASE IF EXISTS superheroSightingTest;
CREATE DATABASE superheroSightingTest;
USE superheroSightingTest;

CREATE TABLE superpower (
id INT PRIMARY KEY AUTO_INCREMENT,
powerName VARCHAR(50) NOT NULL,
powerDescription VARCHAR(255)
);

CREATE TABLE superhero(
id INT PRIMARY KEY AUTO_INCREMENT,
superheroName VARCHAR(50) NOT NULL,
superheroDescription VARCHAR(100) NOT NULL,
superpowerId INT NOT NULL,
FOREIGN KEY (superpowerId) REFERENCES superpower(id)
);


CREATE TABLE organizations(
id INT PRIMARY KEY AUTO_INCREMENT,
organizationName VARCHAR(50) NOT NULL,
organizationDescription VARCHAR(100),
address VARCHAR(100),
contacts VARCHAR(100),
phone VARCHAR(20)
);

CREATE TABLE superheroOrganizations(
    superheroId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY(superheroId, organizationId),
    FOREIGN KEY (organizationId) REFERENCES organizations(id),
    FOREIGN KEY (superheroId) REFERENCES superhero(id)
);

CREATE TABLE location(
id INT PRIMARY KEY AUTO_INCREMENT,
locationName VARCHAR(100) NOT NULL,
locationDescription VARCHAR(100),
address VARCHAR(100),
coordinates VARCHAR(50)
);

CREATE TABLE sightings(
id INT PRIMARY KEY AUTO_INCREMENT,
superheroId INT NOT NULL,
locationId INT NOT NULL,
sightDate DATE NOT NULL,
FOREIGN KEY (superheroId) REFERENCES superhero(id), 
FOREIGN KEY (locationId) REFERENCES location(id),
sightDescription VARCHAR(100)
);

SELECT * FROM superhero;
SELECT * FROM location;
SELECT * FROM sightings;
SELECT * FROM superheroOrganizations;
SELECT * FROM organizations;
SELECT * FROM superPower;