WHENEVER OSERROR EXIT FAILURE ROLLBACK
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT IMPLANTACIÓN PROGESIN
prompt
prompt    Autor: EZENTIS
prompt
prompt    Actualización:  07/06/2017    
prompt =========================================================================


prompt =========================================================================
prompt + Tarea1
prompt =========================================================================
prompt Ejecutando borrado de tablas y secuencias...

BEGIN
  FOR cur_rec IN (SELECT object_name, object_type 
                  FROM   user_objects
                  WHERE  object_type IN ('TABLE', 'SEQUENCE')) LOOP
    BEGIN
      IF cur_rec.object_type = 'TABLE' THEN
        EXECUTE IMMEDIATE 'DROP ' || cur_rec.object_type || ' "' || cur_rec.object_name || '" CASCADE CONSTRAINTS';
      ELSE
        EXECUTE IMMEDIATE 'DROP ' || cur_rec.object_type || ' "' || cur_rec.object_name || '"';
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
        DBMS_OUTPUT.put_line('FAILED: DROP ' || cur_rec.object_type || ' "' || cur_rec.object_name || '"');
    END;
  END LOOP;
END;
/    
COMMIT;

prompt =========================================================================
prompt + Tarea2
prompt =========================================================================
prompt Ejecutando creación de tablas...

