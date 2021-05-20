package com.formentor.magnolia.secrets.example;

import com.formentor.magnolia.WithSecrets;
import com.formentor.magnolia.secrets.ModuleConfigSecrets;
import com.formentor.magnolia.secrets.storage.SecretsStorageFactory;
import info.magnolia.init.MagnoliaConfigurationProperties;
import info.magnolia.jcr.node2bean.impl.PreConfiguredBeanUtils;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

/**
 * This class is optional and represents the configuration for the example-with-secrets module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/example-with-secrets</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 * See https://documentation.magnolia-cms.com/display/DOCS/Module+configuration for information about module configuration.
 */
@Setter
@Getter
public class ExampleWithSecrets extends ModuleConfigSecrets {

    private String vendorApiKey;

    @Inject
    public ExampleWithSecrets(WithSecrets withSecrets, SecretsStorageFactory secretsStorageFactory, PreConfiguredBeanUtils beanUtils, MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        super(withSecrets, secretsStorageFactory, beanUtils, magnoliaConfigurationProperties);
    }
}