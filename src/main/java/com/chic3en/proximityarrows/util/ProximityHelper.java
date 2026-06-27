package com.chic3en.proximityarrows.util;

public class ProximityHelper {

    /**
     * Calculates the horizontal (X) grid position of a slot.
     * Hotbar is slots 0-8. Main inventory is slots 9-35.
     */
    public static int getGridX(int slotId) {
        if (slotId >= 0 && slotId <= 8) {
            // Hotbar slots are simply x = 0 through 8
            return slotId;
        } else if (slotId >= 9 && slotId <= 35) {
            // Main inventory slots wrap around every 9 slots
            return (slotId - 9) % 9;
        }
        // Return a negative number if it's an armor slot, offhand, or crafting grid
        return -1;
    }

    /**
     * Calculates the vertical (Y) grid position of a slot.
     * Top row of inventory is 0, hotbar is row 3.
     */
    public static int getGridY(int slotId) {
        if (slotId >= 0 && slotId <= 8) {
            // Hotbar is visually the bottom row (row 3)
            return 3;
        } else if (slotId >= 9 && slotId <= 35) {
            // Main inventory is rows 0, 1, and 2
            return (slotId - 9) / 9;
        }
        return -1;
    }

    /**
     * Calculates the Euclidean distance between two inventory slots.
     * * @param slot1 The slot ID of the Bow
     * @param slot2 The slot ID of the Arrow stack
     * @return The distance, or Double.MAX_VALUE if slots are invalid
     */
    public static double calculateDistance(int slot1, int slot2) {
        int x1 = getGridX(slot1);
        int y1 = getGridY(slot1);

        int x2 = getGridX(slot2);
        int y2 = getGridY(slot2);

        // If either item is not in the main 36 inventory slots (e.g., offhand),
        // Assign a massive distance so it gets ignored,
        // OR add special logic for the offhand later.
        // Update: Offhand takes priority
        if (x1 == -1 || y1 == -1 || x2 == -1 || y2 == -1) {
            return Double.MAX_VALUE;
        }

        // Pythagorean theorem for distance: d = sqrt((x2-x1)^2 + (y2-y1)^2)
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
