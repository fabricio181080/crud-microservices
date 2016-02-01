package com.matera.crudmicroservices.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.matera.crudmicroservices.core.domain.Person;

/**
 * Unit tests for {@link Person} domain class.
 * 
 * @author geiser
 *
 */
public class PersonTest {

    private static final long ID = 0L;
    private static final String PHONE = "0000000000";
    private static final String JOE = "Joe Doe";

    @Test
    public void testBuildNewObject() {

        Person person = Person.builder().withId(ID).withName(JOE).withPhoneNumber(PHONE).build();

        assertEquals(ID, person.getId().longValue());
        assertEquals(JOE, person.getName());
        assertEquals(PHONE, person.getPhoneNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildNewObjectWithError() {

        Person.builder().withId(ID).build();
    }

    @Test
    public void testEqualsSameId() {

        Person person1 = Person.builder().withId(ID).withName(JOE).withPhoneNumber(PHONE).build();
        Person person2 = Person.builder().withId(ID).withName("Fulano").withPhoneNumber("1111111111").build();

        assertEquals(person1, person2);
    }

    @Test
    public void testNotEqualsSameName() {

        Person person1 = Person.builder().withId(ID).withName(JOE).withPhoneNumber(PHONE).build();
        Person person2 = Person.builder().withId(1L).withName(JOE).withPhoneNumber(PHONE).build();

        assertNotEquals(person1, person2);
    }

}
