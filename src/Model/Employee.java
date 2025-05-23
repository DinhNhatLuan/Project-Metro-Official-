/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDate;

/**
 *
 * @author capta
 */
public class Employee {
    private String EmpID, Name, Role, Address, Email, PhoneNum, Phase, ManagerID;
    private int Salary;
    private boolean Gender;
    private LocalDate SWDay;

    // Constructor không tham số
    public Employee() {
        super();
    }

    // Constructor có tham số
    public Employee(String EmpID, String Name, String Role, String Address, String Email,
                    String PhoneNum, String Phase, String ManagerID, int Salary,
                    boolean Gender, LocalDate SWDay) {
        this.EmpID = EmpID;
        this.Name = Name;
        this.Role = Role;
        this.Address = Address;
        this.Email = Email;
        this.PhoneNum = PhoneNum;
        this.Phase = Phase;
        this.ManagerID = ManagerID;
        this.Salary = Salary;
        this.Gender = Gender;
        this.SWDay = SWDay;
    }

    // Getters and Setters
    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getPhase() {
        return Phase;
    }

    public void setPhase(String phase) {
        Phase = phase;
    }

    public String getManagerID() {
        return ManagerID;
    }

    public void setManagerID(String managerID) {
        ManagerID = managerID;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public boolean getGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public LocalDate getSWDay() {
        return SWDay;
    }

    public void setSWDay(LocalDate sWDay) {
        SWDay = sWDay;
    }
}

