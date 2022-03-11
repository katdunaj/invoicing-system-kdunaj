package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.InMemoryDatabase
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat
import spock.lang.Specification
import java.time.LocalDate

class InvoiceServiceTest extends Specification {

    def issuer = new Company(UUID.randomUUID(), "Telnet", 123456, "ul.Ogrodowa 3 Kampinos")
    def issuer2 = new Company(UUID.randomUUID(), "Nokia", 123456, "ul.Ogrodowa 3 Kampinos")
    def issuerUpdated = new Company(UUID.randomUUID(), "Nokia", 123456, "ul.Ogrodowa 8 Kampinos")
    def receiver = new Company(UUID.randomUUID(), "TelPlus", 6732890, "ul.Ogrodowa 8 Kampinos")
    def receiver2 = new Company(UUID.randomUUID(), "IntelPlus", 6732890, "ul.Ogrodowa 8 Kampinos")
    def issuerDate = LocalDate.of(2020, 10,23)
    def issuerDate2 = LocalDate.of(2021,10,24)
    def invoiceEntries1 = new InvoiceEntry("Cukier", BigDecimal.valueOf(20), BigDecimal.valueOf(17), Vat.VAT_8)
    def invoiceEntries2 = new InvoiceEntry("Sól", BigDecimal.valueOf(30), BigDecimal.valueOf(16), Vat.VAT_5)
    def entries = Arrays.asList(invoiceEntries1, invoiceEntries2)
    def invoice = new Invoice(UUID.randomUUID(), issuerDate, issuer, receiver, entries)
    def invoice2 = new Invoice(UUID.randomUUID(), issuerDate, issuer2, receiver, entries)
    def invoice3 = new Invoice(UUID.randomUUID(), issuerDate, issuer, receiver2, entries)
    def invoice4 = new Invoice(UUID.randomUUID(), issuerDate2, issuer, receiver, entries)
    def invoiceUpdated = new Invoice(UUID.randomUUID(), issuerDate, issuerUpdated, receiver, entries)
    def database = new InMemoryDatabase()

    def "should calculate total net value of all invoice entries"() {
        setup:
        database.save(invoice)
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.getTotalNet(invoice)

        then:
        result == 50
    }

    def "should calculate total tax value of all invoice entries"() {
        setup:
        database.save(invoice)
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.getTotalTaxValue(invoice)

        then:
        result == 50
    }

    def "should calculate total gross value of all invoice entries"() {
        setup:
        database.save(invoice)
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.getTotalGross(invoice)

        then:
        result == 100
    }

    def "should filter list of invoices by issuer name"() {
        setup:
        database.save(invoice)
        database.save(invoice)
        database.save(invoice2)
        def invoiceService = new InvoiceService(database)

        when:
        def resultIssuer1 = invoiceService.filterByIssuer("Telnet")
        def resultIssuer2 = invoiceService.filterByIssuer("Nokia")

        then:
        resultIssuer1.size() == 2
        resultIssuer2.size() == 1
    }

    def "should filter list of invoices by receiver name"() {
        setup:
        database.save(invoice)
        database.save(invoice3)
        database.save(invoice3)
        def invoiceService = new InvoiceService(database)

        when:
        def resultReceiver1 = invoiceService.filterByReceiver("TelPlus")
        def resultReceiver2 = invoiceService.filterByReceiver("IntelPlus")

        then:
        resultReceiver1.size() == 1
        resultReceiver2.size() == 2
    }

    def "should filter list of invoices by issue date"() {
        setup:
        database.save(invoice)
        database.save(invoice)
        database.save(invoice4)
        def invoiceService = new InvoiceService(database)

        when:
        def resultDate1 = invoiceService.filterByDate(issuerDate)
        def resultDate2 = invoiceService.filterByDate(issuerDate2)

        then:
        resultDate1.size() == 2
        resultDate2.size() == 1
    }

    def "should save invoice in to database"() {
        setup:
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.save(invoice)

        then:
        database.getById(result.getId()) != null
        database.getById(result.getId()).getIssuer().getName() == "Telnet"
    }

    def "should get invoice from database by Id"() {
        setup:
        database.save(invoice)
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.getById(invoice.getId())

        then:
        result != null
        result.get().getIssuer().getName() == "Telnet"
    }

    def "should get list of all invoices from database"() {
        setup:
        database.save(invoice)
        database.save(invoice2)
        database.save(invoice3)
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.getAll()

        then:
        result.size() == 3
    }

    def "should update invoice in the database"() {
        setup:
        database.save(invoice)
        def invoiceService = new InvoiceService(database)
        invoiceUpdated.setId(invoice.getId())

        when:
        def result = invoiceService.update(invoiceUpdated)

        then:
        database.getById(result.getId()) != null
        database.getById(result.getId()).getIssuer().getName() == "Nokia"
    }

    def "should delete invoice from database"() {
        setup:
        database.save(invoice)
        def invoiceService = new InvoiceService(database)

        when:
        def result = invoiceService.delete(invoice.getId())

        then:
        result
        invoiceService.getAll().size() == 0
    }
}