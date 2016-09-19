package es.mira.progesin.web.controller;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import es.mira.progesin.services.Alerta;
import es.mira.progesin.services.AlertaService;

 
@ManagedBean
public class DataExporterView implements Serializable {
     
	 private List<Alerta> listaUsuarios = new ArrayList<Alerta>();
	 
	    public DataExporterView(){
	        System.out.println("Inicio");
	        initList();
	    }
	 
	    private void initList(){
	    	Alerta user1 = new Alerta(1, "Alerta 1", "111-1", "10");
	    	Alerta user2 = new Alerta(2, "Alerta 2", "222-1", "15");
	    	Alerta user3 = new Alerta(3, "Alerta 3", "333-1", "20");
	        listaUsuarios.add(user1);
	        listaUsuarios.add(user2);
	        listaUsuarios.add(user3);
	    }
	    public List<Alerta> getListaUsuarios() {
	    return listaUsuarios;
	    }
	 
	    public void setListaUsuarios(List<Alerta> listaUsuarios) {
	        this.listaUsuarios = listaUsuarios;
	    }
}