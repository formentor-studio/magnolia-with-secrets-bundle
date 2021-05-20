package com.formentor.magnolia.secrets;

import com.formentor.magnolia.WithSecrets;
import com.formentor.magnolia.secrets.settings.VaultSettings;
import com.formentor.magnolia.secrets.storage.SecretsStorage;
import com.formentor.magnolia.secrets.storage.SecretsStorageFactory;
import info.magnolia.init.MagnoliaConfigurationProperties;
import info.magnolia.jcr.node2bean.impl.PreConfiguredBeanUtils;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.module.model.ModuleDefinition;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModuleConfigSecretsTest {

    @Test
    public void start_should_load_module_config_from_Secrets_storage() throws InvocationTargetException, IllegalAccessException {
        // History input
        final String moduleName = "my-module";

        // Given
        final WithSecrets withSecrets = mockWithSecrets();
        final SecretsStorageFactory secretsStorageFactory = mockSecretsStorageFactory(moduleName);
        final PreConfiguredBeanUtils beanUtils = mock(PreConfiguredBeanUtils.class);
        final MagnoliaConfigurationProperties magnoliaConfigurationProperties = mock(MagnoliaConfigurationProperties.class);

        final ModuleConfigSecrets moduleConfigSecrets = new ModuleConfigSecrets(withSecrets, secretsStorageFactory, beanUtils, magnoliaConfigurationProperties);

        // When
        final ModuleLifecycleContext moduleLifecycleContext = mockModuleLifecycleContext(moduleName);
        moduleConfigSecrets.start(moduleLifecycleContext);

        // Then
        verify(beanUtils).setProperty(moduleConfigSecrets, "secret-key", "secret-value");
    }

    private WithSecrets mockWithSecrets() {
        final WithSecrets withSecrets = new WithSecrets();
        withSecrets.setVaultSettings(new VaultSettings());

        return withSecrets;
    }

    private SecretsStorageFactory mockSecretsStorageFactory(String moduleName) {
        final SecretsStorage secretsStorage = mock(SecretsStorage.class);
        final Map<String, String> configMap = new HashMap<>();
        configMap.put("secret-key", "secret-value");
        when(secretsStorage.readModuleConfig(moduleName)).thenReturn(configMap);

        final SecretsStorageFactory secretsStorageFactory = mock(SecretsStorageFactory.class);
        when(secretsStorageFactory.getSecretsStorageVault(any(VaultSettings.class), any(MagnoliaConfigurationProperties.class))).thenReturn(secretsStorage);

        return secretsStorageFactory;
    }

    private ModuleLifecycleContext mockModuleLifecycleContext(String moduleName) {
        final ModuleLifecycleContext moduleLifecycleContext = mock(ModuleLifecycleContext.class);
        final ModuleDefinition moduleDefinition = new ModuleDefinition();
        moduleDefinition.setName(moduleName);
        when(moduleLifecycleContext.getCurrentModuleDefinition()).thenReturn(moduleDefinition);

        return moduleLifecycleContext;
    }
}
