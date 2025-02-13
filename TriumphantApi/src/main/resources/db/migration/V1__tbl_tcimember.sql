-- Create enum for gender
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');

-- Create the table for tbl_tcimember
CREATE TABLE tbl_tcimember (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    msisdn VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    picture BYTEA, -- BYTEA for storing byte arrays in PostgreSQL
    has_cash BOOLEAN DEFAULT FALSE, -- Default value of hasCash set to false
    is_enabled BOOLEAN
);

-- Add any additional constraints or indexes if needed
