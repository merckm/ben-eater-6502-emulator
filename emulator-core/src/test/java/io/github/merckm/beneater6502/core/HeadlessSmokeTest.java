package io.github.merckm.beneater6502.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HeadlessSmokeTest {
    @Test
    void runsWithoutJavaFxOnTheTestRuntimeClasspath() {
        ;
        assertThrows(ClassNotFoundException.class, () -> Class.forName(
                "javafx.application.Application", false, getClass().getClassLoader()));
    }
}
