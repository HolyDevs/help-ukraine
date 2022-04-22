INSERT INTO users(id, name, surname, account_type, birth_date, email, hashed_password, is_account_verified,
                  phone_number, sex)
VALUES
(123, 'Jan', 'Lokalny', 'REFUGEE', '1988-01-08', 'jan.lokalny@gmail.com','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'MALE'),
(124, 'Anna', 'NaWindowsie', 'REFUGEE', '1988-01-08', 'anna.nawindowsie@gmail.com','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'FEMALE');

INSERT INTO hosts(user_id)
VALUES(123), (124);

INSERT INTO refugees(user_id) VALUES(123);

-- password is encoded 'aaa'
