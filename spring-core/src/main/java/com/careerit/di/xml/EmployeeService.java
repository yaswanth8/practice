package com.careerit.di.xml;

public class EmployeeService {

    String[] names={"a","b","c","d"};

    private SearchingTechnique searchingTechnique;

    public EmployeeService(SearchingTechnique searchingTechnique){
        this.searchingTechnique=searchingTechnique;
    }
    public void searchName(String name){

        int index=searchingTechnique.search(names,name);

        if(index==-1){
            System.out.println("Name: "+name+" not found");
        }
        else{
            System.out.println("Name: "+name+" found at index : "+index);
        }

    }
}
