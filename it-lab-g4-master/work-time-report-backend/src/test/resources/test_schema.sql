DROP DATABASE IF EXISTS wtrlite_test##

CREATE DATABASE IF NOT EXISTS wtrlite_test##

use wtrlite_test##

CREATE TABLE `Report` (
    `reportId` int  NOT NULL AUTO_INCREMENT,
    `userId` int  NOT NULL ,
    `projectId` int  NOT NULL ,
    `featureId` int  NOT NULL ,
    `taskId` int NULL ,
    `factorId` int  NOT NULL ,    
    `date` date  NOT NULL ,
    `hours` int  NOT NULL ,
    `workUnits` int  NOT NULL ,
    `comment` varchar(999)  NOT NULL ,
    `status` varchar(999)  NOT NULL ,
    PRIMARY KEY (
        `reportId`
    )
) engine=InnoDB##

CREATE TABLE `Projects` (
    `projectId` int  NOT NULL,
    `userId` int  NOT NULL
) engine=InnoDB##

CREATE TABLE `Task` (
    `taskId` int  NOT NULL AUTO_INCREMENT,
    `taskName` varchar(999)  NOT NULL ,
    `featureId` int  NOT NULL ,
    PRIMARY KEY (
        `taskId`
    )
) engine=InnoDB##

CREATE TABLE `Factor` (
    `factorId` int  NOT NULL AUTO_INCREMENT,
    `factorName` varchar(999)  NOT NULL ,
    PRIMARY KEY (
        `factorId`
    )
) engine=InnoDB##

CREATE TABLE `Project`(
	`projectId` int NOT NULL AUTO_INCREMENT,
    `projectName` varchar(999) NOT NULL,
    PRIMARY KEY (
    `projectId`
    )
) engine=InnoDB##

CREATE TABLE `Feature` (
    `featureId` int  NOT NULL AUTO_INCREMENT,
    `featureName` varchar(999)  NOT NULL ,
    `projectId` int  NOT NULL ,
    PRIMARY KEY (
        `featureId`
    )
) engine=InnoDB##

CREATE TABLE `User` (
    `userId` int  NOT NULL AUTO_INCREMENT,
    `userName` varchar(700)  NOT NULL ,
    `userPassword` varchar(999)  NOT NULL ,
    PRIMARY KEY (
        `userId`
    ),
    UNIQUE INDEX `userName_UQ` (`userName` ASC) VISIBLE
) engine=InnoDB##

CREATE TABLE `Book` (
    `bookId` int  NOT NULL AUTO_INCREMENT,
    `bookName` varchar(999)  NOT NULL ,
    PRIMARY KEY (
        `bookId`
    )
) engine=InnoDB##



CREATE TABLE IF NOT EXISTS Book( bookId int  NOT NULL AUTO_INCREMENT, bookName varchar(999) NOT NULL, PRIMARY KEY (bookId ))##

DROP PROCEDURE IF EXISTS `find_and_delete_book`##
DROP PROCEDURE IF EXISTS `get_list_of_book`##
DROP PROCEDURE IF EXISTS `get_list_of_report`##

CREATE DEFINER=`root`@`localhost` PROCEDURE `find_and_delete_book` (IN inName VARCHAR(999))
BEGIN
   DELETE FROM book WHERE bookName like inName;
END 
##

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_list_of_book` (IN inName VARCHAR(999))
BEGIN
   SELECT * FROM book WHERE bookName like inName;
END
##

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_list_of_report` (IN inId INT)
BEGIN
   SELECT * FROM report WHERE userId like inId;
END
##

ALTER TABLE `Projects` ADD CONSTRAINT `fk_Projects_projectId` FOREIGN KEY(`projectId`)
REFERENCES `Project` (`projectId`)##

ALTER TABLE `Projects` ADD CONSTRAINT `fk_Projects_userId` FOREIGN KEY(`userId`)
REFERENCES `User` (`userId`)##

ALTER TABLE `Report` ADD CONSTRAINT `fk_Report_userId` FOREIGN KEY(`userId`)
REFERENCES `User` (`userId`)##

ALTER TABLE `Report` ADD CONSTRAINT `fk_Report_taskId` FOREIGN KEY(`taskId`)
REFERENCES `Task` (`taskId`)##

ALTER TABLE `Report` ADD CONSTRAINT `fk_Report_projectId` FOREIGN KEY(`projectId`)
REFERENCES `Project` (`projectId`)##

ALTER TABLE `Report` ADD CONSTRAINT `fk_Report_factor` FOREIGN KEY(`factorId`)
REFERENCES `Factor` (`factorId`)##

ALTER TABLE `Feature` ADD CONSTRAINT `fk_Feature_projectId` FOREIGN KEY(`projectId`)
REFERENCES `Project` (`projectId`)##

ALTER TABLE `Task` ADD CONSTRAINT `fk_Task_featureId` FOREIGN KEY(`featureId`)
REFERENCES `Feature` (`featureId`)##
