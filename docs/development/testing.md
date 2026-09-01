# Build and test matrix

Fishing Frenzy has verified Fabric and NeoForge targets for Minecraft 26.1, 26.1.1, 26.1.2, and
26.2 from one source tree. Each matrix target has pinned dependencies in `gradle/matrix/` and
writes its jar and future run files to a target-specific directory.

The two earliest NeoForge targets need a narrow build-time Architectury compatibility jar. Their
published NeoForge releases expose `BlockEvent.BreakEvent`, whereas Architectury 20.0.4 references
the later `BreakBlockEvent` name. `build.matrix.gradle` recompiles only Architectury's affected
event-handler source against that target's real API and replaces only those class files in a local,
target-only development jar. It does not add a fake `net.neoforged.*` class and it is never used by
the later targets. Re-evaluate this adapter whenever either dependency changes.

For the reusable conversion procedure and the NeoForge platform-wiring pitfalls, see
[Stonecutter multi-version migration](stonecutter-multiversion-migration.md).

Use the smart wrapper from the repository root:

```text
python build-smart.py matrix:compile
python build-smart.py matrix:package
python build-smart.py matrix:server
python build-smart.py matrix
```

`matrix:compile` compiles all eight targets. `matrix:package` processes their resources and builds
their jars. `matrix:server` runs Loom's `configureLaunch` task for each target, generating the
dedicated-server launch configuration without starting a server. A release still requires the
normal Fabric and NeoForge `runServer` runtime smoke tests.

Compilation and launch configuration do not prove a client can survive resource reload. Before
declaring a Minecraft-version target usable, manually run that target's `runClient` task and wait
through the initial resource reload to a stable title screen. Stop the client normally before
testing the next target.
