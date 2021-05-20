package com.formentor.magnolia.secrets.example.pages;

import com.formentor.magnolia.secrets.example.ExampleWithSecrets;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import javax.inject.Inject;
import javax.jcr.Node;

public class PageSecretsModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<ConfiguredTemplateDefinition> {
    private final ExampleWithSecrets exampleWithSecrets;

    @Inject
    public PageSecretsModel(Node content, ConfiguredTemplateDefinition definition, RenderingModel<?> parent, ExampleWithSecrets exampleWithSecrets) {
        super(content, definition, parent);

        this.exampleWithSecrets = exampleWithSecrets;
    }

    public String getVendorApiKey() {
        return exampleWithSecrets.getVendorApiKey();
    }
}
