-- -----------------------------------------------------
-- Table `qsystem`.`users`
-- -----------------------------------------------------

ALTER TABLE `users` ADD `parallel_access` TINYINT(1)  NOT NULL DEFAULT false COMMENT 'Разрешение вести параллельный прием';

-- -----------------------------------------------------
-- Table `qsystem`.`properties`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `properties` ;

CREATE TABLE IF NOT EXISTS `properties` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `psection` VARCHAR(128) NULL COMMENT 'Раздел',
  `pkey` VARCHAR(128) NOT NULL COMMENT 'Ключ',
  `pvalue` VARCHAR(10240) NULL COMMENT 'Значение',
  `pcomment` VARCHAR(256) NULL,
  PRIMARY KEY (`id`));

CREATE UNIQUE INDEX `section_key_idx` ON `properties` (`psection` ASC, `pkey` ASC);

-- -----------------------------------------------------
-- Table  `net`
-- -----------------------------------------------------
UPDATE net SET version = '3' where id<>-1;

COMMIT;

