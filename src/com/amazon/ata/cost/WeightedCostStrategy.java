package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;

public class WeightedCostStrategy implements CostStrategy {

    private CarbonCostStrategy carbonCostStrategy;
    private MonetaryCostStrategy monetaryCostStrategy;
    private BigDecimal carbonCostWeight = BigDecimal.valueOf(0.2);
    private BigDecimal monetaryCostWeight = BigDecimal.valueOf(0.8);

    /**
     * Instantiate WeightedCostStrategy object taking in 2 parameters of MonetaryCostStrategy and CarbonCostStrategy.
     * @param monetaryCostStrategy object used to retrieve the monetary cost
     * @param carbonCostStrategy object used to retrieve the carbon cost
     */
    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy, CarbonCostStrategy carbonCostStrategy) {
        this.carbonCostStrategy = carbonCostStrategy;
        this.monetaryCostStrategy = monetaryCostStrategy;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        ShipmentCost carbonShipmentCost = carbonCostStrategy.getCost(shipmentOption);
        ShipmentCost monetaryShipmentCost = monetaryCostStrategy.getCost(shipmentOption);

        BigDecimal weightedShipmentCost =
                carbonShipmentCost.getCost().multiply(carbonCostWeight)
                        .add(monetaryShipmentCost.getCost().multiply(monetaryCostWeight));

        return new ShipmentCost(shipmentOption, weightedShipmentCost);
    }


}
