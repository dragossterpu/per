-- Inserción de los tipos de respuesta
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila1', 'MATRIZECONOMICOS', 'TASA DE PARO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila2', 'MATRIZECONOMICOS', 'ACTIVIDAD INDUSTRIAL PREDOMINANTE');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila3', 'MATRIZECONOMICOS', 'Nº POLÍGONOS INDUSTRIALES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila4', 'MATRIZECONOMICOS', 'Nº DE ESTABLECIMIENTOS HOTELEROS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila5', 'MATRIZECONOMICOS', 'Nº PLAZAS HOTELERAS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo1', 'MATRIZECONOMICOS', 'Provincial');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo2', 'MATRIZECONOMICOS', '(Ciudad)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('ALTA', 'RADIOALTAMEDIABAJA', 'Alta');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('MEDIA', 'RADIOALTAMEDIABAJA', 'Media');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('BAJA', 'RADIOALTAMEDIABAJA', 'Baja');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo1', 'TABLASERVICIOS', 'Ubicación - Distancia dependencias policiales');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo2', 'TABLASERVICIOS', 'Carácter Internacional (Sí - No)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo3', 'TABLASERVICIOS', 'Servicios policiales permanentes  - Tipo ');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo4', 'TABLASERVICIOS', 'Actividad principal desarrollada');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo5', 'TABLASERVICIOS', 'Volumen anual pasajeros');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo6', 'TABLASERVICIOS', 'Volumen anual mercancías');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo1', 'TABLAPENITENCIARIOS', 'Nombre');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo2', 'TABLAPENITENCIARIOS', 'Ubicación');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo3', 'TABLAPENITENCIARIOS', 'Tipo (preventivo, de penados o mixto)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo4', 'TABLAPENITENCIARIOS', 'Reclusos (Media anual)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo5', 'TABLAPENITENCIARIOS', 'De ETA');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo6', 'TABLAPENITENCIARIOS', 'De GRAPO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo7', 'TABLAPENITENCIARIOS', 'Radicales Islámicos');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo1', 'TABLAMOVILIZACIONES', 'AÑO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo2', 'TABLAMOVILIZACIONES', 'MANIFESTACIONES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo3', 'TABLAMOVILIZACIONES', 'CONCENTRACIONES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo4', 'TABLAMOVILIZACIONES', 'HUELGAS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo5', 'TABLAMOVILIZACIONES', 'ENCIERROS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo6', 'TABLAMOVILIZACIONES', 'EVENTOS DEPORTIVOS (en caso de equipos, reseñar su denominación y categoría)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo7', 'TABLAMOVILIZACIONES', 'OTROS (cadenas humanas, jornadas, etc.)');

--Insert modelo ÁMBITO DE LA INSPECCIÓN DE LA POLICIA NACIONAL
Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.NEXTVAL,'AMBITO_PN','ÁMBITO DE LA INSPECCIÓN DE LA POLICIA NACIONAL');

