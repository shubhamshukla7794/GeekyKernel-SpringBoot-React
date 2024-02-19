-- SQL query to create GeekyKernel database 
CREATE database IF NOT EXISTS `geekykernel`;
USE `geekykernel`;

SELECT * FROM `users`;

SELECT * FROM `posts`;

SELECT * FROM `posts_liked`;

SELECT * FROM `users_saved_post`;

SELECT * from users where id='27e6e1e4-9ba6-4fcf-8b84-a74d5ae3bfe8';

DELETE FROM `users` WHERE id='bdcd1405-d552-4edd-95bd-ccfd651e49b7';

DROP DATABASE `geekykernel`;