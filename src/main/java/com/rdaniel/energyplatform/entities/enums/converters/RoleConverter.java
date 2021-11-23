package com.rdaniel.energyplatform.entities.enums.converters;

import com.rdaniel.energyplatform.entities.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if(role == null) {
            return null;
        }

        return role.getLabel();
    }

    public Role convertToEntityAttribute(String roleLabel) {
        System.out.println("role " + roleLabel);
        if(roleLabel == null) {
            return null;
        }

//        Role[] roles = Role.values();
//        for(Role role: roles) {
//            if(role.getLabel().equals(roleLabel)) {
//                return role;
//            }
//        }

        return Stream.of(Role.values())
                .filter(c -> c.getLabel().equals(roleLabel))
                .findFirst()
                .orElse(null);
    }
}
