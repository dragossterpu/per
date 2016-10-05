package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;

/**
 * Implementación de los métodos definidos en la interfaz ICuerpoEstadoService
 * @author sperezp
 *
 */
@Service
public class CuerpoEstadoService implements ICuerpoEstadoService {

	@Autowired
	ICuerpoEstadoRepository cuerpoEstadoRepository;

	@Override
	public Iterable<CuerpoEstado> findAll() {
		return cuerpoEstadoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public CuerpoEstado save(CuerpoEstado cuerpo) {
		return cuerpoEstadoRepository.save(cuerpo);
	}

	@Override
	public List<CuerpoEstado> findByFechaBajaIsNull() {
		return cuerpoEstadoRepository.findByFechaBajaIsNull();
	}

}
