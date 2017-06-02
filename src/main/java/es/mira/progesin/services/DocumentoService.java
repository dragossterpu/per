package es.mira.progesin.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.DocumentoBlob;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IDocumentoRepository;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;
import es.mira.progesin.web.beans.DocumentoBusqueda;

/**
 * 
 * Implementación del servicio de documentos.
 * 
 * @author EZENTIS
 *
 */

@Service("documentoService")
public class DocumentoService implements IDocumentoService {
    
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Repositorio de documentos.
     */
    @Autowired
    private IDocumentoRepository documentoRepository;
    
    /**
     * Repositorio de inspecciones.
     */
    @Autowired
    private IInspeccionesRepository inspeccionRepository;
    
    /**
     * Repositorio de tipo de documento.
     */
    @Autowired
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**
     * Elimina un documento de la base de datos. El documento se identifica por su id.
     *
     * @param id Identificador del documento a eliminar
     *
     */
    @Override
    public void delete(Long id) {
        documentoRepository.delete(id);
    }
    
    /**
     * Elimina una serie de documentos de la base de datos. El documento a eliminar se pasa como parámetro.
     * 
     * @param entity Documento a eliminar
     * 
     */
    @Override
    public void delete(Documento entity) {
        documentoRepository.delete(entity);
    }
    
    /**
     * Busca todos los documentos no dados de baja lógica.
     * 
     * 
     * @return Iterable<Documento> Documentos seleccionados
     * 
     */
    @Override
    public List<Documento> findByFechaBajaIsNull() {
        return documentoRepository.findByFechaBajaIsNull();
    }
    
    /**
     * Busca todos los documentos eliminados (dados de baja lógica).
     * 
     * 
     * @return Iterable<Documento> Documentos seleccionados
     * 
     */
    @Override
    public List<Documento> findByFechaBajaIsNotNull() {
        return documentoRepository.findByFechaBajaIsNotNull();
    }
    
    /**
     * Guarda una serie de documentos en base de datos. Como parámetro recibe los documentos a guardar y devuelve los
     * documentos guardados.
     * 
     * @param entities Documentos a salvar
     * @return Iterable<Documento> Documentos salvado
     * 
     */
    @Override
    public Iterable<Documento> save(Iterable<Documento> entities) {
        return documentoRepository.save(entities);
    }
    
    /**
     * Guarda un documento en base de datos. Como parámetro recibe el documento a guardar y devuelve el documento
     * guardado.
     * 
     * @param entity Documento Documento a guardar
     * @return Documento Documento guardado
     */
    @Override
    public Documento save(Documento entity) {
        return documentoRepository.save(entity);
    }
    
    /**
     * Recibe un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @param entity Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws SQLException excepción lanzada
     */
    @Override
    public DefaultStreamedContent descargaDocumento(Documento entity) throws SQLException {
        Documento docu = documentoRepository.findById(entity.getId());
        DocumentoBlob doc = docu.getFichero();
        InputStream stream = new ByteArrayInputStream(doc.getFichero());
        return new DefaultStreamedContent(stream, entity.getTipoContenido(), doc.getNombreFichero());
    }
    
    /**
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * 
     * @param id Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws SQLException excepción lanzada
     */
    @Override
    public DefaultStreamedContent descargaDocumento(Long id) throws SQLException {
        Documento entity = documentoRepository.findById(id);
        InputStream stream = new ByteArrayInputStream(entity.getFichero().getFichero());
        return new DefaultStreamedContent(stream, entity.getTipoContenido(), entity.getNombre());
    }
    
    /**
     * Recibe un archivo UploadedFile del que recupera los datos para generar un Documento que se almacenará en base de
     * datos. Devuelve el documento almacenado.
     * 
     * 
     * @param file fichero a cargar en BDD
     * @param tipo tipo de documentp
     * @param inspeccion inspección asociada al documento
     * @return Documento documento cargado en base de datos
     * @throws SQLException excepción lanzada
     * @throws IOException excepción lanzada
     * 
     */
    @Override
    public Documento cargaDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException {
        try {
            registroActividadService.altaRegActividad("cargaFichero", TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.GESTOR.getDescripcion());
            
            return documentoRepository.save(crearDocumento(file, tipo, inspeccion));
        } catch (SQLException | IOException ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw ex;
        }
    }
    
