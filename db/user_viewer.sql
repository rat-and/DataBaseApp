CREATE USER 'viewer'@'localhost' IDENTIFIED BY '';
GRANT SELECT ON `ceneo_2.0`.`items` TO 'viewer'@'localhost';
GRANT SELECT ON `ceneo_2.0`.`store_item` TO 'viewer'@'localhost';
GRANT SELECT ON `ceneo_2.0`.`stores` TO 'viewer'@'localhost';


