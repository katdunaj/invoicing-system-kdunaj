package pl.futurecollars.invoicing.fixtures

import pl.futurecollars.invoicing.model.Company

class CompanyFixture {

    static company(int id) {

        new Company(UUID.randomUUID(), "Telnet", 12345, "Ul.Ogrodowa 3, 05-085 Kampinos")

    }
}
