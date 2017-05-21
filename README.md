# True Social Platform
The True Social Platform is an implementation of simple social hub which allows sharing ideas and opinions without overhelming content. 
Technology stack: Java, Spring framework.

## Prerequisites
You need to have the following tools installed:
 - Java SE 1.8 (tested on 1.8.0_121)
 - Apache Maven (tested on 3.3.9)
 - MongoDB database server (tested on 3.2.10)

## Installation and Commissioning
Start your MongoDB server. This may be done by typing (in the project root directory):
```
mkdir db                    # Creating directory for storing data
mongod --dbpath ./db        # Starting MongoDB server
```
To set up whole infrastructure and deploy the application to the local Tomcat server, simply type:
```
mvn package
```
in the project root directory.
