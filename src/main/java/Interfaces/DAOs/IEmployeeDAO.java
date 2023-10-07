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

    /**
     * The function checks if a citizen with a given ID exists in the User table
     * of a database.
     *
     * @param citizenID The parameter "citizenID" is a String that represents
     * the ID of a citizen.
     * @return The method isExistCitizen returns a boolean value.
     */
    boolean isExistCitizen(String citizenID);

    /**
     * The function checks if a given phone number exists in the "User" table of
     * a database.
     *
     * @param phoneNumber The phoneNumber parameter is a String that represents
     * the phone number to check for existence in the database.
     * @return The method isExistPhoneNumber returns a boolean value. It returns
     * true if a phone number exists in the database table "User" under the
     * column "Employee_Phone_Number", and false otherwise.
     */
    boolean isExistPhoneNumber(String phoneNumber);

    /**
     * The function checks for duplicate values in the employee object's
     * username, email, phone number, and citizen ID, and throws specific
     * exceptions if any duplicates are found.
     *
     * @param employee The employee object that needs to be checked for
     * duplicate values.
     * @return The method is returning a boolean value.
     */
    public boolean checkDuplicate(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException;

    /**
     * The function retrieves all employees from the database and returns them
     * as a list of User objects.
     *
     * @return The method is returning a List of User objects.
     */
    List<User> getAll();

    /**
     * The function retrieves a list of employees from a database table and
     * returns it.
     *
     * @return The method is returning a List of Employee objects.
     */
    List<Employee> getEmployee();

    /**
     * The function retrieves an employee from the database based on their
     * employee ID.
     *
     * @param EmployeeId The EmployeeId parameter is an integer that represents
     * the unique identifier of an employee in the database.
     * @return The method is returning an Employee object.
     */
    Employee getEmployee(int EmployeeId);

    /**
     * The function retrieves an employee from the database based on the given
     * user ID.
     *
     * @param userId The `userId` parameter is an integer that represents the
     * unique identifier of a user.
     * @return The method is returning an Employee object.
     */
    Employee getEmployeeByUserId(int userId);

    /**
     * The function adds an employee to the database if there are no
     * duplications in the employee's username, email, phone number, or citizen
     * ID.
     *
     * @param employee The "employee" parameter is an object of the Employee
     * class. It contains information about an employee such as their user ID,
     * citizen ID, date of birth, phone number, address, role, join date, and
     * retire date.
     * @return The method is returning an integer value.
     */
    int addEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException;

    /**
     * The function updates an employee's information in the database if there
     * are no duplication issues with the provided data.
     *
     * @param employee The parameter "employee" is an object of the class
     * "Employee". It contains information about an employee such as their
     * citizen ID, date of birth, phone number, address, role, join date, retire
     * date, and employee ID.
     * @return The method is returning an integer value.
     */
    int updateEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException;
}
