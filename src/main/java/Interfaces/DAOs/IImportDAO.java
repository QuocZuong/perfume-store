/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.Import;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public interface IImportDAO {

    int addImport(Import ip);

    ArrayList<Import> getAllImport();

    Import getImport(int importId);
}
