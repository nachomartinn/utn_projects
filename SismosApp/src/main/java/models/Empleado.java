package models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EMPLEADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_EMPLEADO")
    private Integer id;
    @Setter
    @Getter
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "TELEFONO")
    private int telefono;
    @Column(name = "MAIL")
    private String mail;
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ID_ROL")
    private Rol rol;

    public Empleado(String nombre, String apellido, int telefono, String mail, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.mail = mail;
        this.rol = rol;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Rol getRol() {
        return rol;
    }

    public Empleado obtenerASLogeado(){
        if (this.rol.sosAnalistaSismos()){
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " | " + mail + " | Tel: " + telefono + " | Rol: " + rol;
    }

}