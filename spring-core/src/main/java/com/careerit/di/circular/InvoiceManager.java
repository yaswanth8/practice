package com.careerit.di.circular;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceManager {

    private final AddressManager addressManager;
    private final ContactManager contactManager;
    private final InvoiceItemManager invoiceItemManager;
}
