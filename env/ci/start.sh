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

sleep 10
echo '起動成功。'

# 起動が上手く行ったようなら、初期パスワードを表示する(取れないなら何も表示せず終わる)
docker-compose logs | grep '/var/jenkins_home/secrets/initialAdminPassword' > /dev/null
if [ ! ${?} = 0 ]; then
  exit 0
fi
echo '初期ログイン用パスワード'
docker-compose logs | grep -B5 '/var/jenkins_home/secrets/initialAdminPassword'
