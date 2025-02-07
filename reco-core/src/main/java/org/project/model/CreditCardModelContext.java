package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.project.model.ranking.context.UserContext;
import org.project.model.response.CreditCardDocument;

@Getter
@AllArgsConstructor
public class CreditCardModelContext implements ModelContext {
    UserContext userContext;
    CreditCardDocument creditCardDocument;
}
