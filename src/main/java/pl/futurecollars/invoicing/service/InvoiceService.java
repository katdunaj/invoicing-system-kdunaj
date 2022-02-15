package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@Service
@Data
public class InvoiceService {

  private final InMemoryDatabase database;

  public BigDecimal getTotalNet(Invoice invoice) {
    return invoice.getInvoiceEntries()
      .stream()
      .map(InvoiceEntry::getPrice)
      .reduce(BigDecimal.valueOf(0), BigDecimal::add);
  }

  public BigDecimal getTotalTaxValue(Invoice invoice) {
    return invoice.getInvoiceEntries()
      .stream()
      .map(InvoiceEntry::getPrice)
      .reduce(BigDecimal.valueOf(0), BigDecimal::add);
  }

  public BigDecimal getTotalGross(Invoice invoice) {
    return getTotalNet(invoice).add(getTotalTaxValue(invoice));
  }

  public List<Invoice> filterByIssuer(String issuer) {
    return database.getAll()
      .stream()
      .filter((Invoice invoice) -> issuer.equals(invoice.getIssuer().getName()))
        .collect(Collectors.toList());
  }

  public List<Invoice> filterByReceiver(String receiver) {
    return database.getAll()
      .stream()
      .filter((Invoice invoice) -> receiver.equals(invoice.getReceiver().getName()))
        .collect(Collectors.toList());
  }

  public Invoice save(Invoice invoice) {
    return database.save(invoice);
  }

  public Invoice getById(UUID id) {
    return database.getById(id);
  }

  public List<Invoice> getAll() {
    return database.getAll();
  }

  public Invoice update(Invoice updatedInvoice) {
    return database.update(updatedInvoice);
  }

  public boolean delete(UUID id) {
    return database.delete(id);
  }
}
