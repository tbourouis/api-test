package tbo.api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tbo.api.model.UserModel;
import tn.tbo.demo.api.v1.model.User;
import tn.tbo.demo.api.v1.model.UserWithId;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source ="login", target="username")
    @Mapping(ignore = true, target="birthdate")
    UserModel map(UserWithId user);

    @Mapping(source ="login", target="username")
    @Mapping(ignore = true, target="birthdate")
    @Mapping(ignore = true, target="userId")
    @Mapping(ignore = true, target="avatar")
    UserModel map(User user);
    List<UserModel> mapList(List<UserWithId> user);

    @Mapping(target ="type", ignore = true)
    @Mapping(target ="login", source="username")
    UserWithId mapToDto(UserModel user);
    List<UserWithId> mapToDtoList(List<UserModel> user);
}
