-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_user WHERE usename = 'final_capstone_owner') THEN
    CREATE USER final_capstone_owner WITH PASSWORD 'finalcapstone';
  END IF;
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_user WHERE usename = 'final_capstone_appuser') THEN
    CREATE USER final_capstone_appuser WITH PASSWORD 'finalcapstone';
  END IF;
END
$$;

CREATE USER final_capstone_owner
WITH PASSWORD 'finalcapstone';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO final_capstone_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO final_capstone_owner;

CREATE USER final_capstone_appuser
WITH PASSWORD 'finalcapstone';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO final_capstone_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO final_capstone_appuser;
