= Setup = 

1) Install mysql
2) Create a database named "securityaction"
3) Create username "security" with the password "auction"
4) run mvn install - this will execute liquibase that will set up the database for you

5) Download and install Glassfish 4.0
6) Copy mysql-connector-java-5.1.6.jar (maven dependency) to GLASSFISH/domains/domain1/lib/ext
6) Start glassfish and navigate to localhost:4848
7) In the administration panel, go to JDBC > JDBC Connection Pools
8) Add a new pool
	- Pool Name: securityauction
	- Resource Type: java.sql.Driver
	- Driver Classname: com.mysql.jdbc.Driver
	- Enable pinging
	
	Under properties, have the following properties
	- URL: jdbc:mysql://localhost/securityauction
	- user: security
	- password: auction
	
	Save (it should now ping the database and make sure the connection is working)
9) Go to JDBC Resources
10) Add a new resource
	- Name: jdbc/securityauction_datasource
	- Pool Name: securityauction

	Save
	
11) Deploy the project to Glassfish
