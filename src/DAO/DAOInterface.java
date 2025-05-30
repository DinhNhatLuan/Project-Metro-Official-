/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.ArrayList;
/**
 *
 * @author capta
 */
public interface DAOInterface<T> {
    public int insert(T m);
    public int update(T m);
    public int delete(T m);
    public T selectbyId(int id, String m);
    public ArrayList<T> selectAll();
}
