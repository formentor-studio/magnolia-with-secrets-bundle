package com.formentor.magnolia.secrets;

import com.bettercloud.vault.SslConfig;
import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VaultIntegrationTest {

//    @Test
    public void storeSecret_should_create_secret_in_Vault() throws VaultException {
        // Given
        final VaultConfig config =
                new VaultConfig()
                        .address("http://127.0.0.1:8200")               // Defaults to "VAULT_ADDR" environment variable
                        .token("myroot")                                // Defaults to "VAULT_TOKEN" environment variable
                        .openTimeout(5)                                 // Defaults to "VAULT_OPEN_TIMEOUT" environment variable
                        .readTimeout(30)                                // Defaults to "VAULT_READ_TIMEOUT" environment variable
                        .sslConfig(new SslConfig().verify(false).build())
                        .build();

        final Vault vault = new Vault(config);


        // When
        final String path = "secret/magnolia/";
        final String moduleName = "example-with-secrets";
        final String property = "vendorApiKey";
        final String propertyValue = "12ab";

        final Map<String, Object> secrets = new HashMap<>();
        secrets.put(property, propertyValue);

        final LogicalResponse writeResponse = vault.logical().write(path + moduleName, secrets);

        // Then
        assertNotNull(writeResponse.getData());
        assertNotNull(writeResponse.getData().get("created_time"));

        final Map<String, String> moduleConfig = vault.logical().read(path + moduleName).getData();

        assertNotNull(moduleConfig);
        assertEquals(propertyValue, moduleConfig.get(property));
    }
}
