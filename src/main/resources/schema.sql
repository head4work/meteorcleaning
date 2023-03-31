DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS prices;


CREATE TABLE users
(
    id         INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name       VARCHAR                 NOT NULL,
    email      VARCHAR                 NOT NULL,
    password   VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOL      DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE tokens
(
    id      INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id INTEGER                 NOT NULL,
    token   VARCHAR,
    created TIMESTAMP DEFAULT now() NOT NULL,
    expire  TIMESTAMP               NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id                 INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,

    create_date        DATE                  NOT NULL DEFAULT CURRENT_DATE,
    name               VARCHAR               NOT NULL,
    last_name          VARCHAR,
    email              VARCHAR               NOT NULL,
    address            VARCHAR               NOT NULL,
    phone              VARCHAR               NOT NULL,
    housing_type       VARCHAR               NOT NULL,
    square_ft          VARCHAR,
    bedrooms           VARCHAR               NOT NULL,
    bathrooms          VARCHAR               NOT NULL,
    half_bathrooms     VARCHAR               NOT NULL,

    green_clean        BOOL    DEFAULT FALSE NOT NULL,
    deep_clean         BOOL    DEFAULT FALSE NOT NULL,
    microwave_clean    BOOL    DEFAULT FALSE NOT NULL,
    refrigerator_clean BOOL    DEFAULT FALSE NOT NULL,
    oven_clean         BOOL    DEFAULT FALSE NOT NULL,
    dishes_clean       BOOL    DEFAULT FALSE NOT NULL,

    windows            INTEGER DEFAULT 0,
    cabinets           INTEGER DEFAULT 0,

    date_time          TIMESTAMP             NOT NULL,
    estimated_price    VARCHAR               NOT NULL,
    estimated_time     VARCHAR               NOT NULL,
    user_id            INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);
create unique index orders_date_time_uindex
    on orders (date_time);



CREATE TABLE prices
(
    prices_id    bool PRIMARY KEY DEFAULT TRUE,

    studio       INTEGER          DEFAULT 140,
    apartments   INTEGER          DEFAULT 170,
    house        INTEGER          DEFAULT 220,
    housesqr     NUMERIC(2, 1)    DEFAULT 0.5,
    office       NUMERIC(2, 1)    DEFAULT 0.3,
    bedroom      INTEGER          DEFAULT 20,
    bathroom     INTEGER          DEFAULT 24,
    green        INTEGER          DEFAULT 40,
    deep         NUMERIC(2, 1)    DEFAULT 1.3,
    microwave    INTEGER          DEFAULT 20,
    refrigerator INTEGER          DEFAULT 30,
    oven         INTEGER          DEFAULT 30,
    windows      INTEGER          DEFAULT 8,
    cabinet      INTEGER          DEFAULT 10,
    dishes       INTEGER          DEFAULT 10,
    weekend      INTEGER          DEFAULT 50,

    CONSTRAINT prices_uni CHECK (prices_id)
);

