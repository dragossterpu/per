package es.mira.progesin.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.converters.ListaExtensionesAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Entidad DocumentacionPrevia. Tipo de documentación relacionada con una solicitud de documentación previa en concreto.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
 * @see es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia
 * @see es.mira.progesin.web.beans.SolicitudDocPreviaBean
 * @see es.mira.progesin.web.beans.ProvisionalSolicitudBean
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "DOCUMENTACION_PREVIA")
// Tipo de documento relacionado con una solicitud de documentación previa en concreto al ser ésta creada. Al ser
// enviada la solicitud, el interlocutor de la unidad a inspeccionar debe subir la documentación, con un usuario
// provisional, ajustándose a dichos documentos tanto en nombre como en tipo.
public class DocumentacionPrevia {
	@Id
	@SequenceGenerator(name = "seq_documentacion_previa", sequenceName = "seq_documentacion_previa", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_documentacion_previa")
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "ID_SOLICITUD")
	private Long idSolicitud;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "NOMBRE", length = 255)
	private String nombre;

	@Column(name = "EXTENSIONES")
	@Convert(converter = ListaExtensionesAdapter.class)
	private List<String> extensiones;

}
