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
) ENGINE=InnoDB AUTO_INCREMENT=966 DEFAULT CHARSET=latin1;

CREATE TABLE `peligro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `nombreEn` varchar(255) DEFAULT NULL,
  `categoria` int(11) DEFAULT NULL,
  `damage` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=latin1;


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
  `caducidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3C6993C66E5E8B6` (`lugarObj_id`),
  KEY `FK3C6993CF194B46F` (`peligro_id`),
  CONSTRAINT `FK_Lugar` FOREIGN KEY (`lugarObj_id`) REFERENCES `lugar` (`id`),
  CONSTRAINT `FK_Peligro` FOREIGN KEY (`peligro_id`) REFERENCES `peligro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3700 DEFAULT CHARSET=latin1;

CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `nombreEn` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  `descripcion` varchar(250) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `nombreEn_UNIQUE` (`nombreEn`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=9542 DEFAULT CHARSET=latin1;

CREATE TABLE `fuente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `fiabilidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
