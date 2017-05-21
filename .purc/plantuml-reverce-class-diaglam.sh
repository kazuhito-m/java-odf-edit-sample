#!/bin/bash

# どこから呼ばれても同じ挙動をする様に
scriptDir=$(cd $(dirname $0);pwd)
cd ${scriptDir}

# 作業ディレクトリ作成
outDir=./class-diagrams
workDir=./work
clearDir ${outDir}
clearDir ${workDir}

# 共通関数読込
source ./functions.sh
# 設定ファイル読みこみ
source ./setting.conf

cd ${workDir}

# plantuml-dependency-cli をダウンロード
wget https://sourceforge.net/projects/plantuml-depend/files/1.4.0/plantuml-dependency-cli-1.4.0-archive-with-bundled-dependencies.tar.gz
tar xzf plantuml-dependency-cli*.tar.gz
mv ./plantuml-dependency-cli*/plantuml-dependency-cli-*.jar ./plantuml-dependency-cli.jar

# plantuml 自体をダウンロード
wget https://sourceforge.net/projects/plantuml/files/1.2017.13/plantuml.1.2017.13.jar
mv plantuml.*.jar plantuml.jar

cd ../

for targetPackage in $(cat ./target-package.list) ; do
  targetDir=`echo ${targetPackage} | sed 's/\./\//g'`
  lastPackageName=`basename ${targetDir}`
  targetFile=${outDir}/${lastPackageName}.pu

  # plantumlのクラス図ファイルへリバース
  java -jar ${workDir}/plantuml-dependency-cli.jar --basedir ../${SRC_DIR}/${targetDir} -o ${targetFile}

  # 作成されたファイルを加工
  for ignoreWord in $(cat ./ignore-words.list) ; do
    rowDelete ${targetFile}  ${ignoreWord}
  done

  # クラス図テキストファイルから、画像へ。
  java -jar ${workDir}/plantuml.jar ${targetFile}

done
