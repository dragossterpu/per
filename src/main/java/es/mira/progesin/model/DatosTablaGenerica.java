package es.mira.progesin.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosTablaGenerica implements Serializable {

	private String meses;

	private String motivos;

	private String numSalidas;

	private String periodoPermanencia;

	private String sexo;
}
