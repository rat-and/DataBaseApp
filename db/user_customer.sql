CREATE USER 'customer'@'localhost' IDENTIFIED BY '';
#GRANT SELECT ON `ceneo_2.0`.* TO 'customer'@'localhost';   #invoke after all
GRANT SELECT ON `ceneo_2.0`.`items` TO 'customer'@'localhost';
GRANT SELECT ON `ceneo_2.0`.`store_item` TO 'customer'@'localhost';
GRANT SELECT ON `ceneo_2.0`.`stores` TO 'customer'@'localhost';
GRANT CREATE ON `ceneo_2.0` TO 'customer'@'localhost';
GRANT DROP ON `ceneo_2.0`.* TO 'customer'@'localhost';
REVOKE DROP ON `ceneo_2.0`.`items` FROM 'customer'@'localhost';
REVOKE DROP ON `ceneo_2.0`.`stores` FROM 'customer'@'localhost';
REVOKE DROP ON `ceneo_2.0`.`store_item` FROM 'customer'@'localhost';
GRANT EXECUTE ON PROCEDURE `ceneo_2.0`.finalize_order TO 'customer'@'localhost';
GRANT FILE ON *.* TO 'customer'@'localhost';



