package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Invoice {

  @ApiModelProperty
  private UUID id;

  @ApiModelProperty
  private LocalDate issuerDate;

  @ApiModelProperty
  private Company issuer;

  @ApiModelProperty
  private Company receiver;

  @ApiModelProperty
  private List<InvoiceEntry> invoiceEntries;

  public Invoice(UUID id, LocalDate issuerDate, Company issuer, Company receiver,
                 List<InvoiceEntry> invoiceEntries) {
    this.id = id;
    this.issuerDate = issuerDate;
    this.issuer = issuer;
    this.receiver = receiver;
    this.invoiceEntries = invoiceEntries;
  }
}

