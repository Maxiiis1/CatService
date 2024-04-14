CREATE TABLE owner (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    born_date DATE,
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE cat (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    breed VARCHAR(255),
    color VARCHAR(15),
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES owner(id)
);

CREATE TABLE friendship (
    friend_1 INT,
    friend_2 INT,
    PRIMARY KEY (friend_1, friend_2),
    FOREIGN KEY (friend_1) REFERENCES Cat(id),
    FOREIGN KEY (friend_2) REFERENCES Cat(id)
);