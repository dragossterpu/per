package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Alerta;


public interface IAlertaService {
    void delete(String id);
    void deleteAll();
    boolean exists(String id);
    Iterable<Alerta> findAll();
  
    Alerta findOne(String id);
    Iterable<Alerta> save(Iterable<Alerta> entities);
    Alerta save(Alerta entity);

}
