insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_alta, username_alta) values (SEQ_GUIAS.NEXTVAL,'ÁREAS ADMINISTRATIVAS Y APOYO PN','I.G.P.',0,trunc(sysdate),'system');



insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN SECRETARÍA GENERAL:Solicitar el Organigrama de la Secretaría, identificando al responsable y cada uno de los departamentos que la componen','0');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN SECRETARÍA GENERAL:Dimensionado del personal que atiende los departamentos de la Secretaría. Valorar suficiencia, insuficiencia o sobredimensionamiento. Adecuación de su formación','1');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN SECRETARÍA GENERAL:Turnos y horario de trabajo. Ver si tienen encomendadas labores complementarias con su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','2');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN SECRETARÍA GENERAL:Medios informáticos con los que cuentan. Suficiencia y estado','3');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN SECRETARÍA GENERAL:Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','4');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Identificar al responsable y solicitar relación nominal del personal dedicado a estos cometidos. Adecuación de su formación','5');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Identificar al cajero pagador, suplente y habilitado','6');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','7');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','8');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Cursos realizados por el personal, que tengan relación con el puesto de trabajo. Detectar necesidades de formación adicionales','9');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Programas informáticos que utilizan y su compatibilidad con la Hacienda Pública. Problemática que pueda plantear su utilización','10');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Solicitar el último informe de control financiero ejecutado por la Intervención Territorial de la Intervención General de la Administración del Estado, comprobando si se han cumplido las recomendaciones establecidas en el mismo','11');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Solicitar informe detallado de los créditos asignados, por concepto presupuestario, gestionados en  los DOS ÚLTIMOS AÑOS NATURALES y del año en curso (hasta la fecha de la inspección). Asignación inicial, modificaciones y gastado. Ver si ha habido incremento y si se ejecuta el dinero asignado, así como si se fomenta la videoconferencia en los juicios para ahorrar en gastos de dietas','12');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Indicar cuantía de Anticipo de Caja Fija. Suficiencia de la cantidad asignada','13');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Cantidad autorizada de dinero en metálico en caja. Suficiencia de dicha cantidad','14');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Solicitar informe relativo a la contratación y prestación de servicios de interpretación. Modo de prestación y provisión de estos recursos. Recursos económicos asignados. Deficiencias, problemáticas y necesidades','15');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Verificar que se envían en tiempo y forma los estados trimestrales de la  situación de tesorería, solicitando el correspondiente al último trimestre','16');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','17');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','18');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GESTIÓN ECONÓMICA Y HABILITACIÓN (Solo Jefaturas Superiores y Comisarías Provinciales)Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','19');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','20');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','21');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','22');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Comprobar si el registro es único y si está informatizado o es manual. Posibles problemas que pueda plantear su manejo y operatividad','23');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Identificar qué personas tienen acceso al mismo, tanto para los documentos de entrada como para los de salida, y medios de acceso (claves personales u otros).','24');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Solicitar datos de la actividad realizada durante los dos últimos años naturales completos, que comprenda:
Número de escritos físicos registrados de entrada y de salida.
Número de correos electrónicos registrados de entrada y de salida
','25');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales, estableciendo la media diaria de documentos registrados (solo días laborables), así como la ratio de documentos/hora','26');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Comprobar posibles retrasos en el registro de los documentos y si se consideran normales en función de la carga de trabajo','27');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Videoconferencias que se realizan anualmente, valorando la carga de trabajo que suponen','28');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','29');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','30');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO GENERAL: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','31');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','32');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','33');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','34');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Dimensionado del archivo. Si está informatizado o no (proponer digitalización, en su caso). Posible saturación (necesidad de espurgue)','35');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Control de la actividad realizada mediante la solicitud de los siguientes datos, correspondientes a los dos últimos años naturales completos, de forma separada:
Número de legajos abiertos.
Número de documentos tramitadosarchivados por día (media).
Tiempo medio de retraso en el archivo de los documentos desde su tramitación.
Espurgue de legajos realizado.
Número de consultadas realizadas (Libro de control).','36');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','37');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Procedimiento de archivo de los atestados (y número de los archivados cada año) sin autor conocido y no enviados a la Autoridad Judicial ni al Ministerio Fiscal, de conformidad con lo dispuesto en el artículo 284 de la Ley de Enjuiciamiento Criminal','38');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Comprobar “in situ” posibles retrasos en el registro de los documentos y si se consideran normales en función de la carga de trabajo','39');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','40');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','41');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ARCHIVO GENERAL: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','42');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','43');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','44');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','45');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Control de la actividad. Solicitar la siguiente información correspondiente a los dos últimos años naturales completos, de forma separada:
Número de Procedimientos incoados por falta leve.
Motivo por el que se ha incoado cada uno de los citados procedimientos, especificando su número.
Funcionarios inculpados en los mismos.
Resoluciones adoptadas para cada uno de dichos inculpados:
Archivo.
Apercibimiento.
Sanción (indicando el número de días).
Elevaciones de expediente a falta grave o muy grave.
Procedimientos en trámite.
Tiempo medio empleado para la tramitación.
Número de informaciones reservadas:
Abiertas.
Archivadas.
Elevadas a procedimiento por falta leve
Elevadas a expediente por falta grave o muy grave.
Reseñar los procedimientos caducados y archivados por prescripción, indicando los motivos, consignando la falta leve por la que se ha instruido y su número de procedimiento.
Número de procedimientos por faltas graves y muy graves incoados a personal de la Comisaría, funcionarios implicados y sanción impuesta.
Otros procedimientos. Número de expedientes por:
Lesiones en acto de servicio.
Resarcimiento de daños.
Otros (especificar).','46');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','47');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Examinar, de manera aleatoria, algunos de los procedimientos incoados, comprobando adecuación de su estructura, si cumple con los trámites de notificación, alegaciones y resoluciones en tiempo y forma','48');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Infraestructuras (oficinas, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','49');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','50');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','51');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','52');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','53');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','54');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Control de la actividad. Solicitar la siguiente información correspondiente a los dos últimos años naturales completos, de forma separada:
Número de casos asignados.
Número de entrevistas preparatorias con funcionarios:
Personales.
Telefónicas.
Número de intervenciones preliminares ante órganos judiciales.
Número de juicios orales.
Número de Resoluciones definitivas:
Autos de sobreseimiento provisionales.
Autos de sobreseimiento libres.
Número de recursos:
Contra resoluciones interlocutorias.
Contra resoluciones definitivas.','55');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','56');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Asuntos pendientes en el momento de la inspección. Justificación del posible retraso acumulado','57');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','58');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','59');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','60');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','61');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','62');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','63');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Control de la actividad. Solicitar la siguiente información numérica correspondiente a los dos últimos años naturales completos, de forma separada:
Notas de prensa.
Ruedas de prensa.
Noticias propias aparecidas en prensa.
Noticias propias aparecidas en radio.
Noticias propias aparecidas en televisión.
Noticias ajenas aparecidas en medios de comunicación.
Intervenciones en radio.
Intervenciones en televisión.
Intervenciones en otros medios (especificar cuáles).
Conferencias.
Actos oficiales.
Colaboraciones (especificar).
Festivales y ferias.
Otras actividades no recogidas (especificar).','64');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','65');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Examinar, de manera aleatoria, varias notas de prensa emitidas, valorando la adecuación de su estructura y contenido','66');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Presencia en las redes sociales','67');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Infraestructuras (oficina, mobiliario, etc.) y medios técnicos, audiovisuales e informáticos con los que cuentan. Suficiencia y estado','68');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','69');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN GABINETE DE PRENSA (Solo Jefaturas Superiores y Comisarías Provinciales)Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','70');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','71');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Dimensionado del personal. Equipos establecidos. Turnos de mañana, tarde y/o jornada continuada. Valorar suficiencia, insuficiencia o sobredimensionamiento. Existencia de equipos móviles y análisis ','72');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','73');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Funcionamiento de la cita previa. Retrasos. Existencia de aglomeración de gente en determinados horarios (colas). Propuestas de mejora','74');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE:  Control de la actividad. Solicitar la siguiente información numérica, POR CADA EQUIPO, correspondiente a los dos últimos años naturales completos, de forma separada:
Producción de DNI (jornada ordinaria):
Número de funcionarios.
Número de terminales/Puestos de trabajo.
Citas previas anuales DNI.
DNI,s expedidos.
Número de Tarjetas anuladas:
Número total.
Causas:
Chip.
Impresora
Otras (especificar).
Informes emitidos.
Producción de Pasaportes (jornada ordinaria):
Número de funcionarios.
Número de terminales/Puestos de trabajo.
Citas previas anuales Pasaportes.
Pasaportes expedidos.
Número de Libretas anuladas:
Número total.
Causas:
Chip.
Impresora
Otras (especificar).
Informes emitidos.
Producción de DNI+Pasaportes (en horas extraordinarias):
Recabar la misma información de los apartados anteriores, pero referida a la actividad durante las horas extraordinarias, en su caso.
Otros trámites documentales que se realizan (especificar).
DNI RURAL:
Número de funcionarios.
DNI,s expedidos.','75');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','76');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Tiempos de tramitación empleados (por días laborables, puestos de trabajo, funcionario, etc.).','77');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Examinar, de manera aleatoria, varias actas de destrucción de DNI y Pasaportes, valorando los motivos y su posible corrección','78');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Porcentaje de cobro en metálico (seguridad en la conservación e ingreso bancario) y de pago telemático previo','79');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Infraestructuras (oficina, mobiliario, etc.), equipos de DNI-Pasaportes, y medios informáticos con los que cuentan. Suficiencia y estado','80');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Horarios de actividad. Especificar el horario de atención al público (indicando el horario de verano). Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios. Horas extraordinarias que se realizan valorando si son necesarias','81');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE CIUDADANOS ESPAÑOLES: D.N.I. y PASAPORTE: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','82');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','83');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento. Puestos de trabajo existentes para atender al público ','84');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','85');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Control de la actividad. Solicitar la siguiente información numérica, correspondiente a los dos últimos años naturales completos, de forma separada:
Número de funcionarios.
Número total de trámites realizados:
T.I.E.
Certificados
Certificados UE
Asignación N.I.E.
Prórrogas de estancia.
Residencias no lucrativas.
Solicitud Asilo y Refugio.
Títulos de Viaje.
Cédulas de inscripción.
Otros (especificar).','86');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','87');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Tiempos de tramitación empleados (por días laborables, puestos de trabajo, funcionario, etc.). Posibles retrasos en la expedición de informes de nacionalidad y otros documentos, motivación y soluciones','88');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Control que se tiene de las Cartas de invitación, en el sentido de si comprueban la salida del invitado cuando llega la fecha de marcharse','89');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Infraestructuras (oficina, mobiliario, etc.), y medios informáticos con los que cuentan. Suficiencia y estado','90');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Horarios de actividad. Especificar el horario de atención al público (indicando el horario de verano). Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','91');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'DOCUMENTACIÓN DE EXTRANJEROS: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','92');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Identificar al responsable de supervisar las contestaciones y quiénes las contestan. Dependencia orgánica y funcional. Adecuación de su formación','93');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento. Compatibilización con otras tareas','94');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Número y dónde se encuentran ubicados los libros de atención al ciudadano. Valorar suficiencia','95');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Existencia de cartel anunciador','96');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Comprobación de que se hallan bien cumplimentados','97');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Comprobar tiempos de contestación y si exceden de los establecidos en la Instrucción 7/2007','98');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','99');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Control de la actividad. Solicitar la siguiente información numérica, correspondiente a los dos últimos años naturales completos, de forma separada:
Quejas.
Sugerencias.
Felicitaciones
Otras (especificar).','100');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','101');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Comprobar varias de las contestaciones realizadas para verificar su adecuación al caso concreto y que se evitan respuestas estandarizadas','102');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Infraestructuras (oficina, mobiliario, etc.), y medios informáticos con los que cuentan. Suficiencia y estado','103');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Horarios de actividad. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','104');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIAS: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','105');

COMMIT;