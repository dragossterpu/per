package es.mira.progesin.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.io.StreamUtil;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.util.Base64ImageProvider;

/**
 * @author Ezentis
 */
public class ProgesinXMLParser extends XMLParser {
    
    /**
     * @param documento documento generado
     * @param writer generador de pdfs
     */
    public ProgesinXMLParser(Document documento, PdfWriter writer) {
        
        super();
        
        // CSS
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        CssFile cssTextEditor = XMLWorkerHelper.getCSS(StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF));
        cssResolver.addCss(cssTextEditor);
        
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());
        
        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(documento, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
        
        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        
        addListener(worker);
    }
}
