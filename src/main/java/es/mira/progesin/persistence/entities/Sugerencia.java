package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name="SUGERENCIA", schema="public")


public class Sugerencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_SUGERENCIA", length = 5)
	private Integer idSugerencia;

	@Column(name = "MODULO", length = 50)
	private String modulo;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;

	@Column(name = "FECHA_REGISTRO")
	private Date fechaAlta;

	@Column(name = "USUARIO_REGISTRO", length = 50)
	private String usuario;

	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;
	
	@Column(name = "USUARIO_BAJA", length = 50)
	private String usuarioBaja;
}
