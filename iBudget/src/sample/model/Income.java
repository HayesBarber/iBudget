package sample.model;

public class Income {

    private int incomeid;
    private double incomeAmount;
    private String incomeDescription;
    private String incomeDate;
    private int userid;

    public Income() {
    }

    public Income(double incomeAmount, String incomeDescription, String incomeDate) {
        this.incomeAmount = incomeAmount;
        this.incomeDescription = incomeDescription;
        this.incomeDate = incomeDate;
    }

    public int getIncomeid() {
        return incomeid;
    }

    public void setIncomeid(int incomeid) {
        this.incomeid = incomeid;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

}
