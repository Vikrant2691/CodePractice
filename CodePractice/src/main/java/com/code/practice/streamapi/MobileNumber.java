package com.code.practice.streamapi;

public class MobileNumber {

    private String number;

    public String getNumber() {
        return number;
    }

    private MobileNumber() {
    }

    private MobileNumber(String number) {
        this.number = number;
    }

    public static MobileNumberBuilder builder() {
        return new MobileNumberBuilder();
    }

    static class MobileNumberBuilder {
        private String number;

        public MobileNumberBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public MobileNumber build() {
            return new MobileNumber(number);
        }

    }

    @Override
    public String toString() {
        return "MobileNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
