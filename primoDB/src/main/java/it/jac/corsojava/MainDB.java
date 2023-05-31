package it.jac.corsojava;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.jac.corsojava.service.UserService;

public class MainDB {

	private static Logger log = LogManager.getLogger(MainDB.class);

	public static void main(String[] args) {

		log.info("Applicazione iniziata");

		UserService.getInstance().create(
			"armiespo", "armando", "esposito", "123");

		log.info("Applicazione terminata");
	}
}
