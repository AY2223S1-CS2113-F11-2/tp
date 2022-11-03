package task;

import appointment.Appointment;
import employee.Employee;
import employee.EmployeeList;
import exception.DukeException;

import java.util.ArrayList;
import java.util.Objects;

import static appointment.AppointmentList.findAppointment;

public class TaskList {
    static ArrayList<Task> tasks = new ArrayList<>();

    //view every single task in the clinic
    public static void viewTasks() {
        System.out.println("Here are all the tasks for the clinic:");
        for (Task task : tasks) {
            System.out.println("_______________________________________");
            task.printTask();
        }
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static void addTask(Task task) throws DukeException {

        // appointment aggregate task
        Appointment appointment = findAppointment(task.getAppointmentId());
        if (appointment == null) {
            Task.idCounter--;
            throw new DukeException();
        }

        // employee aggregate task
        Employee employee = EmployeeList.findEmployee(task.getEmployeeId());
        if (employee == null) {
            Task.idCounter--;
            throw new DukeException();
        }

        // only after passing the check, add this task
        tasks.add(task);
        appointment.addTaskToAppointment(task);
        appointment.updateAppointmentStatus();
        employee.addTaskToEmployee(task);

        System.out.print("Got it. I've added this task: ");
        System.out.println(task.getTaskDescription());
        System.out.print("Performed by: ");
        System.out.println(Objects.requireNonNull(EmployeeList.findEmployee(task.getEmployeeId())).getEmployeeName());
        System.out.println("Appointment: ID " + task.getAppointmentId());
        System.out.println("Now you have " + tasks.size() + " task in the list.");
    }

    // assign task to be done by another person
    public static void reassignTask(int taskId, int employeeId) throws DukeException {
        if (TaskList.findTask(taskId) == null) {
            throw new DukeException();
        }
        if (EmployeeList.findEmployee(employeeId) == null) {
            throw new DukeException();
        }
        Task taskToReassign = TaskList.findTask(taskId);
        // Remove from original Employee's task list
        if (taskToReassign != null) {
            EmployeeList.findEmployee(taskToReassign.getEmployeeId()).removeTaskFromEmployee(taskId);
            // Add to new Employee's task list
            EmployeeList.findEmployee(employeeId).addTaskToEmployee(taskToReassign);
            System.out.print("Got it. Task " + taskId + " has been reassigned from ");
            System.out.print(EmployeeList.findEmployee(taskToReassign.getEmployeeId()).getEmployeeName());
            System.out.println(" to " + EmployeeList.findEmployee(employeeId).getEmployeeName() + "!");
            // Change employeeId in taskToReassign
            taskToReassign.setEmployeeId(employeeId);
        }
    }

    public static void removeTask(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                // remove from overall task list
                tasks.remove(task);
                // remove from appointment
                findAppointment(task.getAppointmentId()).removeTaskFromAppointment(taskId);
                // remove from employee
                EmployeeList.findEmployee(task.getEmployeeId()).removeTaskFromEmployee(taskId);
                System.out.print("Got it. I've removed this task: ");
                task.printTask();
                System.out.println("Now you have " + tasks.size() + " task in the list.");
                break;
            }
        }
        System.out.println("Sorry, no corresponding task found.");
    }

    public static Task findTask(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public static void finishTask(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                task.setDone();
                System.out.print("Got it. I've finished this task: ");
                System.out.println(task.getTaskDescription());
                return;
            }
        }
        System.out.println("Sorry, no corresponding task found.");
    }

}
