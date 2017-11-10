WHENEVER OSERROR EXIT FAILURE ROLLBACK 
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    SCRIPT IMPLANTACIÓN PROGESIN
prompt
prompt    Autor: EZENTIS
prompt
prompt    Actualización:  10/11/2017   
prompt =========================================================================

prompt =========================================================================
prompt + Tarea1
prompt =========================================================================
prompt Ejecutando creación de tablas...

prompt =========================================================================
prompt  Creacion tabla  PARAMETROS
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.PARAMETROS 
   (CLAVE VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    SECCION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    VALOR VARCHAR2(4000 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (CLAVE, SECCION, VALOR) ENABLE
   ); 


prompt =========================================================================
prompt  Creacion tabla  CLASE_USUARIO
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.CLASE_USUARIO 
   (ID_CLASE NUMBER(19,0) NOT NULL ENABLE, 
    CLASE VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID_CLASE) USING INDEX ENABLE
   );
  

prompt =========================================================================
prompt  Creacion tabla  CUERPOSESTADO
prompt =========================================================================

 CREATE TABLE PROGESIN_SYS.CUERPOSESTADO 
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
   
 
 prompt =========================================================================
prompt  Creacion tabla  DEPARTAMENTO
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.DEPARTAMENTO 
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


prompt =========================================================================
prompt  Creacion tabla  EMPLEO
prompt =========================================================================

 CREATE TABLE PROGESIN_SYS.EMPLEO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(100 CHAR), 
    NOMBRE_CORTO VARCHAR2(20 CHAR), 
    ID_CUERPO NUMBER(10,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_EM_CUERPO FOREIGN KEY (ID_CUERPO) REFERENCES PROGESIN_SYS.CUERPOSESTADO (ID) ENABLE
   ) ; 
   


prompt =========================================================================
prompt  Creacion tabla  PUESTOSTRABAJO
prompt =========================================================================

 CREATE TABLE PROGESIN_SYS.PUESTOSTRABAJO 
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

