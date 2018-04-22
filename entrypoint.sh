#!/bin/sh

set -e

# start UI
python /apm/ui/app.py &

# start webapp
java -javaagent:/apm/javaagent/javaagent-1.0.0.jar -jar /webapp/webapp-1.0.0.jar