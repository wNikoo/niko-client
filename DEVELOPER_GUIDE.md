# Niko Client - Developer Guide

## Architecture Overview

### Core Modules

#### 1. **Command System** (`command/`)
- `CommandRegistry` - Registers `/nikoclient` and `/nc` commands
- Opens the main GUI screen when invoked

#### 2. **GUI System** (`gui/`)
- `ScreenManager` - Manages screen transitions
- `NikoClientMainScreen` - Main menu
- `InventoryMacrosScreen` - Inventory macros submenu
- `ProfileListScreen` - Create, delete, and select profiles
- `ProfileEditorScreen` - Configure individual profile slots
- `KeybindsScreen` - Placeholder for keybind management
- `SlotWidget` - Visual representation of inventory slots

#### 3. **Inventory Macro System** (`inventory/`)

**Models:**
- `InventorySlot` - Represents a single inventory slot with type information
- `ItemMatcher` - Reliable item identification using item ID, custom name, and NBT data
- `Profile` - Represents a complete inventory configuration

**Managers:**
- `ProfileManager` - Handles profile creation, deletion, renaming, and persistence (JSON)
- `InventoryStateManager` - Captures and applies inventory states instantly
- `HotbarActionQueue` - Queues and processes hotbar synchronization sequentially
- `KeybindManager` - Registers and manages profile keybinds
- `MacroExecutor` - Executes profile activation logic
- `ItemFinder` - Searches inventory for items matching criteria
- `ContainerDetector` - Detects whether player is in a container GUI

#### 4. **Event System** (`event/`)
- `ClientEventHandler` - Registers event listeners:
  - Client tick events for queue processing
  - Keybind pressing checks
  - Profile activation

#### 5. **Utilities** (`util/`)
- `ConfigUtil` - Configuration directory management
- `ItemStackUtil` - Item stack comparison and copying utilities

#### 6. **Data** (`data/`)
- `ItemMatcherSerializer` - Gson serialization for ItemMatcher
- `ProfileSerializer` - Gson serialization for Profile

#### 7. **Notifications** (`notification/`)
- `NotificationManager` - Display action bar messages for user feedback

## How Profiles Work

### Profile Structure
```json
{
  "Mining": {
    "name": "Mining",
    "keybind": "G",
    "slots": {
      "0": { "itemId": "minecraft:diamond_pickaxe" },
      "1": { "itemId": "minecraft:cobblestone" },
      "2": { "itemId": "minecraft:torch" }
    }
  }
}
```

### Activation Flow

1. User presses profile keybind (e.g., G)
2. `ClientEventHandler` detects the keybind press
3. `MacroExecutor.executeProfile(profile)` is called
4. Current inventory state is captured via `InventoryStateManager`
5. Desired state is calculated based on profile configuration
6. Items are matched using `ItemFinder`
7. Inventory is applied instantly via `InventoryStateManager.applyInventoryState()`
8. Hotbar changes are optionally queued via `HotbarActionQueue` for synchronization

## Instant Inventory Changes

The key design principle is **instant visual inventory changes**:

- Main inventory is rearranged instantly on the client
- No visible animation or sequence of clicks
- Only hotbar changes are queued for proper server synchronization
- `HotbarActionQueue` processes changes sequentially with tick delays

## Item Matching

Items are identified using:

1. **Item ID** (primary, most reliable)
   - Format: `minecraft:diamond_pickaxe`
   - Fetched from `Registries.ITEM.getId(itemStack.item)`

2. **Custom Name** (secondary)
   - Exact match with `itemStack.name.string`

3. **NBT Data** (tertiary, future enhancement)
   - Stored but not actively used yet

If an item is missing:
- No crash or freeze
- Slot is left unchanged or handled gracefully
- Optional notification to user

## Persistence

Profiles are stored as JSON:
- **Location**: `~/.minecraft/config/nikoclient/profiles.json`
- **Format**: Flat map of profile names to profile objects
- **Auto-save**: Triggered after create/delete/rename operations

## Keybinds

- Each profile can have a single assigned keybind
- Keybinds are registered with Fabric's keybinding system
- Pressed keybinds are checked each client tick
- Default keybind for opening main GUI is `N`

## Container Safety

- `ContainerDetector` checks if player is in a container GUI
- Profiles cannot be executed while in incompatible containers
- Prevents accidental container content modification

## Future Enhancements

- Advanced item matching with enchantments, durability, attributes
- NBT data filtering
- Conditional slot logic
- Profile presets (e.g., "SkyBlock Mining", "PvP Combat")
- GUI slot assignment (click to select items)
- Profile scheduling/automation
- Profile variants and switching chains

## Building and Testing

```bash
# Build the mod
./gradlew build

# Run in development environment
./gradlew runClient

# Clean build
./gradlew clean build
```

## Testing Checklist

- [ ] Profile creation works
- [ ] Profile deletion works
- [ ] Profile renaming works
- [ ] Profile saving/loading works
- [ ] Keybind assignment works
- [ ] Profile execution is instant
- [ ] Hotbar synchronization works
- [ ] Item matching is reliable
- [ ] No crashes when items are missing
- [ ] Container detection prevents execution
- [ ] Notifications display correctly

## Code Style

- Kotlin with modern idioms
- Logger-based debugging (SLF4J)
- Comprehensive error handling
- Modular and extensible design
- Clear separation of concerns
