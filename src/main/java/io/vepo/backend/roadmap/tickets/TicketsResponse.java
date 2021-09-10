package io.vepo.backend.roadmap.tickets;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketsResponse {
    private List<TicketResponse> ticket;

    public TicketsResponse() {
    }

    public TicketsResponse(List<TicketResponse> ticket) {
        this.ticket = ticket;
    }

    public List<TicketResponse> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketResponse> ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticket);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TicketsResponse other = (TicketsResponse) obj;
        return Objects.equals(ticket, other.ticket);
    }

    @Override
    public String toString() {
        return String.format("TicketsResponse [ticket=%s]", ticket);
    }

}
