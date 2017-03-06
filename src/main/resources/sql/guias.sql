DROP SEQUENCE "SEQ_GUIAS";

--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. ADMINISTRACIÓN.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(6, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. ADMINISTRACIÓN.', 'I.T_CIE', 6,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Estructura. Examinar vacantes, comisiones de servicio, absentismo.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Comprobar nombramiento Subgrupo A1 o A2', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Catálogo de puestos de trabajo. Comprobación documental.',2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de quejas ante: Comprobar que se han entregado al interno copia sellada de la primera página. Comprobar en el registro, fecha y hora de la presentación, identificación de interesado y destinatario. Comprobar que se han aportado al interesado copias selladas de los documentos presentados, si lo han solicitado. Examinar el libro registro. Examinar copias de resoluciones (Plazos, recursos y órganos).<br/>
Órganos administrativos<br/>
Órganos judiciales<br/>
El Ministerio Fiscal<br/>
El Defensor del Pueblo<br/>	
El Director del Centro<br/>		
Otros organismos)', 3);
		
insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de solicitud de entrevistas personales con el director. Comprobar las que se han llevado a cabo y las que no y causas. Examinar recibos de presentación en sobres cerrado. (Ver Dirección)',4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de propuestas de alteración de horarios. Comprobación documental. Resolución.',5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de informes sobre deficiencias. Comprobación documental. Resoluciones adoptadas.',6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de instrucciones recibidas del servicio sanitario respecto a la alimentación, limpieza y aseo. Comprobación documental. Resolución adoptada.', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Medidas adoptadas en base a las creencias religiosas de los internos Comprobar', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Medios destinados a facilitar la práctica de diferentes confesiones religiosas. Comprobación documental.', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Solicitudes de internos para entrar en contactos con ONG,s. Comprobación documental, cuáles y causas.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Horario de visita a internos, lugar donde se llevan a cabo. Duración de las mismas. Limitaciones. Examen de las normas de régimen interior.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Como se aborda el problema de idioma de internos. Comprobación', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de ejemplares de libros que contiene la biblioteca. Indicar idioma de los mismos. Comprobación.', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Descripción de medios lúdicos instalados en la sala de estar. Comprobación', 14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de ejemplares de prensa diaria, con indicación de nombre e idioma de edición. Comprobación.',15);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Horarios del centro, con especificación de actividades. Comprobación documental.', 16);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Número de autorizaciones de dispositivos captadores de imágenes. Comprobar', 17);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 6, 'Identificación de todas personas que prestan servicios en el centro. Examinar . art. 49', 18);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS.SERVICIO DE ASISTENCIA SANITARIA.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(8, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS.SERVICIODE ASISTENCIA SANITARIA.', 'I.T_CIE', 8,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Médico de la administración responsable del Servicio de Asistencia Sanitaria. Examinar nombramiento.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de ATS/Diplomado/Graduado Universitario. Examinar nombramiento.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de inspecciones, informes1 y propuestas sobre: Examinar.<br/>
Alimentación.<br/>
Aseo de los internos, sus ropas y pertenencias<br/>
Higiene, calefacción, iluminación, y ventilación de las dependencias<br/>
Controles periódicos de salubridad<br/>
Prevención de epidemias y medidas de aislamiento de pacientes infecto-contagiosos.',2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Entrega de elementos de aseo y abrigo. Comprobar art. 32', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de actos médicos. Comprobación documental.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de actos de enfermería. Comprobación documental.', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Convenios con otra Admón. pública para casos de hospitalización o especialidades. Examinar convenios.', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Convenios con entidades privadas para casos de hospitalización o especialidades. Examinar convenios.', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de reconocimientos médicos mediante mandamiento judicial, por negación del interno. Recabar copia del mandamiento.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de reconocimientos ordenados por el Director. Recabar copia de la resolución. Copia de la solicitud del Servicio y de la cuenta al juez competente.', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de internos de ese Cie, hospitalizados. Comprobar documentalmente. Comunicación al juez.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de partes de lesiones, por las producidas anteriores al ingreso, y que estuvieran descritas en el parte facultativo de lesiones que entregan los funcionarios que hacen entrega del interno. Comprobar.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de partes de lesiones, por las producidas anteriores al ingreso, y que no estuvieran descritas en el parte facultativo de lesiones que entregan los funcionarios que hacen entrega del interno. Comprobar cuenta al director y la remisión al juez del partido judicial donde se encuentra el centro.', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Número de reuniones entre la Comisaria General de Extranjería y Fronteras y representante de las entidades con las que se haya suscritos convenios de sanidad. Comprobar.', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Atención farmacéutica. Comprobar en que consiste y, cómo y dónde se almacenan los productos', 14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Procedimiento para la solicitud de asistencia médica. Examinar',15);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 8, 'Tratamiento de los datos de salud. En, su caso, Entidades concertadas Comprobar art. 17', 16);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. SERVICIOS DE ASISTENCIA SOCIAL, JURÍDICA Y CULTURAL.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(9, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. SERVICIOS DE ASISTENCIA SOCIAL, JURÍDICA Y CULTURAL.', 'I.T_CIE', 9,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Estructura. Comprobar documentalmente.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Número de trabajadores sociales. Comprobar.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Planes y proyectos de actuación presentados. Examinar.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Planes y proyectos de actuación aprobados. Examinar.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Convenios con órganos de otros Ministerios, con entidades públicas y privadas y con ONG,s. Examinar los convenios.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Entidades colaboradoras. Detallando la actividad que realiza cada una. Comprobar documentalmente.', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Actividades llevadas a cabo. Examinar', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Formación en derechos humanos, extranjería, protección internacional, mediación intercultural y violencia de género. Numero de curso, jornadas, etc. que han recibido los trabajadores sociales. Comprobar documentalmente, mediante actas, títulos, etc.', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Convenios con colegios de abogados. Examinar los convenios.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Dependencias destinadas a asegurar la confidencialidad en la orientación jurídica. Visitar las dependencias. Reportaje fotográfico.', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Número de entrevistas y comunicaciones con abogados y representantes diplomáticos y consulares. Comprobar documentalmente.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Horarios de visitas. Examinar las normas de régimen interior.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Limitaciones establecidas para las visitas. Examinar las normas de régimen interior.', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Número de personas por visita. Comprobar documentalmente.',13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Donde tienen lugar. Comprobar documentalmente.', 14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Como se informa a los visitantes de las normas de régimen interior. Comprobar documentalmente.', 15);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Número de personas que pueden simultanear la visita. Examinar las normas de régimen interior.', 16);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Comunicaciones telefónicas que se realizan al ingreso Comprobar art. 16', 17);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Como se informa de su situación al interno y de las resoluciones administrativas y judiciales. Examinar', 18);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Números de casos de asistencia de intérprete Examinar', 19);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 9, 'Plazo de entrevista con servicio de asistencia social interno nuevo Comprobar art.30', 20);

