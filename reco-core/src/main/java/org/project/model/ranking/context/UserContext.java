package org.project.model.ranking.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.banking.model.UserTransactions;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContext {
    UserTransactions userTransactions;
    Map<String, Feature> userFeatures;
}
