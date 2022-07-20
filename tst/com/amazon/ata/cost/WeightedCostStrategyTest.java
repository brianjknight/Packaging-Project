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

public class WeightedCostStrategyTest {
    //Box with mass of 1000
    private static final Packaging BOX_10x10x20 =
            new Box(Material.CORRUGATE, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(20));
    //PolyBag with mass of 27
    private static final Packaging POLYBAG_2025 = new PolyBag(BigDecimal.valueOf(2025));

    private CarbonCostStrategy carbonCostStrategy;
    private MonetaryCostStrategy monetaryCostStrategy;
    private WeightedCostStrategy weightedCostStrategy;

    @BeforeEach
    void setup() {
        carbonCostStrategy = new CarbonCostStrategy();
        monetaryCostStrategy = new MonetaryCostStrategy();
        weightedCostStrategy = new WeightedCostStrategy(monetaryCostStrategy, carbonCostStrategy);
    }

    @Test
    public void getCost_weightedCorrugateCost_returnsCorrectCost() {
        //GIVEN
        ShipmentOption shipmentOption = ShipmentOption.builder()
                .withPackaging(BOX_10x10x20)
                .build();

        //THEN
        ShipmentCost shipmentCost = weightedCostStrategy.getCost(shipmentOption);

        //THEN
        assertTrue(BigDecimal.valueOf(7.7440).compareTo(shipmentCost.getCost()) == 0, "Expected weighted average cost of BOX_10x10x20 to be 7.7440.");
    }

    @Test
    public void getCost_weightedLaminatedPlasticCost_returnsCorrectCost() {
        //GIVEN
        ShipmentOption shipmentOption = ShipmentOption.builder()
                .withPackaging(POLYBAG_2025)
                .build();

        //THEN
        ShipmentCost shipmentCost = weightedCostStrategy.getCost(shipmentOption);

        //THEN
        assertTrue(BigDecimal.valueOf(5.8088).compareTo(shipmentCost.getCost()) == 0, "Expected weighted average cost of POLYBAG_2025 to be 5.8008.");
    }

}
