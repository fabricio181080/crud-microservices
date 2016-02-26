package com.matera.crudmicroservices.store;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.util.Providers;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.store.impl.PersonStoreImpl;

public class PersonStoreIT {

	private final Session session = Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices");
	
	private final PersonStore store = 
			new PersonStoreImpl(Providers.of(session));
	
	@BeforeClass
	public static void setup() {
		System.setProperty("crudmicroservicesmiddle.cassandra.keyspace", "crudmicroservices");
		System.setProperty("crudmicroservicesmiddle.cassandra.cf.person", "person");
		System.setProperty("crudmicroservicesmiddle.cassandra.cf.personbyname", "person_by_name");
	}
	
	@Before
	public void cleanup() {
		session.execute("TRUNCATE crudmicroservices.person;");
		session.execute("TRUNCATE crudmicroservices.person_by_name;");
	}
	
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
		
		store.update(Person.builder()
				.withId(2L)
				.withName("Meint Antonius")
				.withPhoneNumber("09324670644")
				.build());
		
		Assert.assertEquals(Integer.valueOf(2), store.findAll().count().toBlocking().single());
		
		Person queried = store.findById(2).toBlocking().single();
		Assert.assertEquals(Long.valueOf(2), queried.getId());
		Assert.assertEquals("Meint Antonius", queried.getName());
		Assert.assertEquals("09324670644", queried.getPhoneNumber());
	}
	
	@Test
	public void findByID() {
		
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
		Assert.assertEquals(Long.valueOf(2), queried.getId());
		Assert.assertEquals("Martin Schweitzer", queried.getName());
		Assert.assertEquals("09324670644", queried.getPhoneNumber());
	}
	
	@Test
	public void findByName() {
		
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