--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS.DIRECCIÓN.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(3, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS.DIRECCIÓN.', 'I.T_CIE', 4,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Comprobar nombramiento Dentro del subgrupo A1', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Periodos de sustitución y causas. Comprobar', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de respuestas a escritos, quejas y peticiones de los internos o remitidas a la autoridad competentes. Examinar, comprobando el contenido de las mismas.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de reservas de plazas aceptadas o denegadas. Causas de denegación.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Visitas fuera de horarios, autorizadas y causas. Comprobar', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de Iniciativas trasladadas a superiores. Detallar de que se tratan diferenciando las propias de las realizadas a propuestas de administración.', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Normas de régimen interior/modificaciones. Comprobar que el horario del centro hace referencia a actos de aseo, visitas médicas, comidas, visitas externas, comunicaciones telefónicas, paseos al aire libre, ocio y descansos.', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de entrevistas personales con el Director. Comprobar y cotejar con las solicitudes (Ver administración)', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Medidas a imponer a los internos que incumplan las normas. Comprobar que están perfectamente definidas.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Numero de reconocimientos médicos ordenados por la dirección por causa de salud colectiva. Examinar las copias de la comunicación al juez.', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Cómo se efectúa el control sobre la custodia y cumplimentación de los libros. Examinar.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de reuniones de seguimiento de la gestión entre Comisaria General de extranjería y Fronteras, con directores e instituciones. Remitir actas del año. Examinar actas', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de visitas del Juez competente, especificando los motivos. Comprobar documentalmente', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Número de inspecciones, con inclusión de fechas, distinguiendo las llevadas a cabo por la autoridad judicial, el CNP, u otros organismos. Comprobar documentalmente', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 3, 'Datos anuales publicados por la Comisaría General de Extranjería y Fronteras. Comprobar documentalmente', 14);

--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. INFRAESTRUCTURAS, INSTALACIONES Y MEDIOS BÁSICOS.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(0, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. INFRAESTRUCTURAS, INSTALACIONES Y MEDIOS BÁSICOS.', 'I.T_CIE', 1,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Ubicación y estado del centro. Titularidad, ¿Se comparte con otras dependencias policiales? año de construcción, años de las últimas obras de rehabilitación y contenido. Comprobar estado y deficiencias que pongan en peligro a las personas.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Fecha de creación del CIE. Examinar la Orden Ministerial de creación.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Instalaciones. Descripción de las mismas. Es aconsejable realizar un reportaje fotográfico. Examinar el estado de limpieza, luminosidad, climatización, estado del mobiliario, y en general, estado de conservación y habitabilidad.  Comprobar que no existe hacinamiento.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Disponibilidad de accesos para personas con movilidad reducida. Comprobar', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Capacidad, por sexos. Comprobar documentalmente.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Número de módulos destinados al alojamiento de hombres. Describir y acompañar de reportaje fotográfico.',5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Número de módulos destinados al alojamiento de mujeres. Describir y acompañar de reportaje fotográfico.', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Número de alojamientos para unidades familiares. Describir y acompañar de reportaje fotográfico.', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Servicios complementarios (Bibliotecas, salas multiconfesionales, etc.). Dimensiones. Describir y acompañar de reportaje fotográfico.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Número de habitaciones para llevar a cabo la separación preventiva. Describir y acompañar de reportaje fotográfico.',9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Numero de dependencias y descripción de las mismas, destinadas a alojar  a internos, que aun no necesitando atención hospitalaria, por las características de enfermedad física o psíquica aconseje su separación del resto de los internos. Describir. Comprobar estado de uso y de limpieza.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Infraestructuras utilizadas para alojar internados en virtud del art.89.6 del Código Penal. Describir. Comprobar estado de uso y de limpieza.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Como se consigue la intimidad necesaria para los internos. Comprobar.', 27);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Espacios para el esparcimiento y recreo. Descripción. Comprobar si son suficientes y adecuadas, teniendo en cuenta el número de internos, y si hay separación entre hombres y mujeres.', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Espacio para almacenamiento de equipajes. Caja fuerte. Capacidad. Comprobar cómo están almacenados y la seguridad de las pertenencias.', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Almacén de equipos básicos de higiene diaria. Comprobar cómo están almacenados y estado de limpieza.', 14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Número de teléfonos públicos para uso de los internos. Comprobar su estado de funcionamiento e higiene.', 15);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Locutorios para abogados y sala de visita. Comprobar adecuación y limpieza.',16);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Carteles Informativos. Horarios. Comprobar que en lugar bien visible se encuentra anunciado el horario de visitas. Zona Videovigilada. Comprobar', 17);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Limpieza: Empresa, número de personal, horario, horario en los servicios de 25 horas, grado de satisfacción, incidencias... Comprobar documentalmente.', 18);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Ascensores y aparatos elevadores: 
Ascensores. Número de ellos y ubicación. Inspección.<br/>
Montacargas. Número de ellos y peso máximo. Inspección.', 19);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Instalaciones eléctrica (AT y BT) (RD 615/2001, disposiciones mínimas de protección contra riesgos eléctricos). Comprobar revisiones e inspecciones.<br/>
Alta Tensión. Centros de transformación, centrales eléctricas o subestaciones.<br/>
Baja Tensión. Cuadros eléctricos.<br/>
Grupos electrógenos<br/>
Sistema de Alimentación Ininterrumpida (S.A.I.)/Baterías de acumuladores.', 20);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Sistema de ventilación, climatización y ACS. Comprobar revisiones e inspecciones.<br/>
Calderas de calefacción y ACS.<br/>
Aire acondicionado y/o ventilación forzada.<br/>
Aparatos a presión (compresores).<br/>
Depósitos de combustibles. Comprobar revisiones e inspecciones.<br/>
Combustibles líquidos.<br/>
Combustibles gaseosos.<br/>', 21);
	
insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 
'Prevención/control Legionelosis.<br/>
Mantenimiento de sistemas de ACS y agua fía de consumo humano.<br/>
Aspecto a revisar: General. Plazo de revisión: Anual.<br/>
Aspecto a revisar: Conservación y limpieza. Plazo de revisión: Trimestral.<br/>
Aspecto a revisar: Puntos de terminales de la red, duchas y grifos. Plazo de revisión: Mensual.<br/>
Mantenimiento de torres de refrigeración y dispositivos análogos.<br/>
Aspecto a revisar: Condensador y separador de gotas. Plazo de revisión: Anual.<br/>
Aspecto a revisar: Relleno. Plazo de revisión: Semestral.<br/>
Aspecto a revisar: Bandeja. Plazo de revisión: Mensual', 22);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Libro del edificio. Comprobación documental.', 23);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Libros de mantenimiento de cada una de las instalaciones, con sus informes, actas, etc. correspondientes a las operaciones de mantenimiento, revisiones e inspecciones. Comprobación documental.', 24);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Protección contra incendios. Empresas de mantenimientos y revisiones.<br/>
Central de alarmas: número, ubicación.<br/>
Sistemas automáticos de detección y alarma de incendios: número, ubicación.<br/>
Sistemas manuales (pulsadores) de alarma de incendio: número, ubicación.<br/>
Sirenas de alarma y megafonía: número, ubicación.<br/>
Señalización de equipos contra incendios (de BIE,s, extintores, dirección evacuación, etc.). Comprobar su colocación.<br/>
Extintores portátiles: Están redistribuidos y/o colocados los extintores portátiles en el sentido establecido en el RD  485/1977,  de 14 de abril, por el que se establecen las disposiciones mínimas en materia de señalización de seguridad y salud en el trabajo, y en el RD  314/2006, que aprueba el Documento Básico de Seguridad en caso de Incendio (DB-SI), del Código Técnico de Edificación (CTE)? ¿Existe un plano de distribución/colocación de los extintores portátiles? Solicitar.<br/>
Bocas de Incendio Equipadas (BIE,s): número, ubicación.<br/>
Sistema fijo de extinción. Agua. Gás. Espuma. Número, ubicación.<br/>
Sistema de abastecimiento de agua: número, ubicación.<br/>
Hidrantes exteriores: número, ubicación.<br/>
Columnas secas: número, ubicación.<br/>
Alumbrado de emergencias: número, ubicación.<br/>
Otros.'
, 25);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Plan de Emergencia/Plan de Autoprotección Comprobar viabilidad, evacuación, señalización, simulacros (actas), nombramientos, formación, información, etc', 26);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Empresa de mantenimiento: Contrato, número de operarios, comprobar si alguno de ellos se ocupa en exclusividad al CIE', 27);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Empresas encargadas de las inspecciones', 28);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Pedir la documentación de la ITE', 29);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 0, 'Residuos tóxicos. Comprobar cuáles son, donde se ubican, que empresa es la encargada de su recogida y gestión', 30);

