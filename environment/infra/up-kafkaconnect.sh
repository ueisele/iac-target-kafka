#!/usr/bin/env bash
set -e
pushd . > /dev/null
cd $(dirname ${BASH_SOURCE[0]})
source ./../docker-compose.sh
popd > /dev/null

function main () {
    start_service "kafkaconnect1"
    start_service "kafkaconnect2"
    start_service "kafkaconnect3"
    start_service "kafkaconnect4"
}

function start_service () {
    docker_compose_in_environment up -d $1
}

main
