#! /bin/sh
#/*
# Added to the Apache River distribution by
# Dr Gary Allen, University of Huddersfield
# to simplify all the services needed to run
# Javaspace programs.
# The script starts:
#     http server
#     reggie  (lookup service)
#     outrigger  (javaspace)
#     mahalo  (transaction manager)
#*/

# Shell script to start all services needed 
# to run Javaspace applications

echo "\nStarting http server"
java -jar ../lib/classserver.jar -port 8080 -dir lib:../lib-dl $* &
sleep 3

echo "\n\nStarting reggie  (lookup service)"
java -Djava.rmi.server.useCodebaseOnly=false \
     -Djava.security.policy=config/policy.all \
     -jar ../lib/start.jar \
     config/start-transient-jrmp-reggie.config &
sleep 3

echo "\n\nStarting outrigger  (javaspace)"
java -Djava.rmi.server.useCodebaseOnly=false \
     -Djava.security.policy=config/policy.all \
     -jar ../lib/start.jar \
     config/start-transient-jrmp-outrigger.config &
sleep 3

echo "\n\nStarting mahalo  (transaction manager)"
java -Djava.rmi.server.useCodebaseOnly=false \
     -Djava.security.policy=config/policy.all \
     -jar ../lib/start.jar \
     config/start-transient-jrmp-mahalo.config &
#sleep 3

echo "\nDone"

