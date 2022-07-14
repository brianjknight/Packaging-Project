package com.amazon.ata.types;

import java.math.BigDecimal;

public class PolyBag extends Packaging{
    private BigDecimal volume;

    public PolyBag(BigDecimal volume) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = volume;
    }

    @Override
    public boolean canFitItem (Item item) {
        BigDecimal itemVolume = item.getLength().multiply(item.getHeight()).multiply(item.getWidth());
        return  this.volume.compareTo(itemVolume) > 0;
    }

    @Override
    public BigDecimal getMass() {
        double mass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        return new BigDecimal(mass);
    }


}
