package com.shop.shopapp.infrastructure.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "roles")
public class Role extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    public Role(){

    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
