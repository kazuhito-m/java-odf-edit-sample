FROM postgres:15.1-alpine

# タイムゾーン＆ロケール設定。
RUN apk --update add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata && \
    rm -rf /var/cache/apk/*
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP.UTF-8
ENV LC_ALL ja_JP.UTF-8
ENV TZ Asia/Tokyo

COPY init_sql/*.sql /docker-entrypoint-initdb.d/

# 多段ファイルシステム対応。(仮想機の上で別ファイルシステムがある場合、ファイル権限がおかしくなり読めなく成る場合があるため)
RUN chmod 777 /docker-entrypoint-initdb.d/*
