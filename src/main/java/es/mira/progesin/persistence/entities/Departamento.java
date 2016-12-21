package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
@Table(name="DEPARTAMENTO")
@NamedQuery(name = "Departamento.findAll", query = "SELECT t FROM Departamento t")
public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTAMENTO")
    @SequenceGenerator(name="SEQ_DEPARTAMENTO", sequenceName="SEQ_DEPARTAMENTO", allocationSize=1) 
	@Column(name = "SEQ_DEPARTAMENTO", length = 5)
	private Long id;
	
	@Column(name="descripcion", length=100)
	private String descripcion;

}
