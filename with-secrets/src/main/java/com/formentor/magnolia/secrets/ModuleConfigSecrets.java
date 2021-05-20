package com.formentor.magnolia.secrets;

import com.formentor.magnolia.WithSecrets;
import com.formentor.magnolia.secrets.storage.SecretsStorage;
import com.formentor.magnolia.secrets.storage.SecretsStorageException;
import com.formentor.magnolia.secrets.storage.SecretsStorageFactory;
import info.magnolia.init.MagnoliaConfigurationProperties;
import info.magnolia.jcr.node2bean.impl.PreConfiguredBeanUtils;
import info.magnolia.module.ModuleLifecycleContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class ModuleConfigSecrets implements info.magnolia.module.ModuleLifecycle {
    private final WithSecrets withSecrets;
    private final SecretsStorageFactory secretsStorageFactory;
    private final PreConfiguredBeanUtils beanUtils;
    private final MagnoliaConfigurationProperties magnoliaConfigurationProperties;

    public ModuleConfigSecrets(WithSecrets withSecrets, SecretsStorageFactory secretsStorageFactory, PreConfiguredBeanUtils beanUtils, MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        this.withSecrets = withSecrets;
        this.secretsStorageFactory = secretsStorageFactory;
        this.beanUtils = beanUtils;
        this.magnoliaConfigurationProperties = magnoliaConfigurationProperties;
    }

    @Override
    public void start(ModuleLifecycleContext moduleLifecycleContext) {
        try {
            /**
             * I decided creating the Secret manager client instead of injecting keep it simple and to avoid security and concurrency issues.
             * The cost is low, it is used just during start up and client is just used by this class
             */
            // Get instance of Secrets storage
            final SecretsStorage secretsStorage = getSecretsStorage(withSecrets, secretsStorageFactory, magnoliaConfigurationProperties);

            // Read module config from Secrets storage
            final String moduleName = moduleLifecycleContext.getCurrentModuleDefinition().getName(); // The path in Vault of secret configuration is "path" + "name-of-the-module", - for example secret/magnolia/myModule -
            Map<String, String> config = secretsStorage.readModuleConfig(moduleName);

            // Set config properties
            config.forEach((propertyName, propertyValue) -> {
                try {
                    beanUtils.setProperty(this, propertyName, propertyValue);
                } catch (ReflectiveOperationException e) {
                    log.error("Failed to resolve and set bean property {} due to a reflection operation issue: {}, bean property is skipped",
                            propertyName, Optional.ofNullable(e.getMessage()).orElse(e.getCause().getMessage()));
                } catch (Exception e) {
                    log.error("Failed to resolve and set property {} due to an unexpected issue: {}, bean property is skipped", propertyName, e.getMessage());
                }
            });
        } catch (SecretsStorageException e) {
            log.error("Failed to load configuration from Secret storage", e);
        }
    }

    @Override
    public void stop(ModuleLifecycleContext moduleLifecycleContext) {

    }

    /**
     * Returns an implementation of SecretsStorage depending on the configuration of the module
     * @param withSecrets
     * @return
     */
    private SecretsStorage getSecretsStorage(WithSecrets withSecrets, SecretsStorageFactory secretsStorageFactory, MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        if (withSecrets.getVaultSettings() != null) {
            return secretsStorageFactory.getSecretsStorageVault(withSecrets.getVaultSettings(), magnoliaConfigurationProperties);
        } else {
            throw new SecretsStorageException("Missing secrets storage configuration");
        }
    }
}
