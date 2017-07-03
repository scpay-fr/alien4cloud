package org.alien4cloud.tosca.model.instances;

import java.util.Map;

import org.alien4cloud.tosca.model.definitions.PropertyValue;
import org.elasticsearch.annotation.Id;
import org.elasticsearch.annotation.StringField;
import org.elasticsearch.annotation.query.TermFilter;
import org.elasticsearch.mapping.IndexType;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;

/**
 * Defines an abstract instance.
 */
@Getter
@Setter
public class AbstractInstance {
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed)
    private String deploymentId;
    /** Id of the location */
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed)
    private String locationId;
    /** Id of the orchestrator. */
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed)
    private String orchestratorId;

    /** Id of the instance as specified for the orchestrator. */
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed)
    private String instanceId;
    /** hierarchy of the resource type, this include the node type as well as it's parent types. */
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed)
    private String[] resourceTypeHierarchy;

    /** Contains the map of attribute values for the instance. */
    @StringField(indexType = IndexType.not_analyzed, includeInAll = false)
    private Map<String, PropertyValue> attributeValues = Maps.newHashMap();

    /** Optional field that contains orchestrator specific properties. */
    @StringField(indexType = IndexType.not_analyzed, includeInAll = false)
    private Map<String, String> runtimeProperties = Maps.newHashMap();
}