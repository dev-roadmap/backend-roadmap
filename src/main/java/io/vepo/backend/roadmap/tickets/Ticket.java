package io.vepo.backend.roadmap.tickets;

import java.util.Objects;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "ticket")
public class Ticket {
    private ObjectId id;
    private String titulo;
    private String descricao;
    private ObjectId reporterId;
    private ObjectId assigneeId;

    public Ticket() {
    }

    public Ticket(ObjectId id, String titulo, String descricao, ObjectId reporterId, ObjectId assigneeId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public ObjectId getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(ObjectId assigneeId) {
        this.assigneeId = assigneeId;
    }

    public ObjectId getReporterId() {
        return reporterId;
    }

    public void setReporterId(ObjectId reporterId) {
        this.reporterId = reporterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, id, titulo, assigneeId, reporterId);
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
        Ticket other = (Ticket) obj;
        return Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
                && Objects.equals(titulo, other.titulo) && Objects.equals(assigneeId, other.assigneeId)
                && Objects.equals(reporterId, other.reporterId);
    }

    @Override
    public String toString() {
        return String.format("Ticket [id=%s, titulo=%s, descricao=%s, reporterId=%s, assigneeId=%s]", id, titulo,
                             descricao, reporterId, assigneeId);
    }

}
