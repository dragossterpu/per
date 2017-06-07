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

insert into modelos_informe(id, nombre) values (1, 'Modelo Informe Inspecciones Generales (Guardia Civil)');

insert into areas_informe(id, descripcion, modelo_informe_id) values (1, '1.- INTRODUCCIÓN', 1);
insert into subareas_informe(id, descripcion, area_id) values (1, '1.1.- Introducción.', 1);

insert into areas_informe(id, descripcion, modelo_informe_id) values (2, '2.- ÁMBITO Y OBJETIVOS DE LA INSPECCIÓN', 1);
insert into subareas_informe(id, descripcion, area_id) values (2, '2.1- Unidad inspeccionada.', 2);
insert into subareas_informe(id, descripcion, area_id) values (3, '2.2- Ámbto territorial, población y servicios.', 2);
insert into subareas_informe(id, descripcion, area_id) values (4, '2.3- Objetivos generales y específicos.', 2);
insert into subareas_informe(id, descripcion, area_id) values (5, '2.4- Problemática de interés policial.', 2);
insert into subareas_informe(id, descripcion, area_id) values (6, '2.5- Conclusiones y propuestas.', 2);

insert into areas_informe(id, descripcion, modelo_informe_id) values (3, '3.- REUNIONES Y VISITAS INSTITUCIONALES', 1);
insert into subareas_informe(id, descripcion, area_id) values (7, '3.1.- Con Autoridades.', 3);
insert into subareas_informe(id, descripcion, area_id) values (8, '3.2.- Con Asociaciones Civiles.', 3);
insert into subareas_informe(id, descripcion, area_id) values (9, '3.3.- Con Asociaciones Profesionales.', 3);
insert into subareas_informe(id, descripcion, area_id) values (10, '3.4.- Conclusiones y propuestas.', 3);

insert into areas_informe(id, descripcion, modelo_informe_id) values (4, '4.- INFRAESTRUCTURAS E INSTALACIONES', 1);
insert into subareas_informe(id, descripcion, area_id) values (11, '4.1.- Situación y estado de los inmuebles.', 4);
insert into subareas_informe(id, descripcion, area_id) values (12, '4.2.- Instalaciones y equipos.', 4);
insert into subareas_informe(id, descripcion, area_id) values (13, '4.3.- Medidas de seguridad y protección de los acuartelamientos.', 4);
insert into subareas_informe(id, descripcion, area_id) values (14, '4.4.- Depósito de detenidos.', 4);
insert into subareas_informe(id, descripcion, area_id) values (15, '4.5.- Galería de tiro.', 4);
insert into subareas_informe(id, descripcion, area_id) values (16, '4.6.- Otros aspectos relevantes.', 4);
insert into subareas_informe(id, descripcion, area_id) values (17, '4.7.- Conclusiones y propuestas.', 4);

insert into areas_informe(id, descripcion, modelo_informe_id) values (5, '5.- DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', 1);
insert into subareas_informe(id, descripcion, area_id) values (18, '5.1.- Plantilla y distribución.', 5);
insert into subareas_informe(id, descripcion, area_id) values (19, '5.2.- Resumen general de la carga de trabajo de la Unidad.', 5);
insert into subareas_informe(id, descripcion, area_id) values (20, '5.3.- Horarios de servicio.', 5);
insert into subareas_informe(id, descripcion, area_id) values (21, '5.4.- Servicio de Sanidad. Gabinete de Psicología.', 5);
insert into subareas_informe(id, descripcion, area_id) values (22, '5.5.- Absentismo.', 5);
insert into subareas_informe(id, descripcion, area_id) values (23, '5.6.- Enseñanza.', 5);
insert into subareas_informe(id, descripcion, area_id) values (24, '5.7.- Conclusiones y propuestas.', 5);

