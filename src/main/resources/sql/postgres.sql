insert into CUERPOSESTADO (id, descripcion, username_alta, fecha_alta, username_modif, fecha_modif, username_baja, fecha_baja) values(1, 'Policía Nacional', 'system', current_date, null, null,null,null);
insert into CUERPOSESTADO (id, descripcion, username_alta, fecha_alta, username_modif, fecha_modif, username_baja, fecha_baja) values(2, 'Guardia Civil', 'system', current_date, null, null,null,null);
insert into CUERPOSESTADO (id, descripcion, username_alta, fecha_alta, username_modif, fecha_modif, username_baja, fecha_baja) values(3, 'Cuerpo General de la Administración', 'system', current_date, null, null,null,null);
insert into CUERPOSESTADO (id, descripcion, username_alta, fecha_alta, username_modif, fecha_modif, username_baja, fecha_baja) values(4, 'Cuerpos Comunes de la Defensa', 'system', current_date, null, null,null,null);
insert into CUERPOSESTADO (id, descripcion, username_alta, fecha_alta, username_modif, fecha_modif, username_baja, fecha_baja) values(5, 'Contratados', 'system', current_date, null, null,null,null);
insert into CUERPOSESTADO (id, descripcion, username_alta, fecha_alta, username_modif, fecha_modif, username_baja, fecha_baja) values(6, 'Correos y Telégrafos', 'system', current_date, null, null,null,null);
 
 
insert into PUESTOSTRABAJO values(1, 'Teniente');
insert into PUESTOSTRABAJO values(2, 'Comandante');
insert into PUESTOSTRABAJO values(3, 'Jefe de Apoyo');
insert into PUESTOSTRABAJO values(4, 'Personal de Apoyo');
insert into PUESTOSTRABAJO values(5, 'Secretaria de N30');
insert into PUESTOSTRABAJO values(6, 'Jefe de Equipo Inspecciones');
insert into PUESTOSTRABAJO values(7, 'Inspector-Auditor');
insert into PUESTOSTRABAJO values(8, 'Jefe de Negociado');
insert into PUESTOSTRABAJO values(9, 'Fuera de Catálogo (Conductores)');
insert into PUESTOSTRABAJO values(10, 'Responsable Estudios y Programas');
insert into PUESTOSTRABAJO values(11, 'Jefe Sección');
insert into PUESTOSTRABAJO values(12, 'Secretario Técnico');
insert into PUESTOSTRABAJO values(13, 'Subdirector General');
 
Insert into TIPO_EQUIPO (ID_TIPO_EQUIPO, DESCRIPCION) Values (1, 'Gestión  Inspección Área Prevención de Riesgos Laborales.'),
	(2, 'Gestión Inspecciones Generales.'),
	(3, 'Gestión Inspecciones de Seguimiento.'),
	(4, 'Gestión Inspecciones Incidentales.'),
	(5, 'Gestión Inspecciones Prevención de Riesgos Laborales.'),
	(6, 'Gestión Inspecciones Temáticas (CIES).'),
	(7, 'Gestión Servicio de Calidad y Quejas'),
	(8, 'Otros');

 



