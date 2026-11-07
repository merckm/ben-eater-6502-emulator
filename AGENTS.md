# Agent instructions

- Implement only the assigned issue. Confirm prerequisites are merged and consult the backlog, architecture, hardware reference, and matching skill before editing.
- Keep the headless core and assembly tools independent of JavaFX. Simulated time and observable bus cycles govern hardware behavior. UI actions enter through machine commands or simulated pins and serial endpoints.
- Use unsigned values or explicit masks at 8-bit and 16-bit boundaries. Keep hardware observations traceable to a chip revision, fixture, or cited specification. Record any intentional deviations.
- Add deterministic unit tests per feature and boundary tests for integrations. Run the local checks established by the foundation work and record commands and outcomes. The planned baseline is Gradle Wrapper `check` on the selected JDK.
- Work on one issue branch and PR. Use the Copilot-managed branch when GitHub creates it. Keep the PR in draft during implementation. Target the default branch and include `Closes #N` only for a complete issue.
- Request independent review. Address findings on the same branch, rerun checks and re-request review after changes. Keep the issue open until the owner merges manually. Agents never merge, directly close an issue, or edit repository rules.
