WHENEVER OSERROR EXIT FAILURE ROLLBACK
WHENEVER SQLERROR EXIT FAILURE ROLLBACK

prompt =========================================================================
prompt
prompt    LANZADOR SCRIPTS CARGA PROGESIN
prompt
prompt    Autor: EZENTIS
prompt
prompt    Fecha modificación: 30/11/2017
prompt =========================================================================


prompt =========================================================================
prompt Ejecutando SCRIPT LIMPIEZA TABLAS...
prompt =========================================================================

@limpia.sql

prompt =========================================================================
prompt Ejecutando SCRIPT IMPLANTACIÓN PROGESIN...
prompt =========================================================================

@scriptCarga.sql

prompt =========================================================================
prompt Carga finalizada...
prompt =========================================================================


prompt =========================================================================
prompt Ejecutando SCRIPT CARGA PROVINCIAS Y MUNICIPIOS...
prompt =========================================================================

@scriptProvinciasMunicipios.sql

prompt =========================================================================
prompt Ejecutando SCRIPT CARGA DE CUESTIONARIOS...
prompt =========================================================================

@scriptCuestionarios.sql

prompt =========================================================================
prompt Ejecutando SCRIPT CARGA DE GUIAS...
prompt =========================================================================

@scriptGuias.sql

prompt =========================================================================
prompt Ejecutando SCRIPT CARGA DE INFORMES...
prompt =========================================================================

@scriptInformes.sql

prompt =========================================================================
prompt Ejecutando SCRIPT CARGA DE INSPECCIONES...
prompt =========================================================================

@scriptInspecciones.sql

prompt =========================================================================
prompt Finalizada carga con éxito...
prompt =========================================================================