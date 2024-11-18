package it.unibo.deathnote;

import static java.lang.Thread.sleep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static it.unibo.deathnote.api.DeathNote.RULES;
import it.unibo.deathnote.impl.DeathNoteImplementation;
import it.unibo.deathnote.impl.DeathNoteImplementation.LastDeath;

class TestDeathNote {
    private DeathNoteImplementation<String,LastDeath> deathnote = new DeathNoteImplementation<>();

    /**
     * Tests that rule number 0 and negative rules do not exist.
     */
    @Test
    void testNRule(){
        try {
            deathnote.getRule(0);
            deathnote.getRule(-1);
        } catch ( IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
    }
    
    /**
     * Checks that no rule is empty or null.
     */
    @Test
    void testAllRules() {
        for (var rule: RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    /**
     * Checks that the human whose name is written in this DeathNote will die.
     */
    @Test
    void testWriteName() {
        assertFalse(deathnote.isNameWritten("L"));
        deathnote.writeName("L");
        assertTrue(deathnote.isNameWritten("L"));
        assertFalse(deathnote.isNameWritten("Ryuk"));
        assertFalse(deathnote.isNameWritten(""));
    }

    /**
     * Checks that only if the cause of death is written within the next 40 milliseconds
     * of writing the person's name, it will happen.
     */
    @Test
    void testDeathCause() throws InterruptedException {
        try {
            deathnote.writeDeathCause("suicide");
        } catch (IllegalStateException e) {
            assertTrue(e instanceof IllegalStateException);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        deathnote.writeName("Mello");
        assertEquals(deathnote.getDeathCause("Mello"), "heart attack");
        deathnote.writeName("Light");
        deathnote.writeDeathCause("karting accident");
        assertEquals(deathnote.getDeathCause("Light"),"karting accident");
        sleep(100);
        assertFalse(deathnote.writeDeathCause("suicide"));
        assertEquals(deathnote.getDeathCause("Light"),"karting accident");
    }

    /**
     * Checks that only if the details of death is written within the next 6 seconds and
     * 40 milliseconds of writing the death's cause, it will happen.
     */
    @Test
    void testDeathDetails() throws InterruptedException {
        try {
            deathnote.writeDetails("ethyl coma");
        } catch (IllegalStateException e) {
            assertTrue(e instanceof IllegalStateException);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        deathnote.writeName("Mello");
        assertEquals(deathnote.getDeathDetails("Mello"), "");
        deathnote.writeDetails("ran for too long");
        assertEquals(deathnote.getDeathDetails("Mello"),"ran for too long");
        deathnote.writeName("Light");
        sleep(6100);
        assertFalse(deathnote.writeDetails("drowned"));
        assertEquals(deathnote.getDeathDetails("Light"),"");
    }
}