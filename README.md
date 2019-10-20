# PPA2 - Adam Wood - Carlos Henriquez

/* Instructions*/
//download git and clone src code
1. Download git from https://git-scm.com/download/win
2. Once the download is finished, open it up and install git using the wizard.
3. After git is installed, clone the repository to your local machine from the repo:
   https://github.com/Awood0511/PPA2.git

//download jdk 13 and add it to your system path
1. Go to the following link: https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html
2. Select the windows exe download option and wait for it to download
3. Run the installer and it will create a folder at C:\Program Files\Java\jdk-13.x.x "Xs being the version number"
4. Open control panel and navigate as such: System and Security -> System -> Advanced system settings
5. It should have opened a window called System Properties. Select Environement Variables
6. Under System Variables select New
7. In the window enter JAVA_HOME into the variable name field and enter the absolute path to your jdk in the
   variable value (for instance- C:\Program Files\Java\jdk-13.0.1 is what it is on my system) and hit ok
8. Hit ok to exit out of editing system variables and to save your changes

//download maven and add it to your system path
1. Go to the following link: https://maven.apache.org/download.cgi
2. Download the binary .zip and extract the archive into a location of your choice (you will need the absolute path to this later)
3. Open control panel and navigate as such: System and Security -> System -> Advanced system settings
4. It should have opened a window called System Properties. Select Environement Variables
5. Under System Variables select New
6. In the window enter M2_HOME into the variable name field and enter the absolute path to where you extracted maven in the
   variable value (for instance- C:\Program Files\apache-maven-3.6.2 is what it is on my system) and hit ok
7. Under system variables find a variable called Path, select it and hit edit
8. Select new and past in the same path as you did for M2_HOME but add \bin to the end, then hit ok
   (for example mine was C:\Program Files\apache-maven-3.6.2\bin)
9. Hit ok to exit out of editing system variables and to save your changes

//Download docker
1. Go to this link: https://www.docker.com/products/docker-desktop and select download desktop for mac and windows
2. It may prompt you to create and account or log in, if it does log in or create an acount and then log in
3. select download docker desktop and run the installer after it finishes
4. Open a docker terminal after it finishes installing and run the following commands to set up the database

//commands to run postgres sql database in docker
1. The command below starts up a new postgress db container named ppa2db

docker run -d -p 5432:5432 -v postgres-data:/var/lib/postgresql/data --name ppa2db -e POSTGRES_PASSWORD=password -e POSTGRES_DB=ppa2db postgres

2. Enter the docker container for the database

docker exec -it ppa2db sh

3. Enter the database ppa2db

psql -U postgres ppa2db

4. Create the tables that will store the inputs and outputs for SplitTheTip and BodyMass
create table splitTheTip(
	createdAt TIMESTAMPTZ NOT NULL PRIMARY KEY DEFAULT NOW(),
	dinnerAmount NUMERIC(999,2),
	guests INTEGER,
	costPerGuest NUMERIC(999,2),
	remainder NUMERIC(999,2)
);
create table bodymass(
	createdAt TIMESTAMPTZ NOT NULL PRIMARY KEY DEFAULT NOW(),
	feet INTEGER,
	inches INTEGER,
	weight NUMERIC(999,2),
	bmi NUMERIC(999,2),
	bodytype varchar(100)
);

5. (Optional) Exit out of postgres bash

   a) Get out of the database

   \q

   b) Exit out of the container bash

   exit

//execute the code using maven
1. open a terminal window in the folder that you cloned the repository into
2. Use this command to build the project

   mvn clean install

3. Use this command to run the code

   mvn exec:java

//Connect to code using http requests
You can use postman or a browser to make get and post requests to localhost:5000 when the program is running
The following explains the possible routes and their parameters

GET to http://localhost:5000/splitthetip
>returns a json of all the split the tip function calls inputs and outputs

POST to http://localhost:5000/splitthetip
>accesses the split the tip function with the following variables passed in
Headers:
	Variable	Data Type
	dinnerAmount	double
	guests		int

GET to http://localhost:5000/bmi
>returns a json of all the BMI function calls inputs and outputs

POST to http://localhost:5000/bmi
>accesses the BMI function with the following variables passed in
Headers:
	Variable	Data Type
	feet		int
	inches		int
	weight		double
