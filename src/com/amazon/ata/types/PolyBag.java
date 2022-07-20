package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *Represents a package option that inherits from Packaging class.
 *
 * PolyBag has one measurement of volume. Material is inherited from Packaging.
 * An item will fit into a PolyBag if the item volume is less than the PolyBag.
 */

public class PolyBag extends Packaging {
    private BigDecimal volume;

    /**
     * Instantiate PolyBag object with provided volume. Calls super constructor always with LAMINATED_PLASTIC material.
     *
     * @param volume capacity of the PolyBag.
     */
    public PolyBag(BigDecimal volume) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * Returns whether item will fit in the PolyBag.
     *
     * @param item the item to test fit for.
     * @return whether item will fit in the PolyBag.
     */
    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemVolume = item.getLength().multiply(item.getHeight()).multiply(item.getWidth());
        return  this.volume.compareTo(itemVolume) > 0;
    }

    /**
     * Returns mass of the Box. PolyBags weigh 0.6 grams per volume^1/2.
     *
     * @return mass of the PolyBag.
     */
    @Override
    public BigDecimal getMass() {
        double mass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        System.out.println("test mass = " + mass);
        return new BigDecimal(mass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PolyBag polyBag = (PolyBag) o;
        return Objects.equals(volume, polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }

}
