create sequence hibernate_sequence start with 1 increment by 1;

create table accepted
(
    premise_offer_id   bigint not null,
    searching_offer_id bigint not null,
    primary key (premise_offer_id, searching_offer_id)
);
create table hosts
(
    user_id bigint not null,
    primary key (user_id)
);
create table offer_images
(
    id               bigint not null,
    image            BYTEA,
    premise_offer_id bigint,
    primary key (id)
);
create table pending
(
    premise_offer_id   bigint not null,
    searching_offer_id bigint not null,
    primary key (premise_offer_id, searching_offer_id)
);
create table premise_offers
(
    id                  bigint  not null,
    active              boolean not null,
    animals_allowed     boolean not null,
    bathrooms           integer not null,
    bedrooms            integer not null,
    description         varchar(255),
    from_date           timestamp,
    host_user_id        bigint,
    kitchens            integer not null,
    people_to_take      integer not null,
    premise_address     varchar(255),
    to_date             timestamp,
    verified            boolean not null,
    wheelchair_friendly boolean not null,
    primary key (id)
);
create table refugees
(
    user_id bigint not null,
    primary key (user_id)
);
create table searching_offers
(
    id                                  bigint  not null,
    additional_info                     TEXT,
    animals_involved                    boolean,
    refugee_user_id                     bigint,
    people_total                        integer not null,
    preferred_location                  varchar(255),
    range_from_preferred_location_in_km double precision,
    primary key (id)
);
create table searching_people
(
    id                 bigint  not null,
    birth_date         timestamp,
    moving_issues      boolean not null,
    name               varchar(255),
    searching_offer_id bigint,
    sex                varchar(255),
    surname            varchar(255),
    primary key (id)
);
create table users
(
    id                  bigint  not null,
    account_type        varchar(255),
    birth_date          timestamp,
    email               varchar(255),
    hashed_password     varchar(255),
    is_account_verified boolean not null,
    name                varchar(255),
    phone_number        varchar(255),
    sex                 varchar(255),
    surname             varchar(255),
    primary key (id)
);
alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table accepted
    add constraint FK50c8yu84p4lo3o5r3kikansm0 foreign key (premise_offer_id) references premise_offers;
alter table accepted
    add constraint FK4v9f0xqpaff4i8j7dpovwio9s foreign key (searching_offer_id) references searching_offers;
alter table hosts
    add constraint FKim6h7w6syp1n0vvcrycxggs6x foreign key (user_id) references users;
alter table offer_images
    add constraint FKlu4uesoptoqj4ewg6avei7vlr foreign key (premise_offer_id) references premise_offers;
alter table pending
    add constraint FKfn3n711mqf4j5vfi4axglf5io foreign key (premise_offer_id) references premise_offers;
alter table pending
    add constraint FKqysyyyhllfkr2wydh379vkswp foreign key (searching_offer_id) references searching_offers;
alter table premise_offers
    add constraint FKnd8gs0v11mcxldjb1l9q11362 foreign key (host_user_id) references hosts;
alter table refugees
    add constraint FKbqu1sl8mxp4n3w16hy005vjf6 foreign key (user_id) references users;
alter table searching_offers
    add constraint FKmahuk20by1bgbgoi8t2h6oito foreign key (refugee_user_id) references refugees;
alter table searching_people
    add constraint FKscfc6sbucsrak13eprgw6iday foreign key (searching_offer_id) references searching_offers;
