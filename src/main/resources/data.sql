-- ========================================================
-- Script DML: Carga Inicial de Dados (data.sql)
-- Atende à Regra de Negócio 01 (RN01): Mínimo 3 registros por tabela
-- ========================================================

-- 1. Usuários do Sistema (RN01)
-- Senhas hasheadas com BCrypt (senha original de teste: '123456')
-- Hash real verificado de '123456': $2a$10$/bUPr.kSNUAK2qcFmhFOKezKMNxe4BhXBwNTJGrLk8LJJO/iUr0Hm
INSERT INTO usuario (nome, login, senha_hash, ultimo_acesso) VALUES 
('Administrador do Sistema', 'admin', '$2a$10$/bUPr.kSNUAK2qcFmhFOKezKMNxe4BhXBwNTJGrLk8LJJO/iUr0Hm', CURRENT_TIMESTAMP),
('Carlos Atendente', 'carlos', '$2a$10$/bUPr.kSNUAK2qcFmhFOKezKMNxe4BhXBwNTJGrLk8LJJO/iUr0Hm', CURRENT_TIMESTAMP),
('Mariana Especialista', 'mariana', '$2a$10$/bUPr.kSNUAK2qcFmhFOKezKMNxe4BhXBwNTJGrLk8LJJO/iUr0Hm', CURRENT_TIMESTAMP);

-- 2. Clientes (RN01 e RN03)
-- CPFs armazenados com criptografia AES simétrica (Base64) conforme LGPD:
-- 'Yq0NTRV3SJFdecqi8dxt4Q==' decodifica para: 123.456.789-01
-- '5gvOxFchnO36DwY8vgK/9w==' decodifica para: 987.654.321-09
-- 'WxOLfRqzxP32Ew9X+yIYOA==' decodifica para: 456.789.012-34
INSERT INTO cliente (nome, cpf, telefone, email, endereco) VALUES 
('João da Silva', 'Yq0NTRV3SJFdecqi8dxt4Q==', '(11) 98888-1111', 'joao.silva@email.com', 'Rua das Palmeiras, 120 - São Paulo/SP'),
('Ana Paula Souza', '5gvOxFchnO36DwY8vgK/9w==', '(11) 97777-2222', 'ana.souza@email.com', 'Av. Paulista, 1500, Apto 42 - São Paulo/SP'),
('Roberto Santos', 'WxOLfRqzxP32Ew9X+yIYOA==', '(21) 96666-3333', 'roberto.santos@email.com', 'Rua Visconde de Pirajá, 300 - Rio de Janeiro/RJ');

-- 3. Equipamentos (RN01 e RN07)
-- Todos associados obrigatoriamente a um cliente previamente cadastrado
INSERT INTO equipamento (cliente_id, tipo, marca, modelo, numero_serie, defeito_declarado) VALUES 
(1, 'Notebook', 'Dell', 'Inspiron 15 3000', 'BR10293847', 'Aparelho superaquece e desliga repentinamente durante o uso'),
(2, 'Smartphone', 'Samsung', 'Galaxy S21 5G', 'SM90812345', 'Tela com listras verticais verdes após impacto leve'),
(3, 'Monitor', 'LG', 'Ultrawide 29" Full HD', 'LG29WK600', 'Não liga e emite estalos no conector de energia');

-- 4. Agendamentos de Atendimentos (RN01 e RN06)
-- Vinculados ao cliente e equipamento, com ordenação por data de agendamento
INSERT INTO agendamento (cliente_id, equipamento_id, data_agendamento, status, observacoes) VALUES 
(1, 1, '2026-11-20 09:30:00', 'AGENDADO', 'Troca de pasta térmica de prata e limpeza completa dos coolers'),
(2, 2, '2026-11-20 14:00:00', 'EM_ANALISE', 'Substituição do módulo de display frontal e teste de touch'),
(3, 3, '2026-11-21 10:15:00', 'AGENDADO', 'Diagnóstico da fonte primária de alimentação e barramento');
