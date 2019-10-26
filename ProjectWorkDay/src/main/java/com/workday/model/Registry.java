package com.workday.model;

import com.workday.entity.EntityRegistry;

public class Registry {

    private EntityRegistry entityRegistry;

    private float percentageHour;

    public EntityRegistry getEntityRegistry() {
        return entityRegistry;
    }

    public void setEntityRegistry(EntityRegistry entityRegistry) {
        this.entityRegistry = entityRegistry;
    }

    public float getPercentageHour() {
        return percentageHour;
    }

    public void setPercentageHour(float percentageHour) {
        this.percentageHour = percentageHour;
    }

    public Registry(EntityRegistry entityRegistry, float percentageHour) {
        this.entityRegistry = entityRegistry;
        this.percentageHour = percentageHour;
    }

    public Registry(EntityRegistry entityRegistry) {
        this.entityRegistry = entityRegistry;
    }

    public Registry() {
    }
}
