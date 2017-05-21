#!/bin/bash

function clearDir() {
  targetDir=${1}
  rm -rf ${targetDir}
  mkdir -p ${targetDir}
}

function rowDelete() {
  fileName=${1}
  target=${2}
  cat ${fileName} | grep -v "${target}"  > ${fileName}.tmp
  mv ${fileName}.tmp ${fileName}
}

function makeHtml() {
  packageListFilePath=${1}
  outDir=${2}
  htmlFile="${outDir}/index.html"
  echo '<!DOCTYPE HTML><head><title>Class Diaglam Powerd by PlantUML</title></head><body>' > ${htmlFile}
  for targetPackage in $(cat ./target-package.list) ; do
    targetDir=`echo ${targetPackage} | sed 's/\./\//g'`
    lastPackageName=`basename ${targetDir}`
    echo "<h1>package : ${targetPackage}</h1>" >> ${htmlFile}
    echo "<img src='./${lastPackageName}.png' alt='${lastPackageName}'>" >> ${htmlFile}
  done
  echo '</body></html>' >> ${htmlFile}
}
