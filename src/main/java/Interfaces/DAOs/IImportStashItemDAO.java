package Interfaces.DAOs;

import Models.ImportStashItem;
import java.util.List;

public interface IImportStashItemDAO {

    int addImportStashItem(ImportStashItem importStashItem);

    int updateImportStashItem(ImportStashItem importStashItem);

    int deleteImportStashItem(ImportStashItem importStashItem);

    int deleteAllImportStashItemOfManager(int inventoryManagerId);

    ImportStashItem getImportStashItem(int managerId, int productId);

    List<ImportStashItem> getAllImportStashItemOfManager(int managerId);
}
