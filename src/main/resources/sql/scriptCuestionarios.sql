WHENEVER OSERROR EXIT FAILURE ROLLBACK
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT CARGA DE CUESTIONARIOS
prompt
prompt    Autor: EZENTIS
prompt
prompt    Fecha creación: 08/05/2017
prompt =========================================================================


prompt =========================================================================
prompt Ejecutando carga de tipos de respuesta...
prompt =========================================================================


Insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TEXTAREA', '1', '1');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('INPUT', '1', '1');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('ADJUNTOINPUT', '1', '1');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('ADJUNTORADIOSINO', '1', '1');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('RADIOSINO', 'SI', 'Sí');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('RADIOSINO', 'NO', 'No');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('RADIOCALIDAD', 'BUENO', 'Bueno');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('RADIOCALIDAD', 'ACEPTABLE', 'Aceptable');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('RADIOCALIDAD', 'DEFICIENTE', 'Deficiente');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLASUSTITUCIONES', 'campo01', 'DE Fecha');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLASUSTITUCIONES', 'campo02', 'HASTA Fecha');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLASUSTITUCIONES', 'campo03', 'MOTIVO');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLARESERVAPLAZAS', 'campo01', 'NÚMERO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLARESERVAPLAZAS', 'campo02', 'MOTIVO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLARESERVAPLAZAS', 'campo03', 'Aceptadas');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLARESERVAPLAZAS', 'campo04', 'Denegadas');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAVISITASFUERAHORARIO', 'campo01', 'NÚMERO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAVISITASFUERAHORARIO', 'campo02', 'MOTIVO');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAJUNTACOORDINACION', 'campo01', 'CARGO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAJUNTACOORDINACION', 'campo02', 'SI/NO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAJUNTACOORDINACION', 'campo03', 'OBSERVACIONES');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','MATRIZTIRO','Total CIE');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','MATRIZTIRO','1º trimestre');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','MATRIZTIRO','2º trimestre');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','MATRIZTIRO','3º trimestre');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo05','MATRIZTIRO','4º trimestre');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila1','MATRIZTIRO','Ejecutiva');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila2','MATRIZTIRO','Subinspección');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila3','MATRIZTIRO','Básica');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila4','MATRIZTIRO','T O T A L');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','MATRIZINFORMATICA','EXISTENCIAS');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','MATRIZINFORMATICA','NECESIDADES');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila1','MATRIZINFORMATICA','Servidores');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila2','MATRIZINFORMATICA','Impresoras de red');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila3','MATRIZINFORMATICA','Impresoras personales');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila4','MATRIZINFORMATICA','Ordenadores sobremesa');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila5','MATRIZINFORMATICA','Ordenadores portátiles');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila6','MATRIZINFORMATICA','Scanner');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila7','MATRIZINFORMATICA','Otros (especificar)');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','MATRIZTELECOMUNICACIONES','TOTAL');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','MATRIZTELECOMUNICACIONES','BUEN ESTADO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','MATRIZTELECOMUNICACIONES','ESTADO REGULAR');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','MATRIZTELECOMUNICACIONES','MAL ESTADO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila1','MATRIZTELECOMUNICACIONES','FAX');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila2','MATRIZTELECOMUNICACIONES','EMISORAS BASE');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila3','MATRIZTELECOMUNICACIONES','EMISORAS MÓVILES');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila4','MATRIZTELECOMUNICACIONES','PORTÁTILES');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila5','MATRIZTELECOMUNICACIONES','INHIBIDOR FREC. FIJO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila6','MATRIZTELECOMUNICACIONES','INHIBIDOR FREC. MOVIL');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila7','MATRIZTELECOMUNICACIONES','TELF. MÓVILES');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila8','MATRIZTELECOMUNICACIONES','OTROS (especif.)');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila9','MATRIZTELECOMUNICACIONES','TOTAL');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLAQUEJAS','ORGANISMOS');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLAQUEJAS','NÚMERO');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLAINGRESOS','MESES');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLAINGRESOS','NÚMERO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','TABLAINGRESOS','SEXO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','TABLAINGRESOS','TOTAL');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLAMOTIVOSALIDA','NÚMERO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLAMOTIVOSALIDA','MOTIVO');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLATRASLADOS','NÚMERO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLATRASLADOS','A OTROS CIES');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','TABLATRASLADOS','POR COMPARECENCIAS');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','TABLATRASLADOS','POR CUESTIONES MEDICAS');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','MATRIZCURSOS','ESCALA EJECUTIVA');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','MATRIZCURSOS','ESCALA SUBINSPECCION');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','MATRIZCURSOS','ESCALA BASICA');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','MATRIZCURSOS','PERSONAL FUNCIONARIO/LABORAL');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila1','MATRIZCURSOS','Derechos Humanos');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila2','MATRIZCURSOS','Extranjería');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila3','MATRIZCURSOS','Seguridad y Prevención');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila4','MATRIZCURSOS','Género y violencia contra las mujeres');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila5','MATRIZCURSOS','Equipos de rayos X, escáner y arcos detectores de metales');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila6','MATRIZCURSOS','Defensa personal y técnica de inmovilización');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila7','MATRIZCURSOS','Emergencia, evacuación y primeros auxilios');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila8','MATRIZCURSOS','Prevención de riesgos laborales');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('nombreFila9','MATRIZCURSOS','Otros específicos relacionados con la actividad en el CIE');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLALIBROS','LIBRO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLALIBROS','INFORMATIZADO SI/NO');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','TABLALIBROS','UBICACIÓN');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','TABLALIBROS','OBSERVACIONES');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLACOLABORADORES','Entidad colaboradora');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLACOLABORADORES','Actividad');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','TABLACOLABORADORES','Nº de acreditaciones');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAPERSONAL', 'campo01', 'PUESTO DE TRABAJO/ACTIVIDAD');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAPERSONAL', 'campo02', 'ESC/CAT/GRUPO');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAPERSONAL', 'campo03', 'DOTACIÓN');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo01', 'CAUSAS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo02', 'Inspector Jefe');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo03', 'Inspector');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo04', 'Sub');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo05', 'Oficial');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo06', 'Policía');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo07', 'Otros');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('TABLAAUSENCIAS', 'campo08', 'Total');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo01', 'INSPECTORES JEFES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo02', 'INSPECTORES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo03', 'SUBINSPECTORES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo04', 'OFICIALES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo05', 'POLICÍAS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo06', 'SUMAN');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo07', 'OTROS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'campo08', 'TOTAL');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'nombreFila1', 'Año anterior');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZRELACIONPERSONAL', 'nombreFila2', 'Año actual');

insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo01', 'E1');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo02', 'E2');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo03', 'SB');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo04', 'OF');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo05', 'PL');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo06', 'OTROS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'campo07', 'TOTAL');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'nombreFila1', 'Altas');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZMOVIMIENTOPERSONAL', 'nombreFila2', 'Bajas');


insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'campo01', 'Nº TOTAL ACCIDENTES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'campo02', 'Nº TOTAL ACCIDENTADOS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'nombreFila1', 'FALLECIDOS');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'nombreFila2', 'MUY GRAVES Y GRAVES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'nombreFila3', 'MÁS DE CUATRO FUNCIONARIOS CON LESIONES CARÁCTER LEVE (en el mismo accidente)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'nombreFila4', 'LEVES');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (seccion,clave,valor) values ('MATRIZACCIDENTES', 'nombreFila5', 'TOTAL');

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01','TABLAMEDIASPOLICIA','INSP. JEFE');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02','TABLAMEDIASPOLICIA','INSPECTOR');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03','TABLAMEDIASPOLICIA','SUBINSPECT');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04','TABLAMEDIASPOLICIA','OFICIAL');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo05','TABLAMEDIASPOLICIA','POLICÍA');

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de CUESTIONARIO CIES...
prompt =========================================================================

Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.nextval,'CIES','CUESTIONARIO CIES');

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'INFRAESTRUCTURAS, INSTALACIONES Y MEDIOS BÁSICOS', SEQ_AREASCUESTIONARIOS.NEXTVAL, 0);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Titularidad.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Años de construcción y rehabilitación, en su caso.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Fecha y contenido de las últimas obras.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Fecha de creación del Cie.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Instalaciones del Cie, descripción de las mismas indicando si existen otras unidades policiales que las compartan instalaciones con él.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Distribución del espacio destinado a Cie por plantas, con la expresión de las dependencias administrativas (oficinas) y m² de que disponen cada una, así como los aseos por plantas y vestuarios.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Distribución del espacio destinado a otras dependencias policiales por plantas, con la expresión de las dependencias y m² de que disponen cada una.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Disponibilidad de accesos para personas con movilidad reducida.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Capacidad, diferenciando por sexos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de módulos destinados al alojamiento de hombres.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de módulos destinados al alojamiento de mujeres.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de módulos destinados a alojamientos de unidades familiares.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de habitaciones para llevar a cabo la separación preventiva de internos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de dependencias y descripción de las mismas, destinadas a alojar a internos, que aun no necesitando atención hospitalaria, por las características de la enfermedad física o psíquica aconseje su separación del resto de los internos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de dependencias y descripción de las mismas, destinadas a alojar a internos, en virtud del art.89.8 del C.P.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción  de espacios destinados al esparcimiento y recreo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de espacios destinados al almacenamiento de equipajes.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 17);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de espacios destinados al almacenamiento de equipos básicos de higiene diaria.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 18);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Número de teléfonos públicos para uso de los internos, diferenciando aquellos habilitados para recibir llamadas de los que no lo están.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 19);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de los locutorios para abogados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 20);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de las salas destinadas a entrevistas con ONG.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 21);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de las salas destinadas a entrevistas con autoridades consulares.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 22);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de las salas de visita familiares y amigos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 23);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Servicios complementarios (Salas de ocio, salas multiconfesionales, etc.).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 24);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Disponibilidad de libro del edificio. En caso afirmativo, poner a disposición del equipo inspector en el momento de la visita.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 25);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Libros de mantenimiento de cada una de las instalaciones, con sus informes, actas, etc. correspondientes a las operaciones de mantenimiento, revisiones e inspecciones. En caso afirmativo, poner a disposición del equipo inspector en el momento de la visita.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 26);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Funcionalidad del edificio (conjunto de características que hacen que la edificación sea práctica y útil para el desempeño de la función a que se destina). Posibles valores: Bueno, Aceptable o Deficiente. En el supuesto de que se considere deficiente, argumentar el motivo y las gestiones llevadas a cabo para paliarlas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 27);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Estado de conservación. Posibles valores: Bueno, Aceptable o Deficiente. En el supuesto de que se considere deficiente, argumentar el motivo y las gestiones llevadas a cabo para paliarlas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 28);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Sistema de cierre de las habitaciones.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 29);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: ¿Existen baños en el interior de las habitaciones?. En caso afirmativo, indique su número y si están dotados de puertas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 30);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Descripción de las posibles deficiencias de que adolece el edificio, si las hubiera.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 31);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: ¿El edificio ha pasado la Inspección Técnica de Edificaciones?. En caso afirmativo adjuntar documento.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 32);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Mantenimiento: Indique la empresa encargada del mantenimiento.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 33);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Mantenimiento: Fecha de entrada en vigor del contrato de mantenimiento, y la prevista para su finalización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 34);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Mantenimiento: ¿Dispone de operario con presencia permanente en el edificio?.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 35);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Mantenimiento: Número de operarios de la empresa dedicados a esta tarea, indicando el horario de trabajo en la dependencia.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 36);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Mantenimiento: Calidad del servicio. Posibles valores: Bueno, Aceptable o Deficiente. En el supuesto de que se considere deficiente, argumentar el motivo y las gestiones llevadas a cabo para paliarlas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 37);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Mantenimiento: Pliego de prescripciones técnicas correspondiente al contrato de mantenimiento del inmueble y/o inmuebles. En caso afirmativo, poner a disposición del equipo inspector en el momento de la visita.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 38);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Limpieza: Nombre de la empresa.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 39);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Limpieza: Número de empleados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 40);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Limpieza: Horarios.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 41);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Limpieza: Grado de satisfacción (Bueno, Aceptable o Deficiente). En el supuesto de que se considere deficiente, argumentar el motivo y las gestiones llevadas a cabo para paliarlas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 42);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Limpieza: ¿Disponen de copia del contrato de limpieza y/o de las prescripciones técnicas del mismo?. En caso afirmativo, poner a disposición del equipo inspector en el momento de la visita.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 43);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Desinfección/Desinsectación/Desratización: Nombre de la empresa encargada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 44);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Desinfección/Desinsectación/Desratización: Periodicidad de las operaciones.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 45);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Desinfección/Desinsectación/Desratización: Fecha de la última operación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 46);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Marcado CE o declaración de conformidad (en caso negativo, año de fabricación).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 47);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Libro o instrucciones de uso y mantenimiento redactadas en castellano. (valores posibles: No, Sí) ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 48);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 49);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 50);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 51);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Empresa en cargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 52);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 53);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Empresa en cargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 54);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Centro de transformación energía eléctrica: Clase de refrigerante.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 55);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Instalación eléctrica de baja tensión: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 56);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Instalación eléctrica de baja tensión: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 57);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Instalación eléctrica de baja tensión: Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 58);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Instalación eléctrica de baja tensión: Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 59);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Instalación eléctrica de baja tensión: Empresa encargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 60);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Marcado CE o declaración de conformidad (en caso negativo, año de fabricación).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 61);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Libro o instrucciones de uso y mantenimiento redactadas en castellano.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 62);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 63);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 64);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 65);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 66);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 67);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Grupo electrógeno: Empresa encargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 68);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Marcado CE o declaración de conformidad (en caso negativo, año de fabricación)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 69);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Libro o instrucciones de uso y mantenimiento redactadas en castellano', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 70);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Número', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 71);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Localización', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 72);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Estado en que se encuentra', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 73);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Fecha última revisión', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 74);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Empresa encargada de la revisión', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 75);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Fecha de la última inspección', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 76);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Calderas de calefacción y agua caliente sanitaria: Empresa encargada de la inspección (OCA)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 77);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 78);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Libro o instrucciones de uso y mantenimiento redactadas en castellano.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 79);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Persona encargada de su mantenimiento habitual.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 80);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 81);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 82);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 83);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 84);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 85);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Ascensores: Empresa encargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 86);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 87);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Libro o instrucciones de uso y mantenimiento redactadas en castellano.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 88);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 89);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 90);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 91);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 92);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 93);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Montacargas: Empresa encargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 94);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Marcado CE o declaración de conformidad (en caso negativo, año de fabricación).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 95);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 96);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Libro o instrucciones de uso y mantenimiento redactadas en castellano.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 97);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 98);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 99);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 100);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 101);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Torres de refrigeración: Empresa encargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 102);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aire acondicionado y/o ventilación forzada (aparatos industriales): Marcado CE o declaración de conformidad (en caso negativo, año de fabricación).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 103);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aire acondicionado y/o ventilación forzada (aparatos industriales): Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 104);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aire acondicionado y/o ventilación forzada (aparatos industriales): Libro o instrucciones de uso y mantenimiento redactadas en castellano.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 105);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aire acondicionado y/o ventilación forzada (aparatos industriales): Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 106);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aire acondicionado y/o ventilación forzada (aparatos industriales): Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 107);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aire acondicionado y/o ventilación forzada (aparatos industriales): Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 108);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aparatos individuales de aire acondicionado: Marcado CE o declaración de conformidad (en caso negativo, año de fabricación).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 109);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aparatos individuales de aire acondicionado: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 110);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Libro o instrucciones de uso y mantenimiento redactadas en castellano.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 111);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aparatos individuales de aire acondicionado: Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 112);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aparatos individuales de aire acondicionado: Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 113);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Aparatos individuales de aire acondicionado: Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 114);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 115);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 116);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Capacidad', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 117);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Situación (Enterrado, semienterrado, al aire).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 118);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 119);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 120);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 121);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 122);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible líquido (gasóleo): Empresa encargada de la inspección (OCA)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 123);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 124);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 125);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Estado en que se encuentra.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 126);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Fecha última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 127);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Empresa encargada de la revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 128);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Fecha de la última inspección.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 129);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Depósitos combustible gaseoso (butano, propano): Empresa encargada de la inspección (OCA).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 130);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Baterías-acumuladores (SAI,s): Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 131);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Baterías-acumuladores (SAI,s): Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 132);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Baterías-acumuladores (SAI,s): Estado en que se encuentra', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 133);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Servidores informáticos: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 134);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Inhibidores de frecuencia: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 135);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Pararrayos: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 136);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones - Pararrayos: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 137);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones: Relación de productos químicos utilizados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 138);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones: Relación de productos químicos almacenados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 139);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Instalaciones: En el caso de que existan contratos de mantenimiento de las anteriores instalaciones y equipamientos, ya sean de tipo preventivo, correctivo y/o técnico legal, se remitirá copia del pliego de prescripciones técnicas o de las condiciones de prestación de dicho servicio.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 140);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Prevención control Legionella: Revisiones realizadas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 141);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Prevención control Legionella: Empresa encargada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 142);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Prevención control Legionella: Fecha de las últimas operaciones efectuadas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 143);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Prevención control Legionella: Fecha de la última inspección realizada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 144);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Prevención control Legionella: Empresa encargada de la inspección OCA.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 145);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 146);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles: Fecha de la última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 147);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de agua: Número de equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 148);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de agua: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 149);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de espuma: Número de equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 150);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de espuma: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 151);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de polvo ABC: Número de equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 152);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de polvo ABC: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 153);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de CO2: Número de equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 154);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de CO2: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 155);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de carro: Número de equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 156);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Extintores portátiles de carro: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 157);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Bocas de Incendio Equipadas (BIE): Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 158);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Bocas de Incendio Equipadas (BIE): Fecha de la última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 159);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Bocas de Incendio Equipadas (BIE) de 25 mm: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 160);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Bocas de Incendio Equipadas (BIE) de 25 mm: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 161);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Bocas de Incendio Equipadas (BIE) de 45 mm: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 162);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Bocas de Incendio Equipadas (BIE) de 45 mm: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 163);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por rociadores: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 164);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por rociadores: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 165);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por rociadores: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 166);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por gases: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 167);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por gases: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 168);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por gases: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 169);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Instalación automática por gases: Última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 170);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Hidrantes exteriores: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 171);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Hidrantes exteriores: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 172);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Hidrantes exteriores: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 173);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Hidrantes exteriores: Última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 174);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Sistema abastecimiento de agua BIEs, depósito de reserva de agua: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 175);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Sistema abastecimiento de agua BIEs, depósito de reserva de agua: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 176);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Sistema abastecimiento de agua BIEs, depósito de reserva de agua: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 177);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Sistema abastecimiento de agua BIEs, depósito de reserva de agua: Última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 178);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Sistema abastecimiento de agua BIEs, depósito de reserva de agua: Red pública.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 179);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Columnas secas: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 180);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Columnas secas: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 181);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Columnas secas: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 182);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de extinción de incendios - Columnas secas: Última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 183);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Detectores de humos: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 184);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Detectores de humos: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 185);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Detectores de humos: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 186);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Detectores de humos: Última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 187);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Central de alarma incendios: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 188);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Central de alarma incendios: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 189);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Central de alarma incendios: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 190);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Pulsadores manuales de alarma: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 191);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Pulsadores manuales de alarma: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 192);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Pulsadores manuales de alarma: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 193);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios de detección de incendios - Pulsadores manuales de alarma: Última revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 194);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Alumbrado de emergencia: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 195);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Alumbrado de emergencia: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 196);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Alumbrado de emergencia: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 197);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Señalización de evacuación y de los equipos contra incendios: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 198);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Sirenas de alarma: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 199);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Sirenas de alarma: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 200);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Sirenas de alarma: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 201);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Megafonía: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 202);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Megafonía: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 203);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Megafonía: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 204);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Comunicadores, interfonos, etc: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 205);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Comunicadores, interfonos, etc: Ubicación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 206);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otros medios antiincendios - Comunicadores, interfonos, etc: Empresa mantenedora.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 207);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de emergencia y evacuación: Órgano que lo redactó.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 208);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de emergencia y evacuación: Fecha de implantación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 209);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de emergencia y evacuación: Fecha del último simulacro. Adjuntar acta.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 210);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Residuos tóxicos/peligrosos: Empresa encargada de la retirada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 211);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Residuos tóxicos/peligrosos: Periodicidad de estas operaciones.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 212);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'DOTACIÓN Y GESTIÓN DE RECURSOS MATERIALES', SEQ_AREASCUESTIONARIOS.NEXTVAL, 2);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Inventario de recursos materiales, con expresión de fecha de adjudicación o reposición y estado de conservación. En caso afirmativo, poner a disposición del equipo inspector en el momento de la visita.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edificio: Estado del mobiliario. Posibles valores: Bueno, Aceptable o Deficiente. En el supuesto de que se considere deficiente, argumentar el motivo y las gestiones llevadas a cabo para paliarlas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Botiquines de primeros auxilios: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Botiquines de primeros auxilios: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Desfibriladores automáticos externos: Localización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Desfibriladores automáticos externos: Número.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'DOTACIÓN Y GESTIÓN DE RECURSOS HUMANOS DEL CIE', SEQ_AREASCUESTIONARIOS.NEXTVAL, 3);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Personal Policial', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAPERSONAL', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Personal Administrativo/ Laboral (CCGG, Personal sanitario, Asistencial, etc.)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAPERSONAL', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Personal ausente por tiempo igual o superior a 4 semanas. Causas: Enfermedad/Accidente/Licencias/Liberado sindical/ Paternidad/ Comisiones de servicio/otros. No incluir Vacaciones ni permisos por Asuntos particulares.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAAUSENCIAS', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación numérica del personal que ocupa puesto de trabajo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZRELACIONPERSONAL', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Movimiento de personal', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZMOVIMIENTOPERSONAL', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Grado de cobertura del CIE. Se considera adecuado en todas sus categorías y/o especialidades. En caso negativo informar sobre el motivo de las mismas y las actuaciones llevadas cabo para su solución.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemática y causas relativas al absentismo y/o dificultad cobertura vacantes (especificar por categorías o especialidades). ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Personal de la plantilla que presta servicio en un puesto de trabajo distinto al que tiene asignado. ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Otras cuestiones, problemas o necesidades en materia de personal que haya que  reseñar o valorar. ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Horarios de servicio  y cadencia de los turnos:  Personal Operativo y de Gestión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones Iniciales de Riesgo/Planificación de la actividad preventiva (Organización periférica del Servicio de Prevención de Riesgos Laborales, SPRL): Fecha de evaluación y entidad evaluadora, así como la fecha de entrega  de dicha documentación en la Unidad encargada de su recepción.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Cumplimentación de las fichas de seguimiento y control de las acciones correctoras (SPRL).  ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Organización de las emergencias (SPRL): Plan de Medidas de Emergencia/Plan de Autoprotección: Fechas de elaboración, revisión/supervisión (por el Servicio de Prevención de Riesgos Laborales) e implantación. Si están constituidos los Equipos de Emergencia y formación de los mismos. Simulacros (aportar Actas). (Artículo 9, RD 2/2006, de 16 de enero; y RD 393/2007, de 23 de marzo, que aprueba la Norma Básica de Autoprotección, respectivamente).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación/Información (SPRL): Mecanismos o Procedimientos utilizados para la capacitación y el traslado de la información a los funcionarios del CIE de los riesgos derivados de sus puestos de trabajo/actividad.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la salud (SPRL): Actuaciones relacionadas con la vigilancia de la salud de dichos funcionarios. Vigilancia obligatoria.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales (SPRL): Procedimientos de comunicación, cooperación e información recíprocos establecidos en caso de concurrencia de actividades en un mismo centro de trabajo (artículo 24 de la Ley 31/1995, de Prevención de Riesgos Laborales y RD 171/ 2004 sobre coordinación de actividades empresariales).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Siniestralidad Laboral.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZACCIDENTES', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Accidentes de trabajo investigados. Adjuntar informe.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'DIRECCIÓN', SEQ_AREASCUESTIONARIOS.NEXTVAL, 4);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Nombramiento del Director', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Periodos de sustitución y motivo', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLASUSTITUCIONES', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de escritos y respuestas a escritos, quejas y peticiones de los internos o remitidas a la autoridad competentes', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimiento sobre quejas y peticiones', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Especificar buzones de correspondencia para uso de los internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Custodia y depósito en caja fuerte de objetos de valor y dinero. Procedimiento', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de reservas de plazas aceptadas o denegadas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLARESERVAPLAZAS', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Visitas fuera de horarios autorizadas y motivos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAVISITASFUERAHORARIO', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Iniciativas trasladadas a superiores', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Directrices de organización y funcionamiento', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Normas de régimen interior y, en su caso,  modificaciones', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de entrevistas personales de internos con el director', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 11);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'JUNTA DE COORDINACIÓN', SEQ_AREASCUESTIONARIOS.NEXTVAL, 5);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Composición de la junta de coordinación. (Introduzca los siguientes cargos: Director, Jefe Seguridad, Administrador, Asistencia sanitaria y social, secretario u otros).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAJUNTACOORDINACION', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Normas de funcionamiento', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Actas de reuniones ordinarias o extraordinarias', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informes a consultas sobre normas de régimen interior', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informes a consultas sobre directrices e instrucciones sobre organización de los distintos servicios', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informes a consultas sobre criterios de actuación en cuestiones de alteración del orden, o   incumplimiento de normas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informes a consultas sobre peticiones y quejas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 6);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'UNIDAD DE SEGURIDAD', SEQ_AREASCUESTIONARIOS.NEXTVAL, 6);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estructura orgánica', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Criterios de selección', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Promedio real o efectivo del personal policial que presta servicio en cada turno', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de policías mínimo establecido para cada turno', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Edad media de los funcionarios policiales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAMEDIASPOLICIA', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Tiempo medio de permanencia de los funcionarios policiales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAMEDIASPOLICIA', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de atestados o diligencias instruidas y motivo de las mismas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Dependencias o lugares donde se prestan servicio sin armas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios donde se van provistos de equipos conectados a la sala del 091 o al canal interno de trabajo del CIE', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Inspecciones, comprobaciones, rondas, cacheos  y recuentos de los internos. Normas, instrucciones u órdenes de servicio existentes. Operatividad y  frecuencia con que deben realizarse', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Registro de habitaciones. Normas, instrucciones u órdenes de servicio existentes. Cuantificar, de llevarse un registro contable', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Objetos prohibidos o no autorizados incautados o intervenidos. Normas, instrucciones u órdenes de servicio existentes. Cuantificar de llevarse un registro contable', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Control y gestión de llaves. Normas, instrucciones u órdenes de servicio existentes. Operatividad', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Control de acceso de personas, vehículos, correspondencia y paquetería. Normas, instrucciones u órdenes de servicio existentes. Operatividad', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Detalle de instrumentos de control instalados, con expresión de clase, y ubicación (cámaras, arcos detectores, etc.)', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Destino dado a las grabaciones y tiempo de permanencia', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ocasiones en que, por razón de urgencia, se han adoptados medidas tendentes a restablecer y asegurar el orden con apoyo de otras unidades policiales en motines, fugas u otros incidentes graves acaecidos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ocasiones en que ha sido necesario el empleo de medios de contención física personal, especificando en que han consistido y causa que las hayan motivado', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 17);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ocasiones en que ha sido necesaria la separación preventiva individual de algún interno, especificando su causa', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Tiro', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZTIRO', 19);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Automoción: Dotación de vehículos policiales asignados al CIE', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 20);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Automoción: Servicio a que se destinan los vehículos policiales asignados ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 21);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Automoción: Causas más comunes en los accidentes registrados. Medidas adoptadas para prevenirlos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 22);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Automoción: Determinación de responsabilidad del siniestro', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 23);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Automoción: Daños personales o materiales a terceras personas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 24);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Armamento y equipación policial: Dotación de armas largas que dispone el CIE', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 25);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Armamento y equipación policial: Dotación de material antidisturbios', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 26);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Armamento y equipación policial: Existencia de armeros para su depósito', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 27);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Armamento y equipación policial: Dotación de chalecos antibalas ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 28);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informática', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZINFORMATICA', 29);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Telecomunicaciones', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZTELECOMUNICACIONES', 30);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Determinar los problemas o  necesidades existentes  en materia de organización, funcionamiento y equipamiento policial, así como las actuaciones llevadas a cabo para solucionarlos o corregirlos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 31);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Régimen disciplinario: Número de informaciones reservadas, procedimientos sancionadores y expedientes disciplinarios: situación y motivo de la apertura o incoación', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 32);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'ADMINISTRACIÓN', SEQ_AREASCUESTIONARIOS.NEXTVAL, 7);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Nombramiento del administrador ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de quejas ante los organismo indicados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAQUEJAS', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de solicitud de entrevistas personales con el Director', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de propuestas de alteración de horarios ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de informes sobre deficiencias ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Actuaciones o medidas adoptadas en materia de creencia religiosa de los internos ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios o instalaciones destinados a facilitar la práctica de diferentes confesiones religiosas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Solicitudes de internos para entrar en contactos con ONG,s. Procedimiento y tratamiento de la solicitud', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Horario y días de visitas de familiares', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de personas por visita', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Como se informa a los visitantes de las normas de régimen interior', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de personas que pueden simultanear la visita', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Lugar donde se llevan a cabo las visitas a internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Duración mínima de las visitas a internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Limitaciones de las visitas a internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, '¿Existe cartel informativo de las visitas a internos? ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Como se aborda el problema de idioma de internos con los diferentes derechos y obligaciones, asistencia legal y socio-sanitaria', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ejemplares de libros que contiene la biblioteca. Indicar temática e idioma de los mismos. Procedimiento de entrega y control del préstamo', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Descripción de medios lúdicos instalados en la sala de estar y zonas comunes', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Indicar deportes y actividades diferenciados por sexos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 19);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ejemplares de prensa diaria, con indicación de nombre e idioma de edición', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 20);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Horarios del centro, con especificación de actividades ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 21);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'SECRETARÍA', SEQ_AREASCUESTIONARIOS.NEXTVAL, 8);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Nombramiento del Secretario', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Especificar documentación de ingreso', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Especificar documentos que integra el expediente personal y fichas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Como se le informa al interno sobre derechos y obligaciones', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Como se informa de su situación al interno y de las resoluciones administrativas y judiciales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Especificar idiomas de del Boletín Informativo', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Boletín Informativo ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Comunicación del ingreso a terceras personas. Destinatarios', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Llamada telefónica gratuita. Destinatarios y procedimiento', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de trámites documentales con las Unidades policiales que gestionan los expedientes de extranjeros', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de trámites documentales con los juzgados competentes para el control de extranjeros internados ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ingresos y reingresos diferenciados por sexos y meses', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAINGRESOS', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de salidas, diferenciadas por sexos y meses ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAINGRESOS', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Motivos salida ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAMOTIVOSALIDA', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de traslados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLATRASLADOS', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de situaciones que se han producido en las cuales no se pueda llevar a efecto la expulsión de un interno por el art. 89.8 del CP', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de reingresos por no poderse llevar a cabo la expulsión, devolución o regreso', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Cómo se tratan los datos de carácter personal en los expedientes', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'FORMACIÓN', SEQ_AREASCUESTIONARIOS.NEXTVAL, 9);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación numérica del personal que ha recibido cursos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'MATRIZCURSOS', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Problemas o necesidades  en materia de formación relacionadas con la actividad del CIE', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'MECANISMO DE CONTROL E INSPECCIÓN', SEQ_AREASCUESTIONARIOS.NEXTVAL, 10);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relacionar las visitas o actuaciones de control llevadas a cabo por la Autoridad Judicial o el Ministerio Fiscal,  con inclusión de fechas y  motivos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relacionar las visitas o actuaciones de control llevadas a cabo por el Defensor del Pueblo,  otras autoridades u organismos, con inclusión de fechas y motivos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relacionar las visitas o actuaciones de control llevadas a cabo por la Dirección General de la Policía u otros órganos del Mº Interior', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Datos anuales referidos al CIE publicados por la Comisaría General de Extranjería y Fronteras', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);


Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'LIBROS REGISTRO', SEQ_AREASCUESTIONARIOS.NEXTVAL, 11);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Libros Registros ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLALIBROS', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Cómo se efectúa el control sobre la custodia y cumplimentación de los libros', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'SERVICIO DE ASISTENCIA SANITARIA', SEQ_AREASCUESTIONARIOS.NEXTVAL, 12);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Médico de la administración, responsable del servicio de Asistencia sanitaria y/o médico de entidad concertada y horarios', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de ATS/Diplomado/Graduado Universitario en Enfermería. Horarios', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de actos médicos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de actos de enfermería', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Actuaciones, informes y propuestas llevadas a cabo, en materia de alimentación', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informes y propuestas llevadas a cabo, en materia de aseo de los internos, sus ropas y pertenencias', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Actuaciones llevadas a cabo en materia de higiene, calefacción, iluminación, y ventilación de las dependencia', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Controles periódicos de salubridad', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Actuaciones, informes y propuestas en materia de prevención de epidemias y medidas de aislamiento de pacientes infecto-contagiosos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Convenios con otra Administración Pública para casos de hospitalización o especialidades', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Convenios con entidades privadas para casos de hospitalización o especialidades', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Numero de reconocimientos médicos mediante mandamiento judicial, por negación del interno', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Numero de reconocimientos médicos ordenados por el Director', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de desplazamientos para hospitalización o asistencia médica especializada en los últimos doce meses y número de desplazados que requirieron hospitalización. Número de internos hospitalizados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Numero de partes de lesiones, por las producidas anteriores al ingreso, y que estuvieran descritas en el parte facultativo de lesiones que aportan los funcionarios que hacen entrega del interno', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Numero de partes de lesiones, por las producidas anteriores al ingreso, y que no estuvieran descritas en el parte facultativo de lesiones que aportan los funcionarios que hacen entrega del interno', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'El centro cuenta con protocolo de ingreso de nuevos internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 16);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Existe Protocolo de actuación frente a posibles patologías infectocontagiosas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 17);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Kit de higiene: composición y periodicidad de reposición', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Ropas: relación de prendas de que se dota a los internos con carencias en cuestión de vestuario diferenciando por sexos, incluyendo ropa interior y calzado (pantalones, jerséis,  mudas, etc.). Frecuencia de reposición y procedimiento de lavado: lavandería, lavadoras, lavado a mano por los internos, etc', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 19);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'En el tipo de prenda suministrada se tiene en cuenta los aspectos ambientales: climatología, estación del año, etc', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 20);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio sanitario: descripción de las instalaciones. Material del que dispone. Capacidad para atender urgencias', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 21);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de reuniones de seguimiento de la prestación de los servicios sanitarios, asistenciales y sociales entre la Comisaria General de Extranjería y Fronteras y representante de las entidades con las que se haya suscritos convenios para la prestación de dichos servicios. Asistentes a la reunión, temas tratados y acuerdos adoptados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 22);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Atención farmacéutica: Indicar procedimiento adquisición medicamentos, control y almacenamiento de los mismos. Procedimiento de administración de medicamentos a los internos o protocolo establecido al efecto', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 23);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimiento para la solicitud de asistencia médica. Mecanismo de registro y control de consultas médicas: libro de registro de consultas médicas y de asistencia de enfermería, hojas de registro, etc', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 24);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Tratamiento de los datos de salud', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 25);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Derecho a la intimidad de los pacientes en consulta: medidas para garantizar este derecho', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 26);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Existe enfermería para cortas estancias y situaciones que requieran un seguimiento y observación  más estrecha del enfermo', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 27);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Existen normas de actuación  para gestionar la realización de analíticas y otras pruebas complementarias  a los  internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 28);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Protocolos: el personal sanitario del CIE dispone de  protocolos de  asistencia sanitaria acorde a la actividad desarrollada en el mismo y adaptados a las peculiares condiciones de las personas a quienes va dirigida dicha asistencia: de ingreso, de huelga de hambre, de aislamiento disciplinario, de actuación ante embarazos detectados en el CIE, de administración de medicamentos, de atención al drogodependiente, etc', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 29);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'El Servicio Sanitario ha sido informado de las “MEDIDAS DE ACTUACIÓN PREVENTIVAS DE SUICIDIOS EN LOS CENTROS DE INTERNAMIENTO DE EXTRANJEROS”  dirigidas a reducir su riesgo elaborado por la Comisaría General de Extranjería y Fronteras', SEQ_AREASCUESTIONARIOS.CURRVAL, 'RADIOSINO', 30);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'A la salida del  interno se le entrega una copia de su expediente médico', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 31);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'La gestión de las historias clínicas y datos médicos esta informatizada', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 32);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Alimentación: Adjuntar menú básico', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 33);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Casos intoxicación alimentaria o de retirada de alimentos por mal estado desde la última inspección', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 34);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Adaptación alimentación a situaciones enfermedad: indicar procedimiento y control del  cumplimiento dietas prescritas por servicio sanitario de causa médica', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 35);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Adaptación alimentación a otras situaciones: creencias o celebraciones religiosas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 36);


Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'SERVICIOS DE ASISTENCIA SOCIAL, JURÍDICA Y CULTURAL', SEQ_AREASCUESTIONARIOS.NEXTVAL, 13);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Estructura ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Número de trabajadores y/o mediadores sociales. Horarios', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Planes o proyectos de actuación presentados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Planes o proyectos de actuación aprobados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Tratamiento de datos de carácter personal', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Convenios con órganos de otros Ministerios, con entidades públicas y privadas y con ONGs', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Actividades llevadas a cabo. Horario de actividades', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Plazo de entrevista del interno nuevo con el servicio de asistencia social', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicio de asistencia social y cultural: Formación en derechos humanos, extranjería, protección internacional, mediación intercultural y violencia de género. Numero de curso, jornadas, etc. que ha recibido el personal que desarrolla esta actividad', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios de orientación jurídica: Convenios con colegios de abogados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios de orientación jurídica: Información al interno y tramitación de la solicitud de entrevista', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios de orientación jurídica: Número de abogados puestos a disposición. Horarios establecidos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 11);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios de orientación jurídica: Gestión de las entrevistas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Servicios de orientación jurídica: Dependencias destinadas a asegurar la confidencialidad en la orientación jurídica', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 13);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de entrevistas y comunicaciones con abogados (DISTINTOS A LOS DEL SERVICIO DE ORIENTACIÓN JURÍDICA) y representantes diplomáticos y consulares', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Indicar horarios de visitas y limitaciones establecidas', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 15);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Dependencias destinadas a asegurar la confidencialidad de las entrevistas y comunicaciones con abogados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 16);

