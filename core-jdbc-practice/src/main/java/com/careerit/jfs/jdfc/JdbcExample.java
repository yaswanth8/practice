package com.careerit.jfs.jdfc;
import com.careerit.jfs.util.DbConnectionUtil;

import java.sql.*;

public class JdbcExample {

    public static void main(String[] args) {
        showAllEmp();
        showEmpCount();

    }

    private static void showEmpCount() {
    }

    public static void showAllEmp(){
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        try{

            con= DbConnectionUtil.getConnection();
            st=con.createStatement();
            rs= st.executeQuery("select empno,ename,sal from emp");
            while(rs.next()){
                System.out.println(rs.getInt("empno"));
                System.out.println(rs.getString("ename"));
                System.out.println(rs.getDouble("sal"));
                System.out.println("-".repeat(50));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DbConnectionUtil.close(rs,st,con);
        }
    }
    
}
