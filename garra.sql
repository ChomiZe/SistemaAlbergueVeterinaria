CREATE DATABASE  IF NOT EXISTS `garra` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `garra`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: garra
-- ------------------------------------------------------
-- Server version	5.6.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `animales`
--

DROP TABLE IF EXISTS `animales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animales` (
  `idAnimales` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Edad aprox` int(11) NOT NULL,
  `ColorPelaje` varchar(45) NOT NULL,
  `FechaIngreso` date NOT NULL,
  `FechaSalida` date DEFAULT NULL,
  `idGenero` int(11) NOT NULL,
  `Veterinario_idVeterinario` int(11) NOT NULL,
  `TipoServicio_idTipoServicio` int(11) NOT NULL,
  PRIMARY KEY (`idAnimales`,`Veterinario_idVeterinario`,`TipoServicio_idTipoServicio`),
  UNIQUE KEY `idAnimales_UNIQUE` (`idAnimales`),
  KEY `fk_Animales_Genero_idx` (`idGenero`),
  KEY `fk_Animales_Veterinario1_idx` (`Veterinario_idVeterinario`),
  KEY `fk_Animales_TipoServicio1_idx` (`TipoServicio_idTipoServicio`),
  CONSTRAINT `fk_Animales_Genero` FOREIGN KEY (`idGenero`) REFERENCES `genero` (`idGenero`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Animales_TipoServicio1` FOREIGN KEY (`TipoServicio_idTipoServicio`) REFERENCES `tiposervicio` (`idTipoServicio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Animales_Veterinario1` FOREIGN KEY (`Veterinario_idVeterinario`) REFERENCES `veterinario` (`idVeterinario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animales`
--

LOCK TABLES `animales` WRITE;
/*!40000 ALTER TABLE `animales` DISABLE KEYS */;
INSERT INTO `animales` VALUES (1,'lucky',2,'blanco','2015-01-01','2015-02-02',1,1,1);
/*!40000 ALTER TABLE `animales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `animales_vista`
--

DROP TABLE IF EXISTS `animales_vista`;
/*!50001 DROP VIEW IF EXISTS `animales_vista`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `animales_vista` (
  `idAnimales` tinyint NOT NULL,
  `Nombre` tinyint NOT NULL,
  `Edad aprox` tinyint NOT NULL,
  `ColorPelaje` tinyint NOT NULL,
  `FechaIngreso` tinyint NOT NULL,
  `FechaSalida` tinyint NOT NULL,
  `idGenero` tinyint NOT NULL,
  `Veterinario_idVeterinario` tinyint NOT NULL,
  `Veterinario` tinyint NOT NULL,
  `TipoServicio_idTipoServicio` tinyint NOT NULL,
  `Servicio` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `aperturacaja`
--

DROP TABLE IF EXISTS `aperturacaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aperturacaja` (
  `idAperturaCaja` int(11) NOT NULL,
  `FechaApetura` date NOT NULL,
  `MontoApertura` double NOT NULL,
  `idCaja` int(11) NOT NULL,
  PRIMARY KEY (`idAperturaCaja`),
  UNIQUE KEY `idAperturaCaja_UNIQUE` (`idAperturaCaja`),
  KEY `fk_AperturaCaja_Caja1_idx` (`idCaja`),
  CONSTRAINT `fk_AperturaCaja_Caja1` FOREIGN KEY (`idCaja`) REFERENCES `caja` (`idCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aperturacaja`
--

LOCK TABLES `aperturacaja` WRITE;
/*!40000 ALTER TABLE `aperturacaja` DISABLE KEYS */;
INSERT INTO `aperturacaja` VALUES (1,'2015-03-20',1000000,1),(2,'2015-03-20',500000,1),(3,'2015-03-24',500000,1),(4,'2015-04-11',500000,1);
/*!40000 ALTER TABLE `aperturacaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `arqueocaja`
--

DROP TABLE IF EXISTS `arqueocaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arqueocaja` (
  `idArqueoCaja` int(11) NOT NULL,
  `FechaArqueo` date NOT NULL,
  `MontoInicial` double NOT NULL,
  `MontoFinal` double NOT NULL,
  `idAperturaCaja` int(11) NOT NULL,
  PRIMARY KEY (`idArqueoCaja`),
  KEY `fk_ArqueoCaja_AperturaCaja1_idx` (`idAperturaCaja`),
  CONSTRAINT `fk_ArqueoCaja_AperturaCaja1` FOREIGN KEY (`idAperturaCaja`) REFERENCES `aperturacaja` (`idAperturaCaja`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arqueocaja`
--

LOCK TABLES `arqueocaja` WRITE;
/*!40000 ALTER TABLE `arqueocaja` DISABLE KEYS */;
/*!40000 ALTER TABLE `arqueocaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `barrios`
--

DROP TABLE IF EXISTS `barrios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barrios` (
  `idBarrio` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idBarrio`),
  UNIQUE KEY `Nombre_UNIQUE` (`Nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barrios`
--

LOCK TABLES `barrios` WRITE;
/*!40000 ALTER TABLE `barrios` DISABLE KEYS */;
/*!40000 ALTER TABLE `barrios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja` (
  `idCaja` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idCaja`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja`
--

LOCK TABLES `caja` WRITE;
/*!40000 ALTER TABLE `caja` DISABLE KEYS */;
INSERT INTO `caja` VALUES (1,'Caja 1');
/*!40000 ALTER TABLE `caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargos`
--

DROP TABLE IF EXISTS `cargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargos` (
  `idCargo` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idCargo`),
  UNIQUE KEY `Nombre_UNIQUE` (`Nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargos`
--

LOCK TABLES `cargos` WRITE;
/*!40000 ALTER TABLE `cargos` DISABLE KEYS */;
INSERT INTO `cargos` VALUES (1,'Cargo');
/*!40000 ALTER TABLE `cargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargosvoluntarios`
--

DROP TABLE IF EXISTS `cargosvoluntarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargosvoluntarios` (
  `idVoluntarios` int(11) NOT NULL,
  `idCargo` int(11) NOT NULL,
  PRIMARY KEY (`idVoluntarios`),
  KEY `fk_CargosVoluntarios_Cargos1_idx` (`idCargo`),
  CONSTRAINT `fk_CargosVoluntarios_Cargos1` FOREIGN KEY (`idCargo`) REFERENCES `cargos` (`idCargo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cargos_Voluntarios1` FOREIGN KEY (`idVoluntarios`) REFERENCES `voluntarios` (`idVoluntarios`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargosvoluntarios`
--

LOCK TABLES `cargosvoluntarios` WRITE;
/*!40000 ALTER TABLE `cargosvoluntarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `cargosvoluntarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorias` (
  `idCategorias` int(11) NOT NULL,
  `CodCategorias` varchar(45) NOT NULL,
  `PorcIva` float NOT NULL,
  PRIMARY KEY (`idCategorias`),
  UNIQUE KEY `idCategorias_UNIQUE` (`idCategorias`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Iva10',10),(2,'Iva 5',5),(3,'Exenta',0);
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cierrecaja`
--

DROP TABLE IF EXISTS `cierrecaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cierrecaja` (
  `idAperturaCaja` int(11) NOT NULL,
  `FechaCierre` date NOT NULL,
  `MontoCierre` float NOT NULL,
  `idVoluntarios` int(11) NOT NULL,
  PRIMARY KEY (`idAperturaCaja`),
  KEY `fk_CierreCaja_Cargos1_idx` (`idVoluntarios`),
  CONSTRAINT `fk_CierreCaja_AperturaCaja1` FOREIGN KEY (`idAperturaCaja`) REFERENCES `aperturacaja` (`idAperturaCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CierreCaja_Cargos1` FOREIGN KEY (`idVoluntarios`) REFERENCES `voluntarios` (`idVoluntarios`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cierrecaja`
--

LOCK TABLES `cierrecaja` WRITE;
/*!40000 ALTER TABLE `cierrecaja` DISABLE KEYS */;
INSERT INTO `cierrecaja` VALUES (1,'2015-03-24',300000,1),(4,'2015-04-11',500000,1);
/*!40000 ALTER TABLE `cierrecaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cobros`
--

DROP TABLE IF EXISTS `cobros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cobros` (
  `idCobros` int(11) NOT NULL,
  `idVoluntarios` int(11) NOT NULL,
  `FechaCobros` date NOT NULL,
  `MontoEntregado` double NOT NULL,
  `idSocioProtector` int(11) NOT NULL,
  `idCondicionPago` int(11) NOT NULL,
  `idAperturaCaja` int(11) NOT NULL,
  PRIMARY KEY (`idCobros`,`idVoluntarios`),
  UNIQUE KEY `idVoluntarios_UNIQUE` (`idVoluntarios`),
  KEY `fk_Cobros_Cargos1_idx` (`idVoluntarios`),
  KEY `fk_Cobros_CondicionPago1_idx` (`idCondicionPago`),
  KEY `fk_Cobros_SocioProtector1_idx` (`idSocioProtector`),
  KEY `fk_Cobros_AperturaCaja1_idx` (`idAperturaCaja`),
  CONSTRAINT `fk_Cobros_AperturaCaja1` FOREIGN KEY (`idAperturaCaja`) REFERENCES `aperturacaja` (`idAperturaCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cobros_Cargos1` FOREIGN KEY (`idVoluntarios`) REFERENCES `voluntarios` (`idVoluntarios`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Cobros_CondicionPago1` FOREIGN KEY (`idCondicionPago`) REFERENCES `condicionpago` (`idCondicionPago`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Cobros_SocioProtector1` FOREIGN KEY (`idSocioProtector`) REFERENCES `socioprotector` (`idSocioProtector`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cobros`
--

LOCK TABLES `cobros` WRITE;
/*!40000 ALTER TABLE `cobros` DISABLE KEYS */;
INSERT INTO `cobros` VALUES (1,1,'2015-04-11',15000,1,1,2);
/*!40000 ALTER TABLE `cobros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `cobros_v`
--

DROP TABLE IF EXISTS `cobros_v`;
/*!50001 DROP VIEW IF EXISTS `cobros_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `cobros_v` (
  `idCobros` tinyint NOT NULL,
  `idVoluntarios` tinyint NOT NULL,
  `Voluntario` tinyint NOT NULL,
  `Fecha` tinyint NOT NULL,
  `MontoEntregado` tinyint NOT NULL,
  `idSocioProtector` tinyint NOT NULL,
  `SocioProtector` tinyint NOT NULL,
  `idCondicionPago` tinyint NOT NULL,
  `CondicionPago` tinyint NOT NULL,
  `idAperturaCaja` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `compras_v`
--

DROP TABLE IF EXISTS `compras_v`;
/*!50001 DROP VIEW IF EXISTS `compras_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `compras_v` (
  `NroFact` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `Fecha` tinyint NOT NULL,
  `idCondicionPago` tinyint NOT NULL,
  `idMonedas` tinyint NOT NULL,
  `idProveedores` tinyint NOT NULL,
  `ruc` tinyint NOT NULL,
  `DescripProveedores` tinyint NOT NULL,
  `total` tinyint NOT NULL,
  `CondicionPago` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `comprasdet_v`
--

DROP TABLE IF EXISTS `comprasdet_v`;
/*!50001 DROP VIEW IF EXISTS `comprasdet_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `comprasdet_v` (
  `NroFact` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `idItems` tinyint NOT NULL,
  `DescripItems` tinyint NOT NULL,
  `Cantidad` tinyint NOT NULL,
  `PrecioCostUnitario` tinyint NOT NULL,
  `PorcIva` tinyint NOT NULL,
  `Subtotal` tinyint NOT NULL,
  `Exentas` tinyint NOT NULL,
  `Gravadas5` tinyint NOT NULL,
  `Gravadas10` tinyint NOT NULL,
  `Liquidacion5` tinyint NOT NULL,
  `Liquidacion10` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `compratotal_v`
--

DROP TABLE IF EXISTS `compratotal_v`;
/*!50001 DROP VIEW IF EXISTS `compratotal_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `compratotal_v` (
  `NroFact` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `Exentas` tinyint NOT NULL,
  `Gravadas5` tinyint NOT NULL,
  `Liquidacion5` tinyint NOT NULL,
  `Gravadas10` tinyint NOT NULL,
  `Liquidacion10` tinyint NOT NULL,
  `TotalIva` tinyint NOT NULL,
  `Total` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `conceptos_notadebito`
--

DROP TABLE IF EXISTS `conceptos_notadebito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conceptos_notadebito` (
  `idConceptoNotaDebito` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  PRIMARY KEY (`idConceptoNotaDebito`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`),
  KEY `fk_categoria_idx` (`idCategoria`),
  CONSTRAINT `fk_categoria` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategorias`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conceptos_notadebito`
--

LOCK TABLES `conceptos_notadebito` WRITE;
/*!40000 ALTER TABLE `conceptos_notadebito` DISABLE KEYS */;
INSERT INTO `conceptos_notadebito` VALUES (1,'Servicio de Flete',1),(2,'Delivery',1);
/*!40000 ALTER TABLE `conceptos_notadebito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `conceptos_notadebito_v`
--

DROP TABLE IF EXISTS `conceptos_notadebito_v`;
/*!50001 DROP VIEW IF EXISTS `conceptos_notadebito_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `conceptos_notadebito_v` (
  `idConceptoNotaDebito` tinyint NOT NULL,
  `Descripcion` tinyint NOT NULL,
  `idCategoria` tinyint NOT NULL,
  `DescripCategoria` tinyint NOT NULL,
  `PorcIva` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `condicionpago`
--

DROP TABLE IF EXISTS `condicionpago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicionpago` (
  `idCondicionPago` int(11) NOT NULL,
  `DescripCondicionP` varchar(15) NOT NULL,
  PRIMARY KEY (`idCondicionPago`),
  UNIQUE KEY `idCondicionPago_UNIQUE` (`idCondicionPago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicionpago`
--

LOCK TABLES `condicionpago` WRITE;
/*!40000 ALTER TABLE `condicionpago` DISABLE KEYS */;
INSERT INTO `condicionpago` VALUES (1,'contado'),(2,'credito');
/*!40000 ALTER TABLE `condicionpago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalledocporpagar`
--

DROP TABLE IF EXISTS `detalledocporpagar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalledocporpagar` (
  `NroFact` varchar(15) NOT NULL,
  `idTimbrados` int(11) NOT NULL,
  `nrocuota` int(11) NOT NULL,
  `montopago` double NOT NULL,
  PRIMARY KEY (`NroFact`,`idTimbrados`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalledocporpagar`
--

LOCK TABLES `detalledocporpagar` WRITE;
/*!40000 ALTER TABLE `detalledocporpagar` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalledocporpagar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docporpagar`
--

DROP TABLE IF EXISTS `docporpagar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docporpagar` (
  `idTipoServicio` int(11) NOT NULL,
  `NroFact` varchar(15) NOT NULL,
  `idTimbrados` int(11) NOT NULL,
  `fechaVenc` date NOT NULL,
  `montoTotal` double NOT NULL,
  `idProveedores` int(11) DEFAULT NULL,
  PRIMARY KEY (`NroFact`,`idTimbrados`),
  KEY `fk_DocporPagar_TipoServicio1_idx` (`idTipoServicio`),
  KEY `fk_DocporPagar_FactCompraEncab1_idx` (`NroFact`,`idTimbrados`),
  KEY `fk_doc_factura_idx` (`idTimbrados`),
  CONSTRAINT `fk_001` FOREIGN KEY (`NroFact`) REFERENCES `facturacompra` (`NroFact`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DocporPagar_TipoServicio1` FOREIGN KEY (`idTipoServicio`) REFERENCES `tiposervicio` (`idTipoServicio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docporpagar`
--

LOCK TABLES `docporpagar` WRITE;
/*!40000 ALTER TABLE `docporpagar` DISABLE KEYS */;
/*!40000 ALTER TABLE `docporpagar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donaciones`
--

DROP TABLE IF EXISTS `donaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donaciones` (
  `idDonaciones` int(11) NOT NULL,
  `DescripDonaciones` varchar(45) NOT NULL,
  `CantidadMonto` double NOT NULL,
  `idMonedas` int(11) NOT NULL,
  `idAperturaCaja` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  PRIMARY KEY (`idDonaciones`),
  UNIQUE KEY `idDonaciones_UNIQUE` (`idDonaciones`),
  KEY `fk_Donaciones_Monedas1_idx` (`idMonedas`),
  KEY `fk_Donaciones_AperturaCaja1_idx` (`idAperturaCaja`),
  CONSTRAINT `fk_Donaciones_AperturaCaja1` FOREIGN KEY (`idAperturaCaja`) REFERENCES `aperturacaja` (`idAperturaCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Donaciones_Monedas1` FOREIGN KEY (`idMonedas`) REFERENCES `monedas` (`idMonedas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donaciones`
--

LOCK TABLES `donaciones` WRITE;
/*!40000 ALTER TABLE `donaciones` DISABLE KEYS */;
INSERT INTO `donaciones` VALUES (1,'Donacion 1 millon',1000000,1,2,'2015-04-11'),(2,'Donacion 2',15000,1,2,'2015-04-11');
/*!40000 ALTER TABLE `donaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `donaciones_v`
--

DROP TABLE IF EXISTS `donaciones_v`;
/*!50001 DROP VIEW IF EXISTS `donaciones_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `donaciones_v` (
  `idDonaciones` tinyint NOT NULL,
  `DescripDonaciones` tinyint NOT NULL,
  `CantidadMonto` tinyint NOT NULL,
  `idMonedas` tinyint NOT NULL,
  `Moneda` tinyint NOT NULL,
  `idAperturaCaja` tinyint NOT NULL,
  `fecha` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `factcompradet`
--

DROP TABLE IF EXISTS `factcompradet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factcompradet` (
  `NroFact` varchar(15) NOT NULL,
  `NroTimbrado` int(11) NOT NULL,
  `idItems` int(11) NOT NULL,
  `Cantidad` int(11) NOT NULL,
  `PrecioCostUnitario` double NOT NULL,
  `PorcIva` float NOT NULL,
  PRIMARY KEY (`idItems`,`NroTimbrado`,`NroFact`),
  KEY `fk_FactCombraDet_FactCompraEncab1_idx` (`NroFact`),
  KEY `fk_FactCombraDet_Items1_idx` (`idItems`),
  KEY `fk_FactCombraDet_FactCompraEncab1_idx1` (`NroFact`,`NroTimbrado`),
  CONSTRAINT `fk_compra` FOREIGN KEY (`NroFact`, `NroTimbrado`) REFERENCES `facturacompra` (`NroFact`, `NroTimbrado`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_FactCombraDet_Items1` FOREIGN KEY (`idItems`) REFERENCES `items` (`idItems`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factcompradet`
--

LOCK TABLES `factcompradet` WRITE;
/*!40000 ALTER TABLE `factcompradet` DISABLE KEYS */;
INSERT INTO `factcompradet` VALUES ('003-003-1321321',2,1,4,10000,10),('001-002-0000222',321,1,2,10000,10);
/*!40000 ALTER TABLE `factcompradet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturacompra`
--

DROP TABLE IF EXISTS `facturacompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facturacompra` (
  `NroFact` varchar(15) NOT NULL,
  `NroTimbrado` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `idCondicionPago` int(11) NOT NULL,
  `idMonedas` int(11) NOT NULL,
  `idProveedores` int(11) NOT NULL,
  PRIMARY KEY (`NroFact`,`NroTimbrado`),
  UNIQUE KEY `NroFact_UNIQUE` (`NroFact`),
  KEY `fk_FactCompraEncab_CondicionPago1_idx` (`idCondicionPago`),
  KEY `fk_FactCompraEncab_Monedas1_idx` (`idMonedas`),
  KEY `fk_FactCompraEncab_Proveedores1_idx` (`idProveedores`),
  CONSTRAINT `fk_FactCompraEncab_CondicionPago1` FOREIGN KEY (`idCondicionPago`) REFERENCES `condicionpago` (`idCondicionPago`) ON UPDATE CASCADE,
  CONSTRAINT `fk_FactCompraEncab_Monedas1` FOREIGN KEY (`idMonedas`) REFERENCES `monedas` (`idMonedas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_FactCompraEncab_Proveedores1` FOREIGN KEY (`idProveedores`) REFERENCES `proveedores` (`idProveedores`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturacompra`
--

LOCK TABLES `facturacompra` WRITE;
/*!40000 ALTER TABLE `facturacompra` DISABLE KEYS */;
INSERT INTO `facturacompra` VALUES ('001-002-0000222',321,'2015-04-11',1,1,1),('003-003-1321321',2,'2015-04-08',1,1,1);
/*!40000 ALTER TABLE `facturacompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formaabono`
--

DROP TABLE IF EXISTS `formaabono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formaabono` (
  `idFormaAbono` int(11) NOT NULL,
  `DescripAbono` varchar(45) NOT NULL,
  PRIMARY KEY (`idFormaAbono`),
  UNIQUE KEY `idFormaAbono_UNIQUE` (`idFormaAbono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formaabono`
--

LOCK TABLES `formaabono` WRITE;
/*!40000 ALTER TABLE `formaabono` DISABLE KEYS */;
INSERT INTO `formaabono` VALUES (1,'efectivo');
/*!40000 ALTER TABLE `formaabono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genero` (
  `idGenero` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idGenero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genero`
--

LOCK TABLES `genero` WRITE;
/*!40000 ALTER TABLE `genero` DISABLE KEYS */;
INSERT INTO `genero` VALUES (1,'Macho'),(2,'Hembra');
/*!40000 ALTER TABLE `genero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_clasificacion`
--

DROP TABLE IF EXISTS `item_clasificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_clasificacion` (
  `idItem_Clasificacion` int(11) NOT NULL,
  `DescripClas` varchar(45) NOT NULL,
  PRIMARY KEY (`idItem_Clasificacion`),
  UNIQUE KEY `idItem_Clasificacion_UNIQUE` (`idItem_Clasificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_clasificacion`
--

LOCK TABLES `item_clasificacion` WRITE;
/*!40000 ALTER TABLE `item_clasificacion` DISABLE KEYS */;
INSERT INTO `item_clasificacion` VALUES (1,'clasificacionuno');
/*!40000 ALTER TABLE `item_clasificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `idItems` int(11) NOT NULL,
  `DescripItems` varchar(45) NOT NULL,
  `PrecioCosto` double NOT NULL,
  `idItem_Clasificacion` int(11) NOT NULL,
  `idMarcas` int(11) NOT NULL,
  `idCategorias` int(11) NOT NULL,
  `Existecia` double NOT NULL,
  PRIMARY KEY (`idItems`),
  UNIQUE KEY `idMedicamentos_UNIQUE` (`idItems`),
  KEY `fk_Items_Item_Clasificacion1_idx` (`idItem_Clasificacion`),
  KEY `fk_Items_Marcas1_idx` (`idMarcas`),
  KEY `fk_Items_Categorias1_idx` (`idCategorias`),
  CONSTRAINT `fk_Items_Categorias1` FOREIGN KEY (`idCategorias`) REFERENCES `categorias` (`idCategorias`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Items_Item_Clasificacion1` FOREIGN KEY (`idItem_Clasificacion`) REFERENCES `item_clasificacion` (`idItem_Clasificacion`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Items_Marcas1` FOREIGN KEY (`idMarcas`) REFERENCES `marcas` (`idMarcas`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'Item 1',10000,1,1,1,20),(2,'Item 2',20000,1,1,1,2);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `items_vista`
--

DROP TABLE IF EXISTS `items_vista`;
/*!50001 DROP VIEW IF EXISTS `items_vista`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `items_vista` (
  `idItems` tinyint NOT NULL,
  `DescripItems` tinyint NOT NULL,
  `PrecioCosto` tinyint NOT NULL,
  `idItem_Clasificacion` tinyint NOT NULL,
  `Clasificacion` tinyint NOT NULL,
  `idMarcas` tinyint NOT NULL,
  `DescripMarcas` tinyint NOT NULL,
  `idCategorias` tinyint NOT NULL,
  `CodCategorias` tinyint NOT NULL,
  `porciva` tinyint NOT NULL,
  `Existecia` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `marcas`
--

DROP TABLE IF EXISTS `marcas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marcas` (
  `idMarcas` int(11) NOT NULL,
  `DescripMarcas` varchar(45) NOT NULL,
  PRIMARY KEY (`idMarcas`),
  UNIQUE KEY `idMarcas_UNIQUE` (`idMarcas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcas`
--

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
INSERT INTO `marcas` VALUES (1,'Marca 1');
/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monedas`
--

DROP TABLE IF EXISTS `monedas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monedas` (
  `idMonedas` int(11) NOT NULL,
  `Descrip` varchar(20) NOT NULL,
  PRIMARY KEY (`idMonedas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monedas`
--

LOCK TABLES `monedas` WRITE;
/*!40000 ALTER TABLE `monedas` DISABLE KEYS */;
INSERT INTO `monedas` VALUES (1,'Guaranies'),(2,'Dolares');
/*!40000 ALTER TABLE `monedas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `nctotales_v`
--

DROP TABLE IF EXISTS `nctotales_v`;
/*!50001 DROP VIEW IF EXISTS `nctotales_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `nctotales_v` (
  `NroNotaCredito` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `Exentas` tinyint NOT NULL,
  `Gravadas5` tinyint NOT NULL,
  `Liquidacion5` tinyint NOT NULL,
  `Gravadas10` tinyint NOT NULL,
  `Liquidacion10` tinyint NOT NULL,
  `TotalIva` tinyint NOT NULL,
  `Total` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `ndtotales_v`
--

DROP TABLE IF EXISTS `ndtotales_v`;
/*!50001 DROP VIEW IF EXISTS `ndtotales_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ndtotales_v` (
  `NroNotaDebitoCompra` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `Exentas` tinyint NOT NULL,
  `Gravadas5` tinyint NOT NULL,
  `Liquidacion5` tinyint NOT NULL,
  `Gravadas10` tinyint NOT NULL,
  `Liquidacion10` tinyint NOT NULL,
  `TotalIva` tinyint NOT NULL,
  `Total` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `notacreditocompra`
--

DROP TABLE IF EXISTS `notacreditocompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notacreditocompra` (
  `NroNotaCredito` varchar(15) NOT NULL,
  `NroTimbrado` int(11) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `NroFact` varchar(15) NOT NULL,
  `NroTimbradoFactura` int(11) NOT NULL,
  PRIMARY KEY (`NroNotaCredito`,`NroTimbrado`),
  KEY `fk_EncabNotaCreditoCom_FactCompraEncab1_idx` (`NroFact`,`NroTimbradoFactura`),
  CONSTRAINT `fk_facturacompra` FOREIGN KEY (`NroFact`, `NroTimbradoFactura`) REFERENCES `facturacompra` (`NroFact`, `NroTimbrado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notacreditocompra`
--

LOCK TABLES `notacreditocompra` WRITE;
/*!40000 ALTER TABLE `notacreditocompra` DISABLE KEYS */;
INSERT INTO `notacreditocompra` VALUES ('001-001-0000002',122,'2015-04-10','003-003-1321321',2),('001-002-0031155',5,'2015-04-11','001-002-0000222',321);
/*!40000 ALTER TABLE `notacreditocompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `notacreditocompra_v`
--

DROP TABLE IF EXISTS `notacreditocompra_v`;
/*!50001 DROP VIEW IF EXISTS `notacreditocompra_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `notacreditocompra_v` (
  `NroNotaCredito` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `Fecha` tinyint NOT NULL,
  `NroFact` tinyint NOT NULL,
  `NroTimbradoFactura` tinyint NOT NULL,
  `idProveedores` tinyint NOT NULL,
  `ruc` tinyint NOT NULL,
  `RazonSocial` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `notacreditocompradet`
--

DROP TABLE IF EXISTS `notacreditocompradet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notacreditocompradet` (
  `NroNotaCredito` varchar(15) NOT NULL,
  `NroTimbrado` int(11) NOT NULL,
  `idItems` int(11) NOT NULL,
  `cantidad` double NOT NULL,
  `preciocosto` float NOT NULL,
  `porciva` float NOT NULL,
  PRIMARY KEY (`NroNotaCredito`,`NroTimbrado`,`idItems`),
  KEY `fk_DetalleNotaCreditoComp_Items1_idx` (`idItems`),
  CONSTRAINT `fk_items` FOREIGN KEY (`idItems`) REFERENCES `items` (`idItems`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_nc` FOREIGN KEY (`NroNotaCredito`, `NroTimbrado`) REFERENCES `notacreditocompra` (`NroNotaCredito`, `NroTimbrado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notacreditocompradet`
--

LOCK TABLES `notacreditocompradet` WRITE;
/*!40000 ALTER TABLE `notacreditocompradet` DISABLE KEYS */;
INSERT INTO `notacreditocompradet` VALUES ('001-001-0000002',122,1,4,10000,10),('001-002-0031155',5,1,2,10000,10);
/*!40000 ALTER TABLE `notacreditocompradet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `notacreditocompradet_v`
--

DROP TABLE IF EXISTS `notacreditocompradet_v`;
/*!50001 DROP VIEW IF EXISTS `notacreditocompradet_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `notacreditocompradet_v` (
  `NroNotaCredito` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `idItems` tinyint NOT NULL,
  `DescripItems` tinyint NOT NULL,
  `cantidad` tinyint NOT NULL,
  `preciocosto` tinyint NOT NULL,
  `porciva` tinyint NOT NULL,
  `Exentas` tinyint NOT NULL,
  `Gravadas5` tinyint NOT NULL,
  `Gravadas10` tinyint NOT NULL,
  `Liquidacion5` tinyint NOT NULL,
  `Liquidacion10` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `notadebitocompra`
--

DROP TABLE IF EXISTS `notadebitocompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notadebitocompra` (
  `NroNotaDebitoCompra` varchar(45) NOT NULL,
  `NroTimbrado` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `NroFact` varchar(15) NOT NULL,
  `NroTimbradoFactura` int(11) NOT NULL,
  PRIMARY KEY (`NroNotaDebitoCompra`,`NroTimbrado`),
  KEY `fk_NotadebitoCompra_FactCompraEncab1_idx` (`NroFact`,`NroTimbradoFactura`),
  CONSTRAINT `fk_compras494` FOREIGN KEY (`NroFact`, `NroTimbradoFactura`) REFERENCES `facturacompra` (`NroFact`, `NroTimbrado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notadebitocompra`
--

LOCK TABLES `notadebitocompra` WRITE;
/*!40000 ALTER TABLE `notadebitocompra` DISABLE KEYS */;
INSERT INTO `notadebitocompra` VALUES ('002-005-0016546',255,'2015-04-11','001-002-0000222',321);
/*!40000 ALTER TABLE `notadebitocompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `notadebitocompra_v`
--

DROP TABLE IF EXISTS `notadebitocompra_v`;
/*!50001 DROP VIEW IF EXISTS `notadebitocompra_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `notadebitocompra_v` (
  `NroNotaDebitoCompra` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `Fecha` tinyint NOT NULL,
  `NroFact` tinyint NOT NULL,
  `NroTimbradoFactura` tinyint NOT NULL,
  `idProveedores` tinyint NOT NULL,
  `ruc` tinyint NOT NULL,
  `RazonSocial` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `notadebitocompradet`
--

DROP TABLE IF EXISTS `notadebitocompradet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notadebitocompradet` (
  `NroNotaDebitoCompra` varchar(45) NOT NULL,
  `NroTimbrado` int(11) NOT NULL,
  `idConceptoNotaDebito` int(11) NOT NULL,
  `cantidad` double NOT NULL,
  `Precio` float NOT NULL,
  `PorcIva` float NOT NULL,
  PRIMARY KEY (`NroNotaDebitoCompra`,`NroTimbrado`,`idConceptoNotaDebito`),
  KEY `fk_DetalleNotaDebitoComp_EncabNotadebitoCom1_idx` (`NroNotaDebitoCompra`),
  KEY `fk_conceptond_idx` (`idConceptoNotaDebito`),
  CONSTRAINT `fk_conceptond` FOREIGN KEY (`idConceptoNotaDebito`) REFERENCES `conceptos_notadebito` (`idConceptoNotaDebito`) ON UPDATE CASCADE,
  CONSTRAINT `fk_DetalleNotaDebitoComp_EncabNotadebitoCom1` FOREIGN KEY (`NroNotaDebitoCompra`, `NroTimbrado`) REFERENCES `notadebitocompra` (`NroNotaDebitoCompra`, `NroTimbrado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notadebitocompradet`
--

LOCK TABLES `notadebitocompradet` WRITE;
/*!40000 ALTER TABLE `notadebitocompradet` DISABLE KEYS */;
INSERT INTO `notadebitocompradet` VALUES ('002-005-0016546',255,1,1,25000,10);
/*!40000 ALTER TABLE `notadebitocompradet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `notadebitocompradet_v`
--

DROP TABLE IF EXISTS `notadebitocompradet_v`;
/*!50001 DROP VIEW IF EXISTS `notadebitocompradet_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `notadebitocompradet_v` (
  `NroNotaDebitoCompra` tinyint NOT NULL,
  `NroTimbrado` tinyint NOT NULL,
  `idConceptoNotaDebito` tinyint NOT NULL,
  `Descripcion` tinyint NOT NULL,
  `cantidad` tinyint NOT NULL,
  `Precio` tinyint NOT NULL,
  `PorcIva` tinyint NOT NULL,
  `Subtotal` tinyint NOT NULL,
  `Exentas` tinyint NOT NULL,
  `Gravadas5` tinyint NOT NULL,
  `Gravadas10` tinyint NOT NULL,
  `Liquidacion5` tinyint NOT NULL,
  `Liquidacion10` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedores` (
  `idProveedores` int(11) NOT NULL,
  `DescripProveedores` varchar(25) NOT NULL,
  `Ruc` varchar(20) NOT NULL,
  `NroTelCel` int(11) NOT NULL,
  `Direccion` varchar(20) NOT NULL,
  PRIMARY KEY (`idProveedores`),
  UNIQUE KEY `idProveedores_UNIQUE` (`idProveedores`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,'Proveedor','800330025',522123456,'Centro');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socioprotector`
--

DROP TABLE IF EXISTS `socioprotector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socioprotector` (
  `idSocioProtector` int(11) NOT NULL,
  `Nombre` varchar(15) NOT NULL,
  `Apellido` varchar(15) NOT NULL,
  `NroDoc` int(11) NOT NULL,
  `NroTelCel` int(11) NOT NULL,
  `FechaCobro` date NOT NULL,
  `MontoAporte` double NOT NULL,
  `idVoluntarios` int(11) NOT NULL,
  `idFormaAbono` int(11) NOT NULL,
  PRIMARY KEY (`idSocioProtector`),
  UNIQUE KEY `idSocioProtector_UNIQUE` (`idSocioProtector`),
  UNIQUE KEY `NroDoc_UNIQUE` (`NroDoc`),
  KEY `fk_SocioProtector_Cargos1_idx` (`idVoluntarios`),
  KEY `fk_SocioProtector_FormaAbono1_idx` (`idFormaAbono`),
  CONSTRAINT `fk_SocioProtector_Cargos1` FOREIGN KEY (`idVoluntarios`) REFERENCES `voluntarios` (`idVoluntarios`) ON UPDATE CASCADE,
  CONSTRAINT `fk_SocioProtector_FormaAbono1` FOREIGN KEY (`idFormaAbono`) REFERENCES `formaabono` (`idFormaAbono`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socioprotector`
--

LOCK TABLES `socioprotector` WRITE;
/*!40000 ALTER TABLE `socioprotector` DISABLE KEYS */;
INSERT INTO `socioprotector` VALUES (1,'Luis','perez',123456,52240226,'2015-01-01',10000,1,1);
/*!40000 ALTER TABLE `socioprotector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposervicio`
--

DROP TABLE IF EXISTS `tiposervicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposervicio` (
  `idTipoServicio` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  `Costo` double NOT NULL,
  PRIMARY KEY (`idTipoServicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposervicio`
--

LOCK TABLES `tiposervicio` WRITE;
/*!40000 ALTER TABLE `tiposervicio` DISABLE KEYS */;
INSERT INTO `tiposervicio` VALUES (1,'Servicio',1);
/*!40000 ALTER TABLE `tiposervicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `Usuclave` varchar(25) NOT NULL,
  `Alias` varchar(45) NOT NULL,
  `UsuActivo` tinyint(1) NOT NULL,
  `idVoluntarios` int(11) NOT NULL,
  PRIMARY KEY (`idVoluntarios`),
  UNIQUE KEY `Alias_UNIQUE` (`Alias`),
  CONSTRAINT `fk_Usuario_Cargos1` FOREIGN KEY (`idVoluntarios`) REFERENCES `voluntarios` (`idVoluntarios`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('±²³´µ¶','romina',0,1),('±²³´µ¶','alvarenga',0,2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veterinario`
--

DROP TABLE IF EXISTS `veterinario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veterinario` (
  `idVeterinario` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Apellido` varchar(45) NOT NULL,
  `NroDoc` int(11) NOT NULL,
  `NroTel` int(11) NOT NULL,
  PRIMARY KEY (`idVeterinario`),
  UNIQUE KEY `NroDoc_UNIQUE` (`NroDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veterinario`
--

LOCK TABLES `veterinario` WRITE;
/*!40000 ALTER TABLE `veterinario` DISABLE KEYS */;
INSERT INTO `veterinario` VALUES (1,'Ariel','Ramirez',3658729,981112458);
/*!40000 ALTER TABLE `veterinario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `vistausuario`
--

DROP TABLE IF EXISTS `vistausuario`;
/*!50001 DROP VIEW IF EXISTS `vistausuario`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vistausuario` (
  `Usuclave` tinyint NOT NULL,
  `Alias` tinyint NOT NULL,
  `UsuActivo` tinyint NOT NULL,
  `idVoluntarios` tinyint NOT NULL,
  `nombre` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `voluntarios`
--

DROP TABLE IF EXISTS `voluntarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voluntarios` (
  `idVoluntarios` int(11) NOT NULL,
  `Nombre` varchar(15) NOT NULL,
  `Apellido` varchar(15) NOT NULL,
  `nroDoc` int(11) NOT NULL,
  `FechaNac` date NOT NULL,
  `NroTelCel` int(11) NOT NULL,
  `Domicilio` varchar(70) NOT NULL,
  PRIMARY KEY (`idVoluntarios`),
  UNIQUE KEY `idVoluntarios_UNIQUE` (`idVoluntarios`),
  UNIQUE KEY `nroDoc_UNIQUE` (`nroDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voluntarios`
--

LOCK TABLES `voluntarios` WRITE;
/*!40000 ALTER TABLE `voluntarios` DISABLE KEYS */;
INSERT INTO `voluntarios` VALUES (1,'Romina','Gomez',1234567,'1993-01-01',971202303,'BarrioCentro'),(2,'Jorge','Alvarenga',4567898,'1990-01-01',987131312,'caaguazu');
/*!40000 ALTER TABLE `voluntarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'garra'
--
/*!50003 DROP PROCEDURE IF EXISTS `abm_cobros` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_cobros`(

IN param_Bandera INTEGER,
IN param_nro int,
IN param_idvoluntario int,
IN param_fecha DATE,
IN param_monto double,
in param_idsocioprotector integer,
IN param_idcondicionpago INTEGER,
in param_idapertura integer
)
BEGIN
CASE param_Bandera
WHEN 1 THEN
INSERT INTO `cobros` VALUES (
 param_nro ,
 param_idvoluntario ,
 param_fecha ,
 param_monto ,
 param_idsocioprotector ,
 param_idcondicionpago ,
 param_idapertura );
WHEN 2 THEN
UPDATE `cobros` SET `FechaCobros`=param_fecha, `MontoEntregado` =param_monto,
`idSocioProtector`=param_idsocioprotector, idcondicionpago = param_idcondicionpago,idaperturacaja=param_idapertura 
where `idCobros` = param_nro and `idVoluntarios` = param_idvoluntario;

WHEN 3 THEN

DELETE FROM `cobros` WHERE `idCobros` = param_nro and `idVoluntarios` = param_idvoluntario;

END CASE;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_donaciones` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_donaciones`(

IN param_Bandera INTEGER,
IN param_nro int,
IN param_descripcion varchar(45),
IN param_monto double,
in param_idmonedas integer,
in param_idapertura integer,
in param_fecha date
)
BEGIN
CASE param_Bandera
WHEN 1 THEN
INSERT INTO `donaciones` VALUES (
 param_nro ,
 param_descripcion ,
 param_monto ,
 param_idmonedas ,
 param_idapertura ,
 param_fecha);
WHEN 2 THEN
UPDATE `donaciones` SET `DescripDonaciones`=param_descripcion, `CantidadMonto` =param_monto,
`idMonedas`=param_idmonedas, idaperturacaja=param_idapertura ,fecha = param_fecha
where `idDonaciones` = param_nro;

WHEN 3 THEN

DELETE FROM `Donaciones` WHERE `idDonaciones` = param_nro ;

END CASE;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_facturacompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_facturacompra`(

IN param_Bandera INTEGER,
IN param_nro varchar(15),
IN param_timbrado int,
IN param_fecha DATE,
IN param_idcondicionpago INTEGER,
in param_idmonedas integer,
in param_idproveedores integer

)
BEGIN

CASE param_Bandera

WHEN 1 THEN

INSERT INTO `FacturaCompra` VALUES (param_nro,param_timbrado, param_fecha,param_idcondicionpago,param_idmonedas,param_idproveedores);

WHEN 2 THEN

UPDATE `FacturaCompra` SET `Fecha`=param_fecha, `idCondicionPago` =param_idcondicionpago,

`idmonedas`=param_idmonedas, idproveedores = param_idproveedores

WHERE `NroFact` = param_nro and `NroTimbrado` = param_timbrado;

WHEN 3 THEN

DELETE FROM `FacturaCompra` WHERE `NroFact` = param_nro and `NroTimbrado` = param_timbrado;

END CASE;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_facturacompradet` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_facturacompradet`(

IN param_Bandera INTEGER,
IN param_nrofact varchar(15),
IN param_nrotimbrado int,
IN param_iditems int,
IN param_cantidad INTEGER,
in param_preciocosto double,
in param_porciva float

)
BEGIN

CASE param_Bandera
WHEN 1 THEN
INSERT INTO `factcompradet` VALUES (param_nrofact,param_nrotimbrado, param_iditems,param_cantidad,param_preciocosto,param_porciva);
WHEN 2 THEN
UPDATE `factcompradet` SET `Cantidad`=param_cantidad, `PrecioCostUnitario` =param_preciocosto,
`PorcIva`=param_porciva
WHERE `NroFact` = param_nrofact and `NroTimbrado` = param_nrotimbrado and idItems = param_iditems;
WHEN 3 THEN
DELETE FROM `factcompradet` WHERE `NroFact` = param_nro and `NroTimbrado` = param_timbrado and idItems = param_iditems ;

END CASE;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_notacreditocompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_notacreditocompra`(

IN param_Bandera INTEGER,
IN param_nro varchar(15),
IN param_timbrado int,
IN param_fecha DATE,
IN param_nrofact varchar(15),
in param_nrotimbradofactura integer
)
BEGIN
CASE param_Bandera
WHEN 1 THEN
INSERT INTO `notacreditocompra` VALUES (param_nro,param_timbrado, param_fecha,param_nrofact,param_nrotimbradofactura);
WHEN 2 THEN
UPDATE `notacreditocompra` SET `Fecha`=param_fecha, `NroFact` =param_nrofact,
`NroTimbradoFactura`=param_nrotimbradofactura
WHERE `NroNotaCredito` = param_nro and `NroTimbrado` = param_timbrado;
WHEN 3 THEN
DELETE FROM `notacreditocompra` WHERE `NroNotaCredito` = param_nro and `NroTimbrado` = param_timbrado;
END CASE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_notacreditocompradet` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_notacreditocompradet`(
 IN param_Bandera INTEGER,
 IN param_nro varchar(15),
 IN param_nrotimbrado int,
 IN param_iditems INTEGER,
 IN param_cantidad double,
 IN param_precio FLOAT, 
 IN param_iva FLOAT
 )
BEGIN
 CASE param_Bandera
 WHEN 1 THEN
 INSERT INTO `notacreditocompradet` VALUES (param_nro,param_nrotimbrado,param_iditems,param_cantidad,param_precio,param_iva);
 WHEN 2 THEN 
 UPDATE `notacreditocompradet` SET Cantidad = param_cantidad, porciva = param_iva, PrecioCosto = param_precio
 WHERE `NroNotaCredito` = param_nro and NroTimbrado = param_nrotimbrado and iditems = param_iditems;
 WHEN 3 THEN
 DELETE FROM `notacreditocompradet` WHERE `NroNotaCredito` = param_nro and NroTimbrado = param_nrotimbrado 
 and iditems = param_iditems;
 END CASE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_notadebitocompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_notadebitocompra`(
IN param_Bandera INTEGER,
IN param_nro varchar(15),
IN param_nrotimbrado int,
IN param_fecha DATE,
IN param_nrofact varchar(15),
in param_nrotimbradofactura integer
)
BEGIN
CASE param_Bandera
WHEN 1 THEN
INSERT INTO `notadebitocompra` VALUES (param_nro,param_nrotimbrado, param_fecha,param_nrofact,param_nrotimbradofactura);
WHEN 2 THEN
UPDATE `notadebitocompra` SET `Fecha`=param_fecha, `NroFact` =param_nrofact,
`NroTimbradoFactura`=param_nrotimbradofactura
WHERE `NroNotaDebitoCompra` = param_nro and `NroTimbrado` = param_nrotimbrado;
WHEN 3 THEN
DELETE FROM `notadebitocompra` WHERE `NroNotaDebitoCompra` = param_nro and `NroTimbrado` = param_nrotimbrado;
END CASE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `abm_notadebitocompradet` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `abm_notadebitocompradet`(
 IN param_Bandera INTEGER,
 IN param_nro varchar(15),
 IN param_nrotimbrado int,
 IN param_idConcepto INTEGER,
 IN param_cantidad double,
 IN param_precio FLOAT, 
 IN param_iva FLOAT
 )
BEGIN
 CASE param_Bandera
 WHEN 1 THEN
 INSERT INTO `notadebitocompradet` VALUES (param_nro,param_nrotimbrado,param_idConcepto,param_cantidad,param_precio,param_iva);
 WHEN 2 THEN 
 UPDATE `notadebitocompradet` SET Cantidad = param_cantidad, porciva = param_iva, Precio = param_precio
 WHERE `NroNotaDebitoCompra` = param_nro and NroTimbrado = param_nrotimbrado and idConcepto = param_idconcepto;
 WHEN 3 THEN
 DELETE FROM `notadebitocompradet` WHERE `NroNotaDebitoCompra` = param_nro and NroTimbrado = param_nrotimbrado 
 and idConceptoNotaDebito = param_idConcepto;
 END CASE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `animales_vista`
--

/*!50001 DROP TABLE IF EXISTS `animales_vista`*/;
/*!50001 DROP VIEW IF EXISTS `animales_vista`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `animales_vista` AS select `animales`.`idAnimales` AS `idAnimales`,`animales`.`Nombre` AS `Nombre`,`animales`.`Edad aprox` AS `Edad aprox`,`animales`.`ColorPelaje` AS `ColorPelaje`,`animales`.`FechaIngreso` AS `FechaIngreso`,`animales`.`FechaSalida` AS `FechaSalida`,`animales`.`idGenero` AS `idGenero`,`animales`.`Veterinario_idVeterinario` AS `Veterinario_idVeterinario`,concat(`veterinario`.`Nombre`,' ',`veterinario`.`Apellido`) AS `Veterinario`,`animales`.`TipoServicio_idTipoServicio` AS `TipoServicio_idTipoServicio`,`tiposervicio`.`Descripcion` AS `Servicio` from ((`animales` join `veterinario` on((`animales`.`Veterinario_idVeterinario` = `veterinario`.`idVeterinario`))) join `tiposervicio` on((`animales`.`TipoServicio_idTipoServicio` = `tiposervicio`.`idTipoServicio`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `cobros_v`
--

/*!50001 DROP TABLE IF EXISTS `cobros_v`*/;
/*!50001 DROP VIEW IF EXISTS `cobros_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `cobros_v` AS select `cobros`.`idCobros` AS `idCobros`,`cobros`.`idVoluntarios` AS `idVoluntarios`,concat(`voluntarios`.`Nombre`,' ',`voluntarios`.`Apellido`) AS `Voluntario`,`cobros`.`FechaCobros` AS `Fecha`,`cobros`.`MontoEntregado` AS `MontoEntregado`,`cobros`.`idSocioProtector` AS `idSocioProtector`,concat(`socioprotector`.`Nombre`,' ',`socioprotector`.`Apellido`) AS `SocioProtector`,`cobros`.`idCondicionPago` AS `idCondicionPago`,`condicionpago`.`DescripCondicionP` AS `CondicionPago`,`cobros`.`idAperturaCaja` AS `idAperturaCaja` from (((`cobros` join `voluntarios` on((`cobros`.`idVoluntarios` = `voluntarios`.`idVoluntarios`))) join `socioprotector` on((`cobros`.`idSocioProtector` = `socioprotector`.`idSocioProtector`))) join `condicionpago` on((`cobros`.`idCondicionPago` = `condicionpago`.`idCondicionPago`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `compras_v`
--

/*!50001 DROP TABLE IF EXISTS `compras_v`*/;
/*!50001 DROP VIEW IF EXISTS `compras_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `compras_v` AS select `facturacompra`.`NroFact` AS `NroFact`,`facturacompra`.`NroTimbrado` AS `NroTimbrado`,`facturacompra`.`Fecha` AS `Fecha`,`facturacompra`.`idCondicionPago` AS `idCondicionPago`,`facturacompra`.`idMonedas` AS `idMonedas`,`facturacompra`.`idProveedores` AS `idProveedores`,`proveedores`.`Ruc` AS `ruc`,`proveedores`.`DescripProveedores` AS `DescripProveedores`,0 AS `total`,`condicionpago`.`DescripCondicionP` AS `CondicionPago` from ((`facturacompra` join `proveedores` on((`facturacompra`.`idProveedores` = `proveedores`.`idProveedores`))) join `condicionpago` on((`facturacompra`.`idCondicionPago` = `condicionpago`.`idCondicionPago`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `comprasdet_v`
--

/*!50001 DROP TABLE IF EXISTS `comprasdet_v`*/;
/*!50001 DROP VIEW IF EXISTS `comprasdet_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `comprasdet_v` AS select `factcompradet`.`NroFact` AS `NroFact`,`factcompradet`.`NroTimbrado` AS `NroTimbrado`,`factcompradet`.`idItems` AS `idItems`,`items`.`DescripItems` AS `DescripItems`,`factcompradet`.`Cantidad` AS `Cantidad`,`factcompradet`.`PrecioCostUnitario` AS `PrecioCostUnitario`,`factcompradet`.`PorcIva` AS `PorcIva`,(`factcompradet`.`Cantidad` * `factcompradet`.`PrecioCostUnitario`) AS `Subtotal`,if((`factcompradet`.`PorcIva` = 0),(`factcompradet`.`Cantidad` * `factcompradet`.`PrecioCostUnitario`),0) AS `Exentas`,if((`factcompradet`.`PorcIva` = 5),(`factcompradet`.`Cantidad` * `factcompradet`.`PrecioCostUnitario`),0) AS `Gravadas5`,if((`factcompradet`.`PorcIva` = 10),(`factcompradet`.`Cantidad` * `factcompradet`.`PrecioCostUnitario`),0) AS `Gravadas10`,if((`factcompradet`.`PorcIva` = 5),((`factcompradet`.`Cantidad` * `factcompradet`.`PrecioCostUnitario`) / 21),0) AS `Liquidacion5`,if((`factcompradet`.`PorcIva` = 10),((`factcompradet`.`Cantidad` * `factcompradet`.`PrecioCostUnitario`) / 11),0) AS `Liquidacion10` from (`factcompradet` join `items` on((`factcompradet`.`idItems` = `items`.`idItems`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `compratotal_v`
--

/*!50001 DROP TABLE IF EXISTS `compratotal_v`*/;
/*!50001 DROP VIEW IF EXISTS `compratotal_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `compratotal_v` AS select `comprasdet_v`.`NroFact` AS `NroFact`,`comprasdet_v`.`NroTimbrado` AS `NroTimbrado`,sum(`comprasdet_v`.`Exentas`) AS `Exentas`,sum(`comprasdet_v`.`Gravadas5`) AS `Gravadas5`,sum(`comprasdet_v`.`Liquidacion5`) AS `Liquidacion5`,sum(`comprasdet_v`.`Gravadas10`) AS `Gravadas10`,sum(`comprasdet_v`.`Liquidacion10`) AS `Liquidacion10`,sum((`comprasdet_v`.`Liquidacion5` + `comprasdet_v`.`Liquidacion10`)) AS `TotalIva`,sum(((`comprasdet_v`.`Exentas` + `comprasdet_v`.`Gravadas5`) + `comprasdet_v`.`Gravadas10`)) AS `Total` from `comprasdet_v` group by `comprasdet_v`.`NroFact`,`comprasdet_v`.`NroTimbrado` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `conceptos_notadebito_v`
--

/*!50001 DROP TABLE IF EXISTS `conceptos_notadebito_v`*/;
/*!50001 DROP VIEW IF EXISTS `conceptos_notadebito_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `conceptos_notadebito_v` AS select `conceptos_notadebito`.`idConceptoNotaDebito` AS `idConceptoNotaDebito`,`conceptos_notadebito`.`Descripcion` AS `Descripcion`,`conceptos_notadebito`.`idCategoria` AS `idCategoria`,`categorias`.`CodCategorias` AS `DescripCategoria`,`categorias`.`PorcIva` AS `PorcIva` from (`conceptos_notadebito` join `categorias` on((`conceptos_notadebito`.`idCategoria` = `categorias`.`idCategorias`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `donaciones_v`
--

/*!50001 DROP TABLE IF EXISTS `donaciones_v`*/;
/*!50001 DROP VIEW IF EXISTS `donaciones_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `donaciones_v` AS select `donaciones`.`idDonaciones` AS `idDonaciones`,`donaciones`.`DescripDonaciones` AS `DescripDonaciones`,`donaciones`.`CantidadMonto` AS `CantidadMonto`,`donaciones`.`idMonedas` AS `idMonedas`,`monedas`.`Descrip` AS `Moneda`,`donaciones`.`idAperturaCaja` AS `idAperturaCaja`,`donaciones`.`Fecha` AS `fecha` from (`donaciones` join `monedas` on((`donaciones`.`idMonedas` = `monedas`.`idMonedas`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `items_vista`
--

/*!50001 DROP TABLE IF EXISTS `items_vista`*/;
/*!50001 DROP VIEW IF EXISTS `items_vista`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `items_vista` AS select `items`.`idItems` AS `idItems`,`items`.`DescripItems` AS `DescripItems`,`items`.`PrecioCosto` AS `PrecioCosto`,`items`.`idItem_Clasificacion` AS `idItem_Clasificacion`,`item_clasificacion`.`DescripClas` AS `Clasificacion`,`items`.`idMarcas` AS `idMarcas`,`marcas`.`DescripMarcas` AS `DescripMarcas`,`items`.`idCategorias` AS `idCategorias`,`categorias`.`CodCategorias` AS `CodCategorias`,`categorias`.`PorcIva` AS `porciva`,`items`.`Existecia` AS `Existecia` from (((`items` join `item_clasificacion` on((`items`.`idItem_Clasificacion` = `item_clasificacion`.`idItem_Clasificacion`))) join `marcas` on((`items`.`idMarcas` = `marcas`.`idMarcas`))) join `categorias` on((`items`.`idCategorias` = `categorias`.`idCategorias`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `nctotales_v`
--

/*!50001 DROP TABLE IF EXISTS `nctotales_v`*/;
/*!50001 DROP VIEW IF EXISTS `nctotales_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `nctotales_v` AS select `notacreditocompradet_v`.`NroNotaCredito` AS `NroNotaCredito`,`notacreditocompradet_v`.`NroTimbrado` AS `NroTimbrado`,sum(`notacreditocompradet_v`.`Exentas`) AS `Exentas`,sum(`notacreditocompradet_v`.`Gravadas5`) AS `Gravadas5`,sum(`notacreditocompradet_v`.`Liquidacion5`) AS `Liquidacion5`,sum(`notacreditocompradet_v`.`Gravadas10`) AS `Gravadas10`,sum(`notacreditocompradet_v`.`Liquidacion10`) AS `Liquidacion10`,sum((`notacreditocompradet_v`.`Liquidacion5` + `notacreditocompradet_v`.`Liquidacion10`)) AS `TotalIva`,sum(((`notacreditocompradet_v`.`Exentas` + `notacreditocompradet_v`.`Gravadas5`) + `notacreditocompradet_v`.`Gravadas10`)) AS `Total` from `notacreditocompradet_v` group by `notacreditocompradet_v`.`NroNotaCredito`,`notacreditocompradet_v`.`NroTimbrado` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ndtotales_v`
--

/*!50001 DROP TABLE IF EXISTS `ndtotales_v`*/;
/*!50001 DROP VIEW IF EXISTS `ndtotales_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ndtotales_v` AS select `notadebitocompradet_v`.`NroNotaDebitoCompra` AS `NroNotaDebitoCompra`,`notadebitocompradet_v`.`NroTimbrado` AS `NroTimbrado`,sum(`notadebitocompradet_v`.`Exentas`) AS `Exentas`,sum(`notadebitocompradet_v`.`Gravadas5`) AS `Gravadas5`,sum(`notadebitocompradet_v`.`Liquidacion5`) AS `Liquidacion5`,sum(`notadebitocompradet_v`.`Gravadas10`) AS `Gravadas10`,sum(`notadebitocompradet_v`.`Liquidacion10`) AS `Liquidacion10`,sum((`notadebitocompradet_v`.`Liquidacion5` + `notadebitocompradet_v`.`Liquidacion10`)) AS `TotalIva`,sum(((`notadebitocompradet_v`.`Exentas` + `notadebitocompradet_v`.`Gravadas5`) + `notadebitocompradet_v`.`Gravadas10`)) AS `Total` from `notadebitocompradet_v` group by `notadebitocompradet_v`.`NroNotaDebitoCompra`,`notadebitocompradet_v`.`NroTimbrado` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `notacreditocompra_v`
--

/*!50001 DROP TABLE IF EXISTS `notacreditocompra_v`*/;
/*!50001 DROP VIEW IF EXISTS `notacreditocompra_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `notacreditocompra_v` AS select `notacreditocompra`.`NroNotaCredito` AS `NroNotaCredito`,`notacreditocompra`.`NroTimbrado` AS `NroTimbrado`,`notacreditocompra`.`Fecha` AS `Fecha`,`notacreditocompra`.`NroFact` AS `NroFact`,`notacreditocompra`.`NroTimbradoFactura` AS `NroTimbradoFactura`,`compras_v`.`idProveedores` AS `idProveedores`,`compras_v`.`ruc` AS `ruc`,`compras_v`.`DescripProveedores` AS `RazonSocial` from (`notacreditocompra` join `compras_v` on(((`notacreditocompra`.`NroFact` = `compras_v`.`NroFact`) and (`notacreditocompra`.`NroTimbradoFactura` = `compras_v`.`NroTimbrado`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `notacreditocompradet_v`
--

/*!50001 DROP TABLE IF EXISTS `notacreditocompradet_v`*/;
/*!50001 DROP VIEW IF EXISTS `notacreditocompradet_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `notacreditocompradet_v` AS select `notacreditocompradet`.`NroNotaCredito` AS `NroNotaCredito`,`notacreditocompradet`.`NroTimbrado` AS `NroTimbrado`,`notacreditocompradet`.`idItems` AS `idItems`,`items`.`DescripItems` AS `DescripItems`,`notacreditocompradet`.`cantidad` AS `cantidad`,`notacreditocompradet`.`preciocosto` AS `preciocosto`,`notacreditocompradet`.`porciva` AS `porciva`,if((`notacreditocompradet`.`porciva` = 0),(`notacreditocompradet`.`cantidad` * `notacreditocompradet`.`preciocosto`),0) AS `Exentas`,if((`notacreditocompradet`.`porciva` = 5),(`notacreditocompradet`.`cantidad` * `notacreditocompradet`.`preciocosto`),0) AS `Gravadas5`,if((`notacreditocompradet`.`porciva` = 10),(`notacreditocompradet`.`cantidad` * `notacreditocompradet`.`preciocosto`),0) AS `Gravadas10`,if((`notacreditocompradet`.`porciva` = 5),((`notacreditocompradet`.`cantidad` * `notacreditocompradet`.`preciocosto`) / 21),0) AS `Liquidacion5`,if((`notacreditocompradet`.`porciva` = 10),((`notacreditocompradet`.`cantidad` * `notacreditocompradet`.`preciocosto`) / 11),0) AS `Liquidacion10` from (`notacreditocompradet` join `items` on((`notacreditocompradet`.`idItems` = `items`.`idItems`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `notadebitocompra_v`
--

/*!50001 DROP TABLE IF EXISTS `notadebitocompra_v`*/;
/*!50001 DROP VIEW IF EXISTS `notadebitocompra_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `notadebitocompra_v` AS select `notadebitocompra`.`NroNotaDebitoCompra` AS `NroNotaDebitoCompra`,`notadebitocompra`.`NroTimbrado` AS `NroTimbrado`,`notadebitocompra`.`Fecha` AS `Fecha`,`notadebitocompra`.`NroFact` AS `NroFact`,`notadebitocompra`.`NroTimbradoFactura` AS `NroTimbradoFactura`,`compras_v`.`idProveedores` AS `idProveedores`,`compras_v`.`ruc` AS `ruc`,`compras_v`.`DescripProveedores` AS `RazonSocial` from (`notadebitocompra` join `compras_v` on(((`notadebitocompra`.`NroFact` = `compras_v`.`NroFact`) and (`notadebitocompra`.`NroTimbradoFactura` = `compras_v`.`NroTimbrado`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `notadebitocompradet_v`
--

/*!50001 DROP TABLE IF EXISTS `notadebitocompradet_v`*/;
/*!50001 DROP VIEW IF EXISTS `notadebitocompradet_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `notadebitocompradet_v` AS select `notadebitocompradet`.`NroNotaDebitoCompra` AS `NroNotaDebitoCompra`,`notadebitocompradet`.`NroTimbrado` AS `NroTimbrado`,`notadebitocompradet`.`idConceptoNotaDebito` AS `idConceptoNotaDebito`,`conceptos_notadebito`.`Descripcion` AS `Descripcion`,`notadebitocompradet`.`cantidad` AS `cantidad`,`notadebitocompradet`.`Precio` AS `Precio`,`notadebitocompradet`.`PorcIva` AS `PorcIva`,(`notadebitocompradet`.`Precio` * `notadebitocompradet`.`cantidad`) AS `Subtotal`,if((`notadebitocompradet`.`PorcIva` = 0),(`notadebitocompradet`.`cantidad` * `notadebitocompradet`.`Precio`),0) AS `Exentas`,if((`notadebitocompradet`.`PorcIva` = 5),(`notadebitocompradet`.`cantidad` * `notadebitocompradet`.`Precio`),0) AS `Gravadas5`,if((`notadebitocompradet`.`PorcIva` = 10),(`notadebitocompradet`.`cantidad` * `notadebitocompradet`.`Precio`),0) AS `Gravadas10`,if((`notadebitocompradet`.`PorcIva` = 5),((`notadebitocompradet`.`cantidad` * `notadebitocompradet`.`Precio`) / 21),0) AS `Liquidacion5`,if((`notadebitocompradet`.`PorcIva` = 10),((`notadebitocompradet`.`cantidad` * `notadebitocompradet`.`Precio`) / 11),0) AS `Liquidacion10` from (`notadebitocompradet` join `conceptos_notadebito` on((`notadebitocompradet`.`idConceptoNotaDebito` = `conceptos_notadebito`.`idConceptoNotaDebito`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vistausuario`
--

/*!50001 DROP TABLE IF EXISTS `vistausuario`*/;
/*!50001 DROP VIEW IF EXISTS `vistausuario`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vistausuario` AS select `usuario`.`Usuclave` AS `Usuclave`,`usuario`.`Alias` AS `Alias`,`usuario`.`UsuActivo` AS `UsuActivo`,`usuario`.`idVoluntarios` AS `idVoluntarios`,`voluntarios`.`Nombre` AS `nombre` from (`usuario` join `voluntarios` on((`usuario`.`idVoluntarios` = `voluntarios`.`idVoluntarios`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-11 23:02:10
