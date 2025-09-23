# Release Note and Changelog Writing Guidelines

This document defines the writing standards for Release Notes and Changelogs of the Android dependency library.

The goal is to keep release documentation consistent, clear, developer-friendly, and easy to maintain across versions. Each release document should help library users, SDK integrators, Android developers, QA engineers, and maintainers understand what changed, whether an upgrade is required, and whether any integration action is needed.

## 1. File Location

All release-related documents should be stored in:

```text
document/releases/
```

The directory should follow this structure:

```text
document/releases/
├── release-guidelines.md
├── v1.0.0.md
├── v1.1.0.md
└── v1.2.0.md
```

Where:

* `release-guidelines.md` describes the writing rules for Release Notes and Changelogs.
* `vX.Y.Z.md` records the Release Note and Changelog for the corresponding library version.

## 2. Version File Structure

Each version file must contain the following two sections:

```md
## Release Note

## Changelog
```

The two sections serve different purposes and should be written with different levels of detail.

## 3. Release Note

The Release Note is intended for developers and integrators who use the library.

It should explain the value, impact, and integration requirements of the release in clear technical language. The Release Note should help readers quickly understand whether they should upgrade, what benefits the new version provides, and whether any code or configuration changes are required.

### Writing Principles

* Describe changes from the perspective of library users and SDK integrators.
* Focus on public APIs, integration behavior, compatibility, configuration, runtime behavior, and upgrade impact.
* Highlight new capabilities, important fixes, breaking changes, deprecated APIs, and behavior changes when applicable.
* Explain why the change matters, not only what changed.
* Clearly mention required migration steps when applicable.
* Clearly state whether existing integrations are affected when this is useful for understanding upgrade impact.
* Include compatibility notes when Android SDK, Gradle, Kotlin, Java, Android Gradle Plugin, AndroidX, or third-party dependencies are changed.
* Mention changes to permissions, manifest configuration, ProGuard/R8 rules, initialization flow, callbacks, threading behavior, or error handling when they affect integrators.
* Avoid exposing unnecessary internal implementation details.
* Avoid directly copying commit messages.
* Avoid using only task IDs, branch names, internal codenames, class names, or method names without explanation.
* Avoid adding non-applicable optional sections or placeholder statements.

### Recommended Content

A Release Note should include relevant information from the following categories:

* New public APIs or integration capabilities.
* Deprecated, removed, or changed APIs.
* Breaking changes and migration requirements.
* Important behavior changes.
* Compatibility or dependency updates.
* Configuration changes.
* Important stability, performance, or security fixes.
* Known issues or limitations.
* Recommended upgrade notes.

These categories are guidance only. Include them only when they apply to the current release.

## 4. Changelog

The Changelog is intended for maintainers and developers who need a more detailed summary of code-level changes.

It should be based on the actual changes in the current version, but it must not be a raw copy of commit history.

The Changelog does not need to be split into multiple subsections. Use a direct bullet list.

### Writing Principles

* Summarize code changes into clear technical descriptions.
* Sort changes by importance or module relevance.
* Describe concrete changes and their actual impact.
* Keep necessary technical context when it helps explain the change.
* Use consistent wording and tense across all items.
* Avoid vague descriptions such as “optimized logic,” “fixed bug,” or “updated code” without explaining what changed.
* Do not directly copy commit messages.
* Do not list only task IDs, branch names, temporary internal names, or issue numbers.
* Do not include internal-only details that are not useful for future maintenance.

## 5. Breaking Changes

If a release contains breaking changes, the Release Note must clearly identify them.

Breaking changes include but are not limited to:

* Removed public APIs.
* Changed public API signatures.
* Changed default behavior.
* Changed initialization requirements.
* Changed threading or callback behavior.
* Changed minimum supported Android SDK version.
* Changed required Gradle, Kotlin, Java, or Android Gradle Plugin versions.
* Removed or replaced transitive dependencies.
* Changed permission requirements.
* Changed manifest configuration requirements.
* Changed ProGuard or R8 rules.
* Changed data format, protocol, or serialization behavior.

## 6. Migration Notes

Migration notes should be included whenever developers need to modify code, configuration, dependencies, build scripts, permissions, or initialization logic.

Migration notes should explain:

* What changed.
* Who is affected.
* What action is required.
* Whether the old behavior is still supported.
* Whether the migration is required immediately or can be deferred.
* Example code before and after migration, when helpful.

## 7. Deprecation Notes

When an API is deprecated, the documentation should explain:

* Which API is deprecated.
* What should be used instead.
* Whether the deprecated API still works.
* Whether removal is planned.
* Which version may remove the API, if known.

## 8. Dependency and Compatibility Notes

Compatibility information should be included whenever it changes.

Mention changes such as:

* Minimum SDK version.
* Target SDK compatibility.
* Kotlin version.
* Java version.
* Android Gradle Plugin version.
* Gradle version.
* AndroidX dependency updates.
* Third-party dependency updates.
* Transitive dependency changes.
* ProGuard or R8 rule changes.
* ABI or native library changes.
* Compile-only or runtime dependency requirements.

## 9. Known Issues

If a release has known limitations or risks, they should be documented clearly.

Known issues should explain:

* What the issue is.
* When it may occur.
* Whether there is a workaround.
* Whether it will be addressed in a future version, if known.

## 10. Style Guidelines

Use clear, concise, and professional English.

## 11. Final Checklist

Before publishing a version release document, check the following:

* The file is placed under `document/releases/`.
* The file name follows the `vX.Y.Z.md` format.
* Both `Release Note` and `Changelog` sections are included.
* The Release Note explains the impact on library users.
* New APIs and important behavior changes are clearly described.
* Breaking changes are clearly marked.
* Migration steps are included when needed.
* Deprecated APIs include replacement guidance.
* Compatibility or dependency changes are documented.
* Known issues are documented when applicable.
* The Changelog summarizes actual code changes instead of copying commit messages.
* Internal task IDs, branch names, and temporary codenames are not used as standalone descriptions.
* The language is clear, consistent, and suitable for developers and maintainers.
