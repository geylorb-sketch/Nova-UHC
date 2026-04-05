# NovaUHC

NovaUHC is a comprehensive Minecraft UHC (Ultra Hardcore) plugin suite developed for Spigot 1.8.8. It is structured as a multi-module project consisting of a core API and several scenario modules.

It offers a rich set of features including numerous scenarios, role-based game modes, custom UI configurations, and CloudNet integration for competitive Minecraft gameplay.

## 🚀 Features

NovaUHC is packed with features designed for both casual and competitive UHC hosting.

### 🎭 Comprehensive Scenario System
Over 50 scenarios are built-in, categorized to vary gameplay:
- **Classic**: Cutclean, HasteyBoy, Timber, Rodless, NoFall, BloodDiamonds.
- **Resource & Mining**: DoubleOre, OreSwap, OreRoulette, Magnet, LuckyOre, FastMiner.
- **Combat & PvP**: NoCleanUp, BloodLust, WeakestLink, Gladiator, Vampire, BuffKiller.
- **Game Mechanics**: TimeBombe, WebCage, AcidRain, Blizzard, Fallout.
- **Special Modes (Role-based)**: DragonFall, Werewolf (LoupGarou), FireForce, and DeathNote.

### 🧩 Role Modules
Separate plugin modules inject role-based scenarios into the core engine:

| Module | Description |
|--------|-------------|
| **DanDaDan** | 30+ roles inspired by DanDaDan, JoJo's Bizarre Adventure, Chainsaw Man, Doom Slayer, and more — with a custom Yokai world dimension |
| **Monster** | Monster UHC scenario with Trésor, Nocif/Passif/Boss roles and random events |
| **Ultimate** | Advanced scenarios: Legend (class-based), SkyHigh, SkyDef, TaupeGun, FallenKingdom, BeatTheSanta |

### ⚙️ Advanced Game Lifecycle
Automated management of the entire game flow:
- **Lobby**: Pre-game preparation and team selection.
- **Scattering**: Automated and optimized player distribution across the map.
- **Starting**: Invulnerability period to ensure a fair start.
- **Game Phase**: Active resource gathering with automated timers.
- **PvP Phase**: Configurable timer for PvP activation.
- **Meetup / Border Phase**: Automated world border shrinking (SimpleBorder) to force player encounters.
- **Ending**: Victory detection, firework celebrations, and automated server cleanup.

### 🖥️ Interactive Management GUIs
A set of comprehensive menus allows hosts to configure the game in real-time:
- **Main Config**: Central hub for all game settings.
- **Scenario Manager**: Toggle and configure multiple scenarios simultaneously.
- **Effects Config**: Per-player strength/resistance/crit percent tuning via `EffectsConfigUi`.
- **Border Control**: Adjust initial size, final size, and shrink speed.
- **Team Management**: Configure team sizes, friendly fire, and auto-filling.
- **World Settings**: Manage map parameters and accessibility.
- **Game Rules**: Toggle spectators, whitelist, and general UHC rules.

### 🎨 Per-Viewer TAB Color System
Hosts can colorize player names in the TAB list for specific viewers only (NMS packet-based, no global scoreboard pollution). Managed by `PlayerColorManager` with `/color` command support.

### 🛠️ Ability Templates
Les classes de base sont organisées dans le package `ability/template/` :
- `PassiveAbility` — passif déclenché chaque seconde via `onSec()`
- `MeleeAbility` — déclenché sur attaque au corps à corps
- `UseAbility` — déclenché au clic droit avec un item spécifique
- `BowAbility` — déclenché au tir à l'arc
- `CommandAbility` — déclenché via commande slash
- `ReviveAbility` — base pour les mécaniques de résurrection

### 💀 Revive System
New `ReviveAbility` base class and `/revive` command allow role scenarios to implement resurrection mechanics with full UI and cooldown integration.

### 📊 Robust Backend & Persistence
- **MongoDB Integration**: Persistent storage for player statistics, including Wins, Kills, Deaths, and a custom Coin reward system.
- **CloudNet 4 Support**: Seamless integration for multi-server networks, providing automated server status updates and orchestration.
- **FastBoard**: High-performance scoreboard for real-time player stats and game info without flickering.