Insert into  Areascuestionario (id_cuestionario,nombre_area,id, orden) Values (SEQ_MODELOSCUESTIONARIOS.CURRVAL, 'RELACIONES Y COORDINACIÓN', SEQ_AREASCUESTIONARIOS.NEXTVAL, 14);

insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Entidades colaboradoras. Detallando la actividad que realiza cada una', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLACOLABORADORES', 0);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de visitas de ONGs', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 1);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Número de comunicaciones a ONGs sobre solicitud de visitas por parte de internos', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 2);
insert into  preguntascuestionario (ID,PREGUNTA,Id_area,tipo_respuesta, orden) Values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Numero de exámenes a que han estado sometidos sus integrantes, causas y resultados', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);

COMMIT;

insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo01', 'TABLAFORMACIONINPARTIDA', 'ACTIVIDAD');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo02', 'TABLAFORMACIONINPARTIDA', 'MATERIA (General, específica, Medidas de Emergencia…)');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo03', 'TABLAFORMACIONINPARTIDA', 'Personal que la impartió');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo04', 'TABLAFORMACIONINPARTIDA', 'Personal que lo recibió');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo05', 'TABLAFORMACIONINPARTIDA', 'Número de alumnos');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo06', 'TABLAFORMACIONINPARTIDA', 'Duración.');
insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('campo07', 'TABLAFORMACIONINPARTIDA', 'Método utilizado (Telemático, presencial, divulgativo,…)');

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de CUESTIONARIO DE PRL DE LA GUARDIA CIVIL...
prompt =========================================================================

Insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.nextval,'PRL_GC','CUESTIONARIO DE PRL DE LA GUARDIA CIVIL');

Insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estructura, Dependencia y Ámbito de Competencia.
Organización, estructura, funciones y competencias del Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Recursos Humanos.
Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, así como personal de apoyo y auxiliar, con expresión de Orden y fecha de creación de la Unidad (Sección/Oficina). ', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios Materiales.
Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida. Acreditar si los Técnicos del Servicio de Prevención están instruidos en el uso de los equipos y si éstos están, en su caso, calibrados. A fecha de la visita se tendrá a disposición del equipo inspector copia de la documentación acreditativa relativa a la formación y calibración de los equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);

Insert into  Areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.
Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención.
Planes de Zona, elaborados por las Secciones y fecha de aprobación por el Director General de la Guardia Civil.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos.
Relación de Evaluaciones de Riesgo realizadas, diferenciando las llevadas a cabo por el Servicio de Prevención, Secciones y Oficinas, en el periodo indicado, con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido el tiempo previsto y aquellas otras circunstancias que exijan una revisión y actualización).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos. 
Relación de las evaluaciones que quedan pendientes de realizar. Si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos (Unidades especializadas).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 3);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Propuesta de Planificación Preventiva. 
Actividades de seguimiento y control de las acciones correctoras', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección. 
Planes de Emergencia/Autoprotección elaborados durante el periodo, diferenciando los llevados a cabo por el Servicio de Prevención, Secciones y Oficinas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección. 
Relación de los que quedan pendientes de realizar.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección. 
Indicar simulacros realizados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia.
Procedimientos de actuación y mecanismos de coordinación entre el Servicio de Prevención y los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia en materia de vigilancia y control de la salud de los guardias civiles en relación con los riesgos derivados del ejercicio de las funciones profesionales', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia. Actuaciones desarrolladas destinadas a la protección de trabajadores especialmente sensible a determinados riesgos y declarados útil con limitaciones al objeto de adaptar las condiciones y exigencias que demanda el puesto de trabajo al estado psicofísico del funcionario.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Coordinación con los Servicios de Asistencia Sanitaria y de Psicología y Psicotecnia. Actuaciones llevadas a acabo en relación a la protección de la guardia civil en situación de embarazo, parto reciente o en periodo de lactancia.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Relación de accidentes de trabajo por provincias con indicación de mortales, graves y más de 4 leves.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 11);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de notificación e investigación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Indicar si los informes de investigación son recibidos por la Sección de Prevención de la Unidad afectada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 13);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Relación de los informes de investigación realizados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Responsable de ejecutar las medidas preventivas propuestas en los informes de investigación cuando el puesto de trabajo es común a nivel nacional.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 15);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio.
Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 16);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.
Manuales, Campañas de Prevención, Trípticos y cualquier otro medio informativo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales.
Procedimiento empleado para justificar la recepción por el personal.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida.
Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAFORMACIONINPARTIDA', 19);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida.
Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAFORMACIONINPARTIDA', 20);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Asesoramiento e informes emitidos.
Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos laborales en el ámbito de la Dirección General de la Guardia Civil.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 21);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.
Medidas que se han adoptado para que las distintas Unidades de la Guardia Civil dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 22);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.
¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Guardia Civil?.  En caso afirmativo indicar número y tipo de actividad contratada. 
Número y tipos de actuaciones en esta materia llevadas a cabo por el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 23);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales.
Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 24);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Registro y Archivo de Documentación.
Relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Guardia Civil en materia de prevención de riesgos laborales, que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 25);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimientos e Instrucciones Operativas de Prevención de Riesgos Laborales.
Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas en vigor, que estén o hayan sido objeto de actualización o revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 26);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Protocolo de actuación frente al acoso laboral.
Papel desempeñado por el Servicio de Prevención en el Protocolo de Acoso.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 27);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación de propuestas de mejora y sugerencias en materia de prevención, recibidas en el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 28);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Elaboración de la Memoria Anual.
Las actividades preventivas que se documentan a través de una programación y memoria anual. Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 29);

