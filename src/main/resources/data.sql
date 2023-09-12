INSERT INTO users(username, password, email, role, enabled) VALUES ('admin', '$2a$10$MiEHKF9DzlQHZMCJvZK4NuxnNkWk26IgXLSTjmgwpPObmrBE1HnSC', 'admin@email.com', 'ADMIN', true)
INSERT INTO users(username, password, email, role, enabled) VALUES ('user', '$2a$10$MiEHKF9DzlQHZMCJvZK4NuxnNkWk26IgXLSTjmgwpPObmrBE1HnSC', 'user@email.com', 'ADMIN', true)

INSERT INTO auction (initial_value,name,opening_date,user_user_id) values (100,'Auction 1','2023-11-24',1)
INSERT INTO auction (initial_value,name,opening_date,user_user_id) values (200,'Auction 2','2023-10-25',2)