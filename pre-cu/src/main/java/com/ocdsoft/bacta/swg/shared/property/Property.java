package com.ocdsoft.bacta.swg.shared.property;

import com.ocdsoft.bacta.swg.shared.object.GameObject;
import lombok.Getter;

public abstract class Property {
    @Getter
    private GameObject owner;
    @Getter
    private final int propertyId; //LabelHash

    protected Property(final int propertyId, final GameObject owner) {
        this.owner = owner;
        this.propertyId = propertyId;
    }

    public void initializeFirstTimeObject() {
    }

    public void addToWorld() {
    }

    public void removeFromWorld() {
    }

    public void conclude() {
    }
}
