package com.formentor.magnolia.secrets.storage;

import java.util.Map;

public interface SecretsStorage {
    Map<String, String> readModuleConfig(String module);
}