CREATE TABLE PROGESIN_SYS.PROVINCIAS 
   (    CODIGO VARCHAR2(3 CHAR) NOT NULL ENABLE, 
    CODIGO_MN VARCHAR2(10 CHAR), 
    NOMBRE VARCHAR2(100 CHAR), 
    PRIMARY KEY (CODIGO) USING INDEX ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  MUNICIPIOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.MUNICIPIOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NAME VARCHAR2(100 CHAR), 
    CODE_PROVINCE VARCHAR2(3 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_PROVINCIA FOREIGN KEY (CODE_PROVINCE)
    REFERENCES PROGESIN_SYS.PROVINCIAS (CODIGO) ENABLE
   ) ;    
 
 prompt =========================================================================
prompt  Creacion tabla  ALERTAS
prompt =========================================================================

   CREATE TABLE PROGESIN_SYS.ALERTAS 
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

    CREATE INDEX indice_alertas ON PROGESIN_SYS.ALERTAS (FECHA_REGISTRO);
    


prompt =========================================================================
prompt  Creacion tabla  NOTIFICACIONES
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.NOTIFICACIONES 
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

    CREATE INDEX indice_notificaciones ON PROGESIN_SYS.NOTIFICACIONES (FECHA_NOTIFICACION);
   
prompt =========================================================================
prompt  Creacion tabla  ALERTAS_NOTIFICACIONES_USUARIO
prompt =========================================================================

  CREATE TABLE PROGESIN_SYS.ALERTAS_NOTIFICACIONES_USUARIO 
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

    CREATE INDEX indice_notifalertas ON PROGESIN_SYS.ALERTAS_NOTIFICACIONES_USUARIO (FECHA_ALTA);


prompt =========================================================================
prompt  Creacion tabla  REG_ACTIVIDAD
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.REG_ACTIVIDAD 
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
   
    CREATE INDEX indice_regactividad ON PROGESIN_SYS.REG_ACTIVIDAD (FECHA_ALTA); 

prompt =========================================================================
prompt  Creacion tabla  SUGERENCIA
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.SUGERENCIA 
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
CREATE TABLE PROGESIN_SYS.TIPOS_UNIDAD 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(100 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  TIPODOCUMENTACIONPREVIA
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.TIPODOCUMENTACIONPREVIA 
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

CREATE TABLE PROGESIN_SYS.DOCUMENTACION_PREVIA 
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

CREATE TABLE PROGESIN_SYS.TIPO_DOCUMENTO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  TIPO_EQUIPO
prompt =========================================================================

 CREATE TABLE PROGESIN_SYS.TIPO_EQUIPO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    CODIGO VARCHAR2(5 CHAR), 
    DESCRIPCION VARCHAR2(100 CHAR), 
    CONSTRAINT INDICE_TIPO_EQUIPO UNIQUE (CODIGO) USING INDEX ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ) ;   

prompt =========================================================================
prompt  Creacion tabla  USERS
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.USERS 
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
    REFERENCES PROGESIN_SYS.CLASE_USUARIO (ID_CLASE) ENABLE, 
    CONSTRAINT FK_U_CUERPO FOREIGN KEY (ID_CUERPO)
    REFERENCES PROGESIN_SYS.CUERPOSESTADO (ID) ENABLE, 
    CONSTRAINT FK_U_DEPARTAMENTO FOREIGN KEY (ID_DEPARTAMENTO)
    REFERENCES PROGESIN_SYS.DEPARTAMENTO (ID) ENABLE, 
    CONSTRAINT FK_U_EMPLEO FOREIGN KEY (ID_EMPLEO)
    REFERENCES PROGESIN_SYS.EMPLEO (ID) ENABLE, 
    CONSTRAINT FK_U_PUESTO FOREIGN KEY (ID_PUESTO)
    REFERENCES PROGESIN_SYS.PUESTOSTRABAJO (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  EQUIPO
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.EQUIPO 
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
    REFERENCES PROGESIN_SYS.TIPO_EQUIPO (ID) ENABLE, 
    CONSTRAINT FK_EQ_JEFE FOREIGN KEY (JEFE_EQUIPO)
    REFERENCES PROGESIN_SYS.USERS (USERNAME) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  MIEMBROS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.MIEMBROS 
   (    ID NUMBER(19,0) NOT NULL ENABLE,  
    POSICION VARCHAR2(255 CHAR), 
    USUARIO VARCHAR2(255 CHAR), 
    ID_EQUIPO NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_M_EQUIPO FOREIGN KEY (ID_EQUIPO)
    REFERENCES PROGESIN_SYS.EQUIPO (ID) ENABLE, 
    CONSTRAINT FK_U_MIEMBRO FOREIGN KEY (USUARIO)
    REFERENCES PROGESIN_SYS.USERS (USERNAME) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  TIPOS_INSPECCION
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.TIPOS_INSPECCION 
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

CREATE TABLE PROGESIN_SYS.INSPECCIONES 
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
    REFERENCES PROGESIN_SYS.EQUIPO (ID) ENABLE, 
    CONSTRAINT FK_I_MUNICIPIO FOREIGN KEY (ID_MUNICIPIO)
    REFERENCES PROGESIN_SYS.MUNICIPIOS (ID) ENABLE, 
    CONSTRAINT FK_I_TIPOUNIDAD FOREIGN KEY (TIPO_UNIDAD)
    REFERENCES PROGESIN_SYS.TIPOS_UNIDAD (ID) ENABLE, 
    CONSTRAINT FK_I_TIPO_INSPECCION FOREIGN KEY (TIPO_INSPECCION)
    REFERENCES PROGESIN_SYS.TIPOS_INSPECCION (CODIGO) ENABLE
   ) 
    partition by range(FECHA_ALTA)
    (
      partition INSPECCIONES_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition INSPECCIONES_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition INSPECCIONES_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition INSPECCIONES_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition INSPECCIONES_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_inspec_unidad ON PROGESIN_SYS.INSPECCIONES (NOMBRE_UNIDAD);
    CREATE INDEX indice_inspec_fecha ON PROGESIN_SYS.INSPECCIONES (FECHA_ALTA);

prompt =========================================================================
prompt  Creacion tabla  INSPECCIONES_ASOCIADAS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.INSPECCIONES_ASOCIADAS 
   (    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION_ASOCIADA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FA_INSASO_INSPECCIONASOC FOREIGN KEY (ID_INSPECCION_ASOCIADA)
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_INSASO_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE
   ) ;

prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS_BLOB
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.DOCUMENTOS_BLOB 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FICHERO BLOB, 
    NOMBRE_FICHERO VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE
   ); 
 

prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.DOCUMENTOS 
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
    REFERENCES PROGESIN_SYS.TIPO_DOCUMENTO (ID) ENABLE, 
    CONSTRAINT FK_D_FICHERO FOREIGN KEY (ID_FICHERO)
    REFERENCES PROGESIN_SYS.DOCUMENTOS_BLOB (ID) ENABLE
   ) 
   partition by range(FECHA_ALTA)
    (
      partition DOCUMENTOS_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition DOCUMENTOS_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition DOCUMENTOS_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition DOCUMENTOS_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition DOCUMENTOS_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    
    CREATE INDEX indice_documentos ON PROGESIN_SYS.DOCUMENTOS (FECHA_ALTA);


prompt =========================================================================
prompt  Creacion tabla  DOCUMENTOS_INSPECCION
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.DOCUMENTOS_INSPECCION 
   (    ID_DOCUMENTO NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_DOCUINS_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_DOCUINS_DOCUMENTO FOREIGN KEY (ID_DOCUMENTO)
    REFERENCES PROGESIN_SYS.DOCUMENTOS (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  CONFIG_RESPUESTAS_CUESTIONARIO
prompt =========================================================================

  CREATE TABLE PROGESIN_SYS.CONFIG_RESPUESTAS_CUESTIONARIO 
   (    CLAVE VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    SECCION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    VALOR VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY (CLAVE, SECCION, VALOR) USING INDEX ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  MODELOSCUESTIONARIOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.MODELOSCUESTIONARIOS 
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


prompt =========================================================================
prompt  Creacion tabla  AREASCUESTIONARIO
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.AREASCUESTIONARIO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE_AREA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    ID_CUESTIONARIO NUMBER(10,0), 
    ORDEN NUMBER(10,0), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_AC_CUESTIONARIO FOREIGN KEY (ID_CUESTIONARIO)
    REFERENCES PROGESIN_SYS.MODELOSCUESTIONARIOS (ID) ENABLE
   );  


prompt =========================================================================
prompt  Creacion tabla  PREGUNTASCUESTIONARIO
prompt =========================================================================

 CREATE TABLE PROGESIN_SYS.PREGUNTASCUESTIONARIO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    ORDEN NUMBER(10,0), 
    PREGUNTA VARCHAR2(2000 CHAR) NOT NULL ENABLE, 
    TIPO_RESPUESTA VARCHAR2(100 CHAR), 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_AREA NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX  ENABLE, 
    CONSTRAINT FK_PC_AREA FOREIGN KEY (ID_AREA)
    REFERENCES PROGESIN_SYS.AREASCUESTIONARIO (ID) ENABLE
   ); 

  
 prompt =========================================================================
prompt  Creacion tabla  CUESTIONARIO_PERSONALIZADO
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_CREACION TIMESTAMP (6) NOT NULL ENABLE, 
    NOMBRE_CUESTIONARIO VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_CREACION VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    ID_MODELO_CUESTIONARIO NUMBER(10,0) NOT NULL ENABLE, 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_CP_MODELO_CUESTIONARIO FOREIGN KEY (ID_MODELO_CUESTIONARIO)
    REFERENCES PROGESIN_SYS.MODELOSCUESTIONARIOS (ID) ENABLE
   )
partition by range(FECHA_CREACION)
    (
      partition CUEST_PER_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition CUEST_PER_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition CUEST_PER_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition CUEST_PER_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition CUEST_PER_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_cuestper_cuest ON PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO (ID_MODELO_CUESTIONARIO);
    CREATE INDEX indice_cuestper_fecha ON PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO (FECHA_CREACION);
 

prompt =========================================================================
prompt  Creacion tabla  CUEST_PER_PREGUNTAS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.CUEST_PER_PREGUNTAS 
   (    ID_CUEST_PERS NUMBER(19,0) NOT NULL ENABLE, 
    ID_PREG_ELEGIDA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_CP_PREGELEGIDA FOREIGN KEY (ID_PREG_ELEGIDA)
    REFERENCES PROGESIN_SYS.PREGUNTASCUESTIONARIO (ID) ENABLE, 
    CONSTRAINT FK_CP_CUESTIONARIOPER FOREIGN KEY (ID_CUEST_PERS)
    REFERENCES PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO (ID) ENABLE
   );


prompt =========================================================================
prompt  Creacion tabla  CUESTIONARIOS_ENVIADOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.CUESTIONARIOS_ENVIADOS 
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
    REFERENCES PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO (ID) ENABLE, 
    CONSTRAINT FK_CE_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE
   )
  partition by range(FECHA_ENVIO)
    (
      partition CUESTIONARIOS_ENVIADOS_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition CUESTIONARIOS_ENVIADOS_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition CUESTIONARIOS_ENVIADOS_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition CUESTIONARIOS_ENVIADOS_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition CUESTIONARIOS_ENVIADOS_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_cuestenv_cuest ON PROGESIN_SYS.CUESTIONARIOS_ENVIADOS (ID_CUESTIONARIO_PERSONALIZADO);
    CREATE INDEX indice_cuestenv_fecha ON PROGESIN_SYS.CUESTIONARIOS_ENVIADOS (FECHA_ENVIO);

prompt =========================================================================
prompt  Creacion tabla  RESPUESTASCUESTIONARIO
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.RESPUESTASCUESTIONARIO 
   (    FECHA_VALIDACION TIMESTAMP (6), 
    RESPUESTA_TEXTO VARCHAR2(2000 CHAR), 
    USERNAME_VALIDACION VARCHAR2(255 CHAR), 
    ID_CUEST_ENVIADO NUMBER(19,0) NOT NULL ENABLE, 
    ID_PREGUNTA NUMBER(19,0) NOT NULL ENABLE, 
    PRIMARY KEY (ID_CUEST_ENVIADO, ID_PREGUNTA) USING INDEX ENABLE, 
    CONSTRAINT FK_RC_CUEST_ENVIADO FOREIGN KEY (ID_CUEST_ENVIADO)
    REFERENCES PROGESIN_SYS.CUESTIONARIOS_ENVIADOS (ID) ENABLE, 
    CONSTRAINT FK_RC_PREGUNTA FOREIGN KEY (ID_PREGUNTA)
    REFERENCES PROGESIN_SYS.PREGUNTASCUESTIONARIO (ID) ENABLE
   );  


prompt =========================================================================
prompt  Creacion tabla  RESPUESTAS_CUEST_DOCS
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.RESPUESTAS_CUEST_DOCS 
   (    ID_CUESTIONARIO_ENVIADO NUMBER(19,0) NOT NULL ENABLE, 
    ID_PREGUNTA NUMBER(19,0) NOT NULL ENABLE, 
    ID_DOCUMENTO NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT INDICE_RESPUESTAS_CUEST UNIQUE (ID_DOCUMENTO) USING INDEX ENABLE, 
    CONSTRAINT FK_RESPCUESTDOCS FOREIGN KEY (ID_CUESTIONARIO_ENVIADO, ID_PREGUNTA)
    REFERENCES PROGESIN_SYS.RESPUESTASCUESTIONARIO (ID_CUEST_ENVIADO, ID_PREGUNTA) ENABLE, 
    CONSTRAINT FK_RESPCUESTDOCS_DOCU FOREIGN KEY (ID_DOCUMENTO)
    REFERENCES PROGESIN_SYS.DOCUMENTOS (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  RESPUESTA_DATOS_TABLA
prompt =========================================================================
CREATE TABLE PROGESIN_SYS.RESPUESTA_DATOS_TABLA 
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
    REFERENCES PROGESIN_SYS.RESPUESTASCUESTIONARIO (ID_CUEST_ENVIADO, ID_PREGUNTA) ENABLE
   );
    
prompt =========================================================================
prompt  Creacion tabla  AREAS_USUARIO_CUESTENV
prompt =========================================================================

   CREATE TABLE PROGESIN_SYS.AREAS_USUARIO_CUESTENV 
   (    ID_AREA NUMBER(19,0) NOT NULL ENABLE, 
    ID_CUESTIONARIO_ENVIADO NUMBER(19,0) NOT NULL ENABLE, 
    USERNAME_PROV VARCHAR2(255 CHAR), 
    PRIMARY KEY (ID_AREA, ID_CUESTIONARIO_ENVIADO)
  USING INDEX ENABLE
   ); 

prompt =========================================================================
prompt  Creacion tabla  CUEST_ENV_PLANTILLA
prompt =========================================================================
   
CREATE TABLE PROGESIN_SYS.CUEST_ENV_PLANTILLA 
   (    ID_CUEST_ENV NUMBER(19,0) NOT NULL ENABLE, 
    ID_PLANTILLA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_DOC_PLANTILLA FOREIGN KEY (ID_PLANTILLA)
    REFERENCES PROGESIN_SYS.DOCUMENTOS (ID) ENABLE, 
    CONSTRAINT FK_CUEST_PLANTILLA FOREIGN KEY (ID_CUEST_ENV)
    REFERENCES PROGESIN_SYS.CUESTIONARIOS_ENVIADOS (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  SOLICITUD_DOC_PREVIA
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.SOLICITUD_DOC_PREVIA 
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
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE
   ) 
partition by range(FECHA_ALTA)
    (
      partition SOLICITUD_DOC_PREVIA_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition SOLICITUD_DOC_PREVIA_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition SOLICITUD_DOC_PREVIA_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition SOLICITUD_DOC_PREVIA_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition SOLICITUD_DOC_PREVIA_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_soldocprevia_insp ON PROGESIN_SYS.SOLICITUD_DOC_PREVIA (ID_INSPECCION);
    CREATE INDEX indice_soldocprevia_fecha ON PROGESIN_SYS.SOLICITUD_DOC_PREVIA (FECHA_ALTA);
   
prompt =========================================================================
prompt  Creacion tabla  SOLICITUD_PREVIA_DOCS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.SOLICITUD_PREVIA_DOCS 
   (    ID_SOLICITUD_PREVIA NUMBER(19,0) NOT NULL ENABLE, 
    ID_DOCUMENTO NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT INDICE_SOLICITUD_PREVIA UNIQUE (ID_DOCUMENTO) USING INDEX ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  GUIAS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.GUIAS 
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
    REFERENCES PROGESIN_SYS.TIPOS_INSPECCION (CODIGO) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  GUIA_PASOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.GUIA_PASOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    ORDEN NUMBER(10,0) NOT NULL ENABLE, 
    PASO VARCHAR2(2000 CHAR) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    ID_GUIA NUMBER(19,0), 
    PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_GP_GUIA FOREIGN KEY (ID_GUIA)
    REFERENCES PROGESIN_SYS.GUIAS (ID) ENABLE
   ) ;    

prompt =========================================================================
prompt  Creacion tabla  GUIA_PERSONALIZADA
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.GUIA_PERSONALIZADA 
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
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_GPR_MODELO_GUIA FOREIGN KEY (ID_MODELO_GUIA)
    REFERENCES PROGESIN_SYS.GUIAS (ID) ENABLE
   );
  
prompt =========================================================================
prompt  Creacion tabla  GUIA_PERSONALIZADA_PASOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.GUIA_PERSONALIZADA_PASOS 
   (    ID_GUIA_PERS NUMBER(19,0) NOT NULL ENABLE, 
    ID_PASO_ELEGIDO NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_GUIAPERPASO_PASO FOREIGN KEY (ID_PASO_ELEGIDO)
    REFERENCES PROGESIN_SYS.GUIA_PASOS (ID) ENABLE, 
    CONSTRAINT FK_GUIAPERPASO_GUIAPER FOREIGN KEY (ID_GUIA_PERS)
    REFERENCES PROGESIN_SYS.GUIA_PERSONALIZADA (ID) ENABLE
   );

prompt =========================================================================
prompt  Creacion tabla  GUIA_INSPECCION
prompt =========================================================================
 CREATE TABLE PROGESIN_SYS.GUIA_INSPECCION 
   (    ID_GUIA NUMBER(19,0) NOT NULL ENABLE, 
    ID_INSPECCION NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_GUIA_INSPECCION FOREIGN KEY (ID_INSPECCION)
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE, 
    CONSTRAINT FK_GUIA FOREIGN KEY (ID_GUIA)
    REFERENCES PROGESIN_SYS.GUIA_PERSONALIZADA (ID) ENABLE
   ) ;

prompt =========================================================================
prompt  Creacion tabla  MODELOS_INFORME
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.MODELOS_INFORME 
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

 CREATE TABLE PROGESIN_SYS.AREAS_INFORME 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(1000 CHAR) NOT NULL ENABLE, 
    MODELO_INFORME_ID NUMBER(19,0) NOT NULL ENABLE, 
    ORDEN NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT PK_AREA_INFORME PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_AREA_MODELOINF FOREIGN KEY (MODELO_INFORME_ID)
    REFERENCES PROGESIN_SYS.MODELOS_INFORME (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  SUBAREAS_INFORME
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.SUBAREAS_INFORME 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    DESCRIPCION VARCHAR2(1000 CHAR) NOT NULL ENABLE, 
    AREA_ID NUMBER(19,0) NOT NULL ENABLE, 
    ORDEN NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT PK_SUBAREA_INFORME PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_AREA_INFORME FOREIGN KEY (AREA_ID)
    REFERENCES PROGESIN_SYS.AREAS_INFORME (ID) ENABLE
   ) ; 
   
prompt =========================================================================
prompt  Creacion tabla  MODELOS_INFORME_PERSONALIZADOS
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.MODELOS_INFORME_PERSONALIZADOS 
   (    ID NUMBER(19,0) NOT NULL ENABLE, 
    NOMBRE VARCHAR2(100 CHAR) NOT NULL ENABLE, 
    ID_MODELO_INFORME NUMBER(19,0) NOT NULL ENABLE, 
    FECHA_BAJA TIMESTAMP (6), 
    FECHA_ALTA TIMESTAMP (6) NOT NULL ENABLE, 
    USERNAME_BAJA VARCHAR2(255 CHAR), 
    USERNAME_ALTA VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    CONSTRAINT PK_INFORME_PERSO PRIMARY KEY (ID) USING INDEX ENABLE, 
    CONSTRAINT FK_MODELO_INFORME FOREIGN KEY (ID_MODELO_INFORME)
    REFERENCES PROGESIN_SYS.MODELOS_INFORME (ID) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  INFORME_PERSONAL_SUBAREAS
prompt =========================================================================

 CREATE TABLE PROGESIN_SYS.INFORME_PERSONAL_SUBAREAS 
   (    ID_INFORME_PERS NUMBER(19,0) NOT NULL ENABLE, 
    ID_SUBAREA NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT FK_SUBAREA_INF_PERS FOREIGN KEY (ID_SUBAREA)
    REFERENCES PROGESIN_SYS.SUBAREAS_INFORME (ID) ENABLE, 
    CONSTRAINT FK_INFOR_PERS FOREIGN KEY (ID_INFORME_PERS)
    REFERENCES PROGESIN_SYS.MODELOS_INFORME_PERSONALIZADOS (ID) ENABLE
   ) ; 

prompt =========================================================================
prompt  Creacion tabla  INFORMES
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.INFORMES 
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
    REFERENCES PROGESIN_SYS.MODELOS_INFORME_PERSONALIZADOS (ID) ENABLE, 
    CONSTRAINT FK_INSP_INFORME FOREIGN KEY (INSPECCION_ID)
    REFERENCES PROGESIN_SYS.INSPECCIONES (ID) ENABLE
   )  
partition by range(FECHA_ALTA)
    (
      partition INFORMES_2017 values less than (TO_DATE('01/01/2018','DD/MM/YYYY')),
      partition INFORMES_2018 values less than (TO_DATE('01/01/2019','DD/MM/yyyy')),
      partition INFORMES_2019 values less than (TO_DATE('01/01/2020','DD/MM/yyyy')),
      partition INFORMES_2020 values less than (TO_DATE('01/01/2021','DD/MM/yyyy')),
      partition INFORMES_2021 values less than (TO_DATE('01/01/2022','DD/MM/yyyy'))
    );

    CREATE INDEX indice_informe_insp ON PROGESIN_SYS.INFORMES (INSPECCION_ID);
    CREATE INDEX indice_informe_fecha ON PROGESIN_SYS.INFORMES (FECHA_ALTA);
   
prompt =========================================================================
prompt  Creacion tabla  RESPUESTAS_INFORME
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.RESPUESTAS_INFORME 
   (    TEXTO BLOB, 
    CONCLUSIONES BLOB, 
    INFORME_ID NUMBER(19,0) NOT NULL ENABLE, 
    SUBAREA_ID NUMBER(19,0) NOT NULL ENABLE, 
    CONSTRAINT PK_RESPUESTA_INF PRIMARY KEY (INFORME_ID, SUBAREA_ID) USING INDEX ENABLE, 
    CONSTRAINT FK_RESPUESTA_INFOR FOREIGN KEY (INFORME_ID)
    REFERENCES PROGESIN_SYS.INFORMES (ID) ENABLE, 
    CONSTRAINT FK_RESPUESTA_SUBAREA FOREIGN KEY (SUBAREA_ID)
    REFERENCES PROGESIN_SYS.SUBAREAS_INFORME (ID) ENABLE
   ) ; 
  
prompt =========================================================================
prompt  Creacion tabla  ASIGN_SUBAREA_INFORME_USER
prompt =========================================================================

CREATE TABLE PROGESIN_SYS.ASIGN_SUBAREA_INFORME_USER 
   (    INFORME_ID NUMBER(19,0) NOT NULL ENABLE, 
        SUBAREA_ID NUMBER(19,0) NOT NULL ENABLE,
        USERNAME VARCHAR2(255 CHAR),
        CONSTRAINT PK_ASIGN_INF PRIMARY KEY (INFORME_ID, SUBAREA_ID) USING INDEX ENABLE,
        CONSTRAINT FK_ASIGN_INFOR FOREIGN KEY (INFORME_ID)
            REFERENCES PROGESIN_SYS.INFORMES (ID) ENABLE,
        CONSTRAINT FK_ASIGN_SUBAREA FOREIGN KEY (SUBAREA_ID)
            REFERENCES PROGESIN_SYS.SUBAREAS_INFORME (ID) ENABLE,
        CONSTRAINT FK_ASIGN_USER FOREIGN KEY (USERNAME)
            REFERENCES PROGESIN_SYS.USERS (USERNAME) ENABLE
   ) ;     
   
   
   
prompt =========================================================================
prompt + Tarea2
prompt =========================================================================
prompt Dando permisos al esquema PROGESIN_APP...

GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.PARAMETROS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CLASE_USUARIO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CUERPOSESTADO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.DEPARTAMENTO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.EMPLEO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.PUESTOSTRABAJO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.PROVINCIAS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.MUNICIPIOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.ALERTAS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.NOTIFICACIONES TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.ALERTAS_NOTIFICACIONES_USUARIO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.REG_ACTIVIDAD TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.SUGERENCIA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.TIPOS_UNIDAD TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.TIPODOCUMENTACIONPREVIA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.DOCUMENTACION_PREVIA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.TIPO_DOCUMENTO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.TIPO_EQUIPO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.USERS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.EQUIPO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.MIEMBROS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.TIPOS_INSPECCION TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.INSPECCIONES TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.INSPECCIONES_ASOCIADAS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.DOCUMENTOS_BLOB TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.DOCUMENTOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.DOCUMENTOS_INSPECCION TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CONFIG_RESPUESTAS_CUESTIONARIO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.MODELOSCUESTIONARIOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.AREASCUESTIONARIO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.PREGUNTASCUESTIONARIO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CUEST_PER_PREGUNTAS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CUESTIONARIOS_ENVIADOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.RESPUESTASCUESTIONARIO TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.RESPUESTAS_CUEST_DOCS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.RESPUESTA_DATOS_TABLA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.AREAS_USUARIO_CUESTENV TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.CUEST_ENV_PLANTILLA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.SOLICITUD_DOC_PREVIA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.SOLICITUD_PREVIA_DOCS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.GUIAS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.GUIA_PASOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.GUIA_PERSONALIZADA TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.GUIA_PERSONALIZADA_PASOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.GUIA_INSPECCION TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.MODELOS_INFORME TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.AREAS_INFORME TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.SUBAREAS_INFORME TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.MODELOS_INFORME_PERSONALIZADOS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.INFORME_PERSONAL_SUBAREAS TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.INFORMES TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.RESPUESTAS_INFORME TO PROGESIN_APP;
GRANT DELETE, INSERT, UPDATE, SELECT on PROGESIN_SYS.ASIGN_SUBAREA_INFORME_USER TO PROGESIN_APP;

prompt =========================================================================
prompt + Tarea3
prompt =========================================================================
prompt Ejecutando creación sinónimos...

CREATE PUBLIC SYNONYM PARAMETROS for PROGESIN_SYS.PARAMETROS;
CREATE PUBLIC SYNONYM CLASE_USUARIO for PROGESIN_SYS.CLASE_USUARIO;
CREATE PUBLIC SYNONYM CUERPOSESTADO for PROGESIN_SYS.CUERPOSESTADO;
CREATE PUBLIC SYNONYM DEPARTAMENTO for PROGESIN_SYS.DEPARTAMENTO;
CREATE PUBLIC SYNONYM EMPLEO for PROGESIN_SYS.EMPLEO;
CREATE PUBLIC SYNONYM PUESTOSTRABAJO for PROGESIN_SYS.PUESTOSTRABAJO;
CREATE PUBLIC SYNONYM PROVINCIAS for PROGESIN_SYS.PROVINCIAS;
CREATE PUBLIC SYNONYM MUNICIPIOS for PROGESIN_SYS.MUNICIPIOS;
CREATE PUBLIC SYNONYM ALERTAS for PROGESIN_SYS.ALERTAS;
CREATE PUBLIC SYNONYM NOTIFICACIONES for PROGESIN_SYS.NOTIFICACIONES;
CREATE PUBLIC SYNONYM ALERTAS_NOTIFICACIONES_USUARIO for PROGESIN_SYS.ALERTAS_NOTIFICACIONES_USUARIO;
CREATE PUBLIC SYNONYM REG_ACTIVIDAD for PROGESIN_SYS.REG_ACTIVIDAD;
CREATE PUBLIC SYNONYM SUGERENCIA for PROGESIN_SYS.SUGERENCIA;
CREATE PUBLIC SYNONYM TIPOS_UNIDAD for PROGESIN_SYS.TIPOS_UNIDAD;
CREATE PUBLIC SYNONYM TIPODOCUMENTACIONPREVIA for PROGESIN_SYS.TIPODOCUMENTACIONPREVIA;
CREATE PUBLIC SYNONYM DOCUMENTACION_PREVIA for PROGESIN_SYS.DOCUMENTACION_PREVIA;
CREATE PUBLIC SYNONYM TIPO_DOCUMENTO for PROGESIN_SYS.TIPO_DOCUMENTO;
CREATE PUBLIC SYNONYM TIPO_EQUIPO for PROGESIN_SYS.TIPO_EQUIPO;
CREATE PUBLIC SYNONYM USERS for PROGESIN_SYS.USERS;
CREATE PUBLIC SYNONYM EQUIPO for PROGESIN_SYS.EQUIPO;
CREATE PUBLIC SYNONYM MIEMBROS for PROGESIN_SYS.MIEMBROS;
CREATE PUBLIC SYNONYM TIPOS_INSPECCION for PROGESIN_SYS.TIPOS_INSPECCION;
CREATE PUBLIC SYNONYM INSPECCIONES for PROGESIN_SYS.INSPECCIONES;
CREATE PUBLIC SYNONYM INSPECCIONES_ASOCIADAS for PROGESIN_SYS.INSPECCIONES_ASOCIADAS;
CREATE PUBLIC SYNONYM DOCUMENTOS_BLOB for PROGESIN_SYS.DOCUMENTOS_BLOB;
CREATE PUBLIC SYNONYM DOCUMENTOS for PROGESIN_SYS.DOCUMENTOS;
CREATE PUBLIC SYNONYM DOCUMENTOS_INSPECCION for PROGESIN_SYS.DOCUMENTOS_INSPECCION;
CREATE PUBLIC SYNONYM CONFIG_RESPUESTAS_CUESTIONARIO for PROGESIN_SYS.CONFIG_RESPUESTAS_CUESTIONARIO;
CREATE PUBLIC SYNONYM MODELOSCUESTIONARIOS for PROGESIN_SYS.MODELOSCUESTIONARIOS;
CREATE PUBLIC SYNONYM AREASCUESTIONARIO for PROGESIN_SYS.AREASCUESTIONARIO;
CREATE PUBLIC SYNONYM PREGUNTASCUESTIONARIO for PROGESIN_SYS.PREGUNTASCUESTIONARIO;
CREATE PUBLIC SYNONYM CUESTIONARIO_PERSONALIZADO for PROGESIN_SYS.CUESTIONARIO_PERSONALIZADO;
CREATE PUBLIC SYNONYM CUEST_PER_PREGUNTAS for PROGESIN_SYS.CUEST_PER_PREGUNTAS;
CREATE PUBLIC SYNONYM CUESTIONARIOS_ENVIADOS for PROGESIN_SYS.CUESTIONARIOS_ENVIADOS;
CREATE PUBLIC SYNONYM RESPUESTASCUESTIONARIO for PROGESIN_SYS.RESPUESTASCUESTIONARIO;
CREATE PUBLIC SYNONYM RESPUESTAS_CUEST_DOCS for PROGESIN_SYS.RESPUESTAS_CUEST_DOCS;
CREATE PUBLIC SYNONYM RESPUESTA_DATOS_TABLA for PROGESIN_SYS.RESPUESTA_DATOS_TABLA;
CREATE PUBLIC SYNONYM AREAS_USUARIO_CUESTENV for PROGESIN_SYS.AREAS_USUARIO_CUESTENV;
CREATE PUBLIC SYNONYM CUEST_ENV_PLANTILLA for PROGESIN_SYS.CUEST_ENV_PLANTILLA;
CREATE PUBLIC SYNONYM SOLICITUD_DOC_PREVIA for PROGESIN_SYS.SOLICITUD_DOC_PREVIA;
CREATE PUBLIC SYNONYM SOLICITUD_PREVIA_DOCS for PROGESIN_SYS.SOLICITUD_PREVIA_DOCS;
CREATE PUBLIC SYNONYM GUIAS for PROGESIN_SYS.GUIAS;
CREATE PUBLIC SYNONYM GUIA_PASOS for PROGESIN_SYS.GUIA_PASOS;
CREATE PUBLIC SYNONYM GUIA_PERSONALIZADA for PROGESIN_SYS.GUIA_PERSONALIZADA;
CREATE PUBLIC SYNONYM GUIA_PERSONALIZADA_PASOS for PROGESIN_SYS.GUIA_PERSONALIZADA_PASOS;
CREATE PUBLIC SYNONYM GUIA_INSPECCION for PROGESIN_SYS.GUIA_INSPECCION;
CREATE PUBLIC SYNONYM MODELOS_INFORME for PROGESIN_SYS.MODELOS_INFORME;
CREATE PUBLIC SYNONYM AREAS_INFORME for PROGESIN_SYS.AREAS_INFORME;
CREATE PUBLIC SYNONYM SUBAREAS_INFORME for PROGESIN_SYS.SUBAREAS_INFORME;
CREATE PUBLIC SYNONYM MODELOS_INFORME_PERSONALIZADOS for PROGESIN_SYS.MODELOS_INFORME_PERSONALIZADOS;
CREATE PUBLIC SYNONYM INFORME_PERSONAL_SUBAREAS for PROGESIN_SYS.INFORME_PERSONAL_SUBAREAS;
CREATE PUBLIC SYNONYM INFORMES for PROGESIN_SYS.INFORMES;
CREATE PUBLIC SYNONYM RESPUESTAS_INFORME for PROGESIN_SYS.RESPUESTAS_INFORME;
CREATE PUBLIC SYNONYM ASIGN_SUBAREA_INFORME_USER for PROGESIN_SYS.ASIGN_SUBAREA_INFORME_USER;


   
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

Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.G.P.',SYSDATE,null,null,'system',null,null,'General Periódica');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.G.S.',SYSDATE,null,null,'system',null,null,'General de Seguimiento');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.T_PRL',SYSDATE,null,null,'system',null,null,'Temática PRL');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.T_CIE',SYSDATE,null,null,'system',null,null,'Temática CIE');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.T_OT',SYSDATE,null,null,'system',null,null,'Temática otros');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('EX.I.INCD',SYSDATE,null,null,'system',null,null,'Incidental extraordinaria');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('I.E_PRL',SYSDATE,null,null,'system',null,null,'PRL Extraordinaria');
Insert into TIPOS_INSPECCION (CODIGO,FECHA_ALTA,FECHA_BAJA,FECHA_MODIFICACION,USERNAME_ALTA,USERNAME_BAJA,USERNAME_MODIF,DESCRIPCION) values ('EX.I.PUNT',SYSDATE,null,null,'system',null,null,'Puntual Extraordinaria');
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
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ODT','extensiones','application/vnd.oasis.opendocument.text');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PPT','extensiones','application/vnd.ms-powerpoint');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PPTX','extensiones','application/vnd.openxmlformats-officedocument.presentationml.presentation');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ODP','extensiones','application/vnd.oasis.opendocument.presentation');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('XLS','extensiones','application/vnd.ms-excel');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('XLSX','extensiones','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ODS','extensiones','application/vnd.oasis.opendocument.spreadsheet');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('JPEG','extensiones','image/jpeg');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PNG','extensiones','image/png');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('BMP','extensiones','image/bmp');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PUB','extensiones','application/x-mspublisher');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('PDF','extensiones','application/pdf');
Insert into PARAMETROS (CLAVE,SECCION,VALOR) values ('ZIP','extensiones','application/x-zip-compressed');
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

Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (1,'Comisaría de Distrito');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (2,'Comisaría Local');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (3,'Comisaría Provincial');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (4,'Compañía');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (5,'Centro de Internamiento de Extranjeros');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (6,'Comandancia');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (7,'Jefatura Superior de Policía');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (8,'Puesto Fronterizo');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (9,'Servicio Central');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (10,'U.E. y D.');
Insert into TIPOS_UNIDAD (ID,DESCRIPCION) values (11,'Zona');

