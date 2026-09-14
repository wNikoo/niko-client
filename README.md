# Niko Client

**A client-side Fabric mod for Minecraft 26.1.2 with an inventory macro system.**

![Minecraft Version](https://img.shields.io/badge/Minecraft-26.1.2-green)
![Fabric](https://img.shields.io/badge/Loader-Fabric-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Features

### 📦 Inventory Macros
- **Profile-based loadouts** - Create custom inventory configurations
- **Instant activation** - Swap entire inventory layout instantly with a keybind
- **Full inventory support** - Configure hotbar, main inventory, armor, and offhand
- **Smart item matching** - Reliable item identification using item IDs and metadata
- **Hotbar synchronization** - Controlled queuing of hotbar changes for server sync

### 🎮 Easy to Use
- **Simple GUI** - Clean, intuitive menu system
- **Commands** - `/nikoclient` or `/nc` to open the GUI
- **Profile management** - Create, edit, delete, and rename profiles easily
- **Keybind assignment** - Assign individual keybinds to each profile

### 🔒 Safe & Reliable
- **Container detection** - Won't execute while interacting with chests, furnaces, etc.
- **Graceful handling** - Missing items don't crash the mod
- **Persistent storage** - Profiles saved as JSON locally

## Installation

1. **Install Fabric Loader** for Minecraft 26.1.2
2. **Download** the latest release JAR
3. **Place** in your `.minecraft/mods` folder
4. **Launch** Minecraft with Fabric

## Usage

### Creating a Profile

1. Open `/nc` or `/nikoclient`
2. Click "Inventory Macros"
3. Click "Profiles"
4. Enter a profile name (e.g., "Mining", "Combat")
5. Click "Create Profile"

### Configuring a Profile

1. In the Profiles list, click your profile name
2. Click on inventory slots to configure them
3. Assign items from your current inventory
4. Click "Save" when done

### Assigning a Keybind

1. In the profile editor, click "Keybinds"
2. Select your profile
3. Press the key you want to assign (e.g., G, H, J)
4. Click "Save"

### Activating a Profile

- **Press the assigned keybind** in-game
- Your entire inventory will instantly rearrange
- Only hotbar changes are synchronized with the server

## Commands

| Command | Alias | Function |
|---------|-------|----------|
| `/nikoclient` | `/nc` | Open the main Niko Client GUI |

## Configuration

Profiles are stored locally in JSON format:

**Location**: `~/.minecraft/config/nikoclient/profiles.json`

**Example**:
```json
{
  "Mining": {
    "name": "Mining",
    "keybind": "key.keyboard.g",
    "slots": {
      "0": { "itemId": "minecraft:diamond_pickaxe" },
      "1": { "itemId": "minecraft:cobblestone" },
      "2": { "itemId": "minecraft:torch" }
    }
  }
}
```

## How It Works

### Instant Inventory Changes

When you activate a profile:

1. **Calculate** desired inventory state based on profile
2. **Apply instantly** to client-side inventory representation
3. **Match items** from your current inventory
4. **Queue hotbar changes** for server synchronization
5. **Result**: Entire inventory appears to change instantly

**Key Design**: Main inventory slots change instantly on the client. Only hotbar changes are queued for proper server communication.

### Item Matching

Items are identified using:
- **Item ID** (primary): `minecraft:diamond_pickaxe`
- **Custom Name** (secondary): Exact text match
- **NBT Data** (future): For advanced filtering

### Container Safety

Profiles cannot be executed while:
- Viewing a chest/storage container
- Using a crafting table
- Using a furnace or other interactive block
- This prevents accidental container content changes

## Architecture

- **Modular design** - Separated concerns for easy maintenance
- **Extensible** - Ready for future SkyBlock-specific features
- **Efficient** - Only processes necessary changes
- **Reliable** - Comprehensive error handling and logging

See [DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md) for detailed architecture documentation.

## Building from Source

```bash
# Clone the repository
git clone https://github.com/wNikoo/niko-client.git
cd niko-client

# Build the mod
./gradlew build

# Run in development environment
./gradlew runClient
```

The built JAR will be in `build/libs/`

## License

MIT License - See LICENSE file for details

## Contributing

Contributions are welcome! Feel free to:
- Report bugs
- Suggest features
- Submit pull requests

## Roadmap

- [x] Basic profile system
- [x] Instant inventory changes
- [x] Hotbar synchronization
- [x] GUI system
- [ ] Advanced item matching (enchantments, durability)
- [ ] GUI slot assignment (click to select items)
- [ ] Profile automation and scheduling
- [ ] SkyBlock-specific profiles
- [ ] Multi-profile switching chains

## Support

For issues, suggestions, or questions:
- Open an issue on GitHub
- Check the [DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md) for technical details

---

**Niko Client** - Fast, reliable inventory macros for vanilla Minecraft.
