drop table if exists VACCINES CASCADE;
drop table if exists CAMPAIGN_STATUSES CASCADE;
drop table if exists CAMPAIGNS CASCADE;
drop table if exists VACCINES_CAMPAIGNS CASCADE;
drop table if exists USER_BASES CASCADE;
drop table if exists USERS CASCADE;
drop table if exists DOSE_REQUESTS CASCADE;
drop table if exists DOSE_TRANSFER_REQUESTS CASCADE;

-- Table: VACCINES
CREATE TABLE VACCINES (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(200)  NOT NULL UNIQUE,
    description VARCHAR(200),
    creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: CAMPAIGNS
CREATE TABLE CAMPAIGN_STATUSES (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status varchar(30) NOT NULL UNIQUE
);

CREATE TABLE CAMPAIGNS (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    campaign_status_id BIGINT NOT NULL REFERENCES CAMPAIGN_STATUSES(id),
    name VARCHAR(200) NOT NULL UNIQUE,
    start_date DATETIME  NOT NULL,
    end_date DATETIME  NOT NULL,
    creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: VACCINE_CAMPAIGN
CREATE TABLE VACCINES_CAMPAIGNS (
    campaign_id BIGINT  NOT NULL REFERENCES CAMPAIGNS(id),
    vaccine_id BIGINT  NOT NULL REFERENCES VACCINES(id)
);

-- Table: USER
CREATE TABLE USER_BASES (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    location varchar(50) NOT NULL UNIQUE
);

CREATE TABLE USERS (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username varchar(200) NOT NULL UNIQUE,
    name varchar(200) NOT NULL,
    password varchar(512) NOT NULL,
    user_base_id BIGINT NOT NULL REFERENCES USER_BASES(id),
    creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: DOSE_REQUESTS
CREATE TABLE DOSE_REQUESTS (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    campaign_id BIGINT NOT NULL REFERENCES CAMPAIGNS(id),
    vaccine_id BIGINT  NOT NULL REFERENCES VACCINES(id),
    requester_user_id BIGINT  NOT NULL REFERENCES USERS(id),
    patient_name varchar(70),
    creation TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transfer_request BOOLEAN NOT NULL DEFAULT FALSE
);

-- Table: DOSE_TRANSFER_REQUESTS
CREATE TABLE DOSE_TRANSFER_REQUEST_STATUSES(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status VARCHAR(16) UNIQUE NOT NULL
);

CREATE TABLE DOSE_TRANSFER_REQUESTS (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
     requested_dose_id BIGINT NOT NULL REFERENCES DOSE_REQUESTS(id),
     recipient_user_id BIGINT NOT NULL REFERENCES USERS(id),
     dose_transfer_request_status_id BIGINT NOT NULL REFERENCES DOSE_TRANSFER_REQUEST_STATUSES(id),
     creation TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
);
