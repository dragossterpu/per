package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembros;
import es.mira.progesin.web.beans.EquipoBusqueda;

public interface IEquipoService {

	// boolean exists(Integer id);

	Iterable<Equipo> findAll();

	// Equipo findOne(Integer id);

	Iterable<Equipo> save(Iterable<Equipo> entities);

	Miembros save(Miembros miembro);

	List<Miembros> findByEquipo(Equipo equipo);

	Equipo save(Equipo entity);

	List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda);

	void delete(Miembros miembro);

}
