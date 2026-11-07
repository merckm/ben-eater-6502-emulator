# ADR 0001 — Desktop stack and distribution

**Accepted direction.** Java/JavaFX and a standalone headless Java core. Proposed baseline is Java 25 LTS, compatible JavaFX, Gradle Wrapper with Kotlin DSL, JUnit, GitHub Actions, and jpackage. Foundation work validates and pins versions.

| Option | Trade-offs |
|---|---|
| Java/JavaFX | Rich desktop controls for editor and debugger, good unit test isolation, Canvas for LCD, bundled runtime per platform. Benchmark cycle batching and allocation pressure. |
| C++20/SDL3 | Low-level rendering and performance control. Editor, accessible widgets and inspectors require extra UI work. Native toolchains multiply build and distribution combinations. |
| C++20/Qt | Rich controls and strong native tooling, with platform-specific compilation and additional licensing and deployment work. |
| Rust with egui or Slint | Good core safety and rendering control. Evaluate editor ergonomics, accessibility, and per-target distribution maturity. |

Target Windows x64, macOS arm64, and Linux x64 for initial native packaging. Track macOS x64 as conditional on runnable CI and smoke coverage. Package and launch on each named target before claiming support.
