package command.servicecommand;

import command.Command;
import service.Service;
import service.ServiceList;

public class AddServiceCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private final Service service;

    public AddServiceCommand(String serviceDescription) {
        this.service = new Service(serviceDescription);
    }

    @Override
    public void execute() {
        ServiceList.addService(service);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}