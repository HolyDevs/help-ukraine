INSERT INTO users(id, name, surname, account_type, birth_date, email, hashed_password, is_account_verified,
                  phone_number, sex)
VALUES
(123, 'Jan', 'Uciekinier', 'REFUGEE', '1988-01-08', 'jan.uciekinier@gmail.com','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'MALE'),
(124, 'Anna', 'NaWindowsie', 'HOST', '1988-01-08', 'anna.nawindowsie@gmail.com','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'FEMALE'),
(125, 'Jan', 'Pomocny', 'HOST', '1988-01-08', 'jan.pomocny@gmail.com','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'MALE'),
(126, 'Michał', 'Kuśmidrowicz', 'HOST', '1988-01-08', 'michal.kusmidrowicz@gmail.com','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'MALE'),
(127, 'Michał', 'Nowak', 'REFUGEE', '1988-01-08', 'michal.kusmidrowicz@wp.pl','$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.', true, '666-666-666', 'MALE');

INSERT INTO hosts(user_id)
VALUES(125), (124), (126);

INSERT INTO refugees(user_id) VALUES(123), (127);

INSERT INTO premise_offers(id, host_user_id, city, street, house_number, postal_code, people_to_take, bathrooms, kitchens, bedrooms, animals_allowed,
                           wheelchair_friendly, smoking_allowed, from_date, to_date, active, verified, description)
VALUES
(221, 126, 'Poznan', 'Akacjowa', '1', '90-000', 5, 1, 1, 1, true, true, true, '2022-01-01', '2025-01-01', true, true, 'Obecnie jako sztab kryzysowy skutecznie wspiera, koordynuje i bezpośrednio pomaga uchodźcom z Ukrainy i organizuje pomoc docelową dla Ukrainy i obronności'),
(222, 124, 'Lodz', 'Akacjowa', '1', '90-000', 4, 1, 1, 1, true, true, true, '2022-01-01', '2025-01-01', true, true, 'Obecnie jako sztab kryzysowy skutecznie wspiera, koordynuje i bezpośrednio pomaga uchodźcom z Ukrainy i organizuje pomoc docelową dla Ukrainy i obronności'),
(223, 124, 'Warsaw', 'Akacjowa', '1', '90-000', 3, 1, 1, 1, true, true, true, '2022-01-01', '2025-01-01', true, true, 'Obecnie jako sztab kryzysowy skutecznie wspiera, koordynuje i bezpośrednio pomaga uchodźcom z Ukrainy i organizuje pomoc docelową dla Ukrainy i obronności'),
(224, 125, 'Wroclaw', 'Akacjowa', '1', '90-000', 3, 1, 1, 1, false, true, true, '2022-01-01', '2025-01-01', true, true, 'Obecnie jako sztab kryzysowy skutecznie wspiera, koordynuje i bezpośrednio pomaga uchodźcom z Ukrainy i organizuje pomoc docelową dla Ukrainy i obronności'),
(225, 125, 'Cracow', 'Akacjowa', '1', '90-000', 3, 1, 1, 1, false, true, true, '2022-01-01', '2025-01-01', true, true, 'Obecnie jako sztab kryzysowy skutecznie wspiera, koordynuje i bezpośrednio pomaga uchodźcom z Ukrainy i organizuje pomoc docelową dla Ukrainy i obronności'),
(226, 126, 'Rzeszow', 'Akacjowa', '1', '90-000', 7, 1, 1, 1, true, true, true, '2022-01-01', '2025-01-01', true, true, 'Obecnie jako sztab kryzysowy skutecznie wspiera, koordynuje i bezpośrednio pomaga uchodźcom z Ukrainy i organizuje pomoc docelową dla Ukrainy i obronności');


INSERT INTO offer_images(id, image_location, premise_offer_id)
VALUES
(331, 'https://ukrainskidom.pl/wp-content/uploads/2022/02/1-1-1600x900.png', 221),
(332, 'https://ocalenie.org.pl/wp-content/themes/wp-bootstrap-starter-child/images/ocalenie_logo_kolor.png', 222),
(333, 'data:image/png;base64,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', 223),
(334, 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Family_trip_to_Oregon.jpg/1200px-Family_trip_to_Oregon.jpg', 224),
(335, 'https://ocalenie.org.pl/wp-content/uploads/2018/11/m07.png', 225),
(336, 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Happy_family_%281%29.jpg/1200px-Happy_family_%281%29.jpg?20120321172928', 226);

INSERT INTO searching_offers(id, additional_info, animals_involved, user_moving_issues, preferred_location,
                             range_from_preferred_location_in_km, refugee_user_id)
VALUES(441, 'I have diabetes', false, false, 'Warsaw', 20, 123),
      (442, 'I have diabetes', false, false, 'Warsaw', 20, 127);

INSERT INTO searching_people(id, birth_date, moving_issues, name, searching_offer_id,
                             sex, surname)
VALUES (551, '1997-01-01', false, 'Andrzej', 441, 'MALE', 'Uciekienier'),
       (552, '1999-01-01', true, 'Joanna', 441, 'FEMALE', 'Uciekienier'),
       (553, '1997-01-01', false, 'Janusz', 442, 'MALE', 'Nowak'),
       (554, '1999-01-01', true, 'Maria', 442, 'FEMALE', 'Nowak');


