package es.mira.progesin.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import es.mira.progesin.constantes.Constantes;

/**
 * Métodos de utilidades.
 * 
 * @author EZENTIS
 *
 */
public class Utilities {
    /**
     * Evitamos que se pueda instanciar la clase.
     */
    private Utilities() {
        throw new IllegalAccessError("Utility class");
    }
    
    // ************* Generating new password Progesin ********************//
    /**
     * Devuelve una contraseña basada en una combinación de letras y números.
     * 
     * @author EZENTIS
     * @return contraseña
     */
    public static String getPassword() {
        return getPinLetters() + getPinNumber();
    }
    
    /**
     * Recupera una cadena de cuatro números al azar.
     * 
     * @author EZENTIS
     * @return números
     */
    public static String getPinNumber() {
        return getRandomChars(Constantes.NUMBERS, 4);
    }
    
    /**
     * Recupera una cadena de cuatro letras al azar.
     * 
     * @author EZENTIS
     * @return letras
     */
    public static String getPinLetters() {
        return getRandomChars(Constantes.LETTERS, 4);
    }
    
    /**
     * Recupera una cadena de caracteres aleatorios a partir de un conjunto de caracteres y una longitud determinada.
     * 
     * @author EZENTIS
     * @param key cadena de caracteres posibles
     * @param length número de caracteres deseados
     * @return cadena de caracteres elegidos
     */
    public static String getRandomChars(String key, int length) {
        StringBuilder pswd = new StringBuilder();
        Random r = new Random();
        
        for (int i = 0; i < length; i++) {
            pswd.append(key.charAt(r.nextInt(key.length())));
        }
        
        return pswd.toString();
    }
    
    // ************* Generating new password Progesin END ********************//
    
    /**
     * Genera un mensaje de error a partir de una excepción.
     * 
     * @param e Excepción
     * @return Mensaje
     */
    public static String messageError(Exception e) {
        String message = Arrays.toString(e.getStackTrace());
        if (message.length() > 2000) {
            message = message.substring(message.length() - 2000);
        }
        return message;
    }
    
    /**
     * Formatea una fecha según un patrón recibido como parámetro.
     * 
     * @param date fecha a formatear
     * @param pattern patrón de fecha
     * @return fecha formateada
     */
    
    public static String getFechaFormateada(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
}