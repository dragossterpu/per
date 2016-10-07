package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.services.ITipoEquipoService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("tipoEquipoBean")
@RequestScoped
public class TipoEquipoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	static Logger LOG = Logger.getLogger(TipoEquipoBean.class);

	private List<TipoEquipo> listaTipoEquipo;

	@Autowired
	ITipoEquipoService tipoEquipoService;

	/**
	 * Método que nos lleva al listado de los tipos de equipos. Se llama desde el menu lateral
	 * @return
	 */
	public String tipoEquipoListado() {
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
		return "/equipos/listadoEquipos";
	}

	public void eliminarTipoEquipo(Integer idTipoEquipo) {
		tipoEquipoService.delete(idTipoEquipo);
		this.listaTipoEquipo = null;
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
	}

	@PostConstruct
	public void init() throws MessagingException {

		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();

	}

}
