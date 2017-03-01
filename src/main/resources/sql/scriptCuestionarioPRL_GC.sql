--Insert modelo
Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.nextval,'cuest_prl_gc','CUESTIONARIO.PRL_GC');


--Insert area datos generales
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estructura, Dependencia y Ámbito de Competencia.' || chr(13) || chr(10) ||
'Organización, estructura, funciones y competencias del Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Recursos Humanos.' || chr(13) || chr(10) ||
'Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, 
así como personal de apoyo y auxiliar, con expresión de Orden y fecha de creación de la Unidad (Sección/Oficina)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios Materiales.' || chr(13) || chr(10) || 
'Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida. Acreditar si los Técnicos del Servicio de Prevención 
están instruidos en el uso de los equipos y si éstos están, en su caso, calibrados. A fecha de la visita se tendrá a disposición del equipo inspector 
copia de la documentación acreditativa relativa a la formación y calibración de los equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 3);

--Insert area funciones y actividades
Insert into  Areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 2);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.' || chr(13) || chr(10) ||  
'Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Zona, elaborados por las Secciones y fecha de aprobación por el Director General de la Guardia Civil. .', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.' || chr(13) || chr(10) ||
'Relación de Evaluaciones de Riesgo realizadas, diferenciando las llevadas a cabo por el Servicio de Prevención, Secciones y Oficinas, en el periodo indicado ,
con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido el tiempo previsto y aquellas 
otras circunstancias que exijan una revisión y actualización).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 3);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.' || chr(13) || chr(10) ||
'Relación de las evaluaciones que quedan pendientes de realizar. Si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos 
(Unidades especializadas).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 4);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Propuesta de Planificación Preventiva.' || chr(13) || chr(10) ||
'Actividades de seguimiento y control de las acciones correctoras', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 5);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.' || chr(13) || chr(10) ||
'Planes de Emergencia/Autoprotección elaborados durante el periodo, diferenciando los llevados a cabo por el Servicio de Prevención, Secciones y Oficinas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 6);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.' || chr(13) || chr(10) ||
'Relación de los que quedan pendientes de realizar', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 7);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.' || chr(13) || chr(10) ||
'Indicar simulacros realizados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 8);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.' || chr(13) || chr(10) ||
'Procedimientos de actuación y mecanismos de coordinación entre el Servicio de Prevención y los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia en materia de vigilancia y control de la salud de los guardias civiles en 
relación con los riesgos derivados del ejercicio de las funciones profesionales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 9);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.' || chr(13) || chr(10) || 
'Actuaciones desarrolladas destinadas a la protección de trabajadores especialmente sensible a determinados riesgos y declarados útil con limitaciones al objeto de adaptar las condiciones y exigencias que demanda el puesto de trabajo 
al estado psicofísico del funcionario.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 10);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.' || chr(13) || chr(10) || 
'Actuaciones llevadas a acabo en relación a la protección de la guardia civil en situación de embarazo, parto reciente o en periodo de lactancia.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 11);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) ||
'Relación de accidentes de trabajo con indicación de mortales, graves y leves.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 12);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) ||
'Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de 
notificación e investigación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 13);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) ||
'Indicar si los informes de investigación son recibidos por la Sección de Prevención de la Unidad afectada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 14);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) ||
'Medidas preventivas de las propuestas en el informe de investigación INVAS23/13-SP adoptadas y, en su caso, situación en qué se encuentren las pendiente.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 15);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) ||
'Responsable de ejecutar las medidas preventivas propuestas en los informes de investigación cuando el puesto de trabajo es común a nivel nacional.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 16);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) ||
'Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 17);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.' || chr(13) || chr(10) ||
'Manuales, Campañas de Prevención, Trípticos y cualquier otro medio informativo. Procedimiento empleado para justificar la recepción por el personal.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.' || chr(13) || chr(10) ||
'Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios. 
De cada una de las actividades se realizará una breve descripción que al menos contenga: Materia de Prevención de Riesgos laborales tratada 
(General, específica, Medidas de Emergencia…), Personal que la impartió, Personal que lo recibió, Número de alumnos, Duración, Método utilizado 
(Telemático, presencial, divulgativo,…)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 19);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.' || chr(13) || chr(10) ||
'Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención. De cada una de las actividades se realizará una breve descripción que al menos contenga: 
Materia de Prevención de Riesgos laborales tratada (General, específica, Medidas de Emergencia…), Personal que la impartió, Personal que lo recibió, Número de alumnos, Duración, Método utilizado 
(Telemático, presencial, divulgativo,…)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 20);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.' || chr(13) || chr(10) ||
'Por otro lado, a fecha de la visita se tendrá documentación acreditativa de los cursos impartidos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 21);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Asesoramiento e informes emitidos.' || chr(13) || chr(10) ||
'Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos 
laborales en el ámbito de la Dirección General de la Guardia Civil.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 22);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) ||
'Medidas que se han adoptado para que las distintas Unidades de la Guardia Civil dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 23);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) ||
'¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Guardia Civil?.  En caso afirmativo indicar número y tipo de actividad contratada. 
Número y tipos de actuaciones en esta materia llevadas a cabo por el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 24);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) ||
'Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 25);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Registro y Archivo de Documentación.' || chr(13) || chr(10) ||
'Relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Guardia Civil en materia de prevención de riesgos laborales, que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 26);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimientos e Instrucciones Operativas de Prevención de Riesgos Laborales.' || chr(13) || chr(10) ||
'Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas en vigor, que estén o hayan sido objeto de actualización o revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 27);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Protocolo de actuación frente al acoso laboral.' || chr(13) || chr(10) ||
'Papel desempeñado por el Servicio de Prevención en el Protocolo de Acoso.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 28);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación de propuestas de mejora 
y sugerencias en materia de prevención, recibidas en el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 29);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, '.- Elaboración de la Memoria Anual.' || chr(13) || chr(10) ||
'Las actividades preventivas que se documentan a través de una programación y memoria anual. Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 30);














































