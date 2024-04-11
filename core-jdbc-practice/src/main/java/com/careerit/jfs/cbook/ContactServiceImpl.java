package com.careerit.jfs.cbook;

import java.util.List;

public class ContactServiceImpl implements ContactService{

    private ContactDao contactDao= new ContactDaoImpl();
    @Override
    public long addContact(Contact contact) {
        if(contact==null|| contact.getName()==null||contact.getName().isBlank() ||
        contact.getEmail()==null|| contact.getEmail().isBlank() || contact.getMobile()==null || contact.getMobile().isBlank()){
            throw new IllegalArgumentException("Contact can't be added");
        }
        return contactDao.insertContact(contact);
    }

    @Override
    public Contact getContact(long cid) {
        return contactDao.selectContact(cid);
    }

    @Override
    public Contact updateEmail(long cid, String email) {

        return contactDao.updateEmail(cid,email);
    }

    @Override
    public Contact updateMobile(long cid, String mobile) {
        return contactDao.updateMobile(cid,mobile);
    }

    @Override
    public boolean deleteContact(long cid) {
        return contactDao.deleteContact(cid);
    }

    @Override
    public List<Contact> getAllContact() {
        return contactDao.selectContacts();
    }

    @Override
    public List<Contact> search(String str) {
        return contactDao.search(str);
    }
}
