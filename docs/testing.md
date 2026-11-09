# Test strategy

Each feature PR supplies targeted unit tests. Test initial state, normal operation, boundary values, adverse input and side effects appropriate to the change. Test through interfaces at integration boundaries, with deterministic simulated time and no host sleeps for hardware correctness.

- CPU: independent W65C02 register/memory vectors and address/direction/data traces. Include a trace that fails on wrong accesses despite correct final register state. Cover decimal arithmetic, stack, wraparound, crossing pages, RMW, IRQ/NMI/BRK, WAI, STP and reset.
- Bus and timing: check all address boundaries and aliases, invalid profiles, ROM protection, event ties, clock changes, reset, and side-effect-free snapshots.
- VIA, LCD and ACIA: register and pin traces, interrupt assertion/acknowledgment, deadlines around state transitions, both LCD modes, custom glyphs, serial framing and overrun.
- Tools and UI: independent encodings, line/column diagnostics, ROM bounds and vectors, failed-burn rollback, view formatting, glyph/theme baselines, and responsive desktop updates.
- End to end: LED and button, IRQ, LCD text and startup in both modes, CGRAM, bidirectional WozMon, and assemble/burn/reset under both maps. Pin source revisions, document licenses and attribution, and prefer redistributable deterministic fixtures.
- CI: formatting, static checks and headless tests on PRs, desktop and packaging smoke jobs on supported hosts. Start with a 90% line-coverage target for the core after measuring its baseline. Report branch coverage and hardware-behavior coverage separately.

Record achieved clock rate on a named host with the representative workload, warmup, sample interval and desktop refresh settings. Treat 1 MHz as the baseline and 10 MHz as a stretch goal.

## Pull request checks

Run `./gradlew --no-daemon --console=plain check` with JDK 25 on Linux or macOS, or replace `./gradlew` with `.\gradlew.bat` on Windows. This runs Checkstyle on Java production and test sources, JUnit tests without a display, and the emulator core JaCoCo report. To regenerate only the core report and its tests, run `:emulator-core:jacocoTestReport` with the wrapper.

GitHub Actions runs the same command on Ubuntu and Windows. The proposed required check names are **Gradle check (ubuntu-latest)** and **Gradle check (windows-latest)**. The owner can enable them in the ruleset after a successful run establishes those names.

Each run uploads JUnit XML, HTML test reports, Checkstyle reports, and core JaCoCo HTML and XML when available. Locally the coverage report is in `emulator-core/build/reports/jacoco/test/`. The foundation has no production classes in the core, so the initial report has no measurable line or branch denominator. Coverage is informational until a real baseline exists.
