package proofit.arina.draftprice.enums;

public enum DiscountType {
    NONE(1),
    CHILDREN(0.5),
    LUGGAGE(0.3);

    private double discount;

    DiscountType(double discount) {
        this.discount = discount;
    }

    public double getDiscount(){
        return discount;
    }
}
