package io.vepo.backend.roadmap.tickets;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketResponse {
    private String id;
    private String titulo;
    private String descricao;
    private String reporterId;
    private String assigneeId;

    public TicketResponse() {
    }

    public TicketResponse(String id, String titulo, String descricao, String reporterId, String assigneeId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descricao, assigneeId, reporterId);
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
        TicketResponse other = (TicketResponse) obj;
        return Objects.equals(id, other.id) && Objects.equals(titulo, other.titulo)
                && Objects.equals(descricao, other.descricao) && Objects.equals(reporterId, other.reporterId)
                && Objects.equals(assigneeId, other.assigneeId);
    }

    @Override
    public String toString() {
        return String.format("TicketResponse [id=%s, titulo=%s, descricao=%s, reporterId=%s, assigneeId=%s]", id,
                             titulo, descricao, reporterId, assigneeId);
    }

}
