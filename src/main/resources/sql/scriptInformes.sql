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

insert into modelos_informe(id, nombre) values (SEQ_MODELOINFORME.NEXTVAL, 'Modelo Informe Inspecciones Generales (Guardia Civil)');

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '1.- INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '1.1.- Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '2.- ÁMBITO Y OBJETIVOS DE LA INSPECCIÓN', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.1- Unidad inspeccionada.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.2- Ámbto territorial, población y servicios.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.3- Objetivos generales y específicos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.4- Problemática de interés policial.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.5- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 5);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '3.- REUNIONES Y VISITAS INSTITUCIONALES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.1.- Con Autoridades.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.2.- Con Asociaciones Civiles.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.3.- Con Asociaciones Profesionales.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.4.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '4.- INFRAESTRUCTURAS E INSTALACIONES', SEQ_MODELOINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.1.- Situación y estado de los inmuebles.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.2.- Instalaciones y equipos.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.3.- Medidas de seguridad y protección de los acuartelamientos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.4.- Depósito de detenidos.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.5.- Galería de tiro.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.6.- Otros aspectos relevantes.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.7.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '5.- DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', SEQ_MODELOINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.1.- Plantilla y distribución.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.2.- Resumen general de la carga de trabajo de la Unidad.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.3.- Horarios de servicio.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.4.- Servicio de Sanidad. Gabinete de Psicología.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.5.- Absentismo.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.6.- Enseñanza.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.7.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '6.- DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', SEQ_MODELOINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.1.- Materia Móvil.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.2.- Armamento, equipamento policial y vestuario.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.3.- Informática y telecomunicaciones.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.4.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '7.- ÁREAS ADMINISTRATIVAS Y DE APOYO', SEQ_MODELOINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.1.- Plana Mayor.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.2.- Gestión económica.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.3.- Registro y archivo.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.4.- Régimen Disciplinario y Asistencia Letrada.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.5.- Oficina Periférica de Comuniación.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.6.- Núcleo de Destinos.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.7.- Acción social.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.8.- Quejas y sugerencias.', SEQ_AREASINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.9.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 9);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '8.- ÁREA OPERATIVA', SEQ_MODELOINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.1.- Seguridad Ciudadana.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.2.- Protección y Seguridad.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.3.- Policía Judicial.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.4.- Información.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.5.- Tráfico.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.6.- Servicio de Protección de la Naturaleza (SEPRONA).', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.7.- Unidades Fiscales.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.8.- Intervención de Armas y Explosivos.', SEQ_AREASINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.9.- Grupo de Desactivación de Explosivos y NRBQ.', SEQ_AREASINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.10.- Servicio Marítimo.', SEQ_AREASINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.11.- Grupo de Actividades Subacuáticas (GEAS).', SEQ_AREASINFORME.CURRVAL, 11);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.12.- Grupo de Rescate e Intervención de Montaña (GREIM).', SEQ_AREASINFORME.CURRVAL, 12);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.13.- Servicio Cinológico y de Remonta.', SEQ_AREASINFORME.CURRVAL, 13);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.14.- Servicio Aéreo.', SEQ_AREASINFORME.CURRVAL, 14);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.15.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 15);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '9.- LIBROS OFICIALES', SEQ_MODELOINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '9.1.- Verificación de la cumplimentación de los libros oficiales.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '9.2.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 2);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '10.- PROGRAMA ESTADÍSTICO DE SEGURIDAD', SEQ_MODELOINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '10.1.- Control de grabación.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '10.2.- Evolución delictiva.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '10.3.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 3);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Inspecciones Generales (Cuerpo Nacional de Policía)...
prompt =========================================================================

insert into modelos_informe(id, nombre) values (SEQ_MODELOINFORME.NEXTVAL, 'Modelo Informe Inspecciones Generales (Cuerpo Nacional de Policía)');

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '1.- INTRODUCCIÓN', SEQ_MODELOINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '1.1.- Introducción.', SEQ_AREASINFORME.CURRVAL, 1);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '2.- ÁMBITO Y OBJETIVOS DE LA INSPECCIÓN', SEQ_MODELOINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.1.- Unidad inspeccionada.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.2.- Ámbito territorial, población y servicios institucionales.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.3.- Objetivos generales y específicos.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.4.- Problemática de interés policial.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '2.5.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 5);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '3.- REUNIONES Y VISITAS INSTITUCIONALES', SEQ_MODELOINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.1.- Con Autoridades.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.2.- Con Asociaciones Civiles.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.3.- Con Representantes Sindicales.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '3.4.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '4.- INFRAESTRUCTURAS E INSTALACIONES', SEQ_MODELOINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.1.- Situación y estado de los inmuebles.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.2.- Instalaciones y equipos.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.3.- Medidas de seguridad y protección de los edificios policiales.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.4.- Depósito de detenidos.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.5.- Galería de tiro.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.6.- Otros aspectos relevantes.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '4.7.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '5.- DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', SEQ_MODELOINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.1.- Plantilla y distribución.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.2.- Resumen general de la carga de trabajo.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.3.- Horarios de servicio.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.4.- Unidad de Sanidad.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.5.- Absentismo.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.6.- Formación.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '5.7.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '6.- DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', SEQ_MODELOINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.1.- Automoción.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.2.- Armamento, equipamento policial y vestuario.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.3.- Informática y telecomunicaciones.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '6.4.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 4);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '7.- ÁREAS ADMINISTRATIVAS Y DE APOYO', SEQ_MODELOINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.1.- Secretaría General.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.2.- Gestión económica-Habilitación.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.3.- Registro y archivo. Gestión y tramitación de la documentación.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.4.- Régimen Disciplinario y Asistencia Letrada.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.5.- Gabinete de Prensa.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.6.- Documentación (DNI-pasaportes-extranjeros).', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.7.- Quejas y sugerencias.', SEQ_AREASINFORME.CURRVAL, 7);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '7.8.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 8);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '8.- ÁREA OPERATIVA', SEQ_MODELOINFORME.CURRVAL, 8);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.1.- Seguridad Ciudadana.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.2.- Extranjería y Fronteras.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.3.- Policía Judicial.', SEQ_AREASINFORME.CURRVAL, 3);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.4.- Policía Científica.', SEQ_AREASINFORME.CURRVAL, 4);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.5.- Información.', SEQ_AREASINFORME.CURRVAL, 5);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.6.- Medios Aéreos.', SEQ_AREASINFORME.CURRVAL, 6);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '8.7.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 7);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '9.- LIBROS OFICIALES', SEQ_MODELOINFORME.CURRVAL, 9);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '9.1.- Verificación de la cumplimentación de los libros oficiales.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '9.2.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 2);

insert into areas_informe(id, descripcion, modelo_informe_id, orden) values (SEQ_AREASINFORME.NEXTVAL, '10.- PROGRAMA ESTADÍSTICO DE SEGURIDAD', SEQ_MODELOINFORME.CURRVAL, 10);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '10.1.- Control de grabación.', SEQ_AREASINFORME.CURRVAL, 1);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '10.2.- Evolución delictiva.', SEQ_AREASINFORME.CURRVAL, 2);
insert into subareas_informe(id, descripcion, area_id, orden) values (SEQ_SUBAREASINFORME.NEXTVAL, '10.3.- Conclusiones y propuestas.', SEQ_AREASINFORME.CURRVAL, 3);

COMMIT;