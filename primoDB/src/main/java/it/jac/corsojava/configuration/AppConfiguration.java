package it.jac.corsojava.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class AppConfiguration extends ResourceConfig {

	public AppConfiguration() {

		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}
}
