#!/bin/sh
set -e

# create docker image
./gradlew clean build docker

# run it
docker run -p 8080:8080 -t springio/stt-sample
