package pl.futurecollars.invoicing.db;

import java.util.List;
import java.util.UUID;
import pl.futurecollars.invoicing.model.Invoice;

public interface Database {

  Invoice save(Invoice invoice);

  Invoice getById(UUID id);

  List<Invoice> getAll();

  boolean delete(UUID id);

  Invoice update(Invoice updatedInvoice);

  void clear();
}
