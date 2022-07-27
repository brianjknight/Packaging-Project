package com.amazon.ata.service;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    private FulfillmentCenter existentFC  = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");


    @InjectMocks
    ShipmentService shipmentService;

    @Mock
    PackagingDAO packagingDAO;

    @Mock
    CostStrategy costStrategy;

    @BeforeEach void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findShipmentOption_unknownFulfillmentCenterItemFits_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        //GIVEN using small item above that fits a package but with nonExistentFC
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenThrow(UnknownFulfillmentCenterException.class);

        //WHEN THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "When asked to ship from an unknown fulfillment center, throw UnknownFulfillmentCenterException.");
    }

    @Test
    public void findShipmentOption_itemDoesNotFitForKnownFC_returnNonNullShipmentOptionWithNullPackaging() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        //GIVEN
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenThrow(NoPackagingFitsItemException.class);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        //WHEN
        Packaging packaging = shipmentOption.getPackaging();

        //THEN
        assertNotNull(shipmentOption, "Expected returned ShipmentOption to be NOT null.");
        assertNull(packaging, "Expected packaging of the ShipmentOption to be null.");

    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        ShipmentOption shipmentOptionBuild = ShipmentOption.builder()
                .build();
        ShipmentCost shipmentCost = new ShipmentCost(shipmentOptionBuild, BigDecimal.ONE);

        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenReturn(Arrays.asList(shipmentOptionBuild));
        when(costStrategy.getCost(any(ShipmentOption.class))).thenReturn(shipmentCost);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

}