# 普通用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
  id       INT AUTO_INCREMENT
    PRIMARY KEY,
  name     VARCHAR(255) NULL,
  password VARCHAR(255) NULL,
  status   VARCHAR(255) NULL,
  mobile   VARCHAR(255) NULL
)
  AUTO_INCREMENT = 1,
  ENGINE = InnoDB;