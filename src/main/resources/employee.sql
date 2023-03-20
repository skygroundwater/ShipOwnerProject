insert into employee(first_name, last_name, gender, age) values ('Oleg', 'Metelev', 'male', 27);
insert into employee(first_name, last_name, gender, age) values ('Starichok', 'Dedushkov', 'male', 62);
insert into employee(first_name, last_name, gender, age) values ('Babulya', 'Babulnaya', 'female', 89);
insert into employee(first_name, last_name, gender, age) values ('Napoleon', 'Bonaparte', 'male', 27);

select first_name as Имя, last_name as Фамилия from employee;

select * from employee where age<30 or age>50
order by first_name desc, last_name;

select * from employee 
where age between 30 and 50
order by first_name desc, last_name;

select * from employee 
order by last_name desc;

select * from employee 
where length(first_name) > 4;

update employee set first_name='Napoleon', gender='male', last_name='Bonaparte' 
where last_name='Babulnaya';

select first_name as ИМЯ_РАБОТНИКА, 
sum(age) as СУММА_ВСЕХ_ВОЗРАСТОВ_РАБОТНИКОВ
from employee group by ИМЯ_РАБОТНИКА;

delete from employee where first_name='Oleg' and age=27;

select first_name as ИМЯ, age as ВОЗРАСТ
from employee where age=(select min(age) from employee);

select first_name as ИМЯ_РАБОТНИКА, max(age) as МАКС_ВОЗРАСТ
from employee group by ИМЯ_РАБОТНИКА having count(first_name)>1;
