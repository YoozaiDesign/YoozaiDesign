# Repository Guidelines

## Project Structure & Module Organization
- Gradle-based Fabric mod living under `src/`: common logic in `src/main/java/archi`, client-only hooks in `src/client/java/archi`, and assets/resources in `src/main/resources`.
- Custom block definitions (e.g., `DynamicStairBlock`) sit alongside registries in `src/main/java/archi`. Client rendering helpers (such as `StairDynamicModel`) are under `src/client/java/archi/client`.
- Textures, blockstates, and models follow the Fabric resource layout at `src/main/resources/assets/archisnap`. Keep OptiFine CTM files in their existing subdirectories.

## Build, Test, and Development Commands
- `./gradlew build` – Compiles, remaps, and packages the mod.
- `./gradlew runClient` – Launches a development Minecraft client with the mod loaded.
- `./gradlew runServer` – Starts a dedicated test server.
- `./gradlew datagen` – Runs Fabric data generation entry points when resource automation is needed.
Use the provided Gradle wrapper (`gradlew` / `gradlew.bat`) to stay aligned with the project’s pinned Gradle 9.1 distribution.

## Coding Style & Naming Conventions
- Java sources target Java 17; follow standard 4-space indentation and meaningful camelCase identifiers.
- Keep block IDs and resource names lowercase with underscores (e.g., `stair_closedstringer_brown_iron`).
- Favor helper utilities (e.g., `VoxelShapeUtils`) over inline duplication; add concise, high-value comments only where logic is non-obvious.

## Testing Guidelines
- No dedicated automated test suite is present; rely on `runClient` / `runServer` smoke tests for behavioral validation.
- When adding data-driven content (blockstates, models), verify in-game placement for every orientation and interaction scenario.
- If you introduce automated tests, document invocation alongside the Gradle task and commit the configuration.

## Commit & Pull Request Guidelines
- Write commits in imperative mood (“Add stair connection states”), grouping related changes logically.
- Include references to related issues or tickets in commit messages when available.
- Pull requests should describe gameplay impact, note testing steps (`runClient`, in-game checks), and attach screenshots or logs when visual/functional changes occur.
- Ensure the workspace builds cleanly (`./gradlew build`) before requesting review; avoid mixing formatting-only changes with functional updates.
