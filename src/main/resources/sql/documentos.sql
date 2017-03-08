/* 
 Script para carga de documentos, ejecutar a parte después de iniciar la aplicación.
 
 PLANTILLAS:
   Tabla documento_blob -> id, fichero (blob)
   Tabla documentos     -> id, id_fichero (idDocumentoBlob), tipo_contenido (contentType), nombre (nombre archivo con extensión)
   Tabla parametros     -> seccion (plantillasGC/PN), clave (nombre documento), valor (idDocumento)
   
  Una vez ejecutado reiniciar la aplicación, la tabla parámetros se carga en el applicationBean
*/

CREATE OR REPLACE DIRECTORY PLANTILLAS_PROGESIN AS 'C:/Plantillas';
/
COMMIT;
/
DECLARE
	l_bfile  BFILE;
	l_blob   BLOB;
BEGIN

	INSERT INTO documentos_blob (id, fichero) VALUES (seq_documentosBlob.nextval, empty_blob()) RETURN fichero INTO l_blob;
		l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_C.xlsx');
		DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
		DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
		DBMS_LOB.CLOSE(l_bfile);
	insert into documentos (id, id_fichero, tipo_contenido, nombre) values (seq_documentos.nextval, seq_documentosBlob.currval,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_C.xlsx');
	insert into parametros(seccion, clave, valor) values ('plantillasGC','Comandancia', seq_documentos.currval);
	
	INSERT INTO documentos_blob (id, fichero) VALUES (seq_documentosBlob.nextval, empty_blob()) RETURN fichero INTO l_blob;
		l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_CIA.xlsx');
		DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
		DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
		DBMS_LOB.CLOSE(l_bfile);
	insert into documentos (id, id_fichero, tipo_contenido, nombre) values (seq_documentos.nextval, seq_documentosBlob.currval,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_CIA.xlsx');
	insert into parametros(seccion, clave, valor) values ('plantillasGC','Compañía', seq_documentos.currval);
		
	INSERT INTO documentos_blob (id, fichero) VALUES (seq_documentosBlob.nextval, empty_blob()) RETURN fichero INTO l_blob;
		l_bfile := BFILENAME('PLANTILLAS_PROGESIN', '00_d_CPT_Z.xlsx');
		DBMS_LOB.OPEN(l_bfile, DBMS_LOB.LOB_READONLY);
		DBMS_LOB.LOADFROMFILE(l_blob, l_bfile,dbms_lob.lobmaxsize);
		DBMS_LOB.CLOSE(l_bfile);
	insert into documentos (id, id_fichero, tipo_contenido, nombre) values (seq_documentos.nextval, seq_documentosBlob.currval,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','00_d_CPT_Z.xlsx');
	insert into parametros(seccion, clave, valor) values ('plantillasGC','Zona', seq_documentos.currval);
	
COMMIT;

END;
/

DROP DIRECTORY PLANTILLAS_PROGESIN;
COMMIT;
/