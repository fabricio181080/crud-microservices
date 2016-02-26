package com.matera.crudmicroservices.config;

import com.google.inject.AbstractModule;
import com.matera.crudmicroservices.edge.service.PersonService;
import com.matera.crudmicroservices.edge.service.stub.PersonServiceStub;

/**
 * @author Igor K. Shiohara
 */
public class CrudMicroservicesLocalModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersonService.class).to(PersonServiceStub.class);
	}

}
