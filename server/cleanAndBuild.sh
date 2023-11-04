#!/bin/sh

echo
echo "-------------------------------"
echo
echo "Clean and build process started"
echo
echo "-------------------------------"

cd /home/quan99122/Project/ && git stash && git fetch origin && git pull && mvn package -DskipTests && rm -rf /opt/tomcat/updated/webapps/ROOT && rm -f /opt/tomcat/updated/webapps/ROOT.war && cp -rf ./target/SQLproject-1.war /opt/tomcat/updated/webapps/ROOT.war

echo "---------------------------------"
echo
echo "Clean and build process completed"
echo
echo "---------------------------------"