use ticket;

INSERT INTO tb_usuario (usuario_id, cpf, email, nome, senha, tipo_usuario)
VALUES ('1', '135.458.820-71', 'gerente@mail.com', 'Usuário Gerente', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'GERENTE'),
       ('2', '151.954.220-87', 'gerente2@mail.com', 'Usuário Gerente 2', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'GERENTE'),
	   ('3', '347.460.380-90', 'cliente@mail.com', 'Usuário Cliente', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'CLIENTE'),
       ('4', '677.808.750-16', 'cliente2@mail.com', 'Usuário Cliente 2', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'CLIENTE'),
       ('5', '233.016.360-63', 'cliente3@mail.com', 'Usuário Cliente 3', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'CLIENTE'),
       ('6', '000.613.240-51', 'funcionario@mail.com', 'Usuário Funcionario', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'FUNCIONARIO'),
       ('7', '285.619.370-68', 'funcionario2@mail.com', 'Usuário Funcionario2', '$2a$10$FuRprba111BXOLzclvPb5.L8DS2IHEVDAScgndL6pmvMdlrJVsEze', 'FUNCIONARIO');