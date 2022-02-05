package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Invoice {

  private UUID id;
  private LocalDate issuerDate;
  private Company issuer;
  private Company receiver;
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