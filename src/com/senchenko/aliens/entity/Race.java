package com.senchenko.aliens.entity;

public class Race extends Entity {
    private int raceId;
    private String race;

    public Race(int raceId, String race) {
        this.raceId = raceId;
        this.race = race;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Race race1 = (Race) o;

        if (raceId != race1.raceId) return false;
        return race != null ? race.equals(race1.race) : race1.race == null;
    }

    @Override
    public int hashCode() {
        int result = raceId;
        result = 31 * result + (race != null ? race.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Race{" +
                "raceId=" + raceId +
                ", race='" + race + '\'' +
                '}';
    }
}
