package ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Entity.Libro;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LIBROS")
@Getter @Setter @ToString @EqualsAndHashCode
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_libro")
    @SequenceGenerator(name = "seq_libro", sequenceName = "seq_libro", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN", unique = true)
    private String isbn;

    @Column(name = "AÑO_PUBLICACION")
    private Long año_publicacion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "AUTOR_ID")
    private Long autor_id;
}
