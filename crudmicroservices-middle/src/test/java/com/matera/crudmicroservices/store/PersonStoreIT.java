package com.matera.crudmicroservices.store;

import org.junit.Assert;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.google.inject.util.Providers;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.store.impl.PersonStoreImpl;

public class PersonStoreIT {

	private final PersonStore store = 
			new PersonStoreImpl(Providers.of(Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices")));
	
	@Test
	public void insert() {
		
		Assert.assertTrue(store.findAll().isEmpty().toBlocking().single());
		
		Person person = 
				Person.builder()
					.withId(1L)
					.withName("Kevin Vriezen")
					.withPhoneNumber("09324670644")
					.build();
		
		store.save(person);

		Person queried = store.findAll().toBlocking().single();
		Assert.assertEquals(Long.valueOf(1), queried.getId());
		Assert.assertEquals("Kevin Vriezen", queried.getName());
		Assert.assertEquals("09324670644", queried.getPhoneNumber());
	}
	
	@Test
	public void delete() {
		
		Assert.assertTrue(store.findAll().isEmpty().toBlocking().single());
		
		store.save(
				Person.builder()
				.withId(1L)
				.withName("Kevin Vriezen")
				.withPhoneNumber("09324670644")
				.build()
		);
		
		store.save(
				Person.builder()
				.withId(2L)
				.withName("Martin Schweitzer")
				.withPhoneNumber("09324670644")
				.build()
		);
		
		Assert.assertEquals(Integer.valueOf(2), store.findAll().count().toBlocking().single());
		
		store.delete(
			Person.builder()
				.withId(2L)
				.withName("Martin Schweitzer")
				.withPhoneNumber("09324670644")
				.build()
		);
		
		Person queried = store.findAll().toBlocking().single();
		Assert.assertEquals(Long.valueOf(1), queried.getId());
		Assert.assertEquals("Kevin Vriezen", queried.getName());
		Assert.assertEquals("09324670644", queried.getPhoneNumber());
	}
	
	@Test
	public void update() {
		
	}
	
	@Test
	public void queryByID() {
		
		store.save(
				Person.builder()
				.withId(1L)
				.withName("Kevin Vriezen")
				.withPhoneNumber("09324670644")
				.build()
		);
		
		store.save(
				Person.builder()
				.withId(2L)
				.withName("Martin Schweitzer")
				.withPhoneNumber("09324670644")
				.build()
		);

		Person queried = store.findById(2L).toBlocking().single();
		Assert.assertEquals(Long.valueOf(1), queried.getId());
		Assert.assertEquals("Martin Schweitzer", queried.getName());
		Assert.assertEquals("09324670644", queried.getPhoneNumber());
	}
	
	@Test
	public void queryByName() {
		
		store.save(
				Person.builder()
				.withId(1L)
				.withName("Swen Herzog")
				.withPhoneNumber("03322905383")
				.build()
		);
		
		store.save(
				Person.builder()
				.withId(2L)
				.withName("Swen Herzog")
				.withPhoneNumber("09324670644")
				.build()
		);
	
		Assert.assertEquals(2, store.findByName("Swen Herzog").toList().toBlocking().single().size());
	}
	
}
