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
