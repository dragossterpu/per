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
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', 'n10323', 'user_alta', null, null, '05/06/2000', 2, 2, 18),
   
( 'jmanuel', 'SIERRA', 'EXOJO', 'correo@correo.es', 'n10704', 'SI', 'ACTIVO', '01/01/2011', NULL, NULL, NULL, 'JOSE MANUEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '22/06/2016', 3, 2, 20),
  	
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
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '22/06/2009', 3, 2, 21),
  
 ( 'mafan', 'AFAN', 'FAJARDO', 'correo@correo.es', 'n24639', 'SI', 'ACTIVO', '01/01/2011', NULL, NULL, NULL, 'MIGUEL',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '22/06/2016', 3, 1, 21),
  
 ( 'parbona', 'ARBONA', 'CAMPOMAR', 'correo@correo.es', 'n25044', 'NO', 'ACTIVO', '01/02/2003', NULL, NULL, NULL, 'PEDRO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '12/08/2007', 9, 2, 21),
  
  ( 'jicerezo', 'CEREZO', 'HERNANDEZ', 'correo@correo.es', 'N25342', 'SI', 'ACTIVO', '01/01/2011', NULL, NULL, NULL, 'JOSE IGNACIO',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '22/06/2016', 9, 1, 21),
     
 ( 'jlcalonge', 'CALONGE', 'DELSO', 'correo@correo.es', 'n25583', 'SI', 'ACTIVO', '01/02/2007', NULL, NULL, NULL, 'JOSE LUIS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '22/06/2009', 9, 2, 21),
  
  ( 'jcgalan', 'GALAN', 'VIVAR', 'correo@correo.es', 'n25655', 'SI', 'ACTIVO', '11/04/2001', NULL, NULL, NULL, 'JOSE CARLOS',
  '2222222222', '$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve', 'USER', '665566544', 'user_alta', null, null, '22/04/2013', 9, 1, 21);
  
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (1, 'cuestionario modificado', '01/09/2016', 'cuestionarios 1', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (2, 'cuestionario modificado', '01/09/2016', 'cuestionarios 2', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (3, 'cuestionario modificado', '01/09/2016', 'cuestionarios 3', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (4, 'cuestionario modificado', '01/09/2016', 'cuestionarios 4', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (5, 'cuestionario modificado', '01/09/2016', 'cuestionarios 5', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (6, 'cuestionario modificado', '01/09/2016', 'cuestionarios 6', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (7, 'cuestionario modificado', '01/09/2016', 'cuestionarios 7', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (8, 'cuestionario modificado', '01/09/2016', 'cuestionarios 8', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (9, 'cuestionario modificado', '01/09/2016', 'cuestionarios 9', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (10, 'cuestionario modificado', '01/09/2016', 'cuestionarios 10', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (11, 'cuestionario modificado', '01/09/2016', 'cuestionarios 11', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (12, 'cuestionario modificado', '01/09/2016', 'cuestionarios 12', 'USER', 'userAltaAlerta');
  insert into alertas (id_alerta, descripcion, fecha_registro, nombre_seccion, tipo_alerta, usuario_registro)
  values (13, 'cuestionario modificado', '01/09/2016', 'cuestionarios 13', 'USER', 'userAltaAlerta');
  