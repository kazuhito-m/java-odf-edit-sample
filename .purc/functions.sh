#!/bin/bash

function rowDelete() {
  fileName=${1}
  target=${2}
  cat ${fileName} | grep -v "${target}"  > ${fileName}.tmp
  mv ${fileName}.tmp ${fileName}
}

function rowLeave() {
  fileName=${1}
  target=${2}
  cat ${fileName} | grep "${target}"  > ${fileName}.tmp
  mv ${fileName}.tmp ${fileName}
}

function rowReplace() {
  fileName=${1}
  target=${2}
  cat ${fileName} | sed "s/${target}//g"  >  ${fileName}.tmp
  mv ${fileName}.tmp ${fileName}
}

function addHeaderFooter() {
  fileName=${1}
  echo '@startuml' > ${fileName}.tmp
  cat ${fileName} >> ${fileName}.tmp
  echo '@enduml' >> ${fileName}.tmp
  mv ${fileName}.tmp ${fileName}
}

function clearDir() {
  targetDir=${1}
  rm -rf ${targetDir}
  mkdir ${targetDir}
}
