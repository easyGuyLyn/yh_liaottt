package com.nwf.sports.mvp.model;

import com.ivi.imsdk.R;
import com.nwf.sports.utils.PNCheck;

/**
 * Created by Nereus on 2017/5/18.
 */

public class AddBankInput {

    public String bankAccountName;

    public String bankAccountNo;

    public String bankAccountType;

    public String bankCountry;

    public String bankCity;

    public String bankName;

    public String branchName;

    public AddBankInput() {
    }

    public AddBankInput(String bankAccountName, String bankAccountNo, String bankAccountType, String bankCountry, String bankCity, String bankName, String
            branchName) {
        this.bankAccountName = bankAccountName;
        this.bankAccountNo = bankAccountNo;
        this.bankAccountType = bankAccountType;
        this.bankCountry = bankCountry;
        this.bankCity = bankCity;
        this.bankName = bankName;
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "AddBankInput{" +
                "bankAccountName='" + bankAccountName + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountType='" + bankAccountType + '\'' +
                ", bankCountry='" + bankCountry + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }

    public PNCheck.CheckResult check() {
        return PNCheck.collect(PNCheck.checkAccountName(bankAccountName),
                PNCheck.checkAccountNO(bankAccountNo),
                PNCheck.checkNotEmpty(bankName, R.string.str_addbank_bank_name_empty),
                PNCheck.checkNotEmpty(bankAccountType, R.string.str_addbank_bank_type_empty),
                PNCheck.checkNotEmpty(bankCountry, R.string.str_addbank_country_empty),
                PNCheck.checkNotEmpty(bankCity, R.string.str_addbank_city_empty),
                PNCheck.checkAccountSite(branchName));
    }

}
