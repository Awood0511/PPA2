# PPA2

//command to start jenkins in docker
docker run \
  --rm \
  -u root \
  -p 8080:8080 \
  -v jenkins-data:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v "$HOME":/home \
  jenkinsci/blueocean

//dowload Git and clone src code
//download jdk 13
//download maven

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

5. (Optional) Exit out of postgres command line in docker

   a) Get out of the database

   \q

   b) Exit out of the container shell

   exit

//execute the code in Maven
//COnnect to code using http requests

//db info
user = postgres
password = password
db name = testdb
ip = 192.168.99.100