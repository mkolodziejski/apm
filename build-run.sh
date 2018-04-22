#!/bin/bash

set -e

# build client webapp
pushd webapp
 ./gradlew clean build
popd

# build javaagent
pushd javaagent
./gradlew clean build
popd

# build docker image
docker build -t apm-webapp .

# run docker image
docker run -it --rm -p 8080:8080 -p 5000:5000 apm-webapp

