package es.mira.progesin.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.component.export.ExporterOptions;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IRegistroActividadService;

/**
 * Clase para la exportación en Word.
 * @author Ezentis
 *
 */
public class DocExporter extends Exporter {
    /**
     * Variable cellFont.
     */
    private Font cellFont;
    
    /**
     * Variable facetFont.
     */
    private Font facetFont;
    
    /**
     * Variable facetBgColor.
     */
    private Color facetBgColor;
    
    /**
     * Variable expOptions.
     */
    private ExporterOptions expOptions;
    
    /**
     * Variable docTable.
     */
    private Table docTable;
    
    /**
     * Registro de actividad.
     */
    @Autowired
    private IRegistroActividadService regActividadService;
    
    /**
     * Exporta en Word una DataTable de PrimeFaces.
     */
    @Override
    public void export(FacesContext context, DataTable table, String filename, boolean pageOnly, boolean selectionOnly,
            String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, ExporterOptions options)
            throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            RtfWriter2.getInstance(document, baos);
            
            if (preProcessor != null) {
                preProcessor.invoke(context.getELContext(), new Object[] { document });
            }
            
            if (!document.isOpen()) {
                document.open();
            }
            if (options != null) {
                expOptions = options;
            }
            
            document.add(exportDocTable(context, table, pageOnly, selectionOnly, encodingType));
            
            if (postProcessor != null) {
                postProcessor.invoke(context.getELContext(), new Object[] { document });
            }
            
            document.close();
            
            writeDocToResponse(context.getExternalContext(), baos, filename);
            
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
    
