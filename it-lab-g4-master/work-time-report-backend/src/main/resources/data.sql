USE wtrlite##
DELETE FROM `Book`##
DELETE FROM `report`##
DELETE FROM `factor`##
DELETE FROM `task`##
DELETE FROM `feature`##
DELETE FROM `projects`##
DELETE FROM `project`##
DELETE FROM `user`##

INSERT INTO `Book` (`bookId`, `bookName`) VALUES ('1', 'C++')##
INSERT INTO `Book` (`bookId`, `bookName`) VALUES ('2', 'C#')##

INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (1, 'User1', 'pass1')##
INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (2, 'User2', 'pass2')##
INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (3, 'User3', 'pass3');##
INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (4, 'User4', 'pass4');##
INSERT INTO `user` (`userId`, `userName`, `userPassword`) VALUES (5, 'User5', 'pass5');##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (1, 'Project1')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (2, 'Project2')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (3, 'Project3')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (4, 'Project4')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (5, 'Project5')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (6, 'Project6')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (7, 'Project7')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (8, 'Project8')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (9, 'Project9')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (10, 'Project10')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (11, 'Project11')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (12, 'Project12')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (13, 'Project13')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (14, 'Project14')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (15, 'Project15')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (16, 'Project16')##
INSERT INTO `project`(`projectId`, `projectName`) VALUES (17, 'Project17')##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (1, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (2, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (3, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (4, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (5, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (10, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (11, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (12, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (13, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (14, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (15, 1)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (6, 2)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (7, 2)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (8, 2)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (9, 2)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (16, 2)##
INSERT INTO `projects`(`projectId`, `userId`) VALUES (17, 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (1, 'Feature1-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (2, 'Feature2-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (3, 'Feature3-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (4, 'Feature4-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (5, 'Feature5-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (6, 'Feature6-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (7, 'Feature7-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (8, 'Feature8-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (9, 'Feature9-1', 1)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (10, 'Feature10-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (11, 'Feature11-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (12, 'Feature12-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (13, 'Feature13-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (14, 'Feature14-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (15, 'Feature15-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (16, 'Feature16-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (17, 'Feature17-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (18, 'Feature18-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (19, 'Feature19-2', 2)##
INSERT INTO `feature`(`featureId`, `featureName`, `projectId`) VALUES (20, 'Feature20-2', 2)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (1, 'task1-1', 1)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (2, 'task2-1', 1)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (3, 'task3-1', 1)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (4, 'task4-2', 2)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (5, 'task5-2', 2)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (6, 'task6-2', 2)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (7, 'task7-3', 3)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (8, 'task8-3', 3)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (9, 'task9-3', 3)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (10, 'task10-4', 4)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (11, 'task11-4', 4)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (12, 'task12-4', 4)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (13, 'task13-5', 5)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (14, 'task14-5', 5)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (15, 'task15-5', 5)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (16, 'task16-6', 6)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (17, 'task17-6', 6)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (18, 'task18-6', 6)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (19, 'task19-7', 7)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (20, 'task20-7', 7)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (21, 'task21-7', 7)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (22, 'task22-8', 8)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (23, 'task23-8', 8)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (24, 'task24-8', 8)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (25, 'task25-9', 9)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (26, 'task26-9', 9)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (27, 'task27-9', 9)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (28, 'task28-10', 10)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (29, 'task29-10', 10)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (30, 'task30-10', 10)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (31, 'task31-11', 11)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (32, 'task32-11', 11)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (33, 'task33-11', 11)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (34, 'task34-12', 12)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (35, 'task35-12', 12)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (36, 'task36-12', 12)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (37, 'task37-13', 13)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (38, 'task38-13', 13)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (39, 'task39-13', 13)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (40, 'task40-14', 14)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (41, 'task41-14', 14)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (42, 'task42-14', 14)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (43, 'task43-15', 15)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (44, 'task44-15', 15)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (45, 'task45-15', 15)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (46, 'task46-16', 16)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (47, 'task47-16', 16)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (48, 'task48-16', 16)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (49, 'task49-17', 17)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (50, 'task50-17', 17)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (51, 'task51-17', 17)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (52, 'task52-18', 18)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (53, 'task53-18', 18)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (54, 'task54-18', 18)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (55, 'task55-19', 19)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (56, 'task56-19', 19)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (57, 'task57-19', 19)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (58, 'task58-20', 20)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (59, 'task59-20', 20)##
INSERT INTO `task`(`taskId`, `taskName`, `featureId`) VALUES (60, 'task60-20', 20)##
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (1, 'Factor1')##
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (2, 'Factor2')##
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (3, 'Factor3')##
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (4, 'Factor4')##
INSERT INTO `factor`(`factorId`, `factorName`) VALUES (5, 'Factor5')##
INSERT INTO `report`(`reportId`, `userId`, `projectId`, `featureId`, `taskId`, `factorId`, `date`, `hours`, `workUnits`, `comment`, `status`) VALUES (1, 1, 2, 2, null, 1, '2012-12-31', 8, 8, 'com1', 'mystatus1')##
INSERT INTO `report`(`reportId`, `userId`, `projectId`, `featureId`, `taskId`, `factorId`, `date`, `hours`, `workUnits`, `comment`, `status`) VALUES (2, 2, 2, 2, 1, 2, '2012-12-30', 8, 8, 'com2', 'mystatus2')##
INSERT INTO `report`(`reportId`, `userId`, `projectId`, `featureId`, `taskId`, `factorId`, `date`, `hours`, `workUnits`, `comment`, `status`) VALUES (3, 2, 1, 2, 1, 2, '2012-12-30', 8, 8, 'com2', 'mystatus2');