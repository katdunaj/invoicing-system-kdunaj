package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.fixtures.InvoiceFixture
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import spock.lang.Specification

import java.time.LocalDate

abstract class DatabaseTest extends Specification {

    abstract Database getDatabaseInstance();

    def issuer = new Company ("112-425-567-89", "ul.Ogrodowa 3 Kampinos", "Telnet", 1000.00, 1000.00)
    def receiver = new Company("112-425-567-69", "ul.Ogrodowa 6 Kampinos", "Netplus", 1000.00, 1000.00)
    def date = LocalDate.of(2017, 7, 21)
    def entries = new ArrayList<InvoiceEntry>()
    def invoice = new Invoice(date, issuer, receiver, entries)
    Database database

    def setup() {
        database = getDatabaseInstance()
    }

    def "should save invoice"() {
        when:
        def result = database.save(invoice)

        then:
        database.getById(result.getId()) != null
        database.getById(result.getId()).getIssuer().getName() == "Telnet"
    }

    def "should get invoice from by id"() {
        setup:
        database.save(invoice)

        when:
        def result = database.getById(invoice.getId())

        then:
        result != null
        result.getIssuer().getName() == "Telnet"
    }

    def "should get list of all invoice "() {
        setup:
        def invoice2 = InvoiceFixture.invoice(1)
        def invoice3 = InvoiceFixture.invoice(3)
        database.save(invoice)
        database.save(invoice2)
        database.save(invoice3)

        when:
        def result = database.getAll()

        then:
        result.size() == 3
    }

    def "should delete invoice"() {
        setup:
        database.save(invoice)

        when:
        def result = database.delete(invoice.getId())

        then:
        result
        database.getAll().size() == 0
    }

    def "should update invoice in the database"() {
        setup:
        database.save(invoice)
        def invoiceUpdated = new Invoice(date, issuer, receiver, entries)
        invoiceUpdated.setId(invoice.getId())

        when:
        def result = database.update(invoiceUpdated)

        then:
        database.getById(result.getId()) != null
        database.getById(result.getId()).getIssuer().getName() == "Telnet"
    }

    def "should delete not existing UUID"() {
        when:
        def result = database.delete(UUID.randomUUID())

        then:
        result

        database.getAll().size() == 0
    }

    def "should remove all invoices form database"() {
        setup:
        def invoice2 = InvoiceFixture.invoice(1)
        def invoice3 = InvoiceFixture.invoice(3)
        database.save(invoice)
        database.save(invoice2)
        database.save(invoice3)

        when:
        def result = database.clear()

        then:
        result
        database.getAll().size() == 0
    }
}
