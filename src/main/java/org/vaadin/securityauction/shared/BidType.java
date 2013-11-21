package org.vaadin.securityauction.shared;

import java.util.Set;

public enum BidType {

    NORMAL(Role.NORMAL), POSTPONED(Role.NORMAL), AUTOBID(Role.POWER);

    private Role requiredRole;

    private BidType(Role requiredRole) {
        this.requiredRole = requiredRole;
    }

    public boolean isAllowedWithRoles(Set<Role> roles) {
        return roles.contains(requiredRole);
    }

}
