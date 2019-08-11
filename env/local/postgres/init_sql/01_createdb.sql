CREATE USER databaseuser WITH SUPERUSER PASSWORD 'databasepassword';
CREATE DATABASE odf_edit_sample ENCODING 'UTF8' LC_COLLATE 'C' TEMPLATE 'template0' OWNER 'databaseuser';
ALTER DATABASE odf_edit_sample SET timezone TO 'Asia/Tokyo';