    /**
     * Método implementado que no se utiliza.
     */
    @Override
    public void export(FacesContext context, List<String> clientIds, String outputFileName, boolean pageOnly,
            boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor,
            ExporterOptions options) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Método implementado que no se utiliza.
     */
    @Override
    public void export(FacesContext context, String outputFileName, List<DataTable> tables, boolean pageOnly,
            boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor,
            ExporterOptions options) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Retorna el objeto tabla exportada.
     * 
     * @param context FacesContext
     * @param table DataTable PrimeFaces
     * @param pageOnly exportar toda la página o la lista entera de la consulta
     * @param selectionOnly sólo se exportarán registros seleccionados
     * @param encoding codificación
     * @return tabla objeto tabla exportada.
     * @throws IOException lanzada
     * @throws BadElementException lanzada
     */
    private Table exportDocTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly,
            String encoding) throws IOException, BadElementException {
        int columnsCount = getColumnsCount(table);
        Table docTab = new Table(columnsCount);
        this.cellFont = FontFactory.getFont(FontFactory.TIMES, encoding);
        this.facetFont = FontFactory.getFont(FontFactory.TIMES, encoding, Font.DEFAULTSIZE, Font.BOLD);
        
        if (this.expOptions != null) {
            applyFacetOptions(this.expOptions);
            applyCellOptions(this.expOptions);
        }
        
        addTableFacets(context, table, docTab, "header");
        
        addColumnFacets(table, docTab, ColumnType.HEADER);
        
        if (pageOnly) {
            exportPageOnly(context, table, docTab);
        } else if (selectionOnly) {
            exportSelectionOnly(context, table, docTab);
        } else {
            exportAll(context, table, docTab);
        }
        
        if (table.hasFooterColumn()) {
            addColumnFacets(table, docTab, ColumnType.FOOTER);
        }
        
        addTableFacets(context, table, docTab, "footer");
        
        table.setRowIndex(-1);
        
        return docTab;
    }
    
    /**
     * 
     * @param context FacesContext
     * @param table DataTable PrimeFaces
     * @param docTab tabla a exportar
     * @param facetType tipo Face
     * @throws BadElementException lanzada
     */
    private void addTableFacets(FacesContext context, DataTable table, Table docTab, String facetType)
            throws BadElementException {
        String facetText = null;
        UIComponent facet = table.getFacet(facetType);
        if (facet != null) {
            if (facet instanceof UIPanel) {
                facetText = getFacetsTextWhenUIPanel(facet, context);
            } else {
                facetText = ComponentUtils.getValueToRender(context, facet);
            }
        }
        
        if (facetText != null) {
            int colspan = 0;
            
            for (UIColumn col : table.getColumns()) {
                if (col.isRendered() && col.isExportable()) {
                    colspan++;
                }
            }
            
            Cell cell = new Cell(new Paragraph(facetText, this.facetFont));
            if (this.facetBgColor != null) {
                cell.setBackgroundColor(this.facetBgColor);
            }
            
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(colspan);
            docTab.addCell(cell);
        }
    }
    
    /**
     * Obtiene los hijos de un componente y el value del primero de ellos que esté renderizado.
     * 
     * @param facet Componente
     * @param context FacesContext
     * @return value
     */
    private String getFacetsTextWhenUIPanel(UIComponent facet, FacesContext context) {
        String value = null;
        for (UIComponent child : facet.getChildren()) {
            if (child.isRendered()) {
                value = ComponentUtils.getValueToRender(context, child);
                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }
    
    /**
     * 
     */
    @Override
    protected void exportCells(DataTable table, Object document) {
        Table docTab = (Table) document;
        
        for (UIColumn col : table.getColumns()) {
            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            if (col.isRendered() && col.isExportable()) {
                
                addColumnValue(docTab, col.getChildren(), this.cellFont, col);
                
            }
        }
    }
    
    /**
     * Añade título de las columnas.
     * 
     * @param table tabla primefaces
     * @param docTab tabla a exportar
     * @param columnType tipo columna
     * @throws IOException
     * @throws BadElementException lanzada
     */
    private void addColumnFacets(DataTable table, Table docTab, ColumnType columnType) throws BadElementException {
        for (UIColumn col : table.getColumns()) {
            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            
            if (col.isRendered() && col.isExportable()) {
                UIComponent facet = col.getFacet(columnType.facet());
                if (facet != null) {
                    addColumnValue(facet);
                } else {
                    addColum(columnType, col, docTab);
                }
            }
        }
    }
    
    /**
     * 
     * @param columnType tipo columna
     * @param col columna
     * @param docTab tabla exportada
     * @throws BadElementException lanzada
     */
    private void addColum(ColumnType columnType, UIColumn col, Table docTab) throws BadElementException {
        String textValue;
        switch (columnType) {
            case HEADER:
                textValue = col.getHeaderText();
                break;
            
            case FOOTER:
                textValue = col.getFooterText();
                break;
            
            default:
                textValue = "";
                break;
        }
        
        if (textValue != null) {
            Cell cell = new Cell(new Paragraph(textValue, this.facetFont));
            if (this.facetBgColor != null) {
                cell.setBackgroundColor(this.facetBgColor);
            }
            docTab.addCell(cell);
        }
    }
    
    /**
     * Añade columnas a un componente.
     * 
     * @param component a añadir
     */
    private void addColumnValue(UIComponent component) {
        String value = "";
        if (component != null) {
            value = exportValue(FacesContext.getCurrentInstance(), component);
        }
        
        Cell cell = new Cell(value);
        cell.setBorderColor(new Color(255, 0, 0));
        docTable.addCell(cell);
    }
    
    /**
     * añade un valor a la culumna.
     * 
     * @param docTab tabla
     * @param components lista de componentes
     * @param font fuente
     * @param column columna
     * @throws BadElementException excepción lanzada
     */
    private void addColumnValue(Table docTab, List<UIComponent> components, Font font, UIColumn column) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (column.getExportFunction() != null) {
                
                docTab.addCell(new Paragraph(exportColumnByFunction(context, column), font));
                
            } else {
                
                docTab.addCell(new Paragraph(getValues(components, context), font));
            }
        } catch (BadElementException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * 
     * @param components UIComponent
     * @param context contexto
     * @return values
     */
    private String getValues(List<UIComponent> components, FacesContext context) {
        StringBuilder builder = new StringBuilder();
        for (UIComponent component : components) {
            if (component.isRendered()) {
                String value = exportValue(context, component);
                
                if (value != null) {
                    builder.append(value);
                }
            }
        }
        return builder.toString();
    }
    
    /**
     * Escribe el documento word.
     * 
     * @param externalContext contexto
     * @param baos flujo
     * @param fileName nombre fichero
     * @throws IOException lanzada
     * @throws DocumentException lanzada
     */
    private void writeDocToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName)
            throws IOException {
        externalContext.setResponseContentType("application/msword");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=\"" + fileName + ".doc\"");
        externalContext.setResponseContentLength(baos.size());
        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object> emptyMap());
        OutputStream out = externalContext.getResponseOutputStream();
        baos.writeTo(out);
        externalContext.responseFlushBuffer();
    }
    
    /**
     * Devuelve el número de columnas de una tabla.
     * 
     * @param table tabla
     * @return cols
     */
    private int getColumnsCount(DataTable table) {
        int count = 0;
        
        for (UIColumn col : table.getColumns()) {
            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            
            if (!col.isRendered() || !col.isExportable()) {
                continue;
            }
            count++;
        }
        
        return count;
    }
    
    /**
     * Aplica estilos a las cabeceras y pies de página.
     * 
     * @param options opciones
     */
    private void applyFacetOptions(ExporterOptions options) {
        String facetBackground = options.getFacetBgColor();
        if (facetBackground != null) {
            facetBgColor = Color.decode(facetBackground);
        }
        
        String facetFontColor = options.getFacetFontColor();
        if (facetFontColor != null) {
            facetFont.setColor(Color.decode(facetFontColor));
        }
        
        String facetFontSize = options.getFacetFontSize();
        if (facetFontSize != null) {
            facetFont.setSize(Integer.valueOf(facetFontSize));
        }
        
        String facetFontStyle = options.getFacetFontStyle();
        if (facetFontStyle != null) {
            if ("NORMAL".equalsIgnoreCase(facetFontStyle)) {
                facetFontStyle = Integer.toString(Font.NORMAL);
            }
            if ("BOLD".equalsIgnoreCase(facetFontStyle)) {
                facetFontStyle = Integer.toString(Font.BOLD);
            }
            if ("ITALIC".equalsIgnoreCase(facetFontStyle)) {
                facetFontStyle = Integer.toString(Font.ITALIC);
            }
            
            facetFont.setStyle(facetFontStyle);
        }
    }
    
    /**
     * Aplica estilos a las celdas.
     * 
     * @param options opciones
     */
    private void applyCellOptions(ExporterOptions options) {
        String cellFontColor = options.getCellFontColor();
        if (cellFontColor != null) {
            cellFont.setColor(Color.decode(cellFontColor));
        }
        
        String cellFontSize = options.getCellFontSize();
        if (cellFontSize != null) {
            cellFont.setSize(Integer.valueOf(cellFontSize));
        }
        
        String cellFontStyle = options.getCellFontStyle();
        if (cellFontStyle != null) {
            if ("NORMAL".equalsIgnoreCase(cellFontStyle)) {
                cellFontStyle = Integer.toString(Font.NORMAL);
            }
            if ("BOLD".equalsIgnoreCase(cellFontStyle)) {
                cellFontStyle = Integer.toString(Font.BOLD);
            }
            if ("ITALIC".equalsIgnoreCase(cellFontStyle)) {
                cellFontStyle = Integer.toString(Font.ITALIC);
            }
            
            cellFont.setStyle(cellFontStyle);
        }
    }
}
