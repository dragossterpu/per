package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.services.IPuestoTrabajoService;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApplicationScoped
@Component
@Getter
@NoArgsConstructor
public class ApplicationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	IPuestoTrabajoService puestosTrabajoService;

	// Los cargo en la aplicación porque van a ser siempre los mismo y así agilizo la aplicación
	private List<PuestoTrabajo> listaPuestosTrabajo;

	@PostConstruct
	public void init() {
		this.listaPuestosTrabajo = (List<PuestoTrabajo>) puestosTrabajoService.findAll();
	}
}
