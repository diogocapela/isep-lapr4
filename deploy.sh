#!/bin/bash
export SERVER="$(ps aux | grep ./rest/target/rest-1.0-jar-with-dependencies.jar | grep -v "grep" | awk {'print $2'})"
kill -15 $SERVER
nohup java -cp ./rest/target/rest-1.0-jar-with-dependencies.jar rest.RestMain "-mysql" < /dev/null >> ~/logfile.log 2>&1 &
