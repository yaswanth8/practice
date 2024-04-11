package com.careerit.jfs.cbook;

import com.careerit.jfs.util.DbConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements  ContactDao{

    private final String ADD_CONTACT="insert into contact(name,email,mobile)values(?,?,?)";
    private final String SELECT_CONTACT="select id,name,email,mobile from contact where id=?";

    private  final String UPDATE_EMAIL="update contact set email= ? where id= ?";
    private  final String UPDATE_MOBILE="update contact set mobile=? where id=?";
    private  final String DELETE_CONTACT="delete from contact where id=?";
    private  final String SELECT_ALL_CONTACTS="select * from contact";
    private  final String SEARCH_CONTACT="select * from contact where name ilike ? or email ilike ? or mobile ilike ?";


    @Override
    public long insertContact(Contact contact) {
        Connection con=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        long id=0;
        try{
            con= DbConnectionUtil.getConnection();
            pst=con.prepareStatement(ADD_CONTACT, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, contact.getName());
            pst.setString(2, contact.getEmail());
            pst.setString(3, contact.getMobile());
            pst.executeUpdate();
             rs=pst.getGeneratedKeys();
            if(rs.next()){
                id=rs.getInt(1);
            }

        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        } finally {
            DbConnectionUtil.close(rs,pst,con);
        }
        return id;
    }

    @Override
    public Contact selectContact(long cid) {

        Connection con=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        Contact contact=null;
        try{

            con= DbConnectionUtil.getConnection();
            pst=con.prepareStatement(SELECT_CONTACT);
            pst.setLong(1,cid);
            rs = pst.executeQuery();
            if(rs.next()){
                contact = new Contact();
                contact.setCid(rs.getLong(1));
                contact.setName(rs.getString(2));
                contact.setEmail(rs.getString(3));
                contact.setMobile(rs.getString(4));
            }

        }catch(SQLException e){
            System.out.println(" Error : "+e.getMessage());
        }finally {
            DbConnectionUtil.close(rs,pst,con);
        }

        return contact;
    }

    @Override
    public Contact updateEmail(long cid, String email) {

        Connection con=null;
        PreparedStatement pst=null;
        Contact contact=null;
        try{
            con = DbConnectionUtil.getConnection();
            pst= con.prepareStatement(UPDATE_EMAIL);
            pst.setString(1,email);
            pst.setLong(2,cid);
            pst.executeUpdate();
            contact = selectContact(cid);
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }finally {
            DbConnectionUtil.close(pst,con);
        }
        return contact;
    }

    @Override
    public Contact updateMobile(long cid, String mobile) {
        Connection con=null;
        PreparedStatement pst=null;
        Contact contact=null;
        try{
            con = DbConnectionUtil.getConnection();
            pst= con.prepareStatement(UPDATE_MOBILE);
            pst.setString(1,mobile);
            pst.setLong(2,cid);
            pst.executeUpdate();
            contact = selectContact(cid);
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }finally {
            DbConnectionUtil.close(pst,con);
        }
        return contact;
    }

    @Override
    public boolean deleteContact(long cid) {
        Connection con=null;
        PreparedStatement pst=null;
        boolean isDeleted = false;
        try{
            con = DbConnectionUtil.getConnection();
            pst= con.prepareStatement(DELETE_CONTACT);
            pst.setLong(1,cid);
            isDeleted = pst.executeUpdate()==1;

        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }finally {
            DbConnectionUtil.close(pst,con);
        }
        return isDeleted;


    }

    @Override
    public List<Contact> selectContacts() {
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        List<Contact> contacts=new ArrayList<>();
        try{

            con= DbConnectionUtil.getConnection();
            st=con.createStatement();
            rs = st.executeQuery(SELECT_ALL_CONTACTS);
            if(rs.next()){
                Contact contact = new Contact();
                contact.setCid(rs.getLong(1));
                contact.setName(rs.getString(2));
                contact.setEmail(rs.getString(3));
                contact.setMobile(rs.getString(4));
                contacts.add(contact);
            }

        }catch(SQLException e){
            System.out.println(" Error : "+e.getMessage());
        }finally {
            DbConnectionUtil.close(rs,st,con);
        }

        return contacts;
    }

    @Override
    public List<Contact> search(String str) {
        Connection con=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        List<Contact> contacts=new ArrayList<>();
        try{

            con= DbConnectionUtil.getConnection();
            pst=con.prepareStatement(SEARCH_CONTACT);
            pst.setString(1,"%"+str+"%");
            pst.setString(2,"%"+str+"%");
            pst.setString(3,"%"+str+"%");
            rs = pst.executeQuery();
            if(rs.next()){
                Contact contact = new Contact();
                contact.setCid(rs.getLong(1));
                contact.setName(rs.getString(2));
                contact.setEmail(rs.getString(3));
                contact.setMobile(rs.getString(4));
                contacts.add(contact);
            }

        }catch(SQLException e){
            System.out.println(" Error : "+e.getMessage());
        }finally {
            DbConnectionUtil.close(rs,pst,con);
        }

        return contacts;
    }

    public boolean isContactExists(long cid){
        return selectContact(cid)!=null;
    }

}
