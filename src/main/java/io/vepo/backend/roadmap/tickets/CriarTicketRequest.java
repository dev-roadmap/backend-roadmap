package io.vepo.backend.roadmap.tickets;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class CriarTicketRequest {
    private String titulo;
    private String descricao;
    private String reporterId;
    private String assigneeId;

    public CriarTicketRequest() {
    }

    public CriarTicketRequest(String titulo, String descricao, String reporterId, String assigneeId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
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
        return Objects.hash(titulo, descricao, reporterId, assigneeId);
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
        CriarTicketRequest other = (CriarTicketRequest) obj;
        return Objects.equals(titulo, other.titulo) && Objects.equals(descricao, other.descricao)
                && Objects.equals(reporterId, other.reporterId) && Objects.equals(assigneeId, other.assigneeId);
    }

    @Override
    public String toString() {
        return String.format("CriarTicketRequest [titulo=%s, descricao=%s, reporterId=%s, assigneeId=%s]", titulo,
                             descricao, reporterId, assigneeId);
    }

}
