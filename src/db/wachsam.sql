CREATE TABLE `db` (
  `id` bigint(20) NOT NULL,
  `size` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `lugar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `nombreEn` varchar(255) DEFAULT NULL,
  `padre1_id` bigint(20) DEFAULT NULL,
  `padre2_id` bigint(20) DEFAULT NULL,
  `padre3_id` bigint(20) DEFAULT NULL,
  `latitud` varchar(255) DEFAULT NULL,
  `longitud` varchar(255) DEFAULT NULL,
  `nivel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_padre1_idx` (`padre1_id`),
  KEY `FK_padre2_idx` (`padre2_id`),
  KEY `FK_padre3_idx` (`padre3_id`),
  CONSTRAINT `FK_padre1` FOREIGN KEY (`padre1_id`) REFERENCES `lugar` (`id`),
  CONSTRAINT `FK_padre2` FOREIGN KEY (`padre2_id`) REFERENCES `lugar` (`id`),
  CONSTRAINT `FK_padre3` FOREIGN KEY (`padre3_id`) REFERENCES `lugar` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE `peligro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `nombreEn` varchar(255) DEFAULT NULL,
  `categoria` int(11) DEFAULT NULL,
  `damage` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


CREATE TABLE `alert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `link1` varchar(500) DEFAULT NULL,
  `link2` varchar(500) DEFAULT NULL,
  `link3` varchar(500) DEFAULT NULL,
  `texto` varchar(1000) DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `lugar` varchar(255) DEFAULT NULL,
  `fechaPub` datetime DEFAULT NULL,
  `peligro_id` bigint(20) DEFAULT NULL,
  `lugarObj_id` bigint(20) DEFAULT NULL,
  `caducidad` decimal(10,0) DEFAULT NULL,
  `fuente_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3C6993C66E5E8B6` (`lugarObj_id`),
  KEY `FK3C6993CF194B46F` (`peligro_id`),
  CONSTRAINT `FK_Lugar` FOREIGN KEY (`lugarObj_id`) REFERENCES `lugar` (`id`),
  CONSTRAINT `FK_Peligro` FOREIGN KEY (`peligro_id`) REFERENCES `peligro` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alias` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `nombre` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `nombreEn` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `descripcion` varchar(250) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `nombreEn_UNIQUE` (`nombreEn`),
  UNIQUE KEY `alias_UNIQUE` (`alias`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;



CREATE TABLE `data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` int(11) DEFAULT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `tipoValor` int(11) NOT NULL,
  `tag1_id` int(11) DEFAULT NULL,
  `tag2_id` int(11) DEFAULT NULL,
  `tag3_id` int(11) DEFAULT NULL,
  `lugarId` int(11) DEFAULT NULL,
  `subjectId` int(11) DEFAULT NULL,
  `eventoId` int(11) DEFAULT NULL,
  `sitioId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE `viajarseguro`.`sitio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `nombreEn` VARCHAR(100) NULL,
  `direccion` VARCHAR(250) NULL,
  `tipo` INT NOT NULL,
  `texto` VARCHAR(500) NULL,
  `textoEn` VARCHAR(500) NULL,
  `valoracion` INT NULL,
  `lugarObj_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
  CREATE TABLE `airport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `city` varchar(55) DEFAULT NULL,
  `country` varchar(55) DEFAULT NULL,
  `IATA_FAA` varchar(5) DEFAULT NULL,
  `ICAO` varchar(5) DEFAULT NULL,
  `latitud` varchar(10) DEFAULT NULL,
  `logitud` varchar(10) DEFAULT NULL,
  `altitud` varchar(10) DEFAULT NULL,
  `timezone` varchar(3) DEFAULT NULL,
  `DST` varchar(1) DEFAULT NULL,
  `TZ` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE `fuente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `fiabilidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `factor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `nombreEn` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  `texto` varchar(500) COLLATE latin1_spanish_ci DEFAULT NULL,
  `textoEn` varchar(500) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `password` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `email` varchar(50) COLLATE latin1_spanish_ci NOT NULL, 
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE `permiso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `objeto` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `accion` int(11) NOT NULL,
  `filtro` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  `filtroFlag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE `usuario_permiso` (
  `usuario_id` int(11) NOT NULL,
  `permisos_id` int(11) NOT NULL,
  KEY `usuario_FK_idx` (`usuario_id`),
  KEY `permiso_FK_idx` (`usuario_id`,`permisos_id`),
  KEY `FK_permiso_2_idx` (`permisos_id`),
  CONSTRAINT `FK_permiso_2` FOREIGN KEY (`permisos_id`) REFERENCES `permiso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_usuario_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE `operationlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `objeto` varchar(100) NOT NULL,
  `objetoId` int(11) DEFAULT NULL,
  `operation` varchar(100) NOT NULL,
  `usuarioid` int(11) NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

  
  CREATE TABLE `data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(500) DEFAULT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `tipoValor` int(11) NOT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `objetoid` int(11) DEFAULT NULL,
  `connecttoid` int(11) DEFAULT NULL,
  `objetotipo` int(2) DEFAULT NULL,
  `objetoconnectedtipo` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK2EEFAA76382C92` (`tag_id`),
  CONSTRAINT `FK2EEFAA76382C92` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `riesgo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lugar_id` int(11) NOT NULL,
  `peligro_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE `viajarseguro`.`tipositio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(250) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('9', 'Hospital');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('1', 'Embajada');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('2', 'CamaraHiperbárica');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('3', 'Playa');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('4', 'Cueva');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('5', 'Pico');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('6', 'Mercado');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('7', 'Carcel');
INSERT INTO `viajarseguro`.`tipositio` (`id`, `nombre`) VALUES ('8', 'Campo de Refugiados');

-- cambio de id
update sitio set tipo=9 where tipo=0;
----
ALTER TABLE `viajarseguro`.`sitio` 
CHANGE COLUMN `tipo` `tipo_id` INT(11) NOT NULL ;

----
ALTER TABLE `viajarseguro`.`operationlog` 
ADD COLUMN `stamp` VARCHAR(100) NULL AFTER `usuarioId`;


--------------------1.2.0
CREATE TABLE `viajarseguro`.`mitigacion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `peligro_id` INT NOT NULL,
  `factor_id` INT NOT NULL,
  `value` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

  
CREATE TABLE `recurso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `uri` varchar(250) DEFAULT NULL,
  `s3Bucket` varchar(150) DEFAULT NULL,
  `s3Publico` int(11) DEFAULT NULL,
  `formato` varchar(100) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