insert into areas_informe(id, descripcion, modelo_informe_id) values (6, '6.- DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', 1);
insert into subareas_informe(id, descripcion, area_id) values (25, '6.1.- Materia Móvil.', 6);
insert into subareas_informe(id, descripcion, area_id) values (26, '6.2.- Armamento, equipamento policial y vestuario.', 6);
insert into subareas_informe(id, descripcion, area_id) values (27, '6.3.- Informática y telecomunicaciones.', 6);
insert into subareas_informe(id, descripcion, area_id) values (28, '6.4.- Conclusiones y propuestas.', 6);

insert into areas_informe(id, descripcion, modelo_informe_id) values (7, '7.- ÁREAS ADMINISTRATIVAS Y DE APOYO', 1);
insert into subareas_informe(id, descripcion, area_id) values (29, '7.1.- Plana Mayor.', 7);
insert into subareas_informe(id, descripcion, area_id) values (30, '7.2.- Gestión económica.', 7);
insert into subareas_informe(id, descripcion, area_id) values (31, '7.3.- Registro y archivo.', 7);
insert into subareas_informe(id, descripcion, area_id) values (32, '7.4.- Régimen Disciplinario y Asistencia Letrada.', 7);
insert into subareas_informe(id, descripcion, area_id) values (33, '7.5.- Oficina Periférica de Comuniación.', 7);
insert into subareas_informe(id, descripcion, area_id) values (34, '7.6.- Núcleo de Destinos.', 7);
insert into subareas_informe(id, descripcion, area_id) values (35, '7.7.- Acción social.', 7);
insert into subareas_informe(id, descripcion, area_id) values (36, '7.8.- Quejas y sugerencias.', 7);
insert into subareas_informe(id, descripcion, area_id) values (37, '7.9.- Conclusiones y propuestas.', 7);

insert into areas_informe(id, descripcion, modelo_informe_id) values (8, '8.- ÁREA OPERATIVA', 1);
insert into subareas_informe(id, descripcion, area_id) values (38, '8.1.- Seguridad Ciudadana.', 8);
insert into subareas_informe(id, descripcion, area_id) values (39, '8.2.- Protección y Seguridad.', 8);
insert into subareas_informe(id, descripcion, area_id) values (40, '8.3.- Policía Judicial.', 8);
insert into subareas_informe(id, descripcion, area_id) values (41, '8.4.- Información.', 8);
insert into subareas_informe(id, descripcion, area_id) values (42, '8.5.- Tráfico.', 8);
insert into subareas_informe(id, descripcion, area_id) values (43, '8.6.- Servicio de Protección de la Naturaleza (SEPRONA).', 8);
insert into subareas_informe(id, descripcion, area_id) values (44, '8.7.- Unidades Fiscales.', 8);
insert into subareas_informe(id, descripcion, area_id) values (45, '8.8.- Intervención de Armas y Explosivos.', 8);
insert into subareas_informe(id, descripcion, area_id) values (46, '8.9.- Grupo de Desactivación de Explosivos y NRBQ.', 8);
insert into subareas_informe(id, descripcion, area_id) values (47, '8.10.- Servicio Marítimo.', 8);
insert into subareas_informe(id, descripcion, area_id) values (48, '8.11.- Grupo de Actividades Subacuáticas (GEAS).', 8);
insert into subareas_informe(id, descripcion, area_id) values (49, '8.12.- Grupo de Rescate e Intervención de Montaña (GREIM).', 8);
insert into subareas_informe(id, descripcion, area_id) values (50, '8.13.- Servicio Cinológico y de Remonta.', 8);
insert into subareas_informe(id, descripcion, area_id) values (51, '8.14.- Servicio Aéreo.', 8);
insert into subareas_informe(id, descripcion, area_id) values (52, '8.15.- Conclusiones y propuestas.', 8);

insert into areas_informe(id, descripcion, modelo_informe_id) values (9, '9.- LIBROS OFICIALES', 1);
insert into subareas_informe(id, descripcion, area_id) values (53, '9.1.- Verificación de la cumplimentación de los libros oficiales.', 9);
insert into subareas_informe(id, descripcion, area_id) values (54, '9.2.- Conclusiones y propuestas.', 9);

