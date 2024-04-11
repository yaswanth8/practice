package com.careerit.jfs.cbook;

import java.util.List;

public interface ContactService {

    long addContact(Contact contact);
    Contact getContact(long cid);
    Contact updateEmail(long cid,String email);
    Contact updateMobile(long cid,String mobile);
    boolean deleteContact(long cid);
    List<Contact> getAllContact();
    List<Contact> search(String str);

}
