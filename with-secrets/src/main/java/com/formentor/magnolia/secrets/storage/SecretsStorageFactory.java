package com.formentor.magnolia.secrets.storage;

import com.formentor.magnolia.WithSecrets;
import com.formentor.magnolia.secrets.settings.AWSSettings;
import com.formentor.magnolia.secrets.settings.GoogleSettings;
import com.formentor.magnolia.secrets.settings.VaultSettings;
import com.formentor.magnolia.secrets.storage.impl.SecretsStorageAWSImpl;
import com.formentor.magnolia.secrets.storage.impl.SecretsStorageGoogleImpl;
import com.formentor.magnolia.secrets.storage.impl.SecretsStorageVaultImpl;
import info.magnolia.init.MagnoliaConfigurationProperties;

public class SecretsStorageFactory {
    protected static final String PROPERTY_VAULT_TOKEN = "VAULT_TOKEN"; // Environment variable that stores the token of Vault

    public <T extends SecretsStorage> T getSecretsStorage(Class<T> implementation, WithSecrets config) {

        return null;
    }

    /**
     * Returns an instance of SecretsStorage implemented for Hashicorp Vault
     * @param config
     * @param magnoliaConfigurationProperties
     * @return
     */
    public SecretsStorage getSecretsStorageVault(VaultSettings config, MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        return new SecretsStorageVaultImpl()
                .withAddress(config.getHost())
                .withToken(getVaultToken(magnoliaConfigurationProperties))
                .withPrefix(config.getPrefix())
                .build();
    }

    /**
     * Returns an instance of SecretsStorage implemented for AWS Secrets manager
     * @param config
     * @param magnoliaConfigurationProperties
     * @return
     */
    public SecretsStorage getSecretsStorageAWS(AWSSettings config, MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        return new SecretsStorageAWSImpl();
    }

    /**
     * Returns an instance of SecretsStorage implemented for Google secret manager
     * @param config
     * @param magnoliaConfigurationProperties
     * @return
     */
    public SecretsStorage getSecretsStorageGoogle(GoogleSettings config, MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        return new SecretsStorageGoogleImpl();
    }

    /**
     * Returns Vault token from Magnolia configuration.
     *
     * @param magnoliaConfigurationProperties
     * @return
     */
    private String getVaultToken(MagnoliaConfigurationProperties magnoliaConfigurationProperties) {
        return magnoliaConfigurationProperties.getProperty(PROPERTY_VAULT_TOKEN);
    }

}