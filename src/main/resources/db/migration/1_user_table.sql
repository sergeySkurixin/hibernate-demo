CREATE SEQUENCE user_id_seq INCREMENT BY 1;

-- CREATE TABLE users
-- (
--     user_id numeric(20) PRIMARY KEY/* GENERATED ALWAYS AS ( user_id_seq.nextval ) STORED*/,
--     phone  numeric(20) not NULL ,
--     email  varchar(512)
-- );

CREATE TABLE users
(
    user_id varchar(36) PRIMARY KEY DEFAULT (uuid_generate_v4())::varchar(36),
    phone   numeric(20) NOT NULL,
    email   varchar(512)
);