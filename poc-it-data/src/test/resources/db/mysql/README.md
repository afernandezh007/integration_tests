# Guide to init the database

The steps 2 to 4 are executed automatically by Java thanks to Spring DatabasePopulator

1. Create the Docker Container `mysqlDocker_poc` in the port `3306` with the root credentials:

        username: root
        password: root_password

        docker run --name mysqlDocker_poc -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root_password -d mysql:5.7

2. Execute `00-test-init.sql` with root
3. Execute `01-test-ddl.sql` with user
4. Execute `02.test-dml.sql` with user