import java.util.function.Supplier;

class Customer {
    private final int customerID;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;

    Customer(int customerID, double arrivalTime, Supplier<Double> serviceTime) {
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    } 

    public double getServiceTime() {
        return this.serviceTime.get();
    }   

    public String toString() {
        return String.format("%.3f customer %d arrives", this.arrivalTime, this.customerID);
    }
}
