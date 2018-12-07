package myProg.csv.processors;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import myProg.csv.dto.Address;
import myProg.csv.dto.CityWithType;
import myProg.csv.dto.StreetWithType;
import myProg.csv.entries.AbonEntry;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy
@Slf4j
@Getter
public class AbonEntryProcessor implements EntryProcessor<AbonEntry> {
    private Map<String, Short> streetTypes = new HashMap<>();
    private Map<String, Short> cityTypes = new HashMap<>();
    private Map<StreetWithType, Integer> streets = new HashMap<>();
    private Map<CityWithType, Integer> cities = new HashMap<>();

    {
        streetTypes.put("НЕТ ДАННЫХ", (short) 1);
        cityTypes.put("НЕТ ДАННЫХ", (short) 1);
    }

    @Override
    public void process(AbonEntry entry) {
        if (StringUtils.isEmpty(entry.getAddress())) return;

        Address address = extractAddress(entry);

        if (address.getStreetPref() != null) {
            streetTypes.putIfAbsent(address.getStreetPref(), (short) (streetTypes.size() + 1));
        }

        if (address.getCityPref() != null) {
            cityTypes.putIfAbsent(address.getCityPref(), (short) (cityTypes.size() + 1));
        }

        if (address.getCityName() != null) {
            CityWithType cityWithType = new CityWithType(address.getCityName(), cityTypes.getOrDefault(address.getCityPref(), (short) 1));
            cities.putIfAbsent(cityWithType, entry.getCityId());
        }

        if (address.getStreetName() != null) {
            StreetWithType streetWithType = new StreetWithType(address.getStreetName(), streetTypes.getOrDefault(address.getStreetPref(), (short) 1));
            streets.putIfAbsent(streetWithType, entry.getStreetId());
        }

    }

    Address extractAddress(AbonEntry abon) {
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
        // Должны остаться только "город и улица" или "город"
        List<String> adrList = commaDelimitedStringToList(adr);
        Address address = new Address();
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

    List<String> commaDelimitedStringToList(@NonNull String str) {
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

