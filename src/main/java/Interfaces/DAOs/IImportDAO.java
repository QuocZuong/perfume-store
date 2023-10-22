package Interfaces.DAOs;

import Models.Import;
import java.util.ArrayList;
import java.util.List;

public interface IImportDAO {

    int addImport(Import ip);

    List<Import> getAllImport();

    Import getImport(int importId);
}