insert into areas_informe(id, descripcion, modelo_informe_id) values (10, '10.- PROGRAMA ESTADÍSTICO DE SEGURIDAD', 1);
insert into subareas_informe(id, descripcion, area_id) values (55, '10.1.- Control de grabación.', 10);
insert into subareas_informe(id, descripcion, area_id) values (56, '10.2.- Evolución delictiva.', 10);
insert into subareas_informe(id, descripcion, area_id) values (57, '10.3.- Conclusiones y propuestas.', 10);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de Modelo Informe Inspecciones Generales (Cuerpo Nacional de Policía)...
prompt =========================================================================

insert into modelos_informe(id, nombre) values (2, 'Modelo Informe Inspecciones Generales (Cuerpo Nacional de Policía)');

insert into areas_informe(id, descripcion, modelo_informe_id) values (11, '1.- INTRODUCCIÓN', 2);
insert into subareas_informe(id, descripcion, area_id) values (58, '1.1.- Introducción.', 11);

insert into areas_informe(id, descripcion, modelo_informe_id) values (12, '2.- ÁMBITO Y OBJETIVOS DE LA INSPECCIÓN', 2);
insert into subareas_informe(id, descripcion, area_id) values (59, '2.1.- Unidad inspeccionada.', 12);
insert into subareas_informe(id, descripcion, area_id) values (60, '2.2.- Ámbito territorial, población y servicios institucionales.', 12);
insert into subareas_informe(id, descripcion, area_id) values (61, '2.3.- Objetivos generales y específicos.', 12);
insert into subareas_informe(id, descripcion, area_id) values (62, '2.4.- Problemática de interés policial.', 12);
insert into subareas_informe(id, descripcion, area_id) values (63, '2.5.- Conclusiones y propuestas.', 12);

insert into areas_informe(id, descripcion, modelo_informe_id) values (13, '3.- REUNIONES Y VISITAS INSTITUCIONALES', 2);
insert into subareas_informe(id, descripcion, area_id) values (64, '3.1.- Con Autoridades.', 13);
insert into subareas_informe(id, descripcion, area_id) values (65, '3.2.- Con Asociaciones Civiles.', 13);
insert into subareas_informe(id, descripcion, area_id) values (66, '3.3.- Con Representantes Sindicales.', 13);
insert into subareas_informe(id, descripcion, area_id) values (67, '3.4.- Conclusiones y propuestas.', 13);

insert into areas_informe(id, descripcion, modelo_informe_id) values (14, '4.- INFRAESTRUCTURAS E INSTALACIONES', 2);
insert into subareas_informe(id, descripcion, area_id) values (68, '4.1.- Situación y estado de los inmuebles.', 14);
insert into subareas_informe(id, descripcion, area_id) values (69, '4.2.- Instalaciones y equipos.', 14);
insert into subareas_informe(id, descripcion, area_id) values (70, '4.3.- Medidas de seguridad y protección de los edificios policiales.', 14);
insert into subareas_informe(id, descripcion, area_id) values (71, '4.4.- Depósito de detenidos.', 14);
insert into subareas_informe(id, descripcion, area_id) values (72, '4.5.- Galería de tiro.', 14);
insert into subareas_informe(id, descripcion, area_id) values (73, '4.6.- Otros aspectos relevantes.', 14);
insert into subareas_informe(id, descripcion, area_id) values (74, '4.7.- Conclusiones y propuestas.', 14);

insert into areas_informe(id, descripcion, modelo_informe_id) values (15, '5.- DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS', 2);
insert into subareas_informe(id, descripcion, area_id) values (75, '5.1.- Plantilla y distribución.', 15);
insert into subareas_informe(id, descripcion, area_id) values (76, '5.2.- Resumen general de la carga de trabajo.', 15);
insert into subareas_informe(id, descripcion, area_id) values (77, '5.3.- Horarios de servicio.', 15);
insert into subareas_informe(id, descripcion, area_id) values (78, '5.4.- Unidad de Sanidad.', 15);
insert into subareas_informe(id, descripcion, area_id) values (79, '5.5.- Absentismo.', 15);
insert into subareas_informe(id, descripcion, area_id) values (80, '5.6.- Formación.', 15);
insert into subareas_informe(id, descripcion, area_id) values (81, '5.7.- Conclusiones y propuestas.', 15);

