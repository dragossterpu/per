package es.mira.progesin.persistence.entities;

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
public class ParametroId implements Serializable {
	private static final long serialVersionUID = 1L;

	String seccion;
	
	String clave;

	String valor;
}

