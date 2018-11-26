package homework;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class Executor {

    private static final String FILE_PATH = "C:\\Users\\PC\\Downloads\\Sirma\\src\\main\\resources\\text.txt";

    public static void main(String[] args) throws IOException, InterruptedException {

        File file = new File(FILE_PATH);
        List<Employee> employeeList = new LinkedList<>();

        openAndWrite(file, employeeList);

        List<EmployeesAndDays> empsAndDays = employeesToEmployeesAndDays(employeeList);

        List<EmployeesAndDays> employeesAndDaysList = new ArrayList<>(empsAndDays);

        List<EmployeesAndDays> resultList = new ArrayList<>(employeesAndDaysList);

        System.out.println(result(employeesAndDaysList, resultList));
    }

    static void openAndWrite(File file, List<Employee> employeeList) throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec(String.format("cmd /c start /wait %s \\RunFromCode.bat", file));
        process.waitFor();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] tokens = line.split(",\\s*");
                int employeeId = Integer.parseInt(tokens[0]);
                int projectId = Integer.parseInt(tokens[1]);
                LocalDate dateFrom = LocalDate.parse(tokens[2]);
                LocalDate dateTo;
                if (tokens[3].equals("NULL")) {
                    dateTo = LocalDate.now();
                } else {
                    dateTo = LocalDate.parse(tokens[3]);
                }
                Employee employee = new Employee(employeeId, projectId, dateFrom, dateTo);
                employeeList.add(employee);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<EmployeesAndDays> employeesToEmployeesAndDays(List<Employee> employeeList) {

        List<EmployeesAndDays> employeesAndDaysList = new ArrayList<>();

        for (int i = 0; i < employeeList.size(); i++) {
            for (int j = 0; j < employeeList.size(); j++) {
                Employee employeeOne = employeeList.get(i);
                Employee employeeTwo = employeeList.get(j);
                if ((employeeOne.getProjectId() == employeeTwo.getProjectId()) && (employeeOne.getId() != employeeTwo.getId())) {
                    int daysTogether = daysAtSameProjects(employeeOne, employeeTwo);
                    if (daysTogether > 0) {
                        EmployeesAndDays employeesAndDays = new EmployeesAndDays(employeeOne, employeeTwo, daysTogether, employeeOne.getProjectId());
                        employeesAndDaysList.add(employeesAndDays);
                    }
                }
            }
        }
        return employeesAndDaysList;
    }

    static int daysAtSameProjects(Employee employeeOne, Employee employeeTwo) {
        int days = -1;
        LocalDate dateFromEmpOne = employeeOne.getDateFrom();
        LocalDate dateToEmpOne = employeeOne.getDateTo();
        LocalDate dateFromEmpTwo = employeeTwo.getDateFrom();
        LocalDate dateToEmpTwo = employeeTwo.getDateTo();
        boolean firstStartsBeforeSecondEnd = dateFromEmpOne.isBefore(dateToEmpTwo);
        boolean firstStartAfterSecondStart = dateFromEmpOne.isAfter(dateFromEmpTwo);
        boolean firstEndAfterSecondEnd = dateToEmpOne.isAfter(dateToEmpTwo);
        boolean firstEndBeforeSecondEnd = dateToEmpOne.isBefore(dateToEmpTwo);
        boolean firstStartBeforeSecondStart = dateFromEmpOne.isBefore(dateFromEmpTwo);
        boolean dateTwosAreEqual = dateToEmpOne.equals(dateToEmpTwo);
        boolean dateFromsAreEqual = dateFromEmpOne.equals(dateFromEmpTwo);

        if (firstStartsBeforeSecondEnd) {
            if ((firstStartAfterSecondStart || dateFromsAreEqual) && (firstEndBeforeSecondEnd || dateTwosAreEqual)) {
                days = (int) DAYS.between(dateFromEmpOne, dateToEmpOne);
            } else if ((firstStartAfterSecondStart || dateFromsAreEqual) && (firstEndAfterSecondEnd || dateTwosAreEqual)) {
                days = (int) DAYS.between(dateFromEmpOne, dateToEmpTwo);
            } else if ((firstStartBeforeSecondStart || dateFromsAreEqual) && (firstEndBeforeSecondEnd || dateTwosAreEqual)) {
                days = (int) DAYS.between(dateFromEmpTwo, dateToEmpOne);
            } else if ((firstStartBeforeSecondStart || dateFromsAreEqual) && (firstEndAfterSecondEnd || dateTwosAreEqual)) {
                days = (int) DAYS.between(dateFromEmpTwo, dateToEmpTwo);
            }
        }
        return days;
    }

    static EmployeesAndDays result(List<EmployeesAndDays> employeesAndDaysList, List<EmployeesAndDays> resultList) {

        for (int i = 0; i < employeesAndDaysList.size(); i++) {
            for (int j = 0; j < employeesAndDaysList.size(); j++) {
                if (employeesAndDaysList.get(i).isProjectDifferent(employeesAndDaysList.get(j))) {
                    EmployeesAndDays newEmployeesAndDays = new EmployeesAndDays(
                            employeesAndDaysList.get(i).getFirstEmployee(),
                            employeesAndDaysList.get(i).getSecondEmployee(),
                            employeesAndDaysList.get(i).getDaysTogether()
                                    + employeesAndDaysList.get(j).getDaysTogether());

                    resultList.add(newEmployeesAndDays);
                } else if (!employeesAndDaysList.get(i).isProjectAndPeriodSame(employeesAndDaysList.get(j))) {
                    EmployeesAndDays newEmployeesAndDays = new EmployeesAndDays(
                            employeesAndDaysList.get(i).getFirstEmployee(),
                            employeesAndDaysList.get(i).getSecondEmployee(),
                            employeesAndDaysList.get(i).getDaysTogether()
                                    + employeesAndDaysList.get(j).getDaysTogether());

                    resultList.add(newEmployeesAndDays);
                }
            }
        }
        resultList.sort(Comparator.comparing(EmployeesAndDays::getDaysTogether).reversed());
        return resultList.get(0);
    }
}
