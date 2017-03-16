 --PREVENCIÓN DE RIESGOS LABORALES DE LA GUARDIA CIVIL.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(SEQ_GUIAS.NEXTVAL, 'PRL (Guardia civil)', 'I.T_PRL', 16, trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Organización, estructura, funciones y competencias del Servicio de Prevención.', 0);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Estructura, Dependencia y Ámbito de Competencia. 
Comprobar que la estructura se adecúa a lo preceptuado en la Orden de INT 724.', 1);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, así como personal de apoyo y auxiliar, con expresión de Orden y fecha de creación de la Unidad (Sección/Oficina).
Comprobar documentalmente.', 2);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida.', 3);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Acreditar que los técnicos están formados adecuadamente en el correcto manejo de cada uno de los instrumentos. Justificar que cada instrumento se encuentra calibrado. Comprobar si son suficientes.', 4);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.', 5);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Plan de Prevención. 
Planes de Zona, elaborados por las Secciones y fecha de aprobación por el Director General de la Guardia Civil. Comprobar si han sido revisados. Si algún plan se encuentra sin aprobar justificar la causa.', 6);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de Evaluaciones de Riesgo realizadas, diferenciando las llevadas a cabo por el Servicio de Prevención, Secciones y Oficinas, en el periodo indicado, con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido el tiempo previsto y aquellas otras circunstancias que exijan una revisión y actualización).', 7);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Evaluaciones de Riesgos.
Relación de las evaluaciones que quedan pendientes de realizar.', 8);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Comprobar si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos (Unidades especializadas).', 9);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Evaluaciones de Riesgos. 
Examinar las evaluaciones.', 10);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Propuesta de Planificación Preventiva.  
Actividades de seguimiento y control de las acciones correctoras. Acreditar que se lleva control efectivo de las acciones correctoras.', 11);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Planes de Emergencia/Autoprotección elaborados durante el periodo, diferenciando los llevados a cabo por el Servicio de Prevención, Secciones y Oficinas.', 12);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Planes de Emergencia y de Autoprotección. 
Relación de los que quedan pendientes de realizar.', 13);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Planes de Emergencia y de Autoprotección. 
Indicar simulacros realizados.', 14);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Planes de Emergencia y de Autoprotección. 
Examinar copia de la documentación acreditativa (Actas).', 15);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Procedimientos de actuación y mecanismos de coordinación entre el Servicio de Prevención y los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia en materia de vigilancia y control de la salud de los guardias civiles en relación con los riesgos derivados del ejercicio de las funciones profesionales.', 16);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Actuaciones desarrolladas destinadas a la protección de trabajadores especialmente sensible a determinados riesgos y declarados útil con limitaciones al objeto de adaptar las condiciones y exigencias que demanda el puesto de trabajo al estado psicofísico del funcionario.', 17);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Comprobar si la vigilancia de la salud está externalizada y en caso afirmativo, si se adecúa a lo preceptuado en la normativa reguladora.', 18);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Comprobar si se ha desarrollado actuaciones destinadas a la protección de trabajadores sensibles.', 19);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Actuaciones llevadas a acabo en relación a la protección de la guardia civil en situación de embarazo, parto reciente o en periodo de lactancia.', 20);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Vigilancia de la Salud. 
Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia. Comprobar que se han evaluado los puestos de trabajo.', 21);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de accidentes de trabajo con indicación de mortales, graves y que afecten a más de cuatro leves. Comprobar documentalmente.', 22);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de notificación e investigación.', 23);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Comprobar si los informes de investigación son recibidos por la Sección de Prevención de la Unidad afectada. Comprobar documentalmente.', 24);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Medidas preventivas de las propuestas en el informe de investigación INVAS23/13-SP adoptadas y, en su caso, situación en qué se encuentren las pendientes.', 25);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Responsable de ejecutar las medidas preventivas propuestas en los informes de investigación cuando el puesto de trabajo es común a nivel nacional. Comprobar documentalmente.', 26);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.', 27);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio. Comprobar documentalmente.', 28);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Información en Materia de Prevención de Riesgos Laborales. 
Manuales, Campañas de Prevención, Trípticos y cualquier otro medio informativo. 
Procedimiento empleado para justificar la recepción por el personal.
Examinar la web.
Examinar el método de control por parte del Servicio de Prevención.', 29);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios.', 30);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención.', 31);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Formación impartida y recibida. 
De cada una de las actividades contempladas en los puntos anteriores se realizará una breve descripción que al menos contenga:
Materia de Prevención de Riesgos laborales tratada: General, específica, Medidas de Emergencia…
Personal que la impartió.
Personal que lo recibió.
Número de alumnos.
Duración.
Método utilizado: Telemático, presencial, divulgativo,…', 32);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Formación impartida y recibida. 
Comprobar quien imparte la formación. Titulación.', 33);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Formación impartida y recibida. 
Comprobar quienes la reciben.', 34);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Formación impartida y recibida. 
Examinar actas de los diferentes cursos.', 35);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos laborales en el ámbito de la Dirección General de la Guardia Civil.
Comprobar documentalmente.', 36);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Medidas que se han adoptado para que las distintas Unidades de la Guardia Civil dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de actividades empresariales. Comprobación documental.', 37);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, '¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Guardia Civil?.  En caso afirmativo indicar número y tipo de actividad contratada. 
Comprobación documental.', 38);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Coordinación de actividades empresariales. 
Número y tipos de actuaciones en esta materia llevadas a cabo por el Servicio de Prevención. Comprobación documental.', 39);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales. Comprobación documental.', 40);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Guardia Civil en materia de prevención de riesgos laborales, que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.
Comprobar si existe archivo físico y digital.', 41);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. 
Comprobar documentalmente.', 42);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas en vigor, que estén o hayan sido objeto de actualización o revisión. 
Comprobar documentalmente.', 43);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Papel desempeñado por el Servicio de Prevención en el Protocolo de Acoso. Comprobar documentalmente las actuaciones del SPRL.', 44);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de propuestas de mejora y sugerencias en materia de prevención, recibidas en el Servicio de Prevención. Comprobar documentalmente.', 45);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Elaboración de la Memoria Anual. 
Las actividades preventivas que se documentan a través de una programación y memoria anual.', 46);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Elaboración de la Memoria Anual. 
Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.', 47);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Elaboración de la Memoria Anual. 
Analizar la memoria.', 48);
