insert into users (last_name, first_name, patronymic, date_of_birth, email, phone_number, image_link) values
('Иванов', 'Иван', 'Иванович', '1964-10-5', 'user1@user.ru', '+79231234567', 'http://radical-mag.com/wp-content/uploads/2023/02/BMW-M3-E30-22-e1677441967276-1536x837.jpeg'),
('Петров', 'Пётр', 'Петрович', '1987-6-15', 'user2@user.ru', '+79232345678', 'https://media.springernature.com/lw1000/springer-cms/rest/v1/img/18802626/v1/4by3?as=jpg'),
('Сидоров', 'Олег', 'Николаевич', '1984-1-22', 'admin@user.ru', '+79233456789', 'null')
on conflict do nothing;