--GUIA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. JUNTA DE COORDINACIÓN.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(4, 'GUIA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. JUNTA DE COORDINACIÓN.', 'I.T_CIE', 4,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 4, 'Composición de la junta de coordinación. Examinar', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 4, 'Normas de funcionamiento Examinar', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 4, 'Remitir actas de reuniones ordinarias o extraordinarias. Examinar.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 4, 'Informes a consultas sobre: Examinar los informes.<br/>
Normas de régimen interior.<br/>
Directrices e instrucciones sobre organización de los distintos servicios.<br/>
Criterios de actuación en cuestiones de alteración del orden, o incumplimiento de normas.<br/>
Sobre peticiones y quejas.', 3);

--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. LIBROS OFICIALES.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(12, 'GUIA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. JUNTA DE COORDINACIÓN.', 'I.T_CIE', 12,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 12, 'Libros en poder del Centro y fecha de diligencia de cada uno de ellos1. Comprobar si están informatizados.<br/>
Libro Registro de entradas y salidas de internos. Comprobar los diferentes asientos, y si se han cumplimentado las fichas individuales de comunicación. Cotejar las salidas con las copias de resoluciones y diligencias de salida del centro (copias firmadas por el interno). Comprobar los reingresos y causas.<br/>
Libro Registro de traslados y desplazamientos. Examinar los asientos, cotejándolos con comunicaciones al juez competente y la constancia en los expedientes de los internos.<br/>
Libro Registro de visitas. Comprobar que se anotan las visitas de miembros de organizaciones para defensa de los inmigrantes y del resto de personas.<br/>
Libro Registro de correspondencia. Comprobar que está anotada la paquetería. (Observar si anotan correspondencia). Comprobar las anotaciones de paquetería devuelta por denegación de su apertura.<br/>
Libro Registro de peticiones y quejas.- Comprobar que en las anotaciones figuran fecha, hora identificación del interno y del destinatario. Comprobar que la numeración de los impresos es consecutiva2. Comprobar si se archivan copias de los recibos de solicitud de entrevistas personales con el director.<br/>
Comprobar tratamiento de los datos de carácter personal y de salud art.17.<br/>
Ver plazos estancia art.21.', 0);

--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS.

insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(2, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS.', 'I.T_CIE', 2,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Estructura orgánica.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Catálogo de puestos de trabajo personal CNP. Comprobar número de vacantes, comisiones de servicio y absentismo. Averiguar cómo se hacen las sustituciones en épocas vacacionales. Tiempo promedio en el destino.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Catálogo de puestos de trabajo funcionarios Administración General del Estado. Comprobar número de vacantes, comisiones de servicio y absentismo. Averiguar cómo se hacen las sustituciones en épocas vacacionales. Tiempo promedio en el destino.',2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Catálogo de puestos de trabajo personal laboral. Comprobar número de vacantes, comisiones de servicio y absentismo. Averiguar cómo se hacen las sustituciones en épocas vacacionales. Tiempo promedio en el destino.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Otro personal. Comprobar tipo de contratación y requisitos exigidos número de vacantes, comisiones de servicio y absentismo. Averiguar cómo se hacen las sustituciones en épocas vacacionales.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Horarios de trabajo. Acreditar documentalmente el control de cumplimiento del horario.', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Número de actividades formativas dirigidas al personal del CNP y resto de funcionarios y empleados públicos, diferenciando las referidas a derechos humanos, régimen de extranjería, seguridad y enfoque de género y violencia contra las mujeres, con expresión de número de participantes en cada una de ellas, así como personal o institución que las impartió. Comprobar documentalmente a través de actas de los cursos, títulos, etc.', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 2, 'Número de actividades formativas dirigidas al personal no incluido en el párrafo anterior, con expresión de número de participantes en cada una de ellas, así como personal o institución que las impartió. Comprobar documentalmente a través de actas de los cursos, títulos, etc.', 7);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(1, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES.', 'I.T_CIE', 1,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 1, 'Inventario de recursos materiales, con expresión de fecha de adjudicación o reposición y estado de conservación. Comprobar la existencia de inventarios y examinarlos.', 0);

--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. RELACIONES Y COORDINACIÓN.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(13, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. RELACIONES Y COORDINACIÓN.', 'I.T_CIE', 13, trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 13, 'Entidades colaboradoras. Examinar', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 13, 'Numero de organizaciones que están autorizadas para realizar visitas al centro. Comprobar documentalmente. Resoluciones del director, comprobar que se cumplen las 72 h de plazo para concederlas o para subsanación.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 13, 'Acreditaciones personales concedidas. Comprobar la relación de las mismas.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 13, 'Número de visitas de ONG,s. Comprobar documentalmente. En el libro registro debe quedar constancia. Cuáles y causas.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 13, 'Número de comunicaciones a ONG,s, sobre solicitud de visitas de internos. Comprobar documentalmente.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 13, 'Numero de exámenes a que han estado sometidos sus integrantes, causas y resultados. Comprobar documentalmente.', 5);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. SECRETARÍA.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(7, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. SECRETARÍA.', 'I.T_CIE', 7,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Estructura. Comprobar.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Nombramiento del Secretario Comprobar que el jefe es del subgrupo A2 o C1 del CNP.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Catálogo de puestos de trabajo.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de deficiencias o irregularidades apreciadas.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de trámites documentales con las Unidades policiales que gestionan los expedientes de extranjeros. Examinar', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de trámites documentales con los juzgados competentes para el control de extranjeros internados. Examinar', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de ingresos, diferenciadas por sexos, meses y periodo de permanencia de los internos, especificando los motivos. Cotejar con los libros y art 21', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de salidas, diferenciadas por meses y periodo de permanencia de los internos, especificando los motivos. Cotejar con los libros y art 21', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de traslados: Examinar libro de traslados y desplazamientos.<br/>
A otros centros y de otros centros, especificando los motivos. Examinar acuerdo del juez o tribunal que autorizó el internamiento.<br/>
Por comparecencias. Comprobar las constancias de fecha y hora de salida y regreso en el expediente y comunicación al juez o tribunal.<br/>
Por cuestiones médicas. Comunicación inmediata al juez o tribunal. Comprobar los que se han realizado a instancia del director por ausencia del facultativo. Examinar copia de solicitud a la comisaria de la localidad las medidas tendentes a garantizar la seguridad del interno.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de ceses de internamiento, especificando sus causas. Comprobar documentalmente. Libro de entrada y salida de internos. Examinar copia de comunicación a la autoridad judicial que acordó el internamiento. Copia de la diligencia de entrega a los funcionarios encargados de su traslado a la frontera. Resolución por la que se acuerda el cese del internamiento o copia de la orden de expulsión, devolución o regreso.', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de situaciones que se han producido en las cuales no se pueda llevar a efecto la expulsión de un interno por el art. 89.6 del CP. Examinar copia de comunicación a la Brigada o Unidad de extranjería. Examinar diligencia de salida del interno y copia del auto o resolución judicial.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Número de reingresos por no poderse llevar a cabo la expulsión, devolución o regreso. Examinar informe policial detallando las circunstancias, el parte de lesiones, en su caso, y la cuenta a la autoridad judicial. Libro registro de entradas y salidas.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Tratamiento de los datos de carácter personal en los expedientes. Comprobar art. 17.', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Información sobre derechos y obligaciones del nuevo interno. Boletín Informativo. Copia boletín examinar art. 29.', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Expedientes de los internos. Comprobar que están completos.',14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 7, 'Comprobar si los libros están en Secretaria', 15);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. UNIDAD DE SEGURIDAD.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(5, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. UNIDAD DE SEGURIDAD.', 'I.T_CIE', 5,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Estructura orgánica. Examinar documentalmente.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Nombramiento Jefe de Unidad de Seguridad. Comprobar subgrupo A1', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Catálogo de puestos de trabajo. Examinar vacantes, comisiones de servicio, absentismo.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Criterio de selección. Examinar', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Horarios de trabajo. Comprobación documental.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Directrices de organización. Examinarlas',5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de atestados instruidos. Examinarlos.', 6);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Relación de objetos prohibidos intervenidos a internos y destino dados a los mismos. Examinar copia de documentos de remisión a la autoridad competente.', 7);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Relación de objetos no autorizados intervenidos a internos. Examinar documento de retirada y de entrega cuando el interno abandona el centro.', 8);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Custodia y depósito en caja fuerte de objetos de valor y dinero. Procedimiento. Comprobar actas art. 28.', 9);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Relación de objetos prohibidos intervenidos a visitantes. Examinar oficio de remisión a la autoridad competente.', 10);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Relación de objetos no autorizados intervenidos a visitantes. Examinar el acta de custodia.', 11);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Control de seguridad al que se somete la correspondencia recibida o remitida. Comprobar el procedimiento.', 12);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de registros de correspondencia autorizadas por la autoridad judicial. Comprobar autorizaciones judiciales.', 13);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Control de seguridad al que se somete la paquetería recibida. Comprobar el procedimiento.', 14);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de paquetería devuelta por falta de autorización a su apertura. Comprobar documentalmente.', 15);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de objetos intervenidos en paquetería y destinos dado a los mismos. Comprobar documentalmente.', 16);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Detalle de instrumentos de control instalados, con expresión de clase, y ubicación (cámaras, arcos detectores, etc.). Examinar y recabar documentación relativa a sus características y mantenimiento y revisiones. Comprobar que las cámaras no están dirigidas a dormitorios, baños y lugares considerados íntimos. Ver que esta el cartel de Zona Videovigilada.', 17);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Destino dado a las grabaciones y tiempo de permanencia. Comprobar documentalmente.', 18);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de incautaciones de medios de captación de imágenes. Examinar copia del informe al juez.', 19);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de borrados de imágenes. Examinar documentalmente.', 20);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Enumerar lugares donde se presta servicio sin armas de fuego. Comprobar documentación de propuesta y autorización.', 21);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de inspecciones y registros en las instalaciones de uso común. Comprobar documentalmente la resolución del director.', 22);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de ocasiones en que, por razón de urgencia se han adoptado medidas tendentes a reestablecer y asegurar el orden. Examinar las notificaciones al director.', 23);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de ocasiones en que se ha dado cuenta al director del incumplimiento de las normas de régimen interior', 24);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de inspecciones y registros en las instalaciones destinadas a dormitorios de los internos, ropas y enseres de los mismos, indicando las causas. Comprobar documentalmente la resolución del director.', 25);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de registros personales de los internos, indicando cuales lo fueron mediante desnudo integral, las causas que motivaron los mismos, diferenciando los autorizados por el Director de aquellos otros autorizados por el Jefe de la Unidad de seguridad. Indicar funcionarios encargados de practicarlos y lugar donde se llevaron a cabo. Examinar documento suscrito por el funcionario y comprobar que una copia fue remitida al juez.', 26);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de ocasiones en que ha sido necesario el empleo de medios de contención física personal, especificando en que han consistido y causa que las hayan motivado. Separar las que se han tomado previa notificación escrita y aquellas, que por razón de urgencia se haya notificado verbalmente. Examinar la resolución motivada o resolución cuando se ha informado verbalmente. Examinar la habitación de cumplimiento (debe ser similar a las comunes). Examinar el informe de reconocimiento médico preceptivo. Examinar la comunicación al juez y su resolución.', 27);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 5, 'Número de ocasiones en que ha sido necesaria la separación preventiva individual de algún interno, especificando su causa. Examinar la resolución motivada o resolución cuando se ha informado verbalmente. Examinar la habitación de cumplimiento (debe ser similar a las comunes). Examinar el informe de reconocimiento médico preceptivo. Examinar la comunicación al juez y su resolución.', 28);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. FORMACIÓN.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(10, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. FORMACIÓN.', 'I.T_CIE', 10,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Personal en plantilla a fecha de Inspección.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Por categorías tiempo Medio de permanencia en el CIE.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Relación numérica por categorías del personal que ha recibido “el curso sobre centro de Internamiento de Extranjeros” programado por la Centro de Actualización y Especialización, en colaboración  con la CGEF.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Relación numérica por categorías del personal que han recibido  cursos a que hace referencia el art. 48.1. del RD 162/ 2014: Derechos Humanos, Régimen de Extranjería, Seguridad y Prevención y Enfoque de Género y Violencia contra las Mujeres.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Relación numérica por categorías del personal que ha recibido cursos en otras áreas de interés profesional relacionadas con la organización y el funcionamiento del CIE: Defensa Personal y Técnicas de Inmovilización; Prevención de Riesgos Laborales, Emergencias y Primeros Auxilios, Operador de equipos de   rayos X, etc.', 4);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Necesidades y problemas en materia de Formación: Conciliación con la vida laboral. Aprovechamiento/ tiempo de permanencia en el CIE. Escasez o inadecuación de los recursos u ofertas  formativas.', 5);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 10, 'Propuestas de mejoras o alternativas.', 6);


--GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. GUIA MECANISMOS CONTROL/ INSPECCIÓN.
insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_creacion, usuario_creacion) values 
(11, 'GUÍA DEL CENTRO DE INTERNAMIENTO DE EXTRANJEROS. GUIA MECANISMOS CONTROL/ INSPECCIÓN.', 'I.T_CIE', 11,trunc(sysdate), 'system');

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 11, 'Personal en plantilla a fecha de Inspección.', 0);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 11, 'Relacionar las visitas o actuaciones de control llevadas a cabo por el Defensor del Pueblo,  otras autoridades u organismos, con inclusión de fechas y motivos.', 1);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 11, 'Relacionar las visitas o actuaciones de control llevadas a cabo por la Dirección General de la Policía u otros órganos del Mº Interior, con inclusión de fechas y motivos.', 2);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 11, 'Datos anuales referidos al CIE publicados por la Comisaría General de Extranjería y Fronteras.', 3);

insert into guia_Pasos (id, id_guia, paso, orden) values 
(seq_pasosGuia.nextval, 11, 'Informar de las actuaciones realizadas en el CIE, como  consecuencia de las recomendaciones o decisiones  adoptadas, en su caso, por algunas  de las Autoridades anteriores.', 4);

commit;

-- Generacion de secuencia teniendo en cuenta el úmero de registros existentes

CREATE SEQUENCE  "PROGESIN_SES"."SEQ_GUIAS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 14 CACHE 20 NOORDER  NOCYCLE  NOPARTITION ;


--declare
--  maximo number;
--begin
--  select max(id) into maximo from guia_Pasos;
--  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "PROGESIN_SES"."SEQ_GUIAS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

--  end;


