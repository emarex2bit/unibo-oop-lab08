
package it.unibo.deathnote;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;


/**
 * Tests the {@link DeatNoteImpl} class.
 */
class TestDeathNote {

    private DeathNoteImpl deathNote;

    
    @BeforeEach
    void setup()
    {
        deathNote = new DeathNoteImpl();
    }
    /**
     * Test Boundaries
     */
    @Test
    void testBoundsRules()
    {
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
        for (String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    /**
     * Test Human name written correctly in the Death Note
     */
    @Test
    void testWriteName()
    {
        String name = "Nicola Stroffolino";
        if(deathNote.isNameWritten(name))
        {
            
        }
    }

}