package de.flxplzk.frontend.backend.processor;

import java.io.IOException;

public interface Processor<I, R> {

    R process(I input);
}
