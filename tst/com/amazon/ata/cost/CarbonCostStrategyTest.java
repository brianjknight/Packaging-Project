package com.amazon.ata.cost;

import com.amazon.ata.types.Box;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.PolyBag;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarbonCostStrategyTest {
    //Box with mass of 1000
    private static final Packaging BOX_10x10x20 =
            new Box(Material.CORRUGATE, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(20));
    //PolyBag with mass of 30
    private static final Packaging POLYBAG_2500 = new PolyBag(BigDecimal.valueOf(2500));

    private CarbonCostStrategy carbonCostStrategy;

    @BeforeEach
    void setup() {
        carbonCostStrategy = new CarbonCostStrategy();
    }

    @Test
    public void getCost_corrugateMaterial_returnsCorrectCost() {
        //GIVEN
        ShipmentOption shipmentOption = ShipmentOption.builder()
                .withPackaging(BOX_10x10x20)
                .build();

        //THEN
        ShipmentCost shipmentCost = carbonCostStrategy.getCost(shipmentOption);

        //WHEN
        assertTrue(BigDecimal.valueOf(17).compareTo(shipmentCost.getCost()) == 0, "Expected carbon cost to be 17 cu.");
    }

    @Test
    public void getCost_laminatedPlasticMaterial_returnsCorrectCost() {
        //GIVEN
        ShipmentOption option = ShipmentOption.builder()
                .withPackaging(POLYBAG_2500)
                .build();

        //WHEN
        ShipmentCost shipmentCost = carbonCostStrategy.getCost(option);

        //THEN
        assertTrue(BigDecimal.valueOf(.36).compareTo(shipmentCost.getCost()) == 0, "Expected carbon cost to be 0.36 cu.");
    }
}
