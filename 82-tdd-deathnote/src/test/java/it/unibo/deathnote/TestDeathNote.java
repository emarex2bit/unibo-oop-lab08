package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;


/**
 * Tests the {@link DeatNoteImpl} class.
*/
class TestDeathNote {

    /**
     * Test Boundaries.
     */
    @Test
    void testBoundsRules()
    {
        final DeathNoteImpl deathNote = new DeathNoteImpl();
        final IllegalArgumentException e = assertThrowsExactly(IllegalArgumentException.class, 
            () -> {
                deathNote.getRule(0);
                deathNote.getRule(-1);
            },
            "Rule 0 and negatives rules do not exists in the Death Note"
        );
        final String message = e.getMessage();
        assertNotNull(message, "Exception Message Null");
        assertFalse(message.isBlank(), "Exception Message Empy");
    }

    /**
     * Test Rules Validity in the Death Note
     */
    @Test
    void testRulesValidity()
    {
        for (final String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    /**
     * Test Human name written correctly in the Death Note.
     */
    @Test
    void testWriteName()
    {
        final DeathNoteImpl deathNote = new DeathNoteImpl();
        String name = "Nicola Stroffolino";
        assertFalse(deathNote.isNameWritten(name));
        deathNote.writeName(name);
        assertTrue(deathNote.isNameWritten(name));
        name = "Diego Alessi";
        assertFalse(deathNote.isNameWritten(name));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testWriteDeathCause() throws InterruptedException
    {
        final DeathNoteImpl deathNote = new DeathNoteImpl();
        assertThrowsExactly(IllegalStateException.class, 
            () -> {
                deathNote.writeDeathCause("death cause");
            },
            "Illegal writing of a death cause before any name"
        );
        String name = "Nicola Stroffolino";
        deathNote.writeName(name);
        assertTrue(deathNote.getDeathCause(name) == "heart attack");
        name = "Diego Alessi";
        deathNote.writeName(name);
        deathNote.writeDeathCause("karting accident");
        assertTrue(deathNote.getDeathCause(name) == "karting accident");
        
        Thread.sleep(100);
        deathNote.writeDeathCause("test change");
        assertTrue(deathNote.getDeathCause(name) == "karting accident");
    }

    @Test
    void testWriteDeathDetails() throws InterruptedException
    {
        final DeathNoteImpl deathNote = new DeathNoteImpl();
        assertThrowsExactly(IllegalStateException.class, 
            () -> {
                deathNote.writeDetails("in a bus");
            },
            "Illegal writing of death details before any name"
        );
        final String name = "Nicola Stroffolino";
        deathNote.writeName(name);
        assertTrue(deathNote.getDeathDetails(name).isBlank());
        deathNote.writeDetails("ran for too long");
        assertEquals(deathNote.getDeathDetails(name), "ran for too long");
        final String name1 = "Diego Alessi";
        deathNote.writeName(name1);
        Thread.sleep(6100);
        deathNote.writeDetails("another reason");
        assertEquals(deathNote.getDeathDetails(name), "ran for too long");

    }
}
