CREATE SCHEMA bankaccountdb;

CREATE TABLE bankaccountdb.account (
    id bigint NOT NULL PRIMARY KEY,
    account_number bigint(9) NOT NULL,
    current_balance NUMERIC(10,3) NOT NULL,
    bank_name VARCHAR(50) NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    card_type VARCHAR(50) NOT NULL,
    card_no bigint(10) NOT NULL,
    UNIQUE (account_number)
);
CREATE SEQUENCE IF NOT EXISTS id_seq;
CREATE SEQUENCE bankaccountdb.transaction_seq START WITH 5;
CREATE TABLE bankaccountdb.transaction (
    id bigint NOT NULL PRIMARY KEY,
    source_account_id bigint NOT NULL REFERENCES bankaccountdb.account(id),
    target_account_id bigint NOT NULL REFERENCES bankaccountdb.account(id),
    target_owner_name varchar(50) NOT NULL,
    amount NUMERIC(10,3) NOT NULL,
    initiation_date timestamp NOT NULL,
    completion_date TIMESTAMP,
    reference VARCHAR(255));
