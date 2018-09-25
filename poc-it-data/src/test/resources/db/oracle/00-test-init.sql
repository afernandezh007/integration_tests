-- =====================================================================================================================
-- Init script
-- =====================================================================================================================
DECLARE
	userExists integer;
BEGIN
    SELECT COUNT(*) INTO userExists FROM dba_users WHERE username = 'POC_JDBC_USER';
  	IF (userExists = 0) THEN
        EXECUTE IMMEDIATE 'CREATE USER poc_jdbc_user IDENTIFIED BY poc_jdbc_user_password';
		EXECUTE IMMEDIATE 'GRANT CONNECT TO poc_jdbc_user';
		EXECUTE IMMEDIATE 'GRANT ALL PRIVILEGES TO poc_jdbc_user';
		EXECUTE IMMEDIATE 'GRANT UNLIMITED TABLESPACE TO poc_jdbc_user';
	END IF;
END;
/