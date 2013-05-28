drop table if exists company;

drop table if exists computer;

create table company (
  id                        int not null,
  name                      varchar(255) not null,
  constraint pk_company primary key (id))
;

create table computer (
  id                        int not null,
  name                      varchar(255) not null,
  introduced                date,
  discontinued              date,
  company_id                int,
  constraint pk_computer primary key (id))
;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;