prompt =========================================================================
prompt + Tarea5
prompt =========================================================================
prompt Ejecutando creación de secuencias...


declare
  maximo number;
begin
 
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_ALERTA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_AREASCUESTIONARIOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  select max(id_clase)+1 into maximo from PROGESIN_SYS.CLASE_USUARIO;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_CLASE  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || '  CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  select max(id)+1 into maximo from PROGESIN_SYS.DEPARTAMENTO;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_DEPARTAMENTO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || '  CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  select max(id)+1 into maximo from PROGESIN_SYS.EMPLEO;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_EMPLEO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || '  CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  select max(id)+1 into maximo from PROGESIN_SYS.cuerposestado;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_CUERPOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_CUESTIONARIOPERSONALIZADO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_CUESTIONARIOSENVIADOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_DOCUMENTACION_PREVIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  select max(id)+1 into maximo from PROGESIN_SYS.PUESTOSTRABAJO;    
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_PUESTO_TRABAJO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_DOCUMENTOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_DOCUMENTOSBLOB  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_EQUIPO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_GUIAS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_GUIAPERSONALIZADA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_INSPECCION  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_MIEMBROS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_MODELOSCUESTIONARIOS  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_NOTIFICACIONES  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_PREGUNTASCUESTIONARIO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_PASOSGUIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_REGISTRO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_RESPUESTATABLA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_SOLDOCPREVIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  select max(id)+1 into maximo from PROGESIN_SYS.tipodocumentacionprevia;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_TIPODOCUMENTACIONPREVIA  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH ' || maximo || ' CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_TIPO_EQUIPO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_MUNICIPIO  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_sugerencias  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_INFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION';
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_MODELOINFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_INFORMEPERSONAL  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION';
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_AREASINFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
  EXECUTE IMMEDIATE 'CREATE SEQUENCE PROGESIN_SYS.SEQ_SUBAREASINFORME  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOPARTITION' ;
