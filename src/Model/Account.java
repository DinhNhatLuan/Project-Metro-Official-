package Model;


public class Account {
    private String Username, Password, EmpID;
    
    public Account(){
        super();
    }
    public Account(String Username, String Password, String EmpID){
        this.Username=Username;
        this.Password=Password;
        this.EmpID=EmpID;
    }

    // Getter và Setter cho Username
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    // Getter và Setter cho Password
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    // Getter và Setter cho EmpID
    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String EmpID) {
        this.EmpID = EmpID;
    }
}