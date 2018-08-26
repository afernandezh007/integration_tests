-- =====================================================================================================================
-- DDL script
-- =====================================================================================================================

DROP TABLE IF EXISTS dummytable;

CREATE TABLE dummytable (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  value VARCHAR(200) NOT NULL
) ENGINE=InnoDB;
