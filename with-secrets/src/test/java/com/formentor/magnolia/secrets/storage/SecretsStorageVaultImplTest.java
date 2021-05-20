package com.formentor.magnolia.secrets.storage;

import com.formentor.magnolia.secrets.storage.impl.SecretsStorageVaultImpl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SecretsStorageVaultImplTest {
    @Test
    public void readModuleConfig_should_return_Config() throws IOException {
        final String RESOURCE_PATH = "/v1/secret/data/foo";
        final String vaultResponse = FileUtils.readFileToString(new File(SecretsStorageVaultImplTest.class.getResource("/VaultAPIResponse.json").getFile()), "utf-8");
        final MockWebServer server = mockupWebServer(RESOURCE_PATH, vaultResponse);
        server.start();

        final SecretsStorageVaultImpl secretsStorageVault = new SecretsStorageVaultImpl()
                .withAddress(server.url("").toString())
                .withToken("my-token")
                .withPrefix("secret/")
                .build();

        final String module = "foo";
        Map<String, String> moduleConfig = secretsStorageVault.readModuleConfig(module);

        assertNotNull(moduleConfig);
    }

    @Test(expected = SecretsStorageException.class)
    public void readModuleConfig_Should_fail_when_missing_Vault_client() {
        final SecretsStorageVaultImpl secretsStorageVault = new SecretsStorageVaultImpl();

        final String module = "foo";
        secretsStorageVault.readModuleConfig(module);

        // Reinforces the test
        assertTrue(false);
    }

    /**
     * Creates a mock of a web server that exposes Vault API
     * @param path
     * @param response
     * @return
     */
    private static MockWebServer mockupWebServer(final String path, String response) {
        MockWebServer mockWebServer = new MockWebServer();

        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if (path.equals(request.getPath())) {
                    return new MockResponse().setResponseCode(200).setBody(response);
                }
                return new MockResponse().setResponseCode(404);
            }
        });
        return mockWebServer;
    }

}
