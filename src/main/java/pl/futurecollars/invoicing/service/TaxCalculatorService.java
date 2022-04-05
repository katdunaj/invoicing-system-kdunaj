package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;
import pl.futurecollars.invoicing.model.TaxReport;

@Service
@RequiredArgsConstructor
public class TaxCalculatorService {

  private final Database database;

  private BigDecimal visit(Predicate<Invoice> predicate, Function<InvoiceEntry, BigDecimal> function) {
    return database.getAll()
      .stream()
      .filter(predicate)
      .flatMap(invoice -> invoice.getInvoiceEntries().stream())
      .map(function)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  BigDecimal income(String taxIdentificationNumber) {
    return visit(invoice -> invoice.getIssuer().getTaxIdentificationNumber().equals(taxIdentificationNumber),
      InvoiceEntry::getPrice);
  }

  BigDecimal costs(String taxIdentificationNumber) {
    return visit(invoice -> invoice.getReceiver().getTaxIdentificationNumber().equals(taxIdentificationNumber),
      InvoiceEntry::getPrice);
  }

  BigDecimal incomingVat(String taxIdentificationNumber) {
    return visit(invoice -> invoice.getIssuer().getTaxIdentificationNumber().equals(taxIdentificationNumber),
      InvoiceEntry::getVatValue);
  }

  BigDecimal outgoingVat(String taxIdentificationNumber) {
    return visit(invoice -> invoice.getReceiver().getTaxIdentificationNumber().equals(taxIdentificationNumber),
      InvoiceEntry::getVatValue);
  }

  BigDecimal earnings(String taxIdentificationNumber) {
    return income(taxIdentificationNumber).subtract(costs(taxIdentificationNumber));

  }

  BigDecimal vatToPay(String taxIdentificationNumber) {
    return incomingVat(taxIdentificationNumber).subtract(outgoingVat(taxIdentificationNumber));

  }

  public TaxReport taxReport(String taxIdentificationNumber) {
    return new TaxReport.TaxReportBuilder()
      .setIncomingVat(incomingVat(taxIdentificationNumber))
      .setOutgoingVat(outgoingVat(taxIdentificationNumber))
      .setIncome(income(taxIdentificationNumber))
      .setCosts(costs(taxIdentificationNumber))
      .setEarnings(earnings(taxIdentificationNumber))
      .setVatToPay(vatToPay(taxIdentificationNumber))
      .build();
  }
}

