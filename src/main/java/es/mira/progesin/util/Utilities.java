package es.mira.progesin.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings.Syntax;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.ClaseUsuario;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;

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
    
    /**
     * Limpia el html pasado como parámetro para cerrar todas las etiquetas que haya sin cerrar.
     * 
     * @param html html a limpiar
     * @return html limpio, con todas las etiquetas cerradas
     */
    public static String limpiarHtml(String html) {
        final Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Syntax.xml);
        return document.html();
    }
    
    /**
     * Comprueba que el html contiene texto, es decir, que no sólo lleva etiquetas de párrafo, salto de línea, etc.
     * 
     * @param html Html a comprobar
     * @return True si el html contiene algo más que etiquetas
     */
    public static Boolean noEstaVacio(String html) {
        final Document contenido = Jsoup.parse(html);
        return (contenido.text().isEmpty() && contenido.getElementsByTag("img").isEmpty()) == Boolean.FALSE;
    }
    
    /**
     * Obtiene una cadena con los campos que han cambiado durante la modificación de un usuario así como su valor
     * anterior y posterior a la misma.
     * 
     * @param original Objeto original
     * @param modificado Objeto modificado
     * @return Cadena con los campos modificados
     * @throws ProgesinException Excepción
     */
    
    public static String camposModificados(Object original, Object modificado) throws ProgesinException {
        Field[] listaCampos = original.getClass().getDeclaredFields();
        
        StringBuilder camposModificados = new StringBuilder();
        
        try {
            for (int i = 0; i < listaCampos.length; i++) {
                Field campo = listaCampos[i];
                campo.setAccessible(true);
                
                // Valores
                
                String valorOriginal;
                String valorModificado;
                
                if (campo.getType().getSimpleName().contains("List")) {
                    valorOriginal = devuelveValoresList((ArrayList<?>) campo.get(original));
                    valorModificado = devuelveValoresList((ArrayList<?>) campo.get(modificado));
                } else {
                    valorOriginal = devuelveValor(campo.get(original));
                    valorModificado = devuelveValor(campo.get(modificado));
                }
                
                if (!valorOriginal.equals(valorModificado)) {
                    camposModificados.append(campo.getName()).append(" (Antes '").append(valorOriginal)
                            .append("' ahora modificado a '").append(valorModificado).append("')\n\r");
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new ProgesinException(e);
        }
        
        return camposModificados.toString();
    }
    
    /**
     * Devuelve el valor contenido dentro de un objeto.
     * 
     * @param campo Objeto del que se desea obtener una cadena
     * @return Cadena de texto con el valor
     * @throws ProgesinException Excepción
     */
    private static String devuelveValor(final Object campo) throws ProgesinException {
        
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String valor = "No definido";
        
        if (campo != null) {
            final Class<? extends Object> clase = campo.getClass();
            
            switch (clase.getSimpleName()) {
                case "Empleo":
                case "PuestoTrabajo":
                case "RoleEnum":
                case "CuerpoEstado":
                case "TipoInspeccion":
                case "CuatrimestreEnum":
                case "EstadoInspeccionEnum":
                case "AmbitoInspeccionEnum":
                case "TipoUnidad":
                case "Departamento":
                    try {
                        valor = (String) clase.getDeclaredMethod("getDescripcion", (Class<?>[]) null).invoke(campo,
                                (Object[]) null);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                            | NoSuchMethodException | SecurityException e) {
                        throw new ProgesinException(e);
                    }
                    break;
                case "ClaseUsuario":
                    valor = ((ClaseUsuario) campo).getClase();
                    break;
                case "Equipo":
                    valor = ((Equipo) campo).getNombreEquipo();
                    break;
                case "Municipio":
                    valor = ((Municipio) campo).getName();
                    break;
                case "Inspeccion":
                    valor = ((Inspeccion) campo).getNumero();
                    break;
                case "Timestamp":
                case "Date":
                    valor = sdf.format(campo);
                    break;
                default:
                    valor = campo.toString();
                    break;
            }
        }
        
        return valor;
    }
    
    /**
     * Devuelve el valor de una lista.
     * 
     * @param lista Lista de la que se desean extraer los valores
     * @return Cadena conteniendo los valores extraídos
     * @throws ProgesinException Excepción
     */
    private static String devuelveValoresList(ArrayList<?> lista) throws ProgesinException {
        String cadena;
        if (lista.isEmpty()) {
            cadena = "No definido";
        } else {
            String[] cadenArray = new String[lista.size()];
            
            for (int i = 0; i < lista.size(); i++) {
                cadenArray[i] = devuelveValor(lista.get(i));
            }
            
            Arrays.sort(cadenArray);
            cadena = String.join(", ", cadenArray);
        }
        return cadena;
    }
    
    /**
     * Carga la plantilla y genera el texto final con los parámetros proporcionados.
     * 
     * @param template plantilla (txt,html,etc.)
     * @param parametros info a añadir a la plantilla
     * @return texto compilado
     * @throws PebbleException error al compilar
     * @throws IOException error al cargar la plantilla
     */
    public static String generarTextoConPlantilla(String template, Map<String, Object> parametros)
            throws PebbleException, IOException {
        PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(false).build();
        PebbleTemplate compiledTemplate = engine.getTemplate(template);
        
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, parametros);
        
        String textoCompilado = writer.toString();
        
        // TODO Borrar salida para pruebas de desarrollo
        System.out.println(textoCompilado);
        return textoCompilado;
    }
}