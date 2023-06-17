# Skip-List
## Skip List Using Java

### Notes when using the program:

#### We created a database to store and retrieve the data. 
#### You should create a database called Algorithm_Project_One on your workbench using the following commands (MySql).

- create database `Algorithm_Project_One`;
- use Algorithm_Project_One;
- create table rectangles(rectangleName varchar(50) not null,x int not null,y int not null,w int not null,h int not null);

#### Second, you should connect your database with the project by following these steps:
1- Right click on the project name and choose properties.

2- From the left window choose Java Build Path.

3- Choose ClassPath from the right window then click Add External JARS.

4- Then choose the JDBC that you will find in the src file with the name (mysql-connector-j-8.0.33.jar).

5- Finally click open.

#### Finally, Go to the DatabaseJDBC class in line 14 and change the following:
1- The localhost of the database with your database localhost.

2- Change the username to your workbench username.

3- Finally, Change the password to your password.
