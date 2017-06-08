package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Departamento;

/**
 * Interfaz del servicio para la gestión de departamentos.
 * 
 * @author EZENTIS
 *
 */
public interface IDepartamentoService {
    
    /**
     * Guarda o actualiza un departamento.
     * 
     * @param departamento a guardar
     * @return Departamento actualizado
     */
    Departamento save(Departamento departamento);
    
    /**
     * Busca todos los departamentos de trabajo dados de alta en la BBDD.
     * @return lista con todos los departamentos en BBDD
     */
    List<Departamento> findAll();
    
    /**
     * Elimina un departamento.
     * 
     * @param id clave del departamento
     */
    void delete(Long id);
    
}
