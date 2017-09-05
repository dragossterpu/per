WHENEVER OSERROR EXIT FAILURE ROLLBACK
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT CARGA DE INFORMES
prompt
prompt    Autor: EZENTIS
prompt
prompt    Fecha creación: 07/06/2017
prompt =========================================================================


prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Inspecciones Generales (Guardia Civil)...
prompt =========================================================================

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Modelo Informe Inspecciones Generales (Guardia Civil)', 1, 'system', SYSDATE);

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

insert into modelos_informe(id, nombre, estandar, username_alta, fecha_alta) values (SEQ_MODELOINFORME.NEXTVAL, 'Modelo Informe Inspecciones Generales (Cuerpo Nacional de Policía)', 1, 'system', SYSDATE);

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