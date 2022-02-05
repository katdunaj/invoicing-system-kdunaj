package pl.futurecollars.invoicing.service;

import java.util.List;
import java.util.UUID;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InvoiceService {

  private Database database;

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
