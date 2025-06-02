package Model;

import java.time.LocalDate;

public class CurrentUser {
    public static int EmpID, ManagerID;
    public static String Name, Role, Address, Email, PhoneNum, Phase;
    public static int Salary;
    public static boolean Gender;
    public static LocalDate SWDay, BirthDay;



    // Constructor có tham số
    public void SetCurrentUser(Employee emp) {
        CurrentUser.EmpID = emp.getEmpID();
        CurrentUser.Name = emp.getName();
        CurrentUser.Role = emp.getRole();
        CurrentUser.Address = emp.getAddress();
        CurrentUser.Email = emp.getEmail();
        CurrentUser.PhoneNum = emp.getPhoneNum();
        CurrentUser.Phase = emp.getPhase();
        CurrentUser.ManagerID = emp.getManagerID();
        CurrentUser.Salary = emp.getSalary();
        CurrentUser.Gender = emp.getGender();
        CurrentUser.SWDay = emp.getSWDay();
        CurrentUser.BirthDay = emp.getBirthDay();
    }
}