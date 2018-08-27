-- =====================================================================================================================
-- DDL script
-- =====================================================================================================================
DECLARE
	tableExists integer;
BEGIN
    SELECT COUNT(*) INTO tableExists FROM user_tables WHERE table_name='DUMMYTABLE';
  	if (tableExists = 0) THEN
        EXECUTE IMMEDIATE 'CREATE TABLE dummytable(id NUMBER(19) NOT NULL,value VARCHAR(200) NOT NULL,CONSTRAINT dummytable_pk PRIMARY KEY (id))';
		EXECUTE IMMEDIATE 'CREATE SEQUENCE dummy_seq START WITH 1';
		EXECUTE IMMEDIATE 'CREATE OR REPLACE TRIGGER dummy_trigger BEFORE INSERT ON dummytable FOR EACH ROW BEGIN SELECT dummy_seq.NEXTVAL INTO :new.id FROM dual; END;';
	END IF;
END;
/