--Insert modelo
Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.nextval,'cuest_prl_gc','CUESTIONARIO.PRL_GC');

--Insert area datos generales
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estructura, Dependencia y Ámbito de Competencia.<br/>
Organización, estructura, funciones y competencias del Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Recursos Humanos.<br/>Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, así como personal de 
apoyo y auxiliar, con expresión de Orden y fecha de creación de la Unidad (Sección/Oficina). ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios Materiales.<br/>Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida. Acreditar si los Técnicos del Servicio de Prevención 
están instruidos en el uso de los equipos y si éstos están, en su caso, calibrados. A fecha de la visita se tendrá a disposición del equipo inspector 
copia de la documentación acreditativa relativa a la formación y calibración de los equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);

--Insert area funciones y actividades
Insert into  Areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.<br/>Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.<br/>Planes de Zona, elaborados por las Secciones y fecha de aprobación por el Director General de la Guardia Civil.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.<br/>Relación de Evaluaciones de Riesgo realizadas, diferenciando las llevadas a cabo por el Servicio de Prevención, Secciones y Oficinas, en el periodo indicado ,
con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido el tiempo previsto y aquellas 
otras circunstancias que exijan una revisión y actualización).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.<br/>Relación de las evaluaciones que quedan pendientes de realizar. Si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos 
(Unidades especializadas).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Propuesta de Planificación Preventiva.<br/>Actividades de seguimiento y control de las acciones correctoras', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.<br/>Planes de Emergencia/Autoprotección elaborados durante el periodo, diferenciando los llevados a cabo por el Servicio de Prevención, Secciones y Oficinas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.<br/>Relación de los que quedan pendientes de realizar', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.<br/>Indicar simulacros realizados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.<br/>Procedimientos de actuación y mecanismos de coordinación entre el Servicio de Prevención y los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia en materia de vigilancia y control de la salud de los guardias civiles en 
relación con los riesgos derivados del ejercicio de las funciones profesionales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.<br/>Actuaciones desarrolladas destinadas a la protección de trabajadores especialmente sensible a determinados riesgos y declarados útil con limitaciones al objeto de adaptar las condiciones y exigencias que demanda el puesto de trabajo 
al estado psicofísico del funcionario.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.<br/>Actuaciones llevadas a acabo en relación a la protección de la guardia civil en situación de embarazo, parto reciente o en periodo de lactancia.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.<br/>Relación de accidentes de trabajo por provincias con indicación de mortales, graves y más de 4 leves.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 11);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.<br/>Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de 
notificación e investigación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.<br/>Indicar si los informes de investigación son recibidos por la Sección de Prevención de la Unidad afectada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 13);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.<br/>Medidas preventivas de las propuestas en el informe de investigación INVAS23/13-SP adoptadas y, en su caso, situación en qué se encuentren las pendiente.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.<br/>Responsable de ejecutar las medidas preventivas propuestas en los informes de investigación cuando el puesto de trabajo es común a nivel nacional.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 15);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.<br/>Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 16);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.<br/>Manuales, Campañas de Prevención, Trípticos y cualquier otro medio informativo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.<br/>Procedimiento empleado para justificar la recepción por el personal.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.<br/>Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios. 
De cada una de las actividades se realizará una breve descripción que al menos contenga:<br/>Materia de Prevención de Riesgos laborales tratada (General, específica, Medidas de Emergencia…),<br/>Personal que la impartió,<br/>Personal que lo recibió,<br/>Número de alumnos,<br/>Duración,<br/>Método utilizado (Telemático, presencial, divulgativo,…)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 19);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.<br/>Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención. 
De cada una de las actividades se realizará una breve descripción que al menos contenga:<br/>Materia de Prevención de Riesgos laborales tratada (General, específica, Medidas de Emergencia…),<br/>Personal que la impartió,<br/>Personal que lo recibió,<br/>Número de alumnos,<br/>Duración,<br/>Método utilizado (Telemático, presencial, divulgativo,…)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 20);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.<br/>Por otro lado, a fecha de la visita se tendrá documentación acreditativa de los cursos impartidos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 21);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Asesoramiento e informes emitidos.<br/>Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos 
laborales en el ámbito de la Dirección General de la Guardia Civil.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 22);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.<br/>Medidas que se han adoptado para que las distintas Unidades de la Guardia Civil dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 23);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.<br/>¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Guardia Civil?.  En caso afirmativo indicar número y tipo de actividad contratada. 
Número y tipos de actuaciones en esta materia llevadas a cabo por el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 24);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.<br/>Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 25);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Registro y Archivo de Documentación.<br/>Relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Guardia Civil en materia de prevención de riesgos laborales, que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 26);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimientos e Instrucciones Operativas de Prevención de Riesgos Laborales.<br/>Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas en vigor, que estén o hayan sido objeto de actualización o revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 27);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Protocolo de actuación frente al acoso laboral.<br/>Papel desempeñado por el Servicio de Prevención en el Protocolo de Acoso.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 28);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación de propuestas de mejora 
y sugerencias en materia de prevención, recibidas en el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 29);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Elaboración de la Memoria Anual.<br/>Las actividades preventivas que se documentan a través de una programación y memoria anual. Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 30);














































