package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.services.IAlertaService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("alertasBean")
@Scope(FacesViewScope.NAME)
public class AlertasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	IAlertaService alertasService;

	private List<Alerta> listaAlertas = new ArrayList<Alerta>();

	@PostConstruct
	public void init() {
		listaAlertas = (List<Alerta>) alertasService.findAll();
	}

}
