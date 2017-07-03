package org.alien4cloud.tosca.model.instances;

import java.util.Map;

import org.alien4cloud.tosca.model.definitions.AbstractPropertyValue;

/**
 * Instance of a relationship.
 */
public class RelationshipInstance {
    private String sourceInstanceId;
    private String targetInstanceId;

    /** Map of attributes of the relationship. */
    private Map<String, AbstractPropertyValue> attributes;
}