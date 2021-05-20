package com.formentor.magnolia.secrets.storage.impl;

import com.bettercloud.vault.SslConfig;
import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.formentor.magnolia.secrets.storage.SecretsStorage;
import com.formentor.magnolia.secrets.storage.SecretsStorageException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SecretsStorageVaultImpl implements SecretsStorage {
    private String prefix;       // Prefix of module secrets
    private String address;      // Defaults to "VAULT_ADDR" environment variable
    private String token;        // Defaults to "VAULT_TOKEN" environment variable
    private Integer openTimeout; // Defaults to "VAULT_OPEN_TIMEOUT" environment variable
    private Integer readTimeout; // Defaults to "VAULT_READ_TIMEOUT" environment variable

    private Vault vault;

    @Override
    public Map<String, String> readModuleConfig(String module) {
        if (vault == null) throw new SecretsStorageException("Vault client is not built");

        final String path = prefix + module;
        try {
            return vault.logical().read(path).getData();
        } catch (VaultException e) {
            log.error("Errors getting secret {}", path, e);
            throw new SecretsStorageException("Errors getting secret " + path);
        }

    }

    public SecretsStorageVaultImpl() {

    }

    public SecretsStorageVaultImpl withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public SecretsStorageVaultImpl withAddress(String address) {
        this.address = address;
        return this;
    }

    public SecretsStorageVaultImpl withToken(String token) {
        this.token = token;
        return this;
    }

    public SecretsStorageVaultImpl withOpenTimeout(Integer openTimeout) {
        this.openTimeout = openTimeout;
        return this;
    }

    public SecretsStorageVaultImpl withReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public SecretsStorageVaultImpl build() throws SecretsStorageException {
        /**
         * In case "address" or "token" is null, VaultConfig will take value from environment variables VAULT_ADDR and VAULT_TOKEN
         */
        final VaultConfig config;
        try {
            config = new VaultConfig()
                    .address(address)          // Defaults to "VAULT_ADDR" environment variable
                    .token(token)              // Defaults to "VAULT_TOKEN" environment variable
                    .openTimeout(openTimeout)  // Defaults to "VAULT_OPEN_TIMEOUT" environment variable
                    .readTimeout(readTimeout)  // Defaults to "VAULT_READ_TIMEOUT" environment variable
                    .sslConfig(new SslConfig().verify(false).build())
                    .build();

            vault = new Vault(config);
        } catch (VaultException e) {
            throw new SecretsStorageException(e.getMessage());
        }

        return this;
    }
}