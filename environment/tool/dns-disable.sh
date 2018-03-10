#!/usr/bin/env bash
set -e
pushd . > /dev/null
cd $(dirname ${BASH_SOURCE[0]})
source ./../docker-compose.sh
popd > /dev/null

function dns_disable () {
    remove_dns_from_host_dnsmasq
    stop_service "dns"
}

function remove_dns_from_host_dnsmasq () {
    sudo rm /etc/NetworkManager/dnsmasq.d/ue_docker_${DOMAIN_NAME}.conf
    sudo systemctl restart NetworkManager
}

function stop_service () {
    docker_compose_in_environment stop $1
}

if [ "${BASH_SOURCE[0]}" == "$0" ]; then
    dns_disable
fi
