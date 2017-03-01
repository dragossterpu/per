--Insert modelo
Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.NEXTVAL,'cuest_prl_pnc','CUESTIONARIO.PRL_CNP');


--Insert area datos generales
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estructura, Dependencia y Ámbito de Competencia.' || chr(13) || chr(10) ||
'Relación documentada de las actividades preventivas encuadradas en las especialidades contempladas no asumidas por el Servicio de Prevención y para cuya utilización se ha recurrido a un Servicio de Prevención Ajeno.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Recursos Humanos.' || chr(13) || chr(10) ||
'Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, así como personal 
de apoyo y auxiliar.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios Materiales.' || chr(13) || chr(10) || 
'Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida. Acreditar si los Técnicos del Servicio de Prevención están instruidos en el 
uso de los equipos y si éstos están, en su caso, calibrados. A fecha de la visita se tendrá a disposición del equipo inspector copia de la documentación acreditativa relativa 
a la formación y calibración de los equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);


--Insert area datos funciones y actividades
Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.' || chr(13) || chr(10) ||  
'Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.' || chr(13) || chr(10) || 
'Deficiencias e incumplimientos detectados y puestos de manifiesto durante el desarrollo e implantación del Plan.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.' || chr(13) || chr(10) ||
'En el caso de haberse detectado deficiencias, ¿se han elevado propuestas de revisión y modificación del Plan de Prevención al objeto de adaptarlo a la situación real que permita 
llevar a cabo adecuadamente las actividades preventivas de las especialidades asumidas por el Servicio? En caso afirmativo acreditar las propuestas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.' || chr(13) || chr(10) ||
'Relación de Evaluaciones de Riesgo realizadas en el periodo indicado con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido cuatro años y aquellas otras circunstancias 
que exijan una revisión y actualización). Si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 3);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.' || chr(13) || chr(10) || 
'Relación de plantillas o unidades a las que se ha practicado revisiones de evaluación indicando las circunstancias y el Servicio de Prevención que las ha llevado a cabo. Previsiones o programación de las evaluaciones pendientes de realizar y 
el Servicio de Prevención que vaya a asumir su realización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 4);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Propuesta de Planificación Preventiva.' || chr(13) || chr(10) || 
'Actividades de seguimiento y control de las Fichas de Seguimiento de las Acciones Correctoras.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 5);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.' || chr(13) || chr(10) || 
'Planes de Emergencia/Autoprotección supervisados y/o elaborados por el Servicio de Prevención durante el periodo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 6);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección.' || chr(13) || chr(10) || 
'Indicar simulacros realizados. A fecha de la visita se tendrá a disposición del equipo inspector copia de la documentación acreditativa (Actas).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 7);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud.' || chr(13) || chr(10) || 
'Protocolos de reconocimiento médico específico en relación a los riesgos en el puesto de trabajo enumerando aquellos que por razón de la naturaleza de la actividad profesional sean de carácter obligatorio, con indicación de la periodicidad establecida.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 8);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud.' || chr(13) || chr(10) || 
'Informes, dictámenes y recomendaciones emitidos en materia de vigilancia de la salud: de aptitud física, de aptitud para el trabajo, de cambio de puesto de trabajo por motivos de salud, sobre riesgo durante el embarazo, parto reciente o lactancia o cualquier circunstancia o situación en el ámbito de la evaluación 
de la salud de los empleados públicos en relación con las condiciones del puesto de trabajo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud.' || chr(13) || chr(10) || 
'Actividades en materia de asistencia sanitaria, prevención y promoción de la salud dentro de las previsiones que, en materia de seguridad y salud laboral, tiene establecidos el 
Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 10);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud.' || chr(13) || chr(10) || 
'Relación y organización del personal sanitario capacitado en la materia tanto a nivel central como territorial.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 11);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud.' || chr(13) || chr(10) || 
'Recursos materiales: relación de instalaciones, medios materiales sanitarios y de archivo de los que está dotado el servicio de prevención para desarrollar las actividades sanitarias asignadas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 12);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud.' || chr(13) || chr(10) || 
'Procedimientos de actuación y mecanismos de coordinación entre el Servicio Sanitario y el Servicio de Prevención en materia de vigilancia y control de la salud de los empleados públicos en el ámbito de la Dirección General de la Policía.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 13);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) || 
'Relación de accidentes de trabajo con indicación de mortales, graves y leves.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 14);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) || 
'Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de notificación e investigación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 15);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.' || chr(13) || chr(10) || 
'Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 16);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.' || chr(13) || chr(10) || 
'Fichas de información de carácter general, especifico o de cualquier otra naturaleza elaborados por el Servicio de Prevención, así como Manuales, Campañas de Prevención, Trípticos y cualquier otro medio informativo. Procedimiento 
empleado para justificar la recepción por el personal.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.' || chr(13) || chr(10) || 
'Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios.De cada una de las actividades contempladas se realizará una breve descripción que al menos contenga:' || chr(13) || chr(10) ||
'Materia de Prevención de Riesgos laborales tratada (General, específica, Medidas de Emergencia…)' || chr(13) || chr(10) ||
'Personal que la impartió.' || chr(13) || chr(10) ||
'Personal que lo recibió.' || chr(13) || chr(10) ||
'Número de alumnos.' || chr(13) || chr(10) ||
'Duración.' || chr(13) || chr(10) ||
'Método utilizado (Telemático, presencial, divulgativo)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 18);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.' || chr(13) || chr(10) || 
'Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención. De cada una de las actividades contempladas se realizará una breve descripción que al menos contenga:' || chr(13) || chr(10) ||
'Materia de Prevención de Riesgos laborales tratada (General, específica, Medidas de Emergencia…)' || chr(13) || chr(10) ||
'Personal que la impartió.' || chr(13) || chr(10) ||
'Personal que lo recibió.' || chr(13) || chr(10) ||
'Número de alumnos.' || chr(13) || chr(10) ||
'Duración.' || chr(13) || chr(10) ||
'Método utilizado (Telemático, presencial, divulgativo)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 19);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida y recibida.' || chr(13) || chr(10) || 
'Por otro lado, a fecha de la visita se tendrá documentación acreditativa de los cursos impartidos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 20);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Asesoramiento e informes emitidos.' || chr(13) || chr(10) || 
'Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos laborales 
en el ámbito de la Dirección General de la Policía.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 21);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) || 
'Qué tipo de medidas se han adoptado para que las distintas plantillas y unidades del Cuerpo Nacional de Policía dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de 
actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 22);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) || 
'¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Policía? En caso afirmativo indicar número y 
tipo de actividad contratada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 23);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) || 
'Número y tipo de actuaciones en esta materia solicitadas al Servicio de Prevención por los titulares de los órganos de la Institución.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 24);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.' || chr(13) || chr(10) || 
'Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 25);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Registro y Archivo de Documentación.' || chr(13) || chr(10) || 
'En referencia a lo dispuesto en el Art. 23 de la Ley de Prevención de Riesgos Laborales, relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Policía en materia de prevención de riesgos laborales, 
que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 26);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimientos e Instrucciones Operativas de Prevención de Riesgos Laborales.' || chr(13) || chr(10) || 
'Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas 
en vigor que estén o hayan sido objeto de actualización o revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 27);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Protocolo de actuación frente al acoso laboral. Informar brevemente, en su caso, sobre los siguientes aspectos:' || chr(13) || chr(10) || 
'Registros de Actuaciones por Acoso Laboral.' || chr(13) || chr(10) ||  
'Relación de Acuerdos adoptados por Acoso laboral.' || chr(13) || chr(10) ||   
'Número de Informes elaborados sobre situación previa de los riesgos psicosociales en la Unidad afectada.' || chr(13) || chr(10) || 
'Relación de las actuaciones de seguimiento respecto del cumplimiento de las medidas preventivas/correctoras propuestas en el marco de los riesgos laborales.' || chr(13) || chr(10) || 
'Número de denuncias falsas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 28);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación de propuestas de mejora y sugerencias en materia de prevención 
recibidas en el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 29);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Elaboración de la Memoria Anual.' || chr(13) || chr(10) || 
'Las actividades preventivas se documentan a través de una programación y memoria anual. Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 30);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Elaboración de la Memoria Anual.' || chr(13) || chr(10) || 
'Informe de Auditoría Interna realizado por ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 31);


























