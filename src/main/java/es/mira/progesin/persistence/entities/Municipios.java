package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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
@Table(name = "municipios")
@NamedQuery(name = "Municipios.findByCode_province", query = "SELECT m FROM Municipios m where m.provincia=:provinciaSeleccionada")
public class Municipios implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "code_province", foreignKey = @ForeignKey(name = "FK_PROVINCIA"))
    private Provincias provincia;
    
}
