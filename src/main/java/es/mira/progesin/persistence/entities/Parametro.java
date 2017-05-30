package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para los parámetros.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "parametros")
public class Parametro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Parámetro.
     */
    @EmbeddedId
    ParametroId param;
}