end;
/

GRANT SELECT on PROGESIN_SYS.SEQ_ALERTA TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_AREASCUESTIONARIOS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_AREASINFORME TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_CLASE TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_CUERPOS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_CUESTIONARIOPERSONALIZADO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_CUESTIONARIOSENVIADOS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_DEPARTAMENTO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_DOCUMENTACION_PREVIA TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_DOCUMENTOS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_DOCUMENTOSBLOB TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_EMPLEO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_EQUIPO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_GUIAPERSONALIZADA TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_GUIAS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_INFORME TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_INFORMEPERSONAL TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_INSPECCION TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_MIEMBROS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_MODELOINFORME TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_MODELOSCUESTIONARIOS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_MUNICIPIO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_NOTIFICACIONES TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_PASOSGUIA TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_PREGUNTASCUESTIONARIO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_PUESTO_TRABAJO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_REGISTRO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_RESPUESTATABLA TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_SOLDOCPREVIA TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_SUBAREASINFORME TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_SUGERENCIAS TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_TIPO_EQUIPO TO PROGESIN_APP;
GRANT SELECT on PROGESIN_SYS.SEQ_TIPODOCUMENTACIONPREVIA TO PROGESIN_APP;

CREATE PUBLIC SYNONYM SEQ_ALERTA FOR PROGESIN_SYS.SEQ_ALERTA;
CREATE PUBLIC SYNONYM SEQ_AREASCUESTIONARIOS FOR PROGESIN_SYS.SEQ_AREASCUESTIONARIOS;
CREATE PUBLIC SYNONYM SEQ_AREASINFORME FOR PROGESIN_SYS.SEQ_AREASINFORME;
CREATE PUBLIC SYNONYM SEQ_CLASE FOR PROGESIN_SYS.SEQ_CLASE;
CREATE PUBLIC SYNONYM SEQ_CUERPOS FOR PROGESIN_SYS.SEQ_CUERPOS;
CREATE PUBLIC SYNONYM SEQ_CUESTIONARIOPERSONALIZADO FOR PROGESIN_SYS.SEQ_CUESTIONARIOPERSONALIZADO;
CREATE PUBLIC SYNONYM SEQ_CUESTIONARIOSENVIADOS FOR PROGESIN_SYS.SEQ_CUESTIONARIOSENVIADOS;
CREATE PUBLIC SYNONYM SEQ_DEPARTAMENTO FOR PROGESIN_SYS.SEQ_DEPARTAMENTO;
CREATE PUBLIC SYNONYM SEQ_DOCUMENTACION_PREVIA FOR PROGESIN_SYS.SEQ_DOCUMENTACION_PREVIA;
CREATE PUBLIC SYNONYM SEQ_DOCUMENTOS FOR PROGESIN_SYS.SEQ_DOCUMENTOS;
CREATE PUBLIC SYNONYM SEQ_DOCUMENTOSBLOB FOR PROGESIN_SYS.SEQ_DOCUMENTOSBLOB;
CREATE PUBLIC SYNONYM SEQ_EMPLEO FOR PROGESIN_SYS.SEQ_EMPLEO;
CREATE PUBLIC SYNONYM SEQ_EQUIPO FOR PROGESIN_SYS.SEQ_EQUIPO;
CREATE PUBLIC SYNONYM SEQ_GUIAPERSONALIZADA FOR PROGESIN_SYS.SEQ_GUIAPERSONALIZADA;
CREATE PUBLIC SYNONYM SEQ_GUIAS FOR PROGESIN_SYS.SEQ_GUIAS;
CREATE PUBLIC SYNONYM SEQ_INFORME FOR PROGESIN_SYS.SEQ_INFORME;
CREATE PUBLIC SYNONYM SEQ_INFORMEPERSONAL FOR PROGESIN_SYS.SEQ_INFORMEPERSONAL;
CREATE PUBLIC SYNONYM SEQ_INSPECCION FOR PROGESIN_SYS.SEQ_INSPECCION;
CREATE PUBLIC SYNONYM SEQ_MIEMBROS FOR PROGESIN_SYS.SEQ_MIEMBROS;
CREATE PUBLIC SYNONYM SEQ_MODELOINFORME FOR PROGESIN_SYS.SEQ_MODELOINFORME;
CREATE PUBLIC SYNONYM SEQ_MODELOSCUESTIONARIOS FOR PROGESIN_SYS.SEQ_MODELOSCUESTIONARIOS;
CREATE PUBLIC SYNONYM SEQ_MUNICIPIO FOR PROGESIN_SYS.SEQ_MUNICIPIO;
CREATE PUBLIC SYNONYM SEQ_NOTIFICACIONES FOR PROGESIN_SYS.SEQ_NOTIFICACIONES;
CREATE PUBLIC SYNONYM SEQ_PASOSGUIA FOR PROGESIN_SYS.SEQ_PASOSGUIA;
CREATE PUBLIC SYNONYM SEQ_PREGUNTASCUESTIONARIO FOR PROGESIN_SYS.SEQ_PREGUNTASCUESTIONARIO;
CREATE PUBLIC SYNONYM SEQ_PUESTO_TRABAJO FOR PROGESIN_SYS.SEQ_PUESTO_TRABAJO;
CREATE PUBLIC SYNONYM SEQ_REGISTRO FOR PROGESIN_SYS.SEQ_REGISTRO;
CREATE PUBLIC SYNONYM SEQ_RESPUESTATABLA FOR PROGESIN_SYS.SEQ_RESPUESTATABLA;
CREATE PUBLIC SYNONYM SEQ_SOLDOCPREVIA FOR PROGESIN_SYS.SEQ_SOLDOCPREVIA;
CREATE PUBLIC SYNONYM SEQ_SUBAREASINFORME FOR PROGESIN_SYS.SEQ_SUBAREASINFORME;
CREATE PUBLIC SYNONYM SEQ_SUGERENCIAS FOR PROGESIN_SYS.SEQ_SUGERENCIAS;
CREATE PUBLIC SYNONYM SEQ_TIPO_EQUIPO FOR PROGESIN_SYS.SEQ_TIPO_EQUIPO;
CREATE PUBLIC SYNONYM SEQ_TIPODOCUMENTACIONPREVIA FOR PROGESIN_SYS.SEQ_TIPODOCUMENTACIONPREVIA;



