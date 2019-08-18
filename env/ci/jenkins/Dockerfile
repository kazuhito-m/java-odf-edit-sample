FROM jenkins/jenkins:lts-alpine

USER root

RUN apk add --no-cache --virtual make && \
    apk add --no-cache ca-certificates wget && \
    update-ca-certificates && \
    apk add --no-cache openssl && \
    apk add --no-cache postgresql-client && \
    rm -rf /var/cache/apk/*

# install docker, docker-compose
ENV DOCKER_VERSION 19.03.3-rc1
RUN curl -fL -o docker.tgz "https://download.docker.com/linux/static/test/x86_64/docker-${DOCKER_VERSION}.tgz" && \
    tar --strip-components=1 -xvzf docker.tgz -C /usr/bin
RUN curl -o /usr/local/bin/docker-compose -L https://github.com/docker/compose/releases/download/1.25.0-rc2/docker-compose-`uname -s`-`uname -m` && \
    chmod +x /usr/local/bin/docker-compose

# add docker group
RUN addgroup -S docker && adduser jenkins docker

# TimeZone & Locale settings
RUN apk --update add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata && \
    rm -rf /var/cache/apk/*
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP.UTF-8
ENV LC_ALL ja_JP.UTF-8
ENV TZ Asia/Tokyo
