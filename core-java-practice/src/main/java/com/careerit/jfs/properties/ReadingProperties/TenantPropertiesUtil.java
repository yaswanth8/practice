package com.careerit.jfs.properties.ReadingProperties;

import java.util.List;
import java.util.Properties;

public class TenantPropertiesUtil {

    public static Tenant loadTenantDetails(){
        Properties properties=new Properties();
        try{
            properties.load(TenantPropertiesUtil.class.getResourceAsStream("/tenant.properties"));;

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error while loading the tenant "+e);
        }

        Tenant tenant=new Tenant();
        tenant.setName(properties.getProperty("tenant.name"));
        tenant.setEmail(properties.getProperty("tenant.email"));
        tenant.setCity(properties.getProperty("tenant.city"));

        return tenant;
    }

    public static List<Tenant> loadAllTenants(){

        Properties properties=new Properties();

        try{
            properties.load(TenantPropertiesUtil.class.getResourceAsStream("/dp.properties"));

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
