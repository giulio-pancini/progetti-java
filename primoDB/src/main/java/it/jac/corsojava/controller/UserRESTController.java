package it.jac.corsojava.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.jac.corsojava.entity.User;
import it.jac.corsojava.exception.EntityNotFoundException;
import it.jac.corsojava.service.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users")
public class UserRESTController {

	private static Logger log = LogManager.getLogger(UserRESTController.class);

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(User user) {

		log.info("Creo un nuovo utente");

//		controllo che lo user abbia i campi valorizzati in modo corretto
		String username = user.getUsername();
		if (username == null || username.trim().length() == 0) {

			return Response.status(Status.BAD_REQUEST)
					.entity("Username non valorizzato")
					.header("Content-Type", "text/plain")
					.build();
		}

		String password = user.getPassword();
		if (password == null || password.trim().length() == 0) {

			return Response.status(Status.BAD_REQUEST)
					.entity("Password non valorizzata")
					.header("Content-Type", "text/plain")
					.build();
		}

		User result = UserService.getInstance().create(
			username, user.getNome(), user.getCognome(), password);

		log.info("Utente creato con successo");

		return Response.ok(result).build();
	}


	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAll() {

		log.info("Ricerco l'elenco degli utenti");

		List<User> list = UserService.getInstance().findAll();

		return list;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findById(@PathParam("id") long id) {

		log.info("Ricerco utente [id={}]", id);

		User user = UserService.getInstance().findById(id);
		if (user == null) {

			throw new EntityNotFoundException("User not found");
		}
		return user;
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, User userData) {

		log.info("Modifico utente [id={}]", id);

		UserService.getInstance().update(id, userData.getNome(), userData.getCognome());

		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id) {

		log.info("Elimino utente [id={}]", id);

		UserService.getInstance().delete(id);

		return Response.ok().build();
	}

}
