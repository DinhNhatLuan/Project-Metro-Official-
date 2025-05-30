package Model;

public class Train {
    private int trainID;
    private String trainName;
    private int capacity;

    public Train() {
        super();
    }

    public Train(int trainID, String trainName, int capacity) {
        this.trainID = trainID;
        this.trainName = trainName;
        this.capacity = capacity;
    }

    public int getTrainID() {
        return trainID;
    }

    public void setTrainID(int trainID) {
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


