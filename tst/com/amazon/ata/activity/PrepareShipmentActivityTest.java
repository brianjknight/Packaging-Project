package com.amazon.ata.activity;

import com.amazon.ata.service.ShipmentService;

import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.ShipmentOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PrepareShipmentActivityTest {

    private PrepareShipmentRequest request = PrepareShipmentRequest.builder()
        .withFcCode("fcCode")
        .withItemAsin("itemAsin")
        .withItemDescription("description")
        .withItemLength("10.0")
        .withItemWidth("10.0")
        .withItemHeight("10.0")
        .build();

    @Mock
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handleRequest_noAvailableShipmentOption_returnsNull() throws Exception {
        // GIVEN
        PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService);
        when(shipmentService.findShipmentOption(any(Item.class), any(FulfillmentCenter.class))).thenReturn(null);

        // WHEN
        String response = activity.handleRequest(request, null);
        System.out.println("response first test = " + response);
        // THEN
        assertNull(response);
    }

    @Test
    public void handleRequest_availableShipmentOption_returnsNonEmptyResponse() throws Exception {
        // GIVEN
        // PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService, dataConverter);
        PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService);
        when(shipmentService.findShipmentOption(any(Item.class), any(FulfillmentCenter.class)))
            .thenReturn(ShipmentOption.builder().build());

        // WHEN
        // PrepareShipmentResponse response = activity.handleRequest(request);
        String response = activity.handleRequest(request, null);
        System.out.println("response second test = " + response);

        // THEN
        //assertNotNull(response.getAttributes());
        assertNotNull(response);
    }
}
