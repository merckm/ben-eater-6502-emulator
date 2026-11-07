package io.github.merckm.beneater6502.desktop;

import javafx.scene.Node;
import javafx.scene.control.Control;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaFxDependencySmokeTest {
    @Test
    void resolvesControlsAndGraphicsWithoutStartingTheToolkit() {
        assertTrue(Node.class.isAssignableFrom(Control.class));
    }
}
