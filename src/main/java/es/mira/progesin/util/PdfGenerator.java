package es.mira.progesin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Clase para la generación de documentos PDF de la aplicación.
 * 
 * @author EZENTIS
 *
 */
@Component("pdfGenerator")
public class PdfGenerator {
    
    /**
     * Nombre del pdf generado para la solicitud de documentación previa.
     */
    public static final String NOMBREFICHEROSOLICITUD = "Solicitud_Documentacion.pdf";
    
    /**
     * Ruta de la imagen que aparece en el header de la solicitud en la página 1 del pdf generado.
     */
    public static final String HEADERSOLDOCPAG1 = "static/images/header_sol_doc_pag_1.png";
    
    /**
     * Ruta del logo de calidad.
     */
    public static final String LOGOCALIDAD = "static/images/footer_solicitud_1.png";
    
    /**
     * Nombre del pdf del cuestionario original (antes de añadirle el número de página).
     */
    private static final String NOMBREPDFCUESIONARIOORIGINAL = "Cuestionario_OR.pdf";
    
    /**
     * Nombre del pdf del cuestionario.
     */
    private static final String NOMBREPDFCUESTIONARIO = "Cuestionario.pdf";
    
    /**
     * Nombre del pdf de estadisticas.
     */
    private static final String NOMBREPDFESTADISTICAS = "Estadisticas.pdf";
    
    /**
     * Repositorio de respuestas de cuestionario.
     */
    @Autowired
    private IRespuestaCuestionarioRepository respuestaCuestionarioRepository;
    
    /**
     * Repositorio de configuración de los tipos de respuesta de un cuestionario.
     */
    @Autowired
    private IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;
    
    /**
     * Genera un PDF con la solicitud de documentación previa.
     * 
     * @param solDocPrevia Datos de la solicitud a imprimir
     * @param listadoDocumentosPrevios Listado de los documentos que contendrá la solicitud
     * @return StreamedContent
     * @throws ProgesinException excepción que puede lanzar
     */
    public StreamedContent imprimirSolicitudDocumentacionPrevia(SolicitudDocumentacionPrevia solDocPrevia,
            List<DocumentacionPrevia> listadoDocumentosPrevios) throws ProgesinException {
        StreamedContent pdfStream = null;
        try {
            File file = File.createTempFile(NOMBREFICHEROSOLICITUD, Constantes.EXTENSIONPDF);
            
            try (PdfWriter writer = new PdfWriter(file.getAbsolutePath()); PdfDocument pdf = new PdfDocument(writer)) {
                Document document = new Document(pdf, PageSize.A4);
                document.setMargins(100, 36, 70, 36);
                
                crearCabeceraFooter(pdf, document, true);
                
                Paragraph p1 = new Paragraph("Nº INSPECCIÓN: " + solDocPrevia.getInspeccion().getNumero());
                p1.setMarginTop(30);
                Paragraph p2 = new Paragraph(
                        "FECHA: Madrid, " + Utilities.getFechaFormateada(solDocPrevia.getFechaAlta(), "dd/MM/yyyy"));
                Paragraph p3 = new Paragraph("ASUNTO: " + solDocPrevia.getAsunto());
                Paragraph p4 = new Paragraph("DESTINATARIO: " + solDocPrevia.getDestinatario());
                p4.setBold();
                p4.setMarginBottom(20);
                
                document.add(p1);
                document.add(p2);
                document.add(p3);
                document.add(p4);
                
                // TODO: añadir el resto de textos
                
                document.add(crearTablaTipoDocumentacion(listadoDocumentosPrevios));
                
                Paragraph p = new Paragraph("Para cualquier aclaración, podrá dirigirse a:");
                p.setPaddingBottom(20);
                p.setPaddingTop(20);
                document.add(p);
                
                document.add(crearTablaDatosApoyo(solDocPrevia));
                
                p = new Paragraph(
                        "El Jefe del Equipo Auditor encargado de dirigir y coordinar la Inspección, próximamente, se pondrá en contacto con el Jefe de esa Unidad a los fines expuestos.");
                p.setPaddingTop(20);
                document.add(p);
                InputStream inputStream = new FileInputStream(file);
                pdfStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
                        NOMBREFICHEROSOLICITUD);
            }
        } catch (IOException e) {
            throw new ProgesinException(e);
        }
        
