package org.project.ranking;

import org.project.model.ModelContext;

public interface IModel<T extends ModelContext> {
    String getModelName();

    String getModelVersion();

    double score(T modelContext);

}
