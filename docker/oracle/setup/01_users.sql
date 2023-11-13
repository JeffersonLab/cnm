alter session set container = XEPDB1;

ALTER SYSTEM SET db_create_file_dest = '/opt/oracle/oradata';

create tablespace CNM;

create user "CNM_OWNER" profile "DEFAULT" identified by "password" default tablespace "CNM" account unlock;

grant connect to CNM_OWNER;
grant unlimited tablespace to CNM_OWNER;

grant create view to CNM_OWNER;
grant create sequence to CNM_OWNER;
grant create table to CNM_OWNER;
grant create procedure to CNM_OWNER;
grant create type to CNM_OWNER;
grant create trigger to CNM_OWNER;