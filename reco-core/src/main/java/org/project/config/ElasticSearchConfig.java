package org.project.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ElasticSearchConfig {
    private String host;
    private int port;

    @Override
    public String toString() {
        return String.format("%s:%d", host, port);
    }
}
