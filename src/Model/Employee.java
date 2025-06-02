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
    private int EmpID, ManagerID;
    private String Name, Role, Address, Email, PhoneNum, Phase;
    private int Salary;
    private boolean Gender;
    private LocalDate SWDay, BirthDay;

    // Constructor không tham số
    public Employee() {
        super();
    }

    // Constructor có tham số
    public Employee(String Name, String Role, String Address, String Email,
                    String PhoneNum, String Phase, int ManagerID, int Salary,
                    boolean Gender, LocalDate SWDay, LocalDate BirthDay) {
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
        this.BirthDay = BirthDay;
    }
 public Employee(int EmpID,String Name, String Role, String Address, String Email,
                    String PhoneNum, String Phase, int ManagerID, int Salary,
                    boolean Gender, LocalDate SWDay, LocalDate BirthDay) {
        this.EmpID =EmpID;
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
        this.BirthDay = BirthDay;
    }

    // Getters and Setters

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int empID) {
        EmpID = empID;
    }
    
    public int getManagerID() {
        return ManagerID;
    }

    public void setManagerID(int managerID) {
        ManagerID = managerID;
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

    public LocalDate getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        BirthDay = birthDay;
    }
}

