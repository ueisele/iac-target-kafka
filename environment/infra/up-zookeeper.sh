#!/usr/bin/env bash
set -e
pushd . > /dev/null
cd $(dirname ${BASH_SOURCE[0]})
source ./../docker-compose.sh
popd > /dev/null

function main () {
    start_service "zookeeper1"
    start_service "zookeeper2"
    start_service "zookeeper3"
}

function start_service () {
    docker_compose_in_environment up -d $1
}

main
