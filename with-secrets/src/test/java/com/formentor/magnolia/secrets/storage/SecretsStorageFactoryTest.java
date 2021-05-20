package com.formentor.magnolia.secrets.storage;

import com.formentor.magnolia.secrets.settings.VaultSettings;
import com.formentor.magnolia.secrets.storage.impl.SecretsStorageVaultImpl;
import info.magnolia.init.MagnoliaConfigurationProperties;
import org.junit.Test;

import static com.formentor.magnolia.secrets.storage.SecretsStorageFactory.PROPERTY_VAULT_TOKEN;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecretsStorageFactoryTest {
    @Test
    public void getSecretsStorageVault_Should_return_Vault_instance() {
        // Given
        final VaultSettings vaultSettings = new VaultSettings();
        vaultSettings.setHost("http://vault-host:8200");
        vaultSettings.setPrefix("secret/magnolia");

        // When
        MagnoliaConfigurationProperties magnoliaConfigurationProperties = mock(MagnoliaConfigurationProperties.class);
        when(magnoliaConfigurationProperties.getProperty(PROPERTY_VAULT_TOKEN)).thenReturn("myroot");
        SecretsStorage secretsStorage = new SecretsStorageFactory().getSecretsStorageVault(vaultSettings, magnoliaConfigurationProperties);

        // Then
        assertNotNull(secretsStorage);
        assertTrue(secretsStorage instanceof SecretsStorageVaultImpl);
    }
}
