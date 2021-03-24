USE wtrlite_test;
DELETE FROM `Book`;
DELETE FROM `report`;
DELETE FROM `factor`;
DELETE FROM `task`;
DELETE FROM `feature`;
DELETE FROM `projects`;
DELETE FROM `project`;
DELETE FROM `user`;

INSERT INTO `Book` (`bookId`, `bookName`) VALUES ('1', 'C++');
INSERT INTO `Book` (`bookId`, `bookName`) VALUES ('2', 'C#');

INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (1, 'User1', 'pass1');
INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (2, 'User2', 'pass2');
INSERT INTO `project`(`projectId`, `projectName`) VALUES (1, 'Project1');
INSERT INTO `project`(`projectId`, `projectName`) VALUES (2, 'Project2');
INSERT INTO `projects`(`projectId`, `userId`) VALUES (1, 1);
INSERT INTO `projects`(`projectId`, `userId`) VALUES (2, 2);
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (1, 'Feature1', 1);
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (2, 'Feature2', 2);
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (1, 'task1', 1);
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (2, 'task2', 2);
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (1, 'Factor1');
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (2, 'Factor2');
INSERT INTO `report`(`reportId`, `userId`, `projectId`, `featureId`, `taskId`, `factorId`, `date`, `hours`, `workUnits`, `comment`, `status`) VALUES (1, 1, 2, 2, null, 1, '2012-12-31', 8, 8, 'com1', 'mystatus1');

