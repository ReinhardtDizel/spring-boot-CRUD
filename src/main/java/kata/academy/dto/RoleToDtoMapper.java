package kata.academy.dto;

import kata.academy.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleToDtoMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDto convertRoleToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public Role convertDtoToRole(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
}
