FROM anapsix/alpine-java:8
ARG VERSION="0.1.0"
ARG CHECKSUM_SHA1="b4604e6871ad87f040c4f1ff1403ce33f55b3b6b"

RUN apk update && apk upgrade && apk --update add coreutils curl python python-dev py-pip && rm -rf /tmp/* /var/cache/apk/* && \
    pip install j2cli && \
    curl -vsLo /usr/local/lib/jmx-prometheus-exporter.jar "https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_httpserver/${VERSION}/jmx_prometheus_httpserver-${VERSION}-jar-with-dependencies.jar" && \
    echo "${CHECKSUM_SHA1} /usr/local/lib/jmx-prometheus-exporter.jar" | sha1sum -c

COPY config.yaml.j2 /etc/JmxPrometheusExporter/config.yaml.j2
COPY rules.yaml /etc/JmxPrometheusExporter/rules.yaml
COPY jmx-prometheus-exporter.sh /usr/local/bin/jmx-prometheus-exporter.sh
COPY jmx-prometheus-exporter-entrypoint.sh /usr/local/bin/jmx-prometheus-exporter-entrypoint.sh

ENV CONFIG_FILE="/etc/JmxPrometheusExporter/config.yaml.j2"
ENV RULE_FILE="/etc/JmxPrometheusExporter/rules.yaml"
ENV EXPORTER_PORT=9404
EXPOSE ${EXPORTER_PORT}
ENTRYPOINT ["/usr/local/bin/jmx-prometheus-exporter-entrypoint.sh"]