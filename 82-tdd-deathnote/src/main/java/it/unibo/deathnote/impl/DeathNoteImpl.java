package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.deathnote.api.DeathNote;

/**
 * Death Note Implementation.
 */
public class DeathNoteImpl implements DeathNote {

    public static final int MAX_MS_CAUSE = 40;
    public static final int MAX_MS_DETAILS = 6040;

    private final List<DeathNoteRecord> records;

    /**
     * Death Note Object Init.
     */
    public DeathNoteImpl() {
        records = new ArrayList<>();
    }

    /**
     * Get rule number of @param ruleNumber.
     */
    @Override
    public String getRule(final int ruleNumber) {
       if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Invalid rule number, only value in the range 1...." + RULES.size());
       }
       return RULES.get(ruleNumber - 1);
    }

    /**
     * Write @param name in the Death Note.
     */
    @Override
    public void writeName(final String name) {
        if (!isNameWritten(name)) {
            records.add(new DeathNoteRecord(name));
        }
    }

    /**
     * Write Death @param cause.
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (records.isEmpty()) {
            throw new IllegalStateException("No name written");
        }
        final DeathNoteRecord lastRecord = records.getLast();

        return lastRecord.writeCause(cause);
    }

    /**
     * Write Death @param details.
     */
    @Override
    public boolean writeDetails(final String details) {
        if (records.isEmpty()) {
            throw new IllegalStateException("No name written");
        }
        final DeathNoteRecord lastRecord = records.getLast();

        return lastRecord.writeDetails(details);
    }

    /**
     * Get Death cause for person with @param name.
     */
    @Override
    public String getDeathCause(final String name) {
        final DeathNoteRecord record = findRecord(name);
        if (record == null) { 
            return "";
        }
        return record.getCause();
    }

    private DeathNoteRecord findRecord(final String name) {
        for (final var record : records) {
            if (record.getName().equalsIgnoreCase(name)) {
                return record;
            }
        }
        return null;
    }

    /**
     * Get Death details for person with @param name.
     */
    @Override
    public String getDeathDetails(final String name) {
        final DeathNoteRecord record = findRecord(name);
        if (record == null) {
            return "";
        }
        return record.getDetails();
    }

    /**
     * Get if this name is written in the Death Note.
     */
    @Override
    public boolean isNameWritten(final String name) {
        return findRecord(name) != null;
    }

    private class DeathNoteRecord {

        private final String name;
        private String cause;
        private String details;

        private final Timer timerDeathCause;
        private final Timer timerDeathDetails;

        DeathNoteRecord(final String name) {
            this.name = name;
            this.cause = "heart attack";
            this.details = "";
            this.timerDeathCause = new Timer();
            this.timerDeathDetails = new Timer();
        }

        public String getName() {
            return this.name;
        }

        public String getCause() {
            return this.cause;
        }

        public String getDetails() {
            return this.details;
        }

        public boolean writeCause(final String deathCause) {
            if (timerDeathCause.evalueteFor(MAX_MS_CAUSE)) {
                this.cause = deathCause;
                timerDeathDetails.reset();
                return true;
            }
            return false;
        }

        public boolean writeDetails(final String deathDetails) {
            if (timerDeathDetails.evalueteFor(MAX_MS_DETAILS)) {
                this.details = deathDetails;
                return true;
            }
            return false;
        }

        private class Timer {
            private long time;

            Timer() {
                reset();
            }

            public boolean evalueteFor(final int timeMax) {
                return System.currentTimeMillis() - time < timeMax;
            }

            private void reset() {
                this.time = System.currentTimeMillis();
            }
        }
    }
}
