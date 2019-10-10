"OpenDocument Spreadsheet" edit sample by Java
==============================================

OpenDocument Spreadsheetを使った「帳票テンプレート」サンプルアプリ

## Latest integration

+ Wercker : [![wercker status](https://app.wercker.com/status/220acd08eb808b9ba686d1130ae5d1bd/s/ "wercker status")](https://app.wercker.com/project/byKey/220acd08eb808b9ba686d1130ae5d1bd)
+ CircleCI : [![CircleCI](https://circleci.com/gh/kazuhito-m/java-odf-edit-sample.svg?style=svg)](https://circleci.com/gh/kazuhito-m/java-odf-edit-sample)
+ SonarCloud: [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=java-odf-edit-sample&metric=alert_status)](https://sonarcloud.io/dashboard?id=java-odf-edit-sample)

## What's this ?

「[第13回関西LibreOffice勉強会](http://connpass.com/event/40278/)」の「[むしゃくしゃしたのでOpenDocumentで帳票テンプレート](http://www.slideshare.net/miurakazuhito/opendocument-13libreoffice-libokansai)」のデモで使用したサンプルアプリケーションです。

基本的には「OpenDocument Spreadsheet(Libre Officeのドキュメント)をプログラム(Java)から操作する例」ですが、
以下のファクタを利用したサンプル例だったりします。

- JOpenDocument(Javaのライブラリ)
- Spring boot
- Doma2
- Flyway(DBマイグレーションツール)
- Twitter Bootstrap

## Usage

### Requirement

以下を前提とします。

- JDK11インストール
- docker & docker-compose がインストールされている

### Build and Run

- PostgresのDBを用意
```bash
./env/local/postgres/start.sh
```
- SpringBootのアプリ起動コマンドを叩く
```bash
./gradlew clean bootRun
```
- ブラウザからURLを指定
    + [http://localhost:8080](http://localhost:8080)

### Test

```bash
./gradlew clean check
```

### CI Server

```bash
./env/ci/start.sh
```

上記を実行した際、コンソールに表示されるメッセージに従ってください。


## Author

Kazuhito Miura ( [@kazuhito_m](https://twitter.com/kazuhito_m) on Twitter )
