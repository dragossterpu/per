 --DATOS GENERALES.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(SEQ_GUIAS.NEXTVAL, 'DATOS GENERALES', 'I.T_PRL', 15,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Estructura, Dependencia y Ámbito de Competencia.
Relación documentada de las actividades preventivas encuadradas en las especialidades contempladas no asumidas por el Servicio de Prevención y para cuya utilización se ha recurrido a un Servicio de Prevención Ajeno.
Analizar la estructura y configuración actual del SP.
Examinar el concierto suscrito con el Servicio de Prevención de riesgos Laborales ajeno, al objeto de comprobar si reúne las condiciones dispuestas en el artículo 20 apartado 1 del R.D. 39/1997.
Analizar la situación competencial  en materia de vigilancia de la salud', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Recursos Humanos.
Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, así como personal de apoyo y auxiliar.
Examinar la norma en la que se materializa la creación del S.P.
Estructura Central: 
Examinar el catálogo de recursos humanos destinados en S.P.
Comprobar el personal destinado realmente en S.P concretando situación: fijos, en comisión de servicio, adscripción temporal, etc.
Estructura Periférica:
Examinar la documentación, si existe, en la que conste la creación de dichos puestos de trabajo, sus funciones y tipo de dependencia (orgánica y funcional) 
Examinar ajuste entre lo dispuesto en el Plan de Prevención y la situación real.
', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Medios Materiales.
Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida. Acreditar si los Técnicos del Servicio de Prevención están instruidos en el uso de los equipos y si éstos están, en su caso, calibrados. 
Examinar copia documental por muestreo de las últimas calibraciones  de aquel instrumental que lo requiere y si se encuentra en vigor.
Verificar si los técnicos poseen el conocimiento necesario sobre el manejo de los instrumentos.
Verificar si son suficientes', 2);

--FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(SEQ_GUIAS.NEXTVAL, 'FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN', 'I.T_PRL', 16,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Plan de Prevención.
Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.
Deficiencias e incumplimientos detectados y puestos de manifiesto durante el desarrollo e implantación del Plan.
En el caso de haberse detectado deficiencias, ¿se han elevado propuestas de revisión y modificación del Plan de Prevención al objeto de adaptarlo a la situación real que permita llevar a cabo adecuadamente las actividades preventivas de las especialidades asumidas por el Servicio? En caso afirmativo acreditar las propuestas.
Verificar cumplimiento de lo establecido en el Art. 3 del RD 67/2010 (Plan de Prevención). 
Comprobar si se ha realizado o se está realizando alguna revisión del Plan a fecha de hoy: contenido y alcance.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Evaluaciones de Riesgos.
Relación de Evaluaciones de Riesgo realizadas en el periodo indicado con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido cuatro años y aquellas otras circunstancias que exijan una revisión y actualización).
Si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos.
Relación de plantillas o unidades a las que se ha practicado revisiones de evaluación indicando las circunstancias y el Servicio de Prevención que las ha llevado a cabo. Previsiones o programación de las evaluaciones pendientes de realizar y el Servicio de Prevención que vaya a asumir su realización.
Comprobar quién realiza las Evaluaciones de Riesgo Servicio Prevención Propio, Servicio Prevención Ajeno o una fórmula intermedia. 
Si se ha recurrido a un SPA (total o parcialmente) verificar todos aquellos aspectos relativos a la realización de dichas Evaluaciones: distribución geográfica y temporal (cronograma), si se realizan por separado o conjuntamente, que tipo de riesgos evalúa uno y otro servicio de prevención, etc. 
Verificar el grado de cumplimiento de los distintos aspectos exigidos en la normativa de aplicación.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Propuesta de Planificación Preventiva.
Actividades de seguimiento y control de las Fichas de Seguimiento de las Acciones Correctoras.
Control del cumplimiento de la Actividad Preventiva: que forma o de que instrumento dispone para velar porque la misma sea eficaz e incluso vigilar y supervisar que las distintas unidades policiales llevan a cabo estas actividades conforme a la citada normativa.
Verificar si el SP controla adecuadamente el cumplimiento de la IOPR 006 y el procedimiento de seguimiento y control de las medidas correctoras por parte de las distintas Unidades.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Planes de Emergencia y de Autoprotección.
Planes de Emergencia/Autoprotección supervisados y/o elaborados por el Servicio de Prevención durante el periodo.
Indicar simulacros realizados. 
Análisis de las actas levantadas con ocasión de la realización de simulacros.
Comprobar si el modelo de Plan de Medidas de Emergencia se ha distribuido entre las plantillas, así como que actividades de supervisión y asesoramiento han desarrollado al respecto. 
Comprobar el mecanismo de control de plantillas que contestan, de la supervisión y asesoramiento que se ha ejercido, igual respecto de los simulacros, etc.)', 3);


insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Vigilancia de la Salud (parte I).
Protocolos de reconocimiento médico específico en relación a los riesgos en el puesto de trabajo enumerando aquellos que por razón de la naturaleza de la actividad profesional sean de carácter obligatorio, con indicación de la periodicidad establecida.
Informes, dictámenes y recomendaciones emitidos en materia de vigilancia de la salud: de aptitud física, de aptitud para el trabajo, de cambio de puesto de trabajo por motivos de salud, sobre riesgo durante el embarazo, parto reciente o lactancia o cualquier circunstancia o situación en el ámbito de la evaluación de la salud de los empleados públicos en relación con las condiciones del puesto de trabajo.
Actividades en materia de asistencia sanitaria, prevención y promoción de la salud dentro de las previsiones que, en materia de seguridad y salud laboral, tiene establecidos el Servicio de Prevención.
Relación y organización del personal sanitario capacitado en la materia tanto a nivel central como territorial.
Recursos materiales: relación de instalaciones, medios materiales sanitarios y de archivo de los que está dotado el servicio de prevención para desarrollar las actividades sanitarias asignadas.
Procedimientos de actuación y mecanismos de coordinación entre el Servicio Sanitario y el Servicio de Prevención en materia de vigilancia y control de la salud de los empleados públicos en el ámbito de la Dirección General de la Policía.
Verificar si se han definido los protocolos específicos para llevar a cabo la vigilancia de la salud en  función de los riesgos a los que están sometidos los funcionarios.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Vigilancia de la Salud (parte II).
Examinar el Manual de Vigilancia de la Salud donde están definidos los protocolos aplicables, en caso de que el párrafo anterior sea positivo.
Comprobar si se realizan controles del estado de salud de los trabajadores en orden a la prevención de los riesgos inherentes al trabajo.
En caso afirmativo, comprobar si la vigilancia de la salud es realizada por personal acreditado y cualificado, indicando los responsables de esta actividad.
Recabar información respecto a la asunción de competencias en relación con la vigilancia de la salud.
Relacionar con el punto primero y en particular, clarificar los aspectos por los que no ejerce esta competencia que tiene atribuida por normativa. Caso de no realizar dicho cometido, conocer si se han analizado las causas y en su caso, actuaciones que se hayan adoptado o emprendido para dar a conocer dicha situación a la superioridad, al objeto de adoptar las medidas correspondientes. De ser así, conocer el estado en que se encuentra.
Analizar la situación actual respecto de la asunción de funciones en materia de primeros auxilios y gestión de botiquines.
Verificar cumplimiento art.25 (personal sensible) y 26 (embarazo). En especial determinar cumplimiento IOPR 008 de medidas para promover la mejora de la seguridad y salud de la funcionaria embarazada.', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Relación de accidentes de trabajo con indicación de mortales, graves y que afecten a más de cuatro leves.
Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de notificación e investigación”.
Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.
Verificar cumplimientos procedimientos 1300 y 1301, control estadístico de la siniestralidad  y  relación de accidentes y enfermedades profesionales. Definir las causas que lo obstaculizan o impiden y soluciones o alternativas existentes para su solución, en el caso de incumplimiento.', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Información en Materia de  Prevención de Riesgos Laborales.
Fichas de información de carácter general, especifico o de cualquier otra naturaleza elaborados por el Servicio de Prevención, así como Manuales,Campañas de Prevención, Trípticos y cualquier otro medio informativo. Procedimiento empleado para justificar la recepción por el personal.
Verificar las medidas adoptadas para que los empleados públicos reciban información sobre los  riesgos y  las medidas de prevención y protección  que afecten a las dependencias policiales, a  su actividad o función específica  y las medidas de emergencia: examinar manuales, boletines informativos, evaluaciones de riesgos, documentos acreditativos firmados por el trabajador, página Web, etc. 
Existencia de cauces o procedimientos de  información como vehículo de transmisión de instrucciones en materia preventiva: examinar modelos de actas, instrucciones u órdenes de trabajo con acuse de recibo, etc.', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Formación impartida y recibida.
Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios. 
Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención.
De cada una de las actividades contempladas en los puntos anteriores se realizará una breve descripción que al menos contenga:
-Materia de Prevención de Riesgos laborales tratada: General, específica, Medidas de Emergencia…
-Personal que la impartió.
-Personal que lo recibió.
-Número de alumnos.
-Duración.
-Método utilizado: Telemático, presencial, divulgativo,…
Por otro lado, a fecha de la visita se tendrá documentación acreditativa de los cursos impartidos.
Comprobar los planes de formación o la aportación del servicio de prevención en los distintos planes formativos: ingreso, promoción y especialización; introducción de cambios  en los equipos de trabajo o nuevas tecnologías.
Delegados de Prevención, mandos con responsabilidades en la gestión de la prevención, etc. 
Verificar la formación recibida por los componentes del Servicio de Prevención, en materia preventiva, comprobando registros sobre características de los cursos  e instituciones que los imparten.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Asesoramiento e informes emitidos.
Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos laborales en el ámbito de la Dirección General de la Policía.
Verificar actuaciones, estudios e informes realizados en relación con las funciones encomendadas al Servicio de Prevención (Art. 18 del RD 2/2006 y 31 de la Ley de PRL.)', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Coordinación de actividades empresariales.
Qué tipo de medidas se han adoptado para que las distintas plantillas y unidades del Cuerpo Nacional de Policía dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de actividades empresariales.
¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Policía?. En caso afirmativo indicar número y tipo de actividad contratada.
Número y tipo de actuaciones en esta materia solicitadas al Servicio de Prevención por los titulares de los órganos de la Institución.
Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales.
Comprobar la relación de órganos policiales a los que para su conocimiento y difusión se ha enviado el procedimiento 404 de Coordinación de Actividades Empresariales.
Ver informaciones suministradas o actuaciones realizadas en esta materia por el Servicio de Prevención con otros organismos o empresas en aquellas actividades concurrentes en los que hayan tenido participación.
Comprobar si se ha informado a los empleados públicos la existencia de riesgos derivado de la concurrencia de actividades empresariales.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Registro y Archivo de Documentación.
En referencia a lo dispuesto en el Art. 23 de la Ley de Prevención de Riesgos Laborales, relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Policía en materia de prevención de riesgos laborales, que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.
Comprobar por muestreo el archivo y los registros documentales reglamentarios en materia preventiva: Evaluaciones de Riesgos, notificación e investigación de accidentes, etc.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Procedimientos e Instrucciones Operativas de Prevención de Riesgos Laborales.
Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. 
En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas en vigor que estén o hayan sido objeto de actualización o revisión.
Conocer y valorar la actividad en relación con los procedimientos implantados, así como los que se encuentren pendientes de aprobación o redacción y el tiempo empleado en su elaboración.', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Protocolo de actuación frente al acoso laboral.
Informar brevemente, en su caso, sobre los siguientes aspectos:
-Registros de Actuaciones por Acoso Laboral. 
-Relación de Acuerdos adoptados por Acoso laboral.  
-Número de Informes elaborados sobre situación previa de los riesgos psicosociales en la Unidad afectada.
-Relación de las actuaciones de seguimiento respecto del cumplimiento de las medidas preventivas/correctoras propuestas en el marco de los riesgos laborales.
-Número de denuncias falsas.
Comprobar las actuaciones desarrolladas  y  las obligaciones impuestas al Servicio de Prevención al amparo de lo establecido en el mencionado  Protocolo.', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Relación de propuestas de mejora y sugerencias en materia de prevención recibidas en el Servicio de Prevención.
Examinar su número, proyección, adecuación y coherencia con la normativa vigente. ', 14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL, 'Elaboración de la Memoria Anual.
Las actividades preventivas se documentan a través de una programación y memoria anual.
Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.
Informe de Auditoría Interna realizado por ese Servicio de Prevención. 
Analizar  la  actividad desplegada por la Subdirección General de Recursos Humanos en materia de prevención de riesgos laborales a través del Servicio  de Prevención, en unión de las conclusiones y valoraciones alcanzadas sobre su situación.', 15);











