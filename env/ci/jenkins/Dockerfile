FROM jenkinsci/blueocean:1.19.0

USER root

RUN apk add --no-cache postgresql-client && \
    rm -rf /var/cache/apk/*

# install docker-compose
RUN curl -o /usr/local/bin/docker-compose -L https://github.com/docker/compose/releases/download/1.25.0-rc2/docker-compose-`uname -s`-`uname -m` && \
    chmod +x /usr/local/bin/docker-compose

# TimeZone & Locale settings
RUN apk --update add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata && \
    rm -rf /var/cache/apk/*
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP.UTF-8
ENV LC_ALL ja_JP.UTF-8
ENV TZ Asia/Tokyo
