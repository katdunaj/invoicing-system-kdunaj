package pl.futurecollars.invoicing

import spock.lang.Specification

class InvoiceApplicationTest extends Specification {
    def "application has a greeting"() {
        setup:
        def app = new InvoiceApplication()

        when:
        def result = app.greeting

        then:
        result != null
    }
}
