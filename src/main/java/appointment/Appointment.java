package appointment;

import task.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Appointment {
    private static int id = 0;
    public final int appointmentId;
    public String petName;
    public String service;

    private String appointmentDate;
    private AppointmentStatus appointmentStatus;

    private ArrayList<Task> tasks = new ArrayList<>();

    public Appointment(String petName, String appointmentDate, String service) {
        this.appointmentId = ++id;
        this.petName = petName;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.appointmentDate = appointmentDate;
        this.service = service;
    }

    // view tasks for a particular appointment
    public void viewTasks(){
        System.out.println("Appointment " + appointmentId + " Task List:");
        for(Task task: tasks){
            System.out.println("________________________");
            task.printTask();
        }
    }

    public void addTaskToAppointment(Task task){
        this.tasks.add(task);
    }

    // update appointment status after changing task
    public void updateAppointmentStatus() {
        appointmentStatus = AppointmentStatus.PROCESSING;
        boolean isDone = true;
        for (Task task: tasks) {
            if (task.isDone == false) {
                isDone = false;
                break;
            }
        }
        if (isDone) {
            appointmentStatus = AppointmentStatus.PROCESSED;
        }
    }

    public String getAppointmentStatus() {
        if (appointmentStatus == AppointmentStatus.PENDING) {
            return "PENDING";
        }
        if (appointmentStatus == AppointmentStatus.PROCESSING) {
            return "PROCESSING";
        }
        if (appointmentStatus == AppointmentStatus.PROCESSED) {
            return "PROCESSED";
        }
        // control should never reach here
        return "";
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }
}