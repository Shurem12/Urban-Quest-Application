-- Drop existing tables and sequences if they exist
DROP TABLE IF EXISTS users_itinerary CASCADE;
DROP TABLE IF EXISTS itinerary_landmarks CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS itinerary CASCADE;
DROP TABLE IF EXISTS landmarks CASCADE;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP SEQUENCE IF EXISTS seq_itinerary_id;

BEGIN TRANSACTION;

-- Create sequence for user IDs
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

-- Create users table
CREATE TABLE users (
  user_id int DEFAULT nextval('seq_user_id') PRIMARY KEY,
  username varchar(50) NOT NULL UNIQUE,
  password_hash varchar(200) NOT NULL,
  role varchar(50) NOT NULL
);

-- Create sequence for itinerary IDs
CREATE SEQUENCE seq_itinerary_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

-- Create itinerary table
CREATE TABLE itinerary (
  itinerary_id int DEFAULT nextval('seq_itinerary_id') PRIMARY KEY,
  name varchar(250) NOT NULL,
  event_date DATE NOT NULL,
  start_time TIME,
  end_time TIME,
  starting_point TEXT
);

-- Create landmarks table with only place_id
CREATE TABLE landmarks (
  place_id varchar(50) PRIMARY KEY
);

-- Create linking table for users and itineraries
CREATE TABLE users_itinerary (
  user_id int,
  itinerary_id int,
  CONSTRAINT pk_users_itinerary PRIMARY KEY (user_id, itinerary_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (itinerary_id) REFERENCES itinerary(itinerary_id) ON DELETE CASCADE
);

-- Create linking table for itineraries and landmarks
CREATE TABLE itinerary_landmarks (
  itinerary_id int,
  place_id varchar(50),
  CONSTRAINT pk_itinerary_landmarks PRIMARY KEY (itinerary_id, place_id),
  FOREIGN KEY (itinerary_id) REFERENCES itinerary(itinerary_id) ON DELETE CASCADE,
  FOREIGN KEY (place_id) REFERENCES landmarks(place_id) ON DELETE CASCADE
);

COMMIT;
