CREATE TABLE app_user(
    id bytea not null,
    fullname varchar(127) not null,
    username varchar(127),
    password varchar(255),
    birthday date,
    address varchar(127),
    role varchar(127),
    primary key (id)
);

CREATE TABLE device(
    id bytea not null,
    device_description varchar(255),
    sensor_description varchar(255),
    sensor_max_value float8,
    location varchar(255),
    maxec float8,
    avgec float8,
    user_id bytea,
    primary key(id),
    foreign key (user_id) references app_user(id) on delete cascade
);

CREATE TABLE measurement(
    id bytea not null,
    ec float8,
    timestamp timestamp,
    device_id bytea,
    primary key(id),
    foreign key (device_id) references device(id) on delete cascade
);