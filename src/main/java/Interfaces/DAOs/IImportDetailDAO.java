package Interfaces.DAOs;

import Models.ImportDetail;
import java.util.List;

public interface IImportDetailDAO {

    public enum Status {
        Wait,
        Used;
    }

    int addImportDetail(ImportDetail importDetail);

    int addAllImportDetailOfImport(List<ImportDetail> ArrayImportDetail);

    int updateImportDetail(ImportDetail importDetail);

    int deleteImportDetail(ImportDetail importDetail);

    int deleteImportDetailOfImport(int importId);

    ImportDetail getImportDetail(int importId, int productId);

    List<ImportDetail> getAllImportDetailOfImport(int importId);

    int getTotalQuantityImportDetail(List<ImportDetail> ArrayImportDetail);

    int getTotalCostImportDetail(List<ImportDetail> ArrayImportDetail);
}
