package com.convenio.entities;

import com.convenio.dto.ConvenioDTO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@SqlResultSetMapping(name = "convenioMapping",
        classes = {
                @ConstructorResult(
                        targetClass = ConvenioDTO.class,
                        columns = {
                                @ColumnResult(name = "partnerId", type = Integer.class),
                                @ColumnResult(name = "partnerName", type = String.class)
                        }
                )
        }
)
@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "convenio.queryById",
                query = "Select partner_Id as partnerId, partner_name as partnerName" +
                        "  From conveniosDB.convenios" +
                        "  where partner_id = :partnerId",
                resultSetMapping = "convenioMapping"),
        @NamedNativeQuery(name = "convenio.queryByName",
                query = "Select partner_Id as partnerId, partner_name as partnerName" +
                        "  From conveniosDB.convenios" +
                        "  where coalesce(lower(partner_name), '') like concat('%', lower(:partnerName), '%')",
                resultSetMapping = "convenioMapping")
})
@Entity
@Table(name = "convenios", schema = "conveniosDB", catalog = "")
public class ConveniosEntity {
  private Integer partnerId;
  private String partnerName;
  private Byte status;
  private String description;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  @Id
  @Column(name = "partner_id")
  public Integer getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Integer partnerId) {
    this.partnerId = partnerId;
  }

  @Basic
  @Column(name = "partner_name")
  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  @Basic
  @Column(name = "status")
  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  @Basic
  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Basic
  @Column(name = "created_at")
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Basic
  @Column(name = "updated_at")
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConveniosEntity that = (ConveniosEntity) o;
    return Objects.equals(partnerId, that.partnerId) &&
            Objects.equals(partnerName, that.partnerName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partnerId, partnerName, status, description, createdAt, updatedAt);
  }
}
