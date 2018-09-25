# Guide to init the database

The steps 2 to 4 are executed automatically by Java thanks to Spring DatabasePopulator

1. Create the Docker Container `oracleDocker_poc` in the port `1521` with the system credentials:

        username: system
        password: oracle

        docker run --name oracleDocker_poc -d -p 1521:1521 wnameless/oracle-xe-11g

2. Execute `00-test-init.sql` with root
3. Execute `01-test-ddl.sql` with user
4. Execute `02.test-dml.sql` with user