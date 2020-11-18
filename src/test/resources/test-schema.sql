DROP TABLE IF EXISTS user_details;
DROP TABLE IF EXISTS address;

CREATE TABLE user_details (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(10) NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  gender VARCHAR(10) NULL,
  address_id INT NULL
);

CREATE TABLE address (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  street VARCHAR(50) NOT NULL,
  city VARCHAR(20) NOT NULL,
  state VARCHAR(5) NOT NULL,
  post_code INT NOT NULL
);
