package myProg.csv.processors;

import myProg.csv.dto.Address;
import myProg.csv.entries.AbonEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AbonEntryProcessorTest {
    private AbonEntryProcessor processor;
    private AbonEntry abon;

    @BeforeEach
    void setUp() {
        processor = new AbonEntryProcessor();
        abon = new AbonEntry();
    }

    @Test
    void extractAddressTest() {
        abon.setAddress("г. Витебск, пр-т Победы, д.21 корп.2, кв.96");
        abon.setBuilding("д.21 корп.2");
        abon.setRoom("кв.96");

        var address = processor.extractAddress(abon);

        assertEquals("г.", address.getCityPref());
        assertEquals("Витебск", address.getCityName());
        assertEquals("пр-т", address.getStreetPref());
        assertEquals("Победы", address.getStreetName());
    }

    @Test
    void extractAddressTest2() {
        abon.setAddress("г. Витебск, д.21 корп.2, кв.96");
        abon.setBuilding("д.21 корп.2");
        abon.setRoom("кв.96");

        Address address = processor.extractAddress(abon);

        assertEquals("г.", address.getCityPref());
        assertEquals("Витебск", address.getCityName());
        assertNull(address.getStreetPref());
        assertNull(address.getStreetName());
    }

    @Test
    void extractAddressTest3() {
        abon.setAddress("г. Витебск");

        Address address = processor.extractAddress(abon);

        assertEquals("г.", address.getCityPref());
        assertEquals("Витебск", address.getCityName());
        assertNull(address.getStreetPref());
        assertNull(address.getStreetName());

    }

    @Test
    void extractAddressTest4() {
        abon.setAddress("пгт. Руба, ул. Гралевская, д.1 корп.2, кв.25,,б,,");
        abon.setBuilding("д.1 корп.2");
        abon.setRoom("кв.25,,б,,");

        Address address = processor.extractAddress(abon);

        assertEquals("пгт.", address.getCityPref());
        assertEquals("Руба", address.getCityName());
        assertEquals("ул.", address.getStreetPref());
        assertEquals("Гралевская", address.getStreetName());
    }

    @Test
    void commaDelimitedListToStringListTest() {
        List<String> actList = processor.commaDelimitedStringToList("г. Витебск, пр-т Победы, д.21 корп.2, кв.96");
        List<String> expList = Arrays.asList("г. Витебск", "пр-т Победы", "д.21 корп.2", "кв.96");
        assertThat(actList, is(expList));
    }

}