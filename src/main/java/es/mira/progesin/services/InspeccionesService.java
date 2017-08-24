package es.mira.progesin.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.web.beans.InspeccionBusqueda;
import lombok.NoArgsConstructor;

/**
 * Implementación del servicio de inspecciones.
 * 
 * @author EZENTIS
 */
@NoArgsConstructor
@Service
public class InspeccionesService implements IInspeccionesService {
    /**
     * Contante antes.
     */
    private static final String ANTES = " (antes: ";
    
    /**
     * Contante antes.
     */
    private static final String AHORA = ", ahora: ";
    
    /**
     * Contante nueva línea.
     */
    private static final String NUEVALINEA = "\n";
    
    /**
     * Variable utilizada para inyectar la SessionFactory.
     * 
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Variable utilizada para inyectar el repositorio de inspecciones.
     * 
     */
    @Autowired
    private IInspeccionesRepository inspeccionesRepository;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * Constructor usado para el test.
     * 
     * @param sessionFact Factoría de sesiones
     */
    public InspeccionesService(SessionFactory sessionFact) {
        this.sessionFactory = sessionFact;
    }
    
    /**
     * Método que guarda una inspección.
     * @param inspeccion a guardar
     * @return devuelve la inspección guardada
     */
    @Override
    @Transactional(readOnly = false)
    public Inspeccion save(Inspeccion inspeccion) {
        return inspeccionesRepository.save(inspeccion);
    }
    
    /**
     * Borra una inspección pasada por parámetro.
     * @param inspeccion a borrar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Inspeccion inspeccion) {
        inspeccionesRepository.delete(inspeccion);
    }
    
    /**
     * Busca inspecciones no finalizadas filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(String infoInspeccion) {
        return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumero("%" + infoInspeccion + "%");
    }
    
    /**
     * Busca inspecciones filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String infoInspeccion,
            String usernameJefeEquipo) {
        return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo("%" + infoInspeccion + "%",
                usernameJefeEquipo);
    }
    
    /**
     * Busca una inspección con cierto id pasado por parámetro.
     * 
     * @param id de la inspección
     * @return devuelve inspección si es encontrada.
     */
    @Override
    public Inspeccion findInspeccionById(Long id) {
        return inspeccionesRepository.findOne(id);
    }
    
    /**
     * Método que realiza una consulta de inspecciones, usando criteria, coincidente con determinados parámetros.
     * 
     * @param first primer registro
     * @param pageSize tamaño de la página (número de registros que queremos)
     * @param sortField campo por el que ordenamos
     * @param sortOrder si la ordenación es ascendente o descendente
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return lista de inspecciones resultado de la consulta
     */
    @Override
    public List<Inspeccion> buscarInspeccionPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            InspeccionBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspecciones(busqueda, criteria);
        
