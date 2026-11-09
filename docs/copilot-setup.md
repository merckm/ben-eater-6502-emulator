# Copilot cloud agent setup

The repository's [setup workflow](../.github/workflows/copilot-setup-steps.yml) uses a GitHub-hosted Ubuntu runner, Temurin JDK 25, and the checked-in Gradle Wrapper. The Java setup action is pinned by commit and JDK 25 tracks supported patch releases. The wrapper pins and verifies the Gradle distribution. The setup runs the same root `check` used locally, downloading the required dependencies before the agent begins. It uses a read-only workflow token and no repository secrets.

## Verify setup

1. Review the **Copilot Setup Steps** check on the setup PR. This runs the same job and steps the agent will use. After the workflow is on the default branch, it can also be run manually from **Actions → Copilot Setup Steps → Run workflow**.
2. For a live smoke test after merge, use a small issue with a disposable documentation change, ask the agent to run `./gradlew --no-daemon --console=plain check` and include the result in its PR, then assign the issue to Copilot. GitHub should start a session and create a draft PR on a dedicated branch. In the session log, verify the `copilot-setup-steps` job and the agent's test result.
3. If the PR shows **Approve and run workflows**, a maintainer with write access reviews the branch and approves the run from the PR. Check the resulting workflow and request a separate code review after the agent presents the PR. See [contributing](../CONTRIBUTING.md) for the review and owner-merge process.

The workflow check validates the setup job. Assignment, draft creation and approval are validated by the live smoke test after the setup file lands on the default branch. If the setup fails in an agent session, inspect the setup log before relying on the environment. GitHub may start the agent even after a setup failure.

## Account and repository prerequisites

Use an account with an eligible Copilot plan and access to assign Copilot in this repository. Copilot cloud agent and GitHub Actions must be enabled by the applicable account, organization and repository policies. Confirm the branch rules permit the agent's dedicated branch and the repository can run the pinned actions. Formal required approvals must come from eligible reviewers. Use a separate reviewer if the person who initiated the agent work cannot satisfy the approval rule.

[Configure the development environment](https://docs.github.com/en/copilot/how-tos/copilot-on-github/customize-copilot/customize-cloud-agent/customize-the-agent-environment) · [Assign work to Copilot](https://docs.github.com/en/copilot/how-tos/copilot-on-github/use-copilot-agents/kick-off-a-task) · [Review output and approve workflow runs](https://docs.github.com/en/copilot/how-tos/copilot-on-github/use-copilot-agents/review-copilot-output)
