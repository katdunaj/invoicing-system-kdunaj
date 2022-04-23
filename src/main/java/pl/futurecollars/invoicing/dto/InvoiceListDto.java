package pl.futurecollars.invoicing.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class InvoiceListDto {
  private LocalDate date;
  private UUID id;
  private String issuer;
  private String receiver;
}

