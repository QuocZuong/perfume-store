/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.ImportDetail;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public interface IImportDetailDAO {

    int addImportDetail(ImportDetail importDetail);

    int addAllImportDetailOfImport(ArrayList<ImportDetail> ArrayImportDetail);

    int updateImportDetail(ImportDetail importDetail);

    int deleteImportDetail(ImportDetail importDetail);

    ImportDetail getImportDetail(int importId, int productId);

    ArrayList<ImportDetail> getAllImportDetailOfImport(int importId);

    int getTotalQuantityImportDetail(ArrayList<ImportDetail> ArrayImportDetail);

    int getTotalCostImportDetail(ArrayList<ImportDetail> ArrayImportDetail);
}
