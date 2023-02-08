drop table databasechangeloglock;
drop table databasechangelog;
drop table application;
drop table client;
drop table credit;

create table credit (
    credit_id bigserial  not null primary key,
    amount double precision,
    term int,
    monthly_payment double precision,
    rate double precision,
    psk double precision,
    payment_schedule json,
    insurance_enable boolean,
    salary_client boolean,
    credit_status varchar
);
create table client (
    client_id bigserial not null  primary key,
    last_name varchar,
    first_name varchar,
    middle_name varchar,
    birth_date date,
    email varchar,
    gender varchar,
    marital_status varchar,
    dependent_amount int,
    passport json,
    employment json,
    account varchar
);
create table application (
    application_id bigserial primary key,
    client_id bigint,
    credit_id bigint,
    status varchar,
    creation_date date,
    applied_offer json,
    sign_date date,
    ses_code int,
    status_history json,
    foreign key (client_id) references client (client_id),
    foreign key (credit_id) references credit (credit_id)
);



select * from client;
select * from application;
select * from credit;

