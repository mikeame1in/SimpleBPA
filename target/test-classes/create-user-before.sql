delete from user_roles;
delete from employees;

insert into employees(id, email, first_name, last_name, middle_name, phone, active, password, username)
values
        (1, 'test1@mail.com', 'andrew', 'sokolof', 'sergeevich', '88888', true, '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', 'dru'),
        (2, 'test1@mail.com', 'mike', 'orlov', 'alekseevich','88888', true, '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', 'mike');

insert into user_roles(user_id, roles)
values
          (1, 'ADMIN'), (1, 'USER'),
          (2, 'USER');