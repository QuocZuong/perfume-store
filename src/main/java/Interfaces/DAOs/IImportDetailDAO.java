package Interfaces.DAOs;

import Models.ImportDetail;
import java.util.ArrayList;

public interface IImportDetailDAO {

    public enum Status {
        Wait,
        Used;
    }

    int addImportDetail(ImportDetail importDetail);

    int addAllImportDetailOfImport(ArrayList<ImportDetail> ArrayImportDetail);

    int updateImportDetail(ImportDetail importDetail);

    int deleteImportDetail(ImportDetail importDetail);

    int deleteImportDetailOfImport(int importId);

    ImportDetail getImportDetail(int importId, int productId);

    ArrayList<ImportDetail> getAllImportDetailOfImport(int importId);

    int getTotalQuantityImportDetail(ArrayList<ImportDetail> ArrayImportDetail);

    int getTotalCostImportDetail(ArrayList<ImportDetail> ArrayImportDetail);
}
