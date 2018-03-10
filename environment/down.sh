#!/usr/bin/env bash
set -e
DIR=$(cd $(dirname $0); pwd)
pushd . > /dev/null
cd $DIR
source ./docker-compose.sh
source ./tool/dns-disable.sh
popd > /dev/null

function main() {
    dns_disable || true
    docker_compose_in_environment down -v --remove-orphans
}

main