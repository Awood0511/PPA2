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
docker run -d -p 5432:5432 -v postgres-data:/var/lib/postgresql/data --name ppa2db POSTGRES_PASSWORD=password POSTGRES_DB=testdb postgres
2. This command enters the container and creates the tables

//command to run our program in docker

//db info
user = postgres
password = password
db name = testdb
ip = 192.168.99.100