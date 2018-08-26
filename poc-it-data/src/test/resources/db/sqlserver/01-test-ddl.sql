-- =====================================================================================================================
-- DDL script
-- =====================================================================================================================

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='dummytable' AND xtype='U') CREATE TABLE poc_jdbc.dummytable (id BIGINT NOT NULL IDENTITY PRIMARY KEY,value VARCHAR(200) NOT NULL)

