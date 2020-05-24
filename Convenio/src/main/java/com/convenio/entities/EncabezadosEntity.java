package com.convenio.entities;

import com.convenio.dto.ConvenioDTO;
import com.convenio.model.HeaderService;

import javax.persistence.*;
import java.util.Objects;

@SqlResultSetMapping(name = "encabezadoMapping",
        classes = {
                @ConstructorResult(
                        targetClass = HeaderService.class,
                        columns = {
                                @ColumnResult(name = "headerName", type = String.class),
                                @ColumnResult(name = "headerValue", type = String.class)
                        }
                )
        }
)

@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "encabezadosQuery",
                query = "Select nombre as headerName, valor as headerValue" +
                        " From conveniosDB.encabezados" +
                        " Where configuracion_Id = :configurationId",
                resultSetMapping = "encabezadoMapping")
})

@Entity
@Table(name = "encabezados", schema = "conveniosDB", catalog = "")
public class EncabezadosEntity {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer encabezadoId;
  private String nombre;
  private String valor;

  @Id
  @Column(name = "encabezado_id")
  public Integer getEncabezadoId() {
    return encabezadoId;
  }

  public void setEncabezadoId(Integer encabezadoId) {
    this.encabezadoId = encabezadoId;
  }

  @Basic
  @Column(name = "nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Basic
  @Column(name = "valor")
  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EncabezadosEntity that = (EncabezadosEntity) o;
    return Objects.equals(encabezadoId, that.encabezadoId) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(valor, that.valor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(encabezadoId, nombre, valor);
  }
}
