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
    public static final double ESCALA = 0.6;
    
    /**
     * Mensaje fallo envío correo.
     */
    public static final String FALLOCORREO = "Se ha producido un error al enviar el correo electrónico de aviso";
    
    /**
     * Constructor para que no se pueda instanciar la clase.
     */
    private Constantes() {
        throw new IllegalAccessError("Constantes class");
    }
    
}
