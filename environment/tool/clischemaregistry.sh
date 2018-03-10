#!/usr/bin/env bash
set -e
cd $(readlink -f $(dirname $0))
source ../docker-compose-runcmd.sh

run_in_environment "toolclischemaregistry" $@
