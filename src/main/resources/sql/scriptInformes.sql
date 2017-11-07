WHENEVER OSERROR EXIT FAILURE ROLLBACK
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT CARGA DE MODELOS DE INFORME
prompt
prompt    Autor: EZENTIS
prompt
prompt    Fecha actualización: 07/11/2017
prompt =========================================================================


prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Inspecciones Generales (Guardia Civil)...
prompt =========================================================================

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Inspecciones Generales (Guardia Civil)', 1, 'system', SYSDATE);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ÁMBITO Y OBJETIVOS DE LA INSPECCIÓN', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Unidad inspeccionada.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Ámbito territorial, población y servicios.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Objetivos generales y específicos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Problemática de interés policial.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 5);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'REUNIONES Y VISITAS INSTITUCIONALES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Con Autoridades.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Con Asociaciones Civiles.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Con Asociaciones Profesionales.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INFRAESTRUCTURAS E INSTALACIONES', SEQ_MODELOINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Situación y estado de los inmuebles.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Instalaciones y equipos.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Medidas de seguridad y protección de los acuartelamientos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Depósito de detenidos.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Galería de tiro.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Otros aspectos relevantes.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', SEQ_MODELOINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Plantilla y distribución.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Resumen general de la carga de trabajo de la Unidad.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Horarios de servicio.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicio de Sanidad. Gabinete de Psicología.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Absentismo.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Enseñanza.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', SEQ_MODELOINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Materia Móvil.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Armamento, equipamento policial y vestuario.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Informática y telecomunicaciones.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ÁREAS ADMINISTRATIVAS Y DE APOYO', SEQ_MODELOINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Plana Mayor.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Gestión económica.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Registro y archivo.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Régimen Disciplinario y Asistencia Letrada.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Oficina Periférica de Comuniación.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Núcleo de Destinos.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Acción social.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Quejas y sugerencias.', SEQ_AREASINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 9);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ÁREA OPERATIVA', SEQ_MODELOINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Seguridad Ciudadana.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Protección y Seguridad.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Policía Judicial.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Información.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Tráfico.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicio de Protección de la Naturaleza (SEPRONA).', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Unidades Fiscales.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Intervención de Armas y Explosivos.', SEQ_AREASINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Grupo de Desactivación de Explosivos y NRBQ.', SEQ_AREASINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicio Marítimo.', SEQ_AREASINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Grupo de Actividades Subacuáticas (GEAS).', SEQ_AREASINFORME.CURRVAL, 11);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Grupo de Rescate e Intervención de Montaña (GREIM).', SEQ_AREASINFORME.CURRVAL, 12);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicio Cinológico y de Remonta.', SEQ_AREASINFORME.CURRVAL, 13);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicio Aéreo.', SEQ_AREASINFORME.CURRVAL, 14);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 15);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'LIBROS OFICIALES', SEQ_MODELOINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Verificación de la cumplimentación de los libros oficiales.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 2);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PROGRAMA ESTADÍSTICO DE SEGURIDAD', SEQ_MODELOINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Control de grabación.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Evolución delictiva.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 3);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Inspecciones Generales (Cuerpo Nacional de Policía)...
prompt =========================================================================

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Inspecciones Generales (Cuerpo Nacional de Policía)', 1, 'system', SYSDATE);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ÁMBITO Y OBJETIVOS DE LA INSPECCIÓN', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Unidad inspeccionada.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Ámbito territorial, población y servicios institucionales.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Objetivos generales y específicos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Problemática de interés policial.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 5);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'REUNIONES Y VISITAS INSTITUCIONALES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Con Autoridades.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Con Asociaciones Civiles.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Con Representantes Sindicales.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INFRAESTRUCTURAS E INSTALACIONES', SEQ_MODELOINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Situación y estado de los inmuebles.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Instalaciones y equipos.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Medidas de seguridad y protección de los edificios policiales.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Depósito de detenidos.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Galería de tiro.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Otros aspectos relevantes.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', SEQ_MODELOINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Plantilla y distribución.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Resumen general de la carga de trabajo.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Horarios de servicio.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Unidad de Sanidad.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Absentismo.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Formación.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', SEQ_MODELOINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Automoción.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Armamento, equipamento policial y vestuario.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Informática y telecomunicaciones.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ÁREAS ADMINISTRATIVAS Y DE APOYO', SEQ_MODELOINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Secretaría General.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Gestión económica-Habilitación.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Registro y archivo. Gestión y tramitación de la documentación.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Régimen Disciplinario y Asistencia Letrada.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Gabinete de Prensa.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Documentación (DNI-pasaportes-extranjeros).', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Quejas y sugerencias.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 8);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ÁREA OPERATIVA', SEQ_MODELOINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Seguridad Ciudadana.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Extranjería y Fronteras.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Policía Judicial.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Policía Científica.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Información.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Medios Aéreos.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'LIBROS OFICIALES', SEQ_MODELOINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Verificación de la cumplimentación de los libros oficiales.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 2);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PROGRAMA ESTADÍSTICO DE SEGURIDAD', SEQ_MODELOINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Control de grabación.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Evolución delictiva.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 3);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Centro de Internamiento de Extranjeros...
prompt =========================================================================

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Centro de Internamiento de Extranjeros - CIE', 1, 'system', SYSDATE);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ORGANIZACIÓN INTERIOR DEL CENTRO DE INTERNAMIENTO', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Infraestructuras, instalaciones y medios básicos.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Departamentos y servicios - Recursos Humanos.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Departamentos y servicios - Recursos Materiales.', SEQ_AREASINFORME.CURRVAL, 3);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ESTRUCTURA Y FUNCIONES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Dirección.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Junta de coordinación.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Unidad de seguridad.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Administración.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Secretaría.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicio de Asistencia Sanitaria.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Servicios de asistencia social, jurídica y cultural.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ESTATUTO JURIDICO DE LOS EXTRANJEROS INTERNADOS', SEQ_MODELOINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Derechos y deberes de los internos.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Protección de datos de carácter personal.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Peticiones, quejas y recursos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Entrevista personal con el director.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PROCEDIMIENTOS DE ACTUACIÓN, INGRESOS, SALIDAS, TRASLADOS Y CONDUCCIONES', SEQ_MODELOINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Ingreso en el centro.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conducciones, desplazamientos y traslados.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Salida del centro.', SEQ_AREASINFORME.CURRVAL, 3);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'NORMAS DE CONVIVENCIA Y REGIMEN INTERIOR', SEQ_MODELOINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Horario y medidas de régimen interior del centro.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Régimen de las comunicaciones.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Visitas de familiares y otras personas.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Comunicaciones telefónicas.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Actividades recreativas.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Práctica religiosa.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Envío y recepción de correspondencia.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Recepción de entregas y paquetes.', SEQ_AREASINFORME.CURRVAL, 8);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'FORMACIÓN DEL PERSONAL DEL CENTRO Y MECANISMOS DE CONTROL E INSPECCIÓN', SEQ_MODELOINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Formación y reglas de conducta del personal de los centros.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Mecanismos de control e inspección - Inspección y control.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Mecanismos de control e inspección - Libros-registro.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Seguimiento de la prestación de los servicios sanitarios, asistenciales y sociales.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'MEDIDAS DE SEGURIDAD', SEQ_MODELOINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Medidas de seguridad.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PARTICIPACIÓN Y COLABORACIÓN DE LAS ORGANIZACIONES NO GUBERNAMENTALES', SEQ_MODELOINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Participación y colaboración de las organizaciones no gubernamentales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'MEJORAS DESDE LA ÚLTIMA INSPECCIÓN', SEQ_MODELOINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Mejoras desde la última inspección.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'CONCLUSIONES FINALES', SEQ_MODELOINFORME.CURRVAL, 11);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones finales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PROPUESTAS FINALES', SEQ_MODELOINFORME.CURRVAL, 12);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Propuestas finales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ANEXOS', SEQ_MODELOINFORME.CURRVAL, 13);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Anexos.', SEQ_AREASINFORME.CURRVAL, 1);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Prevención de Riesgos Laborales (Guardia Civil)...
prompt =========================================================================

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Prevención de Riesgos Laborales - PRL (Guardia Civil)', 1, 'system', SYSDATE);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Estructura, dependencia y ámbito de competencia (Unidades, personas e infraestructuras).', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Plan de Prevención.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Evaluaciones de Riesgos y Propuesta de Planificación Preventiva.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Planes de Emergencia y de Autoprotección.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Vigilancia de la salud.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Formación.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Asesoramiento e Informes emitidos.', SEQ_AREASINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Coordinación de Actividades Empresariales.', SEQ_AREASINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Registro y Archivo de Documentación.', SEQ_AREASINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Procedimientos e Instrucciones Operativas de P.R.L.', SEQ_AREASINFORME.CURRVAL, 11);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Redacción de la Memoria Anual y Programación Anual.', SEQ_AREASINFORME.CURRVAL, 12);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Otros aspectos evaluados.', SEQ_AREASINFORME.CURRVAL, 13);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'CONCLUSIONES FINALES', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones finales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PROPUESTAS FINALES', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Propuestas finales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ANEXOS', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Anexos.', SEQ_AREASINFORME.CURRVAL, 1);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Prevención de Riesgos Laborales (Cuerpo Nacional de Policía)...
prompt =========================================================================

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Prevención de Riesgos Laborales - PRL (Cuerpo Nacional de Policía)', 1, 'system', SYSDATE);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Estructura, dependencia y ámbito de competencia (Unidades, personas e infraestructuras).', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Plan de Prevención.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Evaluaciones de Riesgos y Propuesta de Planificación Preventiva.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Planes de Emergencia y de Autoprotección.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Vigilancia de la salud.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Formación.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Asesoramiento e Informes emitidos.', SEQ_AREASINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Coordinación de Actividades Empresariales.', SEQ_AREASINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Registro y Archivo de Documentación.', SEQ_AREASINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Procedimientos e Instrucciones Operativas de P.R.L.', SEQ_AREASINFORME.CURRVAL, 11);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Redacción de la Memoria Anual y Programación Anual.', SEQ_AREASINFORME.CURRVAL, 12);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Otros aspectos evaluados.', SEQ_AREASINFORME.CURRVAL, 13);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'CONCLUSIONES FINALES', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Conclusiones finales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'PROPUESTAS FINALES', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Propuestas finales.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, 'ANEXOS', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, 'Anexos.', SEQ_AREASINFORME.CURRVAL, 1);

COMMIT;