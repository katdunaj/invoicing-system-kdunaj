package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Company {

  @ApiModelProperty(value = "Object name ", required = true, example = "Telnet")
  private String name;

  @ApiModelProperty(value = "Tax identification number", required = true, example = "112-425-567-89")
  private long taxIdentificationNumber;

  @ApiModelProperty(value = "Object address", required = true, example = "ul.Ogrodowa 6, 05-085 Kampinos A")
  private String address;

  public Company(String name, long taxIdentificationNumber, String address) {
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.address = address;
  }
}
