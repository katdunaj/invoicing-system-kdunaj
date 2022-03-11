package pl.futurecollars.invoicing.fixtures

import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

class InvoiceEntryFixture {

    static product(int id) {
        new InvoiceEntry("Description $id",
                BigDecimal.valueOf(100 * id)
                , BigDecimal.valueOf(100 + 23),
                Vat.VAT_23)
    }
}