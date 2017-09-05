package es.mira.progesin.constantes;

/**
 * Clase de constantes.
 * 
 * @author Ezentis
 *
 */
public final class Constantes {
    
    /**
     * Email para el envío de correo a IPSS.
     */
    public static final String EMAILIPSS = "ipss_progesin@interior.es";
    
    /**
     * Tipo de respuesta MATRIZ para los cuestionarios.
     */
    public static final String TIPORESPUESTAMATRIZ = "MATRIZ";
    
    /**
     * Tipo de respuesta TABLA para los cuestionarios.
     */
    public static final String TIPORESPUESTATABLA = "TABLA";
    
    /**
     * Tipo de respuesta RADIOBUTTON para los cuestionarios.
     */
    public static final String TIPORESPUESTARADIO = "RADIO";
    
    /**
     * Logo del Ministerio del Interior para los documentos generados por la aplicación.
     */
    public static final String LOGOMININISTERIOINTERIOR = "static/images/ministerior_interior_logo.png";
    
    /**
     * Logo de IPSS para los documentos generados por la aplicación.
     */
    public static final String LOGOIPSS = "static/images/logo_ipss.png";
    
    /**
     * Ruta de la imagen que aparece en el header de la solicitud en la página 1 del pdf generado.
     */
    public static final String HEADERSOLDOCPAG1 = "static/images/header_sol_doc_pag_1.png";
    
    /**
     * Ruta del logo de calidad.
     */
    public static final String LOGOCALIDAD = "static/images/footer_solicitud_1.png";
    
    /**
     * Números del 0 al 9.
     */
    public static final String NUMBERS = "0123456789";
    
    /**
     * Letras del abecedario español.
     */
    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Palabra "login" para no repetirla n veces dentro de una clase.
     */
    public static final String LOGIN = "login";
    
    /**
     * Ruta "/login" para no repetirla n veces dentro de una clase.
     */
    public static final String RUTALOGIN = "/login";
    
    /**
     * Ruta "/logout" para no repetirla n veces dentro de una clase.
     */
    public static final String RUTALOGOUT = "/logout";
    
    /**
     * Palabra "Error" para no repetirla n veces dentro de una clase.
     */
    public static final String ERRORMENSAJE = "Error";
    
    /**
     * CSS utilizado en la generación de un informe en formato PDF.
     */
    public static final String CSSTEXTEDITORPDF = "/static/css/texteditor_pdf.css";
    
    // "upper(convert(replace(CAMPO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"+ VALOR + "', ' ', '')
    // || '%', 'US7ASCII'))"
    /**
     * Para buscar en oracle sin tener en cuenta las tildes.
     */
    public static final String COMPARADORSINACENTOS = "upper(convert(replace(%1$s, \' \', \'\'), \'US7ASCII\')) LIKE upper(convert(\'%%\' || replace(\'%2$s\', \' \', \'\') || \'%%\', \'US7ASCII\'))";
    
    /**
     * Palabra "fechaAlta" para no repetirla n veces dentro de una clase.
     */
    public static final String FECHAALTA = "fechaAlta";
    
    /**
     * Palabra "usernameAlta" para no repetirla n veces dentro de una clase.
     */
    public static final String USERNAMEALTA = "usernameAlta";
    
    /**
     * Palabra "fechaCreacion" para no repetirla n veces dentro de una clase.
     */
    public static final String FECHACREACION = "fechaCreacion";
    
    /**
     * Palabra "fechaFinalizacion" para no repetirla n veces dentro de una clase.
     */
    public static final String FECHAFINALIZACION = "fechaFinalizacion";
    
    /**
     * Palabra "fechaAnulacion" para no repetirla n veces dentro de una clase.
     */
    public static final String FECHAANULACION = "fechaAnulacion";
    
    /**
     * Palabra "fechaBaja" para no repetirla n veces dentro de una clase.
     */
    public static final String FECHABAJA = "fechaBaja";
    
    /**
     * Expresión regular para quitar tildes en java.
     */
    public static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
    /**
     * Factor de escala para las imágenes de cabecera de los documentos Word y PDF.
     */
    public static final float ESCALA = 0.6f;
    
    /**
     * Mensaje fallo envío correo.
     */
    public static final String FALLOCORREO = "Se ha producido un error al enviar el correo electrónico de aviso";
    
    /**
     * Constante que contiene el texto "La guía '".
     */
    public static final String LAGUIA = "La guía '";
    
    /**
     * Constante con el literal correspondiente a la extensión de los documentos PDF.
     */
    public static final String EXTENSIONPDF = ".pdf";
    
    /**
     * Constante ruta recurso imagen fondo de la portada de informes.
     */
    public static final String PORTADAINFORME = "/static/images/informe_portada.png";
    
    /**
     * Constante ruta recurso plantilla (dotx) para generar informes en word (docx).
     */
    public static final String TEMPLATEINFORMEDOTX = "/static/templates/templateInforme.dotx";
    
    /**
     * Constante ruta recurso imagen cabecera de las páginas del informes en PDF.
     */
    public static final String CABECERAINFORMEPDF = "/static/images/cabecera_informe.png";
    
    /**
     * Constante ruta recurso imagen pie de las páginas del informes en PDF.
     */
    public static final String PIEINFORMEPDF = "/static/images/pie_informe.png";
    
    /**
     * Tamaño de página para los listados.
     */
    public static final int TAMPAGINA = 20;
    
    /**
     * Constante ruta recurso imagen firma de comunicaciones del sistema.
     */
    public static final String ESCUDOIPSS = "/static/images/escudo_ipss.png";
    
    /**
     * Constante ruta recurso plantilla (html) base con etiquetas pebble para generar correos electrónicos con imágenes.
     */
    public static final String TEMPLATECORREOBASE = "/static/templates/templateCorreoBase.html";
    
    /**
     * Constante ruta recurso plantilla (html) con etiquetas pebble para generar correos electrónicos de recuperación de
     * contraseña.
     */
    public static final String TEMPLATECORREORESTAURARPASSWORD = "/static/templates/templateCorreoPassword.html";
    
    /**
     * Constante ruta recurso plantilla (html) con etiquetas pebble para generar correos electrónicos de recuperación de
     * contraseña.
     */
    public static final String TEMPLATECORREORESTABLECERPASSWORD = "/static/templates/templateRestablecerAcceso.html";
    
    /**
     * Constante ruta recurso plantilla (html) con etiquetas pebble para generar correos electrónicos de recuperación de
     * contraseña.
     */
    public static final String TEMPLATEALTAPLICACION = "/static/templates/templateAltaAplicacion.html";
    
    /**
     * Constante ruta recurso plantilla (html) con etiquetas pebble para generar correos electrónicos de recuperación de
     * contraseña.
     */
    public static final String TEMPLATEBAJACUESTIONARIO = "/static/templates/templateBajaCuestionario.html";
    
    /**
     * Constante ruta recurso plantilla (html) con etiquetas pebble para generar correos electrónicos de recuperación de
     * contraseña.
     */
    public static final String TEMPLATERECORDATORIOCUESTIONARIO = "/static/templates/templateRecordatorioEnvioCuestionario.html";
    
    /**
     * Constante ruta recurso plantilla (html) con etiquetas pebble para generar correos electrónicos de recuperación de
     * contraseña.
     */
    public static final String TEMPLATERECORDATORIOFINALIZACIONCUESTIONARIO = "/static/templates/templateRecordatorioEnvioCuestionario.html";
    
    /**
     * Constructor para que no se pueda instanciar la clase.
     */
    private Constantes() {
        throw new IllegalAccessError("Constantes class");
    }
    
}
