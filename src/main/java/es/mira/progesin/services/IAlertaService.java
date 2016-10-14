package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Alerta;

public interface IAlertaService {
	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	Alerta findOne(Integer id);

	List<Alerta> findByFechaBajaIsNull();

	Alerta save(Alerta entity);
}
