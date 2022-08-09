#!/usr/bin/env bash

set -e

TAG=$1
JAR=ideabucket-0.0.1-SNAPSHOT.jar

docker buildx build --target builder .
docker buildx build --load --target runner --build-arg JAR="/app/target/$JAR" -t $TAG . 
