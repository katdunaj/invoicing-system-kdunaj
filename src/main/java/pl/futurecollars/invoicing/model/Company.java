package pl.futurecollars.invoicing.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Company {

  private UUID id;
  private String name;
  private long taxIdentificationNumber;
  private String address;

  public Company(UUID id, String name, long taxIdentificationNumber, String address) {
    this.id = id;
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.address = address;
  }
}