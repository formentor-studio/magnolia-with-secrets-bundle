package com.formentor.magnolia;

import com.formentor.magnolia.secrets.settings.AWSSettings;
import com.formentor.magnolia.secrets.settings.GoogleSettings;
import com.formentor.magnolia.secrets.settings.VaultSettings;
import info.magnolia.module.ModuleLifecycleContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is optional and represents the configuration for the magnolia-with-secrets module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/magnolia-with-secrets</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 * See https://documentation.magnolia-cms.com/display/DOCS/Module+configuration for information about module configuration.
 */
@Slf4j
@Data
public class WithSecrets implements info.magnolia.module.ModuleLifecycle {

    private VaultSettings vaultSettings;
    private AWSSettings awsSettings;
    private GoogleSettings googleSettings;

    @Override
    public void start(ModuleLifecycleContext moduleLifecycleContext) {

    }

    @Override
    public void stop(ModuleLifecycleContext moduleLifecycleContext) {

    }
}
