package command;

import appointment.AppointmentList;
import employee.EmployeeList;
import service.ServiceList;

public class RemoveServiceCommand extends Command{

    private int serviceId;

    public RemoveServiceCommand(int serviceId){
        this.serviceId = serviceId;
    }

    @Override
    public void execute(AppointmentList appointmentList, EmployeeList employeeList, ServiceList serviceList) {
        ServiceList.removeService(serviceId);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}