COMMIT;

prompt =========================================================================
prompt Ejecutando carga de CUESTIONARIO DE PRL DE LA POLICIA NACIONAL...
prompt =========================================================================

insert into MODELOSCUESTIONARIOS (ID,CODIGO,DESCRIPCION) values (SEQ_MODELOSCUESTIONARIOS.NEXTVAL,'PRL_PN','CUESTIONARIO DE PRL DE LA POLICÍA NACIONAL');

insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'DATOS GENERALES', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 0);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estructura, Dependencia y Ámbito de Competencia. 
Relación documentada de las actividades preventivas encuadradas en las especialidades contempladas no asumidas por el Servicio de Prevención y para cuya utilización se ha recurrido a un Servicio de Prevención Ajeno.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Recursos Humanos.
Personal destinado en el Servicio de Prevención con indicación de las titulaciones en materia de prevención tanto en la estructura central como territorial, así como personal de apoyo y auxiliar.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Medios Materiales.
Medios materiales de que dispone para desarrollar la acción preventiva encomendada y asumida. Acreditar si los Técnicos del Servicio de Prevención están instruidos en el uso de los equipos y si éstos están, en su caso, calibrados. A fecha de la visita se tendrá a disposición del equipo inspector copia de la documentación acreditativa relativa a la formación y calibración de los equipos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);


