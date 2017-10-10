WHENEVER OSERROR EXIT FAILURE ROLLBACK 
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT IMPLANTACIÓN PROGESIN
prompt
prompt    Autor: EZENTIS
prompt
prompt    Actualización:  30/08/2018   
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
prompt  Creacion tabla  PARAMETROS
prompt =========================================================================
CREATE TABLE PARAMETROS 
   (CLAVE VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    SECCION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    VALOR VARCHAR2(4000 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (CLAVE, SECCION, VALOR) ENABLE
   ); 
/

prompt =========================================================================
prompt  Creacion tabla  CLASE_USUARIO
prompt =========================================================================

CREATE TABLE CLASE_USUARIO 
   (ID_CLASE NUMBER(19,0) NOT NULL ENABLE, 
    CLASE VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID_CLASE) USING INDEX ENABLE
   );
  /

prompt =========================================================================
prompt  Creacion tabla  CUERPOSESTADO
prompt =========================================================================

 CREATE TABLE CUERPOSESTADO 
   (ID NUMBER(10,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    NOMBRE_CORTO VARCHAR2(10 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   );
 /  
 
 prompt =========================================================================
prompt  Creacion tabla  DEPARTAMENTO
prompt =========================================================================

CREATE TABLE DEPARTAMENTO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   );
 /

prompt =========================================================================
prompt  Creacion tabla  EMPLEO
prompt =========================================================================

 CREATE TABLE EMPLEO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(100 CHAR), 
    NOMBRE_CORTO VARCHAR2(20 CHAR), 
    ID_CUERPO NUMBER(10,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_EM_CUERPO FOREIGN KEY (ID_CUERPO) REFERENCES CUERPOSESTADO (ID) ENABLE
   ) ; 
   
 /

prompt =========================================================================
prompt  Creacion tabla  PUESTOSTRABAJO
prompt =========================================================================

 CREATE TABLE PUESTOSTRABAJO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(100 CHAR), 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_MODIF TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ; 
    
prompt =========================================================================
prompt  Creacion tabla  PROVINCIAS
prompt =========================================================================

CREATE TABLE PROVINCIAS 
   (    CODIGO VARCHAR2(3 CHAR) NOT NULL ENABLE, 
    CODIGO_MN VARCHAR2(10 CHAR), 
    NOMBRE VARCHAR2(100 CHAR), 
    PRIMARY KEY (CODIGO) USING INDEX ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  MUNICIPIOS
prompt =========================================================================

CREATE TABLE MUNICIPIOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NAME VARCHAR2(100 CHAR), 
    CODE_PROVINCE VARCHAR2(3 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_PROVINCIA FOREIGN KEY (CODE_PROVINCE)
    REFERENCES PROVINCIAS (CODIGO) ENABLE
   ) ;    
 
 prompt =========================================================================
prompt  Creacion tabla  ALERTAS
prompt =========================================================================

   CREATE TABLE ALERTAS 
   (    ID_ALERTA NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(2000 CHAR), 
    FECHA_REGISTRO TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    NOMBRE_SECCION VARCHAR2(50 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR), 
    USUARIO_BAJA VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID_ALERTA) USING INDEX  ENABLE
   )  
    partition by range(FECHA_REGISTRO)
    (
      partition ALERTAS_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition ALERTAS_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition ALERTAS_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition ALERTAS_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition ALERTAS_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_alertas ON ALERTAS (FECHA_REGISTRO);
    
/

prompt =========================================================================
prompt  Creacion tabla  NOTIFICACIONES
prompt =========================================================================
CREATE TABLE NOTIFICACIONES 
   (    ID_NOTIFICACION NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(2000 CHAR), 
    FECHA_NOTIFICACION TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    NOMBRE_SECCION VARCHAR2(50 CHAR), 
    TIPO_NOTIFICACION VARCHAR2(20 CHAR), 
    USUARIO_BAJA VARCHAR2(255 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (ID_NOTIFICACION) USING INDEX ENABLE
   )
    partition by range(FECHA_NOTIFICACION)
    (
      partition NOTIFICACIONES_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition NOTIFICACIONES_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition NOTIFICACIONES_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition NOTIFICACIONES_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition NOTIFICACIONES_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_notificaciones ON NOTIFICACIONES (FECHA_NOTIFICACION);
   
prompt =========================================================================
prompt  Creacion tabla  ALERTAS_NOTIFICACIONES_USUARIO
prompt =========================================================================

  CREATE TABLE ALERTAS_NOTIFICACIONES_USUARIO 
   (    USUARIO VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    TIPO_MENSAJE VARCHAR2(50 CHAR) NOT NULL ENABLE, 
    ID_MENSAJE NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    NOMBRE_SECCION VARCHAR2(50 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (USUARIO, TIPO_MENSAJE, ID_MENSAJE, FECHA_ALTA, NOMBRE_SECCION) USING INDEX ENABLE
   )
    partition by range(FECHA_ALTA)
    (
      partition ALERTAS_NOTIFI_USUARIO_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition ALERTAS_NOTIFI_USUARIO_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition ALERTAS_NOTIFI_USUARIO_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition ALERTAS_NOTIFI_USUARIO_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition ALERTAS_NOTIFI_USUARIO_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_notifalertas ON ALERTAS_NOTIFICACIONES_USUARIO (FECHA_ALTA);
/

prompt =========================================================================
prompt  Creacion tabla  REG_ACTIVIDAD
prompt =========================================================================

CREATE TABLE REG_ACTIVIDAD 
   (    REG_ACTIVIDAD NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION CLOB, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    NOMBRE_SECCION VARCHAR2(50 CHAR), 
    TIPO_REG_ACTIVIDAD VARCHAR2(255 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (REG_ACTIVIDAD) USING INDEX  ENABLE
   ) 
    partition by range(FECHA_ALTA)
    (
      partition REG_ENE_2017 values less than (TO_DATE('01/01/2018','DD/MM/yyyy')),
      partition REG_FEB_2017 values less than (TO_DATE('01/02/2018','DD/MM/yyyy')),
      partition REG_MAR_2017 values less than (TO_DATE('01/03/2018','DD/MM/yyyy')),
      partition REG_ABR_2017 values less than (TO_DATE('01/04/2018','DD/MM/yyyy')),
      partition REG_MAY_2017 values less than (TO_DATE('01/05/2018','DD/MM/yyyy')),
      partition REG_JUN_2017 values less than (TO_DATE('01/06/2018','DD/MM/yyyy')),
      partition REG_JUL_2017 values less than (TO_DATE('01/07/2018','DD/MM/yyyy')),
      partition REG_AGO_2017 values less than (TO_DATE('01/08/2018','DD/MM/yyyy')),
      partition REG_SEP_2017 values less than (TO_DATE('01/09/2018','DD/MM/yyyy')),
      partition REG_OCT_2017 values less than (TO_DATE('01/10/2018','DD/MM/yyyy')),
      partition REG_NOV_2017 values less than (TO_DATE('01/11/2018','DD/MM/yyyy')),
      partition REG_DIC_2017 values less than (TO_DATE('01/12/2018','DD/MM/yyyy')),
      partition REG_ENE_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition REG_FEB_2018 values less than (TO_DATE('01/02/2019','DD/MM/yyyy')),
      partition REG_MAR_2018 values less than (TO_DATE('01/03/2019','DD/MM/yyyy')),
      partition REG_ABR_2018 values less than (TO_DATE('01/04/2019','DD/MM/yyyy')),
      partition REG_MAY_2018 values less than (TO_DATE('01/05/2019','DD/MM/yyyy')),
      partition REG_JUN_2018 values less than (TO_DATE('01/06/2019','DD/MM/yyyy')),
      partition REG_JUL_2018 values less than (TO_DATE('01/07/2019','DD/MM/yyyy')),
      partition REG_AGO_2018 values less than (TO_DATE('01/08/2019','DD/MM/yyyy')),
      partition REG_SEP_2018 values less than (TO_DATE('01/09/2019','DD/MM/yyyy')),
      partition REG_OCT_2018 values less than (TO_DATE('01/10/2019','DD/MM/yyyy')),
      partition REG_NOV_2018 values less than (TO_DATE('01/11/2019','DD/MM/yyyy')),
      partition REG_DIC_2018 values less than (TO_DATE('01/12/2019','DD/MM/yyyy')),
      partition REG_ENE_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition REG_FEB_2019 values less than (TO_DATE('01/02/2020','DD/MM/yyyy')),
      partition REG_MAR_2019 values less than (TO_DATE('01/03/2020','DD/MM/yyyy')),
      partition REG_ABR_2019 values less than (TO_DATE('01/04/2020','DD/MM/yyyy')),
      partition REG_MAY_2019 values less than (TO_DATE('01/05/2020','DD/MM/yyyy')),
      partition REG_JUN_2019 values less than (TO_DATE('01/06/2020','DD/MM/yyyy')),
      partition REG_JUL_2019 values less than (TO_DATE('01/07/2020','DD/MM/yyyy')),
      partition REG_AGO_2019 values less than (TO_DATE('01/08/2020','DD/MM/yyyy')),
      partition REG_SEP_2019 values less than (TO_DATE('01/09/2020','DD/MM/yyyy')),
      partition REG_OCT_2019 values less than (TO_DATE('01/10/2020','DD/MM/yyyy')),
      partition REG_NOV_2019 values less than (TO_DATE('01/11/2020','DD/MM/yyyy')),
      partition REG_DIC_2019 values less than (TO_DATE('01/12/2020','DD/MM/yyyy')),
      partition REG_ENE_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition REG_FEB_2020 values less than (TO_DATE('01/02/2021','DD/MM/yyyy')),
      partition REG_MAR_2020 values less than (TO_DATE('01/03/2021','DD/MM/yyyy')),
      partition REG_ABR_2020 values less than (TO_DATE('01/04/2021','DD/MM/yyyy')),
      partition REG_MAY_2020 values less than (TO_DATE('01/05/2021','DD/MM/yyyy')),
      partition REG_JUN_2020 values less than (TO_DATE('01/06/2021','DD/MM/yyyy')),
      partition REG_JUL_2020 values less than (TO_DATE('01/07/2021','DD/MM/yyyy')),
      partition REG_AGO_2020 values less than (TO_DATE('01/08/2021','DD/MM/yyyy')),
      partition REG_SEP_2020 values less than (TO_DATE('01/09/2021','DD/MM/yyyy')),
      partition REG_OCT_2020 values less than (TO_DATE('01/10/2021','DD/MM/yyyy')),
      partition REG_NOV_2020 values less than (TO_DATE('01/11/2021','DD/MM/yyyy')),
      partition REG_DIC_2020 values less than (TO_DATE('01/12/2021','DD/MM/yyyy')),
      partition REG_ENE_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy')),
      partition REG_FEB_2021 values less than (TO_DATE('01/02/2022','DD/MM/yyyy')),
      partition REG_MAR_2021 values less than (TO_DATE('01/03/2022','DD/MM/yyyy')),
      partition REG_ABR_2021 values less than (TO_DATE('01/04/2022','DD/MM/yyyy')),
      partition REG_MAY_2021 values less than (TO_DATE('01/05/2022','DD/MM/yyyy')),
      partition REG_JUN_2021 values less than (TO_DATE('01/06/2022','DD/MM/yyyy')),
      partition REG_JUL_2021 values less than (TO_DATE('01/07/2022','DD/MM/yyyy')),
      partition REG_AGO_2021 values less than (TO_DATE('01/08/2022','DD/MM/yyyy')),
      partition REG_SEP_2021 values less than (TO_DATE('01/09/2022','DD/MM/yyyy')),
      partition REG_OCT_2021 values less than (TO_DATE('01/10/2022','DD/MM/yyyy')),
      partition REG_NOV_2021 values less than (TO_DATE('01/11/2022','DD/MM/yyyy')),
      partition REG_DIC_2021 values less than (TO_DATE('01/12/2022','DD/MM/yyyy'))
    );
   
    CREATE INDEX indice_regactividad ON REG_ACTIVIDAD (FECHA_ALTA); 

prompt =========================================================================
prompt  Creacion tabla  SUGERENCIA
prompt =========================================================================
CREATE TABLE SUGERENCIA 
   (    ID_SUGERENCIA NUMBER(10,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(4000 CHAR), 
    FECHA_CONTESTACION TIMESTAMP (6), 
    FECHA_REGISTRO TIMESTAMP (6) NOT NULL ENABLE, 
    MODULO VARCHAR2(50 CHAR), 
    USUARIO_CONTESTACION VARCHAR2(255 CHAR), 
    USUARIO_REGISTRO VARCHAR2(255 CHAR) NOT NULL ENABLE,
    PRIMARY KEY (ID_SUGERENCIA) USING INDEX ENABLE
   ); 

prompt =========================================================================
prompt  Creacion tabla  TIPOS_UNIDAD
prompt =========================================================================
CREATE TABLE TIPOS_UNIDAD 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(100 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  TIPODOCUMENTACIONPREVIA
prompt =========================================================================
CREATE TABLE TIPODOCUMENTACIONPREVIA 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    AMBITO VARCHAR2(10 CHAR), 
    DESCRIPCION VARCHAR2(255 CHAR), 
    EXTENSIONES VARCHAR2(255 CHAR), 
    NOMBRE VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ;  

prompt =========================================================================
prompt  Creacion tabla  DOCUMENTACION_PREVIA
prompt =========================================================================

CREATE TABLE DOCUMENTACION_PREVIA 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(255 CHAR), 
    EXTENSIONES VARCHAR2(255 CHAR), 
    ID_SOLICITUD NUMBER(19,0), 
    NOMBRE VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  TIPO_DOCUMENTO
prompt =========================================================================

CREATE TABLE TIPO_DOCUMENTO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  TIPO_EQUIPO
prompt =========================================================================

 CREATE TABLE TIPO_EQUIPO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    CODIGO VARCHAR2(5 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    CONSTRAINT INDICE_TIPO_EQUIPO UNIQUE (CODIGO) USING INDEX ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ;   

prompt =========================================================================
prompt  Creacion tabla  USERS
prompt =========================================================================
CREATE TABLE USERS 
   (    USERNAME VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    PRIM_APELLIDO VARCHAR2(50 CHAR) NOT NULL ENABLE, 
    SEGUNDO_APELLIDO VARCHAR2(50 CHAR), 
    CATEGORIA VARCHAR2(20 CHAR), 
    CORREO VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    DESPACHO VARCHAR2(20 CHAR), 
    DOC_IDENTIDAD VARCHAR2(10 CHAR) NOT NULL ENABLE, 
    NUM_IDENTIFICACION VARCHAR2(20 CHAR), 
    ESTADO VARCHAR2(8 CHAR) NOT NULL ENABLE, 
    FECHA_DESTINO_IPSS TIMESTAMP (6), 
    FECHA_INACTIVO TIMESTAMP (6), 
    FECHA_INGRESO TIMESTAMP (6), 
    NIVEL NUMBER(10,0), 
    NOMBRE VARCHAR2(50 CHAR) NOT NULL ENABLE, 
    PASSWORD VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    ROLE VARCHAR2(25 CHAR) NOT NULL ENABLE, 
    TELEFONO VARCHAR2(12 CHAR), 
    TFNO_MOVIL_OFICIAL VARCHAR2(12 CHAR), 
    TFNO_MOVIL_PARTICULAR VARCHAR2(12 CHAR), 
    ID_CLASE NUMBER(19,0), 
    ID_CUERPO NUMBER(10,0), 
    ID_DEPARTAMENTO NUMBER(19,0), 
    ID_EMPLEO NUMBER(19,0), 
    ID_PUESTO NUMBER(19,0), 
    PRIMARY KEY (USERNAME) USING INDEX ENABLE, 
    CONSTRAINT FK_U_CLASE FOREIGN KEY (ID_CLASE)
    REFERENCES CLASE_USUARIO (ID_CLASE) ENABLE, 
    CONSTRAINT FK_U_CUERPO FOREIGN KEY (ID_CUERPO)
    REFERENCES CUERPOSESTADO (ID) ENABLE, 
    CONSTRAINT FK_U_DEPARTAMENTO FOREIGN KEY (ID_DEPARTAMENTO)
    REFERENCES DEPARTAMENTO (ID) ENABLE, 
    CONSTRAINT FK_U_EMPLEO FOREIGN KEY (ID_EMPLEO)
    REFERENCES EMPLEO (ID) ENABLE, 
    CONSTRAINT FK_U_PUESTO FOREIGN KEY (ID_PUESTO)
    REFERENCES PUESTOSTRABAJO (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  EQUIPO
prompt =========================================================================

CREATE TABLE EQUIPO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    JEFE_EQUIPO VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    NOMBRE_EQUIPO VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    ID_TIPO_EQUIPO NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX  ENABLE, 
    CONSTRAINT FK_EQ_TIPOEQUIPO FOREIGN KEY (ID_TIPO_EQUIPO)
    REFERENCES TIPO_EQUIPO (ID) ENABLE, 
    CONSTRAINT FK_EQ_JEFE FOREIGN KEY (JEFE_EQUIPO)
    REFERENCES USERS (USERNAME) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  MIEMBROS
prompt =========================================================================

CREATE TABLE MIEMBROS 
   (    ID NUMBER(19,0) NOT NULL ENABLE,  
    POSICION VARCHAR2(255 CHAR), 
    USUARIO VARCHAR2(255 CHAR), 
    ID_EQUIPO NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_M_EQUIPO FOREIGN KEY (ID_EQUIPO)
    REFERENCES EQUIPO (ID) ENABLE, 
    CONSTRAINT FK_U_MIEMBRO FOREIGN KEY (USUARIO)
    REFERENCES USERS (USERNAME) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  TIPOS_INSPECCION
prompt =========================================================================

CREATE TABLE TIPOS_INSPECCION 
   (    CODIGO VARCHAR2(10 CHAR) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    PRIMARY KEY (CODIGO) USING INDEX ENABLE
   ) ;

prompt =========================================================================
prompt  Creacion tabla  INSPECCIONES
prompt =========================================================================

CREATE TABLE INSPECCIONES 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    AMBITO VARCHAR2(10 CHAR) NOT NULL ENABLE, 
    ANIO NUMBER(10,0) NOT NULL ENABLE, 
    CUATRIMESTRE VARCHAR2(30 CHAR), 
    ESTADO_INSPECCION VARCHAR2(30 CHAR), 
    FECHA_ANULACION TIMESTAMP (6), 
    FECHA_FINALIZACION TIMESTAMP (6), 
    FECHA_PREVISTA TIMESTAMP (6), 
    NOMBRE_UNIDAD VARCHAR2(255 CHAR), 
    NUMERO VARCHAR2(100 CHAR), 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    USERNAME_FINALIZACION VARCHAR2(255 CHAR), 
    ID_EQUIPO NUMBER(19,0) NOT NULL ENABLE, 
    ID_MUNICIPIO NUMBER(19,0), 
    TIPO_INSPECCION VARCHAR2(10 CHAR) NOT NULL ENABLE, 
    TIPO_UNIDAD NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_I_EQUIPO FOREIGN KEY (ID_EQUIPO)
    REFERENCES EQUIPO (ID) ENABLE, 
    CONSTRAINT FK_I_MUNICIPIO FOREIGN KEY (ID_MUNICIPIO)
    REFERENCES MUNICIPIOS (ID) ENABLE, 
    CONSTRAINT FK_I_TIPOUNIDAD FOREIGN KEY (TIPO_UNIDAD)
    REFERENCES TIPOS_UNIDAD (ID) ENABLE, 
    CONSTRAINT FK_I_TIPO_INSPECCION FOREIGN KEY (TIPO_INSPECCION)
    REFERENCES TIPOS_INSPECCION (CODIGO) ENABLE
   ) 
    partition by range(FECHA_ALTA)
    (
      partition INSPECCIONES_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition INSPECCIONES_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition INSPECCIONES_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition INSPECCIONES_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition INSPECCIONES_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_inspec_unidad ON INSPECCIONES (NOMBRE_UNIDAD);
    CREATE INDEX indice_inspec_fecha ON INSPECCIONES (FECHA_ALTA);

prompt =========================================================================
prompt  Creacion tabla  INSPECCIONES_ASOCIADAS
prompt =========================================================================

CREATE TABLE INSPECCIONES_ASOCIADAS 
   (    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION_ASOCIADA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FA_INSASO_INSPECCIONASOC FOREIGN KEY (ID_INSPECCION_ASOCIADA)
    REFERENCES INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_INSASO_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES INSPECCIONES (ID) ENABLE
   ) ;

prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS_BLOB
prompt =========================================================================

CREATE TABLE DOCUMENTOS_BLOB 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FICHERO BLOB, 
    NOMBRE_FICHERO VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ); 
/ 

prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS
prompt =========================================================================

CREATE TABLE DOCUMENTOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(2000 CHAR), 
    FECHA_ALTA TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    MATERIA_INDEXADA VARCHAR2(2000 CHAR), 
    NOMBRE VARCHAR2(255 CHAR), 
    TIPO_CONTENIDO VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_ALTA VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_FICHERO NUMBER(19,0), 
    TIPO_DOCUMENTO NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_DOC_TIPODOC FOREIGN KEY (TIPO_DOCUMENTO)
    REFERENCES TIPO_DOCUMENTO (ID) ENABLE, 
    CONSTRAINT FK_D_FICHERO FOREIGN KEY (ID_FICHERO)
    REFERENCES DOCUMENTOS_BLOB (ID) ENABLE
   ) 

partition by range(FECHA_ALTA)
    (
      partition DOCUMENTOS_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition DOCUMENTOS_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition DOCUMENTOS_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition DOCUMENTOS_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition DOCUMENTOS_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    
    CREATE INDEX indice_documentos ON DOCUMENTOS (FECHA_ALTA);
/

prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS_INSPECCION
prompt =========================================================================

CREATE TABLE DOCUMENTOS_INSPECCION 
   (    ID_DOCUMENTO NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_DOCUINS_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_DOCUINS_DOCUMENTO FOREIGN KEY (ID_DOCUMENTO)
    REFERENCES DOCUMENTOS (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  CONFIG_RESPUESTAS_CUESTIONARIO
prompt =========================================================================

  CREATE TABLE CONFIG_RESPUESTAS_CUESTIONARIO 
   (    CLAVE VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    SECCION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    VALOR VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (CLAVE, SECCION, VALOR) USING INDEX ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  MODELOSCUESTIONARIOS
prompt =========================================================================

CREATE TABLE MODELOSCUESTIONARIOS 
   (    ID NUMBER(10,0) NOT NULL ENABLE, 
    CODIGO VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    ESTANDAR NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE,
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   );
/

prompt =========================================================================
prompt  Creacion tabla  AREASCUESTIONARIO
prompt =========================================================================

CREATE TABLE AREASCUESTIONARIO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE_AREA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    ID_CUESTIONARIO NUMBER(10,0), 
    ORDEN NUMBER(10,0), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_AC_CUESTIONARIO FOREIGN KEY (ID_CUESTIONARIO)
    REFERENCES MODELOSCUESTIONARIOS (ID) ENABLE
   );  
/

prompt =========================================================================
prompt  Creacion tabla  PREGUNTASCUESTIONARIO
prompt =========================================================================

 CREATE TABLE PREGUNTASCUESTIONARIO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    ORDEN NUMBER(10,0), 
    PREGUNTA VARCHAR2(2000 CHAR) NOT NULL ENABLE, 
    TIPO_RESPUESTA VARCHAR2(100 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_AREA NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX  ENABLE, 
    CONSTRAINT FK_PC_AREA FOREIGN KEY (ID_AREA)
    REFERENCES AREASCUESTIONARIO (ID) ENABLE
   ); 

  
 prompt =========================================================================
prompt  Creacion tabla  CUESTIONARIO_PERSONALIZADO
prompt =========================================================================

CREATE TABLE CUESTIONARIO_PERSONALIZADO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_CREACION TIMESTAMP (6) NOT NULL ENABLE, 
    NOMBRE_CUESTIONARIO VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_CREACION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    ID_MODELO_CUESTIONARIO NUMBER(10,0) NOT NULL ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_CP_MODELO_CUESTIONARIO FOREIGN KEY (ID_MODELO_CUESTIONARIO)
    REFERENCES MODELOSCUESTIONARIOS (ID) ENABLE
   )
partition by range(FECHA_CREACION)
    (
      partition CUEST_PER_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition CUEST_PER_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition CUEST_PER_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition CUEST_PER_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition CUEST_PER_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_cuestper_cuest ON CUESTIONARIO_PERSONALIZADO (ID_MODELO_CUESTIONARIO);
    CREATE INDEX indice_cuestper_fecha ON CUESTIONARIO_PERSONALIZADO (FECHA_CREACION);
/  

prompt =========================================================================
prompt  Creacion tabla  CUEST_PER_PREGUNTAS
prompt =========================================================================

CREATE TABLE CUEST_PER_PREGUNTAS 
   (    ID_CUEST_PERS NUMBER(19,0) NOT NULL ENABLE, 
    ID_PREG_ELEGIDA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_CP_PREGELEGIDA FOREIGN KEY (ID_PREG_ELEGIDA)
    REFERENCES PREGUNTASCUESTIONARIO (ID) ENABLE, 
    CONSTRAINT FK_CP_CUESTIONARIOPER FOREIGN KEY (ID_CUEST_PERS)
    REFERENCES CUESTIONARIO_PERSONALIZADO (ID) ENABLE
   );
/

prompt =========================================================================
prompt  Creacion tabla  CUESTIONARIOS_ENVIADOS
prompt =========================================================================

CREATE TABLE CUESTIONARIOS_ENVIADOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    CARGO VARCHAR2(500 CHAR), 
    CORREO VARCHAR2(500 CHAR) NOT NULL ENABLE, 
    FECHA_ANULACION TIMESTAMP (6), 
    FECHA_CUMPLIMENTACION TIMESTAMP (6), 
    FECHA_ENVIO TIMESTAMP (6), 
    FECHA_FINALIZACION TIMESTAMP (6), 
    FECHA_LIMITE_CUESTIONARIO TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_NO_CONFORME TIMESTAMP (6), 
    MOTIVO VARCHAR2(2000 CHAR) NOT NULL ENABLE, 
    NOMBRE_USUARIO VARCHAR2(500 CHAR) NOT NULL ENABLE, 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    USERNAME_ENVIO VARCHAR2(255 CHAR), 
    USERNAME_FINALIZACION VARCHAR2(255 CHAR), 
    USERNAME_NO_CONFORME VARCHAR2(255 CHAR), 
    ID_CUESTIONARIO_PERSONALIZADO NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_CE_CUEST_PERSON FOREIGN KEY (ID_CUESTIONARIO_PERSONALIZADO)
    REFERENCES CUESTIONARIO_PERSONALIZADO (ID) ENABLE, 
    CONSTRAINT FK_CE_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES INSPECCIONES (ID) ENABLE
   )

partition by range(FECHA_ENVIO)
    (
      partition CUESTIONARIOS_ENVIADOS_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition CUESTIONARIOS_ENVIADOS_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition CUESTIONARIOS_ENVIADOS_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition CUESTIONARIOS_ENVIADOS_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition CUESTIONARIOS_ENVIADOS_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_cuestenv_cuest ON CUESTIONARIOS_ENVIADOS (ID_CUESTIONARIO_PERSONALIZADO);
    CREATE INDEX indice_cuestenv_fecha ON CUESTIONARIOS_ENVIADOS (FECHA_ENVIO);

prompt =========================================================================
prompt  Creacion tabla  RESPUESTASCUESTIONARIO
prompt =========================================================================

CREATE TABLE RESPUESTASCUESTIONARIO 
   (    FECHA_VALIDACION TIMESTAMP (6), 
    RESPUESTA_TEXTO VARCHAR2(2000 CHAR), 
    USERNAME_VALIDACION VARCHAR2(255 CHAR), 
    ID_CUEST_ENVIADO NUMBER(19,0) NOT NULL ENABLE, 
    ID_PREGUNTA NUMBER(19,0) NOT NULL ENABLE, 
    PRIMARY KEY (ID_CUEST_ENVIADO, ID_PREGUNTA) USING INDEX ENABLE, 
    CONSTRAINT FK_RC_CUEST_ENVIADO FOREIGN KEY (ID_CUEST_ENVIADO)
    REFERENCES CUESTIONARIOS_ENVIADOS (ID) ENABLE, 
    CONSTRAINT FK_RC_PREGUNTA FOREIGN KEY (ID_PREGUNTA)
    REFERENCES PREGUNTASCUESTIONARIO (ID) ENABLE
   );  


prompt =========================================================================
prompt  Creacion tabla  RESPUESTAS_CUEST_DOCS
prompt =========================================================================
CREATE TABLE RESPUESTAS_CUEST_DOCS 
   (    ID_CUESTIONARIO_ENVIADO NUMBER(19,0) NOT NULL ENABLE, 
    ID_PREGUNTA NUMBER(19,0) NOT NULL ENABLE, 
    ID_DOCUMENTO NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT INDICE_RESPUESTAS_CUEST UNIQUE (ID_DOCUMENTO) USING INDEX ENABLE, 
    CONSTRAINT FK_RESPCUESTDOCS FOREIGN KEY (ID_CUESTIONARIO_ENVIADO, ID_PREGUNTA)
    REFERENCES RESPUESTASCUESTIONARIO (ID_CUEST_ENVIADO, ID_PREGUNTA) ENABLE, 
    CONSTRAINT FK_RESPCUESTDOCS_DOCU FOREIGN KEY (ID_DOCUMENTO)
    REFERENCES DOCUMENTOS (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  RESPUESTA_DATOS_TABLA
prompt =========================================================================
CREATE TABLE RESPUESTA_DATOS_TABLA 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
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
    RESPUESTA_ID_PREGUNTA NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_RESPUESTADATOS FOREIGN KEY (RESPUESTA_ID_CUEST_ENVIADO, RESPUESTA_ID_PREGUNTA)
    REFERENCES RESPUESTASCUESTIONARIO (ID_CUEST_ENVIADO, ID_PREGUNTA) ENABLE
   );
    
prompt =========================================================================
prompt  Creacion tabla  AREAS_USUARIO_CUESTENV
prompt =========================================================================

   CREATE TABLE AREAS_USUARIO_CUESTENV 
   (    ID_AREA NUMBER(19,0) NOT NULL ENABLE, 
    ID_CUESTIONARIO_ENVIADO NUMBER(19,0) NOT NULL ENABLE, 
    USERNAME_PROV VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID_AREA, ID_CUESTIONARIO_ENVIADO)
  USING INDEX ENABLE
   ); 
/
prompt =========================================================================
prompt  Creacion tabla  CUEST_ENV_PLANTILLA
prompt =========================================================================
   
CREATE TABLE CUEST_ENV_PLANTILLA 
   (    ID_CUEST_ENV NUMBER(19,0) NOT NULL ENABLE, 
    ID_PLANTILLA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_DOC_PLANTILLA FOREIGN KEY (ID_PLANTILLA)
    REFERENCES DOCUMENTOS (ID) ENABLE, 
    CONSTRAINT FK_CUEST_PLANTILLA FOREIGN KEY (ID_CUEST_ENV)
    REFERENCES CUESTIONARIOS_ENVIADOS (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  SOLICITUD_DOC_PREVIA
prompt =========================================================================

CREATE TABLE SOLICITUD_DOC_PREVIA 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    APOYO_CORREO VARCHAR2(255 CHAR),  
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
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
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
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_ENVIO VARCHAR2(255 CHAR), 
    USERNAME_VALID_APOYO VARCHAR2(255 CHAR), 
    USERNAME_VALID_JEFE_EQUIPO VARCHAR2(255 CHAR), 
    USUARIO_FINALIZACION VARCHAR2(255 CHAR), 
    USUARIO_NO_CONFORME VARCHAR2(255 CHAR), 
    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_SDP_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES INSPECCIONES (ID) ENABLE
   ) 
partition by range(FECHA_ALTA)
    (
      partition SOLICITUD_DOC_PREVIA_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition SOLICITUD_DOC_PREVIA_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition SOLICITUD_DOC_PREVIA_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition SOLICITUD_DOC_PREVIA_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition SOLICITUD_DOC_PREVIA_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_soldocprevia_insp ON SOLICITUD_DOC_PREVIA (ID_INSPECCION);
    CREATE INDEX indice_soldocprevia_fecha ON SOLICITUD_DOC_PREVIA (FECHA_ALTA);
   
prompt =========================================================================
prompt  Creacion tabla  SOLICITUD_PREVIA_DOCS
prompt =========================================================================

CREATE TABLE SOLICITUD_PREVIA_DOCS 
   (    ID_SOLICITUD_PREVIA NUMBER(19,0) NOT NULL ENABLE, 
    ID_DOCUMENTO NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT INDICE_SOLICITUD_PREVIA UNIQUE (ID_DOCUMENTO) USING INDEX ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  GUIAS
prompt =========================================================================

CREATE TABLE GUIAS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    FECHA_ANULACION TIMESTAMP (6), 
    NOMBRE_GUIA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    ORDEN NUMBER(10,0), 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    TIPO_INSPECCION VARCHAR2(10 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_G_TIPO_INSPECCION FOREIGN KEY (TIPO_INSPECCION)
    REFERENCES TIPOS_INSPECCION (CODIGO) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  GUIA_PASOS
prompt =========================================================================

CREATE TABLE GUIA_PASOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    ORDEN NUMBER(10,0) NOT NULL ENABLE, 
    PASO VARCHAR2(2000 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_GUIA NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_GP_GUIA FOREIGN KEY (ID_GUIA)
    REFERENCES GUIAS (ID) ENABLE
   ) ;    

prompt =========================================================================
prompt  Creacion tabla  GUIA_PERSONALIZADA
prompt =========================================================================

CREATE TABLE GUIA_PERSONALIZADA 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ANULACION TIMESTAMP (6), 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_CREACION TIMESTAMP (6) NOT NULL ENABLE, 
    NOMBRE_GUIA_PERSONALIZADA VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    USERNAME_ANULACION VARCHAR2(255 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_CREACION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    ID_MODELO_GUIA NUMBER(19,0) NOT NULL ENABLE, 
    INSPECCION NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_GPR_INSPECCION FOREIGN KEY (INSPECCION)
    REFERENCES INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_GPR_MODELO_GUIA FOREIGN KEY (ID_MODELO_GUIA)
    REFERENCES GUIAS (ID) ENABLE
   );
  
prompt =========================================================================
prompt  Creacion tabla  GUIA_PERSONALIZADA_PASOS
prompt =========================================================================

CREATE TABLE GUIA_PERSONALIZADA_PASOS 
   (    ID_GUIA_PERS NUMBER(19,0) NOT NULL ENABLE, 
    ID_PASO_ELEGIDO NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_GUIAPERPASO_PASO FOREIGN KEY (ID_PASO_ELEGIDO)
    REFERENCES GUIA_PASOS (ID) ENABLE, 
    CONSTRAINT FK_GUIAPERPASO_GUIAPER FOREIGN KEY (ID_GUIA_PERS)
    REFERENCES GUIA_PERSONALIZADA (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  GUIA_INSPECCION
prompt =========================================================================
 CREATE TABLE GUIA_INSPECCION 
   (    ID_GUIA NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_GUIA_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_GUIA FOREIGN KEY (ID_GUIA)
    REFERENCES GUIA_PERSONALIZADA (ID) ENABLE
   ) ;

prompt =========================================================================
prompt  Creacion tabla  MODELOS_INFORME
prompt =========================================================================

CREATE TABLE MODELOS_INFORME 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    ESTANDAR NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    CONSTRAINT PK_MODELO_INFORME PRIMARY KEY (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  AREAS_INFORME
prompt =========================================================================

 CREATE TABLE AREAS_INFORME 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(1000 CHAR) NOT NULL ENABLE, 
    MODELO_INFORME_ID NUMBER(19,0) NOT NULL ENABLE, 
    ORDEN NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT PK_AREA_INFORME PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_AREA_MODELOINF FOREIGN KEY (MODELO_INFORME_ID)
    REFERENCES MODELOS_INFORME (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  SUBAREAS_INFORME
prompt =========================================================================

CREATE TABLE SUBAREAS_INFORME 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(1000 CHAR) NOT NULL ENABLE, 
    AREA_ID NUMBER(19,0) NOT NULL ENABLE, 
    ORDEN NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT PK_SUBAREA_INFORME PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_AREA_INFORME FOREIGN KEY (AREA_ID)
    REFERENCES AREAS_INFORME (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  MODELOS_INFORME_PERSONALIZADOS
prompt =========================================================================

CREATE TABLE MODELOS_INFORME_PERSONALIZADOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    ID_MODELO_INFORME NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    CONSTRAINT PK_INFORME_PERSO PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_MODELO_INFORME FOREIGN KEY (ID_MODELO_INFORME)
    REFERENCES MODELOS_INFORME (ID) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  INFORME_PERSONAL_SUBAREAS
prompt =========================================================================

 CREATE TABLE INFORME_PERSONAL_SUBAREAS 
   (    ID_INFORME_PERS NUMBER(19,0) NOT NULL ENABLE, 
    ID_SUBAREA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_SUBAREA_INF_PERS FOREIGN KEY (ID_SUBAREA)
    REFERENCES SUBAREAS_INFORME (ID) ENABLE, 
    CONSTRAINT FK_INFOR_PERS FOREIGN KEY (ID_INFORME_PERS)
    REFERENCES MODELOS_INFORME_PERSONALIZADOS (ID) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  INFORMES
prompt =========================================================================

CREATE TABLE INFORMES 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    INSPECCION_ID NUMBER(19,0) NOT NULL ENABLE, 
    INFORME_PERSONAL_ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_MODIFICACION TIMESTAMP (6), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_MODIF VARCHAR2(255 CHAR), 
    FECHA_FINALIZACION TIMESTAMP (6), 
    USERNAME_FINALIZACION VARCHAR2(255 CHAR), 
    MOTIVO_ANULACION VARCHAR2(2000 CHAR),
    CONSTRAINT PK_INFORME PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_INFOR_PERSONAL FOREIGN KEY (INFORME_PERSONAL_ID)
    REFERENCES MODELOS_INFORME_PERSONALIZADOS (ID) ENABLE, 
    CONSTRAINT FK_INSP_INFORME FOREIGN KEY (INSPECCION_ID)
    REFERENCES INSPECCIONES (ID) ENABLE
   )  
partition by range(FECHA_ALTA)
    (
      partition INFORMES_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition INFORMES_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition INFORMES_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition INFORMES_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition INFORMES_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_informe_insp ON INFORMES (INSPECCION_ID);
    CREATE INDEX indice_informe_fecha ON INFORMES (FECHA_ALTA);
   
prompt =========================================================================
prompt  Creacion tabla  RESPUESTAS_INFORME
prompt =========================================================================

CREATE TABLE RESPUESTAS_INFORME 
   (    TEXTO BLOB, 
    CONCLUSIONES BLOB, 
    INFORME_ID NUMBER(19,0) NOT NULL ENABLE, 
    SUBAREA_ID NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT PK_RESPUESTA_INF PRIMARY KEY (INFORME_ID, SUBAREA_ID) USING INDEX ENABLE, 
    CONSTRAINT FK_RESPUESTA_INFOR FOREIGN KEY (INFORME_ID)
    REFERENCES INFORMES (ID) ENABLE, 
    CONSTRAINT FK_RESPUESTA_SUBAREA FOREIGN KEY (SUBAREA_ID)
    REFERENCES SUBAREAS_INFORME (ID) ENABLE
   ) ; 
  
prompt =========================================================================
prompt  Creacion tabla  ASIGN_SUBAREA_INFORME_USER
prompt =========================================================================

CREATE TABLE ASIGN_SUBAREA_INFORME_USER 
   (    INFORME_ID NUMBER(19,0) NOT NULL ENABLE, 
        SUBAREA_ID NUMBER(19,0) NOT NULL ENABLE,
        USERNAME VARCHAR2(255 CHAR),
        CONSTRAINT PK_ASIGN_INF PRIMARY KEY (INFORME_ID, SUBAREA_ID) USING INDEX ENABLE,
        CONSTRAINT FK_ASIGN_INFOR FOREIGN KEY (INFORME_ID)
            REFERENCES INFORMES (ID) ENABLE,
        CONSTRAINT FK_ASIGN_SUBAREA FOREIGN KEY (SUBAREA_ID)
            REFERENCES SUBAREAS_INFORME (ID) ENABLE,
        CONSTRAINT FK_ASIGN_USER FOREIGN KEY (USERNAME)
            REFERENCES USERS (USERNAME) ENABLE
   ) ;     
   
prompt =========================================================================
prompt + Tarea3
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
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (12,'HISTÓRICO INFORMES');
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
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (23,'VÍDEO');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (24,'PLANTILLA CUESTIONARIO');
Insert into TIPO_DOCUMENTO (ID,NOMBRE) values (25,'PLANTILLA SOLICITUD');

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
    INSERT INTO documentos_blob (id, fichero, nombre_fichero) VALUES (4, empty_blob(),'Plantilla Infraestructuras -Otras sedes- CG.docx') RETURN fichero INTO l_blob;
        l_bfile := BFILENAME('PLANTILLAS_PROGESIN', 'Plantilla Infraestructuras -Otras sedes- CG.docx');
        DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
        DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
        DBMS_LOB.CLOSE(l_bfile);    
    INSERT INTO documentos_blob (id, fichero, nombre_fichero) VALUES (5, empty_blob(),'Plantilla Infraestructuras -Otras sedes- PN.docx') RETURN fichero INTO l_blob;
        l_bfile := BFILENAME('PLANTILLAS_PROGESIN', 'Plantilla Infraestructuras -Otras sedes- PN.docx');
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

Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (1, 1,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Comandancia Guardia Civil', SYSDATE, 'system',25);
Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (2, 2,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Compañía Guardia Civil', SYSDATE, 'system',25);
Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (3, 3,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Zona Guardia Civil', SYSDATE, 'system',25);
Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (4, 4,'application/vnd.openxmlformats-officedocument.wordprocessingml.document','Plantilla infraestructuras Guardia Civil', SYSDATE, 'system',24);
Insert into documentos (id, id_fichero, tipo_contenido, nombre, fecha_alta, username_alta, tipo_documento) values (5, 5,'application/vnd.openxmlformats-officedocument.wordprocessingml.document','Plantilla infraestructuras Policía Nacional', SYSDATE, 'system',24);


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

Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (1,'GC','Actas de las Comisiones Regionales/Provinciales de Seguridad Privada de los dos últimos años y año en curso','PDF','AC_seguridad_privada');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (2,'PN','Actas de las Comisiones Regionales/Provinciales de Seguridad Privada de los dos últimos años y año en curso','PDF','AC_seguridad_privada');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (3,'GC','Actas de las Comisiones de Coordinación de Policía Judicial de los dos últimos años y año en curso','PDF','ACC_policia_judicial');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (4,'PN','Actas de las Comisiones de Coordinación de Policía Judicial de los dos últimos años y año en curso','PDF','ACC_policia_judicial');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (5,'GC','Actas de las Juntas Locales de Seguridad de los años de los dos últimos años y año en curso','PDF','AJ_locales_seguridad');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (6,'PN','Actas de las Juntas Locales de Seguridad de los años de los dos últimos años y año en curso','PDF','AJ_locales_seguridad');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (7,'GC','Actas de las Juntas de Coordinación de los dos últimos años y año en curso','PDF','AJ_coordinacion');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (8,'GC','Catálogo de Puestos de Trabajo (última aprobación CECIR) y fuerza en revista (Descargar de la aplicación la plantilla que deberá cumplimentar)','XLS, XLSX','CPT_CECIR');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (9,'GC','Convenios de colaboración con Policías Locales (tanto genéricos como específicos)','PDF','CC_policias_locales');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (10,'PN','Convenios de colaboración con Policías Locales (tanto genéricos como específicos)','PDF','CC_policias_locales');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (11,'PN','Actas de las reuniones sindicales celebradas en los dos últimos años y año en curso','PDF','A_reunion_sindical');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (12,'PN','Documentación relativa al Plan Territorial. Objetivos operativos fijados','PDF','Plan_territorial');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (13,'GC','Evaluación de Riesgos Laborales','PDF','Eval_riesgos_laborales');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (14,'PN','Evaluación de Riesgos Laborales','PDF','Eval_riesgos_laborales');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (15,'GC','Libro de Organización y Libro de Normas de Régimen Interno','PDF','Libro_org_libro_normas');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (16,'GC','Mapa con la distribución territorial de sus Unidades subordinadas','JPG, JPEG, PNG, BMP','Mapa');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (17,'PN','Mapa con la distribución territorial de sus Unidades subordinadas','JPG, JPEG, PNG, BMP','Mapa');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (18,'GC','Memoria Anual de los dos últimos años','PDF','Memoria_anual');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (19,'PN','Memoria Anual de los dos últimos años','PDF','Memoria_anual');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (20,'GC','Organigrama de la Unidad inspeccionada, con detalle de las Unidades Territoriales y Especialidades','DOC, DOCX, PPTX, PPT, PUB','Organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (21,'PN','Organigramas de la Unidad inspeccionada, con detalle de sus Unidades y Servicios','DOC, DOCX, PPTX, PPT, PUB','Organigrama');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (22,'GC','Plan anual vigente de la Unidad Orgánica de Policía Judicial, SEPRONA y otras especialidades que lo confeccionen','PDF','Plan_anual_especialidades');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (23,'GC','Plan de Emergencia','PDF','Plan_emergencia');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (24,'PN','Plan de Emergencia','PDF','Plan_emergencia');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (25,'GC','Plan de Seguridad Ciudadana','PDF','Plan_seguridad_ciudanada');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (26,'GC','Relación de mandos de las distintas Unidades, de categoría Oficial, con indicación de teléfonos y correos electrónicos de contacto','DOC, DOCX, PDF','Relacion_mandos');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (27,'GC','Relación de Núcleos Operativos constituidos. Puestos integrados en los mismos, con detalle de sus municipios, población y superficie','DOC, DOCX','Relac_nucleos_op_puestos');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (28,'PN','Relación del Equipo Directivo con indicación del nombre y apellidos, categoría y cargo, números de teléfono y dirección de correo electrónico','DOC, DOCX','Relacion_eq_directivo');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (29,'GC','Catálogo de puestos de trabajo Comandancia','XLSX','00_d_CPT_C');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (30,'GC','Catálogo de puestos de trabajo Compañía','XLSX','00_d_CPT_CIA');
Insert into TIPODOCUMENTACIONPREVIA (ID,AMBITO,DESCRIPCION,EXTENSIONES,NOMBRE) values (31,'GC','Catálogo de puestos de trabajo Zona','XLSX','00_d_CPT_Z');

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

--******************************************************************************************************

-- CAMBIAR LA URL DE ACCESO AL SISTEMA
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('URLPROGESIN','URLPROGESIN','http://194.224.253.45:8080/progesin');

-- CAMBIAR CONFIGURACIÓN DEL SERVIDOR DE CORREO
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.host','mail','localhost');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.port','mail','25');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.auth','mail','false');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('mail.smtp.starttls.enable','mail','true');

-- ELIMINAR EZENTIS COMO DOMINIO VÁLIDO EN PRODUCCIÓN
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('dominiosCorreo','dominiosCorreo','ezentis.com|interior.es|policia.es|dgp.mir.es|guardiacivil.org|guardiacivil.es');

--******************************************************************************************************

Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoCorreo','datosApoyo','apoyo_ipss@interior.es');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoPuesto','datosApoyo','Inspección de Personal y Servicios de Seguridad');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoTelefono','datosApoyo','915372505');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoDireccion','datosApoyo','C/ Cea Bermúdez, 35-37 - 28003 MADRID');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoFax','datosApoyo','915372600');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ApoyoSecretaria','datosApoyo','SECRETARIA DE ESTADO DE SEGURIDAD');
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
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plazoDiasDocumentacion','tareas',5);
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('plazoDiasCuestionario','tareas',5);
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('correoApoyo','tareas','apoyo_ipss@interior.es');



COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos PUESTOSTRABAJO...
prompt =========================================================================

Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (1,'Subdirector General',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (2,'Secretario Técnico',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (3,'Jefe de Servicios de Inspección',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (4,'Responsable Estudios y Programas',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (5,'Jefe de Equipo Inspecciones',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (6,'Inspector-Auditor',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (7,'Jefe de Apoyo',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (8,'Jefe de Sección',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (9,'Personal de Apoyo',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (10,'Jefe de Negociado',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (11,'Fuera de Catálogo (Conductores)',SYSDATE,'system');
Insert into PUESTOSTRABAJO (ID,DESCRIPCION,FECHA_ALTA,USERNAME_ALTA) values (12,'Secretaria de N30',SYSDATE,'system');
COMMIT;


prompt =========================================================================
prompt Ejecutando inserción de datos TIPOS_UNIDAD...
prompt =========================================================================

Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (1,'Cª Distrito');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (2,'Cª Local');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (3,'Cª Provincial');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (4,'Compañia');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (5,'CIE');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (6,'Comandancia');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (7,'JSP');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (8,'Puesto Fronterizo');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (9,'Servicio Central');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (10,'U.E. y D.');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (11,'Zona');


prompt =========================================================================
prompt Ejecutando inserción de datos USERS...
prompt =========================================================================

Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ezentis','ezentis','ezentis','correo@ezentis.com','111111111','A1',null,'ACTIVO',SYSDATE,null,SYSDATE,null,null,null,'20','Ezentis','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','665566544',null,null,'system',null,null,'2','8','17','1','2');
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_BAJA,FECHA_DESTINO_IPSS,FECHA_INACTIVO,FECHA_INGRESO,FECHA_MODIFICACION,NIVEL,NOMBRE,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,PASSWORD,ROLE,TELEFONO,TFNO_MOVIL_OFICIAL,TFNO_MOVIL_PARTICULAR,ID_CLASE,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO) values ('ipss_progesin','ipss_progesin',null,'ipss_progesin@interior.es','1111111','A1',null,'ACTIVO',SYSDATE,null,SYSDATE,null,null,null,'30','ipss_progesin','system',null,'system','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN',null,null,null,'2','2','8','17','1');


COMMIT;


prompt =========================================================================
prompt + Tarea4
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
  select decode(max(id), null, 1, max(id)+1) into maximo from documentos;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE  SEQ_DOCUMENTOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
    
  --secuencia SEQ_DOCUMENTOSBLOB  
  select decode(max(id), null, 1, max(id)+1) into maximo from documentos_blob;
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
 
   --secuencia   SEQ_MODELOINFORME
  EXECUTE IMMEDIATE 'CREATE SEQUENCE   SEQ_MODELOINFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  
  --secuencia   SEQ_INFORMEPERSONAL
  EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_INFORMEPERSONAL  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION';
  
    --secuencia   SEQ_AREASINFORME
  EXECUTE IMMEDIATE 'CREATE SEQUENCE   SEQ_AREASINFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

    --secuencia   SEQ_AREASINFORME
  EXECUTE IMMEDIATE 'CREATE SEQUENCE   SEQ_SUBAREASINFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;

end;




   