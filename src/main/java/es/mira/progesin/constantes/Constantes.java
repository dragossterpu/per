package es.mira.progesin.constantes;

/**
 * Clase de constantes
 * @author Ezentis
 *
 */
@SuppressWarnings("javadoc")
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
    
    public static final String ERROR_MENSAJE = "Error";
    
    // "upper(convert(replace(CAMPO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"+ VALOR + "', ' ', '')
    // || '%', 'US7ASCII'))"
    /**
     * Para buscar en oracle sin tener en cuenta las tildes
     */
    public static final String COMPARADORSINACENTOS = "upper(convert(replace(%1$s, \' \', \'\'), \'US7ASCII\')) LIKE upper(convert(\'%%\' || replace(\'%2$s\', \' \', \'\') || \'%%\', \'US7ASCII\'))";
    
    private Constantes() {
        throw new IllegalAccessError("Constantes class");
    }
    
}
