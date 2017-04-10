insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_alta, username_alta) 
values (SEQ_GUIAS.NEXTVAL,'ÁREAS ADMINISTRATIVAS Y APOYO GC','I.G.P.',0,trunc(sysdate),'system');


insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN PLANA MAYOR:Estructura y composición. Solicitar el Organigrama de la Plana Mayor, identificando al responsable y cada uno de los negociados que la componen','0');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN PLANA MAYOR:Dimensionado del personal que atiende los distintos negociados de la Plana Mayor. Valorar suficiencia, insuficiencia o sobredimensionamiento. Adecuación de su formación','1');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN PLANA MAYOR: Turnos y horario de trabajo. Ver si tienen encomendadas labores complementarias con su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','2');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN PLANA MAYOR: Medios informáticos con los que cuentan. Suficiencia y estado','3');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN PLANA MAYOR: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','4');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias):  Identificar al responsable y solicitar relación nominal del personal dedicado a estos cometidos. Adecuación de su formación','5');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias):  Identificar responsables de áreas','6');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias):  Identificar cajero pagador, suplente y habilitado','7');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','8');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','9');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Cursos realizados por el personal, que tengan relación con el puesto de trabajo. Detectar necesidades de formación adicionales','10');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Programas informáticos que utilizan y su compatibilidad con la Hacienda Pública. Problemática que pueda plantear su utilización','11');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Indicar cuantía de Anticipo de Caja Fija. Valorar suficiencia','12');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Cantidad autorizada de dinero en metálico en caja. Valorar suficiencia','13');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Solicitar el último informe de control financiero ejecutado por la Intervención Territorial de la Intervención General de la Administración del Estado, comprobando si se han cumplido las recomendaciones establecidas en el mismo','14');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Solicitar informe detallado de los créditos asignados, por concepto presupuestario, gestionados en  los DOS ÚLTIMOS AÑOS NATURALES y del año en curso (hasta la fecha de la inspección). Asignación inicial, modificaciones y gastado. Ver si ha habido incremento y si se ejecuta el dinero asignado, así como si se fomenta la videoconferencia en los juicios para ahorrar en gastos de dietas','15');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Solicitar informe relativo a la contratación y prestación de servicios de interpretación. Modo de prestación y provisión de estos recursos. Recursos económicos asignados. Deficiencias, problemáticas y necesidades','16');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Verificar que se envían en tiempo y forma los estados trimestrales de la  situación de tesorería, solicitando el correspondiente al último trimestre','17');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','18');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias): Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','19');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN UNIDAD/OFICINA DE GESTIÓN ECONÓMICA (UGE/OGE) (Solo Zonas y Comandancias):  Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','20');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','21');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','22');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','23');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Comprobar si el registro es único y si está informatizado o es manual. Posibles problemas que pueda plantear su manejo y operatividad.','24');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Identificar qué personas tienen acceso al mismo, tanto para los documentos de entrada como para los de salida, y medios de acceso (claves personales u otros).','25');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Solicitar datos de la actividad realizada durante los dos últimos años naturales completos, que comprenda los número de escritos físicos registrados de entrada y de salida
','26');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Solicitar datos de la actividad realizada durante los dos últimos años naturales completos, que comprenda el número de correos electrónicos registrados de entrada y de salida.
','27');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales, estableciendo la media diaria de documentos registrados (solo días laborables), así como la ratio de documentos/hora','28');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Comprobar posibles retrasos en el registro de los documentos y si se consideran normales en función de la carga de trabajo','29');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Videoconferencias que se realizan anualmente, valorando la carga de trabajo que suponen','30');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','31');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','32');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'REGISTRO Y ARCHIVO: Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','33');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','34');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','35');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','36');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Control de la actividad. Solicitar la siguiente información numérica correspondiente a los dos últimos años naturales completos, de forma separada:
Actividad-Informes:
-Recusación instructores expediente por falta grave.
-Incoación expedientes faltas graves.
-Paralización expedientes faltas graves.
-Resoluciones expedientes faltas graves.
-Archivo expedientes faltas graves.
-Resolución Recurso de Alzada expediente disciplinario.
-Permisos, licencias, etc.
-Bajas médicas.
-Riesgos laborales.
-Lesiones en acto de servicio.
-Pabellones.
-Resarcimientos y responsabilidad patrimonial.
-Chalecos antibala.
-Limitaciones de servicio.
-Comisiones de servicio.
-Productividad.
-Otros procedimientos (especificar).
-Varios (especificar).','37');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Control de la actividad. Solicitar la siguiente información numérica correspondiente a los dos últimos años naturales completos, de forma separada: Informaciones reservadas.','38');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','39');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Examinar, de manera aleatoria, algunos de los procedimientos incoados, comprobando adecuación de su estructura, si cumple con los trámites de notificación, alegaciones y resoluciones en tiempo y forma','40');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Infraestructuras (oficinas, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','41');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','42');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASESORÍA JURÍDICA (Solo Zonas): Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','43');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','44');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','45');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','46');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO: Control de la actividad. Solicitar la siguiente información correspondiente a los dos últimos años naturales completos, de forma separada:
-Número de Procedimientos incoados por falta leve.
  -Motivo por el que se ha incoado cada uno de los citados procedimientos, especificando su número.
  -Funcionarios inculpados en los mismos.
  -Resoluciones adoptadas para cada uno de dichos inculpados:
    -Archivo.
    -Reprensión.
    -Sanción (indicando el número de días).
  -Elevaciones de expediente a falta grave o muy grave.
  -Procedimientos en trámite.
  -Tiempo medio empleado para la tramitación.
-Número de informaciones reservadas:
  -Abiertas.
  -Archivadas.
  -Elevadas a procedimiento por falta leve
  -Elevadas a expediente por falta grave o muy grave.
