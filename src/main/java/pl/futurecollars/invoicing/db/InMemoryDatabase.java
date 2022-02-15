package pl.futurecollars.invoicing.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.model.Invoice;

@Service
@RequiredArgsConstructor
public class InMemoryDatabase implements Database {

  HashMap<UUID, Invoice> database = new HashMap<>();

  @Override
  public Invoice save(Invoice invoice) {
    UUID id = UUID.randomUUID();
    invoice.setId(id);

    if (database.get(id) != null) {
      return save(invoice);
    }
    database.put(id, invoice);
    return invoice;
  }

  @Override
  public Invoice getById(UUID id) {
    return database.get(id);
  }

  @Override
  public List<Invoice> getAll() {
    return new ArrayList<>(database.values());
  }

  @Override
  public boolean delete(UUID id) {
    try {
      database.remove(id);
    } catch (Exception ex) {
      return false;
    }
    return true;
  }

  @Override
  public Invoice update(Invoice updatedInvoice) {
    database.put(updatedInvoice.getId(), updatedInvoice);
    return updatedInvoice;

  }
}