package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
    private Material packagingMaterial = Material.CORRUGATE;
    private BigDecimal packagingLength = BigDecimal.valueOf(10);
    private BigDecimal packagingWidth = BigDecimal.valueOf(10);
    private BigDecimal packagingHeight = BigDecimal.valueOf(20);

    private Packaging packaging;

    @BeforeEach
    public void setup() {
        packaging = new Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);
    }

    @Test
    public void canFitItem_itemDimensionsLessThanBox_assertsTrue() {
        //GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.subtract(BigDecimal.ONE))
                .withWidth(packagingWidth.subtract(BigDecimal.ONE))
                .withHeight(packagingHeight.subtract(BigDecimal.ONE))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertTrue(canFit, "Expected item smaller than box to fit.");
    }

    @Test
    public void canFitItem_itemLengthLargerThanBox_assertsFalse() {
        //GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.add(BigDecimal.ONE))
                .withWidth(packagingWidth.subtract(BigDecimal.ONE))
                .withHeight(packagingHeight.subtract(BigDecimal.ONE))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertFalse(canFit, "Expected item with longer length to NOT fit in the box.");
    }

    @Test
    public void canFitItem_itemWidthLargerThanBox_assertsFalse() {
        //GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.subtract(BigDecimal.ONE))
                .withWidth(packagingWidth.add(BigDecimal.ONE))
                .withHeight(packagingHeight.subtract(BigDecimal.ONE))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertFalse(canFit, "Expected item with longer width to NOT fit in the box.");
    }

    @Test
    public void canFitItem_itemHeightLargerThanBox_assertsFalse() {
        //GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.subtract(BigDecimal.ONE))
                .withWidth(packagingWidth.subtract(BigDecimal.ONE))
                .withHeight(packagingHeight.add(BigDecimal.ONE))
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertFalse(canFit, "Expected item with longer height to NOT fit in the box.");
    }

    @Test
    public void canFitItem_sameDimensionsAsBox_assertsFalse() {
        //GIVEN
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth)
                .withHeight(packagingHeight)
                .build();

        //WHEN
        boolean canFit = packaging.canFitItem(item);

        //THEN
        assertFalse(canFit, "Expected item with same dimensions as box to NOT fit in the box.");
    }

    @Test
    public void getMass_returnCorrectMass_assertEquals() {
        //GIVEN
        Material material = Material.CORRUGATE;
        BigDecimal length = BigDecimal.valueOf(1);
        BigDecimal width = BigDecimal.valueOf(1);
        BigDecimal height = BigDecimal.valueOf(1);
        Packaging newBox = new Box(material, length, width, height);

        //WHEN

        BigDecimal mass = newBox.getMass();

        //THEN
        assertEquals(BigDecimal.valueOf(6), mass, "Mass not equal to expected result.");
    }

    @Test
    public void equals_sameAttributes_isTrue() {
        //GIVEN
        Packaging otherPackaging = new Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);

        //WHEN
        boolean result = packaging.equals(otherPackaging);

        //THEN
        assertTrue(result, "Expected objects to be equal.");
    }

    @Test
    public void equals_sameObjects_isTrue() {
        //GIVEN
        Packaging otherPackaging = new Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);

        //WHEN
        boolean result = otherPackaging.equals(otherPackaging);

        //THEN
        assertTrue(result, "Expected objects to be equal.");
    }

    @Test
    public void equals_differentClass_isFalse() {
        //GIVEN
        Packaging boxPackaging = new Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);
        Packaging polybagPackaging = new PolyBag(BigDecimal.ONE);
        //WHEN
        boolean result = boxPackaging.equals(polybagPackaging);
        //THEN
        assertFalse(result, "Expected Box & PolyBag to NOT be equal.");
    }

    @Test
    public void equals_nullObject_isFalse() {
        //GIVEN
        Packaging nullPackaging = null;

        //WHEN
        boolean result = packaging.equals(nullPackaging);

        //THEN
        assertFalse(result, "Expected null object to NOT equal non-null object.");
    }

    @Test
    public void equals_differentLength_returnsFalse() {
        //GIVEN
        Packaging otherPackaging = new Box(packagingMaterial, packagingLength.add(BigDecimal.ONE), packagingWidth, packagingHeight);

        //WHEN
        boolean result = packaging.equals(otherPackaging);

        //THEN
        assertFalse(result, "Expected boxes to NOT be equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        Packaging otherPackaging = new Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);

        //WHEN
        boolean result = packaging.hashCode() == otherPackaging.hashCode();

        //THEN
        assertTrue(result, "Expected hash codes to be equal.");

    }

}
