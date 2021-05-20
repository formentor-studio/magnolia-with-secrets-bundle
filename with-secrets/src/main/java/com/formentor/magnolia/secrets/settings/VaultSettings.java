package com.formentor.magnolia.secrets.settings;

import lombok.Data;

@Data
public class VaultSettings {
    private String host;   // Vault host - for example http://127.0.0.1:8200 -
    private String prefix; // Prefix of secrets - for example secret/magnolia/ -
}
