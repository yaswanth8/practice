package com.careerit.jfs.properties.ReadingProperties;

public class TenatManager {

    public static void main(String[] args) {
        Tenant tenant=TenantPropertiesUtil.loadTenantDetails();
        System.out.println(tenant);
    }
}
