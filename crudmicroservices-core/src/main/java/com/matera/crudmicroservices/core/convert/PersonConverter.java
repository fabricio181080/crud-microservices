package com.matera.crudmicroservices.core.convert;

import com.matera.crudmicroservices.core.domain.Person;

/**
 * Converter for {@link Person}, specially between domain and entity objects.
 * 
 * @author geiser
 */
public class PersonConverter {

    private PersonConverter() {

        // Avoid instantiation
    }

    /**
     * Converts a {@link com.matera.crudmicroservices.core.entities.Person}
     * entity object to a {@link Person} domain.
     * 
     * @param entity the entity object
     * @return domain object
     */
    public static Person toDomain(com.matera.crudmicroservices.core.entities.Person entity) {

        if (entity == null) {
            return null;
        }

        return Person.builder().withName(entity.getName()).withPhoneNumber(entity.getPhoneNumber())
            .withId(entity.getId()).build();
    }

    /**
     * Converts a {@link Person} domain object to a
     * {@link com.matera.crudmicroservices.core.entities.Person} entity.
     * 
     * @param domain the domain object
     * @return entity object
     */
    public static com.matera.crudmicroservices.core.entities.Person toEntity(Person domain) {

        if (domain == null) {
            return null;
        }

        com.matera.crudmicroservices.core.entities.Person entity =
            new com.matera.crudmicroservices.core.entities.Person();
        entity.setName(domain.getName());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setId(domain.getId());
        return entity;
    }
}