-Reseñar los procedimientos caducados y archivados por prescripción, indicando los motivos, consignando la falta leve por la que se ha instruido y su número de procedimiento.
-Número de procedimientos por faltas graves y muy graves incoados a personal de la Comandancia, agentes implicados y sanción impuesta.
-Otros procedimientos. Número de expedientes por:
  -Lesiones en acto de servicio.
  -Resarcimiento de daños.
  -Otros (especificar).','47');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','48');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Examinar, de manera aleatoria, algunos de los procedimientos incoados, comprobando adecuación de su estructura, si cumple con los trámites de notificación, alegaciones y resoluciones en tiempo y forma','49');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Infraestructuras (oficinas, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','50');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','51');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'RÉGIMEN DISCIPLINARIO:Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','52');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','53');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','54');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','55');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Control de la actividad. Solicitar la siguiente información correspondiente a los dos últimos años naturales completos, de forma separada:
-Número de casos asignados.
-Número de entrevistas preparatorias con funcionarios:
  -Personales.
  -Telefónicas.
-Número de intervenciones preliminares ante órganos judiciales.
-Número de juicios orales.
-Número de Resoluciones definitivas:
  -Autos de sobreseimiento provisionales.
  -Autos de sobreseimiento libres.
  -Sentencias absolutorias.
  -Sentencias condenatorias.
-Número de recursos:
  -Contra resoluciones interlocutorias.
  -Contra resoluciones definitivas.','56');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','57');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Asuntos pendientes en el momento de la inspección. Justificación del posible retraso acumulado','58');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Infraestructuras (oficina, mobiliario, etc.) y medios informáticos con los que cuentan. Suficiencia y estado','59');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios.','60');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ASISTENCIA LETRADA:Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','61');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Identificar al responsable y número de personas dedicado a este cometido. Dependencia orgánica y funcional. Adecuación de su formación','62');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento.','63');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','64');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Control de la actividad. Solicitar la siguiente información numérica correspondiente a los dos últimos años naturales completos, de forma separada:
-Notas de prensa.
-Noticias propias aparecidas.
-Noticias ajenas aparecidas.
-Imágenes:
  -Video.
  -Fotos.
-Entrevistas.
-Reportajes.
-Ruedas de prensa.
-Dossier prensa.
-Conferencias.
-Actos oficiales:
  -Guardia Civil.
  -Ajenos.
-Colaboraciones:
  -Policías.
  -Otros.
-Festivales y ferias.
-Vinos de honor.
-Otras actividades no recogidas (especificar).','65');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','66');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Examinar, de manera aleatoria, varias notas de prensa emitidas, valorando la adecuación de su estructura y contenido','67');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Presencia en las redes sociales','68');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Infraestructuras (oficina, mobiliario, etc.) y medios técnicos, audiovisuales e informáticos con los que cuentan. Suficiencia y estado','69');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Horarios de actividad. Ver si tienen encomendadas labores distintas de su actividad principal. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','70');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN OFICINA PERIFERICA DE COMUNICACIÓN (Solo Comandancias)Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','71');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación y cualificación','72');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','73');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','74');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Control de la actividad. Solicitar una relación numérica de la actividad realizada durante los dos últimos años naturales completos, de forma separada','75');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','76');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Infraestructuras (oficina, mobiliario, etc.), equipos y medios informáticos con los que cuentan. Suficiencia y estado','77');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Horarios de actividad. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','78');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'NÚCLEO DE DESTINOS (Solo Comandancias)Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','79');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASIdentificar al responsable de supervisar las contestaciones y quiénes las contestan. Dependencia orgánica y funcional. Adecuación de su formación','80');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASDimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento. Compatibilización con otras tareas','81');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASNúmero y dónde se encuentran ubicados los libros de atención al ciudadano. Valorar suficiencia','82');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASExistencia de cartel anunciador','83');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASComprobación de que se hallan bien cumplimentado','84');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASComprobar tiempos de contestación y si exceden de los establecidos en la Instrucción 7/2007','85');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASComprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','86');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASControl de la actividad. Solicitar la siguiente información numérica, correspondiente a los dos últimos años naturales completos, de forma separada:
-Quejas.
-Sugerencias.
-Felicitaciones
-Otras (especificar).','87');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASEn relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','88');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASComprobar varias de las contestaciones realizadas para verificar su adecuación al caso concreto y que se evitan respuestas estandarizadas','89');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASInfraestructuras (oficina, mobiliario, etc.), y medios informáticos con los que cuentan. Suficiencia y estado','90');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASHorarios de actividad. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','91');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'QUEJAS Y SUGERENCIASProblemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','92');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Identificar al responsable y número de personas dedicado a este cometido. Adecuación de su formación','93');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Dimensionado del personal. Valorar suficiencia, insuficiencia o sobredimensionamiento','94');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Comprobar qué actividades realizan y si tienen establecido un reparto de tareas entre los distintos funcionarios que permita valorar la carga individual de trabajo','95');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Control de la actividad. Solicitar una relación numérica de la actividad realizada durante los dos últimos años naturales completos, de forma separada','96');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)En relación con los datos del apartado anterior, comparar los resultados de ambos periodos anuales','97');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Infraestructuras (oficina, mobiliario, etc.), equipos y medios informáticos con los que cuentan. Suficiencia y estado','98');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Horarios de actividad. Comprobar que se cumple con la normativa en vigor sobre Jornada de Trabajo y medios con los que se cuenta para controlar el cumplimiento de los horarios','99');
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'INSPECCIÓN DE ACCIÓN SOCIAL. (Solo Zonas y Comandancias)Problemática y necesidades en relación con personal y medios, solicitando copia de los escritos mediante los que han comunicado dichas necesidades y las respuestas a los mismos, si se han producido','100');
commit;