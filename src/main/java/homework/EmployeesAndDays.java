package homework;

public class EmployeesAndDays {

    private Employee firstEmployee;

    private Employee secondEmployee;

    private Integer daysTogether;

    private Integer projectId;

    public EmployeesAndDays() {
    }

    public EmployeesAndDays(Employee firstEmploye, Employee secondEmployee, Integer daysTogether, Integer projectId) {
        this.firstEmployee = firstEmploye;
        this.secondEmployee = secondEmployee;
        this.daysTogether = daysTogether;
        this.projectId = projectId;
    }

    public EmployeesAndDays(Employee firstEmployee, Employee secondEmployee, Integer daysTogether) {
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
        this.daysTogether = daysTogether;
    }

    public Employee getFirstEmployee() {
        return firstEmployee;
    }

    public void setFirstEmployee(Employee firstEmploye) {
        this.firstEmployee = firstEmploye;
    }

    public Employee getSecondEmployee() {
        return secondEmployee;
    }

    public void setSecondEmployee(Employee secondEmployee) {
        this.secondEmployee = secondEmployee;
    }

    public Integer getDaysTogether() {
        return daysTogether;
    }

    public void setDaysTogether(Integer daysTogether) {
        this.daysTogether = daysTogether;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public boolean isProjectDifferent(Object object) {
        int myFirstEmployeeId = this.getFirstEmployee().getId();
        int mySecondEmployeeId = this.getSecondEmployee().getId();
        int myProjectId = this.getProjectId();

        int objFirstEmployeeId = ((EmployeesAndDays) object).getFirstEmployee().getId();
        int objSecondEmployeeId = ((EmployeesAndDays) object).getSecondEmployee().getId();
        int objProjectId = ((EmployeesAndDays) object).getProjectId();

        return ((myFirstEmployeeId == objFirstEmployeeId)
                        && (mySecondEmployeeId == objSecondEmployeeId))
                && myProjectId != objProjectId;
    }

    public boolean isProjectAndPeriodSame(Object object) {
        boolean areProjectIdsTheSame = this.getFirstEmployee().getProjectId()
                == ((EmployeesAndDays)object).getFirstEmployee().getProjectId();

        boolean areDateFromsTheSame = this.getFirstEmployee().getDateFrom().isEqual(
                ((EmployeesAndDays) object).getFirstEmployee().getDateFrom());

        boolean areDateTosTheSame = this.getFirstEmployee().getDateTo().isEqual(
                ((EmployeesAndDays) object).getFirstEmployee().getDateTo());

        return  (areProjectIdsTheSame && areDateFromsTheSame && areDateTosTheSame);

    }

    @Override
    public String toString() {
        return String.format("Employee %d and Employee %d worked %d days on same projects",
                firstEmployee.getId(), secondEmployee.getId(), daysTogether);
    }
}
