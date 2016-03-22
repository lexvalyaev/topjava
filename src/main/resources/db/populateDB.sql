DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2016-03-18 8:00:00', 'Завтрак', 400, 100000),
  ('2016-03-18 12:00:00', 'Обед', 400, 100000),
  ('2016-03-18 19:00:00', 'Ужин', 300, 100000),
  ('2016-03-19 8:00:00', 'Завтрак', 500, 100000),
  ('2016-03-19 12:00:00', 'Обед', 500, 100000),
  ('2016-03-19 19:00:00', 'Ужин', 400, 100000),
  ('2016-03-19 10:00:00', 'Завтрак админа', 500, 100001),
  ('2016-03-19 16:00:00', 'Обед админа', 800, 100001),
  ('2016-03-19 20:00:00', 'Ужин админа', 600, 100001)