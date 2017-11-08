package es.mira.progesin.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
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
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
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
     * @return Lista de documentos seleccionados
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
     * @return Lista de documentos seleccionados
     * 
     */
    @Override
    public List<Documento> findByFechaBajaIsNotNull() {
        return documentoRepository.findByFechaBajaIsNotNull();
    }
    
    /**
     * Devuelve un documento localizado por su id.
     * 
     * @param id Identificador del documento
     * @return Documento
     */
    @Override
    public Documento findOne(Long id) {
        return documentoRepository.findOne(id);
    }
    
    /**
     * Guarda una serie de documentos en base de datos. Como parámetro recibe los documentos a guardar y devuelve los
     * documentos guardados.
     * 
     * @param entities Documentos a salvar
     * @return lista de documentos salvados
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
     * @return documento guardado
     */
    @Override
    @Transactional(readOnly = false)
    public Documento save(Documento entity) {
        return documentoRepository.save(entity);
    }
    
    /**
     * Recibe un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @param entity Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws ProgesinException Excepción lanzada
     */
    @Override
    public DefaultStreamedContent descargaDocumento(Documento entity) throws ProgesinException {
        Documento docu = documentoRepository.findById(entity.getId());
        DefaultStreamedContent streamDocumento;
        if (docu != null) {
            DocumentoBlob doc = docu.getFichero();
            InputStream stream = new ByteArrayInputStream(doc.getFichero());
            streamDocumento = new DefaultStreamedContent(stream, entity.getTipoContenido(), doc.getNombreFichero());
        } else {
            throw new ProgesinException(new Exception("Se ha producido un error al descargar el documento"));
        }
        return streamDocumento;
    }
    
    /**
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @param id Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     */
    @Override
    public DefaultStreamedContent descargaDocumento(Long id) throws ProgesinException {
        Documento entity = documentoRepository.findById(id);
        DefaultStreamedContent streamDocumento;
        if (entity != null) {
            DocumentoBlob doc = entity.getFichero();
            InputStream stream = new ByteArrayInputStream(entity.getFichero().getFichero());
            streamDocumento = new DefaultStreamedContent(stream, entity.getTipoContenido(), doc.getNombreFichero());
        } else {
            throw new ProgesinException(new Exception("Se ha producido un error al descargar el documento"));
        }
        return streamDocumento;
        
    }
    
    /**
     * Recibe un archivo UploadedFile del que recupera los datos para generar un Documento que se almacenará en base de
     * datos. Devuelve el documento almacenado.
     * 
     * @param file fichero a cargar en BDD
     * @param tipo tipo de documentp
     * @param inspeccion inspección asociada al documento
     * @return Documento documento cargado en base de datos
     * @throws ProgesinException Excepción lanzada
     * 
     */
    @Override
    public Documento cargaDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws ProgesinException {
        try {
            Documento documento = documentoRepository.save(crearDocumento(file, tipo, inspeccion));
            String mensaje = "Se ha cargado el documento " + documento.getNombre() + " para la inspección "
                    + inspeccion.getNumero();
            registroActividadService.altaRegActividad(mensaje, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.GESTOR.getDescripcion());
            
            return documento;
        } catch (DataAccessException | IOException ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw new ProgesinException(ex);
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
     * @throws ProgesinException excepción lanzada
     */
    @Override
    public Documento cargaDocumentoSinGuardar(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws ProgesinException {
        try {
            
            return crearDocumento(file, tipo, inspeccion);
        } catch (IOException ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw new ProgesinException(ex);
        }
    }
    
    /**
     * Crea el documento.
     * 
     * @param file Fichero subido por el usuario.
     * @param tipo Tipo de documento.
     * @param inspeccion Inspección a la que se asocia.
     * @return Documento generado
     * @throws DataAccessException Excepción SQL
     * @throws IOException Excepción entrada/salida
     */
    private Documento crearDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion) throws IOException {
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
     * @param busquedaDocumento Objeto que contiene los criterios de búsqueda
     * @return Lista de los documentos que corresponden a los criterios recibidos
     * 
     */
    @Override
    public List<Documento> buscarDocumentoPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            DocumentoBusqueda busquedaDocumento) {
        Session session = sessionFactory.openSession();
        Criteria criteriaDocumento = session.createCriteria(Documento.class, "documento");
        
        creaCriteria(busquedaDocumento, criteriaDocumento);
        
        User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean esEquipo = RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuario.getRole());
        
        if (esEquipo) {
            criteriaService.creaCriteriaEquipoInspeccion(criteriaDocumento, usuario.getUsername(), busquedaDocumento);
        } else {
            if (busquedaDocumento.getInspeccion() != null) {
                criteriaDocumento.createAlias("inspeccion", "inspecciones");
                criteriaDocumento.add(Restrictions.eq("inspecciones.id", busquedaDocumento.getInspeccion().getId()));
                criteriaDocumento
                        .add(Restrictions.eq("inspecciones.anio", busquedaDocumento.getInspeccion().getAnio()));
                
            }
        }
        
        criteriaService.prepararPaginacionOrdenCriteria(criteriaDocumento, first, pageSize, sortField, sortOrder, "id");
        
        @SuppressWarnings("unchecked")
        List<Documento> listado = criteriaDocumento.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Añade al criteria los parámetros de búsqueda.
     * 
     * @param busquedaDocumento Objeto que contiene los parámetros de búsqueda
     * @param criteria Criteria al que se añadirán los parámetros.
     */
    private void creaCriteria(DocumentoBusqueda busquedaDocumento, Criteria criteria) {
        
        if (busquedaDocumento.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busquedaDocumento.getFechaDesde()));
        }
        
        if (busquedaDocumento.getFechaHasta() != null) {
            Date fechaHasta = new Date(busquedaDocumento.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (busquedaDocumento.getNombre() != null) {
            criteria.add(Restrictions.ilike("nombre", busquedaDocumento.getNombre(), MatchMode.ANYWHERE));
        }
        
        if (busquedaDocumento.getTipoDocumento() != null) {
            criteria.add(Restrictions.eq("tipoDocumento", busquedaDocumento.getTipoDocumento()));
        }
        
        // if (busquedaDocumento.getInspeccion() != null) {
        // criteria.createAlias("inspeccion", "inspecciones");
        // criteria.add(Restrictions.eq("inspecciones.id", busquedaDocumento.getInspeccion().getId()));
        // criteria.add(Restrictions.eq("inspecciones.anio", busquedaDocumento.getInspeccion().getAnio()));
        //
        // }
        
        if (busquedaDocumento.isEliminado()) {
            criteria.add(Restrictions.isNotNull("fechaBaja"));
        } else {
            criteria.add(Restrictions.isNull("fechaBaja"));
        }
        
        if (busquedaDocumento.getDescripcion() != null) {
            criteria.add(Restrictions.ilike("descripcion", busquedaDocumento.getDescripcion(), MatchMode.ANYWHERE));
        }
        
        criteriaMateriaIndexada(criteria, busquedaDocumento.getMateriaIndexada());
    }
    
    /**
     * Añade al criteria el filtro de la materia indexada introducida en el formulario.
     * 
     * @param criteria Criteria al que se añadirán los parámetros.
     * @param materiaIndexada materia indexada introducida en el filtro (separada por comas)
     */
    private void criteriaMateriaIndexada(Criteria criteria, String materiaIndexada) {
        if (materiaIndexada != null) {
            String[] claves = materiaIndexada.split(",");
            Criterion[] clavesOr = new Criterion[claves.length];
            for (int i = 0; i < claves.length; i++) {
                clavesOr[i] = Restrictions.ilike("materiaIndexada", claves[i].trim(), MatchMode.ANYWHERE);
            }
            criteria.add(Restrictions.or(clavesOr));
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
     * Devuelve el número de cuestionarios enviados que tienen adjunta la plantilla pasada como parámetro.
     * 
     * @param documento Identificador de la plantilla
     * @return Número de cuestionarios en los que está adjunta la plantilla
     */
    @Override
    public Long plantillaPerteneceACuestionario(Documento documento) {
        return documentoRepository.plantillaPerteneceACuestionario(documento.getId());
    }
    
    /**
     * Elimina todos los documentos almacenados en la papelera.
     */
    @Override
    public List<Documento> vaciarPapelera() {
        List<Documento> listaEliminar = documentoRepository.findByFechaBajaIsNotNull();
        documentoRepository.delete(listaEliminar);
        return listaEliminar;
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
    
    /**
     * Devuelve los documentos que corresponden a un tipo de documento.
     * 
     * @param tipoDocumento Nombre del tipo de documento
     * @return Listado de documentos
     */
    @Override
    public List<Documento> buscaNombreTipoDocumento(String tipoDocumento) {
        return documentoRepository.buscaNombreTipoDocumento(tipoDocumento);
    }
    
    /**
     * Devuelve los documentos de tipo plantilla asociados a un cuestionario enviado.
     * 
     * @param idCuestionarioEnviado Identificador del cuestionario.
     * @return Listado de plantillas.
     */
    @Override
    public List<Documento> findPlantillas(Long idCuestionarioEnviado) {
        return documentoRepository.findPlantillas(idCuestionarioEnviado);
    }
    
    /**
     * Indica si el documento pasado por parámetro está adjunto a alguna inspección en la que el usuario está implicado.
     * 
     * @param usuario Usuario del que se desea conocer si alguna de sus inspecciones tiene el documento asignado.
     * @param documento Documento del que se desea consultar si está asignado a una inspección del usuario.
     * @return Respuesta a la consulta
     */
    @Override
    public boolean documentoEnInspeccionUsuario(User usuario, Documento documento) {
        boolean respuesta = false;
        List<Inspeccion> listaInspeccionesAsociadas = listaInspecciones(documento);
        boolean esEquipo = RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuario.getRole());
        
        if (listaInspeccionesAsociadas.isEmpty() || !esEquipo) {
            respuesta = true;
        } else {
            List<Inspeccion> listaInspeccionesUsuario = inspeccionRepository
                    .findInspeccionesByUsuario(usuario.getUsername());
            for (Inspeccion ins : listaInspeccionesAsociadas) {
                if (listaInspeccionesUsuario.contains(ins)) {
                    respuesta = true;
                }
            }
        }
        return respuesta;
    }
    
}