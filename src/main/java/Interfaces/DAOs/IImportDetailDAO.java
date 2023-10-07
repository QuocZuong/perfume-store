package Interfaces.DAOs;

import Models.ImportDetail;
import java.util.ArrayList;

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
