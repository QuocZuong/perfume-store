package Interfaces.DAOs;

import Models.Import;
import java.util.ArrayList;

public interface IImportDAO {

    int addImport(Import ip);

    ArrayList<Import> getAllImport();

    Import getImport(int importId);
}
