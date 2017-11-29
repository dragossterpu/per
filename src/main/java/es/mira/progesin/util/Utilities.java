
package es.mira.progesin.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.context.FacesContext;

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
                    camposModificados.append("<br>").append(devuelveNombreCampo(campo.getName())).append(" (Antes '")
                            .append(valorOriginal).append("' ahora modificado a '").append(valorModificado)
                            .append("')");
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
     * Devuelve el nombre de un campo en formato más amigable.
     * 
     * @param campo Campo del que se desea el nombre
     * @return Nombre del campo
     */
    private static String devuelveNombreCampo(String campo) {
        StringBuffer respuesta = new StringBuffer("<strong>");
        
        Map<String, String> mapaNombres = new HashMap<>();
        mapaNombres.put("username", "Nombre usuario");
        mapaNombres.put("estado", "Estado");
        mapaNombres.put("nombre", "Nombre");
        mapaNombres.put("apellido1", "Primer apellido");
        mapaNombres.put("apellido2", "Segundo apellido");
        mapaNombres.put("docIdentidad", "NIF");
        mapaNombres.put("numIdentificacion", "Número de indentificación personal");
        mapaNombres.put("telefono", "Teléfono");
        mapaNombres.put("telefonoMovilOficial", "Teléfono móvil oficial");
        mapaNombres.put("telefonoMovilParticular", "Teléfono móvil particular");
        mapaNombres.put("correo", "Correo electrónico");
        mapaNombres.put("role", "Rol");
        mapaNombres.put("despacho", "Despacho");
        mapaNombres.put("cuerpoEstado", "Cuerpo");
        mapaNombres.put("empleo", "Empleo");
        mapaNombres.put("puestoTrabajo", "Puesto de trabajo");
        mapaNombres.put("nivel", "Nivel");
        mapaNombres.put("categoria", "Categoría");
        mapaNombres.put("departamento", "Departamento");
        mapaNombres.put("claseUsuario", "Clase");
        mapaNombres.put("fechaDestinoIPSS", "Fecha destino IPSS");
        mapaNombres.put("fechaIngreso", "Fecha de ingreso en el cuerpo");
        mapaNombres.put("fechaInactivo", "Fecha de desactivación");
        
        if (mapaNombres.get(campo) != null) {
            respuesta.append(mapaNombres.get(campo));
        } else {
            respuesta.append(campo);
        }
        respuesta.append("</strong>");
        
        return respuesta.toString();
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
        
        return textoCompilado;
    }
    
    /**
     * Devuelve el número de días que han pasado desde una fecha introducida por parámetro hasta hoy.
     * 
     * @param fecha usuario a consultar
     * @return dias número de días
     */
    public static Long getDiasHastaHoy(Date fecha) {
        LocalDate hoy = LocalDate.now();
        long dias = 0;
        LocalDate fechaDesde = null;
        if (fecha != null) {
            fechaDesde = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dias = ChronoUnit.DAYS.between(fechaDesde, hoy);
        }
        return dias;
    }
    
    /**
     * Método para evitar los nulos en cadenas de texto.
     * @param cadena de entrada
     * @return cadena de salida
     */
    public static String nullToBlank(String cadena) {
        String cad = cadena;
        if (cad == null) {
            cad = "";
        }
        return cad;
    }
    
    /**
     * Elimina los bean de la sesión que no contienen ese nombre.
     * 
     * @param nombreBeanActual Nombre del bean que no se tiene que eliminar de la sesión.
     */
    public static void limpiarSesion(String nombreBeanActual) {
        Map<String, Object> mapaSesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        for (String cabecera : mapaSesion.keySet()) {
            String ubicacion = mapaSesion.get(cabecera).getClass().getPackage().toString().toLowerCase();
            if (ubicacion.contains("bean") && "navegacionBean".equals(cabecera) == Boolean.FALSE
                    && cabecera.equals(nombreBeanActual) == Boolean.FALSE) {
                mapaSesion.remove(cabecera);
            }
        }
    }
    
    /**
     * Elimina los bean de la sesión que no contienen ese nombre.
     * 
     * @param listaBeans Nombre de los beans que no se tienen que eliminar de la sesión.
     */
    public static void limpiarSesion(List<String> listaBeans) {
        Map<String, Object> mapaSesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        for (String cabecera : mapaSesion.keySet()) {
            String ubicacion = mapaSesion.get(cabecera).getClass().getPackage().toString().toLowerCase();
            if (ubicacion.contains("bean") && "navegacionBean".equals(cabecera) == Boolean.FALSE
                    && listaBeans.contains(cabecera) == Boolean.FALSE) {
                mapaSesion.remove(cabecera);
            }
        }
    }
}