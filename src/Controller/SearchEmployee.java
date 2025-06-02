/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.EmployeeDAO;
import Model.Employee;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class SearchEmployee {
    public static SearchEmployee getInstance() {
        return new SearchEmployee();
    }

    public ArrayList<Employee> searchTatCaAcc(String text) {
        ArrayList<Employee> result = new ArrayList<>();
        ArrayList<Employee> armt = EmployeeDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (String.valueOf(ncc.getEmpID()).toLowerCase().contains(text.toLowerCase())
                    || ncc.getName().toLowerCase().contains(text.toLowerCase())
                    || ncc.getRole().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<Employee> searchFullName(String text) {
        ArrayList<Employee> result = new ArrayList<>();
        ArrayList<Employee> armt = EmployeeDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getName().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<Employee> searchEmpID(String text) {
        ArrayList<Employee> result = new ArrayList<>();
        ArrayList<Employee> armt = EmployeeDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (String.valueOf(ncc.getEmpID()).toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<Employee> searchRole(String text) {
        ArrayList<Employee> result = new ArrayList<>();
        ArrayList<Employee> armt = EmployeeDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getRole().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }
}