    /**
     * Recibe un archivo UploadedFile y los datos necesarios para general un Documento pero no lo almacena en base de
     * datos. Sólo deja el objeto preparado para guardarlo.
     * 
     * @param file fichero a cargar en BDD
     * @param tipo tipo de documentp
     * @param inspeccion inspección asociada al documento
     * @return documento cargado en base de datos
     * @throws SQLException excepción lanzada
     * @throws IOException excepción lanzada
     */
    @Override
    public Documento cargaDocumentoSinGuardar(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException {
        try {
            
            return crearDocumento(file, tipo, inspeccion);
        } catch (SQLException | IOException ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw ex;
        }
    }
    
    /**
     * Crea el documento.
     * 
     * @param file Fichero subido por el usuario.
     * @param tipo Tipo de documento.
     * @param inspeccion Inspección a la que se asocia.
     * @return Documento generado
     * @throws SQLException Excepción SQL
     * @throws IOException Excepción entrada/salida
     */
    private Documento crearDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException {
        Documento docu = new Documento();
        docu.setNombre(file.getFileName());
        docu.setTipoDocumento(tipo);
        if (inspeccion != null) {
            List<Inspeccion> inspecciones = new ArrayList<>();
            inspecciones.add(inspeccion);
            docu.setInspeccion(inspecciones);
        }
        byte[] fileBlob = StreamUtils.copyToByteArray(file.getInputstream());
        DocumentoBlob blob = new DocumentoBlob();
        blob.setFichero(fileBlob);
        blob.setNombreFichero(file.getFileName());
        docu.setFichero(blob);
        docu.setTipoContenido(file.getContentType());
        return docu;
    }
    
    /**
     * Consulta el número de registros en base de datos que corresponden a los criterios de búsqueda.
     * 
     * @param busqueda Objeto que contiene los criterios de búsqueda
     * @return número de registros correspondientes a la búsqueda
     */
    @Override
    public int getCounCriteria(DocumentoBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Documento.class, "documento");
        
        creaCriteria(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Consulta en base de datos en base a los parámetros recibidos. La consulta se hace paginada.
     * 
     * @param first Primer elemento a devolver de la búsqueda
     * @param pageSize Número máximo de registros a mostrar
     * @param sortField Campo por el cual se ordena la búsqueda
     * @param sortOrder Sentido de la ordenación
     * @param busqueda Objeto que contiene los criterios de búsqueda
     * @return Lista de los documentos que corresponden a los criterios recibidos
     * 
     */
    @Override
    public List<Documento> buscarDocumentoPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            DocumentoBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Documento.class, "documento");
        
        creaCriteria(busqueda, criteria);
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.asc("id"));
        }
        
        @SuppressWarnings("unchecked")
        List<Documento> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Añade al criteria los parámetros de búsqueda.
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @param criteria Criteria al que se añadirán los parámetros.
     */
    private void creaCriteria(DocumentoBusqueda busqueda, Criteria criteria) {
        if (busqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busqueda.getFechaDesde()));
        }
        
        if (busqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(busqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
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
            criteria.createAlias("inspeccion", "inspecciones");
            criteria.add(Restrictions.eq("inspecciones.id", busqueda.getInspeccion().getId()));
            criteria.add(Restrictions.eq("inspecciones.anio", busqueda.getInspeccion().getAnio()));
            
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
        
    }
    
    /**
     * Devuelve el nombre del fichero contenido en el objeto Documento.
     * 
     * @param documento del cual quiere extraerse el nombre del fichero contenido
     * @return nombre del fichero
     */
    @Override
    public String obtieneNombreFichero(Documento documento) {
        Documento docu = documentoRepository.findById(documento.getId());
        DocumentoBlob doc = docu.getFichero();
        return doc.getNombreFichero();
    }
    
    /**
     * Devuelve la lista de tipos de documentos.
     * 
     * @return lista de tipos de documentos
     */
    @Override
    public List<TipoDocumento> listaTiposDocumento() {
        return (List<TipoDocumento>) tipoDocumentoRepository.findAll();
    }
    
    /**
     * Recupera un documento de la papelera.
     * 
     * @param documento Es el documento a recuperar de la papelera
     */
    @Override
    public void recuperarDocumento(Documento documento) {
        documento.setFechaBaja(null);
        documento.setUsernameBaja(null);
        save(documento);
    }
    
    /**
     * 
     * Devuelve una lista de las inspecciones asociadas al documento pasado como parámetro.
     * 
     * @param documento del que se desean recuperar las inspecciones
     * @return lista de inspecciones asociadas al documento
     */
    @Override
    public List<Inspeccion> listaInspecciones(Documento documento) {
        return inspeccionRepository.cargaInspeccionesDocumento(documento.getId());
    }
    
    /**
     * Devuelve una lista de id de Cuestionarios que tienen asociado el documento pasado como parámetro.
     * 
     * @param documento del que se desean recuperar los cuestionarios
     * @return lista de cuestionarios
     */
    @Override
    public List<Long> buscaDocumentoEnCuestionarios(Documento documento) {
        return documentoRepository.buscaRespuestaDocumento(documento.getId());
    }
    
    /**
     * Devuelve una lista de solicitudes de documentación que tienen asociado el documento pasado como parámetro.
     * 
     * @param documento del que se desean recuperar las solicitudes
     * @return lista de solicitudes
     */
    @Override
    public List<Long> buscaDocumentoEnSolicitudes(Documento documento) {
        return documentoRepository.buscaSolicitudDocumento(documento.getId());
    }
    
    /**
     * Elimina todos los documentos almacenados en la papelera.
     */
    @Override
    public void vaciarPapelera() {
        documentoRepository.deleteByFechaBajaIsNotNull();
        
    }
    
    /**
     * Recupera un tipo de documento a partir de su nombre.
     * 
     * @param nombre nombre del tipo
     * @return tipo de documento
     */
    @Override
    public TipoDocumento buscaTipoDocumentoPorNombre(String nombre) {
        return tipoDocumentoRepository.findByNombre(nombre);
    }
    
}