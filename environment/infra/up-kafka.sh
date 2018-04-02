#!/usr/bin/env bash
set -e
pushd . > /dev/null
cd $(dirname ${BASH_SOURCE[0]})
source ./../docker-compose.sh
popd > /dev/null

function main () {
    start_service "kafka1"
    start_service "kafka2"
    start_service "kafka3"
    start_service "kafka4"
    start_service "kafka5"

    start_service "kafka1prometheusexporter"
    start_service "kafka2prometheusexporter"
    start_service "kafka3prometheusexporter"
    start_service "kafka5prometheusexporter"
}

function start_service () {
    docker_compose_in_environment up -d $1
}

main
