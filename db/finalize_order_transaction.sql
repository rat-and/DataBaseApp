DROP PROCEDURE IF EXISTS finalize_order;
DELIMITER $$
CREATE PROCEDURE finalize_order(IN budget DOUBLE, IN tab_name VARCHAR(255))
BEGIN			
	DECLARE new_tab_name VARCHAR(255);
    DECLARE item_id, store_id, amount INT;
    DECLARE item_name, store_name VARCHAR(255);
    DECLARE cost, total DOUBLE DEFAULT 0.0;
    DECLARE finish INT DEFAULT 0;
    #cursor for my_view which is acrually tab_name
    DECLARE curss CURSOR FOR SELECT * FROM my_view;
    DECLARE CONTINUE HANDLER FOR NOT FOUND 
		SET finish = 1;
    SET new_tab_name = SUBSTR(tab_name, 4);
    
    #drop old my_view
    DROP VIEW IF EXISTS my_view;
        
    #make my_view to apply 'dynamic' cursor
    SET @sql_text = CONCAT('CREATE VIEW my_view AS SELECT * FROM ', tab_name, ';'); 
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;          
             
	START TRANSACTION;
		#creates new table
		SET @sql_text = CONCAT('CREATE TABLE ', new_tab_name, ' (',
					' item_id INT NOT NULL,',
                    ' item_name VARCHAR(255) NOT NULL,',
                    ' store_id INT NOT NULL,',
                    ' store_name VARCHAR(255),',
                    ' amount INT UNSIGNED,',
                    ' cost DOUBLE NOT NULL',
                ' );'); 
                
		PREPARE stmt FROM @sql_text;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt; 
    
		OPEN curss;
		petla: LOOP
			FETCH curss INTO item_id, item_name, store_id, store_name, amount, cost;
			IF finish = 1 THEN
				LEAVE petla;
			END IF;
			SET total = total + cost;            
         
        SET @sql_text = CONCAT('INSERT INTO ', new_tab_name, ' VALUES ( ', item_id, ', ', '"', item_name, '"', ', ', store_id, ', ', '"', store_name, '"', ', ', amount, ', ', cost, ' );');
        PREPARE stmt FROM @sql_text;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        		            
		END LOOP petla;
		CLOSE curss;
        
        IF total >= budget
        THEN 
			ROLLBACK;
		ELSE 
			COMMIT;
        END IF;
        #drop old my_view
		DROP VIEW IF EXISTS my_view;
		#drop old tabe 
		SET @sql_text = CONCAT('DROP TABLE ', tab_name, ';'); 
		PREPARE stmt FROM @sql_text;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
        
	END$$   
 DELIMITER ;
                     
#CALL finalize_order(20000.0, 'tmp_order_20190129_234657');               
	