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

	List<Miembros> findByIdMiembros(Integer idMiembros);

	Equipo save(Equipo entity);

	// Equipo findByEquipoEspecial(String equipoEspecial);

	List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda);
}
