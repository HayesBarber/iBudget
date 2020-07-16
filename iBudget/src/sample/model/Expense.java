package sample.model;

public class Expense {

    private int expenseid;
    private double expenseAmount;
    private String expenseDescription;
    private String expenseDate;
    private int userid;

    public Expense() {
    }

    public Expense(double expenseAmount, String expenseDescription, String expenseDate) {
        this.expenseAmount = expenseAmount;
        this.expenseDescription = expenseDescription;
        this.expenseDate = expenseDate;
    }

    public int getExpenseid() {
        return expenseid;
    }

    public void setExpenseid(int expenseid) {
        this.expenseid = expenseid;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

}
