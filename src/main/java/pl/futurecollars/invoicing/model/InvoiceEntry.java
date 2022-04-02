package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEntry {

  @ApiModelProperty
  private String description;

  @ApiModelProperty
  private BigDecimal price;

  @ApiModelProperty
  private BigDecimal vatValue;

  @ApiModelProperty
  private Vat vatRate;

}