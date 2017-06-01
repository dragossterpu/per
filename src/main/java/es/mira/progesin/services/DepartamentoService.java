package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.repositories.IDepartamentoRepository;

/**
 * Implementación del servicio de departamentos.
 * 
 * @author EZENTIS
 *
 */
@Service
public class DepartamentoService implements IDepartamentoService {
    
    /**
     * Variable utilizada para inyectar el repositorio de departamentos.
     * 
     */
    @Autowired
    IDepartamentoRepository departamentoRepository;
    
    /**
     * Variable utilizada para inyectar el servicio de usuarios.
     * 
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Guarda o actualiza un departamento.
     * 
     * @param departamento a guardar
     * @return Departamento actualizado
     */
    @Override
    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
    
    /**
     * Busca todos los departamentos de trabajo dados de alta en la BBDD.
     * @return Iterable<Departamento> iterable con todos los departamentos en BBDD
     */
    @Override
    public Iterable<Departamento> findAll() {
        return departamentoRepository.findAll();
    }
    
    /**
     * Busca todos los departamentos sin fecha de baja, es decir activos.
     * @return List<Departamento> lista departamentos en alta
     */
    @Override
    public List<Departamento> findByFechaBajaIsNull() {
        return departamentoRepository.findByFechaBajaIsNull();
    }
    
    /**
     * Comprueba si existen usuarios asociados a un departamento.
     * @param departamento a comprobar
     * @return resultado booleano
     */
    @Override
    public boolean existenUsuariosDepartamento(Departamento departamento) {
        boolean tieneUsuarios = false;
        List<User> usuarios = userService.findByDepartamento(departamento);
        if (usuarios != null && usuarios.isEmpty() == Boolean.FALSE) {
            tieneUsuarios = true;
        }
        return tieneUsuarios;
    }
}
