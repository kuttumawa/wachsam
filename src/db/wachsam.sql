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
  `value` varchar(500) COLLATE latin1_spanish_ci DEFAULT NULL,
  `tipoValor` int(11) NOT NULL,
  `descripcion` varchar(250) COLLATE latin1_spanish_ci DEFAULT NULL,
  `tag1_id` int(11) DEFAULT NULL,
  `tag2_id` int(11) DEFAULT NULL,
  `tag3_id` int(11) DEFAULT NULL,
  `lugarId` int(11) DEFAULT NULL,
  `subjectId` int(11) DEFAULT NULL,
  `eventoId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `lugarId_UNIQUE` (`lugarId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

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
