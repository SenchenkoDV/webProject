package com.senchenko.aliens.entity;

public class Monster extends Entity {
    private int monsterId;
    private String name;
    private Race race;
    private String description;
    private Double averageRating;

    public Monster(int monsterId, String name, Race race, String description, Double averageRating) {
        this.monsterId = monsterId;
        this.name = name;
        this.race = race;
        this.description = description;
        this.averageRating = averageRating;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monster monster = (Monster) o;

        if (monsterId != monster.monsterId) return false;
        if (name != null ? !name.equals(monster.name) : monster.name != null) return false;
        if (race != null ? !race.equals(monster.race) : monster.race != null) return false;
        if (description != null ? !description.equals(monster.description) : monster.description != null) return false;
        return averageRating != null ? averageRating.equals(monster.averageRating) : monster.averageRating == null;
    }

    @Override
    public int hashCode() {
        int result = monsterId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (race != null ? race.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (averageRating != null ? averageRating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "monsterId=" + monsterId +
                ", name='" + name + '\'' +
                ", race=" + race +
                ", description='" + description + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
