package Interfaces.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.DuplicateKeyException;

import Exceptions.CitizenIDDuplicationException;
import Exceptions.EmailDuplicationException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.UsernameDuplicationException;
import Models.Employee;
import Models.User;

public interface IEmployeeDAO {

    boolean isExistCitizen(String citizenID);

    boolean isExistPhoneNumber(String phoneNumber);

    public boolean checkDuplicate(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException;

    List<User> getAll();

    List<Employee> getEmployee();

    Employee getEmployee(int EmployeeId);

    Employee getEmployeeByUserId(int userId);

    int addEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException;

    int updateEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException;
}