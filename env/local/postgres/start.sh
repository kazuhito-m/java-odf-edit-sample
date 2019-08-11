#!/bin/bash

# 「このスクリプトがある場所」まで移動
SCRIPT_DIR=$(cd $(dirname $(readlink -f $0 || echo $0));pwd -P)
cd ${SCRIPT_DIR}

# 既にあるなら停止・削除
docker ps | grep 'postgres-odf-edit-sample' && docker-compose down

# 実行
docker-compose up -d --build
