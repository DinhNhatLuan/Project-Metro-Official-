package Model;

public class Train {
    private String trainID;
    private String trainName;
    private int capacity;

    public Train() {
        super();
    }

    public Train(String trainID, String trainName, int capacity) {
        this.trainID = trainID;
        this.trainName = trainName;
        this.capacity = capacity;
    }

    public String getTrainID() {
        return trainID;
    }

    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}


