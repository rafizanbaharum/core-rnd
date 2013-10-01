$ createdb corernd
$ psql
psql (9.1.1)
Type "help" for help.
 
postgres=# CREATE USER corernd WITH PASSWORD 'corernd';
CREATE ROLE
postgres=# CREATE DATABASE corernd;
CREATE DATABASE
postgres=# GRANT ALL PRIVILEGES ON DATABASE corernd to corernd;
GRANT
postgres=#