--insert into users (username, prim_apellido, segundo_apellido, correo, doc_identidad, envio_notif, estado, fecha_alta, fecha_baja, fecha_inactivo, fecha_modificacion,
--  nombre, num_identificacion, password, role, telefono, username_alta, username_baja,  username_modif,
--  fecha_destino_ipss, ID_PUESTO,ID_CUERPO, nivel) 
--  values ( 'system', 'system', 'system', 'correo@correo.es', '111111111', 'NO', 'ACTIVO', '01/09/2016', NULL, NULL, NULL, 'Silvia',
--  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2016', 1, 2, 20),
--  
insert into users (username, prim_apellido, segundo_apellido, correo, doc_identidad, envio_notif, estado, fecha_alta, fecha_baja, fecha_inactivo, fecha_modificacion,
  nombre, num_identificacion, password, role, telefono, username_alta, username_baja,  username_modif,
  fecha_destino_ipss, ID_PUESTO,ID_CUERPO, nivel) 
  values ( 'silpe', 'apellido1', 'apellido2', 'correo@correo.es', '111111111', 'SI', 'ACTIVO', '01/09/2016', NULL, NULL, NULL, 'Silvia',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2016', 1, 2, 20),
  
 ( 'pedro', 'CARRETERO', 'LIAU', 'correo@correo.es', '111111111', 'SI', 'ACTIVO', '01/09/2016', NULL, NULL, NULL, 'PEDRO ',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '10323', 'user_alta', null, null, '05/06/2000', 2, 2, 18),
   
( 'jmanuel', 'SIERRA', 'EXOJO', 'correo@correo.es', 'n10704', 'SI', 'ACTIVO', '01/01/2011', NULL, NULL, NULL, 'JOSE MANUEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '22/06/2016', 3, 2, 20),
  	
	( 'alopez', 'LOPEZ', 'BETRIAN', 'correo@correo.es', 'n10971', 'SI', 'ACTIVO', '12/11/2007', NULL, NULL, NULL, 'ANTONIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/04/2014', 5, 3, 15),
    
	( 'fvilchews', 'VILCHES', 'RENTERO', 'correo@correo.es', 'n11718', 'SI', 'ACTIVO', '01/09/2016', NULL, NULL, NULL, 'FRANCISCO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '17/04/2015', 6, 1, 28),
     
	 ( 'ajangulo', 'ANGULO', 'BALLARIN', 'correo@correo.es', 'n13453', 'SI', 'ACTIVO', '02/2/2007', NULL, NULL, NULL, 'ANTONIO JAVIER',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '12/01/2009', 7, 2, 27),
      ( 'jencuentra', 'ENCUENTRA', 'BAGUES', 'correo@correo.es', 'n13545', 'SI', 'ACTIVO', '05/09/1999', NULL, NULL, NULL, 'JESUS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '25/09/2010', 7, 1, 27),
      
( 'egomez', 'GOMEZ', 'ALMERO', 'correo@correo.es', 'n13575', 'SI', 'ACTIVO', '01/10/2011', NULL, NULL, NULL, 'ENRIQUE',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/07/2012', 8, 3, 16),
  
( 'jcarranz', 'ARRANZ', 'APAOLAZA', 'correo@correo.es', 'n14189', 'SI', 'ACTIVO', '10/08/2000', NULL, NULL, NULL, 'JUAN CARLOS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/05/2016', 7, 2, 27),
  
 ( 'faconde', 'CONDE', 'FALCON', 'correo@correo.es', 'n14975', 'SI', 'ACTIVO', '01/09/2000', NULL, NULL, NULL, 'FRANCISCO ANDRES',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/04/2010', 7, 2, 27),
  
      
 ( 'jbarnils', 'BARNILS', 'COSTA', 'correo@correo.es', 'n15416', 'SI', 'ACTIVO', '01/09/2013', NULL, NULL, NULL, 'JUAN',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2015', 7, 2, 27),
  
( 'jmarquez', 'MARQUES', 'BERDEJO', 'correo@correo.es', 'N15432', 'SI', 'ACTIVO', '01/09/2007', NULL, NULL, NULL, 'JAVIER',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '11/08/2010', 7, 1, 27),
  
 ( 'cgonzalez', 'GONZÁLEZ', 'CASTILLO', 'correo@correo.es', 'n15967', 'SI', 'ACTIVO', '01/04/2014', NULL, NULL, NULL, 'CARLOS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2015', 7, 1, 27),
   
( 'sfcarrasco', 'CARRASCO', 'JIMENEZ', 'correo@correo.es', 'n16115', 'SI', 'ACTIVO', '10/07/2000', NULL, NULL, NULL, 'SALVADOR FRANCISCO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2011', 7, 1, 27),
        
( 'slaguia', 'LAGUIA', 'FERNANDEZ', 'correo@correo.es', 'n16490', 'SI', 'ACTIVO', '01/06/2006', NULL, NULL, NULL, 'SEGUNDO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/03/2010', 7, 4, 27),
  
( 'parribas', 'ARRIBAS', 'MARTINEZ', 'correo@correo.es', 'n16603', 'SI', 'ACTIVO', '01/02/2003', NULL, NULL, NULL, 'PEDRO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/08/2008', 7, 4, 27),
 ( 'acabrero', 'CABRERO', 'GARCIA', 'correo@correo.es', 'n16716', 'SI', 'ACTIVO', '01/09/2000', NULL, NULL, NULL, 'ANDRES',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2004', 6, 2, 28),
( 'msoria', 'SORIA', 'TORRALBA', 'correo@correo.es', 'n17620', 'SI', 'ACTIVO', '01/09/2000', NULL, NULL, NULL, 'MIGUEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2002', 6, 1, 28),
 ( 'amateos', 'MATEOS', 'CHICO', 'correo@correo.es', 'n17702', 'SI', 'ACTIVO', '01/09/2000', NULL, NULL, NULL, 'AMADEO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2005', 6, 2, 28),
  ( 'emartinez', 'MARTINEZ', 'ALLER', 'correo@correo.es', 'n18342', 'SI', 'ACTIVO', '01/09/2009', NULL, NULL, NULL, 'ELIAS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2013', 6, 1, 28),
  
  ( 'japeruyero', 'PERUYERO', 'MARTINEZ', 'correo@correo.es', 'n18440', 'SI', 'ACTIVO', '20/03/2008', NULL, NULL, NULL, 'JOSE ANTONIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '11/08/2012', 6, 2, 28),
  
   ( 'jaesteban', 'ESTEBAN', 'GOMEZ', 'correo@correo.es', 'n19570', 'SI', 'ACTIVO', '01/09/2010', NULL, NULL, NULL, 'JOSE ANTONIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2012', 6, 2, 28),
  
   ( 'jrosas', 'ROSAS', 'MARTIN', 'correo@correo.es', 'n19662', 'SI', 'ACTIVO', '01/09/2013', NULL, NULL, NULL, 'JOSE',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2015', 4, 2, 18),
  
   ( 'mnavarro', 'NAVARRO', 'GRAELLS', 'correo@correo.es', 'n19959', 'SI', 'ACTIVO', '01/09/2011', NULL, NULL, NULL, 'MONTSERRAT',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2012', 4, 2, 18),
  
 ( 'maserrano', 'SERRANO', 'ARGUELLO', 'correo@correo.es', 'n20645', 'SI', 'ACTIVO', '01/07/2007', NULL, NULL, NULL, 'MIGUEL ANGEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2011', 4, 1, 18),
  
   ( 'cjsancha', 'SANCHA', 'HERRERA', 'correo@correo.es', 'n21129', 'SI', 'ACTIVO', '01/09/2012', NULL, NULL, NULL, 'CARLOS JAIME',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2013', 4, 2, 18),
  
   ( 'fmartcasabas', 'MARTINEZ', 'CASBAS', 'correo@correo.es', 'n21694', 'SI', 'ACTIVO', '01/09/2016', NULL, NULL, NULL, 'FRANCISCO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2016', 4, 2, 18),
 ( 'ciibarguren', 'IBARGUREN', 'LEON', 'correo@correo.es', 'n21953', 'SI', 'ACTIVO', '01/10/2010', NULL, NULL, NULL, 'CARLOS IGNACIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/07/2013', 8, 3, 16),
  
 ( 'egomezcorino', 'CARRILES', 'CORINO', 'correo@correo.es', 'n21991', 'SI', 'ACTIVO', '23/11/2004', NULL, NULL, NULL, 'JOSE PEDRO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/08/2008', 8, 2, 16),
  
 ( 'jatristan', 'TRISTAN', 'OSTA', 'correo@correo.es', 'n21999', 'SI', 'ACTIVO', '01/10/2009', NULL, NULL, NULL, 'JESUS ANGEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/07/2011', 8, 4, 16),
  
  ( 'jmcervantes', 'CERVANTES', 'PARDO', 'correo@correo.es', 'n22046', 'SI', 'ACTIVO', '27/09/2011', NULL, NULL, NULL, 'JOSE MARIA',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2013', 7, 1, 27),
  
  ( 'fdonday', 'DONDAY', 'GALVEZ', 'correo@correo.es', 'n22149', 'SI', 'ACTIVO', '24/01/2004', NULL, NULL, NULL, 'FRANCISCO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '23/11/2009', 7, 2, 27),
  
  
 ( 'alopezc', 'LOPEZ', 'CONTRERAS', 'correo@correo.es', 'n22162', 'SI', 'ACTIVO', '01/04/2011', NULL, NULL, NULL, 'ANTONIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2012', 7, 4, 27),
  
 ( 'fsempre', 'SEMPERE', 'PEÑA', 'correo@correo.es', 'n22252', 'SI', 'ACTIVO', '01/04/2011', NULL, NULL, NULL, 'FERNANDO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/06/2013', 7, 1, 27),
  
  ( 'jcgonzalez', 'GONZÁLEZ', 'VALENCIA', 'correo@correo.es', 'n22465', 'SI', 'ACTIVO', '01/04/2000', NULL, NULL, NULL, 'JESUS CARLOS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/12/2012', 7, 4, 27),
  
  ( 'jmalberich', 'ALBERICH', 'LANDABURU', 'correo@correo.es', 'n22468', 'SI', 'ACTIVO', '01/04/2010', NULL, NULL, NULL, 'JUAN MANUEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '18/11/2013', 7, 2, 27),
  
   ( 'fjoliva', 'OLIVA', 'GARCIA', 'correo@correo.es', 'n22664', 'SI', 'ACTIVO', '11/06/2002', NULL, NULL, NULL, 'FRANCISCO JAVIER',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2007', 4, 1, 18),
  
   ( 'mfgonzalez', 'GONZALEZ', 'FERNANDEZ', 'correo@correo.es', 'n22962', 'SI', 'ACTIVO', '01/09/2011', NULL, NULL, NULL, 'MARCELO FRANCISCO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2012', 4, 2, 18),
  
 ( 'jmartinez', 'MARTINEZ', 'GANDIA BANCES', 'correo@correo.es', 'n23093', 'SI', 'ACTIVO', '01/06/2006', NULL, NULL, NULL, 'JAVIER',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/12/2009', 4, 4, 18),
  
  ( 'cperez', 'PEREZ', 'FERNANDEZ', 'correo@correo.es', 'n23958', 'SI', 'ACTIVO', '01/07/2007', NULL, NULL, NULL, 'CONSUELO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2011', 4, 2, 18),
  
  ( 'jsastre', 'SASTRE', 'SANCHEZ', 'correo@correo.es', 'n24286', 'SI', 'ACTIVO', '01/09/2010', NULL, NULL, NULL, 'JOSE',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2011', 4, 4, 18),
  
   ( 'amora', 'MORA', 'GALAN', 'correo@correo.es', 'n24388', 'SI', 'ACTIVO', '01/09/2006', NULL, NULL, NULL, 'ALBERTO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'ADMIN', '665566544', 'user_alta', null, null, '01/09/2000', 4, 1, 18),
     
 ( 'ajgarcia', 'GARCIA', 'ABASCAL', 'correo@correo.es', 'n24519', 'SI', 'ACTIVO', '01/02/2007', NULL, NULL, NULL, 'ANGEL JESUS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '22/06/2009', 3, 2, 21),
  
 ( 'mafan', 'AFAN', 'FAJARDO', 'correo@correo.es', 'n24639', 'SI', 'ACTIVO', '01/01/2011', NULL, NULL, NULL, 'MIGUEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '22/06/2016', 3, 1, 21),
  
 ( 'parbona', 'ARBONA', 'CAMPOMAR', 'correo@correo.es', 'n25044', 'NO', 'ACTIVO', '01/02/2003', NULL, NULL, NULL, 'PEDRO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '12/08/2007', 9, 2, 21),
  
  ( 'jicerezo', 'CEREZO', 'HERNANDEZ', 'correo@correo.es', 'N25342', 'SI', 'ACTIVO', '01/01/2011', NULL, NULL, NULL, 'JOSE IGNACIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '22/06/2016', 9, 1, 21),
     
 ( 'jlcalonge', 'CALONGE', 'DELSO', 'correo@correo.es', 'n25583', 'SI', 'ACTIVO', '01/02/2007', NULL, NULL, NULL, 'JOSE LUIS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '22/06/2009', 9, 2, 21),
  
  ( 'jcgalan', 'GALAN', 'VIVAR', 'correo@correo.es', 'n25655', 'SI', 'ACTIVO', '11/04/2001', NULL, NULL, NULL, 'JOSE CARLOS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'EQUIPO_INSPECCIONES', '665566544', 'user_alta', null, null, '22/04/2013', 9, 1, 21);
  
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (1, 'cuestionario modificado', '01/09/2016', 'cuestionarios 1', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (2, 'cuestionario modificado', '01/09/2016', 'cuestionarios 2', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (3, 'cuestionario modificado', '01/09/2016', 'cuestionarios 3', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (4, 'cuestionario modificado', '01/09/2016', 'cuestionarios 4', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (5, 'cuestionario modificado', '01/09/2016', 'cuestionarios 5', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (6, 'cuestionario modificado', '01/09/2016', 'cuestionarios 6', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (7, 'cuestionario modificado', '01/09/2016', 'cuestionarios 7', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (8, 'cuestionario modificado', '01/09/2016', 'cuestionarios 8', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (9, 'cuestionario modificado', '01/09/2016', 'cuestionarios 9', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (10, 'cuestionario modificado', '01/09/2016', 'cuestionarios 10', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (11, 'cuestionario modificado', '01/09/2016', 'cuestionarios 11', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (12, 'cuestionario modificado', '01/09/2016', 'cuestionarios 12', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (13, 'cuestionario modificado', '01/09/2016', 'cuestionarios 13', 'EQUIPO_INSPECCIONES', 'userAltaAlerta');

  -- modelos cuestionarios
  /*
create or replace function bytea_import(p_path text, p_result out bytea)
                   language plpgsql as $$
declare
  l_oid oid;
  r record;
begin
  p_result := '';
  select lo_import(p_path) into l_oid;
  for r in ( select data
             from pg_largeobject
             where loid = l_oid
             order by pageno ) loop
    p_result = p_result || r.data;
  end loop;
  perform lo_unlink(l_oid);
end;$$;
*/

Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (1, 'codigo1', 'CUESTIONARIO.CIES', 1, 'CUESTIONARIO.CIES.doc');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (2, 'codigo2', 'A - GC - Cuestionario Zona GENERICO', 2, 'A - GC - Cuestionario Zona GENERICO.docx');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (3, 'codigo3', 'A.- Cuestionario  JEFATURA SUPERIOR ', 3, 'A.- Cuestionario  JEFATURA SUPERIOR .docx');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (4, 'codigo4', 'B - GC - Cuestionario Comandancia GENERICO', 4, 'B - GC - Cuestionario Comandancia GENERICO.docx');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (5, 'codigo5', 'B.- Cuestionario COMISARIA PROVINCIAL', 5, 'B.- Cuestionario COMISARIA PROVINCIAL.docx');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (6, 'codigo6', 'C - GC - Cuestionario Compañia GENERICO', 6, 'C - GC - Cuestionario Compañía GENERICO.docx');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (7, 'codigo7', 'C.- Cuestionario  COMISARÍA LOCAL TIPO V0', 7, 'C.- Cuestionario  COMISARÍA LOCAL TIPO V0.docx');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (8, 'codigo8', 'CUESTIONARIO PRLCNP', 8, 'CUESTIONARIO PRLCNP.doc');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (10, 'codigo10', 'CUESTIONARIO.CIES UNIDAD DE VIGILANCIA', 10, 'CUESTIONARIO.CIES UNIDAD DE VIGILANCIA.doc');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (11, 'codigo11', 'CUESTIONARIOPRLGC', 11, 'CUESTIONARIOPRLGC.doc');
Insert into  modeloscuestionarios (id,codigo,descripcion,id_documento,nombre) Values (12, 'codigo12', 'D.- Cuestionario COMISARÍA DISTRITO V0', 12, 'D.- Cuestionario COMISARÍA DISTRITO V0.docx');

insert into documentos (id, fichero, tipo_contenido, nombre) values (1, bytea_import('C:\Program Files\PostgreSQL\9.5\data\CUESTIONARIO.CIES.doc'), 'application/msword','CUESTIONARIO.CIES.doc');
insert into documentos (id, fichero, tipo_contenido, nombre) values (2, bytea_import('C:\Program Files\PostgreSQL\9.5\data\A - GC - Cuestionario Zona GENERICO.docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','A - GC - Cuestionario Zona GENERICO.docx');
insert into documentos (id, fichero, tipo_contenido, nombre) values (3, bytea_import('C:\Program Files\PostgreSQL\9.5\data\A.- Cuestionario  JEFATURA SUPERIOR .docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','A.- Cuestionario  JEFATURA SUPERIOR .docx');
insert into documentos (id, fichero, tipo_contenido, nombre) values (4, bytea_import('C:\Program Files\PostgreSQL\9.5\data\B - GC - Cuestionario Comandancia GENERICO.docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','B - GC - Cuestionario Comandancia GENERICO.docx');
insert into documentos (id, fichero, tipo_contenido, nombre) values (5, bytea_import('C:\Program Files\PostgreSQL\9.5\data\B.- Cuestionario COMISARIA PROVINCIAL.docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','B.- Cuestionario COMISARIA PROVINCIAL.docx');
insert into documentos (id, fichero, tipo_contenido, nombre) values (6, bytea_import('C:\Program Files\PostgreSQL\9.5\data\C - GC - Cuestionario Compania GENERICO.docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','C - GC - Cuestionario Compania GENERICO.docx');
insert into documentos (id, fichero, tipo_contenido, nombre) values (7, bytea_import('C:\Program Files\PostgreSQL\9.5\data\C.- Cuestionario  COMISARIA LOCAL TIPO V0.docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','C.- Cuestionario  COMISARIA LOCAL TIPO V0.docx');
insert into documentos (id, fichero, tipo_contenido, nombre) values (8, bytea_import('C:\Program Files\PostgreSQL\9.5\data\CUESTIONARIO PRLCNP.doc'),'application/msword','CUESTIONARIO PRLCNP.doc');
insert into documentos (id, fichero, tipo_contenido, nombre) values (10, bytea_import('C:\Program Files\PostgreSQL\9.5\data\CUESTIONARIO.CIES UNIDAD DE VIGILANCIA.doc'),'application/msword','CUESTIONARIO.CIES UNIDAD DE VIGILANCIA.doc');
insert into documentos (id, fichero, tipo_contenido, nombre) values (11, bytea_import('C:\Program Files\PostgreSQL\9.5\data\CUESTIONARIOPRLGC.doc'),'application/msword','CUESTIONARIOPRLGC.doc');
insert into documentos (id, fichero, tipo_contenido, nombre) values (12, bytea_import('C:\Program Files\PostgreSQL\9.5\data\D.- Cuestionario COMISARIA DISTRITO V0.docx'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document','D.- Cuestionario COMISARIA DISTRITO V0.docx');

-- Ñapa para alta de nuevos documentos
select setval('documentos_id_seq', 12) from documentos;

  ------------- áreas cuestionario
  
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'INFRAESTRUCTURAS, INSTALACIONES Y MEDIOS BÁSICOS', '1');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', '2');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', '3');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'LIBROS OFICIALES', '4');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'DIRECCIÓN', '5');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'JUNTA DE COORDINACIÓN', '6');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'UNIDAD DE SEGURIDAD', '7');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'ADMINISTRACIÓN', '8');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'SECRETARÍA', '9');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'SERVICIO DE ASISTENCIA SANITARIA', '10');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'SERVICIOS DE ASISTENCIA SOCIAL, JURÍDICA Y CULTURAL', '11');
Insert into  Areascuestionario (id_cuestionario,Area,id) Values (1, 'RELACIONES Y COORDINACIÓN', '12');
  
  ------------- preguntas cuestionario
  
  
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (1, 'Situación y estado del centro', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (2, 'Fecha de creación', 1,'CALENDAR');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (3, 'Instalaciones. Descripción de las mismas', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (4, 'Disponibilidad de accesos para personas con movilidad reducida', 1,'CHECKBOXSINO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (5, 'Capacidad, diferenciando por sexos.', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (6, 'Numero de módulos destinados al alojamiento de hombres', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (7, 'Número de módulos destinados al alojamiento de mujeres', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (8, 'Número de alojamientos para unidades familiares.', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (9, 'Número de módulos independientes. Dimensiones.', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (10, 'Número de habitaciones para llevar a cabo la separación preventiva.', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (11, 'Numero de dependencias y descripción de las mismas, destinadas a alojar a internos, que aun no necesitando atención hospitalaria, por las características de la enfermedad física o psíquica aconseje su separación del resto de los internos', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (12, 'Instalaciones que se utilizan para el alojamiento de internados en virtud del art.89.8 del C.P', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (13, 'Como se consigue la intimidad necesaria para los internos', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (14, 'Espacios para el esparcimiento y recreo. Descripción', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (15, 'Espacio para almacenamiento de equipajes. Caja fuerte. Capacidad', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (16, 'Almacén de equipos básicos de higiene diaria. Descripción', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (17, 'Número de teléfonos públicos para uso de los internos.', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (18, 'Locutorios para abogados y sala de visita', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (19, 'Carteles Informativos. Horarios/Zona Videovigilada', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (20, 'Limpieza: Empresa, número de personal, horario, horario en los servicios de 24 horas, grado de satisfacción, incidencias', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (21, 'Ascensores. Número de ellos y ubicación', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (22, 'Montacargas. Número de ellos y peso máximo', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (23, 'Alta Tensión. Centros de transformación, centrales eléctricas o subestaciones', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (24, 'Baja Tensión. Cuadros eléctricos', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (25, 'Grupos electrógenos', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (26, 'Sistema de Alimentación Ininterrumpida (S.A.I.)/Baterías de acumuladores', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (27, 'Calderas de calefacción y ACS', 1,'CHECKBOXSINO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (28, 'Aire acondicionado y/o ventilación forzada', 1,'CHECKBOXSINO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (29, 'Aparatos a presión (compresores)', 1,'CHECKBOXSINO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (30, 'Depósitos de  Combustibles gaseosos.', 1,'CHECKBOXSINO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (31, 'Combustibles gaseosos.', 1,'CHECKBOXSINO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (32, 'Prevención/control Legionelosis', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (33, 'Libro del edificio', 1,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (34, 'Libros de mantenimiento de cada una de las instalaciones, con sus informes, actas, etc. correspondientes a las operaciones de mantenimiento, revisiones e inspecciones.', 1,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (35, 'PCI. Central de alarmas ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (36, 'PCI Sistemas automáticos de detección y alarma de incendios ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (37, 'PCI Sistemas  manuales (pulsadores) de alarma de incendio ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (38, 'PCI Sirenas de alarma y megafonía ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (39, 'PCI Señalización de equipos contra incendios (de BIE,s, extintores, dirección evacuación, etc.) ', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (40, 'PCI Número de extintores portátiles ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (41, 'PCI Número de bocas de Incendio Equipadas (BIE,s) ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (42, 'PCI Sistema fijo de extinción. Agua. Gas. Espuma', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (43, 'PCI Sistema de abastecimiento de agua. ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (44, 'PCI Hidrantes exteriores ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (45, 'PCI Columnas secas ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (46, 'PCI Alumbrado de emergencias ', 1,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (47, 'PCI Otros ', 1,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (48, 'Plan de Emergencia/ Plan de Autoprotección ', 1,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (49, 'Estructura orgánica', 2,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (50, 'Catálogo de puestos de trabajo personal CNP. Tiempo promedio en el destino.', 2,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (51, 'Catálogo de puestos de trabajo funcionarios Administración General del Estado. Tiempo promedio en el destino.', 2,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (52, 'Catálogo de puestos de trabajo personal laboral. Tiempo promedio en el destino.', 2,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (53, 'Otro personal. Tipo de contratación y requisitos exigidos.', 2,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (54, 'Horarios de trabajo', 2,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (55, 'Número de actividades formativas dirigidas al personal del CNP y resto de funcionarios y empleados públicos, diferenciando las referidas a derechos humanos, régimen de extranjería, seguridad y enfoque de género y violencia contra las mujeres, con expresión de número de participantes en cada una de ellas, así como personal o institución que las impartió', 2,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (56, 'Número de actividades formativas dirigidas al personal no incluido en el párrafo anterior, con expresión de número de participantes en cada una de ellas, así como personal o institución que las impartió', 2,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (57, 'Inventario de recursos materiales, con expresión de fecha de adjudicación o reposición y estado de conservación.', 3,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (58, 'Libros en poder del Centro y fecha de diligencia de cada uno de ellos', 4,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (59, 'Copia del nombramiento del Director', 5,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (60, 'Periodos de sustitución y motivo', 5,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (61, 'Número de respuestas a escritos, quejas y peticiones de los internos o remitidas a la autoridad competentes', 5,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (62, 'Número de reservas de plazas aceptadas o denegadas', 5,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (63, 'Visitas fuera de horarios, autorizadas y causas', 5,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (64, 'Iniciativas trasladadas a superiores', 5,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (65, 'Normas de régimen interior y, en su caso,  modificaciones', 5,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (66, 'Número de entrevistas personales de internos con el director.', 5,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (67, 'Medidas a imponer a los internos que incumplan las normas', 5,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (68, 'Número de reconocimientos médicos ordenados por causas de salud colectiva', 5,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (69, 'Cómo se efectúa el control sobre la custodia y cumplimentación de los libros', 5,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (70, 'Número de reuniones de seguimiento de la gestión entre Comisaria General de extranjería y Fronteras, con directores e instituciones. Remitir actas.', 5,'TEXTAREA+ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (71, 'Número de visitas del Juez competente, especificando los motivos.  ', 5,'TEXTAREA ');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (72, 'Número de inspecciones, con inclusión de fechas, distinguiendo las llevadas a cabo por la autoridad judicial, el CNP, u otros organismos. ', 5,'TEXTAREA ');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (73, 'Datos anuales publicados por la Comisaría General de Extranjería y Fronteras.', 5,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (74, 'Composición de la junta de coordinación.', 6,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (75, 'Normas de funcionamiento.', 6,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (76, 'Remitir actas de reuniones ordinarias o extraordinarias.', 6,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (77, 'Normas de régimen interior.', 6,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (78, 'Directrices e instrucciones sobre organización de los distintos servicios.', 6,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (79, 'Criterios de actuación en cuestiones de alteración del orden, o   incumplimiento de normas.', 6,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (80, 'Sobre peticiones y quejas', 6,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (81, 'Estructura orgánica', 7,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (82, 'Copia del nombramiento del Jefe de la Unidad de Seguridad', 7,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (83, 'Catálogo de puestos de trabajo personal CNP', 7,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (84, 'Criterios de selección', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (85, 'Horarios de trabajo', 7,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (86, 'Directrices de organización', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (87, 'Número de atestados instruidos', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (88, 'Relación de objetos prohibidos intervenidos a internos y destino dados a los mismos ', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (89, 'Relación de objetos no autorizados intervenidos a internos', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (90, 'Custodia y depósito en caja fuerte de objetos de valor y dinero. Procedimiento.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (91, 'Relación de objetos prohibidos intervenidos a visitantes', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (92, 'Relación  de objetos no autorizados intervenidos a visitantes', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (93, 'Control de seguridad al que se somete la correspondencia recibida o remitida.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (94, 'Número de registros de correspondencia autorizadas por la autoridad judicial.', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (95, 'Control de seguridad al que se somete la paquetería recibida.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (96, 'Número de paquetería devuelta por falta de autorización a su apertura.', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (97, 'Número de objetos intervenidos en paquetería y destinos dado a los mismos.', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (98, 'Detalle de instrumentos de control instalados, con expresión de clase, y ubicación (cámaras, arcos detectores, etc.).', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (99, 'Destino dado a las grabaciones y tiempo de permanencia.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (100, 'Número de incautaciones de medios de captación de imágenes', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (101, 'Número de borrados de imágenes', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (102, 'Enumerar lugares donde se presta servicio sin armas de fuego', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (103, 'Número de inspecciones y registros en las instalaciones de uso común.', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (104, 'Número de ocasiones en que, por razón de urgencia, se han adoptados medidas tendentes a restablecer y asegurar el orden', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (105, 'Número de ocasiones en que se ha dado cuenta al director del incumplimiento  de las normas de régimen interior', 7,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (106, 'Número de inspecciones y registros en las instalaciones destinadas a dormitorios de los internos, ropas y enseres de los mismos, indicando las causas y resultado de las mismas.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (107, 'Número de registros personales de los internos, indicando cuales lo fueron mediante desnudo integral, las causas que motivaron los mismos, diferenciando los autorizados por el Director de aquellos otros autorizados por el Jefe de la Unidad de seguridad. Indicar funcionarios encargados de practicarlos y lugar donde se llevaron a cabo.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (108, 'Número de ocasiones en que ha sido necesario el empleo de medios de contención física personal, especificando en que han consistido y causa que las hayan motivado. Separar las que se han tomado previa notificación escrita y aquellas, que por razón de urgencia se haya notificado verbalmente.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (109, 'Número de ocasiones en que ha sido necesaria la separación preventiva individual de algún interno, especificando su causa.', 7,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (110, 'Estructura.', 8,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (111, 'Copia del nombramiento del administrador', 8,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (112, 'Catálogo de puestos de trabajo', 8,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (113, 'Órganos administrativos.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (114, 'Órganos judiciales.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (115, 'El Ministerio Fiscal.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (116, 'El Defensor del Pueblo.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (117, 'El Director del Centro.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (118, 'Otros organismos', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (119, 'Número de solicitud de entrevistas personales con el Director.  ', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (120, 'Número de propuestas de alteración de horarios.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (121, 'Número de informes sobre deficiencias.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (122, 'Número de instrucciones recibidas del servicio sanitario respecto a la alimentación, limpieza y aseo.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (123, 'Medidas adoptadas en base a las creencias religiosas de los internos.', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (124, 'Medios destinados a facilitar la práctica de diferentes confesiones religiosas', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (125, 'Solicitudes de internos para entrar en contactos con ONG,s', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (126, 'Horario de visita a internos, lugar donde se llevan a cabo. Duración de las mismas. Limitaciones.', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (127, 'Como se aborda el problema de idioma de internos.', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (128, 'Número de ejemplares de libros que contiene la biblioteca. Indicar idioma de los mismos.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (129, 'Descripción de medios lúdicos instalados en la sala de estar.', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (130, 'Número de ejemplares de prensa diaria, con indicación de nombre e idioma de edición.', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (131, 'Horarios del centro, con especificación de actividades', 8,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (132, 'Numero de autorizaciones de dispositivos captadores de imágenes.', 8,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (133, 'Identificación de todas personas que prestan servicios en el centro', 8,'TABLA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (134, 'Estructura', 9,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (135, 'Copia del nombramiento del Secretario', 9,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (136, 'Catálogo de puestos de trabajo.', 9,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (137, 'Número de deficiencias o irregularidades apreciadas.', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (138, 'Número de trámites documentales con las Unidades policiales que gestionan los expedientes de extranjeros.', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (139, 'Número de trámites documentales con los juzgados competentes para el control de extranjeros internados.', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (140, 'Número de ingresos y reingresos diferenciados por sexos, meses y periodo de permanencia de  los internos, especificando los motivos. ', 9,'MATRIZINGRESOS');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (141, 'Número de salidas, diferenciadas por sexos, meses y periodo de permanencia de  los internos, especificando los motivos. ', 9,'TABLASALIDAS');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (142, 'A otros centros y de otros centro, especificando los motivos.', 9,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (143, 'Por comparecencias.', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (144, 'Por cuestiones médicas', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (145, 'Número de ceses de internamiento, especificando sus causas.', 9,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (146, 'Número de situaciones que se han producido en las cuales no se pueda llevar a efecto la expulsión de un interno por el art. 89.8 del CP.', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (147, 'Número de reingresos por no poderse llevar a cabo la expulsión, devolución o regreso.', 9,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (148, 'Tratamiento de los datos de carácter personal en los expedientes', 9,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (149, 'Información sobre derechos y obligaciones del nuevo interno. Boletín Informativo.', 9,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (150, 'Médico de la administración, responsable del servicio de Asistencia sanitaria.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (151, 'Número de ATS/Diplomado/Graduado Universitario.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (152, 'Alimentación.', 10,'INPUT+ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (153, 'Aseo de los internos, sus ropas y pertenencias.', 10,'INPUT+ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (154, 'Higiene, calefacción, iluminación, y ventilación de las dependencias.', 10,'INPUT+ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (155, 'Controles periódicos de salubridad.', 10,'INPUT+ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (156, 'Prevención de epidemias y medidas de aislamiento de pacientes infecto-contagiosos', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (157, 'Entrega de elementos de aseo y abrigo', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (158, 'Número de actos médicos.', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (159, 'Número de actos de enfermería.', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (160, 'Servicio sanitario. Descripción de las instalaciones. Capacidad.  Material del que dispone. Capacidad para atender urgencias.', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (161, 'Convenios con otra Admón. pública para casos de hospitalización o especialidades.', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (162, 'Convenios con entidades privadas para casos de hospitalización o especialidades. ', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (163, 'Numero de reconocimientos médicos mediante mandamiento judicial, por negación del interno.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (164, 'Numero de reconocimientos ordenados por el Director.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (165, 'Número de internos de ese Cie, hospitalizados.  ', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (166, 'Numero de partes de lesiones, por las producidas anteriores al ingreso, y que estuvieran descritas en el parte facultativo de lesiones que aportan los funcionarios que hacen entrega del interno.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (167, 'Numero de partes de lesiones, por las producidas anteriores al ingreso, y que no estuvieran descritas en el parte facultativo de lesiones que aportan los funcionarios que hacen entrega del interno.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (168, 'Número de reuniones entre la Comisaria General de Extranjería y Fronteras y representante de las entidades con las que se haya suscritos convenios de sanidad.', 10,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (169, 'Atención farmacéutica. En qué consiste y, cómo y dónde se almacenan los productos', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (170, 'Procedimiento para la solicitud de asistencia médica', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (171, 'Tratamiento de los datos de salud. En, su caso, Entidades concertadas', 10,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (172, 'Estructura ', 11,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (173, 'Número de trabajadores sociales.', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (174, 'Planes o proyectos de actuación presentados.', 11,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (175, 'Planes o proyectos de actuación aprobados. ', 11,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (176, 'Convenios con órganos de otros Ministerios, con entidades públicas y privadas y con ONG,s.', 11,'ADJUNTO');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (177, 'Entidades colaboradoras. Detallando la actividad que realiza cada una.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (178, 'Actividades llevadas a cabo. ', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (179, 'Formación en derechos humanos, extranjería, protección internacional, mediación intercultural y violencia de género. Numero de curso, jornadas, etc. que han recibido los trabajadores sociales.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (180, 'Convenios con colegios de abogados.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (181, 'Dependencias destinadas a asegurar la confidencialidad en la orientación jurídica.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (182, 'Número de entrevistas y comunicaciones con abogados y representantes diplomáticos y consulares.', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (183, 'Horarios de visitas.', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (184, 'Limitaciones establecidas para las visitas.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (185, 'Número de personas por visita.', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (186, 'Donde tienen lugar.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (187, 'Como se informa a los visitantes de las normas de régimen interior.', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (188, 'Número de personas que pueden simultanear la visita.', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (189, 'Comunicaciones telefónicas que se realizan al ingreso', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (190, 'Como se informa de su situación al interno y de las resoluciones administrativas y judiciales. ', 11,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (191, 'Números de casos de asistencia de intérprete', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (192, 'Plazo de entrevista con servicio de asistencia social interno nuevo', 11,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (193, 'Entidades colaboradoras. Detallando la actividad que realiza cada una.', 12,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (194, 'Numero de organizaciones que están autorizadas para realizar visitas al centro.', 12,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (195, 'Acreditaciones concedidas.', 12,'TEXTAREA');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (196, 'Número de visitas de ONG,s.', 12,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (197, 'Número de comunicaciones a ONG,s sobre solicitud de visitas por parte de internos.', 12,'INPUT');
Insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta) Values (198, 'Numero de exámenes a que han estado sometidos sus integrantes, causas y resultados.', 12,'INPUT');

--Ñapa: Comienzo id en 41 debido a problema con las altas de nueva documentacion

Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (41, 'Organigramas de la Unidad, con detalle de sus Unidades y Servicios', '_organigrama', 'DOC, DOCX, PPTX,PPT, PUB');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (42, 'Relación del Equipo Directivo con indicación del nombre y apellidos, categoría y cargo, números de teléfono y dirección de correo electrónico', '_RM', 'DOC, DOCX');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (43, 'Documentación relativa al Plan Territorial. Objetivos operativos fijados para 2015', '_PTyO', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (44, 'Convenios de colaboración con Policías Locales (tanto genéricos como específicos)', '_CCPL', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (45, 'Actas de las Juntas Locales de Seguridad de los años 2014 y 2015', '_AJLS', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (46, 'Plan de Emergencia', '_PE', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (47, 'Evaluación de Riesgos Laborales', '_PRL', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (48, 'Memoria Anual de 2015', '_MA', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (49, 'Copia de las actas de las reuniones sindicales celebradas en 2014 y 2015', '_CAS', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (50, 'Actas de las Comisiones de Coordinación de Policía Judicial de los años 2014 y 2015', '_ACCPJ', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (51, 'Actas de las Comisiones Provinciales de Seguridad Privada de los años 2014 y 2015', '_ACPSP', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (52, 'Relación de municipios que configuran la demarcación de la compañía', '_RMD', 'DOC, DOCX');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (53, 'Catálogo de Puestos de Trabajo (última aprobación CECIR) y fuerza en revista', '_CPT', 'XLSX');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (54, 'Relación de mandos de las distintas Unidades, de categoría Oficial, con indicación de teléfonos y correos electrónicos de contacto', '_RM', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (55, 'Plan de Seguridad Ciudadana', '_PSC', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (56, 'Libro de Organización y Libro de Normas de Régimen Interno', '_LOYRI', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (57, 'Memoria Anual 2015', '_MA', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (58, 'Mapa con la distribución territorial de sus Unidades subordinadas.', '_mapa', 'JPEG/JPG, PNG, BMP');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (59, 'Organigrama de la Zona (Unidades Territoriales y Especialidades)', '_organigrama', 'DOC, DOCX, PPTX, PPT, PUB');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (60, 'Plan anual de la Unidad Orgánica de Policía Judicial vigente', '_PAUOPJ', 'PDF');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (61, 'Organigrama de la Comandancia (Unidades Territoriales y Especialidades)', '_organigrama', 'DOC, DOCX, PPTX, PPT, PUB');
Insert into  tipodocumentacionprevia (ID_TIPO_DOCUMENTO,DESCRIPCION,NOMBRE,EXTENSION) Values (62, 'Actas de las Juntas de Coordinación de 2015', '_AJC', 'PDF');

insert into configuracion_respuestas_cuestionario (clave,valor) values ('CHECKBOXSINO', 'NO');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('CHECKBOXSINO', 'SI');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('MATRIZINGRESOS', 'campo1#SEXO');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('MATRIZINGRESOS', 'campo2#MESES');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('MATRIZINGRESOS', 'campo3#PERIODOS');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('MATRIZINGRESOS', 'nombreFila#Fila 1');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('MATRIZINGRESOS', 'nombreFila#Fila 2');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('MATRIZINGRESOS', 'nombreFila#Fila 3');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLAPRUEBA', 'campo1#COLUMNA1');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLAPRUEBA', 'campo2#COLUMNA2');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLAPRUEBA', 'campo3#COLUMNA3');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLASALIDAS', 'campo1#MESES');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLASALIDAS', 'campo2#MOTIVOS');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLASALIDAS', 'campo3#NUM_SALIDAS');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLASALIDAS', 'campo4#MOTIVOS');
insert into configuracion_respuestas_cuestionario (clave,valor) values ('TABLASALIDAS', 'campo5#PRUEBA');

insert into tipos_inspeccion (codigo,descripcion) values ('I.G.P.','I.Gral.Periodica');
insert into tipos_inspeccion (codigo,descripcion) values ('I.G.S.','I.Gral.Seguimiento');
insert into tipos_inspeccion (codigo,descripcion) values ('I.T_PRL','I.Temática PRL');
insert into tipos_inspeccion (codigo,descripcion) values ('I.T_CIE','I.Temática CIE');
insert into tipos_inspeccion (codigo,descripcion) values ('I.T_OT','I.Temática otros');
insert into tipos_inspeccion (codigo,descripcion) values ('I.E_INCD','I. Incidental extraordinaria');
insert into tipos_inspeccion (codigo,descripcion) values ('I.E_PRL','I.PRL extraordinaria');
insert into tipos_inspeccion (codigo,descripcion) values ('I.E_PUNT','I.Puntual extraordinaria');

INSERT INTO PARAMETROS(clave, valor) VALUES ('mailHost','smtp.gmail.com');
INSERT INTO PARAMETROS(clave, valor) VALUES ('mailHostPort','587');
INSERT INTO PARAMETROS(clave, valor) VALUES ('mailUser','progesinipss@gmail.com');
INSERT INTO PARAMETROS(clave, valor) VALUES ('mailUserPwd','ipss2016');
INSERT INTO PARAMETROS(clave, valor) VALUES ('mailAuth','true');
INSERT INTO PARAMETROS(clave, valor) VALUES ('mailTLS','true');

INSERT INTO public.inspecciones(id, fecha_creacion, numero, tipo_inspeccion, username_creacion, nombre_unidad, ambito) VALUES (1, '01/11/2016', '1/2017', 'I.G.P.','silpe', 'Lérida', 'PN');
INSERT INTO public.inspecciones(id, fecha_creacion, numero, tipo_inspeccion, username_creacion, nombre_unidad, ambito) VALUES (11, '01/11/2016', '11/2017', 'I.G.P.','silpe', 'Granada', 'GC');
INSERT INTO public.inspecciones(id, fecha_creacion, numero, tipo_inspeccion, username_creacion, nombre_unidad, ambito) VALUES (101, '01/11/2016', '101/2017', 'I.G.P.','silpe', 'Lérida', 'PN');
INSERT INTO public.inspecciones(id, fecha_creacion, numero, tipo_inspeccion, username_creacion, nombre_unidad, ambito) VALUES (10, '01/11/2016', '10/2017', 'I.T_CIE','silpe', 'Granada', 'GC');
INSERT INTO public.inspecciones(id, fecha_creacion, numero, tipo_inspeccion, username_creacion, nombre_unidad, ambito) VALUES (12, '01/11/2016', '12/2017', 'I.G.S.','silpe', 'Lérida', 'PN');
INSERT INTO public.inspecciones(id, fecha_creacion, numero, tipo_inspeccion, username_creacion, nombre_unidad, ambito) VALUES (150, '01/11/2016', '150/2017', 'I.E_PUNT','silpe', 'Granada', 'GC');