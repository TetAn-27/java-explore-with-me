DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS events_compilations CASCADE;
DROP TABLE IF EXISTS requests CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(250),
    email varchar(300) UNIQUE
);

CREATE TABLE IF NOT EXISTS categories(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS location(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat float NOT NULL,
    lon float NOT NULL
);

CREATE TABLE IF NOT EXISTS events(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation varchar(250),
    category_id BIGINT NOT NULL,
    confirmed_requests long,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    event_date TIMESTAMP WITHOUT TIME ZONE,
    initiator_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    paid boolean NOT NULL,
    participant_limit integer,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation boolean,
    state BIGINT NOT NULL,
    title varchar(250) NOT NULL,
    views integer
);

CREATE TABLE IF NOT EXISTS compilations(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned boolean NOT NULL,
    title varchar(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS events_compilations(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id integer REFERENCES events(id),
    comp_id integer REFERENCES compilations(id)
);

CREATE TABLE IF NOT EXISTS requests(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status BIGINT NOT NULL
);