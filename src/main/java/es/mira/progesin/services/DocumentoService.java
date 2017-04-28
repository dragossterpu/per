package es.mira.progesin.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.xml.sax.ContentHandler;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.DocumentoBlob;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IDocumentoRepository;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.persistence.repositories.gd.IGestDocSolicitudDocumentacionRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;
import es.mira.progesin.web.beans.DocumentoBusqueda;

/**
 * 
 * Implementación del servicio de documentos
 * 
 * @author Ezentis
 *
 */

@Service("documentoService")
public class DocumentoService implements IDocumentoService {
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    @Autowired
    private IDocumentoRepository documentoRepository;
    
    @Autowired
    private IInspeccionesRepository iInspeccionRepository;
    
    @Autowired
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
    @Autowired
    IGestDocSolicitudDocumentacionRepository gestDocSolicitudDocumentacionRepository;
    
    /***************************************
     * 
     * delete
     * 
     * Elimina un documento de la base de datos. El documento se identifica por su id
     * 
     * @author Ezentis
     * @param Long id Identificador del documento a eliminar
     *
     *************************************/
    
    @Override
    public void delete(Long id) {
        documentoRepository.delete(id);
    }
    
    /***************************************
     * 
     * delete
     * 
     * Elimina una serie de documentos de la base de datos. Los documentos se identifican por sus id
     * 
     * @author Ezentis
     * @param entities Identificadores de los documentos a eliminar
     * 
     *************************************/
    @Override
    public void delete(Iterable<Documento> entities) {
        documentoRepository.delete(entities);
    }
    
    /***************************************
     * 
     * delete
     * 
     * Elimina una serie de documentos de la base de datos. El documento a eliminar se pasa como parámetro.
     * 
     * @author Ezentis
     * @param Documento entity Documento a eliminar
     * 
     *************************************/
    @Override
    public void delete(Documento entity) {
        documentoRepository.delete(entity);
    }
    
    /***************************************
     * 
     * deleteAll
     * 
     * Elimina todos los documentos de la base de datos
     * 
     * @author Ezentis
     *
     *************************************/
    @Override
    public void deleteAll() {
        documentoRepository.deleteAll();
    }
    
    /***************************************
     * 
     * exists
     * 
     * Localiza un documento identificado por su id en la base de datos. Devuelve un booleano con el resultado de la
     * búsqueda.
     * 
     * @author Ezentis
     * @param Long id Identificador del documento a buscar
     * @return boolean Resultado de la búsqueda
     * 
     *************************************/
    @Override
    public boolean exists(Long id) {
        return documentoRepository.exists(id);
    }
    
    /***************************************
     * 
     * findAll
     * 
     * Busca todos los documentos almacenados en base de datos y los devuelve
     * 
     * @author Ezentis
     * @return Iterable<Documento> Todos los documentos almacenados en base de datos
     * 
     *************************************/
    @Override
    public Iterable<Documento> findAll() {
        return documentoRepository.findAll();
    }
    
    /***************************************
     * 
     * findAll
     * 
     * Busca una serie de documentos almacenados en base de datos. Los documentos a buscar están identificados por sus
     * id. Devuelve los documentos buscados
     * 
     * @author Ezentis
     * @param ids Identificadores de los documentos a buscar
     * @return Iterable<Documento> Documentos seleccionados
     * 
     *************************************/
    @Override
    public Iterable<Documento> findAll(Iterable<Long> ids) {
        return documentoRepository.findAll(ids);
    }
    
    /***************************************
     * 
     * findOne
     * 
     * Busca un documento en base de datos identificado por su id y lu devuelve.
     * 
     * @author Ezentis
     * @param Long id Identificador del documento a localizar
     * @return Documento Documento localizado
     * 
     *************************************/
    @Override
    public Documento findOne(Long id) {
        return documentoRepository.findOne(id);
    }
    
    /***************************************
     * 
     * findByFechaBajaIsNotNull
     * 
     * Busca todos los documentos no eliminados.
     * 
     * @author Ezentis
     * @return Iterable<Documento> Documentos seleccionados
     * 
     *************************************/
    @Override
    public List<Documento> findByFechaBajaIsNull() {
        return documentoRepository.findByFechaBajaIsNull();
    }
    
