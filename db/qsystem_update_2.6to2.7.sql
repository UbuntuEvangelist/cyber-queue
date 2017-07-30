
-- -----------------------------------------------------
-- Table `qsystem`.`services`
-- -----------------------------------------------------

ALTER TABLE `services` ADD `link_service_id` BIGINT;

-- -----------------------------------------------------
-- Table `qsystem`.`spec_chedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `qsystem`.`spec_schedule` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
  `date_from` DATE NOT NULL COMMENT 'Это спец расписание для этого календаря действует с этой даты',
  `date_to` DATE NOT NULL COMMENT 'Это спец расписание для этого календаря действует до этой даты',
  `calendar_id` BIGINT NOT NULL COMMENT '',
  `schedule_id` BIGINT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  CONSTRAINT `fk_spec_schedule_calendar`
    FOREIGN KEY (`calendar_id`)
    REFERENCES `qsystem`.`calendar` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_spec_schedule_schedule`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `qsystem`.`schedule` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Специальные расписания для периодов для конкретных календарей. Перекрывают стардартное расписание.';

CREATE INDEX `idx_spec_chedule_calendar` ON `qsystem`.`spec_schedule` (`calendar_id` ASC)  COMMENT '';

CREATE INDEX `idx_spec_chedule_schedule` ON `qsystem`.`spec_schedule` (`schedule_id` ASC)  COMMENT '';

-- -----------------------------------------------------
-- Table `qsystem`.`net`
-- -----------------------------------------------------
ALTER TABLE `net` ADD `ext_priority` INT NOT NULL DEFAULT 0 COMMENT 'Количество дополнительных приоритетов';
UPDATE net SET version = '2.7' where id<>-1;

COMMIT;

