package org.alien4cloud.tosca.model.instances;

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.alien4cloud.tosca.model.CSARDependency;
import org.alien4cloud.tosca.model.definitions.ScalarPropertyValue;
import org.alien4cloud.tosca.model.templates.NodeTemplate;
import org.elasticsearch.annotation.DateField;
import org.elasticsearch.annotation.ESObject;
import org.elasticsearch.annotation.Id;
import org.elasticsearch.annotation.NestedObject;
import org.elasticsearch.annotation.ObjectField;
import org.elasticsearch.annotation.StringField;
import org.elasticsearch.annotation.query.FetchContext;
import org.elasticsearch.annotation.query.TermFilter;
import org.elasticsearch.mapping.IndexType;

import java.util.Map;

import static alien4cloud.dao.model.FetchContext.SUMMARY;

/**
 * An instance of a node.
 */
@Getter
@Setter
@ESObject(all = false)
@EqualsAndHashCode(of = "id")
public class NodeInstance extends AbstractInstance {
    /**
     * Technical id of an instance in time.
     * 
     * The instance has an orchestrator id (instanceId) which is unique at a given point of time. However it is possible in case of scale up/down that a given
     * instance is created, deleted and then another instance with the same orchestrator id is created and deleted again.
     */
    @Id
    private String id;

    /** Id of the location resource if any. */
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed)
    private String locationResourceId;

    // The node template actually does not include the type version (maybe we should add that to the node template ?).
    @TermFilter
    @StringField(indexType = IndexType.not_analyzed, includeInAll = false)
    private String typeVersion;

    @ObjectField
    private NodeTemplate nodeTemplate;

    /** Optional field that contains orchestrator specific properties. */
    @ObjectField(enabled = false)
    private Map<String, String> operationsOutputs;

    /** Date of the creation of the instance. */
    @TermFilter
    @DateField(index = IndexType.not_analyzed)
    private Long creationDate;

    /** Date of the deletion of the instance. */
    @TermFilter
    @DateField(index = IndexType.not_analyzed)
    private Long deletionDate;

    public void setAttribute(String key, String value) {
        getAttributeValues().put(key, new ScalarPropertyValue(value));
    }
}