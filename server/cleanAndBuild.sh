#!/bin/sh
cd ~/Project/ && git pull origin model && mvn package -DskipTests && sudo cp -rf ./target/SQLproject-1.war /opt/tomcat/updated/webapps/ROOT.war