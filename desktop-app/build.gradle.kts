plugins {
    java
    id("org.openjfx.javafxplugin") version "0.1.0"
}

dependencies {
    implementation(project(":emulator-core"))
    implementation(project(":assembly-tools"))
}

javafx {
    version = "25.0.2"
    modules("javafx.controls")
}
