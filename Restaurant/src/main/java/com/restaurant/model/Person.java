package com.restaurant.model;

import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private String email;

    public Person(String name, int age) {
        this(name, age, null);
    }

    public Person(String name, int age, String email) {
        // normalize inputs and validate
        this.name = (name == null) ? "" : name.trim();
        if (age < 0)
            throw new IllegalArgumentException("Age must be non-negative");
        this.age = age;
        this.email = (email == null || email.isBlank()) ? null : email.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name == null) ? "" : name.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0)
            throw new IllegalArgumentException("Age must be non-negative");
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = (email == null || email.isBlank()) ? null : email.trim();
    }

    public void displayInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
        if (this.email != null && !this.email.isBlank()) {
            System.out.println("Email: " + this.email);
        }
    }

    public String getInfo(String prefix) {
        String p = (prefix == null) ? "" : prefix;
        return p + ": " + this.name;
    }

    public String getInfo(String prefix, boolean includeAge) {
        var result = this.getInfo(prefix);
        if (includeAge) {
            result += " (Age: " + this.age + ")";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}