        criteria.setFirstResult(first);
        if (pageSize > 0) {
            criteria.setMaxResults(pageSize);
        }
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.desc("fechaAlta"));
        }
        
        @SuppressWarnings("unchecked")
        List<Inspeccion> listaInspecciones = criteria.list();
        session.close();
        
        return listaInspecciones;
    }
    
    /**
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return número de registros encontrados
     */
    @Override
    public int getCountInspeccionCriteria(InspeccionBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspecciones(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Método que realiza una consulta de inspecciones, usando criteria, coincidente con determinados parámetros.
     * 
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return lista de inspecciones resultado de la consulta
     */
    
    @Override
    public List<Inspeccion> buscarInspeccionPorCriteriaEstadisticas(InspeccionBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspeccionesEstadistica(busqueda, criteria);
        
        @SuppressWarnings("unchecked")
        List<Inspeccion> listaInspecciones = criteria.list();
        session.close();
        
        return listaInspecciones;
    }
    
    /**
     * Consulta criteria para búsqueda de inspecciones para la generación de estadísticas.
     * 
     * @param busquedaInspecciones filtro de búsqueda
     * @param criteria objeto criteria
     */
    
    private void consultaCriteriaInspeccionesEstadistica(InspeccionBusqueda busquedaInspecciones, Criteria criteria) {
        
        if (busquedaInspecciones.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busquedaInspecciones.getFechaDesde()));
        }
        
        if (busquedaInspecciones.getFechaHasta() != null) {
            Date fechaHasta = new Date(busquedaInspecciones.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (busquedaInspecciones.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busquedaInspecciones.getTipoInspeccion()));
        }
        
        if (busquedaInspecciones.getListaEstados() != null) {
            criteria.add(Restrictions.in("estadoInspeccion", (busquedaInspecciones.getListaEstados())));
        }
        
        if (busquedaInspecciones.getProvincia() != null) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Municipio.class, "munic");
            subquery.add(Restrictions.eq("munic.provincia", busquedaInspecciones.getProvincia()));
            subquery.setProjection(Projections.property("munic.id"));
            criteria.add(Property.forName("inspeccion.municipio").in(subquery));
        }
    }
    
    /**
     * Consulta criteria para búsqueda de inspecciones.
     * 
     * @param busquedaInspecciones filtro de búsqueda
     * @param criteria objeto criteria
     */
    private void consultaCriteriaInspecciones(InspeccionBusqueda busquedaInspecciones, Criteria criteria) {
        
        criteriaFechasInspeccion(criteria, busquedaInspecciones);
        
        if (busquedaInspecciones.getId() != null) {
            criteria.add(Restrictions.eq("id", Long.parseLong(busquedaInspecciones.getId())));
        }
        
        if (busquedaInspecciones.getUsuarioCreacion() != null) {
            criteria.add(
                    Restrictions.ilike("usernameAlta", busquedaInspecciones.getUsuarioCreacion(), MatchMode.ANYWHERE));
        }
        
        if (busquedaInspecciones.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busquedaInspecciones.getTipoInspeccion()));
        }
        
        if (busquedaInspecciones.getAmbito() != null) {
            criteria.add(Restrictions.eq("ambito", busquedaInspecciones.getAmbito()));
        }
        
        if (busquedaInspecciones.getNombreUnidad() != null) {
            criteria.add(
                    Restrictions.ilike("nombreUnidad", busquedaInspecciones.getNombreUnidad(), MatchMode.ANYWHERE));
        }
        
        if (busquedaInspecciones.getEstado() != null) {
            criteria.add(Restrictions.eq("estadoInspeccion", busquedaInspecciones.getEstado()));
        }
        
        if (busquedaInspecciones.getTipoUnidad() != null) {
            criteria.add(Restrictions.eq("tipoUnidad", busquedaInspecciones.getTipoUnidad()));
        }
        
        criteriaMunicipioProvincia(criteria, busquedaInspecciones);
        
        criteriaEquipo(criteria, busquedaInspecciones);
        
        criteraAsociarInspeccionModificar(criteria, busquedaInspecciones.isAsociar(),
                busquedaInspecciones.getInspeccionModif());
    }
    
    /**
     * Añade al criteria los criterios relacionados fechas.
     * 
     * @param criteria Criteria
     * @param busquedaInspecciones Filtro de búsqueda con las fechas (desde, hasta, año, cuatrimestre)
     */
    private void criteriaFechasInspeccion(Criteria criteria, InspeccionBusqueda busquedaInspecciones) {
        criteria.add(Restrictions.isNull("fechaBaja"));
        
        if (busquedaInspecciones.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busquedaInspecciones.getFechaDesde()));
        }
        
        if (busquedaInspecciones.getFechaHasta() != null) {
            Date fechaHasta = new Date(busquedaInspecciones.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (busquedaInspecciones.getAnio() != null) {
            criteria.add(Restrictions.eq("anio", Integer.parseInt(busquedaInspecciones.getAnio())));
        }
        if (busquedaInspecciones.getCuatrimestre() != null) {
            criteria.add(Restrictions.eq("cuatrimestre", busquedaInspecciones.getCuatrimestre()));
        }
    }
    
    /**
     * Añade al criteria los criterios relacionados con el equipo de una inspección.
     * 
     * @param criteria Criteria
     * @param busquedaInspecciones Filtro de búsqueda
     */
    private void criteriaEquipo(Criteria criteria, InspeccionBusqueda busquedaInspecciones) {
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        if (busquedaInspecciones.getEquipo() != null) {
            criteria.add(Restrictions.eq("equipo", busquedaInspecciones.getEquipo()));
        }
        
        if (busquedaInspecciones.getJefeEquipo() != null) {
            criteria.add(Restrictions.ilike("equipo.jefeEquipo.username", busquedaInspecciones.getJefeEquipo(),
                    MatchMode.ANYWHERE));
        }
        
        // Desde el buscador muestro sólo las inspecciones de su mismo equipo, desde asociar inspecciones muestro todas
        if (!busquedaInspecciones.isAsociar()) {
            criteriaService.setCriteriaEquipo(criteria);
        }
    }
    
    /**
     * Añade al criteria el filtro del municipio o la provincia.
     * 
     * @param criteria Criteria
     * @param busquedaInspecciones Filtro de búsqueda
     */
    private void criteriaMunicipioProvincia(Criteria criteria, InspeccionBusqueda busquedaInspecciones) {
        criteria.createAlias("inspeccion.municipio", "municipio"); // inner join
        if (busquedaInspecciones.getMunicipio() != null) {
            criteria.add(Restrictions.eq("municipio", busquedaInspecciones.getMunicipio()));
        } else if (busquedaInspecciones.getProvincia() != null) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Municipio.class, "munic");
            subquery.add(Restrictions.eq("munic.provincia", busquedaInspecciones.getProvincia()));
            subquery.setProjection(Projections.property("munic.id"));
            criteria.add(Property.forName("inspeccion.municipio").in(subquery));
        }
    }
    
    /**
     * Elimina del resultado de la busqueda la inspección que está modificando.
     * 
     * @param criteria Criteria
     * @param isAsociar true si va al buscador desde la opción "Asociar inspecciones"
     * @param inspeccion inspección a modificar
     */
    private void criteraAsociarInspeccionModificar(Criteria criteria, boolean isAsociar, Inspeccion inspeccion) {
        if (isAsociar && inspeccion != null && inspeccion.getId() != null) {
            criteria.add(Restrictions.ne("id", inspeccion.getId()));
        }
    }
    
    /**
     * Devuelve la lista de inspecciones asociadas a otra.
     * 
     * @param inspeccion pasada por parámetro
     * @return devuelve lista de inspecciones asociadas
     */
    @Override
    public List<Inspeccion> listaInspeccionesAsociadas(Inspeccion inspeccion) {
        return inspeccionesRepository.cargaInspeccionesAsociadas(inspeccion.getId());
    }
    
    /**
     * Comprueba si existe una inspección de determinado tipo.
     * 
     * @param tipo de inspección
     * @return valor booleando de la comprobación
     */
    @Override
    public boolean existeByTipoInspeccion(TipoInspeccion tipo) {
        return inspeccionesRepository.existsByTipoInspeccion(tipo);
    }
    
    /**
     * Busca inspecciones filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarPorNombreUnidadONumero(String infoInspeccion) {
        return inspeccionesRepository.buscarPorNombreUnidadONumero("%" + infoInspeccion + "%");
    }
    
    /**
     * Cambia el estado de una inspección.
     * 
     * @param inspeccion a cambiar
     * @param estado a poner
     */
    @Override
    public void cambiarEstado(Inspeccion inspeccion, EstadoInspeccionEnum estado) {
        inspeccion.setEstadoInspeccion(estado);
        inspeccionesRepository.save(inspeccion);
    }
    
    /**
     * Devuelve un listado de inspecciones que están en un estado recibido como parámetro.
     * 
     * @param estado Estado de inspección a buscar
     * @return Resultado de la búsqueda
     */
    @Override
    public List<Inspeccion> findByEstadoInspeccion(EstadoInspeccionEnum estado) {
        return inspeccionesRepository.findByEstadoInspeccion(estado);
    }
    
    /**
     * Devuelve las inspecciones en las que ha participado un usuario.
     * 
     * @param usuario Usuario consultado
     * @return Listado de las inspecciones en las que ha participado.
     */
    @Override
    public List<Inspeccion> findInspeccionesByUsuario(String usuario) {
        return inspeccionesRepository.findInspeccionesByUsuario(usuario);
    }
    
    /**
     * Devuelve un listado de inspecciones a partir de su nombre de unidad o año. Los resultados se filtran para
     * devolver sólo aquellos en los que el usuario está implicado.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @param usuario Usuario por el que se filtra
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarPorNombreUnidadONumeroUsuario(String infoInspeccion, User usuario) {
        return inspeccionesRepository.buscarPorNombreUnidadONumeroYUsuario("%" + infoInspeccion + "%", usuario);
    }
    
    /**
     * Método que devuelve si existen inspecciones sin finalizar para un determinado equipo.
     * 
     * @param equipo de la inspección
     * @return valor booleano dependiendo de si existe una inspección finalizada o no
     */
    @Override
    public boolean existenInspeccionesNoFinalizadas(Equipo equipo) {
        return inspeccionesRepository.existsByEquipoAndFechaFinalizacionIsNull(equipo);
    }
    
    /**
     * Devuelve una cadena con el texto del registro utilizado en la modificación de una inspección.
     * 
     * @param insp inspección actual
     * @param inspMod inspección modificada
     * @param inspecciones inspecciones asociadas actuales
     * @param inspeccionesMod inspecciones asociadas modificadas
     * @return descripcion
     */
    
    @Override
    public String getTextoRegistro(Inspeccion insp, Inspeccion inspMod, List<Inspeccion> inspecciones,
            List<Inspeccion> inspeccionesMod) {
        StringBuilder descripcion = new StringBuilder();
        
        descripcion.append(getTextoAnio(insp, inspMod));
        descripcion.append(getTextoAmbito(insp, inspMod));
        descripcion.append(getTextoTipoInspeccion(insp, inspMod));
        descripcion.append(getTextoEstadoInspeccion(insp, inspMod));
        descripcion.append(getTextoFechaPrevista(insp, inspMod));
        descripcion.append(getTextoEquipo(insp, inspMod));
        descripcion.append(getTextoCuatrimestre(insp, inspMod));
        descripcion.append(getTextoTipoUnidad(insp, inspMod));
        descripcion.append(getTextoNombreUnidad(insp, inspMod));
        descripcion.append(getTextoMinicipio(insp, inspMod));
        descripcion.append(getTextoInspeccionesAsociadas(inspecciones, inspeccionesMod));
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del año.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoAnio(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getAnio().equals(inspMod.getAnio())) {
            descripcion.append(NUEVALINEA + "Año" + ANTES + insp.getAnio() + AHORA + inspMod.getAnio() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del ámbito.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoAmbito(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getAmbito().equals(inspMod.getAmbito())) {
            descripcion.append(NUEVALINEA + "Ámbito" + ANTES + insp.getAmbito().getDescripcion() + AHORA
                    + inspMod.getAmbito().getDescripcion() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del tipo de inspección.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoTipoInspeccion(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getTipoInspeccion().equals(inspMod.getTipoInspeccion())) {
            descripcion.append(NUEVALINEA + "Tipo inspección" + ANTES + insp.getTipoInspeccion().getDescripcion()
                    + AHORA + inspMod.getTipoInspeccion().getDescripcion() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del estado de la inspección.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoEstadoInspeccion(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getEstadoInspeccion().equals(inspMod.getEstadoInspeccion())) {
            descripcion.append(NUEVALINEA + "Estado inspección" + ANTES + insp.getEstadoInspeccion().getDescripcion()
                    + AHORA + inspMod.getEstadoInspeccion().getDescripcion() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto de la fecha prevista.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoFechaPrevista(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        LocalDate localDateInsp = insp.getFechaPrevista().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateInspMod = inspMod.getFechaPrevista().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (!localDateInsp.isEqual(localDateInspMod)) {
            descripcion.append(NUEVALINEA + "Fecha prevista" + ANTES + localDateInsp + AHORA + localDateInspMod + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del equipo.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoEquipo(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getEquipo().equals(inspMod.getEquipo())) {
            descripcion.append(NUEVALINEA + "Equipo" + ANTES + insp.getEquipo().getNombreEquipo() + AHORA
                    + inspMod.getEquipo().getNombreEquipo() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del cuatrimestre.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoCuatrimestre(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getCuatrimestre().equals(inspMod.getCuatrimestre())) {
            descripcion.append(NUEVALINEA + "Cuatrimestre" + ANTES + insp.getCuatrimestre().getDescripcion() + AHORA
                    + inspMod.getCuatrimestre().getDescripcion() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del tipo de unidad.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoTipoUnidad(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getTipoUnidad().equals(inspMod.getTipoUnidad())) {
            descripcion.append(NUEVALINEA + "Tipo unidad" + ANTES + insp.getTipoUnidad().getDescripcion() + AHORA
                    + inspMod.getTipoUnidad().getDescripcion() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del nombre de unidad.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoNombreUnidad(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getNombreUnidad().trim().equals(inspMod.getNombreUnidad().trim())) {
            descripcion.append(NUEVALINEA + "Nombre unidad" + ANTES + insp.getNombreUnidad() + AHORA
                    + inspMod.getNombreUnidad() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto del nombre de unidad.
     * 
     * @param insp inspeccion actual
     * @param inspMod inspección modificada
     * @return cadena
     */
    private String getTextoMinicipio(Inspeccion insp, Inspeccion inspMod) {
        StringBuilder descripcion = new StringBuilder();
        if (!insp.getMunicipio().equals(inspMod.getMunicipio())) {
            if (!insp.getMunicipio().getProvincia().equals(inspMod.getMunicipio().getProvincia())) {
                descripcion.append(NUEVALINEA + "Provincia" + ANTES + insp.getMunicipio().getProvincia().getNombre()
                        + AHORA + inspMod.getMunicipio().getProvincia().getNombre() + ")");
            }
            descripcion.append(NUEVALINEA + "Municipio" + ANTES + insp.getMunicipio().getName() + AHORA
                    + inspMod.getMunicipio().getName() + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve el texto de las inspecciones asociadas.
     * 
     * @param inspecciones actuales
     * @param inspeccionesMod inspecciones modificadas
     * @return cadena
     */
    private String getTextoInspeccionesAsociadas(List<Inspeccion> inspecciones, List<Inspeccion> inspeccionesMod) {
        StringBuilder descripcion = new StringBuilder();
        List<Inspeccion> inspsAux = new ArrayList<>(inspecciones);
        boolean contienenMismosObjetos = inspsAux.removeAll(inspeccionesMod);
        
        if ((inspecciones.size() != inspeccionesMod.size() || !contienenMismosObjetos) && !inspeccionesMod.isEmpty()) {
            descripcion.append(NUEVALINEA + "inspecciones asociadas" + ANTES + getNumInspecciones(inspecciones) + AHORA
                    + getNumInspecciones(inspeccionesMod) + ")");
        }
        return descripcion.toString();
    }
    
    /**
     * Devuelve una cadena con los números de inspección de una lista.
     * 
     * @param inspecciones lista
     * @return cadena
     */
    private String getNumInspecciones(List<Inspeccion> inspecciones) {
        StringBuilder cadena = new StringBuilder();
        for (Inspeccion i : inspecciones) {
            cadena.append(i.getNumero() + "; ");
        }
        return cadena.toString();
    }
    
}
