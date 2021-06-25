-- auto-generated definition
create table m_chapter
(
    id             bigserial    not null
        constraint m_chapter_pk
            primary key,
    number_chapter varchar(30)  not null,
    name_chapter   varchar(230) not null,
    text_chapter   varchar      not null,
    id_fun_fiction bigint       not null
        constraint m_chapter_m_fun_fiction__fk
            references m_fun_fiction
);

alter table m_chapter
    owner to postgres;

-- auto-generated definition
create table m_comments
(
    id             bigserial    not null
        constraint m_comments_pk
            primary key,
    text_comment   varchar(500) not null,
    created        date         not null,
    id_user        bigint       not null
        constraint m_comments_m_user__fk
            references m_user,
    id_fun_fiction bigint       not null
        constraint m_comments_m_fun_fiction__fk
            references m_fun_fiction
);

alter table m_comments
    owner to postgres;
-- auto-generated definition
create table m_fun_fiction
(
    id                bigserial        not null
        constraint m_fun_fiction_pk
            primary key,
    name_fun          varchar(60)      not null,
    short_description varchar(500)     not null,
    rating            double precision not null,
    likes             bigint           not null,
    created           date             not null,
    id_genre          bigint           not null
        constraint m_fun_fiction_m_genre__fk
            references m_genre,
    id_user           bigint
        constraint m_fun_fiction_m_user__fk
            references m_user
);

alter table m_fun_fiction
    owner to postgres;

-- auto-generated definition
create table m_genre
(
    id          bigserial    not null
        constraint m_genre_pk
            primary key,
    type_genre  varchar(50)  not null,
    description varchar(250) not null
);

alter table m_genre
    owner to postgres;

-- auto-generated definition
create table m_role
(
    id        bigserial   not null
        constraint m_role_pk
            primary key,
    type_role varchar(15) not null
);

alter table m_role
    owner to postgres;

-- auto-generated definition
create table m_tags
(
    id        bigserial   not null
        constraint m_tags_pk
            primary key,
    type_tags varchar(50) not null
);

alter table m_tags
    owner to postgres;

-- auto-generated definition
create table m_tags_fiction
(
    id_fun_fiction bigint not null
        constraint m_tags_fiction_m_fun_fiction__fk
            references m_fun_fiction,
    id_tags        bigint not null
        constraint m_tags_fiction_m_tags__fk
            references m_tags
);

alter table m_tags_fiction
    owner to postgres;

-- auto-generated definition
create table m_user
(
    id           bigserial   not null
        constraint m_user_pk
            primary key,
    email        varchar(60) not null,
    password     varchar     not null,
    login        varchar(50) not null,
    status       varchar(40) not null,
    created      date        not null,
    updated      date        not null,
    name_user    varchar(50) not null,
    surname_user varchar(40) not null,
    background   varchar,
    language     varchar
);

alter table m_user
    owner to postgres;

-- auto-generated definition
create table m_user_role
(
    id_user bigint not null
        constraint m_user_role_m_user__fk
            references m_user,
    id_role bigint not null
        constraint m_user_role_m_role__fk
            references m_role
);

alter table m_user_role
    owner to postgres;


