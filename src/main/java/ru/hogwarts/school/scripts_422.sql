CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    brand VARCHAR,
    model VARCHAR,
    price NUMERIC CHECK ( price>0));
CREATE TABLE drivers (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    age INTEGER,
    license BOOLEAN,
    car_id INTEGER REFERENCES car(id));
