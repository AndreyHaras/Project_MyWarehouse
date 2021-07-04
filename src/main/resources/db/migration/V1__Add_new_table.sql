
CREATE TABLE банк (
                      id                 serial primary key,
                      код_банка          bigint not null unique,
                      наименование_банка varchar(120) not null unique
);

CREATE TABLE мат_ответственность (
                                     id             serial primary key,
                                     код_мо         integer not null unique
);

CREATE TABLE кладовщик (
                           id              serial primary key,
                           код_сотрудника  integer not null unique,
                           фамилия         varchar(60) not null,
                           имя             varchar(60) not null,
                           отчество        varchar(60) not null,
                           id_ответственности bigint not null
                               constraint кладовщик_id_ответственности_fkey1
                                   references мат_ответственность (id)

);

CREATE TABLE контрагент (
                            id                       serial primary key,
                            код_контрагента          bigint not null unique,
                            наименование_организации varchar(120) not null,
                            код_плательщика          bigint      not null,
                            расчетный_счет           bigint      not null unique,
                            id_банка                 bigint      not null
                                constraint контрагент_id_банка_fkey
                                    references банк (id)
);

CREATE TABLE счет_организации (
                                  id              serial primary key,
                                  наименование    varchar(120) not null,
                                  код_плательщика bigint      not null,
                                  расчетный_счет  bigint      not null unique,
                                  id_банка        bigint      not null
                                      constraint счет_организации_id_банка_fkey
                                          references банк (id)
);

CREATE TABLE операции (
                          id              serial primary key,
                          вид_операции varchar(50) not null unique
);

CREATE TABLE накладная (
                           id               serial primary key,
                           номер_накладной  bigint     not null unique,
                           дата_накладной   date        not null,
                           контрагент_id    bigint     not null
                               constraint накладная_контрагент_id_fkey
                                   references контрагент (id),
                           операция_id      bigint not null
                               constraint накладная_операция_id_fkey
                                   references операции (id),
                           сотрудник_id     integer     not null
                               constraint накладная_сотрудник_id_fkey
                                   references кладовщик (id)
);

CREATE TABLE матрица_товара(
                               id                  serial primary key,
                               код_товара          integer not null,
                               наименование_товара varchar(120) not null
);

CREATE TABLE склад (
                       id                  serial primary key,
                       накладная_id        bigint not null
                           constraint склад_накладная_id_fkey
                               references накладная(id),
                       товар_id            bigint not null
                           constraint склад_товар_id_fkey
                               references матрица_товара(id),
                       цена                numeric(12, 2) not null,
                       количество          numeric(6, 2) not null
);

CREATE TABLE пользователи (
                              id                  serial primary key,
                              имя_пользователя    varchar(255) not null unique,
                              пароль_пользователя varchar(255) not null unique,
                              роль                varchar(255) not null,
                              доступ              boolean not null
);