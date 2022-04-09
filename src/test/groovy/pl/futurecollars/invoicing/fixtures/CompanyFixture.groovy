package pl.futurecollars.invoicing.fixtures

import pl.futurecollars.invoicing.model.Company

class CompanyFixture {

    static company(int id) {

        new Company("123-45-6$id-819"
                , "Ul.Ogrodowa 6$id, 05-085 Kampinos A"
                , "Company $id", 1000.00, 500.97)
    }
}