#!/bin/bash
#
# 「docker-composeで(イメージまでクリアして)立て直すスクリプト。
#

# 「このスクリプトがある場所」まで移動
SCRIPT_DIR=$(cd $(dirname $(readlink -f $0 || echo $0));pwd -P)
cd ${SCRIPT_DIR}

docker-compose down
docker images -qa | xargs docker rmi
docker-compose up -d

result=${?}
if [ ! ${result} = 0 ]; then
  echo 'docker-compose の起動に失敗しました。'
  exit ${result}
fi

echo '起動成功。'
