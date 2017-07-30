-- -----------------------------------------------------
-- Update DB 2.9 to 3
-- -----------------------------------------------------

USE `qsystem` ;

-- -----------------------------------------------------
-- Table `qsystem`.`users`
-- -----------------------------------------------------

ALTER TABLE `qsystem`.`users` ADD `parallel_access` TINYINT(1)  NOT NULL DEFAULT false COMMENT 'Разрешение вести параллельный прием';

-- -----------------------------------------------------
-- Table `qsystem`.`properties`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qsystem`.`properties` ;

CREATE TABLE IF NOT EXISTS `qsystem`.`properties` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `psection` VARCHAR(128) NULL COMMENT 'Раздел',
  `pkey` VARCHAR(128) NOT NULL COMMENT 'Ключ',
  `pvalue` VARCHAR(10240) NULL COMMENT 'Значение',
  `pcomment` VARCHAR(256) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `section_key_idx` ON `qsystem`.`properties` (`psection` ASC, `pkey` ASC);

-- -----------------------------------------------------
-- Table  `net`
-- -----------------------------------------------------
UPDATE `qsystem`.`net` SET version = '3' where id<>-1;

COMMIT;

