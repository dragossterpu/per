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

/**
 * Clase para exportar en Word.
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
     * 
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
            throw new IOException(e.getMessage());
        }
    }
    
    @Override
    public void export(FacesContext context, List<String> clientIds, String outputFileName, boolean pageOnly,
            boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor,
            ExporterOptions options) throws IOException {
        // try {
        // Document document = new Document();
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // RtfWriter2.getInstance(document, baos);
        //
        // if (preProcessor != null) {
        // preProcessor.invoke(context.getELContext(), new Object[] { document });
        // }
        //
        // if (!document.isOpen()) {
        // document.open();
        // }
        //
        // if (options != null) {
        // expOptions = options;
        // }
        //
        // VisitContext visitContext = VisitContext.createVisitContext(context, clientIds, null);
        // VisitCallback visitCallback = new PDFExportVisitCallback(this, document, pageOnly, selectionOnly,
        // encodingType);
        //
        // VisitCallback visitCallback = new context.getViewRoot().visitTree(visitContext, visitCallback);
        
        // if (postProcessor != null) {
        // postProcessor.invoke(context.getELContext(), new Object[] { document });
        // }
        //
        // document.close();
        //
        // writeDocToResponse(context.getExternalContext(), baos, outputFileName);
        //
        // } catch (DocumentException e) {
        // throw new IOException(e.getMessage());
        // }
    }
    
    @Override
    public void export(FacesContext context, String outputFileName, List<DataTable> tables, boolean pageOnly,
            boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor,
            ExporterOptions options) throws IOException {
        // try {
        // Document document = new Document();
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // RtfWriter2.getInstance(document, baos);
        //
        // if (preProcessor != null) {
        // preProcessor.invoke(context.getELContext(), new Object[] { document });
        // }
        //
        // if (!document.isOpen()) {
        // document.open();
        // }
        //
        // if (options != null) {
        // expOptions = options;
        // }
        //
        // for (DataTable table : tables) {
        // document.add(exportDocTable(context, table, pageOnly, selectionOnly, encodingType));
        //
        // Paragraph preface = new Paragraph();
        // addEmptyLine(preface, 3);
        // document.add(preface);
        // }
        //
        // if (postProcessor != null) {
        // postProcessor.invoke(context.getELContext(), new Object[] { document });
        // }
        //
        // document.close();
        //
        // writeDocToResponse(context.getExternalContext(), baos, outputFileName);
        //
        // } catch (DocumentException e) {
        // throw new IOException(e.getMessage());
        // }
    }
    
    /*
     * 
     */
    protected Table exportDocTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly,
            String encoding) throws BadElementException {
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
     * @param table PrimeFaces
     * @param docTable tabla a exportar
     * @param facetType tipo
     * @throws BadElementException lanzada
     */
    protected void addTableFacets(FacesContext context, DataTable table, Table docTable, String facetType)
            throws BadElementException {
        String facetText = null;
        UIComponent facet = table.getFacet(facetType);
        if (facet != null) {
            if (facet instanceof UIPanel) {
                for (UIComponent child : facet.getChildren()) {
                    if (child.isRendered()) {
                        String value = ComponentUtils.getValueToRender(context, child);
                        
                        if (value != null) {
                            facetText = value;
                            break;
                        }
                    }
                }
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
            docTable.addCell(cell);
        }
    }
    
    /**
     * 
     */
    @Override
    protected void exportCells(DataTable table, Object document) {
        try {
            Table docTab = (Table) document;
            for (UIColumn col : table.getColumns()) {
                if (col instanceof DynamicColumn) {
                    ((DynamicColumn) col).applyStatelessModel();
                }
                if (col.isRendered() && col.isExportable()) {
                    addColumnValue(docTab, col.getChildren(), this.cellFont, col);
                }
                
            }
        } catch (BadElementException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Añade título de las columnas.
     * 
     * @param table tabla primefaces
     * @param docTab tabla a exportar
     * @param columnType tipo columna
     * @throws BadElementException lanzada
     */
    protected void addColumnFacets(DataTable table, Table docTab, ColumnType columnType) throws BadElementException {
        for (UIColumn col : table.getColumns()) {
            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            
            if (col.isRendered() && col.isExportable()) {
                UIComponent facet = col.getFacet(columnType.facet());
                if (facet != null) {
                    addColumnValue(facet);
                } else {
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
            }
        }
    }
    
    /**
     * Añade columnas a un componente.
     * 
     * @param component a añadir
     */
    protected void addColumnValue(UIComponent component) {
        String value = "";
        if (component != null) {
            value = exportValue(FacesContext.getCurrentInstance(), component);
        }
        
        Cell cell = new Cell(value);
        cell.setBorderColor(new Color(255, 0, 0));
        docTable.addCell(cell);
    }
    
    /**
     * 
     * @param docTab tabla
     * @param components lista de componentes
     * @param font fuente
     * @param column columna
     * @throws BadElementException excepción lanzada
     */
    protected void addColumnValue(Table docTab, List<UIComponent> components, Font font, UIColumn column)
            throws BadElementException {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (column.getExportFunction() != null) {
            docTab.addCell(new Paragraph(exportColumnByFunction(context, column), font));
        } else {
            StringBuilder builder = new StringBuilder();
            for (UIComponent component : components) {
                if (component.isRendered()) {
                    String value = exportValue(context, component);
                    
                    if (value != null)
                        builder.append(value);
                }
            }
            
            docTab.addCell(new Paragraph(builder.toString(), font));
        }
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
    protected void writeDocToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName)
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
    protected int getColumnsCount(DataTable table) {
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
     * Añade línea vacía.
     * 
     * @param paragraph parrafo
     * @param number número
     */
    protected void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    /**
     * 
     * @param options opciones
     */
    protected void applyFacetOptions(ExporterOptions options) {
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
     * 
     * @param options opciones
     */
    protected void applyCellOptions(ExporterOptions options) {
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
