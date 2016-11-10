package es.mira.progesin.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DatosTablaGenerica implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombreFila; // para cuando haya que dibujar matrices

	private String campo1;

	private String campo2;

	private String campo3;

	private String campo4;

	private String campo5;

	private String campo6;

	private String campo7;

	private String campo8;

	private String campo9;

	private String campo10;

}
