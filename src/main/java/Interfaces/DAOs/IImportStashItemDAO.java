/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.ImportStashItem;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public interface IImportStashItemDAO {

    int addImportStashItem(ImportStashItem importStashItem);

    int updateImportStashItem(ImportStashItem importStashItem);

    int deleteImportStashItem(ImportStashItem importStashItem);

    int deleteAllImportStashItemOfManager(int inventoryManagerId);

    ImportStashItem getImportStashItem(int managerId, int productId);

    ArrayList<ImportStashItem> getAllImportStashItemOfManager(int managerId);
}
