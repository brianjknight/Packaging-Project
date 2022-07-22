package com.amazon.ata.service;

import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC;
    private FulfillmentCenter nonExistentFC;

    private ShipmentService shipmentService;

    @BeforeEach void setup() {
        existentFC = new FulfillmentCenter("ABE2");
        nonExistentFC = new FulfillmentCenter("NonExistentFC");
        shipmentService = new ShipmentService(new PackagingDAO(new PackagingDatastore()),
                new MonetaryCostStrategy());
    }

    @Test
    public void findShipmentOption_unknownFulfillmentCenterItemFits_throwsRuntimeException() {
        //GIVEN using small item above that fits a package but with nonExistentFC

        //WHEN THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "When asked to ship from an unknown fulfillment center, throw UnknownFulfillmentCenterException.");
    }

    @Test
    public void findShipmentOption_itemDoesNotFitForKnownFC_returnNonNullShipmentOptionWithNullPackaging() {
        //GIVEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        //WHEN
        Packaging packaging = shipmentOption.getPackaging();

        //THEN
        assertNotNull(shipmentOption, "Expected returned ShipmentOption to be NOT null.");
        assertNull(packaging, "Expected packaging of the ShipmentOption to be null.");

    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() {
        // GIVEN & WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

//    @Test
//    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption() {
//        // GIVEN & WHEN
//        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);
//
//        // THEN
//        assertNull(shipmentOption);
//    }

//    @Test
//    void findBestShipmentOption_nonExistentFCAndItemCanFit_returnsShipmentOption() {
//        // GIVEN & WHEN
//        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, nonExistentFC);
//
//        // THEN
//        assertNull(shipmentOption);
//    }

//    @Test
//    void findBestShipmentOption_nonExistentFCAndItemCannotFit_returnsShipmentOption() {
//        // GIVEN & WHEN
//        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, nonExistentFC);
//
//        // THEN
//        assertNull(shipmentOption);
//    }


}