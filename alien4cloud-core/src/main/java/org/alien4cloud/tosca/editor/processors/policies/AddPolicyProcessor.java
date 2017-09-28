package org.alien4cloud.tosca.editor.processors.policies;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import org.alien4cloud.tosca.catalog.index.IToscaTypeSearchService;
import org.alien4cloud.tosca.editor.EditionContextManager;
import org.alien4cloud.tosca.editor.operations.policies.AddPolicyOperation;
import org.alien4cloud.tosca.editor.processors.IEditorOperationProcessor;
import org.alien4cloud.tosca.model.templates.PolicyTemplate;
import org.alien4cloud.tosca.model.templates.Topology;
import org.alien4cloud.tosca.model.types.PolicyType;

import alien4cloud.tosca.topology.TemplateBuilder;
import alien4cloud.utils.AlienUtils;
import alien4cloud.utils.NameValidationUtils;
import org.springframework.stereotype.Component;

/**
 * Add a new policy to the topology.
 */
@Slf4j
@Component
public class AddPolicyProcessor implements IEditorOperationProcessor<AddPolicyOperation> {
    @Inject
    private IToscaTypeSearchService toscaTypeSearchService;

    @Override
    public void process(AddPolicyOperation operation) {
        Topology topology = EditionContextManager.getTopology();

        NameValidationUtils.isValid(operation.getPolicyName());
        AlienUtils.failIfExists(topology.getPolicies(), operation.getPolicyName(), "A policy with the given name {} already exists in the topology {}.",
                operation.getPolicyName(), topology.getId());

        String[] splittedId = operation.getPolicyTypeId().split(":");
        PolicyType policyType = toscaTypeSearchService.findOrFail(PolicyType.class, splittedId[0], splittedId[1]);

        if (topology.getPolicies() == null) {
            topology.setPolicies(new LinkedHashMap<>());
        }

        PolicyTemplate policyTemplate = TemplateBuilder.buildPolicyTemplate(policyType);

        log.debug("Adding a new policy template <" + operation.getPolicyName() + "> of type <" + operation.getPolicyTypeId() + "> to the topology <"
                + topology.getId() + "> .");

        topology.getPolicies().put(operation.getPolicyName(), policyTemplate);
    }
}