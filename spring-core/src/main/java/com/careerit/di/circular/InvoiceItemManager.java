package com.careerit.di.circular;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceItemManager {

    private final InvoiceManager invoiceManager;

}
