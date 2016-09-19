package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Alerta;


public interface IAlertaService {
    void delete(Integer id);
    void deleteAll();
    boolean exists(Integer id);
    Iterable<Alerta> findAll();
    Alerta findOne(Integer id);
    Iterable<Alerta> save(Iterable<Alerta> entities);
    Alerta save(Alerta entity);
}
