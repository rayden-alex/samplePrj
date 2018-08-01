package myProg.csv.processors;

import lombok.Getter;
import myProg.csv.Address;
import myProg.csv.entries.AbonEntry;
import myProg.services.CityService;
import myProg.services.CityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AbonEntryProcessor implements EntryProcessor<AbonEntry> {
    @Autowired
    private CityTypeService cityTypeService;

    @Autowired
    private CityService cityService;

    private Map<String, Short> streetType = new HashMap<>();
    private Map<String, Short> cityType = new HashMap<>();
    private Map<StreetWithType, Integer> street = new HashMap<>();
    private Map<CityWithType, Integer> city = new HashMap<>();

    {
        streetType.put("НЕТ ДАННЫХ", (short) 1);
        cityType.put("НЕТ ДАННЫХ", (short) 1);
    }

    @Override
    public void process(AbonEntry entry) {
        if (StringUtils.isEmpty(entry.getAddress())) return;

        Address address = extractAddress(entry);

        if (address.getStreetPref() != null) {
            streetType.putIfAbsent(address.getStreetPref(), (short) (streetType.size() + 1));
        }

        if (address.getCityPref() != null) {
            cityType.putIfAbsent(address.getCityPref(), (short) (cityType.size() + 1));
        }

        if (address.getCityName() != null) {
            CityWithType cityWithType = new CityWithType(address.getCityName(), cityType.getOrDefault(address.getCityPref(), (short) 1));
            city.putIfAbsent(cityWithType, entry.getCityId());
        }

        if (address.getStreetName() != null) {
            StreetWithType streetWithType = new StreetWithType(address.getStreetName(), streetType.getOrDefault(address.getStreetPref(), (short) 1));
            street.putIfAbsent(streetWithType, entry.getStreetId());
        }


    }

    @Override
    public void saveToDB() {
        cityService.deleteAllInBatch();
        cityTypeService.deleteAllInBatch();

        cityTypeService.saveAll(cityType);
        cityService.saveAll(city);
    }

    Address extractAddress(AbonEntry abon) {
        Address address = new Address();
        String adr = abon.getAddress();

        // Убираем известные части из полного адреса
        if (StringUtils.hasLength(abon.getRoom())) {
            adr = removeFromEnd(adr, abon.getRoom());
            adr = removeFromEnd(adr, ", ");
        }

        if (StringUtils.hasLength(abon.getBuilding())) {
            adr = removeFromEnd(adr, abon.getBuilding());
            adr = removeFromEnd(adr, ", ");
        }
        List<String> adrList = commaDelimitedListToStringList(adr);
        // Должны остаться только "город и улица" или "город"
        switch (adrList.size()) {
            case 2:
                String[] street = adrList.get(1).split(" ", 2);
                address.setStreetPref(street[0]);
                address.setStreetName(street[1]);

            case 1:
                String[] city = adrList.get(0).split(" ", 2);
                address.setCityPref(city[0]);
                address.setCityName(city[1]);
                break;

            default:
                throw new RuntimeException("adrList is empty");
        }

        return address;
    }

    private String removeFromEnd(String str, String endStr) {
        int index = str.lastIndexOf(endStr);
        if (index > 0) {
            str = str.substring(0, index);
        }
        return str;
    }

    List<String> commaDelimitedListToStringList(@NonNull String str) {
        final String delimiter = ", ";

        List<String> result = new ArrayList<>();
        int pos = 0;
        int delPos;

        while ((delPos = str.indexOf(delimiter, pos)) != -1) {
            result.add(str.substring(pos, delPos));
            pos = delPos + delimiter.length();
        }

        if (str.length() > 0 && pos <= str.length()) {
            // Add rest of String, but not in case of empty input.
            result.add(str.substring(pos));
        }

        return result;
    }

}

