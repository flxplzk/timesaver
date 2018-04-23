package de.flxplzk.frontend.backend.processor;

public interface Processor<I, R> {

    R process(I input);
}
