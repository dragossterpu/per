package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Informe;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;

@Component("htmlPdfGenerator")
public class HtmlPdfGenerator {
    
    public StreamedContent generarInformePdf(Informe informe) throws IOException, DocumentException {
        File file = File.createTempFile("informe", ".pdf");
        OutputStream fileOS = new FileOutputStream(file);
        
        // Initialize PDF document
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(document.leftMargin(), document.rightMargin(), 100, 70);
        
        // Initialize PDF writer
        PdfWriter writer = PdfWriter.getInstance(document, fileOS);
        
        document.open();
        
        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssTextEditor = XMLWorkerHelper.getCSS(getCSSInputStream(Constantes.CSS_TEXT_EDITOR_PDF));
        cssResolver.addCss(cssTextEditor);
        
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());
        
        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
        
        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(obtenerDatosInformeConEstilo(informe).getBytes()));
        
        // Close document
        document.close();
        writer.close();
        
        InputStream inputStream = new FileInputStream(file);
        return new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(), "informe.pdf");
    }
    
    private InputStream getCSSInputStream(String resource) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(resource);
        return classPathResource.getInputStream();
    }
    
    private String obtenerDatosInformeConEstilo(Informe informe) throws UnsupportedEncodingException {
        String textoInforme;
        StringBuilder informeFormateado = new StringBuilder();
        if (informe != null && informe.getTexto() != null) {
            informeFormateado.append("<div class=\"ql-editor\">");
            textoInforme = new String(informe.getTexto(), "UTF-8");
            informeFormateado.append(textoInforme);
            informeFormateado.append("</div>");
        }
        System.out.println(limpiarHtml(informeFormateado.toString()));
        return limpiarHtml(informeFormateado.toString());
    }
    
    /**
     * Limpia el html pasado como parámetro para cerrar todas las etiquetas que haya sin cerrar
     * 
     * @param html html a limpiar
     * @return html limpio, con todas las etiquetas cerradas
     */
    public String limpiarHtml(String html) {
        final org.jsoup.nodes.Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Syntax.xml);
        
        return document.html();
    }
}