--Insert area datos generales
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'ÁMBITO DE LA INSPECCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Ámbito territorial: Extensión de la demarcación.', 'INPUT', 0);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Ámbito territorial: Municipios que la componen.', 'TEXTAREA', 1);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Ámbito territorial: En el supuesto de que la demarcación de responsabilidad policial no coincida con exactitud con la de la ciudad de adscripción, señalar y documentar la situación actual.', 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Población censada (empadronada).', 'INPUT', 3);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Población flotante.', 'INPUT', 4);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Población activa.', 'INPUT', 5);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Población desempleada.', 'INPUT', 6);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Población extranjera.', 'INPUT', 7);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Población extranjera.', 'INPUT', 8);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Económicos.', 'MATRIZECONOMICOS', 9);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Económicos.', 'TABLASERVICIOS', 10);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Centros hospitalarios. Nº de centros públicos.', 'INPUT', 11);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Centros hospitalarios. De ellos, número con Módulo para detenidos.', 'INPUT', 12);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Centros penitenciarios. Centros de Inserción Social. Centro de Menores', 'TABLAPENITENCIARIOS', 13);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Órganos: (detalar su ubicación por sedes judiciales y quién custodia cada sede-cnp, guardia civil, empresas de seguridad).', 'TEXTAREA', 14);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Problemática de interés policial. Delincuencial: AÑO 2016 (DELITOS GRAVES + DELITOS MENOS GRAVES + DELITOS LEVES). Zonas de mayor conflictividad, reseñando la problemática específica de cada una de ellas.', 'TEXTAREA', 15);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Problemática de interés policial. Delincuencial: AÑO 2016 (DELITOS GRAVES + DELITOS MENOS GRAVES + DELITOS LEVES). Reseñar los seis delitos más frecuentes de 2016.', 'TEXTAREA', 16);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Problemática de interés policial. Delincuencial: AÑO 2016 (DELITOS GRAVES + DELITOS MENOS GRAVES + DELITOS LEVES). Dias y franjas horarias con mayor número de delitos.', 'TEXTAREA', 17);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Problemática de interés policial. Socio-laboral. Grado de conflictividad.', 'RADIOALTAMEDIABAJA', 18);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Grupos colectivos más activos.', 'TEXTAREA', 19);
insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Movilizaciones. Indicar número.', 'TABLAMOVILIZACIONES', 20);


--Insert modelo ÁMBITO DE LA INSPECCIÓN DE LA GUARDIA CIVIL 
Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.NEXTVAL,'AMBITO_GC','ÁMBITO DE LA INSPECCIÓN DE LA GUARDIA CIVIL');

--Insert area datos generales
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'ÁMBITO DE LA INSPECCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);


insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Unidad inspeccionada. Organigrama', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Ámbito territorial: Extensión de la demarcación', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Ámbito territorial: Características a destacar por su influencia en la actividad policial (si es zona industrial, turística, rural, urbana, centro de interés económico, cultural, etc.).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Ámbito territorial: En el supuesto de que la demarcación de responsabilidad policial no coincida con exactitud con la de la ciudad de adscripción, señalar y documentar la situación actual', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Población: Censada (empadronada)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Población: Flotante', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Población: Activa y desempleada', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Población: Extranjera', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Ámbito económico', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZECONOMICOS', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios: Vías de comunicación terrestre. Puertos. Aeropuertos. Principales infraestructuras de comunicaciones comerciales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLASERVICIOS', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Centros hospitalarios: Nº de centros públicos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Centros hospitalarios con módulo para detenidos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Centros penitenciarios. Centros de Inserción Social. Centro de Menores. Reseñar indicando si es preventivo, de penados o mixto y ubicación', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAPENITENCIARIOS', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Centros de internamientos de extranjeros', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Órganos judiciales (Detallar su ubicación por sedes judiciales y quien custodia cada sede -CNP, Guardia Civil, empresas de seguridad)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial:  Delincuencial: AÑO 2016  (DELITOS GRAVES, MENOS GRAVES Y LEVES): ZONAS DE MAYOR CONFLICTIVIDAD, reseñando la problemática específica de cada una de ellas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 19);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial:  Delincuencial: AÑO 2016  (DELITOS GRAVES, MENOS GRAVES Y LEVES): RESEÑAR LOS 6 DELITOS MÁS FRECUENTES DURANTE 2015', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 20);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial:  Delincuencial: AÑO 2016  (DELITOS GRAVES, MENOS GRAVES Y LEVES):DÍAS Y FRANJAS HORARIAS CON  MAYOR NÚMERO DE DELITOS', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 21);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial: Socio-laboral', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 22);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial: Terrorismo: Grado de conflictividad', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOALTAMEDIABAJA', 23);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial: Terrorismo: Grupos o colectivos más activos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 24);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática de interés policial: Terrorismo: Movilizaciones', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAMOVILIZACIONES', 25);

commit;