insert into users (last_name, first_name, patronymic, date_of_birth, email, phone_number) values
('Иванов', 'Иван', 'Иванович', '1964-10-5', 'user1@user.ru', '+79231234567'),
('Петров', 'Пётр', 'Петрович', '1987-6-15', 'user2@user.ru', '+79232345678'),
('Сидоров', 'Олег', 'Николаевич', '1984-1-22', 'admin@user.ru', '+79233456789')
on conflict do nothing;

