-- =====================================================================================================================
-- Init script
-- =====================================================================================================================

CREATE DATABASE IF NOT EXISTS poc_jdbc;
CREATE USER IF NOT EXISTS 'poc_jdbc_user'@'%' IDENTIFIED BY 'poc_jdbc_user_password';
GRANT ALL PRIVILEGES ON poc_jdbc.* TO 'poc_jdbc_user'@'%';
FLUSH PRIVILEGES;