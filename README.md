# Mod Support
You can easily add custom repair configurations for items in MendingReworked by creating a JSON file in your mod's data folder.

### Create the JSON file

Place a json file under:
`<your mod resources>/data/mendingrework/repair_configs/<YOUR_MOD_ID>.json`

### Define your repair entries
In your JSON file, add an array of objects. Each object represents a single repair rule:
```json5
[
    {
        "item": "modid:your_item",              // The item to be repaired
        "repair": "modid:repair_material",      // The material used for repairs
        "repair_fraction": 0.5                  // Fraction of durability restored per repair (0.0–1.0)
    }
]
```

- item: A valid Minecraft resource location string for the item you want to enable repairs on.
- repair: A valid resource location for the item used as the repair material.
- repair_fraction: Decimal between 0.0 and 1.0 defining the percentage of durability restored each time this material is used. For example, 0.5 restores 50% of the item's max durability.