--Insert modelo
Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA,ESTANDAR) values (SEQ_MODELOSCUESTIONARIOS.NEXTVAL,'RELCO_PN','RELACIONES Y COORDINACIÓN DE LA POLICIA NACIONAL', sysdate, 'system',1);

--Insert area datos generales
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'RELACIONES Y COORDINACIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Subdelegación del Gobierno.', 'ADJUNTOINPUT', 0);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Jefatura Superior y Comisarías Locales.', 'ADJUNTOINPUT', 1);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con el Cuerpo de la Guardia Civil: Coordinación con las especialidades de Policía Judicial e Información (número de servicios conjuntos y de informaciones recibidas último  año).', 'ADJUNTOINPUT', 2);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con el Cuerpo de la Guardia Civil: Coordinación en materia de Seguridad Ciudadana.', 'TEXTAREA', 3);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con el Cuerpo de la Guardia Civil: En materia de extranjería, detalle de las actuaciones de la Guardia Civil en el último año y procedimiento o trámites realizados con el CNP.', 'ADJUNTOINPUT', 4);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con el Cuerpo de la Guardia Civil: Problemática en las conducciones de presos y custodias hospitalarias, etc. (cumplimiento de la Instrucción de la Secretaría de Estado 8/2009)..', 'ADJUNTOINPUT', 5);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con el Cuerpo de la Guardia Civil: En materia de Seguridad Privada: existencia de comunicación de las denuncias por infracción a la Ley o Reglamento de Seguridad Privada.', 'ADJUNTOINPUT', 6);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Municipios dotados de Cuerpos de Policía Local. Plantillas de estos Cuerpos.', 'ADJUNTOINPUT', 7);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Existencia de protocolos de coordinación. Definir.', 'ADJUNTOINPUT', 8);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Intercambio de información (partes de novedades, equipos transmisiones, etc.)', 'ADJUNTOINPUT', 9);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Funciones que realizan los agentes de la Policía Local (Policía Judicial, Violencia de Género, etc.)', 'ADJUNTOINPUT', 10);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Actuación de paisano de los agentes locales.', 'ADJUNTOINPUT', 11);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Coordinación en materia de Seguridad Ciudadana.', 'ADJUNTOINPUT', 12);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Coordinación en materia de extranjería.', 'ADJUNTOINPUT', 13);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Actuaciones con detenidos por delitos contra la seguridad del tráfico y otras infracciones penales.', 'ADJUNTOINPUT', 14);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Policía Local: Remisión de copia de diligencias instruidas por Policía Local a la Comisaría Provincial.', 'ADJUNTOINPUT', 15);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con Autoridades Judiciales y Fiscales: Comisiones Provinciales de Coordinación de P.J.  Frecuencia de las reuniones. Temas tratados en el último año. Protocolos de colaboración.', 'ADJUNTOINPUT', 16);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con Autoridades Autonómicas: Seguridad de autoridades y vigilancia de edificios oficiales, normativa del juego, protección de menores….', 'ADJUNTOINPUT', 17);

insert into  preguntascuestionario (id, id_area, pregunta, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, SEQ_AREASCUESTIONARIOS.CURRVAL, 'Con la Unidad Adscrita del Cuerpo Nacional de Policía a la CCAA: Actividades concurrentes, coordinación, apoyos.', 'ADJUNTOINPUT', 18);

COMMIT;