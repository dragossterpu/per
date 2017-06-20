package es.mira.progesin.services;

import java.io.File;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.util.PdfGenerator;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Implementación del servicio de estadísticas.
 * 
 * @author EZENTIS
 *
 */
@Service
public class EstadisticaService implements IEstadisticaService {
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    /**
     * Generador de PDF.
     */
    @Autowired
    PdfGenerator generadorPDF;
    
    /**
     * Obtiene los datos de estadísticas agrupados por el estado de la inspección.
     * 
     * @param filtro Filtro que se aplica a la búsqueda.
     * @return Mapa de las inspecciones agrupadas por su estado.
     */
    @Override
    public Map<EstadoInspeccionEnum, Integer> obtenerEstadisticas(InspeccionBusqueda filtro) {
        Map<EstadoInspeccionEnum, Integer> estadistica = new EnumMap<>(EstadoInspeccionEnum.class);
        
        List<EstadoInspeccionEnum> listaEstados = Arrays.stream(EstadoInspeccionEnum.values())
                .collect(Collectors.toList());
        
        for (EstadoInspeccionEnum estado : listaEstados) {
            estadistica.put(estado, 0);
        }
        
        List<Inspeccion> listaInspeccionesFiltrada = inspeccionesService.buscarInspeccionPorCriteria(0, 0, "id",
                SortOrder.ASCENDING, filtro);
        
        for (Inspeccion ins : listaInspeccionesFiltrada) {
            int actual = 0;
            
            if (estadistica.get(ins.getEstadoInspeccion()) != null) {
                actual = estadistica.get(ins.getEstadoInspeccion());
            }
            
            estadistica.put(ins.getEstadoInspeccion(), actual + 1);
            
        }
        return estadistica;
    }
    
    /**
     * Carga la lista de inspecciones que se encuentran en el estado solicitado.
     * 
     * @param filtro Filtro que se aplica a la búsqueda.
     * @param estado Estado que se desea consultar
     * @return Listado de las inspecciones que corresponden a la búsqueda.
     */
    
    @Override
    public List<Inspeccion> verListaEstado(InspeccionBusqueda filtro, EstadoInspeccionEnum estado) {
        filtro.setEstado(estado);
        return inspeccionesService.buscarInspeccionPorCriteria(0, 0, "id", SortOrder.ASCENDING, filtro);
    }
    
    /**
     * Exporta las estadísticas a un fichero PDF descargable.
     * 
     * @param filtro Filtro que se aplica a la búsqueda.
     * @param listaEstadosSeleccionados Lista de los estados que serán exportados
     * @param fileImg Fichero con la imagen que se incrustará en el PDF.
     * @return Flujo de datos con el PDF generado
     * @throws ProgesinException Posible excepción
     */
    @Override
    public StreamedContent exportar(InspeccionBusqueda filtro, List<EstadoInspeccionEnum> listaEstadosSeleccionados,
            File fileImg) throws ProgesinException {
        Map<EstadoInspeccionEnum, List<Inspeccion>> mapaEstados = new EnumMap<>(EstadoInspeccionEnum.class);
        
        for (EstadoInspeccionEnum estado : listaEstadosSeleccionados) {
            filtro.setEstado(estado);
            List<Inspeccion> listaInspecciones = inspeccionesService.buscarInspeccionPorCriteria(0, 0, "id",
                    SortOrder.ASCENDING, filtro);
            mapaEstados.put(estado, listaInspecciones);
        }
        
        return generadorPDF.generarInformeEstadisticas(mapaEstados, filtro, fileImg);
        
    }
    
    /**
     * Obtiene el total de resultados de la consulta estadística.
     * 
     * @param estadistica Mapa de valores de la consulta.
     * @return Número total de inspecciones de las que se hace estadística
     */
    @Override
    public int obtenerTotal(Map<EstadoInspeccionEnum, Integer> estadistica) {
        int total = 0;
        for (EstadoInspeccionEnum clave : estadistica.keySet()) {
            total += estadistica.get(clave);
        }
        
        return total;
    }
    
}
