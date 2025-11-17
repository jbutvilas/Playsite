package lt.jonas.homework.playsite.exception.model;

public class TicketUsedException extends RuntimeException {

    public TicketUsedException() {
        super("Ticket is being currently used");
    }
}
