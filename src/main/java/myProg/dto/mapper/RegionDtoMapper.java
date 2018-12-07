package myProg.dto.mapper;

import myProg.domain.Region;
import myProg.dto.RegionDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring"/*, injectionStrategy = InjectionStrategy.FIELD*/)
public interface RegionDtoMapper {

    RegionDtoMapper INSTANCE = Mappers.getMapper(RegionDtoMapper.class);

    @Mapping(source = "phonePrefix", target = "phonePrefix")
    RegionDto toDto(Region entity);

    @InheritInverseConfiguration
    Region fromDto(RegionDto dto);
}