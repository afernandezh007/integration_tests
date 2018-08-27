-- =====================================================================================================================
-- DML script
-- =====================================================================================================================

--insert if not exists
INSERT INTO DUMMYTABLE(value) SELECT 'Initial value' FROM dual WHERE NOT EXISTS (SELECT * FROM DUMMYTABLE);