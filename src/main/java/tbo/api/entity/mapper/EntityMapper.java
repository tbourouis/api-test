package tbo.api.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tbo.api.entity.UserEntity;
import tbo.api.model.UserModel;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);
    UserModel map(UserEntity user);
    List<UserModel> mapList(List<UserEntity> user);

    UserEntity mapToEntity(UserModel user);
    List<UserEntity> mapToDtoEntity(List<UserModel> user);
}