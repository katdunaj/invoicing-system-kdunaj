package pl.futurecollars.invoicing.controllers;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.dto.InvoiceListDto;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class InvoiceController implements InvoiceControllerApi {

  private final InvoiceService invoiceService;

  @Override
  public ResponseEntity<Invoice> save(@RequestBody Invoice invoice) {
    log.debug("Adding new invoice to database {}", invoice);
    return ResponseEntity.ok()
      .body(invoiceService.save(invoice));
  }

  @Override
  public ResponseEntity<List<Invoice>> getAll() {
    log.debug("Getting all invoices from database");
    return ResponseEntity.ok()
      .body(invoiceService.getAll());
  }

  @Override
  public ResponseEntity<List<InvoiceListDto>> getList() {
    List<InvoiceListDto> invoices = invoiceService.getList();
    log.debug("Getting list of invoices from database {}", invoices);
    return ResponseEntity.ok()
      .body(invoices);
  }
  @Override
  public ResponseEntity<Invoice> getById(@PathVariable UUID id) {
    log.debug("Getting invoice ID: " + id + " from database");
    return invoiceService.getById(id).map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @Override
  public ResponseEntity<Invoice> update(@RequestBody Invoice invoice) {
    log.debug("Updating invoice ID: " + invoice.getId() + " in database");
    return ResponseEntity.ok()
      .body(invoiceService.update(invoice));
  }

  @Override
  public ResponseEntity<Boolean> update(@PathVariable UUID id) {
    log.debug("Deleting invoice ID: " + id + " from database");
    try {
      return ResponseEntity.ok()
        .body(invoiceService.delete(id));
    } catch (Exception e) {
      log.error("Exception: " + e + " occurred while deleting invoice ID: " + id + " from database");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}