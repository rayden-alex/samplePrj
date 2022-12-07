package myProg.dto.mapper;

import myProg.domain.Region;
import myProg.dto.RegionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(RegionDtoMapperTest.TestConfig.class)
// This test works with "Delegate IDE build/run actions to Gradle" options only
// Something wrong with MapStruct annotation processor generated code when option is off
// and Spring couldn't create and find  RegionDtoMapper bean
class RegionDtoMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = RegionDtoMapper.class)
    static class TestConfig {
        // NO code needed here!
        // Only for @Configuration and @ComponentScan annotation used
        //
        // And avoid this error:
        // [AnnotationConfigContextLoaderUtils] - Could not detect default configuration classes
        // for test class [myProg.dto.mapper.RegionDtoMapperTest]:
        // RegionDtoMapperTest does not declare any static, non-private, non-final,
        // nested classes annotated with @Configuration.
    }

    @Autowired
    private RegionDtoMapper dtoMapper;

    @Test
    void RegionToDtoInjectionTest() {
        Region region = new Region();
        region.setId((short) 12);
        region.setName("bla-bla");
        region.setPhonePrefix("123");

        RegionDto dto = dtoMapper.toDto(region);

        assertEquals(region.getId(), dto.getId());
        assertEquals(region.getName(), dto.getName());
        assertEquals(region.getPhonePrefix(), dto.getPhonePrefix());
    }

    @Test
    void RegionToDtoManualCreateTest() {
        Region region = new Region();
        region.setId((short) 12);
        region.setName("bla-bla");
        region.setPhonePrefix("123");

        //RegionDtoMapper manualDtoMapper = Mappers.getMapper(RegionDtoMapper.class);
        RegionDtoMapper manualDtoMapper = RegionDtoMapper.INSTANCE;
        RegionDto dto = manualDtoMapper.toDto(region);

        assertEquals(region.getId(), dto.getId());
        assertEquals(region.getName(), dto.getName());
        assertEquals(region.getPhonePrefix(), dto.getPhonePrefix());
    }

    @Test
    void DtoToRegionInjectionTest() {
        RegionDto dto = new RegionDto();
        dto.setId((short) 12);
        dto.setName("bla-bla");
        dto.setPhonePrefix("123");

        Region region = dtoMapper.fromDto(dto);

        assertEquals(dto.getId(), region.getId());
        assertEquals(dto.getName(), region.getName());
        assertEquals(dto.getPhonePrefix(), region.getPhonePrefix());
    }


}