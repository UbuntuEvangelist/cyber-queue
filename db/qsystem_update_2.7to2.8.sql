
-- -----------------------------------------------------
-- Table `clients_authorization`
-- -----------------------------------------------------

ALTER TABLE `qsystem`.`clients_authorization` 
CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
ADD COLUMN `comments` VARCHAR(512) NULL,
ADD COLUMN `auth_id` VARCHAR(128) NULL;

CREATE UNIQUE INDEX `idx_auth_id_UNIQUE` ON `qsystem`.`clients_authorization` (`auth_id` ASC);

-- -----------------------------------------------------
-- Table `qsystem`.`responses`
-- -----------------------------------------------------
ALTER TABLE `qsystem`.`responses` 
ADD COLUMN `parent_id` BIGINT NULL,
ADD `input_caption` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '',
ADD `input_required` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '',
ADD  `deleted` DATE NULL;

ALTER TABLE `qsystem`.`responses`
ADD  CONSTRAINT `fk_responses_responses`
    FOREIGN KEY (`parent_id`)
    REFERENCES `qsystem`.`responses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
    
CREATE INDEX `idx_responses_responses` ON `qsystem`.`responses` (`parent_id` ASC);

ALTER TABLE `qsystem`.`responses` 
CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL COMMENT '' ;

INSERT INTO `qsystem`.`responses` (`id`, `parent_id`, `name`, `text`, `input_caption`, `input_required`) VALUES (0, NULL, 'Responses', '<html>Responses', DEFAULT, DEFAULT);
UPDATE responses SET parent_id =0 where id<>0;

-- -----------------------------------------------------
-- Table `qsystem`.`response_event`
-- -----------------------------------------------------
ALTER TABLE `qsystem`.`response_event` 
ADD COLUMN  `comment` VARCHAR(256) NOT NULL DEFAULT '';

-- -----------------------------------------------------
-- Table `qsystem`.`reports`
-- -----------------------------------------------------

UPDATE `qsystem`.`reports` SET `name`='Полный отчет по отзывам клиентов за период' WHERE `id`='11' and`href`='statistic_period_date_responses';

-- -----------------------------------------------------
-- Table `qsystem`.`net`
-- -----------------------------------------------------
UPDATE net SET version = '2.8' where id<>-1;

COMMIT;

