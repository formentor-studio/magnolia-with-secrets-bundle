package com.formentor.magnolia.secrets.settings;

import lombok.Data;

@Data
public class GoogleSettings {
    private String projectId; // Google project id
    private String prefix;    // Prefix of secrets - for example /secret/magnolia/
}
