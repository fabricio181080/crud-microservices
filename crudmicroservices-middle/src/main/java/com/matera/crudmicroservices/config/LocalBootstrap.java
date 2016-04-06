package com.matera.crudmicroservices.config;

import com.google.inject.Binder;
import com.matera.crudmicroservices.core.config.CrudMicroservicesJacksonModule;
import com.netflix.karyon.server.ServerBootstrap;

public class LocalBootstrap extends ServerBootstrap {

	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new RESTModule());
		binder.install(new CrudMicroservicesJacksonModule());
		binder.install(new CrudMicroservicesLocalModule());
	}

}
			 