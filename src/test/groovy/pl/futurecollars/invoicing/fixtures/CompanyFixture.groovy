package pl.futurecollars.invoicing.fixtures

import pl.futurecollars.invoicing.model.Company

class CompanyFixture {

    static company(int id) {

        new Company("156-75-78$id-111"
                , "Ul. Ogrodowa 3/$id, 02-000 Kampinos"
                , "Company $id")

    }

}
