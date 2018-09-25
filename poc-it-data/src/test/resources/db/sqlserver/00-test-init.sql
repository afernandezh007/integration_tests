-- =====================================================================================================================
-- Init script
-- =====================================================================================================================

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'poc_jdbc') BEGIN CREATE DATABASE poc_jdbc END

USE poc_jdbc

IF NOT EXISTS (select loginname from master.dbo.syslogins where name = 'poc_jdbc_user') BEGIN CREATE LOGIN poc_jdbc_user WITH PASSWORD = 'poc_jdbc_user@password_2018' CREATE USER poc_jdbc_user FOR LOGIN poc_jdbc_user EXEC sp_addrolemember 'db_owner', 'poc_jdbc_user' EXEC ('CREATE SCHEMA poc_jdbc') END





