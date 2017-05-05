package es.mira.progesin.constantes;

/**
 * Clase de constantes
 * @author Ezentis
 *
 */
public final class Constantes {
    
    public static final String EMAIL_FROM_IPSS = "ipss_progesin@interior.es";
    
    public static final String TIPO_RESPUESTA_MATRIZ = "MATRIZ";
    
    public static final String TIPO_RESPUESTA_TABLA = "TABLA";
    
    public static final String TIPO_RESPUESTA_RADIO = "RADIO";
    
    public static final String LOGO_MININISTERIO_INTERIOR = "static/images/ministerior_interior_logo.png";
    
    public static final String LOGO_IPSS = "static/images/logo_ipss.png";
    
    public static final String NUMBERS = "0123456789";
    
    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static final String LOGIN = "login";
    
    public static final String RUTA_LOGIN = "/login";
    
    public static final String RUTA_LOGOUT = "/logout";
    
    /**
     * CSS utilizado en la generación de un informe en formato PDF
     */
    public static final String CSS_TEXT_EDITOR_PDF = "/static/css/texteditor_pdf.css";
    
    // "upper(convert(replace(CAMPO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"+ VALOR + "', ' ', '')
    // || '%', 'US7ASCII'))"
    public static final String COMPARADORSINACENTOS = "upper(convert(replace(%1$s, \' \', \'\'), \'US7ASCII\')) LIKE upper(convert(\'%%\' || replace(\'%2$s\', \' \', \'\') || \'%%\', \'US7ASCII\'))";
    
    private Constantes() {
        throw new IllegalAccessError("Constantes class");
    }
    
}
