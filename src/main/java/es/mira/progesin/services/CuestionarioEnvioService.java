package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;

@Service
public class CuestionarioEnvioService implements ICuestionarioEnvioService {

	private static final long serialVersionUID = 1L;

	@Autowired
	ICuestionarioEnvioRepository cuestionarioEnvioRepository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IRegistroActividadService regActividadService;

	@Override
	public CuestionarioEnvio findByInspeccion(Inspeccion inspeccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void enviarCuestionarioService(User user, CuestionarioEnvio cuestionarioEnvio) {
		userRepository.save(user);
		cuestionarioEnvioRepository.save(cuestionarioEnvio);
		regActividadService.crearRegistroActividad(
				"Cuestionario para la inspección " + cuestionarioEnvio.getInspeccion().getNumero() + " enviado",
				EstadoRegActividadEnum.ALTA.name(), "CUESTIONARIOS");
	}

}
