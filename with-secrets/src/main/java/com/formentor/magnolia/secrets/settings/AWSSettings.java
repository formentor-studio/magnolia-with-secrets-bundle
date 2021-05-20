package com.formentor.magnolia.secrets.settings;

import lombok.Data;

@Data
public class AWSSettings {
    private String prefix; // Prefix of secrets - for example /secret/magnolia/ -
}
