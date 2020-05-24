package com.convenio.entities;

import com.convenio.model.ServiceDescription;

import javax.persistence.*;
import java.util.Objects;

@SqlResultSetMapping(name = "configurationMapping",
        classes = {
                @ConstructorResult(
                        targetClass = ServiceDescription.class,
                        columns = {
                                @ColumnResult(name = "configurationId", type = Integer.class),
                                @ColumnResult(name = "serviceType", type = String.class),
                                @ColumnResult(name = "serviceUrl", type = String.class),
                                @ColumnResult(name = "operation", type = String.class),
                                @ColumnResult(name = "method", type = String.class),
                                @ColumnResult(name = "requestPayload", type = String.class),
                                @ColumnResult(name = "referencePath", type = String.class),
                                @ColumnResult(name = "valuePath", type = String.class),
                                @ColumnResult(name = "messagePath", type = String.class),
                        }
                )
        }
)


@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "configurationQuery",
                query = "Select configuracion_id as configurationId, tipo_servicio as serviceType, url as serviceUrl, operacion as operation, metodo as method," +
                        "       payload as requestPayload, reference_path as referencePath, valor_path as valuePath," +
                        "       mensaje_path as messagePath" +
                        "  From conveniosDB.configuracion" +
                        "  Where partner_id = :partnerId",
                resultSetMapping = "configurationMapping")
})


@Entity
@Table(name = "configuracion", schema = "conveniosDB", catalog = "")
public class ConfiguracionEntity {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer configuracionId;
  private String tipoServicio;
  private String url;
  private String operacion;
  private String metodo;
  private String payload;
  private String referencePath;
  private String valorPath;
  private String mensajePath;

  @Id
  @Column(name = "configuracion_id")
  public Integer getConfiguracionId() {
    return configuracionId;
  }

  public void setConfiguracionId(Integer configuracionId) {
    this.configuracionId = configuracionId;
  }

  @Basic
  @Column(name = "tipo_servicio")
  public String getTipoServicio() {
    return tipoServicio;
  }

  public void setTipoServicio(String tipoServicio) {
    this.tipoServicio = tipoServicio;
  }

  @Basic
  @Column(name = "url")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Basic
  @Column(name = "operacion")
  public String getOperacion() {
    return operacion;
  }

  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }

  @Basic
  @Column(name = "metodo")
  public String getMetodo() {
    return metodo;
  }

  public void setMetodo(String metodo) {
    this.metodo = metodo;
  }

  @Basic
  @Column(name = "payload")
  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  @Basic
  @Column(name = "reference_path")
  public String getReferencePath() {
    return referencePath;
  }

  public void setReferencePath(String referencePath) {
    this.referencePath = referencePath;
  }

  @Basic
  @Column(name = "valor_path")
  public String getValorPath() {
    return valorPath;
  }

  public void setValorPath(String valorPath) {
    this.valorPath = valorPath;
  }

  @Basic
  @Column(name = "mensaje_path")
  public String getMensajePath() {
    return mensajePath;
  }

  public void setMensajePath(String mensajePath) {
    this.mensajePath = mensajePath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConfiguracionEntity that = (ConfiguracionEntity) o;
    return Objects.equals(configuracionId, that.configuracionId) &&
            Objects.equals(tipoServicio, that.tipoServicio) &&
            Objects.equals(url, that.url) &&
            Objects.equals(operacion, that.operacion) &&
            Objects.equals(metodo, that.metodo) &&
            Objects.equals(payload, that.payload) &&
            Objects.equals(referencePath, that.referencePath) &&
            Objects.equals(valorPath, that.valorPath) &&
            Objects.equals(mensajePath, that.mensajePath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(configuracionId, tipoServicio, url, operacion, metodo, payload, referencePath, valorPath, mensajePath);
  }
}