    /***************************************
     * 
     * findByFechaBajaIsNotNull
     * 
     * Busca todos los documentos eliminados.
     * 
     * @author Ezentis
     * @return Iterable<Documento> Documentos seleccionados
     * 
     *************************************/
    @Override
    public List<Documento> findByFechaBajaIsNotNull() {
        return documentoRepository.findByFechaBajaIsNotNull();
    }
    
    /***************************************
     * 
     * save
     * 
     * Guarda una serie de documentos en base de datos. Como parámetro recibe los documentos a guardar y devuelve los
     * documentos guardados.
     * 
     * @author Ezentis
     * @param entities Documentos a salvar
     * @return Iterable<Documento> Documentos salvado
     * 
     *************************************/
    @Override
    public Iterable<Documento> save(Iterable<Documento> entities) {
        return documentoRepository.save(entities);
    }
    
    /***************************************
     * 
     * save
     * 
     * Guarda un documento en base de datos. Como parámetro recibe el documento a guardar y devuelve el documento
     * guardado.
     * 
     * @author Ezentis
     * @param Documento Documento a guardar
     * @return Documento Documento guardado
     *************************************/
    @Override
    public Documento save(Documento entity) {
        return documentoRepository.save(entity);
    }
    
    /***************************************
     * 
     * descargaDocumento
     * 
     * Recibe un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @author Ezentis
     * @param Documento Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws SerialException
     *************************************/
    @Override
    public DefaultStreamedContent descargaDocumento(Documento entity) throws SerialException {
        Documento docu = documentoRepository.findById(entity.getId());
        DocumentoBlob doc = docu.getFichero();
        InputStream stream = doc.getFichero().getBinaryStream();
        return new DefaultStreamedContent(stream, entity.getTipoContenido(), doc.getNombreFichero());
    }
    
    /***************************************
     * 
     * descargaDocumento
     * 
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @author Ezentis
     * @param Documento Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws SerialException
     *************************************/
    @Override
    public DefaultStreamedContent descargaDocumento(Long id) throws SerialException {
        Documento entity = documentoRepository.findById(id);
        
        InputStream stream = entity.getFichero().getFichero().getBinaryStream();
        return new DefaultStreamedContent(stream, entity.getTipoContenido(), entity.getNombre());
    }
    
    /***************************************
     * 
     * cargaDocumento
     * 
     * Recibe un archivo UploadedFile del que recupera los datos para generar un Documento que se almacenará en base da
     * datos. Devuelve el documento almacenado
     * 
     * @author Ezentis
     * @param UploadedFile
     * @return Documento
     * @throws Exception
     * 
     *************************************/
    
    @Override
    public Documento cargaDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException {
        try {
            registroActividadService.altaRegActividad("cargaFichero", TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.GESTOR.getDescripcion());
            
            return documentoRepository.save(crearDocumento(file, tipo, inspeccion));
        } catch (Exception ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw ex;
        }
    }
    
