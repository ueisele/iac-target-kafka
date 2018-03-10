#!/usr/bin/env bash
set -e
pushd . > /dev/null
cd $(dirname ${BASH_SOURCE[0]})
CWD_INFRA=`pwd`
source ./../docker-compose.sh
source ./../ips.sh
popd > /dev/null

function main () {
    start_service "grafana"
    sleep 5
    (cd "$CWD_INFRA" && add_datasources_in_dir "grafana" ./../config/infra/grafana/datasource/)
    (cd "$CWD_INFRA" && add_dashboards_in_dir "grafana" ./../config/infra/grafana/dashboard/)
}

function start_service () {
    docker_compose_in_environment up -d $1
}

function add_datasources_in_dir () {
    service_name=${1:?'Missing service name!'}
    datasource_dir=${2:?'Missing datasource directory!'}
    shopt -s extglob
    datasource_files=`ls ${datasource_dir}/!(*.tmpl).json`
    shopt -u extglob
    for file in ${datasource_files}; do
        add_datasource ${service_name} ${file}
    done
}

function add_datasource () {
    service_name=${1:?'Missing service name!'}
    datasource_file=${2:?'Missing datasource file!'}
    grafana_ip=$(ip_of_service ${service_name} 1)
    curl -vsL -u admin:admin -X POST -d "@${datasource_file}" -H "Content-Type: application/json" http://${grafana_ip}:3000/api/datasources
}

function add_dashboards_in_dir () {
    service_name=${1:?'Missing service name!'}
    dashboard_dir=${2:?'Missing dashboard directory!'}
    shopt -s extglob
    dashboard_files=`ls ${dashboard_dir}/!(*.tmpl).json`
    shopt -u extglob
    for file in ${dashboard_files}; do
        add_dashboard ${service_name} ${file}
    done
}

function add_dashboard () {
    service_name=${1:?'Missing service name!'}
    dashboard_file=${2:?'Missing dashboard file!'}
    dashboard=$(cat "$dashboard_file")
    dashboard_request="{\"overwrite\": true, \"dashboard\": ${dashboard} }"
    grafana_ip=$(ip_of_service ${service_name} 1)
    curl -vsfL -u admin:admin -X POST -d "$dashboard_request" -H 'Content-Type: application/json' http://${grafana_ip}:3000/api/dashboards/db
}

main
