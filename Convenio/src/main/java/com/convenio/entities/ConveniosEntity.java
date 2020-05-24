package com.convenio.entities;

import com.convenio.dto.ConvenioDTO;
import com.convenio.model.ConvenioObject;

import javax.persistence.*;
import java.util.Objects;

@SqlResultSetMappings( value = {
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
        ),
        @SqlResultSetMapping(name = "convenioMapping2",
                classes = {
                        @ConstructorResult(
                                targetClass = ConvenioObject.class,
                                columns = {
                                        @ColumnResult(name = "idConvenio", type = Integer.class),
                                        @ColumnResult(name = "nombre", type = String.class),
                                        @ColumnResult(name = "descripcion", type = String.class)
                                }
                        )
                }
        )
})
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
                resultSetMapping = "convenioMapping"),
        @NamedNativeQuery(name = "convenio2.queryById",
                query = "Select partner_Id as idConvenio, partner_name as nombre, description as descripcion" +
                        "  From conveniosDB.convenios" +
                        "  where partner_id = :partnerId",
                resultSetMapping = "convenioMapping2")
})

@Entity
@Table(name = "convenios", schema = "conveniosDB", catalog = "")
public class ConveniosEntity {
  private Integer partnerId;
  private String partnerName;
  private Byte status;
  private String description;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConveniosEntity that = (ConveniosEntity) o;
    return Objects.equals(partnerId, that.partnerId) &&
            Objects.equals(partnerName, that.partnerName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partnerId, partnerName, status, description);
  }
}