prompt =========================================================================
prompt Ejecutando inserción de datos USERS...
prompt =========================================================================

Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('manogal','NOGAL','GOMEZ','manogal@interior.es',1,'A1',123,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),30,'MIGUEL ANGEL ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','915372608','system',1,1,18,1,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rmartin','MARTÍN','PUYUELO','rmartin@interior.es',2,'A1',119,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),29,'RAFAEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','915372540','system',2,2,4,2,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jldominguez','DOMINGUEZ','ALONSO','jldominguez@interior.es',3,'A1',117,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),29,'JOSE LUIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_JEFE_INSPECCIONES','915372628','system',1,3,18,3,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ccasilla','CASILLAS ','CASILLAS','ccasilla@interior.es',4,'A1',111,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),28,'CASIMIRO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372546','system',1,4,18,5,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fjimenezm','JIMENEZ','MESA','fjimenezm@interior.es',5,'A1',115,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),28,'FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372558','system',2,4,4,5,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ariesco','RIESCO','SOBRÉ','ariesco@interior.es',6,'A1',131,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),28,'ANGEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372517','system',1,5,18,5,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rroman','ROMAN','MARTÍN-CONSUEGRA','rroman@interior.es',7,'A1',2533,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),28,'RAMON','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372533','system',1,4,18,5,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('csanchezh','SANCHEZ','HERNÁNDEZ','csanchezh@interior.es',8,'A1',2599,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),28,'CESAREO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372599','system',2,4,4,5,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jsuarez','SUÁREZ','LOSADA','jsuarez@interior.es',9,'A1',129,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),28,'JULIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372620','system',1,4,19,5,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mmdeantonio','ANTONIO','ANTOLÍN','mmdeantonio@interior.es',10,'A1',171,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MARIA MERCEDES','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372584','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('alvarado','ALVARADO','BALLESTEROS','alvarado@interior.es',11,'A1',140,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MARIA PILAR','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372605','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jbarba','BARBA','CEREZO','jbarba@interior.es',12,'A1',146,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JOSÉ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372617','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fernanc','CALATAYUD','DE LA HOZ','fernanc@interior.es',13,'A1',142,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'FERNANDO E. ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372619','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jcoronel','CORONEL','RODRÍGUEZ','jcoronel@interior.es',14,'A1',150,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JUAN FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372552','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mdominguez','DOMÍNGUEZ','ROCA','mdominguez@interior.es',15,'A1',192,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MARÍA','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372624','system',2,4,8,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jbastida','ESPERANZA','BASTIDA','jbastida@interior.es',16,'A1',169,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JUAN JOSÉ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372616','system',2,4,6,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('cfrutosc','FRUTOS','CABRERO','cfrutosc@interior.es',17,'A1',144,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'CARLOS DE','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372513','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ggarrido','GARRIDO','MAZOTERAS','ggarrido@interior.es',18,'A1',148,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'GUSTAVO A. ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372613','system',1,4,21,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fgonzale','GONZÁLEZ','LLANTADA','fgonzale@interior.es',19,'A1',172,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'FRANCISCO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372618','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmjimene','JIMENEZ','NARROS','jmjimene@interior.es',20,'A1',166,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JOSE MANUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372575','system',4,4,29,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jlealp','LEAL','PEREZ-OLAGÜE','jlealp@interior.es',21,'A1',198,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372512','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('xalopez','LOPEZ','FERNANDEZ','xalopez@interior.es',22,'A1',137,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JOSE ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372575','system',2,4,6,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('llorente','LLORENTE','BARRETO','llorente@interior.es',23,'A1',167,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JUAN','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372528','system',2,4,8,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('bmarting','MARTÍN','GARCÍA','bmarting@interior.es',24,'A1',191,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'BENIGNO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372629','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rmartinp','MARTÍN-POZUELO','LÓPEZ','rmartinp@interior.es',25,'A1',154,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'RAFAEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372551','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('fmfernandez','MARTINEZ','FERNANDEZ','fmfernandez@interior.es',26,'A1',147,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'FERNANDO E. ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372627','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rmartine','MARTÍNEZ ','ORTEGA','rmartine@interior.es',27,'A1',190,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'ROGELIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372653','system',1,4,22,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rjmartinez','MARTÍNEZ ','SERRANO','rjmartinez@interior.es',28,'A1',171,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'RAFAEL JESÚS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372652','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mmesa','MESA','HERRERUELA','mmesa@interior.es',29,'A1',155,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MANUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372633','system',2,4,5,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmiranda','MIRANDA','PEÑAS','jmiranda@interior.es',30,'A1',170,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372514','system',1,4,21,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('saturio','NIETO','GARRIDO','saturio@interior.es',31,'A1',168,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'SATURIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372523','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mortegam','ORTEGA','MEDINA','mortegam@interior.es',32,'A1',152,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MARTIN','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372545','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ipascual','PASCUAL','CUEVAS','ipascual@interior.es',33,'A1',157,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'IGNACIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372537','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mpradas','PRADAS','PÁRAMO','mpradas@interior.es',34,'A1',149,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MÓNICA','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372519','system',1,4,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rmriera','RIERA','FERNÁNDEZ','rmriera@interior.es',35,'A1',194,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'ROSA MARÍA','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372511','system',1,4,21,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('vromero','ROMERO','DE ANTONIO','vromero@interior.es',36,'A1',153,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'VICTOR E. ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372617','system',2,4,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('agonzapa','GONZÁLEZ','PASTRANA','agonzapa@interior.es',37,'A1',143,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'ATANASIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372657','system',1,7,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('agmedina','GONZÁLEZ','MEDINA','agmedina@interior.es',38,'A1',133,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372535','system',2,7,6,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('tgarlito','GARLITO','DIAZ','tgarlito@interior.es',39,'A1',197,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'TOMAS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372656','system',2,5,6,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jchorche','HORCHE','DELGADO','jchorche@interior.es',40,'A1',151,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JUAN CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372650','system',2,5,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jmmartin','MARTÍN','RUBIO','jmmartin@interior.es',41,'A1',193,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JUAN MANUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372525','system',1,5,22,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jcperez','PEREZ','SIGLER','jcperez@interior.es',42,'A1',196,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JUAN CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','915372587','system',1,5,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mmayo','MAYO','RODRÍGUEZ','mmayo@interior.es',43,'A1',156,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'MANUEL','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372541','system',1,8,21,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('alvazquez','VAZQUEZ','RODRÍGUEZ','alvazquez@interior.es',44,'A2',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),21,'ALFREDO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372622','system',2,8,12,7,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('dtrujillos','TRUJILLO ','SILVEIRA','dtrujillos@interior.es',45,'A2',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),21,'DÁMASO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372632','system',2,8,12,7,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('lmergeli','MERGELINA','GONZÁLEZ-ROBATTO','lmergeli@interior.es',46,'A2',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'LUIS F. ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372502','system',2,8,14,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('magperez','GARCÍA','PÉREZ','magperez@interior.es',47,'C1',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'MIGUEL ANGEL ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372625','system',2,8,15,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('abarrena','BARRENA','ARTALEJO','abarrena@interior.es',48,'C1',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'ALFONSO CARLOS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372607','system',2,8,17,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('sbastida','ESPERANZA','BASTIDA','sbastida@interior.es',49,'C1',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'JESÚS ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372565','system',2,8,17,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('mbotas','DOMÍNGUEZ','BOTAS','mbotas@interior.es',50,'C1',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'MARTA','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372626','system',1,8,24,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('barjollo','BARJOLLO','MALDONADO','barjollo@interior.es',51,'C1',159,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'FRANCISCO JAVIER','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372574','system',1,8,25,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('gromeral','ROMERAL','MARTÍNEZ','gromeral@interior.es',52,'C1',132,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'GREGORIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372647','system',2,7,17,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('imartinb','MARTÍN','BARBERO','imartinb@interior.es',53,'C1',132,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),18,'IÑAKI','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','915372658','system',1,7,25,9,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('cirujano','CIRUJANO','RODRÍGUEZ','cirujano@interior.es',54,'A1',136,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'FRANCISCO JAVIER ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_GABINETE','915372549','system',1,6,20,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('jgarciab','GARCÍA','BORREGO','jgarciab@interior.es',55,'A1',138,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JOSÉ ANTONIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_GABINETE','915372646','system',2,6,7,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('rherrera','HERRERA','ABIÁN','rherrera@interior.es',56,'A1',192,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'ROSARIO','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_GABINETE','915372655','system',4,6,28,6,1);
--Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('josess','SAN SEGUNDO','CORCHERO','josess@interior.es',57,'A1',135,'ACTIVO',sysdate,to_timestamp('01/01/2000','DD/MM/RR'),27,'JOSÉ','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_GABINETE','915372553','system',,6,,6,1);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('sgsics','SGSICS','SGSICS','jahb@interior.es',58,'A1',0,'ACTIVO',sysdate,to_timestamp('27/09/2017','DD/MM/RR'),20,'SGSICS','$2a$10$kgbRBJTPQoJVGgwtqkrQb.sQj3Z29szzorIcwDAsg2T5/FTJ0bQlO','ROLE_ADMIN','111111111','system',2,8,17,1,2);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ezentisAdmin','EZENTIS','EZENTIS','ramon.garcia@ezentis.com',58,'A1',0,'ACTIVO',sysdate,to_timestamp('27/09/2017','DD/MM/RR'),20,'EZENTIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_ADMIN','111111112','system',2,8,17,1,2);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ezentisJefeInspecciones','EZENTIS','EZENTIS','ramon.garcia@ezentis.com',58,'A1',0,'ACTIVO',sysdate,to_timestamp('27/09/2017','DD/MM/RR'),20,'EZENTIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_JEFE_INSPECCIONES','111111112','system',2,8,17,1,2);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ezentisApoyo','EZENTIS','EZENTIS','ramon.garcia@ezentis.com',58,'A1',0,'ACTIVO',sysdate,to_timestamp('27/09/2017','DD/MM/RR'),20,'EZENTIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_SERVICIO_APOYO','111111112','system',2,8,17,1,2);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ezentisEquipo','EZENTIS','EZENTIS','ramon.garcia@ezentis.com',58,'A1',0,'ACTIVO',sysdate,to_timestamp('27/09/2017','DD/MM/RR'),20,'EZENTIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_EQUIPO_INSPECCIONES','111111112','system',2,8,17,1,2);
Insert into USERS (USERNAME,PRIM_APELLIDO,SEGUNDO_APELLIDO,CORREO,DOC_IDENTIDAD,CATEGORIA,DESPACHO,ESTADO,FECHA_ALTA,FECHA_DESTINO_IPSS,NIVEL,NOMBRE,PASSWORD,ROLE,TELEFONO,USERNAME_ALTA,ID_CUERPO,ID_DEPARTAMENTO,ID_EMPLEO,ID_PUESTO,ID_CLASE) values ('ezentisGabinete','EZENTIS','EZENTIS','ramon.garcia@ezentis.com',58,'A1',0,'ACTIVO',sysdate,to_timestamp('27/09/2017','DD/MM/RR'),20,'EZENTIS','$2a$10$tDGyXBpEASeXlAUCdKsZ9u3MBBvT48xjA.v0lrDuRWlSZ6yfNsLve','ROLE_GABINETE','111111112','system',2,8,17,1,2);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','csanchezh','Equipo 1',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','csanchezh',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jmjimene',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','ipascual',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jlealp',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','llorente',seq_equipo.currval);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','ccasilla','Equipo 2',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','ccasilla',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','rjmartinez',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','rmartine',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jmiranda',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','fernanc',seq_equipo.currval);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','fjimenezm','Equipo 3',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','fjimenezm',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jbarba',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','cfrutosc',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','vromero',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','ggarrido',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','alvarado',seq_equipo.currval);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','mmesa','Equipo 4',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','mmesa',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','saturio',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','mortegam',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','rmriera',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','mdominguez',seq_equipo.currval);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','jsuarez','Equipo 5',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','jsuarez',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','fmfernandez',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jbastida',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','fgonzale',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','mpradas',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','rmartinp',seq_equipo.currval);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','rroman','Equipo 6',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','rroman',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','xalopez',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','mmdeantonio',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jcoronel',seq_equipo.currval);

Insert into EQUIPO (ID,FECHA_ALTA,USERNAME_ALTA,JEFE_EQUIPO,NOMBRE_EQUIPO,ID_TIPO_EQUIPO) values (seq_equipo.nextval,to_timestamp('21/09/2017','DD/MM/RR'),'system','ariesco','Equipo 7',1);

Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'JEFE_EQUIPO','ariesco',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','tgarlito',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jchorche',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jmmartin',seq_equipo.currval);
Insert into MIEMBROS (ID,POSICION,USUARIO,ID_EQUIPO) values (seq_miembros.nextval,'MIEMBRO','jcperez',seq_equipo.currval);


COMMIT;

   