prompt =========================================================================
prompt  Creacion tabla  ALERTAS
prompt =========================================================================

  CREATE TABLE ALERTAS 
   (    ID_ALERTA NUMBER(19,0), 
    DESCRIPCION VARCHAR2(2000 CHAR), 
    FECHA_REGISTRO TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    NOMBRE_SECCION VARCHAR2(50 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR), 
    USUARIO_BAJA VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  ALERTAS_NOTIFICACIONES_USUARIO
prompt =========================================================================

  CREATE TABLE ALERTAS_NOTIFICACIONES_USUARIO 
   (    USUARIO VARCHAR2(255 CHAR), 
    TIPO_MENSAJE VARCHAR2(50 CHAR), 
    ID_MENSAJE NUMBER(19,0), 
    FECHA_ALTA TIMESTAMP (6), 
    NOMBRE_SECCION VARCHAR2(50 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  AREASCUESTIONARIO
prompt =========================================================================

  CREATE TABLE AREASCUESTIONARIO 
   (    ID NUMBER(19,0), 
    NOMBRE_AREA VARCHAR2(255 CHAR), 
    FECHA_BAJA TIMESTAMP (6), 
    ID_CUESTIONARIO NUMBER(10,0), 
    ORDEN NUMBER(10,0), 
    USERNAME_BAJA VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  AREAS_INFORME
prompt =========================================================================

  CREATE TABLE AREAS_INFORME 
   (	ID NUMBER(19,0), 
	DESCRIPCION VARCHAR2(1000 CHAR), 
	MODELO_INFORME_ID NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  AREAS_USUARIO_CUESTENV
prompt =========================================================================

  CREATE TABLE AREAS_USUARIO_CUESTENV 
   (    ID_AREA NUMBER(19,0), 
    ID_CUESTIONARIO_ENVIADO NUMBER(19,0), 
    USERNAME_PROV VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  CLASE_USUARIO
prompt =========================================================================

  CREATE TABLE CLASE_USUARIO 
   (    ID_CLASE NUMBER(19,0), 
    CLASE VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  CONFIG_RESPUESTAS_CUESTIONARIO
prompt =========================================================================

  CREATE TABLE CONFIG_RESPUESTAS_CUESTIONARIO 
   (    CLAVE VARCHAR2(255 CHAR), 
    SECCION VARCHAR2(255 CHAR), 
    VALOR VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  CUERPOSESTADO
prompt =========================================================================

  CREATE TABLE CUERPOSESTADO 
   (    ID NUMBER(10,0), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    NOMBRE_CORTO VARCHAR2(10 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  CUESTIONARIO_PERSONALIZADO
prompt =========================================================================

  CREATE TABLE CUESTIONARIO_PERSONALIZADO 
   (    ID NUMBER(19,0), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_CREACION TIMESTAMP (6), 
    NOMBRE_CUESTIONARIO VARCHAR2(100 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_CREACION VARCHAR2(255 CHAR), 
    ID_MODELO_CUESTIONARIO NUMBER(10,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  CUESTIONARIOS_ENVIADOS
prompt =========================================================================

  CREATE TABLE CUESTIONARIOS_ENVIADOS 
   (    ID NUMBER(19,0), 
    CARGO VARCHAR2(500 CHAR), 
    CORREO VARCHAR2(500 CHAR), 
    FECHA_ANULACION TIMESTAMP (6), 
    FECHA_CUMPLIMENTACION TIMESTAMP (6), 
    FECHA_ENVIO TIMESTAMP (6), 
    FECHA_FINALIZACION TIMESTAMP (6), 
    FECHA_LIMITE_CUESTIONARIO TIMESTAMP (6), 
    FECHA_NO_CONFORME TIMESTAMP (6), 
    MOTIVO VARCHAR2(2000 CHAR), 
    NOMBRE_USUARIO VARCHAR2(500 CHAR), 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    USERNAME_ENVIO VARCHAR2(255 CHAR), 
    USERNAME_FINALIZACION VARCHAR2(255 CHAR), 
    USERNAME_NO_CONFORME VARCHAR2(255 CHAR), 
    ID_CUESTIONARIO_PERSONALIZADO NUMBER(19,0), 
    ID_INSPECCION NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  CUEST_PER_PREGUNTAS
prompt =========================================================================

  CREATE TABLE CUEST_PER_PREGUNTAS 
   (    ID_CUEST_PERS NUMBER(19,0), 
    ID_PREG_ELEGIDA NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  DEPARTAMENTO
prompt =========================================================================

  CREATE TABLE DEPARTAMENTO 
   (    ID NUMBER(19,0), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  DOCUMENTACION_PREVIA
prompt =========================================================================

  CREATE TABLE DOCUMENTACION_PREVIA 
   (    ID NUMBER(19,0), 
    DESCRIPCION VARCHAR2(255 CHAR), 
    EXTENSIONES VARCHAR2(255 CHAR), 
    ID_SOLICITUD NUMBER(19,0), 
    NOMBRE VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS
prompt =========================================================================

  CREATE TABLE DOCUMENTOS 
   (    ID NUMBER(19,0), 
    DESCRIPCION VARCHAR2(2000 CHAR), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    MATERIA_INDEXADA VARCHAR2(2000 CHAR), 
    NOMBRE VARCHAR2(255 CHAR), 
    TIPO_CONTENIDO VARCHAR2(255 CHAR), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_FICHERO NUMBER(19,0), 
    TIPO_DOCUMENTO NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS_BLOB
prompt =========================================================================

  CREATE TABLE DOCUMENTOS_BLOB 
   (    ID NUMBER(19,0), 
    FICHERO BLOB, 
    NOMBRE_FICHERO VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS_INSPECCION
prompt =========================================================================

  CREATE TABLE DOCUMENTOS_INSPECCION 
   (    ID_DOCUMENTO NUMBER(19,0), 
    ID_INSPECCION NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  EMPLEO
prompt =========================================================================

  CREATE TABLE EMPLEO 
   (    ID NUMBER(19,0), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    NOMBRE_CORTO VARCHAR2(20 CHAR), 
    ID_CUERPO NUMBER(10,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  EQUIPO
prompt =========================================================================

  CREATE TABLE EQUIPO 
   (    ID NUMBER(19,0), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    JEFE_EQUIPO VARCHAR2(100 CHAR), 
    NOMBRE_EQUIPO VARCHAR2(100 CHAR), 
    NOMBRE_JEFE VARCHAR2(150 CHAR), 
    ID_TIPO_EQUIPO NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  SOLICITUD_PREVIA_DOCS
prompt =========================================================================

  CREATE TABLE SOLICITUD_PREVIA_DOCS 
   (    ID_SOLICITUD_PREVIA NUMBER(19,0), 
    ID_DOCUMENTO NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  GUIA_PASOS
prompt =========================================================================

  CREATE TABLE GUIA_PASOS 
   (    ID NUMBER(19,0), 
    FECHA_BAJA TIMESTAMP (6), 
    ORDEN NUMBER(10,0), 
    PASO VARCHAR2(2000 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_GUIA NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  GUIA_PERSONALIZADA
prompt =========================================================================

  CREATE TABLE GUIA_PERSONALIZADA 
   (    ID NUMBER(19,0), 
    FECHA_ANULACION TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_CREACION TIMESTAMP (6), 
    NOMBRE_GUIA_PERSONALIZADA VARCHAR2(100 CHAR), 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_CREACION VARCHAR2(255 CHAR), 
    ID_MODELO_GUIA NUMBER(19,0), 
    INSPECCION NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  GUIA_PERSONALIZADA_PASOS
prompt =========================================================================

  CREATE TABLE GUIA_PERSONALIZADA_PASOS 
   (    ID_GUIA_PERS NUMBER(19,0), 
    ID_PASO_ELEGIDO NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  GUIAS
prompt =========================================================================

  CREATE TABLE GUIAS 
   (    ID NUMBER(19,0), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    FECHA_ANULACION TIMESTAMP (6), 
    NOMBRE_GUIA VARCHAR2(255 CHAR), 
    ORDEN NUMBER(10,0), 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    TIPO_INSPECCION VARCHAR2(10 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  INFORMES
prompt =========================================================================

  CREATE TABLE INFORMES 
   (   ID NUMBER(19,0), 
	INSPECCION_ID NUMBER(19,0), 
	MODELO_INFORME_ID NUMBER(19,0) 
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  INSPECCIONES
prompt =========================================================================

  CREATE TABLE INSPECCIONES 
(   ID NUMBER(19,0), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    AMBITO VARCHAR2(10 CHAR), 
    ANIO NUMBER(10,0), 
    CUATRIMESTRE VARCHAR2(30 CHAR), 
    ESTADO_INSPECCION VARCHAR2(30 CHAR), 
    FECHA_ANULACION TIMESTAMP (6), 
    FECHA_FINALIZACION TIMESTAMP (6), 
    FECHA_PREVISTA TIMESTAMP (6), 
    NOMBRE_UNIDAD VARCHAR2(255 CHAR), 
    NUMERO VARCHAR2(100 CHAR), 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    USERNAME_FINALIZACION VARCHAR2(255 CHAR), 
    ID_EQUIPO NUMBER(19,0), 
    ID_MUNICIPIO NUMBER(19,0), 
    TIPO_INSPECCION VARCHAR2(10 CHAR), 
    TIPO_UNIDAD NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  INSPECCIONES_ASOCIADAS
prompt =========================================================================

  CREATE TABLE INSPECCIONES_ASOCIADAS 
   (    ID_INSPECCION NUMBER(19,0), 
    ID_INSPECCION_ASOCIADA NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  MIEMBROS
prompt =========================================================================

  CREATE TABLE MIEMBROS 
   (    ID NUMBER(19,0), 
    NOMBRE_COMPLETO VARCHAR2(255 CHAR), 
    POSICION VARCHAR2(255 CHAR), 
    USERNAME VARCHAR2(255 CHAR), 
    ID_EQUIPO NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  MODELOSCUESTIONARIOS
prompt =========================================================================

  CREATE TABLE MODELOSCUESTIONARIOS 
   (    ID NUMBER(10,0), 
    CODIGO VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  MODELOS_INFORME
prompt =========================================================================

  CREATE TABLE MODELOS_INFORME 
   (    ID NUMBER(19,0), 
	NOMBRE VARCHAR2(100 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  MUNICIPIOS
prompt =========================================================================

  CREATE TABLE MUNICIPIOS 
   (    ID NUMBER(19,0), 
    NAME VARCHAR2(100 CHAR), 
    CODE_PROVINCE VARCHAR2(3 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  NOTIFICACIONES
prompt =========================================================================

  CREATE TABLE NOTIFICACIONES 
   (    ID_NOTIFICACION NUMBER(19,0), 
    DESCRIPCION VARCHAR2(2000 CHAR), 
    FECHA_NOTIFICACION TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    NOMBRE_SECCION VARCHAR2(50 CHAR), 
    TIPO_NOTIFICACION VARCHAR2(20 CHAR), 
    USUARIO_BAJA VARCHAR2(255 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  PARAMETROS
prompt =========================================================================

  CREATE TABLE PARAMETROS 
   (    CLAVE VARCHAR2(255 CHAR), 
    SECCION VARCHAR2(255 CHAR), 
    VALOR VARCHAR2(4000 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  PREGUNTASCUESTIONARIO
prompt =========================================================================

  CREATE TABLE PREGUNTASCUESTIONARIO 
   (    ID NUMBER(19,0), 
    FECHA_BAJA TIMESTAMP (6), 
    ORDEN NUMBER(10,0), 
    PREGUNTA VARCHAR2(2000 CHAR), 
    TIPO_RESPUESTA VARCHAR2(100 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_AREA NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  PROVINCIAS
prompt =========================================================================

  CREATE TABLE PROVINCIAS 
   (    CODIGO VARCHAR2(3 CHAR), 
    CODIGO_MN VARCHAR2(10 CHAR), 
    NOMBRE VARCHAR2(100 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  PUESTOSTRABAJO
prompt =========================================================================

  CREATE TABLE PUESTOSTRABAJO 
   (    ID NUMBER(19,0), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIF TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(12 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  REG_ACTIVIDAD
prompt =========================================================================

  CREATE TABLE REG_ACTIVIDAD 
   (    REG_ACTIVIDAD NUMBER(19,0), 
    DESCRIPCION CLOB, 
    FECHA_ALTA TIMESTAMP (6), 
    NOMBRE_SECCION VARCHAR2(50 CHAR), 
    TIPO_REG_ACTIVIDAD VARCHAR2(255 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  RESPUESTA_DATOS_TABLA
prompt =========================================================================

  CREATE TABLE RESPUESTA_DATOS_TABLA 
   (    ID NUMBER(19,0), 
    CAMPO01 VARCHAR2(255 CHAR), 
    CAMPO02 VARCHAR2(255 CHAR), 
    CAMPO03 VARCHAR2(255 CHAR), 
    CAMPO04 VARCHAR2(255 CHAR), 
    CAMPO05 VARCHAR2(255 CHAR), 
    CAMPO06 VARCHAR2(255 CHAR), 
    CAMPO07 VARCHAR2(255 CHAR), 
    CAMPO08 VARCHAR2(255 CHAR), 
    CAMPO09 VARCHAR2(255 CHAR), 
    CAMPO10 VARCHAR2(255 CHAR), 
    CAMPO11 VARCHAR2(255 CHAR), 
    CAMPO12 VARCHAR2(255 CHAR), 
    CAMPO13 VARCHAR2(255 CHAR), 
    CAMPO14 VARCHAR2(255 CHAR), 
    CAMPO15 VARCHAR2(255 CHAR), 
    CAMPO16 VARCHAR2(255 CHAR), 
    CAMPO17 VARCHAR2(255 CHAR), 
    CAMPO18 VARCHAR2(255 CHAR), 
    CAMPO19 VARCHAR2(255 CHAR), 
    CAMPO20 VARCHAR2(255 CHAR), 
    NOMBRE_FILA VARCHAR2(255 CHAR), 
    RESPUESTA_ID_CUEST_ENVIADO NUMBER(19,0), 
    RESPUESTA_ID_PREGUNTA NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  RESPUESTAS_CUEST_DOCS
prompt =========================================================================

  CREATE TABLE RESPUESTAS_CUEST_DOCS 
   (    ID_CUESTIONARIO_ENVIADO NUMBER(19,0), 
    ID_PREGUNTA NUMBER(19,0), 
    ID_DOCUMENTO NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  RESPUESTASCUESTIONARIO
prompt =========================================================================

  CREATE TABLE RESPUESTASCUESTIONARIO 
   (    FECHA_VALIDACION TIMESTAMP (6), 
    RESPUESTA_TEXTO VARCHAR2(2000 CHAR), 
    USERNAME_VALIDACION VARCHAR2(255 CHAR), 
    ID_CUEST_ENVIADO NUMBER(19,0), 
    ID_PREGUNTA NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  RESPUESTAS_INFORME
prompt =========================================================================

  CREATE TABLE RESPUESTAS_INFORME 
   (    RESPUESTA BLOB, 
	INFORME_ID NUMBER(19,0), 
	SUBAREA_ID NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  SOLICITUD_DOC_PREVIA
prompt =========================================================================

  CREATE TABLE SOLICITUD_DOC_PREVIA 
   (    ID NUMBER(19,0), 
    APOYO_CORREO VARCHAR2(255 CHAR), 
    APOYO_NOMBRE VARCHAR2(255 CHAR), 
    APOYO_PUESTO VARCHAR2(255 CHAR), 
    APOYO_TELEFONO VARCHAR2(255 CHAR), 
    ASUNTO VARCHAR2(255 CHAR), 
    CARGO_INTERLOCUTOR VARCHAR2(255 CHAR), 
    CATEGORIA_INTERLOCUTOR VARCHAR2(255 CHAR), 
    CORREO_CORPORATIVO_INTER VARCHAR2(255 CHAR), 
    CORREO_CORPORATIVO_INTER_COMPL VARCHAR2(255 CHAR), 
    CORREO_DESTINATARIO VARCHAR2(255 CHAR), 
    DESCARGA_PLANTILLAS VARCHAR2(255 CHAR), 
    DESTINATARIO VARCHAR2(255 CHAR), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_CUMPLIMENTACION TIMESTAMP (6), 
    FECHA_ENVIO TIMESTAMP (6), 
    FECHA_FINALIZACION TIMESTAMP (6), 
    FECHA_LIMITE_CUMPLIMENTAR TIMESTAMP (6), 
    FECHA_LIMITE_ENVIO TIMESTAMP (6), 
    FECHA_NO_CONFORME TIMESTAMP (6), 
    FECHA_VALID_APOYO TIMESTAMP (6), 
    FECHA_VALID_JEFE_EQUIPO TIMESTAMP (6), 
    NOMBRE_COMPLETO_INTERLOCUTOR VARCHAR2(255 CHAR), 
    TELEFONO_INTERLOCUTOR VARCHAR2(255 CHAR), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_ENVIO VARCHAR2(255 CHAR), 
    USERNAME_VALID_APOYO VARCHAR2(255 CHAR), 
    USERNAME_VALID_JEFE_EQUIPO VARCHAR2(255 CHAR), 
    USUARIO_FINALIZACION VARCHAR2(255 CHAR), 
    USUARIO_NO_CONFORME VARCHAR2(255 CHAR), 
    ID_INSPECCION NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  SUBAREAS_INFORME
prompt =========================================================================

  CREATE TABLE SUBAREAS_INFORME 
   (    ID NUMBER(19,0), 
	DESCRIPCION VARCHAR2(1000 CHAR), 
	AREA_ID NUMBER(19,0)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  SUGERENCIA
prompt =========================================================================

  CREATE TABLE SUGERENCIA 
   (    ID_SUGERENCIA NUMBER(10,0), 
    DESCRIPCION VARCHAR2(4000 CHAR), 
    FECHA_CONTESTACION TIMESTAMP (6), 
    FECHA_REGISTRO TIMESTAMP (6), 
    MODULO VARCHAR2(50 CHAR), 
    USUARIO_CONTESTACION VARCHAR2(255 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  TIPODOCUMENTACIONPREVIA
prompt =========================================================================

  CREATE TABLE TIPODOCUMENTACIONPREVIA 
   (    ID NUMBER(19,0), 
    AMBITO VARCHAR2(10 CHAR), 
    DESCRIPCION VARCHAR2(255 CHAR), 
    EXTENSIONES VARCHAR2(255 CHAR), 
    NOMBRE VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  TIPO_DOCUMENTO
prompt =========================================================================

  CREATE TABLE TIPO_DOCUMENTO 
   (    ID NUMBER(19,0), 
    NOMBRE VARCHAR2(255 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  TIPO_EQUIPO
prompt =========================================================================

  CREATE TABLE TIPO_EQUIPO 
   (    ID NUMBER(19,0), 
    CODIGO VARCHAR2(5 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  TIPOS_INSPECCION
prompt =========================================================================

  CREATE TABLE TIPOS_INSPECCION 
   (    CODIGO VARCHAR2(10 CHAR), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  TIPOS_UNIDAD
prompt =========================================================================

  CREATE TABLE TIPOS_UNIDAD 
   (    ID NUMBER(19,0), 
    DESCRIPCION VARCHAR2(100 CHAR)
   ) ;
/
prompt =========================================================================
prompt  Creacion tabla  USERS
prompt =========================================================================

  CREATE TABLE USERS 
   (    USERNAME VARCHAR2(255 CHAR), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    PRIM_APELLIDO VARCHAR2(50 CHAR), 
    SEGUNDO_APELLIDO VARCHAR2(50 CHAR), 
    CATEGORIA VARCHAR2(20 CHAR), 
    CORREO VARCHAR2(50 CHAR), 
    DESPACHO VARCHAR2(20 CHAR), 
    DOC_IDENTIDAD VARCHAR2(10 CHAR), 
    ESTADO VARCHAR2(8 CHAR), 
    FECHA_DESTINO_IPSS TIMESTAMP (6), 
    FECHA_INACTIVO TIMESTAMP (6), 
    FECHA_INGRESO TIMESTAMP (6), 
    NIVEL NUMBER(10,0), 
    NOMBRE VARCHAR2(50 CHAR), 
    PASSWORD VARCHAR2(100 CHAR), 
    ROLE VARCHAR2(25 CHAR), 
    TELEFONO VARCHAR2(12 CHAR), 
    TFNO_MOVIL_OFICIAL VARCHAR2(12 CHAR), 
    TFNO_MOVIL_PARTICULAR VARCHAR2(12 CHAR), 
    ID_CLASE NUMBER(19,0), 
    ID_CUERPO NUMBER(10,0), 
    ID_DEPARTAMENTO NUMBER(19,0), 
    ID_EMPLEO NUMBER(19,0), 
    ID_PUESTO NUMBER(19,0)
   ) ;
/

prompt =========================================================================
prompt + Tarea3
prompt =========================================================================
prompt Ejecutando creación de index y Constraints...

prompt =========================================================================
prompt  Creacion índice  INDICE_SOLICITUD_PREVIA
prompt =========================================================================

  CREATE UNIQUE INDEX INDICE_SOLICITUD_PREVIA ON SOLICITUD_PREVIA_DOCS (ID_DOCUMENTO);
/
prompt =========================================================================
prompt  Creacion índice  INDICE_RESPUESTAS_CUEST
prompt =========================================================================

  CREATE UNIQUE INDEX INDICE_RESPUESTAS_CUEST ON RESPUESTAS_CUEST_DOCS (ID_DOCUMENTO);
/
prompt =========================================================================
prompt  Creacion índice  INDICE_TIPO_EQUIPO
prompt =========================================================================

  CREATE UNIQUE INDEX INDICE_TIPO_EQUIPO ON TIPO_EQUIPO (CODIGO);
/
prompt =========================================================================
prompt  Constraints para la tabla  CUERPOSESTADO
prompt =========================================================================

  ALTER TABLE CUERPOSESTADO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE CUERPOSESTADO MODIFY (DESCRIPCION NOT NULL ENABLE);
  ALTER TABLE CUERPOSESTADO MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE CUERPOSESTADO MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE CUERPOSESTADO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  PUESTOSTRABAJO
prompt =========================================================================

  ALTER TABLE PUESTOSTRABAJO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE PUESTOSTRABAJO MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE PUESTOSTRABAJO MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE PUESTOSTRABAJO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  DOCUMENTOS
prompt =========================================================================

  ALTER TABLE DOCUMENTOS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE DOCUMENTOS MODIFY (TIPO_CONTENIDO NOT NULL ENABLE);
  ALTER TABLE DOCUMENTOS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  INSPECCIONES_ASOCIADAS
prompt =========================================================================

  ALTER TABLE INSPECCIONES_ASOCIADAS MODIFY (ID_INSPECCION_ASOCIADA NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES_ASOCIADAS MODIFY (ID_INSPECCION NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  RESPUESTASCUESTIONARIO
prompt =========================================================================

  ALTER TABLE RESPUESTASCUESTIONARIO ADD PRIMARY KEY (ID_CUEST_ENVIADO, ID_PREGUNTA)
  USING INDEX  ENABLE;
  ALTER TABLE RESPUESTASCUESTIONARIO MODIFY (ID_PREGUNTA NOT NULL ENABLE);
  ALTER TABLE RESPUESTASCUESTIONARIO MODIFY (ID_CUEST_ENVIADO NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  ALERTAS
prompt =========================================================================

  ALTER TABLE ALERTAS ADD PRIMARY KEY (ID_ALERTA)
  USING INDEX  ENABLE;
  ALTER TABLE ALERTAS MODIFY (ID_ALERTA NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  INSPECCIONES
prompt =========================================================================

  ALTER TABLE INSPECCIONES ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE INSPECCIONES MODIFY (TIPO_INSPECCION NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES MODIFY (ID_EQUIPO NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES MODIFY (ANIO NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES MODIFY (AMBITO NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE INSPECCIONES MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  GUIA_PERSONALIZADA_PASOS
prompt =========================================================================

  ALTER TABLE GUIA_PERSONALIZADA_PASOS MODIFY (ID_PASO_ELEGIDO NOT NULL ENABLE);
  ALTER TABLE GUIA_PERSONALIZADA_PASOS MODIFY (ID_GUIA_PERS NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  PREGUNTASCUESTIONARIO
prompt =========================================================================

  ALTER TABLE PREGUNTASCUESTIONARIO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE PREGUNTASCUESTIONARIO MODIFY (PREGUNTA NOT NULL ENABLE);
  ALTER TABLE PREGUNTASCUESTIONARIO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  CUESTIONARIOS_ENVIADOS
prompt =========================================================================

  ALTER TABLE CUESTIONARIOS_ENVIADOS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (ID_INSPECCION NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (ID_CUESTIONARIO_PERSONALIZADO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (NOMBRE_USUARIO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (MOTIVO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (FECHA_LIMITE_CUESTIONARIO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (CORREO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIOS_ENVIADOS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  GUIA_PERSONALIZADA
prompt =========================================================================

  ALTER TABLE GUIA_PERSONALIZADA ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE GUIA_PERSONALIZADA MODIFY (ID_MODELO_GUIA NOT NULL ENABLE);
  ALTER TABLE GUIA_PERSONALIZADA MODIFY (USERNAME_CREACION NOT NULL ENABLE);
  ALTER TABLE GUIA_PERSONALIZADA MODIFY (NOMBRE_GUIA_PERSONALIZADA NOT NULL ENABLE);
  ALTER TABLE GUIA_PERSONALIZADA MODIFY (FECHA_CREACION NOT NULL ENABLE);
  ALTER TABLE GUIA_PERSONALIZADA MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  AREAS_USUARIO_CUESTENV
prompt =========================================================================

  ALTER TABLE AREAS_USUARIO_CUESTENV ADD PRIMARY KEY (ID_AREA, ID_CUESTIONARIO_ENVIADO)
  USING INDEX  ENABLE;
  ALTER TABLE AREAS_USUARIO_CUESTENV MODIFY (ID_CUESTIONARIO_ENVIADO NOT NULL ENABLE);
  ALTER TABLE AREAS_USUARIO_CUESTENV MODIFY (ID_AREA NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  ALERTAS_NOTIFICACIONES_USUARIO
prompt =========================================================================

  ALTER TABLE ALERTAS_NOTIFICACIONES_USUARIO ADD PRIMARY KEY (USUARIO, TIPO_MENSAJE, ID_MENSAJE, FECHA_ALTA, NOMBRE_SECCION)
  USING INDEX  ENABLE;
  ALTER TABLE ALERTAS_NOTIFICACIONES_USUARIO MODIFY (NOMBRE_SECCION NOT NULL ENABLE);
  ALTER TABLE ALERTAS_NOTIFICACIONES_USUARIO MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE ALERTAS_NOTIFICACIONES_USUARIO MODIFY (ID_MENSAJE NOT NULL ENABLE);
  ALTER TABLE ALERTAS_NOTIFICACIONES_USUARIO MODIFY (TIPO_MENSAJE NOT NULL ENABLE);
  ALTER TABLE ALERTAS_NOTIFICACIONES_USUARIO MODIFY (USUARIO NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  CONFIG_RESPUESTAS_CUESTIONARIO
prompt =========================================================================

  ALTER TABLE CONFIG_RESPUESTAS_CUESTIONARIO ADD PRIMARY KEY (CLAVE, SECCION, VALOR)
  USING INDEX  ENABLE;
  ALTER TABLE CONFIG_RESPUESTAS_CUESTIONARIO MODIFY (VALOR NOT NULL ENABLE);
  ALTER TABLE CONFIG_RESPUESTAS_CUESTIONARIO MODIFY (SECCION NOT NULL ENABLE);
  ALTER TABLE CONFIG_RESPUESTAS_CUESTIONARIO MODIFY (CLAVE NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  DOCUMENTACION_PREVIA
prompt =========================================================================

  ALTER TABLE DOCUMENTACION_PREVIA ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE DOCUMENTACION_PREVIA MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  MODELOSCUESTIONARIOS
prompt =========================================================================

  ALTER TABLE MODELOSCUESTIONARIOS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE MODELOSCUESTIONARIOS MODIFY (DESCRIPCION NOT NULL ENABLE);
  ALTER TABLE MODELOSCUESTIONARIOS MODIFY (CODIGO NOT NULL ENABLE);
  ALTER TABLE MODELOSCUESTIONARIOS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  DEPARTAMENTO
prompt =========================================================================

  ALTER TABLE DEPARTAMENTO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE DEPARTAMENTO MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE DEPARTAMENTO MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE DEPARTAMENTO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  TIPOS_INSPECCION
prompt =========================================================================

  ALTER TABLE TIPOS_INSPECCION ADD PRIMARY KEY (CODIGO)
  USING INDEX  ENABLE;
  ALTER TABLE TIPOS_INSPECCION MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE TIPOS_INSPECCION MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE TIPOS_INSPECCION MODIFY (CODIGO NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  MIEMBROS
prompt =========================================================================

  ALTER TABLE MIEMBROS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE MIEMBROS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  CUESTIONARIO_PERSONALIZADO
prompt =========================================================================

  ALTER TABLE CUESTIONARIO_PERSONALIZADO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE CUESTIONARIO_PERSONALIZADO MODIFY (ID_MODELO_CUESTIONARIO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIO_PERSONALIZADO MODIFY (USERNAME_CREACION NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIO_PERSONALIZADO MODIFY (NOMBRE_CUESTIONARIO NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIO_PERSONALIZADO MODIFY (FECHA_CREACION NOT NULL ENABLE);
  ALTER TABLE CUESTIONARIO_PERSONALIZADO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  MUNICIPIOS
prompt =========================================================================

  ALTER TABLE MUNICIPIOS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE MUNICIPIOS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  PROVINCIAS
prompt =========================================================================

  ALTER TABLE PROVINCIAS ADD PRIMARY KEY (CODIGO)
  USING INDEX  ENABLE;
  ALTER TABLE PROVINCIAS MODIFY (CODIGO NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  USERS
prompt =========================================================================

  ALTER TABLE USERS ADD PRIMARY KEY (USERNAME)
  USING INDEX  ENABLE;
  ALTER TABLE USERS MODIFY (ROLE NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (PASSWORD NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (NOMBRE NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (ESTADO NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (DOC_IDENTIDAD NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (CORREO NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (PRIM_APELLIDO NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE USERS MODIFY (USERNAME NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  TIPODOCUMENTACIONPREVIA
prompt =========================================================================

  ALTER TABLE TIPODOCUMENTACIONPREVIA ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE TIPODOCUMENTACIONPREVIA MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  SOLICITUD_DOC_PREVIA
prompt =========================================================================

  ALTER TABLE SOLICITUD_DOC_PREVIA ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE SOLICITUD_DOC_PREVIA MODIFY (ID_INSPECCION NOT NULL ENABLE);
  ALTER TABLE SOLICITUD_DOC_PREVIA MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE SOLICITUD_DOC_PREVIA MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  TIPO_DOCUMENTO
prompt =========================================================================

  ALTER TABLE TIPO_DOCUMENTO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE TIPO_DOCUMENTO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  PARAMETROS
prompt =========================================================================

  ALTER TABLE PARAMETROS ADD PRIMARY KEY (CLAVE, SECCION, VALOR)
  USING INDEX  ENABLE;
  ALTER TABLE PARAMETROS MODIFY (VALOR NOT NULL ENABLE);
  ALTER TABLE PARAMETROS MODIFY (SECCION NOT NULL ENABLE);
  ALTER TABLE PARAMETROS MODIFY (CLAVE NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  SOLICITUD_PREVIA_DOCS
prompt =========================================================================

  ALTER TABLE SOLICITUD_PREVIA_DOCS ADD CONSTRAINT INDICE_SOLICITUD_PREVIA UNIQUE (ID_DOCUMENTO)
  USING INDEX  ENABLE;
  ALTER TABLE SOLICITUD_PREVIA_DOCS MODIFY (ID_DOCUMENTO NOT NULL ENABLE);
  ALTER TABLE SOLICITUD_PREVIA_DOCS MODIFY (ID_SOLICITUD_PREVIA NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  NOTIFICACIONES
prompt =========================================================================

  ALTER TABLE NOTIFICACIONES ADD PRIMARY KEY (ID_NOTIFICACION)
  USING INDEX  ENABLE;
  ALTER TABLE NOTIFICACIONES MODIFY (FECHA_NOTIFICACION NOT NULL ENABLE);
  ALTER TABLE NOTIFICACIONES MODIFY (ID_NOTIFICACION NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  RESPUESTA_DATOS_TABLA
prompt =========================================================================

  ALTER TABLE RESPUESTA_DATOS_TABLA ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE RESPUESTA_DATOS_TABLA MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  CLASE_USUARIO
prompt =========================================================================

  ALTER TABLE CLASE_USUARIO ADD PRIMARY KEY (ID_CLASE)
  USING INDEX  ENABLE;
  ALTER TABLE CLASE_USUARIO MODIFY (ID_CLASE NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  DOCUMENTOS_INSPECCION
prompt =========================================================================

  ALTER TABLE DOCUMENTOS_INSPECCION MODIFY (ID_INSPECCION NOT NULL ENABLE);
  ALTER TABLE DOCUMENTOS_INSPECCION MODIFY (ID_DOCUMENTO NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  TIPOS_UNIDAD
prompt =========================================================================

  ALTER TABLE TIPOS_UNIDAD ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE TIPOS_UNIDAD MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  EMPLEO
prompt =========================================================================

  ALTER TABLE EMPLEO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE EMPLEO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  DOCUMENTOS_BLOB
prompt =========================================================================

  ALTER TABLE DOCUMENTOS_BLOB ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE DOCUMENTOS_BLOB MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  REG_ACTIVIDAD
prompt =========================================================================

  ALTER TABLE REG_ACTIVIDAD ADD PRIMARY KEY (REG_ACTIVIDAD)
  USING INDEX  ENABLE;
  ALTER TABLE REG_ACTIVIDAD MODIFY (REG_ACTIVIDAD NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  GUIA_PASOS
prompt =========================================================================

  ALTER TABLE GUIA_PASOS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE GUIA_PASOS MODIFY (PASO NOT NULL ENABLE);
  ALTER TABLE GUIA_PASOS MODIFY (ORDEN NOT NULL ENABLE);
  ALTER TABLE GUIA_PASOS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  TIPO_EQUIPO
prompt =========================================================================

  ALTER TABLE TIPO_EQUIPO ADD CONSTRAINT INDICE_TIPO_EQUIPO UNIQUE (CODIGO)
  USING INDEX  ENABLE;
  ALTER TABLE TIPO_EQUIPO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE TIPO_EQUIPO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  AREASCUESTIONARIO
prompt =========================================================================

  ALTER TABLE AREASCUESTIONARIO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE AREASCUESTIONARIO MODIFY (NOMBRE_AREA NOT NULL ENABLE);
  ALTER TABLE AREASCUESTIONARIO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  SUGERENCIA
prompt =========================================================================

  ALTER TABLE SUGERENCIA ADD PRIMARY KEY (ID_SUGERENCIA)
  USING INDEX  ENABLE;
  ALTER TABLE SUGERENCIA MODIFY (ID_SUGERENCIA NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  GUIAS
prompt =========================================================================

  ALTER TABLE GUIAS ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE GUIAS MODIFY (TIPO_INSPECCION NOT NULL ENABLE);
  ALTER TABLE GUIAS MODIFY (NOMBRE_GUIA NOT NULL ENABLE);
  ALTER TABLE GUIAS MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE GUIAS MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE GUIAS MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  RESPUESTAS_CUEST_DOCS
prompt =========================================================================

  ALTER TABLE RESPUESTAS_CUEST_DOCS ADD CONSTRAINT INDICE_RESPUESTAS_CUEST UNIQUE (ID_DOCUMENTO)
  USING INDEX  ENABLE;
  ALTER TABLE RESPUESTAS_CUEST_DOCS MODIFY (ID_DOCUMENTO NOT NULL ENABLE);
  ALTER TABLE RESPUESTAS_CUEST_DOCS MODIFY (ID_PREGUNTA NOT NULL ENABLE);
  ALTER TABLE RESPUESTAS_CUEST_DOCS MODIFY (ID_CUESTIONARIO_ENVIADO NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  EQUIPO
prompt =========================================================================

  ALTER TABLE EQUIPO ADD PRIMARY KEY (ID)
  USING INDEX  ENABLE;
  ALTER TABLE EQUIPO MODIFY (NOMBRE_EQUIPO NOT NULL ENABLE);
  ALTER TABLE EQUIPO MODIFY (JEFE_EQUIPO NOT NULL ENABLE);
  ALTER TABLE EQUIPO MODIFY (USERNAME_ALTA NOT NULL ENABLE);
  ALTER TABLE EQUIPO MODIFY (FECHA_ALTA NOT NULL ENABLE);
  ALTER TABLE EQUIPO MODIFY (ID NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Constraints para la tabla  CUEST_PER_PREGUNTAS
prompt =========================================================================

  ALTER TABLE CUEST_PER_PREGUNTAS MODIFY (ID_PREG_ELEGIDA NOT NULL ENABLE);
  ALTER TABLE CUEST_PER_PREGUNTAS MODIFY (ID_CUEST_PERS NOT NULL ENABLE);
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  AREASCUESTIONARIO
prompt =========================================================================

  ALTER TABLE AREASCUESTIONARIO ADD CONSTRAINT FK_AC_CUESTIONARIO FOREIGN KEY (ID_CUESTIONARIO)
      REFERENCES MODELOSCUESTIONARIOS (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  CUESTIONARIO_PERSONALIZADO
prompt =========================================================================

  ALTER TABLE CUESTIONARIO_PERSONALIZADO ADD CONSTRAINT FK_CP_MODELO_CUESTIONARIO FOREIGN KEY (ID_MODELO_CUESTIONARIO)
      REFERENCES MODELOSCUESTIONARIOS (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  CUESTIONARIOS_ENVIADOS
prompt =========================================================================

  ALTER TABLE CUESTIONARIOS_ENVIADOS ADD CONSTRAINT FK_CE_CUEST_PERSON FOREIGN KEY (ID_CUESTIONARIO_PERSONALIZADO)
      REFERENCES CUESTIONARIO_PERSONALIZADO (ID) ENABLE;
  ALTER TABLE CUESTIONARIOS_ENVIADOS ADD CONSTRAINT FK_CE_INSPECCION FOREIGN KEY (ID_INSPECCION)
      REFERENCES INSPECCIONES (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  CUEST_PER_PREGUNTAS
prompt =========================================================================

  ALTER TABLE CUEST_PER_PREGUNTAS ADD CONSTRAINT FK_CP_PREGELEGIDA FOREIGN KEY (ID_PREG_ELEGIDA)
      REFERENCES PREGUNTASCUESTIONARIO (ID) ENABLE;
  ALTER TABLE CUEST_PER_PREGUNTAS ADD CONSTRAINT FK_CP_CUESTIONARIOPER FOREIGN KEY (ID_CUEST_PERS)
      REFERENCES CUESTIONARIO_PERSONALIZADO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  DOCUMENTOS
prompt =========================================================================

  ALTER TABLE DOCUMENTOS ADD CONSTRAINT FK_DOC_TIPODOC FOREIGN KEY (TIPO_DOCUMENTO)
      REFERENCES TIPO_DOCUMENTO (ID) ENABLE;
  ALTER TABLE DOCUMENTOS ADD CONSTRAINT FK_D_FICHERO FOREIGN KEY (ID_FICHERO)
      REFERENCES DOCUMENTOS_BLOB (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  DOCUMENTOS_INSPECCION
prompt =========================================================================

  ALTER TABLE DOCUMENTOS_INSPECCION ADD CONSTRAINT FK_DOCUINS_INSPECCION FOREIGN KEY (ID_INSPECCION)
      REFERENCES INSPECCIONES (ID) ENABLE;
  ALTER TABLE DOCUMENTOS_INSPECCION ADD CONSTRAINT FK_DOCUINS_DOCUMENTO FOREIGN KEY (ID_DOCUMENTO)
      REFERENCES DOCUMENTOS (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  EMPLEO
prompt =========================================================================

  ALTER TABLE EMPLEO ADD CONSTRAINT FK_EM_CUERPO FOREIGN KEY (ID_CUERPO)
      REFERENCES CUERPOSESTADO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  EQUIPO
prompt =========================================================================

  ALTER TABLE EQUIPO ADD CONSTRAINT FK_EQ_TIPOEQUIPO FOREIGN KEY (ID_TIPO_EQUIPO)
      REFERENCES TIPO_EQUIPO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  GUIA_PASOS
prompt =========================================================================

  ALTER TABLE GUIA_PASOS ADD CONSTRAINT FK_GP_GUIA FOREIGN KEY (ID_GUIA)
      REFERENCES GUIAS (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  GUIA_PERSONALIZADA
prompt =========================================================================

  ALTER TABLE GUIA_PERSONALIZADA ADD CONSTRAINT FK_GPR_INSPECCION FOREIGN KEY (INSPECCION)
      REFERENCES INSPECCIONES (ID) ENABLE;
  ALTER TABLE GUIA_PERSONALIZADA ADD CONSTRAINT FK_GPR_MODELO_GUIA FOREIGN KEY (ID_MODELO_GUIA)
      REFERENCES GUIAS (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  GUIA_PERSONALIZADA_PASOS
prompt =========================================================================

  ALTER TABLE GUIA_PERSONALIZADA_PASOS ADD CONSTRAINT FK_GUIAPERPASO_PASO FOREIGN KEY (ID_PASO_ELEGIDO)
      REFERENCES GUIA_PASOS (ID) ENABLE;
  ALTER TABLE GUIA_PERSONALIZADA_PASOS ADD CONSTRAINT FK_GUIAPERPASO_GUIAPER FOREIGN KEY (ID_GUIA_PERS)
      REFERENCES GUIA_PERSONALIZADA (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  GUIAS
prompt =========================================================================

  ALTER TABLE GUIAS ADD CONSTRAINT FK_G_TIPO_INSPECCION FOREIGN KEY (TIPO_INSPECCION)
      REFERENCES TIPOS_INSPECCION (CODIGO) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  INSPECCIONES
prompt =========================================================================

  ALTER TABLE INSPECCIONES ADD CONSTRAINT FK_I_EQUIPO FOREIGN KEY (ID_EQUIPO)
      REFERENCES EQUIPO (ID) ENABLE;
  ALTER TABLE INSPECCIONES ADD CONSTRAINT FK_I_MUNICIPIO FOREIGN KEY (ID_MUNICIPIO)
      REFERENCES MUNICIPIOS (ID) ENABLE;
  ALTER TABLE INSPECCIONES ADD CONSTRAINT FK_I_TIPOUNIDAD FOREIGN KEY (TIPO_UNIDAD)
      REFERENCES TIPOS_UNIDAD (ID) ENABLE;
  ALTER TABLE INSPECCIONES ADD CONSTRAINT FK_I_TIPO_INSPECCION FOREIGN KEY (TIPO_INSPECCION)
      REFERENCES TIPOS_INSPECCION (CODIGO) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  INSPECCIONES_ASOCIADAS
prompt =========================================================================

  ALTER TABLE INSPECCIONES_ASOCIADAS ADD CONSTRAINT FA_INSASO_INSPECCIONASOC FOREIGN KEY (ID_INSPECCION_ASOCIADA)
      REFERENCES INSPECCIONES (ID) ENABLE;
  ALTER TABLE INSPECCIONES_ASOCIADAS ADD CONSTRAINT FK_INSASO_INSPECCION FOREIGN KEY (ID_INSPECCION)
      REFERENCES INSPECCIONES (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  MIEMBROS
prompt =========================================================================

  ALTER TABLE MIEMBROS ADD CONSTRAINT FK_M_EQUIPO FOREIGN KEY (ID_EQUIPO)
      REFERENCES EQUIPO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  MUNICIPIOS
prompt =========================================================================

  ALTER TABLE MUNICIPIOS ADD CONSTRAINT FK_PROVINCIA FOREIGN KEY (CODE_PROVINCE)
      REFERENCES PROVINCIAS (CODIGO) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  PREGUNTASCUESTIONARIO
prompt =========================================================================

  ALTER TABLE PREGUNTASCUESTIONARIO ADD CONSTRAINT FK_PC_AREA FOREIGN KEY (ID_AREA)
      REFERENCES AREASCUESTIONARIO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  RESPUESTA_DATOS_TABLA
prompt =========================================================================

  ALTER TABLE RESPUESTA_DATOS_TABLA ADD CONSTRAINT FK_RESPUESTADATOS FOREIGN KEY (RESPUESTA_ID_CUEST_ENVIADO, RESPUESTA_ID_PREGUNTA)
      REFERENCES RESPUESTASCUESTIONARIO (ID_CUEST_ENVIADO, ID_PREGUNTA) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  RESPUESTAS_CUEST_DOCS
prompt =========================================================================

  ALTER TABLE RESPUESTAS_CUEST_DOCS ADD CONSTRAINT FK_RESPCUESTDOCS FOREIGN KEY (ID_CUESTIONARIO_ENVIADO, ID_PREGUNTA)
      REFERENCES RESPUESTASCUESTIONARIO (ID_CUEST_ENVIADO, ID_PREGUNTA) ENABLE;
  ALTER TABLE RESPUESTAS_CUEST_DOCS ADD CONSTRAINT FK_RESPCUESTDOCS_DOCU FOREIGN KEY (ID_DOCUMENTO)
      REFERENCES DOCUMENTOS (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  RESPUESTASCUESTIONARIO
prompt =========================================================================

  ALTER TABLE RESPUESTASCUESTIONARIO ADD CONSTRAINT FK_RC_CUEST_ENVIADO FOREIGN KEY (ID_CUEST_ENVIADO)
      REFERENCES CUESTIONARIOS_ENVIADOS (ID) ENABLE;
  ALTER TABLE RESPUESTASCUESTIONARIO ADD CONSTRAINT FK_RC_PREGUNTA FOREIGN KEY (ID_PREGUNTA)
      REFERENCES PREGUNTASCUESTIONARIO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  SOLICITUD_DOC_PREVIA
prompt =========================================================================

  ALTER TABLE SOLICITUD_DOC_PREVIA ADD CONSTRAINT FK_SDP_INSPECCION FOREIGN KEY (ID_INSPECCION)
      REFERENCES INSPECCIONES (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  USERS
prompt =========================================================================

  ALTER TABLE USERS ADD CONSTRAINT FK_U_CLASE FOREIGN KEY (ID_CLASE)
      REFERENCES CLASE_USUARIO (ID_CLASE) ENABLE;
  ALTER TABLE USERS ADD CONSTRAINT FK_U_CUERPO FOREIGN KEY (ID_CUERPO)
      REFERENCES CUERPOSESTADO (ID) ENABLE;
  ALTER TABLE USERS ADD CONSTRAINT FK_U_DEPARTAMENTO FOREIGN KEY (ID_DEPARTAMENTO)
      REFERENCES DEPARTAMENTO (ID) ENABLE;
  ALTER TABLE USERS ADD CONSTRAINT FK_U_EMPLEO FOREIGN KEY (ID_EMPLEO)
      REFERENCES EMPLEO (ID) ENABLE;
  ALTER TABLE USERS ADD CONSTRAINT FK_U_PUESTO FOREIGN KEY (ID_PUESTO)
      REFERENCES PUESTOSTRABAJO (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  MODELOS_INFORME
prompt =========================================================================

  ALTER TABLE MODELOS_INFORME MODIFY (ID NOT NULL ENABLE);
  ALTER TABLE MODELOS_INFORME MODIFY (NOMBRE NOT NULL ENABLE);
  ALTER TABLE MODELOS_INFORME ADD PRIMARY KEY (ID)
      USING INDEX  ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  AREAS_INFORME
prompt =========================================================================

  ALTER TABLE AREAS_INFORME MODIFY (ID NOT NULL ENABLE);
  ALTER TABLE AREAS_INFORME MODIFY (DESCRIPCION NOT NULL ENABLE);
  ALTER TABLE AREAS_INFORME ADD PRIMARY KEY (ID)
      USING INDEX  ENABLE;
  ALTER TABLE AREAS_INFORME ADD CONSTRAINT FK_MODELO_INFORME FOREIGN KEY (MODELO_INFORME_ID)
	  REFERENCES MODELOS_INFORME (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  SUBAREAS_INFORME
prompt =========================================================================

  ALTER TABLE SUBAREAS_INFORME MODIFY (ID NOT NULL ENABLE);
  ALTER TABLE SUBAREAS_INFORME MODIFY (DESCRIPCION NOT NULL ENABLE);
  ALTER TABLE SUBAREAS_INFORME ADD PRIMARY KEY (ID)
      USING INDEX  ENABLE;
  ALTER TABLE SUBAREAS_INFORME ADD CONSTRAINT FK_AREA_INFORME FOREIGN KEY (AREA_ID)
	  REFERENCES AREAS_INFORME (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  INFORMES
prompt =========================================================================

  ALTER TABLE INFORMES MODIFY (ID NOT NULL ENABLE);
  ALTER TABLE INFORMES ADD PRIMARY KEY (ID)
      USING INDEX  ENABLE;
  ALTER TABLE INFORMES ADD CONSTRAINT FK_INSP_INFORME FOREIGN KEY (INSPECCION_ID)
	  REFERENCES INSPECCIONES (ID) ENABLE;
/
prompt =========================================================================
prompt  Reference Constraints para la tabla  RESPUESTAS_INFORME
prompt =========================================================================

  ALTER TABLE RESPUESTAS_INFORME MODIFY (INFORME_ID NOT NULL ENABLE);
  ALTER TABLE RESPUESTAS_INFORME MODIFY (SUBAREA_ID NOT NULL ENABLE);
  ALTER TABLE RESPUESTAS_INFORME ADD PRIMARY KEY (INFORME_ID, SUBAREA_ID)
      USING INDEX  ENABLE;
  ALTER TABLE RESPUESTAS_INFORME ADD CONSTRAINT FK_INFORME FOREIGN KEY (INFORME_ID)
	  REFERENCES INFORMES (ID) ENABLE;
  ALTER TABLE RESPUESTAS_INFORME ADD CONSTRAINT FK_SUBAREA_INF FOREIGN KEY (SUBAREA_ID)
	  REFERENCES SUBAREAS_INFORME (ID) ENABLE;
/


prompt =========================================================================
prompt + Tarea4
prompt =========================================================================
prompt Ejecutando inserción de datos...

prompt =========================================================================
prompt Ejecutando inserción de datos CUERPOSESTADO...
prompt =========================================================================

Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (1,'Policía Nacional',SYSDATE,null,null,'PN','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (2,'Guardia Civil',SYSDATE,null,null,'GC','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (3,'Cuerpo General de la Administración',SYSDATE,null,null,'CGA','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (4,'Cuerpos Comunes de la Defensa',SYSDATE,null,null,'CCD','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (5,'Contratados',SYSDATE,null,null,'CONTR','system',null,null);
Insert into CUERPOSESTADO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,NOMBRE_CORTO,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (6,'Correos y Telégrafos',SYSDATE,null,null,'CT','system',null,null);
COMMIT;

prompt =========================================================================
prompt Ejecutando inserción de datos CLASE_USUARIO...
prompt =========================================================================

Insert into CLASE_USUARIO (ID_CLASE,CLASE) values (1,'FCSE');
Insert into CLASE_USUARIO (ID_CLASE,CLASE) values (2,'FCSE-FC');
Insert into CLASE_USUARIO (ID_CLASE,CLASE) values (3,'RPT');
Insert into CLASE_USUARIO (ID_CLASE,CLASE) values (4,'RPT-FC');
COMMIT;

prompt =========================================================================
prompt Ejecutando inserción de datos DEPARTAMENTO...
prompt =========================================================================

Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (1,SYSDATE,null,null,'system',null,null,'Subdirección General de Inspección y Servicios');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (2,SYSDATE,null,null,'system',null,null,'Secretaría Técnica');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (3,SYSDATE,null,null,'system',null,null,'Jefatura de Inspecciones');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (4,SYSDATE,null,null,'system',null,null,'Equipos de Inspecciones Generales');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (5,SYSDATE,null,null,'system',null,null,'Equipo de Inspecciones Temáticas y PRL');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (6,SYSDATE,null,null,'system',null,null,'Gabinete de Estudios y Análisis');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (7,SYSDATE,null,null,'system',null,null,'Servicio de Calidad y Quejas');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (8,SYSDATE,null,null,'system',null,null,'Servicio de Apoyo');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (9,SYSDATE,null,null,'system',null,null,'Secretaría');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (10,SYSDATE,null,null,'system',null,null,'Asesores');
Insert into DEPARTAMENTO (ID,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values (11,SYSDATE,null,null,'system',null,null,'Conductores');
COMMIT;

prompt =========================================================================
prompt Ejecutando inserción de datos TIPO_DOCUMENTO...
prompt =========================================================================

Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (1,'ANEXOS AL INFORME');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (2,'ÁREA OPERATIVA');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (3,'ÁREAS ADMINISTRATIVA Y DE APOYO');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (4,'AUDIO');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (5,'CONTROL DE ACTIVIDAD');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (6,'CUESTIONARIO');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (7,'DATOS ESTADISTICOS');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (8,'DOCUMENTACIÓN ENTRADA IPSS');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (9,'DOCUMENTACIÓN SALIDA IPSS');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (10,'FICHA SEGUIMIENTO Y CONTROL');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (11,'HIPEST / SIDENPOL');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (12,'HISTORICO INFORMES');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (13,'IMAGEN');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (14,'INFORME');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (15,'INFRAESTRUCTURAS E INSTALACIONES');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (16,'LIBROS OFICIALES');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (17,'NORMA  INTERNA');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (18,'OTROS');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (19,'PROGRAMA ESTADÍSTICO DE SEGURIDAD');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (20,'QUEJAS');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (21,'RECURSOS HUMANOS');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (22,'RECURSOS MATERIALES');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (23,'VIDEO');
COMMIT;

prompt =========================================================================
prompt Ejecutando inserción de datos DOCUMENTOS_BLOB...
prompt =========================================================================

DECLARE
    l_bfile  BFILE;
    l_blob   BLOB;
BEGIN

    EXECUTE IMMEDIATE 'CREATE OR REPLACE DIRECTORY PLANTILLAS_PROGESIN AS ''C:/Plantillas''';

    INSERT INTO documentos_blob (id, fichero, nombre_fichero) VALUES (1, empty_blob(), '00_d_CPT_C.xlsx') RETURN fichero INTO l_blob;
        l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_C.xlsx');
        DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
        DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
        DBMS_LOB.CLOSE(l_bfile);
        
    INSERT INTO documentos_blob (id, fichero, nombre_fichero) VALUES (2, empty_blob(), '00_d_CPT_CIA.xlsx') RETURN fichero INTO l_blob;
        l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_CIA.xlsx');
        DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
        DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
        DBMS_LOB.CLOSE(l_bfile);
        
    INSERT INTO documentos_blob (id, fichero, nombre_fichero) VALUES (3, empty_blob(),'00_d_CPT_Z.xlsx') RETURN fichero INTO l_blob;
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
prompt Ejecutando inserción de datos DOCUMENTOS...
prompt =========================================================================

Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (1, 1,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_C.xlsx', SYSDATE, 'system',18);
Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (2, 2,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_CIA.xlsx', SYSDATE, 'system',18);
Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (3, 3,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_Z.xlsx', SYSDATE, 'system',18);
COMMIT;

prompt =========================================================================
prompt Ejecutando inserción de datos EMPLEO...
prompt =========================================================================

Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (1,'Teniente General','Tte. Gral.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (2,'General de División','Gral. Div.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (3,'General de Brigada','Gral. Bri.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (4,'Coronel','Col.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (5,'Teniente Coronel','TCol.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (6,'Comandante','Comte.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (7,'Capitán','Ctan.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (8,'Teniente','Tte.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (9,'Alférez','Alfz.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (10,'Suboficial Mayor','Subof. May.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (11,'Subteniente','Subte.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (12,'Brigada','Bgda.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (13,'Sargento Primero','Sgto.1',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (14,'Sargento','Sgto.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (15,'Cabo Mayor','Cab.1',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (16,'Cabo','Cab.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (17,'Guardia Civil','Gua.Civ.',2);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (18,'Comisario Principal','Com. Pral',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (19,'Comisario','Com.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (20,'Inspector Jefe','Insp.J.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (21,'Inspector','Insp.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (22,'Facultativo','Facul.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (23,'Subinspector','Subins.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (24,'Oficial de Policía','Ofi.Pol.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (25,'Policía','Pol.',1);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (26,'General de Brigada','Gral. Brig.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (27,'Coronel','Col.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (28,'Teniente Coronel','TCol.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (29,'Comandante','Comte.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (30,'Capitán','Ctan.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (31,'Teniente','Tte.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (32,'Alférez','Alf.',4);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (33,'Técnico de la Administración Central','TecAdmCent.',3);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (34,'Técnico Superior de Sistemas','TecSupSis.',3);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (35,'Facultativo','Facul.',3);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (36,'Secretaría','Secre.',3);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (37,'Administrativo','Adminis.',3);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (38,'Auxiliar Administrativo','AuxAdminis.',3);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (39,'Técnico','Tec.',5);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (40,'Administrativo','Adminis.',5);
Insert into EMPLEO (ID,DESCRIPCION,NOMBRE_CORTO,ID_CUERPO) values (41,'Ejecutivo Post. Y de Telecomunicaciones','CEPyT.',6);
COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos TIPO_EQUIPO...
prompt =========================================================================

Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (1,'IAPRL','Inspecciones Área Prevención de Riesgos Laborales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (2,'IG','Inspecciones Generales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (3,'IS','Inspecciones de Seguimiento');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (4,'II','Inspecciones Incidentales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (5,'IPRL','Inspecciones Prevención de Riesgos Laborales');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (6,'CIES','Inspecciones Temáticas (CIES).');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (7,'SCQ','Servicio de Calidad y Quejas');
Insert into TIPO_EQUIPO (ID,CODIGO,DESCRIPCION) values (8,'SO','Secretaria Y Otros');
COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos TIPODOCUMENTACIONPREVIA...
prompt =========================================================================

Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (1,'GC','Actas de las Comisiones Provinciales de Seguridad Privada de los años 2014 y 2015','PDF','ACPSP');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (2,'PN','Actas de las Comisiones Provinciales de Seguridad Privada de los años 2014 y 2015','PDF','ACPSP');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (3,'GC','Actas de las Comisiones de Coordinación de Policía Judicial de los años 2014 y 2015','PDF','ACCPJ');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (4,'PN','Actas de las Comisiones de Coordinación de Policía Judicial de los años 2014 y 2015','PDF','ACCPJ');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (5,'GC','Actas de las Juntas Locales de Seguridad de los años 2014 y 2015','PDF','AJLS');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (6,'PN','Actas de las Juntas Locales de Seguridad de los años 2014 y 2015','PDF','AJLS');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (7,'GC','Actas de las Juntas de Coordinación de 2015','PDF','AJC');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (8,'GC','Catálogo de Puestos de Trabajo (última aprobación CECIR) y fuerza en revista','XLS, XLSX','CPT');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (9,'GC','Convenios de colaboración con Policías Locales (tanto genéricos como específicos)','PDF','CCPL');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (10,'PN','Convenios de colaboración con Policías Locales (tanto genéricos como específicos)','PDF','CCPL');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (11,'PN','Copia de las actas de las reuniones sindicales celebradas en 2014 y 2015','PDF','CAS');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (12,'PN','Documentación relativa al Plan Territorial. Objetivos operativos fijados para 2015','PDF','PTyO');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (13,'PN','Evaluación de Riesgos Laborales','PDF','PRL');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (14,'GC','Libro de Organización y Libro de Normas de Régimen Interno','PDF','LOYRI');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (15,'GC','Mapa con la distribución territorial de sus Unidades subordinadas.','JPG, JPEG, PNG, BMP','mapa');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (16,'GC','Memoria Anual de 2015','PDF','MA');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (17,'PN','Memoria Anual de 2015','PDF','MA');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (18,'GC','Organigrama de la Comandancia (Unidades Territoriales y Especialidades)','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (19,'GC','Organigrama de la Compañía (Unidades Territoriales y Especialidades)','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (20,'GC','Organigrama de la Zona (Unidades Territoriales y Especialidades)','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (21,'PN','Organigramas de la Unidad, con detalle de sus Unidades y Servicios','DOC, DOCX, PPTX, PPT, PUB','organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (22,'GC','Plan anual de la Unidad Orgánica de Policía Judicial vigente','PDF','PAUOPJ');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (23,'GC','Plan de Emergencia','PDF','PE');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (24,'PN','Plan de Emergencia','PDF','PE');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (25,'GC','Plan de Seguridad Ciudadana','PDF','PSC');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (26,'GC','Relación de mandos de las distintas Unidades, de categoría Oficial, con indicación de teléfonos y correos electrónicos de contacto','DOC, DOCX, PDF','RM');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (27,'GC','Relación de municipios que configuran la demarcación de la compañía','DOC, DOCX','RMD');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (28,'PN','Relación del Equipo Directivo con indicación del nombre y apellidos, categoría y cargo, números de teléfono y dirección de correo electrónico','DOC, DOCX','RM');
COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos TIPOS_INSPECCION...
prompt =========================================================================

Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.G.P.',SYSDATE,null,null,'system',null,null,'I.Gral.Periodica');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.G.S.',SYSDATE,null,null,'system',null,null,'I.Gral.Seguimiento');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.T_PRL',SYSDATE,null,null,'system',null,null,'I.Temática PRL');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.T_CIE',SYSDATE,null,null,'system',null,null,'I.Temática CIE');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.T_OT',SYSDATE,null,null,'system',null,null,'I.Temática otros');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.E_INCD',SYSDATE,null,null,'system',null,null,'I. Incidental extraordinaria');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.E_PRL',SYSDATE,null,null,'system',null,null,'I.PRL extraordinaria');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.E_PUNT',SYSDATE,null,null,'system',null,null,'I.Puntual extraordinaria');
COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos PARAMETROS...
prompt =========================================================================

Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.host','mail','smtp.gmail.com');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.ssl.trust','mail','smtp.gmail.com');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.port','mail','587');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.user','mail','progesinipss@gmail.com');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('UserPwd','mail','ipss2016');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.auth','mail','true');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.starttls.enable','mail','true');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoCorreo','datosApoyo','mmayo@interior.es');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoNombre','datosApoyo','Manuel Mayo Rodríguez');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoPuesto','datosApoyo','Inspector Auditor, Jefe del Servicio de Apoyo');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoTelefono','datosApoyo','91.537.25.41');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('DOC','extensiones','application/msword');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('DOCX','extensiones','application/vnd.openxmlformats-officedocument.wordprocessingml.document');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PPT','extensiones','application/vnd.ms-powerpoint');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PPTX','extensiones','application/vnd.openxmlformats-officedocument.presentationml.presentation');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('XLS','extensiones','application/vnd.ms-excel');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('XLSX','extensiones','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('JPEG','extensiones','image/jpeg');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PNG','extensiones','image/png');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('BMP','extensiones','image/bmp');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PUB','extensiones','application/x-mspublisher');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PDF','extensiones','application/pdf');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PN','URLPROGESIN','http://194.224.253.45:8080/progesin');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('GC','URLPROGESIN','http://194.224.253.45:8080/progesin');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('OTROS','URLPROGESIN','http://194.224.253.45:8080/progesin');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('dominiosCorreo','dominiosCorreo','ezentis.com|interior.es|policia.es|dgp.mir.es|guardiacivil.org|guardiacivil.es');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plazoDiasDocumentacion','tareas',5);
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plazoDiasCuestionario','tareas',5);
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('correoApoyo','tareas','apoyo_ipss@interior.es');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plantillasGC','Comandancia', 1);
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plantillasGC','Compañía', 2);
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plantillasGC','Zona', 3);

COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos PUESTOSTRABAJO...
prompt =========================================================================

Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (1,'Subdirector General',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (2,'Secretario Técnico',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (3,'Jefe de Servicios de Inspección',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (4,'Responsable Estudios y Programas',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (5,'Jefe de Equipo Inspecciones',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (6,'Inspector-Auditor',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (7,'Jefe de Apoyo',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (8,'Jefe de Sección',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (9,'Personal de Apoyo',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (10,'Jefe de Negociado',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (11,'Fuera de Catálogo (Conductores)',SYSDATE,null,null,'system',null,null);
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,FECHA_BAJA,FECHA_MODIF,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF) values (12,'Secretaria de N30',SYSDATE,null,null,'system',null,null);
COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos TIPOS_UNIDAD...
prompt =========================================================================

Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (1,'Cª Distrito');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (2,'Cª Local');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (3,'Cª Provincial');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (4,'CIA');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (5,'CIE');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (6,'Cmdcia');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (7,'JSP');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (8,'Pto. Fronterizo');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (9,'Serv. Central');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (10,'U.E. y D.');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (11,'Zona');


prompt =========================================================================
prompt Ejecutando inserción de datos USERS...
prompt =========================================================================

Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('system','system','system','correo@guardiacivil.es','111111111','A1',null,'ACTIVO',SYSDATE,null,SYSDATE,null,null,null,'20','System','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'user_alta',null,null,'2','8','17','1','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,ID_CLASE,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO) values ('ipss_progesin','ipss_progesin',null,'ipss_progesin@interior.es','1111111','A1',null,'ACTIVO',SYSDATE,null,SYSDATE,null,null,null,'30','ipss_progesin','system',null,'system','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN',null,null,null,'2','2','8','17','1');


COMMIT;


prompt =========================================================================
prompt + Tarea5
prompt =========================================================================
prompt Ejecutando creación de secuencias...


declare
  maximo number;
begin
 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_ALERTA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_AREASCUESTIONARIOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  select max(id_clase)+1 into maximo from Clase_usuario;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_CLASE  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || '  CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  select max(id)+1 into maximo from DEPARTAMENTO;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_DEPARTAMENTO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || '  CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  select max(id)+1 into maximo from EMPLEO;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_EMPLEO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || '  CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  --secuencia   SEQ_CUERPOS
  select max(id)+1 into maximo from cuerposestado;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_CUERPOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_CUESTIONARIOPERSONALIZADO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_CUESTIONARIOSENVIADOS 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_CUESTIONARIOSENVIADOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia SEQ_DOCUMENTACION_PREVIA  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_DOCUMENTACION_PREVIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  select max(id)+1 into maximo from PUESTOSTRABAJO;    
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_PUESTO_TRABAJO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  --secuencia   SEQ_DOCUMENTOS
  select max(id)+1 into maximo from documentos;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_DOCUMENTOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia SEQ_DOCUMENTOSBLOB  
  select max(id)+1 into maximo from documentos_blob;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_DOCUMENTOSBLOB  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia SEQ_EQUIPO  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_EQUIPO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_GUIAS 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_GUIAS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_GUIAPERSONALIZADA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_INSPECCION 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_INSPECCION  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_MIEMBROS
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_MIEMBROS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia  SEQ_MODELOSCUESTIONARIOS 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_MODELOSCUESTIONARIOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_NOTIFICACIONES
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_NOTIFICACIONES  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_PREGUNTASCUESTIONARIO
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_PREGUNTASCUESTIONARIO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_PASOSGUIA
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_PASOSGUIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_REGISTRO
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_REGISTRO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_RESPUESTATABLA
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_RESPUESTATABLA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_SOLDOCPREVIA
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_SOLDOCPREVIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_TIPODOCUMENTACIONPREVIA
  select max(id)+1 into maximo from tipodocumentacionprevia;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_TIPODOCUMENTACIONPREVIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia   SEQ_TIPO_EQUIPO
  select max(id)+1 into maximo from tipo_equipo;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_TIPO_EQUIPO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

  --secuencia   SEQ_MUNICIPIO
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_MUNICIPIO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

  --secuencia   SEQ_SUGERENCIAS
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  seq_sugerencias  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  --secuencia   SEQ_INFORME
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_INFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION';
  
end;



