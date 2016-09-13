# デプロイ外作業

## MySQLの Docker Command

```bash
docker run --name mysql01 -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:latest --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --lower_case_table_names=1
```

## DBにrootで入って実行。

```bash
mysql -h 127.0.0.1 -P 3306 -u root -p
```

```SQL
CREATE DATABASE odf_edit_sample;
CREATE USER 'databaseuser' IDENTIFIED BY 'databasepassword';
GRANT ALL ON odf_edit_sample.* TO 'databaseuser';
```

## 一度コンソールを出て、接続し直してテスト。

```bash
mysql -h 127.0.0.1 -P 3306 -u databaseuser -p odf_edit_sample
```

## キャラクタセット確認

```SQL
show variables like "chara%";
```
