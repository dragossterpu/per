package es.mira.progesin.util;

import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IRegistroActividadService;

/**
 * Clase con método para exportar a Word.
 * 
 * @author EZENTIS
 *
 */

@Component("exportadorWord")
public class ExportadorWord {
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Recupera el objeto de búsqueda al volver a la vista de búsqueda de inspecciones.
     * @param nombreDoc nombre del documento word
     * @param pageOnly imprepe una pagina o toda la búsqueda
     * @param idTabla id de la tabla PrimeFaces
     * @param secc sección donde se produce la excepción
     */
    public void exportDoc(String nombreDoc, boolean pageOnly, String idTabla, SeccionesEnum secc) {
        
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            MethodExpression preProcessor = context.getApplication().getExpressionFactory().createMethodExpression(
                    context.getELContext(), "#{exportadorWord.rotarPdf}", null, new Class[] { ExportadorWord.class });
            
            DocExporter exporter = new DocExporter();
            DataTable dataTable = (DataTable) context.getViewRoot().findComponent(idTabla);
            exporter.export(context, dataTable, nombreDoc, pageOnly, false, "UTF-8", preProcessor, null, null);
            context.responseComplete();
        } catch (IOException e) {
            registroActividadService.altaRegActividadError(secc.getDescripcion(), e);
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Error",
                    "Ha habido un error al intentar exportar a formato .doc.", null);
        }
    }
    
    /**
     * Rota un documento al realizar la exportación.
     * 
     * @param document documento obtenido de la vista.
     */
    public static void rotarPdf(Object document) {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setMargins(20, 20, 10, 10);
    }
    
}
