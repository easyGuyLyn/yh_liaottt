package com.nwf.sports.mvp.model;

/**
 * Created by Nereus on 2017/6/17.
 */
public enum BankAccountType implements GenericEnum {
    DEBIT("DEBIT"),
    SAVINGS("SAVINGS"),
    CREDIT("CREDIT"),
    BITCOIN("BITCOIN");

    private String type;

    public static BankAccountType getEnum(String o) {
        if(null == o) {
            return null;
        } else {
            BankAccountType[] customerTypes = values();
            BankAccountType[] var2 = customerTypes;
            int var3 = customerTypes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                BankAccountType customerType = var2[var4];
                if(customerType.getType().equals(o)) {
                    return customerType;
                }
            }

            return null;
        }
    }

    private BankAccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getCode() {
        return String.valueOf(this.getType());
    }
}
