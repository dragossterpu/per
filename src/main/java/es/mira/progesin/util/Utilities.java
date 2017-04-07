package es.mira.progesin.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

import es.mira.progesin.constantes.Constantes;

@Component("utilities")
public class Utilities {
    
    private Utilities() {
        throw new IllegalAccessError("Utility class");
    }
    
    // ************* Generating new password Progesin ********************//
    /**
     * @author EZENTIS STAD
     * @param getPassword
     * @return
     * @comment getPassword
     */
    public static String getPassword() {
        return getPinLetters() + getPinNumber();
    }
    
    public static String getPinNumber() {
        return getRandomNumber(Constantes.NUMBERS, 4);
    }
    
    public static String getPinLetters() {
        return getRandomLetters(Constantes.LETTERS, 4);
    }
    
    public static String getRandomNumber(String key, int length) {
        String pswd = "";
        
        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }
        
        return pswd;
    }
    
    public static String getRandomLetters(String key, int length) {
        String pswd = "";
        
        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }
        
        return pswd;
    }
    
    // ************* Generating new password Progesin END ********************//
    
    // ************* Descripcion message error Progesin ********************
    /**
     * @param e
     * @return
     */
    public static String messageError(Exception e) {
        String message = Arrays.toString(e.getStackTrace());
        if (message.length() > 2000) {
            message = message.substring(message.length() - 2000);
        }
        return message;
    }
    
    // ************* Descripcion message error Progesin END********************
    
    public static String getFechaFormateada(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    // ************* Descripcion message error Progesin ********************
    /**
     * Añade ceros a la izquierda de un string
     * 
     * @param s
     * @param length
     * @return result
     */
    public static String leadingZeros(String s, int length) {
        String result;
        
        if (s.length() >= length)
            result = s;
        else
            result = String.format("%0" + (length - s.length()) + "d%s", 0, s);
        
        return result;
    }
}