insert into areas_informe(id, descripcion, modelo_informe_id) values (16, '6.- DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', 2);
insert into subareas_informe(id, descripcion, area_id) values (82, '6.1.- Automoción.', 16);
insert into subareas_informe(id, descripcion, area_id) values (83, '6.2.- Armamento, equipamento policial y vestuario.', 16);
insert into subareas_informe(id, descripcion, area_id) values (84, '6.3.- Informática y telecomunicaciones.', 16);
insert into subareas_informe(id, descripcion, area_id) values (85, '6.4.- Conclusiones y propuestas.', 16);

insert into areas_informe(id, descripcion, modelo_informe_id) values (17, '7.- ÁREAS ADMINISTRATIVAS Y DE APOYO', 2);
insert into subareas_informe(id, descripcion, area_id) values (86, '7.1.- Secretaría General.', 17);
insert into subareas_informe(id, descripcion, area_id) values (87, '7.2.- Gestión económica-Habilitación.', 17);
insert into subareas_informe(id, descripcion, area_id) values (88, '7.3.- Registro y archivo. Gestión y tramitación de la documentación.', 17);
insert into subareas_informe(id, descripcion, area_id) values (89, '7.4.- Régimen Disciplinario y Asistencia Letrada.', 17);
insert into subareas_informe(id, descripcion, area_id) values (90, '7.5.- Gabinete de Prensa.', 17);
insert into subareas_informe(id, descripcion, area_id) values (91, '7.6.- Documentación (DNI-pasaportes-extranjeros).', 17);
insert into subareas_informe(id, descripcion, area_id) values (92, '7.7.- Quejas y sugerencias.', 17);
insert into subareas_informe(id, descripcion, area_id) values (93, '7.8.- Conclusiones y propuestas.', 17);

insert into areas_informe(id, descripcion, modelo_informe_id) values (18, '8.- ÁREA OPERATIVA', 2);
insert into subareas_informe(id, descripcion, area_id) values (94, '8.1.- Seguridad Ciudadana.', 18);
insert into subareas_informe(id, descripcion, area_id) values (95, '8.2.- Extranjería y Fronteras.', 18);
insert into subareas_informe(id, descripcion, area_id) values (96, '8.3.- Policía Judicial.', 18);
insert into subareas_informe(id, descripcion, area_id) values (97, '8.4.- Policía Científica.', 18);
insert into subareas_informe(id, descripcion, area_id) values (98, '8.5.- Información.', 18);
insert into subareas_informe(id, descripcion, area_id) values (99, '8.6.- Medios Aéreos.', 18);
insert into subareas_informe(id, descripcion, area_id) values (100, '8.7.- Conclusiones y propuestas.', 18);

insert into areas_informe(id, descripcion, modelo_informe_id) values (19, '9.- LIBROS OFICIALES', 2);
insert into subareas_informe(id, descripcion, area_id) values (101, '9.1.- Verificación de la cumplimentación de los libros oficiales.', 19);
insert into subareas_informe(id, descripcion, area_id) values (102, '9.2.- Conclusiones y propuestas.', 19);

insert into areas_informe(id, descripcion, modelo_informe_id) values (20, '10.- PROGRAMA ESTADÍSTICO DE SEGURIDAD', 2);
insert into subareas_informe(id, descripcion, area_id) values (103, '10.1.- Control de grabación.', 20);
insert into subareas_informe(id, descripcion, area_id) values (104, '10.2.- Evolución delictiva.', 20);
insert into subareas_informe(id, descripcion, area_id) values (105, '10.3.- Conclusiones y propuestas.', 20);

COMMIT;