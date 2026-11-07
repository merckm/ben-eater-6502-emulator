# Contributing

1. Choose an issue whose prerequisites have merged. Read [agent instructions](AGENTS.md) and the issue's skill.
2. Assign Copilot coding agent to the issue. Use its branch and draft PR. If naming the branch manually, use `feature/<issue-number>-<slug>`.
3. Implement and commit on that branch, run the checks, update affected docs and send the PR for review. Use `Closes #N` for complete issues and a plain reference for partial work.
4. A maintainer enables Copilot-triggered CI runs where GitHub requires approval. Request a separate Copilot code review. The reviewer examines the current diff and checks, records actionable findings with severity and file/line, and leaves implementation changes to the implementer. An eligible human provides formal approval where required.
5. Send findings back with `@copilot` on the PR. Fix and push on the same branch. Re-run CI and re-request review on the new commit.
6. Apply `ready-to-merge` after passing checks and resolving blockers. Leave the issue open. The owner manually merges into the default branch and GitHub closes the linked issue.

Repository settings and permissions require the owner's approval.

## Local build

Install a JDK 25 distribution and make it available via JAVA_HOME or PATH. The first run downloads the pinned Gradle 9.1.0 distribution and build dependencies. JavaFX 25.0.2 is scoped to the desktop project and the tests use JUnit 6.0.3.

- Windows PowerShell: `.\gradlew.bat check`
- Linux and macOS: `./gradlew check`

The root check runs each project's checks. To run only the headless smoke test, use the same wrapper with `:emulator-core:test`. The desktop test checks JavaFX class resolution without opening a window. The Java-only projects are `opcode-metadata`, `emulator-core`, and `assembly-tools`. JavaFX belongs in `desktop-app`.

[Copilot review workflow](https://docs.github.com/en/copilot/how-tos/copilot-on-github/use-copilot-agents/review-copilot-output) · [GitHub issue closing](https://docs.github.com/en/issues/tracking-your-work-with-issues/using-issues/linking-a-pull-request-to-an-issue)
