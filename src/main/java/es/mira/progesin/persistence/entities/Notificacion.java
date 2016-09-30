package es.mira.progesin.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.Alerta.AlertaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "NOTIFICACIONES", schema = "public")
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_NOTIFICACION", length = 15)
	private Integer idNotificacion;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;

	@Column(name = "FECHA_NOTIFICACION")
	private Date fechaAlta;

	@Column(name = "USUARIO_REGISTRO", length = 50)
	private String usernameAlerta;

	@Column(name = "TIPO_NOTIFICACION", length = 20)
	private String tipoNotificacion;
}
