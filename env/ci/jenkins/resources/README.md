「git tag作成時ビルドするJenkinsPlugin」のビルドについて
===

直下にある `hpk` は、 以下の手順でビルドしたものです。

必要なもの:docker入のLinux,git

```bash
rm -rf ./jenkins-build-everything-strategy-plugin
git clone https://github.com/AngryBytes/jenkins-build-everything-strategy-plugin.git
docker run -it --rm --mount type=bind,src=$(pwd)/jenkins-build-everything-strategy-plugin,dst=/work \
  -w /work \
  maven:3-jdk-8 \
mvn -Dmaven.javadoc.skip=true package
```

これが実行された後に、出てくる

`./jenkins-build-everything-strategy-plugin/target/build-everything-strategy.hpi`

を直下に保存、Gitに登録、実際のJenkinsにもGUIから登録しています。

## TODO

AsCodeの枠組み内で、プラグインを登録したい。
