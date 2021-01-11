package com.shop.shopapp.services.dataTransferObjects.requests;

import com.shop.shopapp.infrastructure.entities.RoleName;

public class AddRoleRequest {
    private RoleName roleName;
    private long userId;

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public long getUserId() {
        return userId;
    }
}
