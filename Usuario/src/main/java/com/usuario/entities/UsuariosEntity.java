package com.usuario.entities;

import com.usuario.model.UsuarioObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@SqlResultSetMapping(name = "usuarioMapping",
classes = {@ConstructorResult(
        targetClass = UsuarioObject.class,
        columns = {
                @ColumnResult(name = "userId", type = Integer.class),
                @ColumnResult(name = "userName", type = String.class),
                @ColumnResult(name = "userEmail", type = String.class)
        }
)})

@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "consultaUsuario",
        query = "Select user_id as userId, user_name as userName, user_email as userEmail" +
                "  From usuariosDB.usuarios" +
                "  Where user_id = :userId",
        resultSetMapping = "usuarioMapping")
})

@Entity
@Table(name = "usuarios", schema = "usuariosDB", catalog = "")
public class UsuariosEntity {
  private Integer userId;
  private Byte status;
  private String userEmail;
  private String userName;
  private Timestamp updatedAt;
  private Timestamp createdAt;
  private String password;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "user_id")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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
  @Column(name = "user_email")
  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  @Basic
  @Column(name = "user_name")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Basic
  @Column(name = "updated_at")
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
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
  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UsuariosEntity that = (UsuariosEntity) o;
    return Objects.equals(userId, that.userId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(userEmail, that.userEmail) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, status, userEmail, userName, updatedAt, createdAt, password);
  }

  public UsuariosEntity() {

  }

  public UsuariosEntity(UsuarioObject data) {
    this.userId = data.getIdUsuario();
    this.userName = data.getNombre();
    this.userEmail = data.getEmail();
    this.createdAt = new Timestamp(new Date().getTime());
    this.updatedAt = new Timestamp(new Date().getTime());
    this.password = "XXXX";
    this.status = 1;
  }
}
