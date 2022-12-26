CREATE TABLE name_address
(
    id           bigint AUTO_INCREMENT PRIMARY KEY,
    name         varchar(255) not null,
    house_number varchar(255) not null,
    city         varchar(255) not null,
    province     varchar(255) not null,
    postal_code  varchar(255) not null
);