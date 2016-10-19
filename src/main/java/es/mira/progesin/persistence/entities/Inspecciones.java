package es.mira.progesin.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
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
@Table(name = "INSPECCIONES", schema = "public")

public class Inspecciones {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", length = 5)
	private Integer id;

	@Column(name = "numero", length = 100, nullable = false)
	private String numero;

	@Column(name = "equipo", length = 100, nullable = false)
	private String equipo;

	@Column(name = "jefeEquipo", length = 100, nullable = false)
	private String jefeEquipo;

	@Column(name = "CUAT", length = 2)
	private String cuat;

	@CreatedDate
	@Column(name = "FECHA", nullable = false)
	private Date fecha;

	
}
