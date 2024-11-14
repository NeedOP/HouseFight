package se.eli.adventure.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void getHealth() {
        Resident resident = new Resident("resident", 12, 3);
        int health = resident.getHealth();
        assertEquals(12, health);
    }

    @Test
    void attack() {
        Resident resident = new Resident("resident", 12, 3);
        Burglar burglar = new Burglar("Burglar", 12, 4);
        resident.attack(burglar);
        assertEquals(9, burglar.getHealth());

    }

    @Test
    void takeDamage() {
        Resident resident = new Resident("resident", 12, 3);
        resident.takeDamage(1);
        assertEquals(11, resident.getHealth());
    }

    @Test
    void isConsious() {
        Resident resident = new Resident("resident", 12, 3);
        resident.takeDamage(3);
        assertTrue(resident.isConsious());
    }

    @Test
    void isNotConsious() {
        Resident resident = new Resident("resident", 12, 3);
        resident.takeDamage(12);
        assertFalse(resident.isConsious());
    }


    @Test
    void addDamage() {
        Resident resident = new Resident("resident", 12, 3);
        resident.addDamage(3);
        assertEquals(6, resident.getDamage());
    }
}