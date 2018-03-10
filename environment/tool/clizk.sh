#!/usr/bin/env bash
set -e
cd $(readlink -f $(dirname $0))
source ./../docker-compose.sh

docker_compose_in_environment run --rm "toolclizk" $@
