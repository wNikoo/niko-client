# Niko Client

A client-side Fabric mod for Minecraft 26.1.2 with an inventory macro system.

## Features

### Inventory Macros
- Create, edit, and manage inventory profiles
- Assign keybinds to profiles for instant activation
- Support for all inventory slots (hotbar, main, armor, offhand)
- Instant visual inventory swaps with controlled hotbar synchronization

## Commands

- `/nikoclient` or `/nc` - Open the main Niko Client GUI

## Building

```bash
./gradlew build
```

## Installation

1. Install Fabric Loader for Minecraft 26.1.2
2. Place the built JAR in your `.minecraft/mods` folder

## Architecture

- **Command Registry** - Handles `/nikoclient` and `/nc` commands
- **Profile Manager** - Manages profile creation, deletion, and persistence
- **Inventory State Manager** - Handles instant inventory state changes
- **Hotbar Action Queue** - Queues and synchronizes hotbar changes
- **Keybind Manager** - Manages profile keybinds
- **Macro Executor** - Executes profile activation logic
- **Item Matcher** - Reliable item identification and matching

## License

MIT
