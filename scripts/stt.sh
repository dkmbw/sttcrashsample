#!/bin/bash

# This script has to be called from the git root directory

curl --insecure -X POST "http://localhost:8080/sttwav" -H  "accept: */*" -H  "Content-Type: application/octet-stream" --data-binary @src/test/resources/gut.wav

