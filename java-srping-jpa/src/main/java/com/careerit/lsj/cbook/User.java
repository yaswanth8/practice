package com.careerit.lsj.cbook;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="user_details")
@Getter
@Setter
public class User extends BaseEntity{

    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private boolean deleted;
    @Column(name="created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist(){
        this.id=UUID.randomUUID();
        super.onPrePersist();
    }


}
