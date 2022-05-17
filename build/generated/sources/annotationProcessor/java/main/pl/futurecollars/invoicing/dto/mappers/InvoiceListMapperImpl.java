package pl.futurecollars.invoicing.dto.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.futurecollars.invoicing.dto.InvoiceListDto;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-05T09:35:56+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Eclipse Adoptium)"
)
@Component
public class InvoiceListMapperImpl implements InvoiceListMapper {

    @Override
    public InvoiceListDto toDto(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceListDto invoiceListDto = new InvoiceListDto();

        invoiceListDto.setReceiver( invoiceReceiverTaxIdentificationNumber( invoice ) );
        invoiceListDto.setIssuer( invoiceIssuerTaxIdentificationNumber( invoice ) );
        invoiceListDto.setDate( invoice.getDate() );
        invoiceListDto.setId( invoice.getId() );

        return invoiceListDto;
    }

    private String invoiceReceiverTaxIdentificationNumber(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Company receiver = invoice.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        String taxIdentificationNumber = receiver.getTaxIdentificationNumber();
        if ( taxIdentificationNumber == null ) {
            return null;
        }
        return taxIdentificationNumber;
    }

    private String invoiceIssuerTaxIdentificationNumber(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Company issuer = invoice.getIssuer();
        if ( issuer == null ) {
            return null;
        }
        String taxIdentificationNumber = issuer.getTaxIdentificationNumber();
        if ( taxIdentificationNumber == null ) {
            return null;
        }
        return taxIdentificationNumber;
    }
}
