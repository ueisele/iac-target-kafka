#!/usr/bin/env bash
set -e
j2 ${CONFIG_FILE} > /etc/JmxPrometheusExporter/config.yaml
echo "Using JMX prometheus exporter configuration:"
cat /etc/JmxPrometheusExporter/config.yaml
exec /usr/local/bin/jmx-prometheus-exporter.sh ${EXPORTER_PORT} /etc/JmxPrometheusExporter/config.yaml