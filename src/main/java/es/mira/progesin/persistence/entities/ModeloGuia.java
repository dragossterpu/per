package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "modelosguias", schema = "public")
public class ModeloGuia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "descipcion", nullable = false)
	private String descripcion;

	@Column(name = "extension", nullable = false, length = 4)
	private String extension;

	// no vale con postgres
	// @Column(name = "fichero")	
	// @Lob
	// private Blob fichero;

	@Column(name = "nombre", nullable = false)
	private String nombreFichero;

	@Column(name = "fichero", nullable = false)
	private byte[] fichero;


}
