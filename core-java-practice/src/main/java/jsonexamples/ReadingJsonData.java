package jsonexamples;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Product{
    private String pid;
    private String pname;
    private int price;
    private String brand;
    private int model;

}
public class ReadingJsonData {
    public static void main(String[] args) throws JsonProcessingException {
        String str= """
                {
                
                    "pid":"P1001",
                    "pname":"Lenovo Thinkpad",
                    "price":95000,
                    "brand":"Lenovo",
                    "model":2024
                }
                """;

        ObjectMapper mapper=new ObjectMapper();

        Product product=mapper.readValue(str,Product.class);
        mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
       // System.out.println(product);

        String jsonStr=mapper.writeValueAsString(product);
        System.out.println(jsonStr);

        List<Product> products=getProducts("products.json");
        for(Product p:products){

            System.out.println(p);
        }

    }

    public static List<Product> getProducts(String fname){
        ObjectMapper objectMapperp=new ObjectMapper();
        List<Product> list=new ArrayList<>();
        try{
            list=objectMapperp.readValue(ReadingJsonData.class.getResourceAsStream("/" + fname),
                    new TypeReference<List<Product>>() {
                    });

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
