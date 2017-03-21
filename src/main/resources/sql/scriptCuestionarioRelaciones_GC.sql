--Creación del cuestionario de Relaciones y coordinación Ámbito Guardia Civil

Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.nextval,'RC_GC','RELACIONES Y COORDINACIÓN ÁMBITO GUARDIA CIVIL');


--Creación de Áreas/preguntas

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'RELACIONES Y COORDINACIÓN ÁMBITO GUARDIA CIVIL', SEQ_AREASCUESTIONARIOS.NEXTVAL, 0);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación a nivel interno de la Guardia Civil con las Unidades Superiores (DAO)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación a nivel interno de la Guardia Civil con la Zona respectiva', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación a nivel interno de la Guardia Civil con las Unidades dependientes de la Comandancia: Compañías, Puestos, Especialidades (reuniones periódicas, etc.)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con la Delegación/Subdelegación del Gobierno', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con Autoridades Autonómicas: Seguridad de autoridades y vigilancia de edificios oficiales, normativa del juego, protección de menores.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía: Coordinación con las Brigadas de Policía Judicial e Información, GEDEX, Guías caninos, (número de servicios conjuntos y de informaciones/comunicaciones recibidas durante el último año)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía:  En materia de extranjería, detalle de las actuaciones en el último año y procedimiento o trámites realizados con el Cuerpo Nacional de Policía', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía : Protocolos de actuación en materia de extranjería: Número de extranjeros detenidos y/o presentados por la Guardia Civil única y exclusivamente por estancia irregular en 2015 y 20', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía : Protocolos de actuación en materia de extranjería: Procedimiento seguido cuando la Guardia Civil detiene a un extranjero por la comisión de una infracción penal a un extranjero incurso en alguna de las infracciones previstas en la Ley de Extranjería. Número de casos en 2015 y 2016', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía : Protocolos de actuación en materia de extranjería: Número de extranjeros detenidos por delitos/faltas, y número de las solicitudes al CNP de informe sobre situación de extranjeros detenidos por delitos/faltas, para acompañar en los atestados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía: Problemática existente en el aeropuerto', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía :  Coordinación en operaciones de servicio o ante la comisión de hechos delictivos de especial relevancia y si existe comunicación recíproca y diaria  en este campo', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía :  Comunicaciones recíprocas entre el COS y la Sala del 091. Intercambio de partes de novedades, etc', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con el Cuerpo Nacional de Policía :  En las conducciones de presos y custodias hospitalarias, etc', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Autonómicas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales:  Existencia de Convenios/Protocolos de coordinación o colaboración tanto genéricos como específicos en alguna materia (violencia de género, policía judicial, etc.): Definir', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales:  Funciones que realizan los agentes de la Policía Local (Policía Judicial, Violencia de género, etc.), dentro del ámbito competencial de la Comandancia', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales:  Coordinación en materia de Seguridad Ciudadana con las Unidades Territoriales (Puestos)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales:  Instrucción de diligencias por parte de las Policías Locales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 28);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales: Actuaciones con detenidos por delitos contra la seguridad del tráfico y otras infracciones penales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 19);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales:  Juntas Locales de Seguridad. Con remisión de copia de las actas que se consideren de mayor interés del año 2015 y 2016', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 20);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con las Policías Locales: Resumen de los incidentes habidos con estos Cuerpos en los dos últimos años. Medidas adoptadas en su caso', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 21);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con Judiciales y Fiscales: Comisiones Provinciales de Coordinación de Policía Judicial. Frecuencia de las reuniones. Temas tratados en el último año', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 22);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con Judiciales y Fiscales: Protocolos de colaboración', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 23);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relaciones y coordinación con Judiciales y Fiscales: Otras formas de Coordinación con Jueces y Fiscales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 24);

commit;
