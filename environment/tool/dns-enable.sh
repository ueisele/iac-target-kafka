#!/usr/bin/env bash
set -e
pushd . > /dev/null
cd $(dirname ${BASH_SOURCE[0]})
source ./../docker-compose.sh
source ./../ips.sh
popd > /dev/null

function dns_enable () {
    start_service "dns"
    add_as_dns_to_host_dnsmasq "dns"
}

function start_service () {
    docker_compose_in_environment up -d $1
}

function add_as_dns_to_host_dnsmasq () {
    dns_ip=$(ip_of_service $1 1)
    sudo sh -c "echo \"server=/${DOMAIN_NAME}/${dns_ip}#53\" > /etc/NetworkManager/dnsmasq.d/ue_docker_${DOMAIN_NAME}.conf"
    sudo systemctl restart NetworkManager
}

if [ "${BASH_SOURCE[0]}" == "$0" ]; then
    dns_enable
fi

