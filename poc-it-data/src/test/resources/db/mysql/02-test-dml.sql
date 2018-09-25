-- =====================================================================================================================
-- DML script
-- =====================================================================================================================

--insert if not exists
INSERT INTO poc_jdbc.dummytable(value)
SELECT 'Initial value'
FROM dual
WHERE NOT EXISTS (SELECT * FROM poc_jdbc.dummytable);
