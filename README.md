# IntelliJ Changelist UI Test

This project contains a Kotlin/JUnit integration UI test for IntelliJ IDEA using JetBrains IDE Starter and Driver.

## Scenario covered

The test:

1. Launches IntelliJ IDEA Community.
2. Opens a public sample project.
3. Opens Settings.
4. Navigates to `Version Control > Changelists` using the Settings search.
5. Selects the `Create changelists automatically` checkbox.
6. Verifies that the checkbox is selected through the Swing component state.
7. Confirms the dialog with `OK`.

## Running the test

```bash
./gradlew test
```

On Windows:

```bash
.\gradlew.bat test
```
