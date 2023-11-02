#!/bin/sh
echo "-------------------------------"
echo
echo "Clean and build process started"
echo
echo "-------------------------------"

cd ~/Project/ && git stash && git fetch origin && git pull origin model && mvn package -DskipTests && sudo cp -rf ./target/SQLproject-1.war /opt/tomcat/updated/webapps/ROOT.war

echo "---------------------------------"
echo
echo "Clean and build process completed"
echo
echo "---------------------------------"