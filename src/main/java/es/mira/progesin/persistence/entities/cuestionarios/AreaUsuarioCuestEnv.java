package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que relaciona las areas de un cuestionario enviado con un usuario provisional que debe responderlas
 * 
 * @author EZENTIS
 * @see es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "AreasUsuarioCuestenv")
@IdClass(AreaUsuarioCuestEnvId.class)
public class AreaUsuarioCuestEnv implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column
    private Long idCuestionarioEnviado;
    
    @Id
    @Column
    private Long idArea;
    
    @Column
    private String usernameProv;
    
}