package es.mira.progesin.persistence.entities;

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
@Table(name = "DOCUMENTACION_PREVIA", schema = "public")

public class DocumentacionPrevia {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", length = 5)
	private Integer id;

	@Column(name = "ID_SOLICITUD", length = 5)
	private Integer idSolicitud;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "NOMBRE", length = 255)
	private String nombre;

	@Column(name = "EXTENSION", length = 25)
	private String extension;

}
