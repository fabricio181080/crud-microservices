package com.matera.crudmicroservices.config;

import com.google.inject.AbstractModule;
import com.matera.crudmicroservices.edge.service.PersonService;
import com.matera.crudmicroservices.edge.service.PersonServiceImpl;
import com.netflix.governator.guice.lazy.LazySingletonScope;

/**
 * @author Igor K. Shiohara
 */
public class CrudMicroservicesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersonService.class).to(PersonServiceImpl.class).in(LazySingletonScope.get());
	}
	
}
