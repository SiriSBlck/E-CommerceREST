package com.example.demo.security;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public final class IdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object obj) {
        User user = (User) obj;
        String id = user.getRole().equals(Role.ROLE_CLIENT) ? "01" : "02";
        id.concat(user.getName().substring(0, 0));
        return Long.valueOf(id.concat("" + System.currentTimeMillis()));
    }

}
