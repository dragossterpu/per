package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Departamento;

public interface IDepartamentoService {
    
    /**
     * @param departamento
     * @return Departamento actualizado
     */
    Departamento save(Departamento departamento);
    
    /**
     * Busca todos los departamentos de trabajo dados de alta en la BBDD
     * @return Iterable<Departamento>
     */
    Iterable<Departamento> findAll();
    
    /**
     * departamentos sin fecha de baja, es decir activos
     * @return List<Departamento>
     */
    List<Departamento> findByFechaBajaIsNull();
    
}
