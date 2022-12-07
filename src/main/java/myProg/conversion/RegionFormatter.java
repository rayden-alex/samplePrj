package myProg.conversion;

import myProg.domain.Region;
import myProg.services.RegionService;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class RegionFormatter implements Formatter<Region> {
    private final RegionService regionService;

    public RegionFormatter(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public Region parse(String text, Locale locale) throws ParseException {
        final Short regionId;
        try {
            regionId = Short.valueOf(text);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), 0);
        }

        Optional<Region> region = regionService.findById(regionId);
        return region.orElse(new Region());
    }

    @Override
    public String print(Region region, Locale locale) {
        return (region != null ? region.getId().toString() : "");
    }

}
