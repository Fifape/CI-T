 -- INSERT USERS_BASE
INSERT INTO USER_BASES (location) VALUES ('Campinas'), ('São Paulo'), ('Belo Horizonte'), ('Curitiba');

-- INSERT USERS
INSERT INTO USERS (name, username, password, creation, user_base_id) VALUES
  ('Yusuke Urameshi', 'yurameshi', '$2a$10$TDZocJJFW8W0CpplJKf5VO3R85W4p0ZPfJ7UlOMb7gqbTS42zchJC', {ts '2023-01-12 15:00:00.000'}, 1),
  ('Kazuma Kuwabara', 'kkuwabara', '$2a$10$TDZocJJFW8W0CpplJKf5VO3R85W4p0ZPfJ7UlOMb7gqbTS42zchJC', {ts '2023-01-12 15:00:00.000'}, 1),
  ('Youko Kurama', 'ykurama', '$2a$10$TDZocJJFW8W0CpplJKf5VO3R85W4p0ZPfJ7UlOMb7gqbTS42zchJC', {ts '2023-01-12 15:00:00.000'}, 3);

-- INSERT VACCINES
INSERT INTO VACCINES (name, description, creation) VALUES
 ('GRIPE - ADULTO 3', 'VACINA DA GRIPE PARA MAIORES DE 4 ANOS', {ts '2021-02-17 12:02:44'}),
 ('GRIPE - INFANTIL 2', 'VACINA DA GRIPE PARA MENORES DE 4 ANOS', {ts '2021-02-17 12:02:44'}),
 ('COVID-19', 'VACINA DA COVID-19 PARA MAIORES DE 12 ANOS', {ts '2021-02-17 12:02:44'}),
 ('SARAMPO - INFANTIL', 'VACINA DO SARAMPO PARA MENORES DE 18 ANOS', {ts '2021-02-17 12:02:44'});

-- INSERT CAMPAIGNS
INSERT INTO CAMPAIGN_STATUSES (status) VALUES ('Cancelada'), ('Adesão'), ('Troca de titularidade'), ('Edição de Paciente'), ('Finalizada');

INSERT INTO CAMPAIGNS (campaign_status_id ,name,start_date, end_date, creation) VALUES
 (1, 'CAMPANHA COVID-19', '2022-02-01 12:02:44', '2021-03-30 12:02:44', '2021-02-20 12:02:44.000'),
 (2, 'GRIPE ADULTO E INFANTIL 2022', '2022-01-03 12:02:44', '2022-02-17 12:02:44', '2022-01-17 12:02:44.000'),
 (4, 'GRIPE ADULTO E INFANTIL 2021', '2021-01-05 12:02:44', '2021-02-05 12:02:44', '2021-01-01 12:02:44.000');

INSERT INTO VACCINES_CAMPAIGNS (campaign_id, vaccine_id) VALUES (1, 3), (2, 1), (2, 2), (3, 1), (3,2);

-- INSERT DOSES
INSERT INTO DOSE_REQUESTS (campaign_id, vaccine_id, requester_user_id, creation,patient_name, transfer_request) VALUES
 (2, 1, 1, {ts '2021-01-01 12:02:44'}, 'Monkey D. Luffy', FALSE),
 (2, 1, 1, {ts '2021-01-01 12:02:44'}, 'Roronoa Zoro', FALSE),
 (2, 1, 1, {ts '2021-01-01 12:02:44'}, 'Gol D. Roger', FALSE),
 (2, 1, 2, {ts '2021-01-01 12:02:44'}, 'Pikachu da Silva', FALSE),
 (3, 1, 2, {ts '2021-01-01 12:02:44'}, 'Pikachu da Silva', FALSE),
 (1, 3, 3, {ts '2021-01-01 12:02:44'}, 'Saitama Oliveira', FALSE),
 (2, 1, 3, {ts '2021-01-01 12:02:44'}, 'Saitama Oliveira', FALSE),
 (2, 1, 3, {ts '2021-01-01 12:02:44'}, 'Garou Alencar', FALSE);

 INSERT INTO DOSE_TRANSFER_REQUEST_STATUSES (status) VALUES ('Em aberto'), ('Cancelada'), ('Recusada'), ('Aceita');

