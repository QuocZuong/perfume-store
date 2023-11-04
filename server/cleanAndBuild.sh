#!/bin/sh

echo
echo "-------------------------------"
echo
echo "Clean and build process started"
echo
echo "-------------------------------"

cd /home/quan99122/Project/ && echo "Went into the /Project" && git stash && echo "Stashed" && git fetch origin && echo "fetched" && git pull && echo "Pulled newest changes" && mvn package -DskipTests && sudo rm -rf /opt/tomcat/updated/webapps/ROOT && sudo rm -f /opt/tomcat/updated/webapps/ROOT.war && echo "Removed the old files" && sudo cp -rf ./target/SQLproject-1.war /opt/tomcat/updated/webapps/ROOT.war && echo "Done!"

echo "---------------------------------"
echo
echo "Clean and build process completed"
echo
echo "---------------------------------"