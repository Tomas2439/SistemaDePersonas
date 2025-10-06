package com.utn.SistemaDePersonas.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public interface Stringifiable {
    void showOn(PrintStream oStream);

    default String toStringRepresentation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(outputStream)) {
            this.showOn(printStream);
            return outputStream.toString();
        }
    }
}
