package it.jac.corsojava.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.jac.corsojava.dao.UserDao;
import it.jac.corsojava.entity.User;
import it.jac.corsojava.exception.EntityNotFoundException;

public class UserService {

	private static Logger log = LogManager.getLogger(UserService.class);

	private static UserService instance = new UserService();

	private UserDao dao = UserDao.getInstance();

	private UserService() {

	}

	public static UserService getInstance() {

		return instance;
	}

	public User findById(long idUtente) {

		log.debug("Ricerca User [id={}]", idUtente);

		User result = this.dao.findById(idUtente);

		log.debug("Restituisco {}", result);

		return result;
	}

	public User create(String username, String nome, String cognome, String password) {

		log.debug("Creazione nuovo User");
		log.trace("username [{}], nome [{}], cognome [{}], password [{}]",
			username, nome, cognome, password);

//		verifico che i campi obbligatori siano stati valorizzati
//		mi limito a lanciare eccezione se questi non sono validi
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		User entity = new User();
		entity.setUsername(username);
		entity.setNome(nome);
		entity.setCognome(cognome);
		entity.setPassword(password);
		entity.setUtenteIns("admin");
		entity.setDataIns(ZonedDateTime.now());

		this.dao.create(entity);

		log.info("Nuovo User [id={}]", entity.getIdUtente());

		return entity;
	}

	public User update(long idUtente, String nome, String cognome) {

		log.debug("Modifica User");
		log.trace("id [{}], nome [{}], cognome [{}",
			idUtente, nome, cognome);

		User entity = this.dao.findById(idUtente);
		if (entity == null) {
			throw new EntityNotFoundException("Unable to find utente [" + idUtente + "]");
		}

		entity.setNome(nome);
		entity.setCognome(cognome);
		entity.setUtenteMod("admin");
		entity.setDataMod(ZonedDateTime.now());

		this.dao.update(idUtente, entity);

		log.info("User modificato [id={}]", idUtente);

		return entity;
	}

	public void delete(long idUtente) {

		log.debug("Cancellazione User");
		log.trace("id [{}]", idUtente);

		User entity = this.dao.findById(idUtente);
		if (entity == null) {
			throw new EntityNotFoundException("Unable to find utente [" + idUtente + "]");
		}

		this.dao.delete(idUtente);

		log.info("User eliminato [id={}]", idUtente);
	}

	public List<User> findAll() {

		List<User> result = new ArrayList<>();

		log.debug("Estrazione lista completa User");

		result.addAll(this.dao.findAll());

		log.debug("Estratti {} elementi", result.size());

		return result;
	}
}
