package com.careerit.jfs.properties.ReadingProperties;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tenant {
    private String name;
    private String email;
    private String city;
}
