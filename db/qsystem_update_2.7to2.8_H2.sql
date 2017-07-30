
-- -----------------------------------------------------
-- Table `clients_authorization`
-- -----------------------------------------------------

ALTER TABLE  `clients_authorization` ALTER COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE  `clients_authorization` ADD column if not exists `comments` VARCHAR(512) NULL;
ALTER TABLE  `clients_authorization` ADD column if not exists `auth_id` VARCHAR(128) NULL;

CREATE UNIQUE INDEX  if not exists `idx_auth_id_UNIQUE` ON  `clients_authorization` (`auth_id` ASC);

-- -----------------------------------------------------
-- Table  `responses`
-- -----------------------------------------------------
ALTER TABLE  `responses` ADD column if not exists `parent_id` BIGINT NULL;
ALTER TABLE  `responses` ADD column if not exists  `input_caption` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '';
ALTER TABLE  `responses` ADD column if not exists  `input_required` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '';
ALTER TABLE  `responses` ADD column if not exists  `deleted` DATE NULL;

ALTER TABLE  `responses`
ADD  CONSTRAINT  if not exists  `fk_responses_responses`
    FOREIGN KEY (`parent_id`)
    REFERENCES  `responses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
    
CREATE INDEX if not exists  `idx_responses_responses` ON  `responses` (`parent_id` ASC);

ALTER TABLE  `responses` ALTER COLUMN `id` BIGINT NOT NULL COMMENT '' ;

INSERT INTO  `responses` (`id`, `parent_id`, `name`, `text`, `input_caption`, `input_required`) VALUES (0, NULL, 'Responses', '<html>Responses', DEFAULT, DEFAULT);
UPDATE responses SET parent_id =0 where id<>0;

-- -----------------------------------------------------
-- Table  `response_event`
-- -----------------------------------------------------
ALTER TABLE  `response_event` ADD COLUMN  if not exists  `comment` VARCHAR(256) NOT NULL DEFAULT '';

-- -----------------------------------------------------
-- Table  `reports`
-- -----------------------------------------------------

UPDATE  `reports` SET `name`='Полный отчет по отзывам клиентов за период' WHERE `id`='11' and`href`='statistic_period_date_responses';

-- -----------------------------------------------------
-- Table  `net`
-- -----------------------------------------------------
UPDATE net SET version = '2.8' where id<>-1;

COMMIT;

