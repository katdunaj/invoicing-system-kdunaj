package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

  @Id
  @ApiModelProperty(value = "Tax identification number", required = true, example = "1234567890")
  private String taxIdentificationNumber;

  @ApiModelProperty(value = "Object address", required = true, example = "ul.Ogrodowa 6, 05-085 Kampinos A")
  private String address;

  @ApiModelProperty(value = "Object name ", required = true, example = "Telnet")
  private String name;

  @ApiModelProperty(value = "Healthy insurance amount", required = true, example = "1000.50")
  private BigDecimal healthyInsurance;

  @ApiModelProperty(value = "Company pension insurance amount", required = true, example = "500.25")
  private BigDecimal pensionInsurance;
}
