package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.fixtures.InvoiceFixture
import spock.lang.Shared
import spock.lang.Specification

class TaxCalculatorServiceTest extends Specification {

    private Database database = Mock()

    private TaxCalculatorService taxCalculatorService = new TaxCalculatorService(database)

    @Shared
    def invoice = InvoiceFixture.invoice(1)

    def invoice1 = InvoiceFixture.invoice(2)

    def invoiceGasoline = InvoiceFixture.invoiceWithGasoline(2)

    def setup() {
        database.getAll() >> [invoice, invoice1, invoiceGasoline]
    }

    def "should calculate income for company(2)"() {
        when:
        def result = taxCalculatorService.income(invoice1.getIssuer().getTaxIdentificationNumber())
        then:
        result == 900
    }

    def "should calculate cost for company(2)"() {
        when:
        def result = taxCalculatorService.costs(invoice.getReceiver().getTaxIdentificationNumber())
        then:
        result == 600
    }

    def "should calculate incoming VAT for company(2)"() {
        when:
        def result = taxCalculatorService.incomingVat(invoice1.getIssuer().getTaxIdentificationNumber())
        then:
        result == 369
    }

    def "should calculate outgoing VAT for company(2)"() {
        when:
        def result = taxCalculatorService.outgoingVat(invoice.getReceiver().getTaxIdentificationNumber())
        then:
        result == 369
    }

    def "should calculate earnings for company(2)"() {
        when:
        def result = taxCalculatorService.earnings(invoice1.getIssuer().getTaxIdentificationNumber())
        then:
        result == 300
    }

    def "should calculate VAT to pay for company(2)"() {
        when:
        def result = taxCalculatorService.vatToPay(invoice1.getIssuer().getTaxIdentificationNumber())
        then:
        result == 0
    }

    def "should generate tax Report for company(2)"() {
        when:
        def result = taxCalculatorService.taxReport(invoice1.getIssuer())
        then:
        result.getIncome() == 900
        result.getCosts() == 600
        result.getIncomingVat() == 369
        result.getOutgoingVat() == 369.00
        result.getEarnings() == 300
        result.getVatToPay() == 0
        result.getIncome() == 1701
        result.getCosts() == 611.62
        result.getIncomingVat() == 391.23
        result.getOutgoingVat() == 126.39
        result.getIncomeMinusCosts() == 1089.38
        result.getVatToPay() == 264.84
        result.getPensionInsurance() == 500.97
        result.getIncomeMinusCostsMinusPensionInsurance() == 588.41
        result.getTaxCalculationBase() == 588
        result.getIncomeTax() == 111.72
        result.getHealthInsurance9() == 90
        result.getHealthInsurance775() == 80
        result.getIncomeTaxMinusHealthInsurance() == 31.72
        result.getFinalIncomeTaxValue() == 31
    }
}
