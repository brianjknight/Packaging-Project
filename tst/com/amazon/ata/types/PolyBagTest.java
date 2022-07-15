package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PolyBagTest {
    private BigDecimal volume = new BigDecimal(3600);
    private Packaging packaging;

    @BeforeEach
    public void setup() {
        packaging = new PolyBag(volume);
    }

    @Test
    public void canFitItem_itemWithLessVolumeFits_returnsTrue() {
        //GIVEN
        Item item = Item.builder()
                .withLength(BigDecimal.valueOf(.99999999))
                .withWidth(BigDecimal.valueOf(60))
                .withHeight(BigDecimal.valueOf(60))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertTrue(canFit, "Expected smaller volume item to fit in PolyBag");
    }

    @Test
    public void canFitItem_itemMoreVolumeFits_returnsFalse() {
        //GIVEN
        Item item = Item.builder()
                .withLength(BigDecimal.valueOf(1.000000001))
                .withWidth(BigDecimal.valueOf(60))
                .withHeight(BigDecimal.valueOf(60))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertFalse(canFit, "Expected larger volume item to NOT fit in PolyBag");
    }

     @Test
    public void canFitItem_itemSameVolumeFits_returnsFalse() {
        //GIVEN
        Item item = Item.builder()
                .withLength(BigDecimal.valueOf(1))
                .withWidth(BigDecimal.valueOf(60))
                .withHeight(BigDecimal.valueOf(60))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertFalse(canFit, "Expected same volume item to NOT fit in PolyBag");
    }

    @Test
    public void getMass_returnCorrectMass_returnTrue() {
        //GIVEN
        Packaging newPolyBag = new PolyBag(BigDecimal.valueOf(3600));
        //WHEN

        BigDecimal mass = newPolyBag.getMass();
        BigDecimal expected = BigDecimal.valueOf(36);

        //THEN
        assertEquals(mass, expected, "Expected mass to be equal.");
    }

    @Test
    public void equals_samePolyBag_isTrue() {
        //GIVEN
        Packaging otherPolyBag = new PolyBag(BigDecimal.valueOf(3600));

        //THEN
        boolean result = otherPolyBag.equals(otherPolyBag);

        //WHEN
        assertTrue(result, "Expected item to be equal.");
    }

    @Test
    public void equals_sameVolume_isTrue() {
        //GIVEN
        Packaging otherPolyBag = new PolyBag(BigDecimal.valueOf(3600));

        //THEN
        boolean result = packaging.equals(otherPolyBag);

        //WHEN
        assertTrue(result, "Expected item to be equal.");
    }

    @Test
    public void equals_differentPolyBag_isFalse() {
        //GIVEN
        Packaging otherPolyBag = new PolyBag(BigDecimal.valueOf(99));

        //THEN
        boolean result = packaging.equals(otherPolyBag);

        //WHEN
        assertFalse(result, "Expected item to NOT be equal.");
    }

    @Test
    public void hashcode_sameVolume_isTrue() {
        //GIVEN
        Packaging otherPolyBag = new PolyBag(BigDecimal.valueOf(3600));

        //THEN
        boolean result = packaging.hashCode() == otherPolyBag.hashCode();

        //WHEN
        assertTrue(result, "Expected item to be equal.");
    }

}
