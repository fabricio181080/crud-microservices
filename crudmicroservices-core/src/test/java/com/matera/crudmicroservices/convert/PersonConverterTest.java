package com.matera.crudmicroservices.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.matera.crudmicroservices.core.convert.PersonConverter;
import com.matera.crudmicroservices.core.domain.Person;

/**
 * Unit tests for {@link PersonConverter} static methods.
 * 
 * @author geiser
 *
 */
public class PersonConverterTest {

    private static final Long ID = 0L;
    private static final String PHONE = "0000000000";
    private static final String JOE = "Joe Doe";

    @Test
    public void testConvertEntityToDomain() {

        final Person domain = Person.builder().withId(ID).withName(JOE).withPhoneNumber(PHONE).build();
        final com.matera.crudmicroservices.core.entities.Person entity = PersonConverter.toEntity(domain);

        assertEquals("Converted domain id is not equals at entity object", domain.getId(), entity.getId());
        assertEquals("Converted domain firstName is not equals at entity object", domain.getName(), entity.getName());
        assertEquals("Converted domain lastName is not equals at entity object", domain.getPhoneNumber(),
            entity.getPhoneNumber());
    }

    @Test
    public void testConvertEntityToDomainWithNullObject() {

        final com.matera.crudmicroservices.core.entities.Person entity = PersonConverter.toEntity(null);

        assertNull("Converted object is not null when domain argument is", entity);
    }

    @Test
    public void testConvertEntityToDomainWithNullId() {

        final Person domain = Person.builder().withName(JOE).withPhoneNumber(PHONE).build();
        final com.matera.crudmicroservices.core.entities.Person entity = PersonConverter.toEntity(domain);

        assertNull("Converted domain id is not equals at entity object", entity.getId());
    }

    @Test
    public void testConvertDomainToEntity() {

        final com.matera.crudmicroservices.core.entities.Person entity = createEntity(ID, JOE, PHONE);
        final Person domain = PersonConverter.toDomain(entity);

        assertEquals("Converted entity id is not equals at domain object", entity.getId(), domain.getId());
        assertEquals("Converted entity firstName is not equals at domain object", entity.getName(), domain.getName());
        assertEquals("Converted entity lastName is not equals at domain object", entity.getPhoneNumber(),
            domain.getPhoneNumber());
    }

    @Test
    public void testConvertDomainToEntityWithNullObject() {

        final Person domain = PersonConverter.toDomain(null);

        assertNull("Converted object is not null when entity argument is", domain);
    }

    @Test
    public void testConvertDomainToEntityWithNullId() {

        final com.matera.crudmicroservices.core.entities.Person entity = createEntity(null, JOE, PHONE);
        final Person domain = PersonConverter.toDomain(entity);

        assertNull("Converted entity id is not equals at domain object", domain.getId());
    }

    private com.matera.crudmicroservices.core.entities.Person createEntity(Long id, String name, String phoneNumber) {

        com.matera.crudmicroservices.core.entities.Person entity =
            new com.matera.crudmicroservices.core.entities.Person();

        entity.setName(name);
        entity.setPhoneNumber(phoneNumber);
        entity.setId(id);

        return entity;
    }
}
