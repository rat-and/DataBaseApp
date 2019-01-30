CREATE USER 'employee'@'localhost' IDENTIFIED BY 'ilovemyjob';

GRANT SELECT ON `ceneo_2.0`.`items` TO 'employee'@'localhost';
GRANT SELECT ON `ceneo_2.0`.`store_item` TO 'employee'@'localhost';
GRANT SELECT ON `ceneo_2.0`.`stores` TO 'employee'@'localhost';
GRANT CREATE ON * TO 'employee'@'localhost';
GRANT DELETE ON `ceneo_2.0`.* TO 'employee'@'localhost';
GRANT INSERT ON `ceneo_2.0`.* TO 'employee'@'localhost';
GRANT UPDATE ON `ceneo_2.0`.* TO 'employee'@'localhost';

GRANT DROP ON `ceneo_2.0`.* TO 'employee'@'localhost';
REVOKE DROP ON `ceneo_2.0`.`items` FROM 'employee'@'localhost';
REVOKE DROP ON `ceneo_2.0`.`stores` FROM 'employee'@'localhost';
REVOKE DROP ON `ceneo_2.0`.`store_item` FROM 'employee'@'localhost';

GRANT INSERT ON `ceneo_2.0`.* TO 'employee'@'localhost';
REVOKE INSERT ON `ceneo_2.0`.`items` FROM 'employee'@'localhost';
REVOKE INSERT ON `ceneo_2.0`.`stores` FROM 'employee'@'localhost';
REVOKE INSERT ON `ceneo_2.0`.`store_item` FROM 'employee'@'localhost';

GRANT EXECUTE ON PROCEDURE `ceneo_2.0`.finalize_order TO 'employee'@'localhost';
GRANT FILE ON *.* TO 'employee'@'localhost';



