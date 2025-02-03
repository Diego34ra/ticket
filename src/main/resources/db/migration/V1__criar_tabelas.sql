CREATE TABLE `tb_usuario` (
  `usuario_id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `tipo_usuario` enum('ADMINISTRADOR','CLIENTE','FUNCIONARIO','GERENTE') DEFAULT NULL,
  PRIMARY KEY (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_telefone` (
  `telefone_id` bigint NOT NULL AUTO_INCREMENT,
  `ddd` varchar(255) DEFAULT NULL,
  `ddi` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`telefone_id`),
  KEY `FKlf26qdq78s365njl6nfj1ncn2` (`usuario_id`),
  CONSTRAINT `FKlf26qdq78s365njl6nfj1ncn2` FOREIGN KEY (`usuario_id`) REFERENCES `tb_usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_categoria` (
  `categoria_id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`categoria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_departamento` (
  `departamento_id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `horario_funcionamento` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `gerente_usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`departamento_id`),
  UNIQUE KEY `UKok18ntuwdnvc6pwakd3nqls0f` (`gerente_usuario_id`),
  CONSTRAINT `FKtddn9bdksl809yuqabe10b613` FOREIGN KEY (`gerente_usuario_id`) REFERENCES `tb_usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_regra_prioridade` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `prioridade` enum('ALTA','BAIXO','MUITO_ALTA','NORMAL') DEFAULT NULL,
  `categoria_categoria_id` bigint DEFAULT NULL,
  `departamento_departamento_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5pyijvct3x2ih2ybjp1xurxq` (`categoria_categoria_id`),
  KEY `FKaq1ok0h8gto7pffwb4yutk8m4` (`departamento_departamento_id`),
  CONSTRAINT `FKaq1ok0h8gto7pffwb4yutk8m4` FOREIGN KEY (`departamento_departamento_id`) REFERENCES `tb_departamento` (`departamento_id`),
  CONSTRAINT `FKe5pyijvct3x2ih2ybjp1xurxq` FOREIGN KEY (`categoria_categoria_id`) REFERENCES `tb_categoria` (`categoria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_ticket` (
  `ticket_id` bigint NOT NULL AUTO_INCREMENT,
  `data_criacao` datetime(6) DEFAULT NULL,
  `data_maxima_resolucao` datetime(6) DEFAULT NULL,
  `data_modificacao` datetime(6) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `prioridade` enum('ALTA','BAIXO','MUITO_ALTA','NORMAL') DEFAULT NULL,
  `status` enum('ABERTO','EM_ANDAMENTO','EM_ESPERA','FINALIZADO') DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `categoria_categoria_id` bigint DEFAULT NULL,
  `cliente_usuario_id` bigint DEFAULT NULL,
  `departamento_departamento_id` bigint DEFAULT NULL,
  `responsavel_usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `FKkleejqesdnmcgfa23dj9knny2` (`categoria_categoria_id`),
  KEY `FKnano81m2qxa6grktnm2rrxvw0` (`cliente_usuario_id`),
  KEY `FKespx7fs5x6m6ni6apx1s7r7i0` (`departamento_departamento_id`),
  KEY `FKri0w6shaglbu5ttd26y1194mn` (`responsavel_usuario_id`),
  CONSTRAINT `FKespx7fs5x6m6ni6apx1s7r7i0` FOREIGN KEY (`departamento_departamento_id`) REFERENCES `tb_departamento` (`departamento_id`),
  CONSTRAINT `FKkleejqesdnmcgfa23dj9knny2` FOREIGN KEY (`categoria_categoria_id`) REFERENCES `tb_categoria` (`categoria_id`),
  CONSTRAINT `FKnano81m2qxa6grktnm2rrxvw0` FOREIGN KEY (`cliente_usuario_id`) REFERENCES `tb_usuario` (`usuario_id`),
  CONSTRAINT `FKri0w6shaglbu5ttd26y1194mn` FOREIGN KEY (`responsavel_usuario_id`) REFERENCES `tb_usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_comentario` (
  `comentario_id` bigint NOT NULL AUTO_INCREMENT,
  `conteudo` varchar(255) DEFAULT NULL,
  `autor_usuario_id` bigint DEFAULT NULL,
  `ticket_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comentario_id`),
  KEY `FKnl2o22vuqqngt7aehu4mwkhtp` (`autor_usuario_id`),
  KEY `FK9cytagr4fwkyp9bfuxtrvned0` (`ticket_id`),
  CONSTRAINT `FK9cytagr4fwkyp9bfuxtrvned0` FOREIGN KEY (`ticket_id`) REFERENCES `tb_ticket` (`ticket_id`),
  CONSTRAINT `FKnl2o22vuqqngt7aehu4mwkhtp` FOREIGN KEY (`autor_usuario_id`) REFERENCES `tb_usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_anexo` (
  `anexo_id` bigint NOT NULL AUTO_INCREMENT,
  `download_url` varchar(255) DEFAULT NULL,
  `nome_arquivo` varchar(255) DEFAULT NULL,
  `tamanho` varchar(255) DEFAULT NULL,
  `tipo_arquivo` varchar(255) DEFAULT NULL,
  `comentario_comentario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`anexo_id`),
  KEY `FK6aumbuv4ra5pi11ud926jeg0b` (`comentario_comentario_id`),
  CONSTRAINT `FK6aumbuv4ra5pi11ud926jeg0b` FOREIGN KEY (`comentario_comentario_id`) REFERENCES `tb_comentario` (`comentario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_ticket_historico` (
  `ticket_historico_id` bigint NOT NULL AUTO_INCREMENT,
  `campo` varchar(255) DEFAULT NULL,
  `data_alteracao` datetime(6) DEFAULT NULL,
  `novo_valor` varchar(255) DEFAULT NULL,
  `ultimo_valor` varchar(255) DEFAULT NULL,
  `agente_usuario_id` bigint DEFAULT NULL,
  `ticket_ticket_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ticket_historico_id`),
  KEY `FKiaq4j8md22pwhfbivmrcpp4hp` (`agente_usuario_id`),
  KEY `FKbw5hc2syfuk6xs60u74wp81bc` (`ticket_ticket_id`),
  CONSTRAINT `FKbw5hc2syfuk6xs60u74wp81bc` FOREIGN KEY (`ticket_ticket_id`) REFERENCES `tb_ticket` (`ticket_id`),
  CONSTRAINT `FKiaq4j8md22pwhfbivmrcpp4hp` FOREIGN KEY (`agente_usuario_id`) REFERENCES `tb_usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_registro_trabalho` (
  `registro_trabalho_id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `duration` decimal(21,0) DEFAULT NULL,
  `horario_fim` datetime(6) DEFAULT NULL,
  `horario_inicio` datetime(6) DEFAULT NULL,
  `agente_usuario_id` bigint DEFAULT NULL,
  `ticket_ticket_id` bigint DEFAULT NULL,
  PRIMARY KEY (`registro_trabalho_id`),
  KEY `FKs47hjqjpcme4uigjbg17w3d7` (`agente_usuario_id`),
  KEY `FKsx8pcvwnxfmp3ors05bpeuruj` (`ticket_ticket_id`),
  CONSTRAINT `FKs47hjqjpcme4uigjbg17w3d7` FOREIGN KEY (`agente_usuario_id`) REFERENCES `tb_usuario` (`usuario_id`),
  CONSTRAINT `FKsx8pcvwnxfmp3ors05bpeuruj` FOREIGN KEY (`ticket_ticket_id`) REFERENCES `tb_ticket` (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
