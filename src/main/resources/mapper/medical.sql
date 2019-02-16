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

# 医生
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`
(
  id       INT AUTO_INCREMENT
    PRIMARY KEY,
  name     VARCHAR(255) NULL,
  password VARCHAR(255) NULL,
  status   VARCHAR(255) NULL,
  mobile   VARCHAR(255) NULL,
  photo    VARCHAR(255) NULL
)
  AUTO_INCREMENT = 1,
  ENGINE = InnoDB;

# 管理员
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
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