    @Override
    public Documento cargaDocumentoSinGuardar(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException {
        try {
            
            return crearDocumento(file, tipo, inspeccion);
        } catch (Exception ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw ex;
        }
    }
    
    @Override
    public Documento crearDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException {
        Documento docu = new Documento();
        docu.setNombre(file.getFileName());
        docu.setTipoDocumento(tipo);
        if (inspeccion != null) {
            List<Inspeccion> inspecciones = new ArrayList<>();
            inspecciones.add(inspeccion);
            docu.setInspeccion(inspecciones);
        }
        SerialBlob fileBlob = new SerialBlob(StreamUtils.copyToByteArray(file.getInputstream()));
        DocumentoBlob blob = new DocumentoBlob();
        blob.setFichero(fileBlob);
        blob.setNombreFichero(file.getFileName());
        docu.setFichero(blob);
        docu.setTipoContenido(file.getContentType());
        return docu;
    }
    
    /***************************************
     * 
     * extensionCorrecta
     * 
     * Recibe un objeto de tipo UploadedFile y comprueba que el content-type dado por JSF (basándose en su extensión) se
     * corresponde con el content-type real obtenido a través de Tika (basándose en el contenido de la cabecera)
     * 
     * @author Ezentis
     * @param UploadedFile
     * @return boolean
     *
     *************************************/
    
    @Override
    public boolean extensionCorrecta(UploadedFile file) {
        
        String extension = file.getFileName().substring(file.getFileName().lastIndexOf('.') + 1);
        
        List<String> extensionesNoVerificadas = Arrays.asList("mid", "7z", "zip", "csv", "wav", "htm", "html", "txt",
                "wmv", "avi", "png", "bmp", "jpeg", "mp3", "msg", "jpg");
        
        boolean respuesta = extensionesNoVerificadas.contains(extension);
        
        if (!respuesta) {
            String tipo;
            ContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();
            Parser parser;
            
            if (file.getContentType().contains("openxmlformats")) {
                parser = new OOXMLParser();
            } else {
                parser = new AutoDetectParser();
            }
            
            try {
                parser.parse(file.getInputstream(), handler, metadata, pcontext);
                tipo = metadata.get("Content-Type");
            } catch (Exception e) {
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
                tipo = "error";
            }
            
            respuesta = tipo.equalsIgnoreCase(file.getContentType());
        }
        return respuesta;
    }
    
    @Override
    public long getCounCriteria(DocumentoBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Documento.class, "documento");
        
        creaCriteria(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        
        session.close();
        
        return cnt;
    }
    
    @Override
    public List<Documento> buscarGuiaPorCriteria(int firstResult, int maxResults, DocumentoBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Documento.class, "documento");
        
        creaCriteria(busqueda, criteria);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        
        @SuppressWarnings("unchecked")
        List<Documento> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    private void creaCriteria(DocumentoBusqueda busqueda, Criteria criteria) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (busqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) >= '" + sdf.format(busqueda.getFechaDesde()) + "'"));
        }
        if (busqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) <= '" + sdf.format(busqueda.getFechaHasta()) + "'"));
        }
        if (busqueda.getNombre() != null && !busqueda.getNombre().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "this_.nombre", busqueda.getNombre())));
        }
        
        if (busqueda.getTipoDocumento() != null) {
            criteria.add(Restrictions.eq("tipoDocumento", busqueda.getTipoDocumento()));
        }
        
        if (busqueda.getMateriaIndexada() != null) {
            String[] claves = busqueda.getMateriaIndexada().split(",");
            Criterion[] clavesOr = new Criterion[claves.length];
            for (int i = 0; i < claves.length; i++) {
                clavesOr[i] = Restrictions.ilike("materiaIndexada", claves[i].trim(), MatchMode.ANYWHERE);
            }
            criteria.add(Restrictions.or(clavesOr));
        }
        
        if (busqueda.getInspeccion() != null) {
            criteria.createAlias("inspeccion", "inspecciones"); // inner join
            criteria.add(Restrictions.eq("inspecciones.numero", busqueda.getInspeccion().getNumero()));
        }
        
        if (busqueda.isEliminado()) {
            criteria.add(Restrictions.isNotNull("fechaBaja"));
        } else {
            criteria.add(Restrictions.isNull("fechaBaja"));
        }
        
        if (busqueda.getDescripcion() != null) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "this_.descripcion", busqueda.getDescripcion())));
        }
        
        criteria.addOrder(Order.desc("fechaAlta"));
    }
    
    @Override
    public String obtieneNombreFichero(Documento documento) {
        Documento docu = documentoRepository.findById(documento.getId());
        DocumentoBlob doc = docu.getFichero();
        return doc.getNombreFichero();
    }
    
    @Override
    public List<TipoDocumento> listaTiposDocumento() {
        return (List<TipoDocumento>) tipoDocumentoRepository.findAll();
    }
    
    @Override
    public void recuperarDocumento(Documento documento) {
        documento.setFechaBaja(null);
        documento.setUsernameBaja(null);
        save(documento);
    }
    
    @Override
    public void borrarDocumento(Documento documento) {
        delete(documento);
    }
    
    @Override
    public List<Inspeccion> listaInspecciones(Documento documento) {
        return iInspeccionRepository.cargaInspecciones(documento.getId());
    }
    
    @Override
    public List<Long> buscaDocumentoEnCuestionarios(Documento documento) {
        return documentoRepository.buscaRespuestaDocumento(documento.getId());
    }
    
    @Override
    public List<GestDocSolicitudDocumentacion> buscaDocumentoEnSolicitudes(Documento documento) {
        return gestDocSolicitudDocumentacionRepository.findByIdDocumento(documento.getId());
    }
    
    @Override
    public void vaciarPapelera() {
        documentoRepository.deleteByFechaBajaIsNotNull();
        
    }
    
}