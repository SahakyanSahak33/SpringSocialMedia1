CREATE TABLE `my_db`.`users_roles` (
    `user_id` BIGINT NULL,
    `role_id` BIGINT NULL,
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    INDEX `id_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user_id`
       FOREIGN KEY (`user_id`)
           REFERENCES `my_db`.`social_media_user` (`id`)
           ON DELETE NO ACTION
           ON UPDATE NO ACTION,
    CONSTRAINT `role_id`
       FOREIGN KEY (`role_id`)
           REFERENCES `my_db`.`role` (`id`)
           ON DELETE NO ACTION
           ON UPDATE NO ACTION
);