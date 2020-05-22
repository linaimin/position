-- create role
create role "position" login encrypted password 'position' inherit connection limit -1;
-- create database
create database position with owner = position encoding = 'UTF8';
-- login by position, create schema
create schema position authorization position;