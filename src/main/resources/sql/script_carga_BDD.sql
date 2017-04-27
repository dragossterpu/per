WHENEVER OSERROR EXIT FAILURE ROLLBACK
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT IMPLANTACIÓN PROGESIN
prompt
prompt    Autor: Rubén Astudillo
prompt
prompt    Fecha creación: 01/03/2017
prompt	  Actualización:  22/03/2017   Rubén 
prompt	  Actualización:  24/03/2017   Raúl 
prompt	  Actualización:  27/03/2017   Ramón 
prompt	  Actualización:  21/04/2017   Ramón

prompt =========================================================================


prompt =========================================================================
prompt + Tarea1
prompt =========================================================================
prompt Ejecutando borrado de tablas y de secuencias...


prompt =========================================================================
prompt + Tarea1_1
prompt =========================================================================
prompt Ejecutando borrado de secuencias...

BEGIN
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_ALERTA"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_AREASCUESTIONARIOS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_CLASE"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_CUERPOS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_CUESTIONARIOPERSONALIZADO"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_CUESTIONARIOSENVIADOS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_DEPARTAMENTO"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_DOCUMENTACION_PREVIA"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_DOCUMENTOS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "seq_documentosblob"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_EMPLEO"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_EQUIPO"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_GUIAS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_GUIAPERSONALIZADA"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_INSPECCION"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_MIEMBROS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_MODELOSCUESTIONARIOS"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_NOTIFICACIONES"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_PREGUNTASCUESTIONARIO"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_PASOSGUIA"';
	EXECUTE	IMMEDIATE 'DROP SEQUENCE "SEQ_PUESTO_TRABAJO"';	
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_REGISTRO"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_RESPUESTATABLA"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_SOLDOCPREVIA"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_TIPODOCUMENTACIONPREVIA"';
	EXECUTE IMMEDIATE 'DROP SEQUENCE "SEQ_TIPO_EQUIPO"';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
prompt =========================================================================
prompt + Tarea1_2
prompt =========================================================================
prompt Ejecutando borrado de tablas...
BEGIN
	EXECUTE IMMEDIATE 'DROP TABLE "ALERTAS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "ALERTAS_NOTIFICACIONES_USUARIO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "AREAS_USUARIO_CUESTENV" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "AREASCUESTIONARIO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "CLASE_USUARIO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "CONFIG_RESPUESTAS_CUESTIONARIO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "CUERPOSESTADO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "CUESTIONARIO_PERSONALIZADO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "CUESTIONARIOS_ENVIADOS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "CUEST_PER_PREGUNTAS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "DEPARTAMENTO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "DOCUMENTACION_PREVIA" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "DOCUMENTOS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "DOCUMENTOS_BLOB" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "EMPLEO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "EQUIPO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "GESTDOCSOLICITUDDOCUMENTACION" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "GUIAS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "GUIA_PASOS" CASCADE CONSTRAINTS'; 
	EXECUTE IMMEDIATE 'DROP TABLE "GUIA_PERSONALIZADA" CASCADE CONSTRAINTS'; 
	EXECUTE IMMEDIATE 'DROP TABLE "GUIA_PERSONALIZADA_PASOS"  CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "INSPECCIONES" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "MIEMBROS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "MODELOSCUESTIONARIOS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "NOTIFICACIONES" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "PARAMETROS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "PREGUNTASCUESTIONARIO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "PUESTOSTRABAJO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "REG_ACTIVIDAD" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "RESPUESTA_DATOS_TABLA" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "RESPUESTAS_CUEST_DOCS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "RESPUESTASCUESTIONARIO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "SOLICITUD_DOC_PREVIA" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "SUGERENCIA" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "TIPODOCUMENTACIONPREVIA" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "TIPO_EQUIPO" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "TIPOS_INSPECCION" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "USERS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "MUNICIPIOS" CASCADE CONSTRAINTS';
	EXECUTE IMMEDIATE 'DROP TABLE "PROVINCIAS" CASCADE CONSTRAINTS';
	
EXCEPTION WHEN OTHERS THEN NULL;
END;
/	
COMMIT;


prompt =========================================================================
prompt + Tarea2
prompt =========================================================================
prompt Ejecutando creación de tablas...



