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
     * @return Iterable<Departamento> iterable con todos los departamentos en BBDD
     */
    Iterable<Departamento> findAll();
    
    /**
     * Busca todos los departamentos sin fecha de baja, es decir activos.
     * @return List<Departamento> lista departamentos en alta
     */
    List<Departamento> findByFechaBajaIsNull();
    
    /**
     * Comprueba si existen usuarios asociados a un departamento.
     * @param departamento a comprobar
     * @return resultado booleano
     */
    boolean existenUsuariosDepartamento(Departamento departamento);
}
