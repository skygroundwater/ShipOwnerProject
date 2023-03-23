create table city(
                     id bigserial primary key,
                     name varchar
);

create table employee(
                         first_name varchar,
                         last_name varchar,
                         gender varchar
);

alter table employee
    add city_id int constraint city_id_fk
        references city;

insert into employee(first_name, last_name, gender) VALUES ('Oleg', 'Metelev', 'male');
insert into employee(first_name, last_name, gender) VALUES ('Arina', 'Rodionova', 'female');
insert into employee(first_name, last_name, gender) VALUES ('Koytus', 'Priemniy', 'male');
insert into employee(first_name, last_name, gender) VALUES ('Mikushez', 'Rodionova-Meteleva', 'female');

select * from employee;

insert into city(name) values ('Saint-Petersburg');
insert into city(name) values ('Samara');
insert into city(name) values ('Krasnodar');
insert into city(name) values ('Ekaterinburg');

update employee
set city_id=1 where first_name='Koytus';
update employee
set city_id=1 where first_name='Mikushez';
update employee
set city_id=2 where first_name='Arina';
update employee
set city_id=3 where first_name='Oleg';

select name, first_name, last_name
from city
         full join employee e on city.id = e.city_id;

select first_name, name
from employee
         full join city c on c.id = employee.city_id;

select name, first_name
from employee
         left join city c on c.id = employee.city_id;