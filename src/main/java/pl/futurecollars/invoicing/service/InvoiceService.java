package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.dto.InvoiceListDto;
import pl.futurecollars.invoicing.dto.mappers.InvoiceListMapper;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@Service
@RequiredArgsConstructor
public class InvoiceService {

  private final Database database;

  private final InvoiceListMapper invoiceListMapperImpl;


  public BigDecimal getTotalNet(Invoice invoice) {
    return invoice.getEntries()
      .stream()
      .map(InvoiceEntry::getPrice)
      .reduce(BigDecimal.valueOf(0), BigDecimal::add);
  }

  public BigDecimal getTotalTaxValue(Invoice invoice) {
    return invoice.getEntries()
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

  public List<Invoice> filterByReceiverName(String receiver) {
    return database.getAll()
      .stream()
      .filter((Invoice invoice) -> receiver.equals(invoice.getReceiver().getName()))
      .collect(Collectors.toList());
  }

  public List<Invoice> filterByDate(LocalDate date) {
    return database.getAll()
      .stream()
      .filter((Invoice invoice) -> date.equals(invoice.getDate()))
      .collect(Collectors.toList());
  }

  public Invoice save(Invoice invoice) {
    return database.save(invoice);
  }

  public Optional<Invoice> getById(UUID id) {
    return Optional.ofNullable(database.getById(id));
  }

  public List<Invoice> getAll() {
    return database.getAll();
  }

  public Invoice update(Invoice updatedInvoice) {
    return database.update(updatedInvoice);
  }

  public List<InvoiceListDto> getList() {
    return database.getAll().stream().map(invoiceListMapperImpl::toDto).collect(Collectors.toList());
  }


  public boolean delete(UUID id) {
    return database.delete(id);
  }
}
