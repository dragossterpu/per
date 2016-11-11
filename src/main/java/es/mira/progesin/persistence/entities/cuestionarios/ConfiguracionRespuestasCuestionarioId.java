package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ConfiguracionRespuestasCuestionarioId implements Serializable {
	private static final long serialVersionUID = 1L;

	String clave;

	String valor;
}
