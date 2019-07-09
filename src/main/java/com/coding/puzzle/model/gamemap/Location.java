package com.coding.puzzle.model.gamemap;

import java.util.Objects;
import java.util.UUID;

public class Location {

    private UUID uuid;
    private LocationType type;

    public Location(LocationType type){
        this.uuid = UUID.randomUUID();
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return getUuid().equals(location.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
