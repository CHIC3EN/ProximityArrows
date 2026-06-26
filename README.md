# 🏹 Proximity Arrows
**A lightweight, server-sided Fabric mod that completely overhauls archery combat by making arrow selection intuitive and buffing your allies safe.**

In Vanilla Minecraft, managing different types of arrows is a nightmare. The game blindly prioritizes arrows in your offhand, and then searches your inventory from top left to bottom right. If you want to switch between harming arrows and healing arrows, you have to frantically drag items around your inventory mid-combat or use your hotbar and rely on off-hand swapping, sacrificing precious hotbar space.

Proximity Arrows solves this with two massive QoL changes:

## **✨ Features**
### 1. Smart Arrow Selection
Your bow will now automatically fire the arrow stack that is physically closest to it in your inventory grid.
- Keep a stack of damage arrows next to your main bow.
- Keep a stack of healing arrows next to your secondary support bow (or realistically, any tipped arrows really)
- The mod calculates the 2D distance between the bow's slot and the arrow's slot, bypassing the clunky vanilla top left priority. (Note: The offhand slot is still checked first for convenience and it just feels like it makes sense).
### 2. Harmless Buffs
Ever tried to heal a friend with an arrow of instant health, only to accidentally kill them with the physical arrow damage? Say no more.
- Arrows with beneficial potion effects (Healing, Regeneration, Swiftness, Night Vision, etc.) will no longer deal damage or knockback.
- The arrow safely applies its magical effect and disappears without causing the target to flinch or panic.
- This works automatically on Players, Pets, Villagers, and all Living Entities!
## ⚙️ Modpack & Compatibility Friendly
This mod dynamically checks if a potion effect is classified as "beneficial" by the game engine, meaning it automatically works with custom potions added by other mods! (Yet to be tested)

For custom modded arrows that don't use standard potion effects (like the vanilla Spectral Arrow, which is included by default), server admins and modpack makers can easily add any item to the zero-damage list using the built-in Item Tag:

```
#proximityarrows:harmless_arrows
```

📥 Installation
- **Requires:** Fabric Loader and the Fabric API
- **Server-Sided:** You only need to install this on the server! Vanilla clients can connect and benefit from the smart arrow selection and harmless buffs without needing the mod installed on their end. (It also works perfectly in Singleplayer).
