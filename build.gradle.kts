plugins {
    base
}

subprojects {
    repositories {
        mavenCentral()
    }

    plugins.withType<JavaPlugin> {
        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(25))
        }

        dependencies {
            "testImplementation"(platform("org.junit:junit-bom:6.0.3"))
            "testImplementation"("org.junit.jupiter:junit-jupiter")
            "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}

tasks.named("check") {
    dependsOn(subprojects.map { "${it.path}:check" })
}
