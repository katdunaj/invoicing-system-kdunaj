package pl.futurecollars.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.file.FileService;
import pl.futurecollars.invoicing.file.JsonService;
import pl.futurecollars.invoicing.model.Invoice;

@Data
@ConditionalOnProperty(name = "invoicing-system.database", havingValue = "file")
@Service
public class FileBaseData implements Database {

  private FileService fileService;
  private FileService idFileService;
  private JsonService<Invoice> invoiceService;

  private void setRandomID(Invoice invoice) {
    UUID id = UUID.randomUUID();
    invoice.setId(id);
  }

  @Override
  public Invoice save(Invoice invoice) {
    if (invoice.getId() == null) {
      setRandomID(invoice);
    }

    if (containsID(invoice.getId())) {
      setRandomID(invoice);
      return save(invoice);
    }

    fileService.writeToDatabase(invoiceService.convertToJson(invoice));
    return invoice;
  }

  @Override
  public Invoice getById(UUID id) {
    if (containsID(id)) {
      return getAll()
        .stream()
        .filter(invoice -> invoice.getId().equals(id))
        .collect(Collectors.toList())
        .get(0);
    }
    return null;

  }

  @Override
  public List<Invoice> getAll() {
    return fileService.readFromDatabase()
      .stream()
      .map(s -> invoiceService.convertToObject(s, Invoice.class))
      .collect(Collectors.toList());
  }

  @Override
  public Invoice update(Invoice updatedInvoice) {
    delete(updatedInvoice.getId());
    save(updatedInvoice);
    return updatedInvoice;
  }

  @Override
  public boolean delete(UUID id) {
    if (containsID(id)) {
      List<Invoice> databaseCopy = getAll();
      fileService.clearDatabase();
      databaseCopy
        .stream()
        .filter(invoice -> !invoice.getId().equals(id))
        .forEach(this::save);
      return true;
    }
    return false;
  }

  @Override
  public void clear() {
    fileService.clearDatabase();
  }


  public boolean containsID(UUID id) {
    try {
      return Files.readAllLines(Paths.get(String.valueOf(idFileService)))
        .stream()
        .anyMatch(line -> line.contains(id.toString()));
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}