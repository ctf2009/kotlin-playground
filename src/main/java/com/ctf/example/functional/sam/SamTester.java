package com.ctf.example.functional.sam;

import java.util.function.Consumer;

public class SamTester {

    public interface DifferentConsumer<T> {
        void accept(final T t);
    }

    public static final String VALUE = "This is a Value";

    public void doSomething(final Consumer<String> consumer) {
        System.out.println("Doing Something with Consumer with Consumer Arg");
        consumer.accept(VALUE);
    }

    public void doSomethingAmbiguous(final Consumer<String> consumer) {
        System.out.println("Doing Something with Consumer with Consumer Arg");
        consumer.accept(VALUE);
    }

    public void doSomethingAmbiguous(final DifferentConsumer<String> consumer) {
        System.out.println("Doing Something with Consumer with ExtendedConsumer Arg");
        consumer.accept(VALUE);
    }

    public static void main(String[] args) {
        final Consumer<String> consumer = (s -> System.out.println(VALUE));
        final DifferentConsumer<String> different = (s -> System.out.println(VALUE));

        // Using the Java Version
        final var tester = new SamTester();
        tester.doSomething(consumer);
        tester.doSomethingAmbiguous(consumer);
        tester.doSomethingAmbiguous(different);
        tester.doSomething((s -> System.out.println(VALUE)));

        // Using the Kotlin Migrated Version is exactly the same as using the Java Version
        final var kotlinTester = new KotlinSamTester();
        kotlinTester.doSomething(consumer);
        kotlinTester.doSomethingAmbiguous(consumer);
        kotlinTester.doSomethingAmbiguous(different);
        kotlinTester.doSomething((s -> System.out.println(VALUE)));
    }
}
