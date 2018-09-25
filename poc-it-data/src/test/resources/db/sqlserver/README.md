# Guide to init the database

The steps 2 to 4 are executed automatically by Java thanks to Spring DatabasePopulator

1. Create the Docker Container `sqlserverDocker_poc` in the port `1433` with the root credentials:

        username: sa
        password: sa@password_2018

        docker run -d --name sqlserverDocker_poc -e ACCEPT_EULA=Y -e MSSQL_PID=Developer -e SA_PASSWORD=sa@password_2018 -p 1433:1433 microsoft/mssql-server-linux

2. Execute `00-test-init.sql` with sa
3. Execute `01-test-ddl.sql` with user
4. Execute `02.test-dml.sql` with user