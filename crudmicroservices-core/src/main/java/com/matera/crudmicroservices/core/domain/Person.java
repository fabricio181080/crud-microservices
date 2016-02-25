package com.matera.crudmicroservices.core.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Person domain class.
 * 
 * @author geiser
 *
 */
public class Person implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1594987066010527072L;
	
	private final Long id;
    private final String name;
    private final String phoneNumber;

    private Person(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the id
     */
    public Long getId() {

        return id;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {

        return phoneNumber;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return new HashCodeBuilder().append(id).build();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        return new EqualsBuilder().append(id, other.id).build();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "Person [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
    }

    /**
     * Creates a new {@link Person} {@link Builder}.
     * 
     * @return the builder
     */
    public static Builder builder() {

        return new Builder();
    }

    /**
     * Builder class for {@link Person}.
     * 
     * @author geiser
     *
     */
    public static class Builder {

        private Long id;
        private String name;
        private String phoneNumber;

        /**
         * Sets the id.
         * 
         * @param id the id
         * @return current {@link Builder}
         */
        public Builder withId(Long id) {

            this.id = id;
            return this;
        }

        /**
         * Sets the name.
         * 
         * @param name the name
         * @return current {@link Builder}
         */
        public Builder withName(String name) {

            this.name = name;
            return this;
        }

        /**
         * Sets the phone number.
         * 
         * @param phoneNumber the phone number
         * @return current {@link Builder}
         */
        public Builder withPhoneNumber(String phoneNumber) {

            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * Builds this object by creating a new {@link Person} object.
         * 
         * @return a {@link Person} object
         */
        public Person build() {

            Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "Name is mandatory");

            return new Person(id, name, phoneNumber);
        }

    }

}
