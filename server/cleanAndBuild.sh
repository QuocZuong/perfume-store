#!/bin/sh

echo "-------------------------------"
echo
echo "Clean and build process started"
echo
echo "-------------------------------"

cd /home/quan99122/Project/ && git stash && git fetch origin && git pull && mvn package -DskipTests && sudo rm -rf /opt/tomcat/updated/webapps/ROOT && sudo rm -f /opt/tomcat/updated/webapps/ROOT.war && sudo cp -rf ./target/SQLproject-1.war /opt/tomcat/updated/webapps/ROOT.war

echo "---------------------------------"
echo
echo "Clean and build process completed"
echo
echo "---------------------------------"