prompt =========================================================================
prompt + Tarea2_1
prompt =========================================================================
prompt Ejecutando creación de ALERTAS...

  CREATE TABLE "ALERTAS" 
   (	"ID_ALERTA" NUMBER(19,0), 
	"DESCRIPCION" VARCHAR2(2000 CHAR), 
	"FECHA_REGISTRO" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"NOMBRE_SECCION" VARCHAR2(50 CHAR), 
	"USUARIO_REGISTRO" VARCHAR2(255 CHAR), 
	"USUARIO_BAJA" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_2
prompt =========================================================================
prompt Ejecutando creación de ALERTAS_NOTIFICACIONES_USUARIO...

   CREATE TABLE "ALERTAS_NOTIFICACIONES_USUARIO" 
   (	"USUARIO" VARCHAR2(255 CHAR), 
	"TIPO_MENSAJE" VARCHAR2(50 CHAR), 
	"ID_MENSAJE" NUMBER(19,0), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"NOMBRE_SECCION" VARCHAR2(50 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_3
prompt =========================================================================
prompt Ejecutando creación de AREASCUESTIONARIO...

	CREATE TABLE "AREASCUESTIONARIO" 
   (	"ID" NUMBER(19,0), 
	"NOMBRE_AREA" VARCHAR2(255 CHAR), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"ID_CUESTIONARIO" NUMBER(10,0), 
	"ORDEN" NUMBER(10,0), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_4
prompt =========================================================================
prompt Ejecutando creación de CLASE_USUARIO...

  CREATE TABLE "CLASE_USUARIO" 
   (	"ID_CLASE" NUMBER(19,0), 
	"CLASE" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_5
prompt =========================================================================
prompt Ejecutando creación de CONFIG_RESPUESTAS_CUESTIONARIO...

  CREATE TABLE "CONFIG_RESPUESTAS_CUESTIONARIO" 
   (	"CLAVE" VARCHAR2(255 CHAR), 
	"SECCION" VARCHAR2(255 CHAR), 
	"VALOR" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_6
prompt =========================================================================
prompt Ejecutando creación de CUERPOSESTADO...

  CREATE TABLE "CUERPOSESTADO" 
   (	"ID" NUMBER(10,0), 
	"DESCRIPCION" VARCHAR2(100 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_MODIF" TIMESTAMP (6), 
	"NOMBRE_CORTO" VARCHAR2(10 CHAR), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(12 CHAR), 
	"USERNAME_MODIF" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_7
prompt =========================================================================
prompt Ejecutando creación de CUESTIONARIO_PERSONALIZADO...

  CREATE TABLE "CUESTIONARIO_PERSONALIZADO" 
   (	"ID" NUMBER(19,0), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_CREACION" TIMESTAMP (6), 
	"NOMBRE_CUESTIONARIO" VARCHAR2(100 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_CREACION" VARCHAR2(255 CHAR), 
	"ID_MODELO_CUESTIONARIO" NUMBER(10,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_8
prompt =========================================================================
prompt Ejecutando creación de CUESTIONARIOS_ENVIADOS...

  CREATE TABLE "CUESTIONARIOS_ENVIADOS" 
   (	"ID" NUMBER(19,0), 
	"CARGO" VARCHAR2(500 CHAR), 
	"CORREO" VARCHAR2(500 CHAR), 
	"FECHA_ANULACION" TIMESTAMP (6), 
	"FECHA_CUMPLIMENTACION" TIMESTAMP (6), 
	"FECHA_ENVIO" TIMESTAMP (6), 
	"FECHA_FINALIZACION" TIMESTAMP (6), 
	"FECHA_LIMITE_CUESTIONARIO" TIMESTAMP (6), 
	"FECHA_NO_CONFORME" TIMESTAMP (6), 
	"MOTIVO" VARCHAR2(2000 CHAR), 
	"NOMBRE_USUARIO" VARCHAR2(500 CHAR), 
	"USERNAME_ANULACION" VARCHAR2(255 CHAR), 
	"USERNAME_ENVIO" VARCHAR2(255 CHAR), 
	"USERNAME_FINALIZACION" VARCHAR2(255 CHAR), 
	"USERNAME_NO_CONFORME" VARCHAR2(255 CHAR), 
	"ID_CUESTIONARIO_PERSONALIZADO" NUMBER(19,0), 
	"ID_INSPECCION" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_9
prompt =========================================================================
prompt Ejecutando creación de CUEST_PER_PREGUNTAS...

  CREATE TABLE "CUEST_PER_PREGUNTAS" 
   (	"ID_CUEST_PERS" NUMBER(19,0), 
	"ID_PREG_ELEGIDA" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_10
prompt =========================================================================
prompt Ejecutando creación de DEPARTAMENTO...

  CREATE TABLE "DEPARTAMENTO" 
   (	"ID" NUMBER(19,0), 
	"DESCRIPCION" VARCHAR2(100 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_MODIF" TIMESTAMP (6), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_MODIF" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_11
prompt =========================================================================
prompt Ejecutando creación de DOCUMENTACION_PREVIA...

  CREATE TABLE "DOCUMENTACION_PREVIA" 
   (	"ID" NUMBER(19,0), 
	"DESCRIPCION" VARCHAR2(255 CHAR), 
	"EXTENSIONES" VARCHAR2(255 CHAR), 
	"ID_SOLICITUD" NUMBER(19,0), 
	"NOMBRE" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_12
prompt =========================================================================
prompt Ejecutando creación de DOCUMENTOS...

  CREATE TABLE "DOCUMENTOS" 
   (	"ID" NUMBER(19,0), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"NOMBRE" VARCHAR2(255 CHAR), 
	"TIPO_CONTENIDO" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"ID_FICHERO" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_13
prompt =========================================================================
prompt Ejecutando creación de DOCUMENTOS_BLOB...

  CREATE TABLE "DOCUMENTOS_BLOB" 
   (	"ID" NUMBER(19,0), 
	"FICHERO" BLOB
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_14
prompt =========================================================================
prompt Ejecutando creación de EMPLEO...

  CREATE TABLE "EMPLEO" 
   (	"ID" NUMBER(19,0), 
	"DESCRIPCION" VARCHAR2(100 CHAR), 
	"NOMBRE_CORTO" VARCHAR2(20 CHAR), 
	"ID_CUERPO" NUMBER(10,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_15
prompt =========================================================================
prompt Ejecutando creación de EQUIPO...

  CREATE TABLE "EQUIPO" 
   (	"ID" NUMBER(19,0), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"JEFE_EQUIPO" VARCHAR2(100 CHAR), 
	"NOMBRE_EQUIPO" VARCHAR2(100 CHAR), 
	"NOMBRE_JEFE" VARCHAR2(150 CHAR), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"ID_TIPO_EQUIPO" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_16
prompt =========================================================================
prompt Ejecutando creación de GESTDOCSOLICITUDDOCUMENTACION...

  CREATE TABLE "GESTDOCSOLICITUDDOCUMENTACION" 
   (	"ID" NUMBER(19,0), 
	"EXTENSION" VARCHAR2(4 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_MODIFICACION" TIMESTAMP (6), 
	"ID_DOCUMENTO" NUMBER(19,0), 
	"ID_SOLICITUD" NUMBER(19,0), 
	"NOMBRE" VARCHAR2(255 CHAR), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_MODIFICACION" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_17
prompt =========================================================================
prompt Ejecutando creación de GUIAS...

 CREATE TABLE "GUIAS" 
   (	"ID" NUMBER(19,0), 
	"FECHA_ANULACION" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_CREACION" TIMESTAMP (6), 
	"FECHA_MODIFICACION" TIMESTAMP (6), 
	"NOMBRE_GUIA" VARCHAR2(255 CHAR), 
	"ORDEN" NUMBER(10,0), 
	"USERNAME_ANULACION" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USUARIO_CREACION" VARCHAR2(255 CHAR), 
	"USUARIO_MODIFICACION" VARCHAR2(255 CHAR), 
	"TIPO_INSPECCION" VARCHAR2(10 CHAR)
   ) ;
COMMIT;

prompt =========================================================================
prompt + Tarea2_18
prompt =========================================================================
prompt Ejecutando creación de GUIA_PASOS...

CREATE TABLE "GUIA_PASOS" 
   (	"ID" NUMBER(19,0), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"ORDEN" NUMBER(10,0), 
	"PASO" VARCHAR2(2000 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"ID_GUIA" NUMBER(19,0)
   ) ;

prompt =========================================================================
prompt + Tarea2_19
prompt =========================================================================
prompt Ejecutando creación de GUIA_PERSONALIZADA...   

  CREATE TABLE "GUIA_PERSONALIZADA" 
   (	"ID" NUMBER(19,0), 
	"FECHA_ANULACION" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_CREACION" TIMESTAMP (6), 
	"NOMBRE_GUIA_PERSONALIZADA" VARCHAR2(100 CHAR), 
	"USERNAME_ANULACION" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_CREACION" VARCHAR2(255 CHAR), 
	"ID_MODELO_GUIA" NUMBER(19,0), 
	"INSPECCION" NUMBER(19,0)
   ) ;

prompt =========================================================================
prompt + Tarea2_20
prompt =========================================================================
prompt Ejecutando creación de GUIA_PERSONALIZADA_PASOS...  

  CREATE TABLE "GUIA_PERSONALIZADA_PASOS" 
   (	"ID_GUIA_PERS" NUMBER(19,0), 
	"ID_PASO_ELEGIDO" NUMBER(19,0)
   ) ;

prompt =========================================================================
prompt + Tarea2_21
prompt =========================================================================
prompt Ejecutando creación de INSPECCIONES...

  CREATE TABLE "INSPECCIONES" 
   (	"ID" NUMBER(19,0), 
	"AMBITO" VARCHAR2(10 CHAR), 
	"ANIO" NUMBER(10,0), 
	"CUATRIMESTRE" VARCHAR2(255 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_FINALIZACION" TIMESTAMP (6), 
	"NOMBRE_UNIDAD" VARCHAR2(100 CHAR), 
	"NUMERO" VARCHAR2(100 CHAR), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_FINALIZACION" VARCHAR2(255 CHAR), 
	"ID_EQUIPO" NUMBER(19,0), 
	"TIPO_INSPECCION" VARCHAR2(10 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_22
prompt =========================================================================
prompt Ejecutando creación de MIEMBROS...

   CREATE TABLE "MIEMBROS" 
   (	"ID" NUMBER(19,0), 
	"NOMBRE_COMPLETO" VARCHAR2(255 CHAR), 
	"POSICION" VARCHAR2(255 CHAR), 
	"USERNAME" VARCHAR2(255 CHAR), 
	"ID_EQUIPO" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_23
prompt =========================================================================
prompt Ejecutando creación de MODELOSCUESTIONARIOS...

   CREATE TABLE "MODELOSCUESTIONARIOS" 
   (	"ID" NUMBER(10,0), 
	"CODIGO" VARCHAR2(255 CHAR), 
	"DESCRIPCION" VARCHAR2(255 CHAR)
   ) ;
COMMIT;

prompt =========================================================================
prompt + Tarea2_24
prompt =========================================================================
prompt Ejecutando creación de NOTIFICACIONES...

  CREATE TABLE "NOTIFICACIONES" 
   (	"ID_NOTIFICACION" NUMBER(19,0), 
	"DESCRIPCION" VARCHAR2(2000 CHAR), 
	"FECHA_NOTIFICACION" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"NOMBRE_SECCION" VARCHAR2(50 CHAR), 
	"TIPO_NOTIFICACION" VARCHAR2(20 CHAR), 
	"USUARIO_BAJA" VARCHAR2(255 CHAR), 
	"USUARIO_REGISTRO" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_25
prompt =========================================================================
prompt Ejecutando creación de PARAMETROS...

  CREATE TABLE "PARAMETROS" 
   (	"CLAVE" VARCHAR2(255 CHAR), 
	"SECCION" VARCHAR2(255 CHAR), 
	"VALOR" VARCHAR2(4000 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_26
prompt =========================================================================
prompt Ejecutando creación de PREGUNTASCUESTIONARIO...

  CREATE TABLE "PREGUNTASCUESTIONARIO" 
   (	"ID" NUMBER(19,0), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"ORDEN" NUMBER(10,0), 
	"PREGUNTA" VARCHAR2(2000 CHAR), 
	"TIPO_RESPUESTA" VARCHAR2(100 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"ID_AREA" NUMBER(19,0)
   ) ;
COMMIT;

prompt =========================================================================
prompt + Tarea2_27
prompt =========================================================================
prompt Ejecutando creación de PUESTOSTRABAJO...

  CREATE TABLE "PUESTOSTRABAJO" 
   (	"ID" NUMBER(19,0), 
	"DESCRIPCION" VARCHAR2(100 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_MODIF" TIMESTAMP (6), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(12 CHAR), 
	"USERNAME_MODIF" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_28
prompt =========================================================================
prompt Ejecutando creación de REG_ACTIVIDAD...

  CREATE TABLE "REG_ACTIVIDAD" 
   (	"REG_ACTIVIDAD" NUMBER(19,0), 
	"DESCRIPCION" CLOB, 
	"FECHA_ALTA" TIMESTAMP (6), 
	"NOMBRE_SECCION" VARCHAR2(50 CHAR), 
	"TIPO_REG_ACTIVIDAD" VARCHAR2(255 CHAR), 
	"USUARIO_REGISTRO" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_29
prompt =========================================================================
prompt Ejecutando creación de RESPUESTA_DATOS_TABLA...

  CREATE TABLE "RESPUESTA_DATOS_TABLA" 
   (	"ID" NUMBER(19,0), 
	"CAMPO1" VARCHAR2(255 CHAR), 
	"CAMPO10" VARCHAR2(255 CHAR), 
	"CAMPO2" VARCHAR2(255 CHAR), 
	"CAMPO3" VARCHAR2(255 CHAR), 
	"CAMPO4" VARCHAR2(255 CHAR), 
	"CAMPO5" VARCHAR2(255 CHAR), 
	"CAMPO6" VARCHAR2(255 CHAR), 
	"CAMPO7" VARCHAR2(255 CHAR), 
	"CAMPO8" VARCHAR2(255 CHAR), 
	"CAMPO9" VARCHAR2(255 CHAR), 
	"NOMBRE_FILA" VARCHAR2(255 CHAR), 
	"RESPUESTA_ID_CUEST_ENVIADO" NUMBER(19,0), 
	"RESPUESTA_ID_PREGUNTA" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_30
prompt =========================================================================
prompt Ejecutando creación de RESPUESTAS_CUEST_DOCS...

  CREATE TABLE "RESPUESTAS_CUEST_DOCS" 
   (	"ID_CUESTIONARIO_ENVIADO" NUMBER(19,0), 
	"ID_PREGUNTA" NUMBER(19,0), 
	"ID_DOCUMENTO" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_31
prompt =========================================================================
prompt Ejecutando creación de RESPUESTASCUESTIONARIO...

  CREATE TABLE "RESPUESTASCUESTIONARIO" 
   (	"FECHA_CUMPLIMENTACION" TIMESTAMP (6), 
	"FECHA_VALIDACION" TIMESTAMP (6), 
	"RESPUESTA_TEXTO" VARCHAR2(2000 CHAR), 
	"USERNAME_CUMPLIMENTACION" VARCHAR2(255 CHAR), 
	"USERNAME_VALIDACION" VARCHAR2(255 CHAR), 
	"ID_CUEST_ENVIADO" NUMBER(19,0), 
	"ID_PREGUNTA" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_32
prompt =========================================================================
prompt Ejecutando creación de SOLICITUD_DOC_PREVIA...

  CREATE TABLE "SOLICITUD_DOC_PREVIA" 
   (	"ID" NUMBER(19,0), 
	"APOYO_CORREO" VARCHAR2(255 CHAR), 
	"APOYO_NOMBRE" VARCHAR2(255 CHAR), 
	"APOYO_PUESTO" VARCHAR2(255 CHAR), 
	"APOYO_TELEFONO" VARCHAR2(255 CHAR), 
	"ASUNTO" VARCHAR2(255 CHAR), 
	"CARGO_INTERLOCUTOR" VARCHAR2(255 CHAR), 
	"CATEGORIA_INTERLOCUTOR" VARCHAR2(255 CHAR), 
	"CORREO_CORPORATIVO_INTER" VARCHAR2(255 CHAR), 
	"CORREO_CORPORATIVO_INTER_COMPL" VARCHAR2(255 CHAR), 
	"CORREO_DESTINATARIO" VARCHAR2(255 CHAR), 
	"DESCARGA_PLANTILLAS" VARCHAR2(255 CHAR), 
	"DESTINATARIO" VARCHAR2(255 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_CUMPLIMENTACION" TIMESTAMP (6), 
	"FECHA_ENVIO" TIMESTAMP (6), 
	"FECHA_FINALIZACION" TIMESTAMP (6), 
	"FECHA_LIMITE_CUMPLIMENTAR" TIMESTAMP (6), 
	"FECHA_LIMITE_ENVIO" TIMESTAMP (6), 
	"FECHA_NO_CONFORME" TIMESTAMP (6), 
	"FECHA_VALID_APOYO" TIMESTAMP (6), 
	"FECHA_VALID_JEFE_EQUIPO" TIMESTAMP (6), 
	"NOMBRE_COMPLETO_INTERLOCUTOR" VARCHAR2(255 CHAR), 
	"TELEFONO_INTERLOCUTOR" VARCHAR2(255 CHAR), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_ENVIO" VARCHAR2(255 CHAR), 
	"USERNAME_VALID_APOYO" VARCHAR2(255 CHAR), 
	"USERNAME_VALID_JEFE_EQUIPO" VARCHAR2(255 CHAR), 
	"USUARIO_FINALIZACION" VARCHAR2(255 CHAR), 
	"USUARIO_NO_CONFORME" VARCHAR2(255 CHAR), 
	"ID_INSPECCION" NUMBER(19,0)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_33
prompt =========================================================================
prompt Ejecutando creación de SUGERENCIA...

  CREATE TABLE "SUGERENCIA" 
   (	"ID_SUGERENCIA" NUMBER(10,0), 
	"DESCRIPCION" VARCHAR2(4000 CHAR), 
	"FECHA_CONTESTACION" TIMESTAMP (6), 
	"FECHA_REGISTRO" TIMESTAMP (6), 
	"MODULO" VARCHAR2(50 CHAR), 
	"USUARIO_CONTESTACION" VARCHAR2(255 CHAR), 
	"USUARIO_REGISTRO" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_34
prompt =========================================================================
prompt Ejecutando creación de TIPODOCUMENTACIONPREVIA...

  CREATE TABLE "TIPODOCUMENTACIONPREVIA" 
   (	"ID" NUMBER(19,0), 
	"AMBITO" VARCHAR2(10 CHAR), 
	"DESCRIPCION" VARCHAR2(255 CHAR), 
	"EXTENSIONES" VARCHAR2(255 CHAR), 
	"NOMBRE" VARCHAR2(255 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_35
prompt =========================================================================
prompt Ejecutando creación de TIPO_EQUIPO...

  CREATE TABLE "TIPO_EQUIPO" 
   (	"ID" NUMBER(19,0), 
	"CODIGO" VARCHAR2(5 CHAR), 
	"DESCRIPCION" VARCHAR2(100 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_36
prompt =========================================================================
prompt Ejecutando creación de TIPOS_INSPECCION...

  CREATE TABLE "TIPOS_INSPECCION" 
   (	"CODIGO" VARCHAR2(10 CHAR), 
	"DESCRIPCION" VARCHAR2(100 CHAR)
   ) ;
COMMIT;


prompt =========================================================================
prompt + Tarea2_37
prompt =========================================================================
prompt Ejecutando creación de USERS...

  CREATE TABLE "USERS" 
   (	"USERNAME" VARCHAR2(255 CHAR), 
	"PRIM_APELLIDO" VARCHAR2(50 CHAR), 
	"SEGUNDO_APELLIDO" VARCHAR2(50 CHAR), 
	"CATEGORIA" VARCHAR2(20 CHAR), 
	"CORREO" VARCHAR2(50 CHAR), 
	"DESPACHO" VARCHAR2(20 CHAR), 
	"DOC_IDENTIDAD" VARCHAR2(10 CHAR), 
	"ESTADO" VARCHAR2(8 CHAR), 
	"FECHA_ALTA" TIMESTAMP (6), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"FECHA_DESTINO_IPSS" TIMESTAMP (6), 
	"FECHA_INACTIVO" TIMESTAMP (6), 
	"FECHA_INGRESO" TIMESTAMP (6), 
	"FECHA_MODIFICACION" TIMESTAMP (6), 
	"NIVEL" NUMBER(10,0), 
	"NOMBRE" VARCHAR2(50 CHAR), 
	"PASSWORD" VARCHAR2(100 CHAR), 
	"ROLE" VARCHAR2(20 CHAR), 
	"TELEFONO" VARCHAR2(12 CHAR), 
	"TFNO_MOVIL_OFICIAL" VARCHAR2(12 CHAR), 
	"TFNO_MOVIL_PARTICULAR" VARCHAR2(12 CHAR), 
	"USERNAME_ALTA" VARCHAR2(255 CHAR), 
	"USERNAME_BAJA" VARCHAR2(255 CHAR), 
	"USERNAME_MODIF" VARCHAR2(255 CHAR), 
	"ID_CLASE" NUMBER(19,0), 
	"ID_CUERPO" NUMBER(10,0), 
	"ID_DEPARTAMENTO" NUMBER(19,0), 
	"ID_EMPLEO" NUMBER(19,0), 
	"ID_PUESTO" NUMBER(19,0)
   ) ;
 COMMIT; 
 
 prompt =========================================================================
prompt + Tarea2_38
prompt =========================================================================
prompt Ejecutando creación de PROVINCIAS... 

 CREATE TABLE "PROVINCIAS" 
   (	"CODIGO" VARCHAR2(3 CHAR) NOT NULL ENABLE, 
	"CODIGO_MN" VARCHAR2(10 CHAR), 
	"PROVINCIA" VARCHAR2(100 CHAR), 
	 PRIMARY KEY ("CODIGO")
   ) ;
   COMMIT;

prompt =========================================================================
prompt + Tarea2_39
prompt =========================================================================
prompt Ejecutando creación de MUNICIPIOS... 

 CREATE TABLE "MUNICIPIOS" 
   (	"ID" NUMBER(19,0) NOT NULL ENABLE, 
	"NAME" VARCHAR2(100 CHAR), 
	"CODE_PROVINCE" VARCHAR2(3 CHAR)
	);
  COMMIT;

prompt =========================================================================
prompt + Tarea2_40
prompt =========================================================================
prompt Ejecutando creación de AREAS_USUARIO_CUESTENV...

     CREATE TABLE "AREAS_USUARIO_CUESTENV" 
   (	"ID_AREA" NUMBER(19,0), 
	"ID_CUESTIONARIO_ENVIADO" NUMBER(19,0), 
	"USERNAME_PROV" VARCHAR2(255 CHAR), 
   );
COMMIT;

prompt =========================================================================
prompt + Tarea3
prompt =========================================================================
prompt Ejecutando creación de index y Constraints de datos...


prompt =========================================================================
prompt + Tarea3_1
prompt =========================================================================
prompt Ejecutando creación de index para RESPUESTAS_CUEST_DOCS...

CREATE UNIQUE INDEX "UK_RESPUESTASCUESTIONARIODOCS" ON "RESPUESTAS_CUEST_DOCS" ("ID_DOCUMENTO");


prompt =========================================================================
prompt + Tarea3_2
prompt =========================================================================
prompt Ejecutando creación de index para TIPO_EQUIPO...

  CREATE UNIQUE INDEX "UK_TIPOEQUIPO" ON "TIPO_EQUIPO" ("CODIGO") ;


prompt =========================================================================
prompt + Tarea3_3
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUERPOSESTADO...

   ALTER TABLE "CUERPOSESTADO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "CUERPOSESTADO" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "CUERPOSESTADO" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "CUERPOSESTADO" MODIFY ("DESCRIPCION" NOT NULL ENABLE);
  ALTER TABLE "CUERPOSESTADO" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_4
prompt =========================================================================
prompt Ejecutando creación de Constraints para PUESTOSTRABAJO...

  ALTER TABLE "PUESTOSTRABAJO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "PUESTOSTRABAJO" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "PUESTOSTRABAJO" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "PUESTOSTRABAJO" MODIFY ("ID" NOT NULL ENABLE);



prompt =========================================================================
prompt + Tarea3_5
prompt =========================================================================
prompt Ejecutando creación de Constraints para DOCUMENTOS...

  ALTER TABLE "DOCUMENTOS" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "DOCUMENTOS" MODIFY ("TIPO_CONTENIDO" NOT NULL ENABLE);
  ALTER TABLE "DOCUMENTOS" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_6
prompt =========================================================================
prompt Ejecutando creación de Constraints para RESPUESTASCUESTIONARIO...

  ALTER TABLE "RESPUESTASCUESTIONARIO" ADD PRIMARY KEY ("ID_CUEST_ENVIADO", "ID_PREGUNTA")
  USING INDEX  ENABLE;


prompt =========================================================================
prompt + Tarea3_7
prompt =========================================================================
prompt Ejecutando creación de Constraints para ALERTAS...

  ALTER TABLE "ALERTAS" ADD PRIMARY KEY ("ID_ALERTA")
  USING INDEX  ENABLE;
  ALTER TABLE "ALERTAS" MODIFY ("ID_ALERTA" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_8
prompt =========================================================================
prompt Ejecutando creación de Constraints para INSPECCIONES...

  ALTER TABLE "INSPECCIONES" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "INSPECCIONES" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "INSPECCIONES" MODIFY ("NUMERO" NOT NULL ENABLE);
  ALTER TABLE "INSPECCIONES" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "INSPECCIONES" MODIFY ("ID" NOT NULL ENABLE);

prompt =========================================================================
prompt + Tarea3_9
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIA_PERSONALIZADA_PASOS...

  ALTER TABLE "GUIA_PERSONALIZADA_PASOS" MODIFY ("ID_PASO_ELEGIDO" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PERSONALIZADA_PASOS" MODIFY ("ID_GUIA_PERS" NOT NULL ENABLE);

prompt =========================================================================
prompt + Tarea3_10
prompt =========================================================================
prompt Ejecutando creación de Constraints para PREGUNTASCUESTIONARIO...

  ALTER TABLE "PREGUNTASCUESTIONARIO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "PREGUNTASCUESTIONARIO" MODIFY ("PREGUNTA" NOT NULL ENABLE);
  ALTER TABLE "PREGUNTASCUESTIONARIO" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_11
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUESTIONARIOS_ENVIADOS...

  ALTER TABLE "CUESTIONARIOS_ENVIADOS" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("ID_INSPECCION" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("ID_CUESTIONARIO_PERSONALIZADO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("NOMBRE_USUARIO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("MOTIVO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("FECHA_LIMITE_CUESTIONARIO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("CORREO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" MODIFY ("ID" NOT NULL ENABLE);

prompt =========================================================================
prompt + Tarea3_12
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIA_PERSONALIZADA...  


  ALTER TABLE "GUIA_PERSONALIZADA" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "GUIA_PERSONALIZADA" MODIFY ("ID_MODELO_GUIA" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PERSONALIZADA" MODIFY ("USERNAME_CREACION" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PERSONALIZADA" MODIFY ("NOMBRE_GUIA_PERSONALIZADA" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PERSONALIZADA" MODIFY ("FECHA_CREACION" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PERSONALIZADA" MODIFY ("ID" NOT NULL ENABLE);  

prompt =========================================================================
prompt + Tarea3_13
prompt =========================================================================
prompt Ejecutando creación de Constraints para ALERTAS_NOTIFICACIONES_USUARIO...

  ALTER TABLE "ALERTAS_NOTIFICACIONES_USUARIO" ADD PRIMARY KEY ("USUARIO", "TIPO_MENSAJE", "ID_MENSAJE", "FECHA_ALTA", "NOMBRE_SECCION")
  USING INDEX  ENABLE;
  ALTER TABLE "ALERTAS_NOTIFICACIONES_USUARIO" MODIFY ("NOMBRE_SECCION" NOT NULL ENABLE);
  ALTER TABLE "ALERTAS_NOTIFICACIONES_USUARIO" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "ALERTAS_NOTIFICACIONES_USUARIO" MODIFY ("ID_MENSAJE" NOT NULL ENABLE);
  ALTER TABLE "ALERTAS_NOTIFICACIONES_USUARIO" MODIFY ("TIPO_MENSAJE" NOT NULL ENABLE);
  ALTER TABLE "ALERTAS_NOTIFICACIONES_USUARIO" MODIFY ("USUARIO" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_14
prompt =========================================================================
prompt Ejecutando creación de Constraints para CONFIG_RESPUESTAS_CUESTIONARIO...

  ALTER TABLE "CONFIG_RESPUESTAS_CUESTIONARIO" ADD PRIMARY KEY ("CLAVE", "SECCION", "VALOR")
  USING INDEX  ENABLE;
  ALTER TABLE "CONFIG_RESPUESTAS_CUESTIONARIO" MODIFY ("VALOR" NOT NULL ENABLE);
  ALTER TABLE "CONFIG_RESPUESTAS_CUESTIONARIO" MODIFY ("SECCION" NOT NULL ENABLE);
  ALTER TABLE "CONFIG_RESPUESTAS_CUESTIONARIO" MODIFY ("CLAVE" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_15
prompt =========================================================================
prompt Ejecutando creación de Constraints para DOCUMENTACION_PREVIA...

  ALTER TABLE "DOCUMENTACION_PREVIA" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "DOCUMENTACION_PREVIA" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_16
prompt =========================================================================
prompt Ejecutando creación de Constraints para MODELOSCUESTIONARIOS...

  ALTER TABLE "MODELOSCUESTIONARIOS" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "MODELOSCUESTIONARIOS" MODIFY ("DESCRIPCION" NOT NULL ENABLE);
  ALTER TABLE "MODELOSCUESTIONARIOS" MODIFY ("CODIGO" NOT NULL ENABLE);
  ALTER TABLE "MODELOSCUESTIONARIOS" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_17
prompt =========================================================================
prompt Ejecutando creación de Constraints para DEPARTAMENTO...

  ALTER TABLE "DEPARTAMENTO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "DEPARTAMENTO" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "DEPARTAMENTO" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "DEPARTAMENTO" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_18
prompt =========================================================================
prompt Ejecutando creación de Constraints para TIPOS_INSPECCION...

  ALTER TABLE "TIPOS_INSPECCION" ADD PRIMARY KEY ("CODIGO")
  USING INDEX  ENABLE;
  ALTER TABLE "TIPOS_INSPECCION" MODIFY ("CODIGO" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_19
prompt =========================================================================
prompt Ejecutando creación de Constraints para MIEMBROS...

  ALTER TABLE "MIEMBROS" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "MIEMBROS" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_20
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUESTIONARIO_PERSONALIZADO...

  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" MODIFY ("ID_MODELO_CUESTIONARIO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" MODIFY ("USERNAME_CREACION" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" MODIFY ("NOMBRE_CUESTIONARIO" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" MODIFY ("FECHA_CREACION" NOT NULL ENABLE);
  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_21
prompt =========================================================================
prompt Ejecutando creación de Constraints para USERS...

  ALTER TABLE "USERS" ADD PRIMARY KEY ("USERNAME")
  USING INDEX  ENABLE;
  ALTER TABLE "USERS" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("ROLE" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("NOMBRE" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("ESTADO" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("DOC_IDENTIDAD" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("CORREO" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("PRIM_APELLIDO" NOT NULL ENABLE);
  ALTER TABLE "USERS" MODIFY ("USERNAME" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_22
prompt =========================================================================
prompt Ejecutando creación de Constraints para TIPODOCUMENTACIONPREVIA...

  ALTER TABLE "TIPODOCUMENTACIONPREVIA" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "TIPODOCUMENTACIONPREVIA" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_23
prompt =========================================================================
prompt Ejecutando creación de Constraints para SOLICITUD_DOC_PREVIA...

  ALTER TABLE "SOLICITUD_DOC_PREVIA" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "SOLICITUD_DOC_PREVIA" MODIFY ("ID_INSPECCION" NOT NULL ENABLE);
  ALTER TABLE "SOLICITUD_DOC_PREVIA" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "SOLICITUD_DOC_PREVIA" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_24
prompt =========================================================================
prompt Ejecutando creación de Constraints para PARAMETROS...

  ALTER TABLE "PARAMETROS" ADD PRIMARY KEY ("CLAVE", "SECCION", "VALOR")
  USING INDEX  ENABLE;
  ALTER TABLE "PARAMETROS" MODIFY ("VALOR" NOT NULL ENABLE);
  ALTER TABLE "PARAMETROS" MODIFY ("SECCION" NOT NULL ENABLE);
  ALTER TABLE "PARAMETROS" MODIFY ("CLAVE" NOT NULL ENABLE)


prompt =========================================================================
prompt + Tarea3_25
prompt =========================================================================
prompt Ejecutando creación de Constraints para GESTDOCSOLICITUDDOCUMENTACION...

  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" MODIFY ("NOMBRE" NOT NULL ENABLE);
  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" MODIFY ("ID_DOCUMENTO" NOT NULL ENABLE);
  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" MODIFY ("EXTENSION" NOT NULL ENABLE);
  ALTER TABLE "GESTDOCSOLICITUDDOCUMENTACION" MODIFY ("ID" NOT NULL ENABLE);

  
 

prompt =========================================================================
prompt + Tarea3_26
prompt =========================================================================
prompt Ejecutando creación de Constraints para NOTIFICACIONES...

  ALTER TABLE "NOTIFICACIONES" ADD PRIMARY KEY ("ID_NOTIFICACION")
  USING INDEX  ENABLE;
  ALTER TABLE "NOTIFICACIONES" MODIFY ("FECHA_NOTIFICACION" NOT NULL ENABLE);
  ALTER TABLE "NOTIFICACIONES" MODIFY ("ID_NOTIFICACION" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_27
prompt =========================================================================
prompt Ejecutando creación de Constraints para RESPUESTA_DATOS_TABLA...

  ALTER TABLE "RESPUESTA_DATOS_TABLA" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "RESPUESTA_DATOS_TABLA" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_28
prompt =========================================================================
prompt Ejecutando creación de Constraints para CLASE_USUARIO...

  ALTER TABLE "CLASE_USUARIO" ADD PRIMARY KEY ("ID_CLASE")
  USING INDEX  ENABLE;
  ALTER TABLE "CLASE_USUARIO" MODIFY ("ID_CLASE" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_29
prompt =========================================================================
prompt Ejecutando creación de Constraints para EMPLEO...

  ALTER TABLE "EMPLEO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "EMPLEO" MODIFY ("ID" NOT NULL ENABLE);



prompt =========================================================================
prompt + Tarea3_30
prompt =========================================================================
prompt Ejecutando creación de Constraints para DOCUMENTOS_BLOB...

  ALTER TABLE "DOCUMENTOS_BLOB" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "DOCUMENTOS_BLOB" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_31
prompt =========================================================================
prompt Ejecutando creación de Constraints para REG_ACTIVIDAD...

  ALTER TABLE "REG_ACTIVIDAD" ADD PRIMARY KEY ("REG_ACTIVIDAD")
  USING INDEX  ENABLE;
  ALTER TABLE "REG_ACTIVIDAD" MODIFY ("REG_ACTIVIDAD" NOT NULL ENABLE);

prompt =========================================================================
prompt + Tarea3_32
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIA_PASOS...


  ALTER TABLE "GUIA_PASOS" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "GUIA_PASOS" MODIFY ("PASO" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PASOS" MODIFY ("ORDEN" NOT NULL ENABLE);
  ALTER TABLE "GUIA_PASOS" MODIFY ("ID" NOT NULL ENABLE);  
  
  
prompt =========================================================================
prompt + Tarea3_33
prompt =========================================================================
prompt Ejecutando creación de Constraints para TIPO_EQUIPO...

  ALTER TABLE "TIPO_EQUIPO" ADD CONSTRAINT "UK_TIPOEQUIPO" UNIQUE ("CODIGO")
  USING INDEX  ENABLE;
  ALTER TABLE "TIPO_EQUIPO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "TIPO_EQUIPO" MODIFY ("ID" NOT NULL ENABLE);



prompt =========================================================================
prompt + Tarea3_34
prompt =========================================================================
prompt Ejecutando creación de Constraints para AREASCUESTIONARIO...

  ALTER TABLE "AREASCUESTIONARIO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "AREASCUESTIONARIO" MODIFY ("NOMBRE_AREA" NOT NULL ENABLE);
  ALTER TABLE "AREASCUESTIONARIO" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_35
prompt =========================================================================
prompt Ejecutando creación de Constraints para SUGERENCIA...

  ALTER TABLE "SUGERENCIA" ADD PRIMARY KEY ("ID_SUGERENCIA")
  USING INDEX  ENABLE;
  ALTER TABLE "SUGERENCIA" MODIFY ("ID_SUGERENCIA" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_36
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIAS...

  ALTER TABLE "GUIAS" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "GUIAS" MODIFY ("TIPO_INSPECCION" NOT NULL ENABLE);
  ALTER TABLE "GUIAS" MODIFY ("USUARIO_CREACION" NOT NULL ENABLE);
  ALTER TABLE "GUIAS" MODIFY ("NOMBRE_GUIA" NOT NULL ENABLE);
  ALTER TABLE "GUIAS" MODIFY ("FECHA_CREACION" NOT NULL ENABLE);
  ALTER TABLE "GUIAS" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_37
prompt =========================================================================
prompt Ejecutando creación de Constraints para RESPUESTAS_CUEST_DOCS...

  ALTER TABLE "RESPUESTAS_CUEST_DOCS" ADD CONSTRAINT "UK_RESPUESTASCUESTIONARIODOCS" UNIQUE ("ID_DOCUMENTO")
  USING INDEX  ENABLE;
  ALTER TABLE "RESPUESTAS_CUEST_DOCS" MODIFY ("ID_DOCUMENTO" NOT NULL ENABLE);
  ALTER TABLE "RESPUESTAS_CUEST_DOCS" MODIFY ("ID_PREGUNTA" NOT NULL ENABLE);
  ALTER TABLE "RESPUESTAS_CUEST_DOCS" MODIFY ("ID_CUESTIONARIO_ENVIADO" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_38
prompt =========================================================================
prompt Ejecutando creación de Constraints para EQUIPO...

  ALTER TABLE "EQUIPO" ADD PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "EQUIPO" MODIFY ("USERNAME_ALTA" NOT NULL ENABLE);
  ALTER TABLE "EQUIPO" MODIFY ("NOMBRE_EQUIPO" NOT NULL ENABLE);
  ALTER TABLE "EQUIPO" MODIFY ("JEFE_EQUIPO" NOT NULL ENABLE);
  ALTER TABLE "EQUIPO" MODIFY ("FECHA_ALTA" NOT NULL ENABLE);
  ALTER TABLE "EQUIPO" MODIFY ("ID" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_39
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUEST_PER_PREGUNTAS...

  ALTER TABLE "CUEST_PER_PREGUNTAS" MODIFY ("ID_PREG_ELEGIDA" NOT NULL ENABLE);
  ALTER TABLE "CUEST_PER_PREGUNTAS" MODIFY ("ID_CUEST_PERS" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea3_40
prompt =========================================================================
prompt Ejecutando creación de Constraints para AREASCUESTIONARIO...

  ALTER TABLE "AREASCUESTIONARIO" ADD CONSTRAINT "FK_AREAS_MODELOSCUESTIONARIOS" FOREIGN KEY ("ID_CUESTIONARIO")
	  REFERENCES "MODELOSCUESTIONARIOS" ("ID") ENABLE;



prompt =========================================================================
prompt + Tarea3_41
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUESTIONARIO_PERSONALIZADO...

  ALTER TABLE "CUESTIONARIO_PERSONALIZADO" ADD CONSTRAINT "FK_PERSONALIZADOS_MODELOSCUESTIONARIOS" FOREIGN KEY ("ID_MODELO_CUESTIONARIO")
	  REFERENCES "MODELOSCUESTIONARIOS" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_42
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUESTIONARIOS_ENVIADOS...

  ALTER TABLE "CUESTIONARIOS_ENVIADOS" ADD CONSTRAINT "FK_INSPECCIONES_CUESTIONARIOS" FOREIGN KEY ("ID_INSPECCION")
	  REFERENCES "INSPECCIONES" ("ID") ENABLE;
  ALTER TABLE "CUESTIONARIOS_ENVIADOS" ADD CONSTRAINT "FK_ENVIADOS_PERSONALIZADOS" FOREIGN KEY ("ID_CUESTIONARIO_PERSONALIZADO")
	  REFERENCES "CUESTIONARIO_PERSONALIZADO" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_43
prompt =========================================================================
prompt Ejecutando creación de Constraints para CUEST_PER_PREGUNTAS...

  ALTER TABLE "CUEST_PER_PREGUNTAS" ADD CONSTRAINT "FK_PREGUNTAS_CUESTIONARIOPERSONALIZADO" FOREIGN KEY ("ID_PREG_ELEGIDA")
	  REFERENCES "PREGUNTASCUESTIONARIO" ("ID") ENABLE;
  ALTER TABLE "CUEST_PER_PREGUNTAS" ADD CONSTRAINT "FK_CUESTIONARIOPERSONALIZADO_PREGUNTAS" FOREIGN KEY ("ID_CUEST_PERS")
	  REFERENCES "CUESTIONARIO_PERSONALIZADO" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_44
prompt =========================================================================
prompt Ejecutando creación de Constraints para DOCUMENTOS...

  ALTER TABLE "DOCUMENTOS" ADD CONSTRAINT "FK_DOCUMENTOSBLOB" FOREIGN KEY ("ID_FICHERO")
	  REFERENCES "DOCUMENTOS_BLOB" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_45
prompt =========================================================================
prompt Ejecutando creación de Constraints para EMPLEO...

  ALTER TABLE "EMPLEO" ADD CONSTRAINT "FK_CUERPOESTADO" FOREIGN KEY ("ID_CUERPO")
	  REFERENCES "CUERPOSESTADO" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_46
prompt =========================================================================
prompt Ejecutando creación de Constraints para EQUIPO...

  ALTER TABLE "EQUIPO" ADD CONSTRAINT "FK_TIPOEQUIPO" FOREIGN KEY ("ID_TIPO_EQUIPO")
	  REFERENCES "TIPO_EQUIPO" ("ID") ENABLE;

prompt =========================================================================
prompt + Tarea3_47
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIA_PASOS...	  


   ALTER TABLE "GUIA_PASOS" ADD CONSTRAINT "FK_GUIA" FOREIGN KEY ("ID_GUIA")
	  REFERENCES "GUIAS" ("ID") ENABLE;

prompt =========================================================================
prompt + Tarea3_48
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIA_PERSONALIZADA...


  ALTER TABLE "GUIA_PERSONALIZADA" ADD CONSTRAINT "FK_GUIA_PERSONALIZADA" FOREIGN KEY ("ID_MODELO_GUIA")
	  REFERENCES "GUIAS" ("ID") ENABLE;
  ALTER TABLE "GUIA_PERSONALIZADA" ADD CONSTRAINT "FK_GUIA_INSPECCIONES" FOREIGN KEY ("INSPECCION")
	  REFERENCES "INSPECCIONES" ("ID") ENABLE;

prompt =========================================================================
prompt + Tarea3_49
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIA_PERSONALIZADA_PASOS...

  ALTER TABLE "GUIA_PERSONALIZADA_PASOS" ADD CONSTRAINT "FK_PASOS_GUIAPERSONALIZADA" FOREIGN KEY ("ID_GUIA_PERS")
	  REFERENCES "GUIA_PERSONALIZADA" ("ID") ENABLE;
  ALTER TABLE "GUIA_PERSONALIZADA_PASOS" ADD CONSTRAINT "FK_GUIAPERSONALIZADA_PASOS" FOREIGN KEY ("ID_PASO_ELEGIDO")
	  REFERENCES "GUIA_PASOS" ("ID") ENABLE;

prompt =========================================================================
prompt + Tarea3_50
prompt =========================================================================
prompt Ejecutando creación de Constraints para GUIAS...	  

  ALTER TABLE "GUIAS" ADD CONSTRAINT "FK_TIPOINSPECCION" FOREIGN KEY ("TIPO_INSPECCION")
	  REFERENCES "TIPOS_INSPECCION" ("CODIGO") ENABLE;	  
	  

prompt =========================================================================
prompt + Tarea3_51
prompt =========================================================================
prompt Ejecutando creación de Constraints para INSPECCIONES...

  ALTER TABLE "INSPECCIONES" ADD CONSTRAINT "FK_TIPO" FOREIGN KEY ("TIPO_INSPECCION")
	  REFERENCES "TIPOS_INSPECCION" ("CODIGO") ENABLE;
  ALTER TABLE "INSPECCIONES" ADD CONSTRAINT "FK_EQUIPO" FOREIGN KEY ("ID_EQUIPO")
	  REFERENCES "EQUIPO" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_52
prompt =========================================================================
prompt Ejecutando creación de Constraints para MIEMBROS...

  ALTER TABLE "MIEMBROS" ADD CONSTRAINT "FK_MIEMBROS_EQUIPO" FOREIGN KEY ("ID_EQUIPO")
	  REFERENCES "EQUIPO" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_53
prompt =========================================================================
prompt Ejecutando creación de Constraints para PREGUNTASCUESTIONARIO...

  ALTER TABLE "PREGUNTASCUESTIONARIO" ADD CONSTRAINT "FK_PREGUNTAS_AREA" FOREIGN KEY ("ID_AREA")
	  REFERENCES "AREASCUESTIONARIO" ("ID") ENABLE;



prompt =========================================================================
prompt + Tarea3_55
prompt =========================================================================
prompt Ejecutando creación de Constraints para RESPUESTA_DATOS_TABLA...

  ALTER TABLE "RESPUESTA_DATOS_TABLA" ADD CONSTRAINT "FK_RESPUESTAS" FOREIGN KEY ("RESPUESTA_ID_CUEST_ENVIADO", "RESPUESTA_ID_PREGUNTA")
	  REFERENCES "RESPUESTASCUESTIONARIO" ("ID_CUEST_ENVIADO", "ID_PREGUNTA") ENABLE;


prompt =========================================================================
prompt + Tarea3_56
prompt =========================================================================
prompt Ejecutando creación de Constraints para RESPUESTAS_CUEST_DOCS...

  ALTER TABLE "RESPUESTAS_CUEST_DOCS" ADD CONSTRAINT "FK_DOCUMENTOS" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "DOCUMENTOS" ("ID") ENABLE;
  ALTER TABLE "RESPUESTAS_CUEST_DOCS" ADD CONSTRAINT "FK_RESPUESTASCUESTIONARIO" FOREIGN KEY ("ID_CUESTIONARIO_ENVIADO", "ID_PREGUNTA")
	  REFERENCES "RESPUESTASCUESTIONARIO" ("ID_CUEST_ENVIADO", "ID_PREGUNTA") ENABLE;


prompt =========================================================================
prompt + Tarea3_57
prompt =========================================================================
prompt Ejecutando creación de Constraints para RESPUESTASCUESTIONARIO...

  ALTER TABLE "RESPUESTASCUESTIONARIO" ADD CONSTRAINT "FK_CUESTIONARIOENVIADO_RESPUESTAS" FOREIGN KEY ("ID_CUEST_ENVIADO")
	  REFERENCES "CUESTIONARIOS_ENVIADOS" ("ID") ENABLE;
  ALTER TABLE "RESPUESTASCUESTIONARIO" ADD CONSTRAINT "FK_RESPUESTAS_CUESTIONARIOENVIADO" FOREIGN KEY ("ID_PREGUNTA")
	  REFERENCES "PREGUNTASCUESTIONARIO" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_58
prompt =========================================================================
prompt Ejecutando creación de Constraints para SOLICITUD_DOC_PREVIA...

  ALTER TABLE "SOLICITUD_DOC_PREVIA" ADD CONSTRAINT "FK_INSPECCIONES_SOLICITUD" FOREIGN KEY ("ID_INSPECCION")
	  REFERENCES "INSPECCIONES" ("ID") ENABLE;


prompt =========================================================================
prompt + Tarea3_59
prompt =========================================================================
prompt Ejecutando creación de Constraints para USERS...

  ALTER TABLE "USERS" ADD CONSTRAINT "FK_USER_CUERPOESTADO" FOREIGN KEY ("ID_CUERPO")
	  REFERENCES "CUERPOSESTADO" ("ID") ENABLE;
  ALTER TABLE "USERS" ADD CONSTRAINT "FK_USER_EMPLEO" FOREIGN KEY ("ID_EMPLEO")
	  REFERENCES "EMPLEO" ("ID") ENABLE;
  ALTER TABLE "USERS" ADD CONSTRAINT "FK_USER_DEPARTAMENTO" FOREIGN KEY ("ID_DEPARTAMENTO")
	  REFERENCES "DEPARTAMENTO" ("ID") ENABLE;
  ALTER TABLE "USERS" ADD CONSTRAINT "FK_USER_CLASE" FOREIGN KEY ("ID_CLASE")
	  REFERENCES "CLASE_USUARIO" ("ID_CLASE") ENABLE;
  ALTER TABLE "USERS" ADD CONSTRAINT "FK_USER_PUESTO" FOREIGN KEY ("ID_PUESTO")
	  REFERENCES "PUESTOSTRABAJO" ("ID") ENABLE;
	  
	  prompt =========================================================================
prompt + Tarea3_50
prompt =========================================================================
prompt Ejecutando creación de Constraints para PROVINCIAS...

ALTER TABLE "PROVINCIAS" ADD PRIMARY KEY ("CODIGO") USING INDEX  ENABLE;
ALTER TABLE "PROVINCIAS" MODIFY ("CODIGO_MN" NOT NULL ENABLE);
ALTER TABLE "PROVINCIAS" MODIFY ("PROVINCIA" NOT NULL ENABLE);
COMMIT;

prompt =========================================================================
prompt + Tarea3_51
prompt =========================================================================
prompt Ejecutando creación de Constraints para MUNICIPIOS...

ALTER TABLE "MUNICIPIOS" ADD PRIMARY KEY ("ID") USING INDEX  ENABLE;
ALTER TABLE "MUNICIPIOS" MODIFY ("NAME" NOT NULL ENABLE);
ALTER TABLE "MUNICIPIOS" MODIFY ("CODE_PROVINCE" NOT NULL ENABLE); 
ALTER TABLE "MUNICIPIOS" ADD  CONSTRAINT "FK_PROVINCIA" FOREIGN KEY ("CODE_PROVINCE")
	  REFERENCES "PROVINCIAS" ("CODIGO") ENABLE;


prompt =========================================================================
prompt + Tarea3_52
prompt =========================================================================
prompt Ejecutando creación de Constraints para AREAS_USUARIO_CUESTENV...

ALTER TABLE "AREAS_USUARIO_CUESTENV" ADD PRIMARY KEY ("ID_AREA", "ID_CUESTIONARIO_ENVIADO") USING INDEX  ENABLE;
ALTER TABLE "AREAS_USUARIO_CUESTENV" MODIFY ("ID_AREA" NOT NULL ENABLE);
ALTER TABLE "AREAS_USUARIO_CUESTENV" MODIFY ("ID_CUESTIONARIO_ENVIADO" NOT NULL ENABLE);


prompt =========================================================================
prompt + Tarea4
prompt =========================================================================
prompt Ejecutando inserción de datos...


prompt =========================================================================
prompt + Tarea4_1
prompt =========================================================================
prompt Ejecutando inserción de datos CUERPOSESTADO...

Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('1','Policía Nacional',SYSDATE,null,null,'PN','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('2','Guardia Civil',SYSDATE,null,null,'GC','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('3','Cuerpo General de la Administración',SYSDATE,null,null,'CGA','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('4','Cuerpos Comunes de la Defensa',SYSDATE,null,null,'CCD','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('5','Contratados',SYSDATE,null,null,'CONTR','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('6','Correos y Telégrafos',SYSDATE,null,null,'CT','system',null,null);
COMMIT;


prompt =========================================================================
prompt + Tarea4_2
prompt =========================================================================
prompt Ejecutando inserción de datos CLASE_USUARIO...

Insert into CLASE_USUARIO (ID_CLASE,CLASE) values ('1','FCSE');
Insert into CLASE_USUARIO (ID_CLASE,CLASE) values ('2','FCSE-FC');
Insert into CLASE_USUARIO (ID_CLASE,CLASE) values ('3','RPT');
Insert into CLASE_USUARIO (ID_CLASE,CLASE) values ('4','RPT-FC');
COMMIT;


prompt =========================================================================
prompt + Tarea4_3
prompt =========================================================================
prompt Ejecutando inserción de datos CONFIG_RESPUESTAS_CUESTIONARIO...

Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('1','ADJUNTO','1');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('1','INPUT','1');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('1','TEXTAREA','1');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('ACEPTABLE','RADIOCALIDAD','Aceptable');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('BUENO','RADIOCALIDAD','Bueno');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('DEFICIENTE','RADIOCALIDAD','Deficiente');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('NO','RADIOSINO','No');
Insert into CONFIG_RESPUESTAS_CUESTIONARIO (CLAVE,SECCION,VALOR) values ('SI','RADIOSINO','Sí');
COMMIT;



prompt =========================================================================
prompt + Tarea4_4
prompt =========================================================================
prompt Ejecutando inserción de datos DEPARTAMENTO...

Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('1','Subdirección General de Inspección y Servicios',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('2','Secretaría Técnica',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('3','Jefatura de Inspecciones',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('4','Equipos de Inspecciones Generales',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('5','Equipo de Inspecciones Temáticas y PRL',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('6','Gabinete de Estudios y Análisis',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('7','Servicio de Calidad y Quejas',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('8','Servicio de Apoyo',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('9','Secretaría',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('10','Asesores',SYSDATE,null,null,'system',null,null);
Insert into DEPARTAMENTO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('11','Conductores',SYSDATE,null,null,'system',null,null);
COMMIT;


prompt =========================================================================
prompt + Tarea4_5
prompt =========================================================================
prompt Ejecutando inserción de datos DOCUMENTOS_BLOB...

DECLARE
	l_bfile  BFILE;
	l_blob   BLOB;
BEGIN

	EXECUTE IMMEDIATE 'CREATE OR REPLACE DIRECTORY PLANTILLAS_PROGESIN AS ''C:/Plantillas''';

	INSERT INTO documentos_blob (id, fichero) VALUES ('1', empty_blob()) RETURN fichero INTO l_blob;
		l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_C.xlsx');
		DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
		DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
		DBMS_LOB.CLOSE(l_bfile);
		
	INSERT INTO documentos_blob (id, fichero) VALUES ('2', empty_blob()) RETURN fichero INTO l_blob;
		l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_CIA.xlsx');
		DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
		DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
		DBMS_LOB.CLOSE(l_bfile);
		
	INSERT INTO documentos_blob (id, fichero) VALUES ('3', empty_blob()) RETURN fichero INTO l_blob;
		l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_Z.xlsx');
		DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
		DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
		DBMS_LOB.CLOSE(l_bfile);

	EXECUTE IMMEDIATE 'DROP DIRECTORY PLANTILLAS_PROGESIN';
	
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
COMMIT;


prompt =========================================================================
prompt + Tarea4_6
prompt =========================================================================
prompt Ejecutando inserción de datos DOCUMENTOS...

Insert into documentos (id, id_fichero, tipo_contenido, nombre) values ('1', '1','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_C.xlsx');
Insert into documentos (id, id_fichero, tipo_contenido, nombre) values ('2', '2','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_CIA.xlsx');
Insert into documentos (id, id_fichero, tipo_contenido, nombre) values ('3', '3','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_Z.xlsx');
COMMIT;


prompt =========================================================================
prompt + Tarea4_7
prompt =========================================================================
prompt Ejecutando inserción de datos EMPLEO...

Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('1','Teniente General','Tte. Gral.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('2','General de División','Gral. Div.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('3','General de Brigada','Gral. Bri.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('4','Coronel','Col.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('5','Teniente Coronel','TCol.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('6','Comandante','Comte.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('7','Capitán','Ctan.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('8','Teniente','Tte.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('9','Alférez','Alfz.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('10','Suboficial Mayor','Subof. May.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('11','Subteniente','Subte.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('12','Brigada','Bgda.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('13','Sargento Primero','Sgto.1','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('14','Sargento','Sgto.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('15','Cabo Mayor','Cab.1','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('16','Cabo','Cab.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('17','Guardia Civil','Gua.Civ.','2');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('18','Comisario Principal','Com. Pral','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('19','Comisario','Com.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('20','Inspector Jefe','Insp.J.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('21','Inspector','Insp.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('22','Facultativo','Facul.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('23','Subinspector','Subins.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('24','Oficial de Policía','Ofi.Pol.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('25','Policía','Pol.','1');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('26','General de Brigada','Gral. Brig.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('27','Coronel','Col.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('28','Teniente Coronel','TCol.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('29','Comandante','Comte.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('30','Capitán','Ctan.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('31','Teniente','Tte.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('32','Alférez','Alf.','4');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('33','Técnico de la Administración Central','TecAdmCent.','3');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('34','Técnico Superior de Sistemas','TecSupSis.','3');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('35','Facultativo','Facul.','3');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('36','Secretaría','Secre.','3');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('37','Administrativo','Adminis.','3');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('38','Auxiliar Administrativo','AuxAdminis.','3');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('39','Técnico','Tec.','5');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('40','Administrativo','Adminis.','5');
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values ('41','Ejecutivo Post. Y de Telecomunicaciones','CEPyT.','6');
COMMIT;


prompt =========================================================================
prompt + Tarea4_8
prompt =========================================================================
prompt Ejecutando inserción de datos TIPO_EQUIPO...

Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('1','IAPRL','Inspecciones Área Prevención de Riesgos Laborales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('2','IG','Inspecciones Generales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('3','IS','Inspecciones de Seguimiento');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('4','II','Inspecciones Incidentales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('5','IPRL','Inspecciones Prevención de Riesgos Laborales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('6','CIES','Inspecciones Temáticas (CIES).');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('7','SCQ','Servicio de Calidad y Quejas');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values ('8','SO','Secretaria Y Otros');
COMMIT;


prompt =========================================================================
prompt + Tarea4_9
prompt =========================================================================
prompt Ejecutando inserción de datos TIPODOCUMENTACIONPREVIA...

Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('1','GC','Actas de las Comisiones Provinciales de Seguridad Privada de los años 2014 y 2015','PDF','ACPSP');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('2','PN','Actas de las Comisiones Provinciales de Seguridad Privada de los años 2014 y 2015','PDF','ACPSP');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('3','GC','Actas de las Comisiones de Coordinación de Policía Judicial de los años 2014 y 2015','PDF','ACCPJ');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('4','PN','Actas de las Comisiones de Coordinación de Policía Judicial de los años 2014 y 2015','PDF','ACCPJ');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('5','GC','Actas de las Juntas Locales de Seguridad de los años 2014 y 2015','PDF','AJLS');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('6','PN','Actas de las Juntas Locales de Seguridad de los años 2014 y 2015','PDF','AJLS');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('7','GC','Actas de las Juntas de Coordinación de 2015','PDF','AJC');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('8','GC','Catálogo de Puestos de Trabajo (última aprobación CECIR) y fuerza en revista','XLS, XLSX','CPT');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('9','GC','Convenios de colaboración con Policías Locales (tanto genéricos como específicos)','PDF','CCPL');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('10','PN','Convenios de colaboración con Policías Locales (tanto genéricos como específicos)','PDF','CCPL');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('11','PN','Copia de las actas de las reuniones sindicales celebradas en 2014 y 2015','PDF','CAS');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('12','PN','Documentación relativa al Plan Territorial. Objetivos operativos fijados para 2015','PDF','PTyO');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('13','PN','Evaluación de Riesgos Laborales','PDF','PRL');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('14','GC','Libro de Organización y Libro de Normas de Régimen Interno','PDF','LOYRI');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('15','GC','Mapa con la distribución territorial de sus Unidades subordinadas.','JPG, JPEG, PNG, BMP','mapa');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('16','GC','Memoria Anual de 2015','PDF','MA');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('17','PN','Memoria Anual de 2015','PDF','MA');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('18','GC','Organigrama de la Comandancia (Unidades Territoriales y Especialidades)','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('19','GC','Organigrama de la Compañía (Unidades Territoriales y Especialidades)','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('20','GC','Organigrama de la Zona (Unidades Territoriales y Especialidades)','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('21','PN','Organigramas de la Unidad, con detalle de sus Unidades y Servicios','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('22','GC','Plan anual de la Unidad Orgánica de Policía Judicial vigente','PDF','PAUOPJ');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('23','GC','Plan de Emergencia','PDF','PE');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('24','PN','Plan de Emergencia','PDF','PE');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('25','GC','Plan de Seguridad Ciudadana','PDF','PSC');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('26','GC','Relación de mandos de las distintas Unidades, de categoría Oficial, con indicación de teléfonos y correos electrónicos de contacto','DOC, DOCX, PDF','RM');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('27','GC','Relación de municipios que configuran la demarcación de la compañía','DOC, DOCX','RMD');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values ('28','PN','Relación del Equipo Directivo con indicación del nombre y apellidos, categoría y cargo, números de teléfono y dirección de correo electrónico','DOC, DOCX','RM');
COMMIT;


prompt =========================================================================
prompt + Tarea4_10
prompt =========================================================================
prompt Ejecutando inserción de datos TIPOS_INSPECCION...

Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.G.P.','I.Gral.Periodica');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.G.S.','I.Gral.Seguimiento');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.T_PRL','I.Temática PRL');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.T_CIE','I.Temática CIE');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.T_OT','I.Temática otros');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.E_INCD','I. Incidental extraordinaria');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.E_PRL','I.PRL extraordinaria');
Insert into TIPOS_INSPECCION (CODIGO,DESCRIPCION) values ('I.E_PUNT','I.Puntual extraordinaria');
COMMIT;


prompt =========================================================================
prompt + Tarea4_11
prompt =========================================================================
prompt Ejecutando inserción de datos PARAMETROS...

Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoCorreo','datosApoyo','mmayo@interior.es');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoNombre','datosApoyo','Manuel Mayo Rodríguez');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoPuesto','datosApoyo','Inspector Auditor, Jefe del Servicio de Apoyo');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoTelefono','datosApoyo','91.537.25.41');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('BMP','extensiones','image/bmp');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('DOC','extensiones','application/msword');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('DOCX','extensiones','application/vnd.openxmlformats-officedocument.wordprocessingml.document');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('GC','URLPROGESIN','http://194.224.253.45:8080/progesin');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('JPEG','extensiones','image/jpeg');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('OTROS','URLPROGESIN','http://194.224.253.45:8080/progesin');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PDF','extensiones','application/pdf');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PN','URLPROGESIN','http://194.224.253.45:8080/progesin');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PNG','extensiones','image/png');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PPT','extensiones','application/vnd.ms-powerpoint');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PPTX','extensiones','application/vnd.openxmlformats-officedocument.presentationml.presentation');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PUB','extensiones','application/x-mspublisher');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('UserPwd','mail','ipss2016');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('XLS','extensiones','application/vnd.ms-excel');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('XLSX','extensiones','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('dominiosCorreo','dominiosCorreo','ezentis.com|interior.es|policia.es|dgp.mir.es|guardiacivil.org|guardiacivil.es');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.auth','mail','true');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.host','mail','smtp.gmail.com');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.port','mail','587');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.ssl.trust','mail','smtp.gmail.com');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.starttls.enable','mail','true');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.user','mail','progesinipss@gmail.com');
insert into parametros(seccion, clave, valor) values ('plantillasGC','Comandancia', '1');
insert into parametros(seccion, clave, valor) values ('plantillasGC','Compañía', '2');
insert into parametros(seccion, clave, valor) values ('plantillasGC','Zona', '3');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plazoDiasCuestionario','tareas','5');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plazoDiasDocumentacion','tareas','5');
COMMIT;


prompt =========================================================================
prompt + Tarea4_12
prompt =========================================================================
prompt Ejecutando inserción de datos PUESTOSTRABAJO...

Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('1','Subdirector General',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('2','Secretario Técnico',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('3','Jefe de Servicios de Inspección',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('4','Responsable Estudios y Programas',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('5','Jefe de Equipo Inspecciones',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('6','Inspector-Auditor',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('7','Jefe de Apoyo',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('8','Jefe de Sección',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('9','Personal de Apoyo',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('10','Jefe de Negociado',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('11','Fuera de Catálogo (Conductores)',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values ('12','Secretaria de N30',SYSDATE,null,null,'system',null,null);
COMMIT;


prompt =========================================================================
prompt + Tarea4_13
prompt =========================================================================
prompt Ejecutando inserción de datos USERS...

Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('acabrero','CABRERO','GARCIA','correo@guardiacivil.es','n16716','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 04:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','ANDRES','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'user_alta',null,null,'2','8','17','6','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('system','system','system','correo@guardiacivil.es','111111111','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'20','System','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'user_alta',null,null,'2','8','17','1','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ajgarcia','GARCIA','ABASCAL','correo@guardiacivil.es','n24519','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('22/06/20 09:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'21','ANGEL JESUS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','3','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('alopez','LOPEZ','BETRIAN','correo@guardiacivil.es','n10971','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/04/20 14:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'15','ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'3','8','36','5','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('alopezc','LOPEZ','CONTRERAS','correo@guardiacivil.es','n22162','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'4','8','36','7','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('amateos','MATEOS','CHICO','correo@guardiacivil.es','n17702','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 05:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','AMADEO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','6','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ajangulo','ANGULO','BALLARIN','correo@guardiacivil.es','n13453','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('12/01/20 09:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','ANTONIO JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','7','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('slaguia','LAGUIA','FERNANDEZ','correo@guardiacivil.es','n16490','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/03/20 10:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','SEGUNDO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'4','8','36','7','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('silpe','apellido1','apellido2','correo@guardiacivil.es','111111111','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'20','Silvia','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'user_alta',null,null,'2','8','17','1','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('pedro','CARRETERO','LIAU','correo@guardiacivil.es','111111111','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('05/06/20 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','PEDRO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_GABINETE','10323',null,null,'user_alta',null,null,'2','8','17','2','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('parribas','ARRIBAS','MARTINEZ','correo@guardiacivil.es','n16603','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/08/20 08:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','PEDRO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'4','8','36','7','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('parbona','ARBONA','CAMPOMAR','correo@guardiacivil.es','n25044','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('12/08/20 07:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'21','PEDRO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','665566544',null,null,'user_alta',null,null,'2','8','17','9','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mnavarro','NAVARRO','GRAELLS','correo@guardiacivil.es','n19959','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','MONTSERRAT','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','4','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mfgonzalez','GONZALEZ','FERNANDEZ','correo@guardiacivil.es','n22962','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','MARCELO FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','4','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jsastre','SASTRE','SANCHEZ','correo@guardiacivil.es','n24286','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,to_timestamp('17/01/17 12:46:22,488000000','DD/MM/RR HH24:MI:SS,FF'),'18','JOSE','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,'silpe','4','8','36','4','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jrosas','ROSAS','MARTIN','correo@guardiacivil.es','n19662','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 15:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','JOSE','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','4','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmartinez','MARTINEZ','GANDIA BANCES','correo@guardiacivil.es','n23093','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/12/20 09:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'4','8','36','4','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmanuel','SIERRA','EXOJO','correo@guardiacivil.es','n10704','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('22/06/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'20','JOSE MANUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'user_alta',null,null,'2','8','17','3','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmalberich','ALBERICH','LANDABURU','correo@guardiacivil.es','n22468','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('18/11/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JUAN MANUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','7','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jlcalonge','CALONGE','DELSO','correo@guardiacivil.es','n25583','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('22/06/20 09:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'21','JOSE LUIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','9','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jcgonzalez','GONZÁLEZ','VALENCIA','correo@guardiacivil.es','n22465','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/12/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JESUS CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'4','8','36','7','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ciibarguren','IBARGUREN','LEON','correo@guardiacivil.es','n21953','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/07/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'16','CARLOS IGNACIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'3','8','36','8','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('cjsancha','SANCHA','HERRERA','correo@guardiacivil.es','n21129','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','CARLOS JAIME','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','4','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('cperez','PEREZ','FERNANDEZ','correo@guardiacivil.es','n23958','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 11:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','CONSUELO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','4','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('egomez','GOMEZ','ALMERO','correo@guardiacivil.es','n13575','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/07/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'16','ENRIQUE','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'3','8','36','8','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('egomezcorino','CARRILES','CORINO','correo@guardiacivil.es','n21991','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/08/20 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,to_timestamp('17/01/17 12:31:30,227000000','DD/MM/RR HH24:MI:SS,FF'),'16','JOSE PEDRO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,'silpe','2','8','17','8','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('faconde','CONDE','FALCON','correo@guardiacivil.es','n14975','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/04/20 10:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','FRANCISCO ANDRES','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','7','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fdonday','DONDAY','GALVEZ','correo@guardiacivil.es','n22149','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('23/11/20 09:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','7','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fmartcasabas','MARTINEZ','CASBAS','correo@guardiacivil.es','n21694','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','4','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jaesteban','ESTEBAN','GOMEZ','correo@guardiacivil.es','n19570','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','JOSE ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','6','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('japeruyero','PERUYERO','MARTINEZ','correo@guardiacivil.es','n18440','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('11/08/20 12:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','JOSE ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','6','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jatristan','TRISTAN','OSTA','correo@guardiacivil.es','n21999','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/07/20 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,to_timestamp('17/01/17 12:45:59,857000000','DD/MM/RR HH24:MI:SS,FF'),'16','JESUS ANGEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,'silpe','4','8','36','8','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jbarnils','BARNILS','COSTA','correo@guardiacivil.es','n15416','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 15:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JUAN','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','7','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jcarranz','ARRANZ','APAOLAZA','correo@guardiacivil.es','n14189','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/05/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JUAN CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'2','8','17','7','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('msoria','SORIA','TORRALBA','correo@policia.es','n17620','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 02:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','MIGUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','6','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('amora','MORA','GALAN','correo@policia.es','n24388','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','ALBERTO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','4','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fsempre','SEMPERE','PEÑA','correo@policia.es','n22252','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/06/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','FERNANDO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','7','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jicerezo','CEREZO','HERNANDEZ','correo@policia.es','N25342','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('22/06/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'21','JOSE IGNACIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','9','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fjoliva','OLIVA','GARCIA','correo@policia.es','n22664','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 07:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','FRANCISCO JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','4','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jencuentra','ENCUENTRA','BAGUES','correo@policia.es','n13545','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('25/09/20 10:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JESUS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','7','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmcervantes','CERVANTES','PARDO','correo@policia.es','n22046','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JOSE MARIA','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','7','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('emartinez','MARTINEZ','ALLER','correo@policia.es','n18342','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','ELIAS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','6','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jcgalan','GALAN','VIVAR','correo@policia.es','n25655','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('22/04/20 13:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'21','JOSE CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','665566544',null,null,'user_alta',null,null,'1','8','25','9','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('maserrano','SERRANO','ARGUELLO','correo@policia.es','n20645','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 11:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'18','MIGUEL ANGEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','4','4');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmarquez','MARQUES','BERDEJO','correo@policia.es','N15432','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('11/08/20 10:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'user_alta',null,null,'1','8','25','7','3');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mafan','AFAN','FAJARDO','correo@policia.es','n24639','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('22/06/20 16:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'21','MIGUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','665566544',null,null,'user_alta',null,null,'1','8','25','3','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('sfcarrasco','CARRASCO','JIMENEZ','correo@policia.es','n16115','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 11:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','SALVADOR FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_JEFE_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','7','1');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('cgonzalez','GONZÁLEZ','CASTILLO','correo@policia.es','n15967','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('01/09/20 15:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'27','CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','7','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fvilchews','VILCHES','RENTERO','correo@policia.es','n11718','A1',null,'ACTIVO',SYSDATE,null,to_timestamp('17/04/20 15:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'),null,null,null,'28','FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','665566544',null,null,'user_alta',null,null,'1','8','25','6','4');

prompt =========================================================================
prompt + Tarea4_14
prompt =========================================================================
prompt Ejecutando inserción de datos EQUIPO...

Insert into EQUIPO (ID,FECHA_ALTA,FECHA_BAJA,JEFE_EQUIPO,NOMBRE_EQUIPO,NOMBRE_JEFE,USERNAME_ALTA,USERNAME_BAJA,ID_TIPO_EQUIPO) values ('1',SYSDATE,null,'silpe','Equipo A','Silvia apellido1 apellido2','silpe',null,'1');
Insert into EQUIPO (ID,FECHA_ALTA,FECHA_BAJA,JEFE_EQUIPO,NOMBRE_EQUIPO,NOMBRE_JEFE,USERNAME_ALTA,USERNAME_BAJA,ID_TIPO_EQUIPO) values ('2',SYSDATE,null,'cgonzalez','Equipo B','CARLOS GONZÁLEZ CASTILLO','silpe',null,'2');
Insert into EQUIPO (ID,FECHA_ALTA,FECHA_BAJA,JEFE_EQUIPO,NOMBRE_EQUIPO,NOMBRE_JEFE,USERNAME_ALTA,USERNAME_BAJA,ID_TIPO_EQUIPO) values ('3',SYSDATE,null,'mfgonzalez','Equipo C','MARCELO FRANCISCO GONZALEZ FERNANDEZ','silpe',null,'3');

prompt =========================================================================
prompt + Tarea4_15
prompt =========================================================================
prompt Ejecutando inserción de datos MIEMBROS...

Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('1','Silvia apellido1 apellido2','JEFE_EQUIPO','silpe','1');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('2','PEDRO  CARRETERO LIAU','MIEMBRO','pedro','1');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('3','JOSE MANUEL SIERRA EXOJO','MIEMBRO','jmanuel','1');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('4','ANTONIO LOPEZ BETRIAN','MIEMBRO','alopez','1');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('5','CARLOS GONZÁLEZ CASTILLO','JEFE_EQUIPO','cgonzalez','2');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('6','AMADEO MATEOS CHICO','MIEMBRO','amateos','2');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('7','ELIAS MARTINEZ ALLER','MIEMBRO','emartinez','2');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('8','MARCELO FRANCISCO GONZALEZ FERNANDEZ','JEFE_EQUIPO','mfgonzalez','3');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('9','JOSE LUIS CALONGE DELSO','MIEMBRO','jlcalonge','3');
Insert into MIEMBROS (ID,NOMBRE_COMPLETO,POSICION,USERNAME,ID_EQUIPO) values ('10','JOSE IGNACIO CEREZO HERNANDEZ','MIEMBRO','jicerezo','3');
/

prompt =========================================================================
prompt + Tarea5
prompt =========================================================================
prompt Ejecutando creación de secuencias...

declare
  maximo number;
begin
 
  --secuencia SEQ_ALERTA  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_ALERTA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_AREASCUESTIONARIOS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_CLASE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_DEPARTAMENTO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_EMPLEO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  --secuencia   SEQ_CUERPOS
  select max(id) into maximo from cuerposestado;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_CUERPOS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_CUESTIONARIOPERSONALIZADO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_CUESTIONARIOSENVIADOS 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_CUESTIONARIOSENVIADOS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia SEQ_DOCUMENTACION_PREVIA  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_DOCUMENTACION_PREVIA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
   
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_PUESTO_TRABAJO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  --secuencia   SEQ_DOCUMENTOS
  select max(id) into maximo from documentos;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_DOCUMENTOS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia seq_documentosblob  
  select max(id) into maximo from documentos_blob;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "seq_documentosblob"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia SEQ_EQUIPO  
  select max(id) into maximo from equipo;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_EQUIPO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_GUIAS 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_GUIAS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_GUIAPERSONALIZADA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_INSPECCION 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_INSPECCION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_MIEMBROS
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_MIEMBROS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_MODELOSCUESTIONARIOS 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_MODELOSCUESTIONARIOS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_NOTIFICACIONES
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_NOTIFICACIONES"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_PREGUNTASCUESTIONARIO
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_PREGUNTASCUESTIONARIO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   seq_pasosguia
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "seq_pasosguia"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_REGISTRO
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_REGISTRO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_RESPUESTATABLA
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_RESPUESTATABLA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_SOLDOCPREVIA
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_SOLDOCPREVIA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_TIPODOCUMENTACIONPREVIA
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_TIPODOCUMENTACIONPREVIA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_TIPO_EQUIPO
  select max(id) into maximo from tipo_equipo;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  "SEQ_TIPO_EQUIPO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
end;



prompt =========================================================================
prompt EJECUCION TERMINADA
prompt =========================================================================
