package com.package1.householdledger3;

public class ArrayObject {

    String expensePlace;
    int expenseMoney;
    boolean isSelected = false;
    String strDate;

    ArrayObject( ) {}//default constructor
    public ArrayObject(String expensePlace, int expenseMoney, String mDate) {
        this.expensePlace = expensePlace;
        this.expenseMoney = expenseMoney;
        this.strDate = mDate;
    }

    public ArrayObject(String expensePlace, int expenseMoney, boolean isSelected) {
        this.expensePlace = expensePlace;
        this.expenseMoney = expenseMoney;
        this.isSelected = isSelected;
    }

    public String getExpensePlace() {
        return expensePlace;
    }

    public void setExpensePlace(String expensePlace) {
        this.expensePlace = expensePlace;
    }

    public int getExpenseMoney() {
        return expenseMoney;
    }

    public void setExpenseMoney(int expenseMoney) {
        this.expenseMoney = expenseMoney;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public boolean isSelected() {
        return isSelected;
    }//체크돼있는지 안돼있는지 확인 게터

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }//체크 세터



}
