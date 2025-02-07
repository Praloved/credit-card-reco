package org.project.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreditCardRequest {
    private String name;
    private String parentCompany;
    private List<String> categories;
    private String annualFee;
    private String sortField;
    private String sortOrder;
}
