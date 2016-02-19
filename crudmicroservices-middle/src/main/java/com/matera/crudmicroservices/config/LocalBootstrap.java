package com.matera.crudmicroservices.config;

import com.google.inject.Binder;
import com.netflix.karyon.server.ServerBootstrap;

public class LocalBootstrap extends ServerBootstrap {

	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new CrudMicroservicesLocalModule());
	}

}