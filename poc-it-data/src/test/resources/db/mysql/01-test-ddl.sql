-- =====================================================================================================================
-- DDL script
-- =====================================================================================================================

CREATE TABLE IF NOT EXISTS poc_jdbc.dummytable (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  value VARCHAR(200) NOT NULL
) ENGINE=InnoDB;
