insert into users (id, username, password, email, year_of_birth)
values (1, 'admin', '$2a$12$5z.iZRu13RUFBJMApZiRAOytOr5uD7VXcRMWps.eFo0kaqRsluph6', 'admin@admin', 20);
insert into users (id, username, password, email, year_of_birth)
values (2, 'user', '$2a$12$51qlZdbxPRnFczlqUS0dwO5bAS57tuZeV8/XIXJHhBxV78WahX35C', 'user@user', 5);

insert into roles (id, name) value (1, 'ROLE_ADMIN');
insert into roles (id, name) value (2, 'ROLE_USER');

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (1, 2);
insert into users_roles (user_id, role_id) values (2, 2);