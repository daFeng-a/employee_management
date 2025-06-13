CREATE TABLE employees (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(50) NOT NULL,
                           age INT,
                           department VARCHAR(50),
                           position VARCHAR(50),
                           salary DECIMAL(10, 2)
);

CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      password varchar(20) NOT NULL
);
INSERT INTO `user`(`id`, `name`, `password`) VALUES (1, 'admin', '123456');
