package es.mira.progesin.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Clase con utilidades para usar con primefaces.
 * 
 * @author EZENTIS
 *
 */
@Component("facesUtilities")
public class FacesUtilities {
    
    /**
     * Redirige a la página pasada como parámetro.
     * 
     * @author Ezentis
     * @param pagina deseada
     */
    public void redirect(String pagina) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, pagina));
        
        try {
            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
    }
    
    /**
     * Redirige a la página pasada como parámetro añadiendo parámetros GET a evaluar en destino.
     * 
     * @author Ezentis
     * @param pagina deseada
     * @param paramGET cadena con parametros que se quieran pasar a la página destino separados por '&'
     */
    public void redirect(String pagina, String paramGET) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, pagina));
        
        try {
            extContext.redirect(url + "?" + paramGET);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
    }
    
    /**
     * Muestra una cuadro de diálogo con información.
     * 
     * @author Ezentis
     * @param severity gravedad del aviso
     * @param summary resumen
     * @param detail detalles del mensaje
     */
    public static void setMensajeConfirmacionDialog(Severity severity, String summary, String detail) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
        context.execute("PF('dialogMessage').show()");
    }
    
    /**
     * Muestra un mensaje por pantalla.
     * 
     * @author Ezentis
     * @param severity gravedad del aviso
     * @param summary resumen
     * @param detail detalles del mensaje
     * @param idMensaje identificador del componente "message/s" de PrimeFaces donde se desea mostrar
     */
    public static void setMensajeInformativo(Severity severity, String summary, String detail, String idMensaje) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(idMensaje, message);
    }
    
}
