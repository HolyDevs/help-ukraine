INSERT INTO users(id, name, surname, account_type, birth_date, email, hashed_password, is_account_verified,
                  phone_number, sex)
VALUES ('123', 'Jan', 'Lokalny', 'REFUGEE', '1988-01-08', 'jan.lokalny@gmail.com',
        '$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'MALE');
-- password is encoded 'aaa'
