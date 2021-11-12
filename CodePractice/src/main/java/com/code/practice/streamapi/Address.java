package com.code.practice.streamapi;

public class Address {

    private String street;
    private String city;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    private Address() {
    }

    private Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    static class AddressBuilder {
        private String street;
        private String city;

        public AddressBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public Address build() {
            return new Address(street, city);
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
