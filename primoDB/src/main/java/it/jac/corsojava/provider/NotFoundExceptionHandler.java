package it.jac.corsojava.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.jac.corsojava.exception.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<EntityNotFoundException> {

	private static Logger log = LogManager.getLogger(NotFoundExceptionHandler.class);

	@Override
	public Response toResponse(EntityNotFoundException exception) {

		log.error("Entity not found", exception);
		return Response.status(Response.Status.NOT_FOUND)
				.entity("Not Found")
				.header("Content-Type", "text/plain")
				.build();
	}


}