        return pdfStream;
    }
    
    /**
     * Crea la cabecera y el footer del documento.
     * 
     * @param pdf Dónde se insertará el header y el footer
     * @param document Dónde se insertará el header y el footer
     * @param insertarFooter Indica si hay que poner un footer al documento o sólo se inserta una cabecera
     * @throws IOException excepción que puede lanzar
     */
    private void crearCabeceraFooter(PdfDocument pdf, Document document, boolean insertarFooter) throws IOException {
        File file = new ClassPathResource(Constantes.LOGOMININISTERIOINTERIOR).getFile();
        Image logoMinisterioInterior = new Image(ImageDataFactory.create(file.getPath()));
        logoMinisterioInterior.scaleAbsolute((float) (logoMinisterioInterior.getImageWidth() * Constantes.ESCALA),
                (float) (logoMinisterioInterior.getImageHeight() * Constantes.ESCALA));
        
        file = new ClassPathResource(Constantes.LOGOIPSS).getFile();
        Image ipssLogo = new Image(ImageDataFactory.create(file.getPath()));
        ipssLogo.scaleAbsolute((float) (ipssLogo.getImageWidth() * Constantes.ESCALA),
                (float) (ipssLogo.getImageHeight() * Constantes.ESCALA));
        
        file = new ClassPathResource(HEADERSOLDOCPAG1).getFile();
        Image headerRepetido = new Image(ImageDataFactory.create(file.getPath()));
        headerRepetido.scaleAbsolute((float) (headerRepetido.getImageWidth() * Constantes.ESCALA),
                (float) (headerRepetido.getImageHeight() * Constantes.ESCALA));
        
        // Footer
        Image footer = null;
        if (insertarFooter) {
            file = new ClassPathResource(LOGOCALIDAD).getFile();
            footer = new Image(ImageDataFactory.create(file.getPath()));
            footer.scaleAbsolute((float) (footer.getImageWidth() * Constantes.ESCALA),
                    (float) (footer.getImageHeight() * Constantes.ESCALA));
        }
        
        HeaderFooterPdf handler = new HeaderFooterPdf(document, logoMinisterioInterior, ipssLogo, headerRepetido,
                footer);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
    }
    
    /**
     * Obtiene la tabla con los documentos solicitados.
     * 
     * @see imprimirSolicitudDocumentacionPrevia
     * @param tipoDocumentacion listado de documentos pedidos en la solicitud
     * @return Table Tabla con los documentos
     */
    private Table crearTablaTipoDocumentacion(List<DocumentacionPrevia> tipoDocumentacion) {
        float[] columnWidths = { 5, 3, 2 };
        Table tabla = new Table(columnWidths);
        tabla.setWidthPercent(100);
        
        tabla.addHeaderCell("DOCUMENTO");
        tabla.addHeaderCell("NOMBRE");
        tabla.addHeaderCell("TIPO DE ARCHIVO");
        
        tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
        tabla.getHeader().setPaddingTop(20);
        tabla.getHeader().setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        Cell celda;
        for (DocumentacionPrevia doc : tipoDocumentacion) {
            celda = new Cell();
            celda.add(doc.getDescripcion());
            tabla.addCell(celda);
            
            celda = new Cell();
            celda.add(doc.getNombre());
            tabla.addCell(celda);
            
            celda = new Cell();
            celda.add(String.join(", ", doc.getExtensiones()));
            tabla.addCell(celda);
        }
        
        return tabla;
    }
    
    /**
     * Obtiene la tabla con los datos de la persona de apoyo.
     * 
     * @see imprimirSolicitudDocumentacionPrevia
     * @param solDocPrevia solicitud de documentación previa
     * @return Table Tabla con los datos de la persona de apoyo
     */
    private Table crearTablaDatosApoyo(SolicitudDocumentacionPrevia solDocPrevia) {
        Table tabla = new Table(4);
        tabla.setWidthPercent(100);
        
        Cell celda = new Cell();
        celda.add(solDocPrevia.getApoyoPuesto());
        tabla.addCell(celda);
        
        celda = new Cell();
        celda.add(solDocPrevia.getApoyoNombre());
        tabla.addCell(celda);
        
        celda = new Cell();
        celda.add(solDocPrevia.getApoyoTelefono());
        tabla.addCell(celda);
        
        celda = new Cell();
        celda.add(solDocPrevia.getApoyoCorreo());
        tabla.addCell(celda);
        
        return tabla;
    }
    
    /**
     * Genera un documento PDF con las preguntas y respuestas de un cuestionario enviado.
     * 
     * @param cuestionarioEnviado cuestionario que se quiere imprimir en PDF
     * @return pdf con el contenido del cuestionario
     * @throws ProgesinException excepción que puede lanzar
     */
    public StreamedContent crearCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado) throws ProgesinException {
        StreamedContent pdfStream = null;
        try {
            File fileOr = File.createTempFile(NOMBREPDFCUESIONARIOORIGINAL, Constantes.EXTENSIONPDF);
            
            try (PdfWriter writer = new PdfWriter(fileOr.getAbsolutePath());
                    PdfDocument pdf = new PdfDocument(writer)) {
                
                // Initialize document
                Document document = new Document(pdf, PageSize.A4);
                document.setMargins(100, 36, 70, 36);
                
                crearCabeceraFooter(pdf, document, false);
                
                Paragraph p = new Paragraph(
                        cuestionarioEnviado.getCuestionarioPersonalizado().getNombreCuestionario().toUpperCase());
                p.setBold();
                p.setTextAlignment(TextAlignment.CENTER);
                p.setPadding(20);
                p.setFontSize(14);
                document.add(p);
                
                p = new Paragraph();
                Text text = new Text("INSPECCIÓN:  ").setBold();
                p.add(text);
                p.add(cuestionarioEnviado.getInspeccion().getTipoInspeccion().getCodigo() + " "
                        + cuestionarioEnviado.getInspeccion().getNumero());
                p.setPaddingBottom(10);
                p.setFontSize(14);
                document.add(p);
                
                List<RespuestaCuestionario> listaRespuestas = respuestaCuestionarioRepository
                        .findDistinctByRespuestaIdCuestionarioEnviado(cuestionarioEnviado);
                
                // Ordena la lista de respuestas por el orden del area para tenerlas ya ordenadas en el mapa
                Collections.sort(listaRespuestas,
                        (o1, o2) -> Long.compare(o1.getRespuestaId().getPregunta().getArea().getOrden(),
                                o2.getRespuestaId().getPregunta().getArea().getOrden()));
                
                // Construyo un mapa con las respuestas asociadas a cada área
                Map<AreasCuestionario, List<RespuestaCuestionario>> mapaAreaRespuesta = construirMapaAreaRespuestas(
                        listaRespuestas);
                
                Iterator<AreasCuestionario> areasSet = mapaAreaRespuesta.keySet().iterator();
                AreasCuestionario area;
                while (areasSet.hasNext()) {
                    area = areasSet.next();
                    List<RespuestaCuestionario> listaRespuestasArea = mapaAreaRespuesta.get(area);
                    Paragraph pa = new Paragraph(area.getArea());
                    pa.setBackgroundColor(Color.LIGHT_GRAY);
                    pa.setTextAlignment(TextAlignment.CENTER);
                    pa.setBold();
                    document.add(pa);
                    
                    // Ordeno las preguntas por su orden
                    Collections.sort(listaRespuestasArea,
                            (o1, o2) -> Long.compare(o1.getRespuestaId().getPregunta().getOrden(),
                                    o2.getRespuestaId().getPregunta().getOrden()));
                    
                    crearRespuestasPorArea(listaRespuestasArea, document);
                }
                
                // Close document
                document.close();
                
                File fileDest = File.createTempFile(NOMBREPDFCUESTIONARIO, Constantes.EXTENSIONPDF);
                insertarNumeroPagina(fileOr.getAbsolutePath(), fileDest.getAbsolutePath(), document);
                
                InputStream inputStream = new FileInputStream(fileDest);
                pdfStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
                        NOMBREPDFCUESTIONARIO);
            }
        } catch (IOException e) {
            throw new ProgesinException(e);
        }
        
        return pdfStream;
    }
    
    /**
     * Construye un mapa con las respuestas asociadas a cada área.
     * 
     * @param listaRespuestas lista usada para contruir el mapa
     * @return mapa de lista de repuestas por área
     */
    private Map<AreasCuestionario, List<RespuestaCuestionario>> construirMapaAreaRespuestas(
            List<RespuestaCuestionario> listaRespuestas) {
        Map<AreasCuestionario, List<RespuestaCuestionario>> mapaAreaRespuesta = new LinkedHashMap<>();
        AreasCuestionario area;
        List<RespuestaCuestionario> listaRtasArea;
        for (RespuestaCuestionario rta : listaRespuestas) {
            area = rta.getRespuestaId().getPregunta().getArea();
            if (mapaAreaRespuesta.get(area) == null) {
                listaRtasArea = new ArrayList<>();
            } else {
                listaRtasArea = mapaAreaRespuesta.get(area);
            }
            
            listaRtasArea.add(rta);
            
            mapaAreaRespuesta.put(area, listaRtasArea);
        }
        return mapaAreaRespuesta;
    }
    
    /**
     * Crear las respuestas de un área del cuestionario.
     * 
     * @param listaRespuestas lista de respuestas
     * @param document documento donde se insertan las respuestas
     * @throws ProgesinException excepción que puede lanzar
     * @throws IllegalAccessException excepción que puede lanzar
     */
    private void crearRespuestasPorArea(List<RespuestaCuestionario> listaRespuestas, Document document)
            throws ProgesinException {
        for (RespuestaCuestionario respuesta : listaRespuestas) {
            PreguntasCuestionario pregunta = respuesta.getRespuestaId().getPregunta();
            Paragraph p = new Paragraph(pregunta.getPregunta());
            p.setBold();
            p.setFontSize(10);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setPaddingTop(10);
            p.setPaddingBottom(10);
            document.add(p);
            
            if (pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTATABLA)
                    || pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                document.add(crearRespuestaTipoTablaMatriz(respuesta));
            } else {
                p = new Paragraph(respuesta.getRespuestaTexto());
                document.add(p);
                if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("ADJUNTO")
                        && respuesta.getDocumentos().isEmpty() == Boolean.FALSE) {
                    document.add(crearTablaDocumentos(respuesta));
                }
            }
        }
    }
    
    /**
     * Crear una tabla con la respuesta.
     * 
     * @param respuesta Respuesta de la que se creará la tabla
     * @return tabla
     * @throws ProgesinException excepción que puede lanzar
     */
    private Table crearRespuestaTipoTablaMatriz(RespuestaCuestionario respuesta) throws ProgesinException {
        List<DatosTablaGenerica> listaDatosTabla = respuesta.getRespuestaTablaMatriz();
        String tipoRespuesta = respuesta.getRespuestaId().getPregunta().getTipoRespuesta();
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
                .findColumnasBySeccion(tipoRespuesta);
        
        Table tabla = new Table(valoresColumnas.size());
        tabla.setWidthPercent(100);
        tabla.setPaddingBottom(30);
        tabla.setPaddingTop(30);
        tabla.setFontSize(8);
        
        try {
            if (tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                tabla = new Table(valoresColumnas.size() + 1);
                // Añado la primera columna de la cabecera vacía
                tabla.addHeaderCell("");
            }
            
            tabla.setWidthPercent(100);
            
            for (ConfiguracionRespuestasCuestionario columna : valoresColumnas) {
                Cell cell = new Cell();
                cell.add(columna.getConfig().getValor());
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setFontSize(8);
                tabla.addHeaderCell(cell);
            }
            tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
            tabla.getHeader().setPaddingTop(20);
            
            for (DatosTablaGenerica datosTabla : listaDatosTabla) {
                if (tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                    Cell cell = new Cell();
                    cell.add(datosTabla.getNombreFila());
                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    cell.setTextAlignment(TextAlignment.LEFT);
                    cell.setFontSize(8);
                    tabla.addCell(cell);
                }
                for (ConfiguracionRespuestasCuestionario columna : valoresColumnas) {
                    Field field = DatosTablaGenerica.class.getDeclaredField(columna.getConfig().getClave());
                    field.setAccessible(true);
                    tabla.addCell((String) field.get(datosTabla));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new ProgesinException(e);
        }
        return tabla;
    }
    
    /**
     * Crear una tabla con los documentos adjuntados en una respuesta.
     * 
     * @param respuesta Respuesta con los documentos adjuntados
     * @return tabla
     */
    private Table crearTablaDocumentos(RespuestaCuestionario respuesta) {
        Table tabla = new Table(1);
        tabla.setWidthPercent(100);
        tabla.setPaddingTop(10);
        tabla.setFontSize(8);
        
        tabla.addHeaderCell("DOCUMENTOS ADJUNTADOS");
        tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
        tabla.getHeader().setTextAlignment(TextAlignment.CENTER);
        
        List<Documento> listaDocumentos = respuesta.getDocumentos();
        for (Documento documento : listaDocumentos) {
            tabla.addCell(documento.getNombre());
        }
        
        return tabla;
    }
    
    /**
     * Inserta los números de página en el PDF.
     * 
     * @param src Ruta del pdf origen
     * @param dest Ruta del pdf destino
     * @param doc Doumento a leer
     * @throws ProgesinException excepción que puede lanzar
     */
    private void insertarNumeroPagina(String src, String dest, Document doc) throws ProgesinException {
        PdfReader reader = null;
        try {
            reader = new PdfReader(src);
            int nTotalPaginas = reader.getNumberOfPages();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            PdfContentByte pagecontent;
            for (int i = 1; i <= nTotalPaginas; i++) {
                pagecontent = stamper.getOverContent(i);
                ColumnText.showTextAligned(pagecontent, Element.ALIGN_CENTER,
                        new Phrase(String.format("Página %s de %s", i, nTotalPaginas)),
                        (pagecontent.getPdfDocument().getPageSize().getRight() - doc.getRightMargin()
                                - (pagecontent.getPdfDocument().getPageSize().getLeft() + doc.getLeftMargin())) / 2
                                + doc.getLeftMargin(),
                        pagecontent.getPdfDocument().getPageSize().getBottom() + 20, 0);
            }
            
            stamper.close();
        } catch (DocumentException | IOException e) {
            throw new ProgesinException(e);
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    
    /**
     * Genera el PDF con el informe estadístico de inspecciones.
     * 
     * @param mapaEstados Mapa de las inspecciones a exportar
     * @param filtro Filtros aplicados para recuperar las inspecciones
     * @param fileImg Fichero con la imagen que se incrustará en el PDF.
     * @return Stream con el fichero a descargar
     * @throws ProgesinException Excepción generada al crear/exportar el fichero
     */
    public StreamedContent generarInformeEstadisticas(Map<EstadoInspeccionEnum, List<Inspeccion>> mapaEstados,
            InspeccionBusqueda filtro, File fileImg) throws ProgesinException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        StreamedContent pdfStream = null;
        try {
            File file = File.createTempFile(NOMBREPDFESTADISTICAS, Constantes.EXTENSIONPDF);
            try (PdfWriter writer = new PdfWriter(file.getAbsolutePath()); PdfDocument pdf = new PdfDocument(writer)) {
                Document document = new Document(pdf, PageSize.A4);
                document.setMargins(100, 36, 70, 36);
                crearCabeceraFooter(pdf, document, false);
                // Título
                Paragraph titulo = new Paragraph("Informe estadístico");
                titulo.setBold();
                titulo.setFontSize(16);
                titulo.setTextAlignment(TextAlignment.CENTER);
                titulo.setPadding(5);
                document.add(titulo);
                // Fecha
                Paragraph fecha = new Paragraph("Fecha emisión documento : " + sdf.format(new Date()));
                fecha.setTextAlignment(TextAlignment.RIGHT);
                fecha.setPadding(5);
                document.add(fecha);
                // Datos del informe
                document.add(creaTitulo("Parámetros del informe"));
                // Filtros usados
                document.add(creaSubtitulo("Filtros aplicados"));
                creaInfoFiltros(document, filtro);
                List<EstadoInspeccionEnum> listaEstadosSeleccionados = new ArrayList<>(mapaEstados.keySet());
                document.add(creaSubtitulo("Estados seleccionados"));
                creaInfoSeleccion(document, listaEstadosSeleccionados);
                document.add(new AreaBreak()); // Salto de página
                // Gráfica
                document.add(creaSubtitulo("Gráfica"));
                document.add(new Image(ImageDataFactory.create(fileImg.getPath())));
                document.add(new AreaBreak()); // Salto de página
                // Datos
                document.add(creaSubtitulo("Desglose"));
                for (EstadoInspeccionEnum estado : listaEstadosSeleccionados) {
                    List<Inspeccion> listaInspecciones = mapaEstados.get(estado);
                    
                    // genera tabla de inspecciones
                    creaTablaDesglose(document, listaInspecciones, estado);
                    
                }
                
                document.close();
                File fileDest = File.createTempFile(NOMBREPDFESTADISTICAS, Constantes.EXTENSIONPDF);
                insertarNumeroPagina(file.getAbsolutePath(), fileDest.getAbsolutePath(), document);
                InputStream inputStream = new FileInputStream(fileDest);
                pdfStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
                        NOMBREPDFESTADISTICAS);
                
            }
        } catch (IOException e) {
            throw new ProgesinException(e);
        }
        
        return pdfStream;
    }
    
    /**
     * Crea un párrafo formateado para servir como título.
     * 
     * @param texto Texto del título a generar
     * @return Párrafo formateado como título.
     */
    private Paragraph creaTitulo(String texto) {
        Paragraph titulo = new Paragraph(texto);
        titulo.setBold();
        titulo.setFontSize(12);
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setPadding(10);
        return titulo;
    }
    
    /**
     * Crea un párrafo formateado para servir como título secundario.
     * 
     * @param texto Texto del subtítulo a generar
     * @return Párrafo formateado como título secundario.
     */
    private Paragraph creaSubtitulo(String texto) {
        Paragraph subTitulo = new Paragraph(texto);
        subTitulo.setFontSize(12);
        subTitulo.setBackgroundColor(Color.convertRgbToCmyk(new DeviceRgb(153, 201, 255)));
        subTitulo.setMarginTop(20);
        subTitulo.setMarginBottom(10);
        return subTitulo;
    }
    
    /**
     * Inserta en el informe de estadísticas la información relativa a los filtros empleados para generar la
     * información.
     * 
     * @param doc Documento al que se adjunta la información
     * @param filtro Objeto que contiene los filtros usados
     */
    private void creaInfoFiltros(Document doc, InspeccionBusqueda filtro) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        float[] columnas = { 2, 1 };
        Table tabla;
        Cell celdaFiltro;
        if (filtro.getTipoInspeccion() != null || filtro.getProvincia() != null || filtro.getFechaDesde() != null
                || filtro.getFechaHasta() != null) {
            tabla = new Table(columnas);
            tabla.setWidthPercent(50);
            
            tabla.addHeaderCell("Filtro");
            tabla.addHeaderCell("Valor");
            tabla.getHeader().setFontSize(10);
            tabla.getHeader().setBackgroundColor(new DeviceRgb(204, 228, 255));
            if (filtro.getTipoInspeccion() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Tipo de inspección");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(filtro.getTipoInspeccion().getDescripcion());
                tabla.addCell(celdaFiltro);
            }
            if (filtro.getProvincia() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Provincia");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(filtro.getProvincia().getNombre());
                tabla.addCell(celdaFiltro);
            }
            if (filtro.getFechaDesde() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Fecha desde");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(sdf.format(filtro.getFechaDesde()));
                tabla.addCell(celdaFiltro);
            }
            if (filtro.getFechaHasta() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Fecha hasta");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(sdf.format(filtro.getFechaHasta()));
                tabla.addCell(celdaFiltro);
            }
            doc.add(tabla);
        } else {
            Paragraph texto = new Paragraph("No se han aplicado filtros");
            texto.setFontSize(9);
            texto.setPadding(5);
            doc.add(texto);
        }
    }
    
    /**
     * Inserta en el informe de estadísticas los estados seleccionados para la generación del informe.
     * 
     * @param doc Documento al que se adjunta la información
     * @param listado Listado de los estados seleccionados
     */
    private void creaInfoSeleccion(Document doc, List<EstadoInspeccionEnum> listado) {
        for (EstadoInspeccionEnum estado : listado) {
            Paragraph texto = new Paragraph(estado.getDescripcion());
            texto.setFontSize(9);
            doc.add(texto);
        }
    }
    
    /**
     * Inserta en el informe una tabla con el desglose de inspecciones en un estado dado.
     * 
     * @param doc Documento en el que se insertará la tabla
     * @param listaInspecciones Lista de las inspecciones
     * @param estado Estado de las inspecciones
     */
    private void creaTablaDesglose(Document doc, List<Inspeccion> listaInspecciones, EstadoInspeccionEnum estado) {
        float[] columnWidths = { 1, 2, 1, 1, 1, 1 }; // 6 columnas
        Table tabla = new Table(columnWidths);
        if (!listaInspecciones.isEmpty()) {
            Paragraph descripcionEstado = new Paragraph(estado.getDescripcion());
            descripcionEstado.setBackgroundColor(Color.convertRgbToCmyk(new DeviceRgb(153, 201, 255)));
            descripcionEstado.setMarginTop(20);
            descripcionEstado.setFontSize(10);
            doc.add(descripcionEstado);
            tabla.setWidthPercent(100);
            tabla.addHeaderCell("EXPEDIENTE");
            tabla.addHeaderCell("TIPO DE INSPECCION");
            tabla.addHeaderCell("EQUIPO");
            tabla.addHeaderCell("CUERPO");
            tabla.addHeaderCell("UNIDAD");
            tabla.addHeaderCell("PROVINCIA");
            tabla.getHeader().setBackgroundColor(new DeviceRgb(204, 228, 255));
            tabla.getHeader().setPaddingTop(20);
            tabla.getHeader().setHorizontalAlignment(HorizontalAlignment.CENTER);
            tabla.getHeader().setFontSize(9);
        }
        Cell celda;
        
        for (Inspeccion ins : listaInspecciones) {
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getNumero());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getTipoInspeccion().getDescripcion());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getEquipo().getNombreEquipo());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getAmbito().getDescripcion());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getNombreUnidad());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getMunicipio().getProvincia().getNombre());
            tabla.addCell(celda);
        }
        doc.add(tabla);
    }
}
