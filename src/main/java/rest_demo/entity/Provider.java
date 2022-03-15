package rest_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Provider {
    @Id @NotNull
    private long id;
    @NotNull
    private String name;
    private int contractYear;
    private String billingInfo;
    private String serviceState;
    private String clinicalFocus;
    private String coveragePlan;
}