insert into  areascuestionario (id, nombre_area, id_cuestionario, orden) values (SEQ_AREASCUESTIONARIOS.NEXTVAL, 'FUNCIONES Y ACTIVIDADES DE LOS SERVICIOS DE PREVENCIÓN', SEQ_MODELOSCUESTIONARIOS.CURRVAL, 1);

insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención. 
Fecha de aprobación del Plan y Manual de Prevención de Riesgos Laborales y modificaciones que se hayan llevado a cabo, en su caso, en cada uno de los documentos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 0);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención. 
Deficiencias e incumplimientos detectados y puestos de manifiesto durante el desarrollo e implantación del Plan.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 1);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Plan de Prevención. 
En el caso de haberse detectado deficiencias, ¿se han elevado propuestas de revisión y modificación del Plan de Prevención al objeto de adaptarlo a la situación real que permita llevar a cabo adecuadamente las actividades preventivas de las especialidades asumidas por el Servicio? En caso afirmativo acreditar las propuestas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 2);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos. 
Relación de Evaluaciones de Riesgo realizadas en el periodo indicado con especificación de si son iniciales o reevaluaciones (por reformas estructurales, nuevos equipos de trabajo, haber transcurrido cuatro años y aquellas otras circunstancias que exijan una revisión y actualización). Si dichas evaluaciones de riesgos contemplan los correspondientes riesgos específicos.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTO', 3);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Evaluaciones de Riesgos. 
Relación de plantillas o unidades a las que se ha practicado revisiones de evaluación indicando las circunstancias y el Servicio de Prevención que las ha llevado a cabo. Previsiones o programación de las evaluaciones pendientes de realizar y el Servicio de Prevención que vaya a asumir su realización.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 4);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Propuesta de Planificación Preventiva. 
Actividades de seguimiento y control de las Fichas de Seguimiento de las Acciones Correctoras.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 5);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección. 
Planes de Emergencia/Autoprotección supervisados y/o elaborados por el Servicio de Prevención durante el periodo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 6);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Planes de Emergencia y de Autoprotección. 
Indicar simulacros realizados. A fecha de la visita se tendrá a disposición del equipo inspector copia de la documentación acreditativa (Actas).', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 7);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Protocolos de reconocimiento médico específico en relación a los riesgos en el puesto de trabajo enumerando aquellos que por razón de la naturaleza de la actividad profesional sean de carácter obligatorio, con indicación de la periodicidad establecida.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 8);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Informes, dictámenes y recomendaciones emitidos en materia de vigilancia de la salud: de aptitud física, de aptitud para el trabajo, de cambio de puesto de trabajo por motivos de salud, sobre riesgo durante el embarazo, parto reciente o lactancia o cualquier circunstancia o situación en el ámbito de la evaluación de la salud de los empleados públicos en relación con las condiciones del puesto de trabajo.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 9);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Actividades en materia de asistencia sanitaria, prevención y promoción de la salud dentro de las previsiones que, en materia de seguridad y salud laboral, tiene establecidos el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 10);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Relación y organización del personal sanitario capacitado en la materia tanto a nivel central como territorial.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 11);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Recursos materiales: relación de instalaciones, medios materiales sanitarios y de archivo de los que está dotado el servicio de prevención para desarrollar las actividades sanitarias asignadas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 12);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Vigilancia de la Salud. 
Procedimientos de actuación y mecanismos de coordinación entre el Servicio Sanitario y el Servicio de Prevención en materia de vigilancia y control de la salud de los empleados públicos en el ámbito de la Dirección General de la Policía.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 13);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio. 
Relación de accidentes de trabajo por provincias con indicación de mortales, graves y más de 4 leves.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 14);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio. 
Relación de aquellos incidentes contemplados en el documento “Criterio unificado sobre la valoración de la gravedad de los accidentes e incidentes en las Fuerzas y Cuerpos de Seguridad del Estado, a efectos de notificación e investigación.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 15);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio. 
Relación de los informes de investigación realizados.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 16);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Estudio y Análisis de los Accidentes Ocurridos en Actos de Servicio. 
Relación de comunicaciones efectuadas a la Inspección de Trabajo de conformidad con lo establecido en la disposición adicional cuarta del RD 67/2010.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 17);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Información en Materia de Prevención de Riesgos Laborales. 
Fichas de información de carácter general, especifico o de cualquier otra naturaleza elaborados por el Servicio de Prevención, así como Manuales, Campañas de Prevención, Trípticos y cualquier otro medio informativo. Procedimiento empleado para justificar la recepción por el personal.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 18);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida. 
Relacionar las actividades formativas desplegadas en materia preventiva por ese Servicio de Prevención, así como aquellas otras realizadas en colaboración con otros órganos, unidades o servicios.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAFORMACIONINPARTIDA', 19);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Formación impartida. 
Cursos, conferencias y otras actividades formativas recibidas por el personal del Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TABLAFORMACIONINPARTIDA', 20);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Asesoramiento e informes emitidos. 
Breve descripción de las actividades de asesoramiento y de informes o dictámenes emitidos por el Servicio de Prevención relacionados con el sistema de gestión de la prevención de riesgos laborales en el ámbito de la Dirección General de la Policía.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 21);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales. 
Qué tipo de medidas se han adoptado para que las distintas plantillas y unidades la Policía Nacional dispongan de la información y las instrucciones adecuadas a seguir en materia de coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 22);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales. 
¿Se informa al Servicio de Prevención de los contratos de prestación de obras o servicios suscritos en el ámbito de la actividad de la Dirección General de la Policía? En caso afirmativo indicar número y tipo de actividad contratada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 23);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales. 
Número y tipo de actuaciones en esta materia solicitadas al Servicio de Prevención por los titulares de los órganos de la Institución.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 24);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Coordinación de actividades empresariales. 
Explicar brevemente si existe o se ejerce por el Servicio de Prevención algún tipo de control, vigilancia o seguimiento sobre las medidas previstas para la coordinación de actividades empresariales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 25);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Registro y Archivo de Documentación. 
En referencia a lo dispuesto en el Art. 23 de la Ley de Prevención de Riesgos Laborales, relación detallada de la documentación correspondiente a la actividad desplegada en el ámbito de la Dirección General de la Policía en materia de prevención de riesgos laborales, que se encuentra bajo la custodia y archivo de ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 26);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Procedimientos e Instrucciones Operativas de Prevención de Riesgos Laborales. 
Relación de Procedimientos e Instrucciones Operativas aprobados, pendientes de aprobación o en fase de elaboración y estudio. En su caso, informando sobre su causa o justificación, relación de Procedimientos o de Instrucciones Operativas en vigor que estén o hayan sido objeto de actualización o revisión.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 27);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Acoso laboral.
Registros de Actuaciones por Acoso Laboral.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 28);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Acoso laboral.
Relación de Acuerdos adoptados por Acoso laboral.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 29);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Acoso laboral.
Número de Informes elaborados sobre situación previa de los riesgos psicosociales en la Unidad afectada.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 30);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Acoso laboral.
Relación de las actuaciones de seguimiento respecto del cumplimiento de las medidas preventivas/correctoras propuestas en el marco de los riesgos laborales.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTOINPUT', 31);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Acoso laboral.
Número de denuncias falsas.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'INPUT', 32);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Relación de propuestas de mejora y sugerencias en materia de prevención recibidas en el Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 33);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Elaboración de la Memoria Anual. 
Las actividades preventivas se documentan a través de una programación y memoria anual. Indicar si se contienen las prioridades fijadas y los objetivos alcanzados o en fase de elaboración a este respecto.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'TEXTAREA', 34);
insert into  preguntascuestionario (id, pregunta, id_area, tipo_respuesta, orden) values (SEQ_PREGUNTASCUESTIONARIO.NEXTVAL, 'Informe de Auditoría Interna realizado por ese Servicio de Prevención.', SEQ_AREASCUESTIONARIOS.CURRVAL, 'ADJUNTORADIOSINO', 35);

commit;