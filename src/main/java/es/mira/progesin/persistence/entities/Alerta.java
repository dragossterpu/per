package es.mira.progesin.persistence.entities;
import java.io.Serializable;
import java.sql.Date;

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
@Table(name="ALERTAS", schema="public")
public class Alerta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_ALERTA", length = 15)
	private int idAlerta;

	@Column(name = "TITULO", length = 50)
	private String titulo;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;

	@Column(name = "FECHA_REGISTRO")
	private Date fechaAlta;

	@Column(name = "USUARIO_REGISTRO", length = 50)
	private String usuario;

	@Column(name = "ACTIV")
	private Boolean activ;

	
}