### 🌍 Smart World Management
- **Automated Generation**: Creates Overworld, Nether, and End worlds specifically for each match.
- **Performance Optimized**: Includes chunk pre-loading tasks (`ShadowLoadingChunkTask`) and world populator adjustments.
- **Biome Manipulation**: Built-in `BiomeReplacer` to ensure consistent and fair playing fields.

### 👥 Team Coordination Tools
- **Shared Team Inventory**: Access a global inventory for your team via `/ti`.
- **Team Coordination**: Built-in tools for sharing coordinates and status with teammates.
- **Spectator Mode**: Fully managed spectator system for eliminated players and staff.

## 📋 Requirements

- **Java**: JDK 25 
- **Server**: Spigot 1.8.8 (or compatible forks like Paper).
- **Database**: MongoDB (Required for data persistence).
- **Optional**: CloudNet 4 (For automated server scaling).

## 🛠️ Installation & Setup

1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    cd API
    ```

2.  **Configuration**:
    *   **Main Config**: Edit `core/src/main/resources/config.yml` :
        ```yaml
        api:
          url: "https://api.nova-code.fr"
          key: "TA CLEF API ICI"
          server-name: "Nova-UHC"
        serverinfo:
          ip: "play.novauhc.fr"
          servername: "§cSERVER"
        discord: "https://discord.gg/..."
        ```
    *   **Advanced Configs**: Explore `core/src/main/resources/api/` for detailed settings:
        - `generalconfig.yml`: Core game rules and timers.
        - `lang.yml`: Translation and message settings.
        - `worldconfig.yml`: World generation and lobby settings.
        - `menu.yml`: Customization for the interactive GUIs.

3.  **Build the plugin**:
    ```bash
    ./gradlew.bat build
    ```
    The compiled `.jar` files are automatically copied to `F:\plugin\serv\plugins`.

## 📜 Commands

| Command | Aliases | Description |
| :--- | :--- | :--- |
| `/h` | `/host` | Main host command for managing the game. |
| `/config` | `/preconfig` | Opens the game configuration menu. |
| `/color` | | Opens the TAB color picker for per-viewer coloring. |
| `/revive` | | Revive a player (if the active scenario supports it). |
| `/teamco` | `/tc`, `/tcoo` | Team coordination tools. |
| `/teaminventory` | `/ti`, `/tinv` | Access the shared team inventory. |
| `/helpop` | | Contact host for assistance. |
| `/arena` | `/leave` | Arena management and exit. |
| `/doc` | | View project documentation/info. |
| `/discord` | | Get the official Discord link. |

## 📁 Project Structure

```
API/
├── core/                 # Core UHC engine and API
│   ├── src/main/java/net/novaproject/novauhc/
│   │   ├── ability/      # Ability base classes and templates (Passive, Melee, Use, Bow, Command, Revive)
│   │   ├── arena/        # Arena and lobby logic
│   │   ├── cloudnet/     # CloudNet 4 integration
│   │   ├── command/      # Command registration and handling
│   │   ├── database/     # MongoDB interactions and managers
│   │   ├── listener/     # Event listeners (Player, Entity, etc.)
│   │   ├── scenario/     # Scenarios + ScenarioBuilder + RandomGameEvent system
│   │   ├── task/         # Scheduled Bukkit tasks (Scatter, Timers)
│   │   ├── uhcplayer/    # Player data and session management
│   │   ├── uhcteam/      # Team logic and management
│   │   ├── ui/           # GUI menus (config, effects, color picker, etc.)
│   │   ├── utils/        # Utility classes (NMS, Config, Items, PlayerColorManager)
│   │   └── world/        # World generation and population
│   └── src/main/resources/ # Core assets and config files
├── dandadan/             # DanDaDan role-based scenario module (30+ roles)
├── monster/              # Monster UHC scenario module
├── ultimate/             # Advanced scenarios (Legend, SkyHigh, FallenKingdom, etc.)
├── templates/            # Ready-to-use skeletons to create a new module from scratch
│   ├── HOWTO.md          #   Generation guide + quality checklist
│   ├── classic/          #   Classic scenario template (no roles)
│   └── role/             #   Full role-based module template (Main, ScenarioUHC, Role, Camps, Lang, plugin.yml, build.gradle)
└── build.gradle          # Root build configuration
```

## 🧪 Tests
- TODO: Implement unit and integration tests.

## 📄 License

---
*Maintained by Lezombie3D and Sithey.*
