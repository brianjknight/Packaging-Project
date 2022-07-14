package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a packaging option that is a subclass of Package.
 *
 * Box package has length, width, and height dimensions.
 * Items fit into a box given their dimensions are smaller than the Box dimensions.
 */

public class Box extends Packaging {

    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    /**
     * Instantiates a new object using super constructor for material.
     * @param material - material Box is made from.
     * @param length - middle dimension.
     * @param width - packages shortest dimension.
     * @param height - packages longest dimension.
     */
    public Box(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    /**
     * Returns whether item will fit into the Box object.
     *
     * @param item the item to test fit for.
     * @return whether item will fit into the Box object.
     */
    @Override
        public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
    }

    /**
     * Returns mass of the Box. CORRUGATE boxes weight 1 gram per square centimeter.
     *
     * @return mass of the Box.
     */
    @Override
    public BigDecimal getMass() {
        BigDecimal two = BigDecimal.valueOf(2);

        // For simplicity, we ignore overlapping flaps
        BigDecimal endsArea = length.multiply(width).multiply(two);
        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
        BigDecimal longSidesArea = width.multiply(height).multiply(two);

        return endsArea.add(shortSidesArea).add(longSidesArea);
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
        Box box = (Box) o;
        return Objects.equals(length, box.length) &&
                Objects.equals(width, box.width) &&
                Objects.equals(height, box.height) &&
                Objects.equals(getMaterial(), box.getMaterial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, width, height, getMaterial());
    }
}
