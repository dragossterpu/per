package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.web.beans.EquipoBusqueda;

public interface IEquipoService {

	// boolean exists(Integer id);

	Iterable<Equipo> findAll();

	// Equipo findOne(Integer id);

	// Iterable<Equipo> save(Iterable<Equipo> entities);

	Miembro save(Miembro miembro);

	List<Miembro> save(List<Miembro> listaMiembros);

	List<Miembro> findByEquipo(Equipo equipo);

	Equipo save(Equipo entity);

	List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda);

	void delete(Miembro miembro);

	boolean existsByTipoEquipo(TipoEquipo tipo);

}
