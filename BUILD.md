# Niko Client Build Configuration

This mod is built for:
- **Minecraft**: 26.1.2
- **Mod Loader**: Fabric
- **Language**: Kotlin
- **Java Version**: 21

## Building

```bash
./gradlew build
```

Output JAR: `build/libs/niko-client-1.0.0.jar`

## Development

```bash
# Run in development environment
./gradlew runClient

# Clean build
./gradlew clean build

# Build without running tests
./gradlew build -x test
```

## Dependencies

- Fabric Loader 0.15.11
- Fabric API 0.102.0+1.21.2
- Fabric Language Kotlin 1.12.0
- Gson (for JSON serialization)

## Troubleshooting

### Build fails with "Cannot find symbol"
- Run `./gradlew --refresh-dependencies`
- Check that Minecraft 26.1.2 mappings are available

### Development environment won't start
- Ensure Java 21 is installed
- Run `./gradlew genSources` to generate sources

### Changes not appearing in game
- Rebuild with `./gradlew clean build`
- Restart the development environment
