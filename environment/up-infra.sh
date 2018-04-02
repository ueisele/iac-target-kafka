#!/usr/bin/env bash
set -e
cd $(readlink -f $(dirname $0))

function main () {
    ./infra/up-zookeeper.sh
    sleep 5
    ./infra/up-kafka.sh
    sleep 5
    ./infra/up-schemaregistry.sh
    #./infra/up-kafkarestproxy.sh
    #./infra/up-kafkaconnect.sh
    #./infra/up-confluentcontrolcenter.sh
    ./infra/up-prometheus.sh
    ./infra/up-grafana.sh

    ./tool/dns-enable.sh

    ./ips.sh
}

main
