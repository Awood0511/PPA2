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

//command to run sql database in docker
1. The command below starts up a new postgress db container named ppa2db

docker run -d -p 5432:5432 -v postgres-data:/var/lib/postgresql/data --name ppa2db -e POSTGRES_PASSWORD=password -e POSTGRES_DB=ppa2db postgres

2. Enter the docker container for the database

docker exec -it ppa2db sh

3. Enter the database ppa2db that was created by command 1

psql -U postgres ppa2db

4. Create the tables that will store the inputs and outputs for SplitTheTip and BodyMass
create table splitTheTip(
	id serial PRIMARY KEY,
	dinnerAmount NUMERIC(999,2),
	guests INTEGER,
	costPerGuest NUMERIC(999,2),
	remainder NUMERIC(999,2)
);
create table bodymass(
	id serial PRIMARY KEY,
	feet INTEGER,
	inches INTEGER,
	weight NUMERIC(999,2),
	bmi NUMERIC(999,2),
	bodytype varchar(100)
);

//db info
user = postgres
password = password
db name = testdb
ip = 192.168.99.100