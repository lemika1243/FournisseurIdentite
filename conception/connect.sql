CREATE DATABASE cloud;
CREATE USER cloud WITH PASSWORD ' ';
ALTER DATABASE cloud OWNER TO cloud;

\c cloud cloud

psql -U cloud -d cloud

psql -U postgres