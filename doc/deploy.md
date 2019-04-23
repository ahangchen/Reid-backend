## Mini Deployment
### Install maven
```shell
sudo apt-get install maven
```

### Install MongoDB

```shell
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 9DA31620334BD75D9DCB49F368818C72E52529D4
echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/4.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.0.list
sudo apt-get update
sudo apt-get install -y mongodb-org
```

mongo:

```
use reid # create db
db.createUser({user:"reid",pwd:"p2p543",roles:[{role:"dbAdmin",db:"reid"}]}) # create dateabase
```


## Deploy and Run
```shell
mongod # sudo service mongod start
mvn package
java -jar target/reid-0.0.1-SNAPSHOT.jar
```