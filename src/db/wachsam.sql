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
-------------------------
ALTER TABLE `viajarseguro`.`data` 
DROP FOREIGN KEY `FK2EEFAA76382C92`,
DROP FOREIGN KEY `FK2EEFAA76391550`,
DROP FOREIGN KEY `FK2EEFAA7638A0F1`;
ALTER TABLE `viajarseguro`.`data` 
DROP COLUMN `tag3_id`,
DROP COLUMN `tag2_id`,
CHANGE COLUMN `tag1_id` `tag_id` INT(11) NULL DEFAULT NULL ,
CHANGE COLUMN `lugarId` `objetoid` INT(11) NULL DEFAULT NULL ,
CHANGE COLUMN `subjectId` `connecttoid` INT(11) NULL DEFAULT NULL ,
CHANGE COLUMN `eventoId` `objettipo` INT(99) NULL DEFAULT NULL ,
CHANGE COLUMN `sitioId` `objetoconnected` INT(99) NULL DEFAULT NULL ,
DROP INDEX `FK2EEFAA76391550` ,
DROP INDEX `FK2EEFAA7638A0F1` ;
ALTER TABLE `viajarseguro`.`data` 
ADD CONSTRAINT `FK2EEFAA76382C92`
  FOREIGN KEY (`tag_id`)
  REFERENCES `viajarseguro`.`tag` (`id`);

  
  
  1. tag1_id --> tag_id
2.`objetoid` INT(11) NULL DEFAULT NULL
3.`connecttoid` INT(11) NULL DEFAULT NULL 
4. `objetotipo` INT(3) NULL DEFAULT NULL 
5. `objetoconnectedtipo` INT(3) NULL DEFAULT NULL 
6. ------------------------------

ALTER TABLE `viajarseguro`.`data` 
DROP FOREIGN KEY `FK2EEFAA76382C92`;
ALTER TABLE `viajarseguro`.`data` 
CHANGE COLUMN `tag1_id` `tag_id` INT(11) NULL DEFAULT NULL ,
ADD COLUMN `objetoid` INT(11) NULL AFTER `sitioId`,
ADD COLUMN `connecttoId` INT(11) NULL AFTER `objetoid`,
ADD COLUMN `objettipo` INT(11) NULL AFTER `connectToId`,
ADD COLUMN `objetoconnectedtipo` INT(11) NULL AFTER `objettipo`;
ALTER TABLE `viajarseguro`.`data` 
ADD CONSTRAINT `FK2EEFAA76382C92`
  FOREIGN KEY (`tag_id`)
  REFERENCES `viajarseguro`.`tag` (`id`);

  ------------------------
--LUGARES
 SELECT * FROM viajarseguro.data where lugarId is not null
 update data
set data.objetoid = data.lugarid,data.objetotipo = 2
where lugarId is not null and objetoid is null;
--PELIGROS
SELECT * FROM viajarseguro.data where subjectId is not null
update data
set data.objetoid = data.subjectId,data.objetotipo = 1
where subjectId is not null and objetoid is null;
--ALERTAS
SELECT * FROM viajarseguro.data where eventoId is not null;
update data
set data.objetoid = data.eventoId, data.objetotipo = 0
where eventoId is not null and objetoid is null;
--SITIOS
SELECT * FROM viajarseguro.data where sitioId is not null;
update data
set data.objetoid = data.sitioId, data.objetotipo = 4
where sitioId is not null and objetoid is null;