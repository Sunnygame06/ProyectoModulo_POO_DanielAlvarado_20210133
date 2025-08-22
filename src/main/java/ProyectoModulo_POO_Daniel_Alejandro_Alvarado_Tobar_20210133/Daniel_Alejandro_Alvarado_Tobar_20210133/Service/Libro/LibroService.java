package ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Service.Libro;

import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Entity.Libro.LibroEntity;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Exceptions.ExceptionDatoNoEncontrado;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Models.DTO.LibroDTO;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Repository.Libro.LibroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibroService {

    @Autowired
    private LibroRepository repo;

    public List<LibroDTO> getAllLibros(){
        List<LibroEntity> libros = repo.findAll();
        return libros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public LibroDTO create (LibroDTO libroDto){
        if (libroDto == null){
            throw new IllegalArgumentException("El libro no puede ser nulo");
        }
        try{
            LibroEntity libroEntity = convertirAEntity(libroDto);
            LibroEntity libroGuardado = repo.save(libroEntity);
            return convertirADTO(libroGuardado);
        }catch (Exception e){
            log.error("Error al registrar libro: " + e.getMessage());
            throw new ExceptionDatoNoEncontrado("Error al registrar el libro " + e.getMessage());
        }
    }

    public LibroDTO update (Long id, LibroDTO libro){
        LibroEntity libroExistente = repo.findById(id).orElseThrow(() -> new ExceptionDatoNoEncontrado("Libro no encontrado"));

        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setIsbn(libro.getIsbn());
        libroExistente.setAño_publicacion(libro.getAño_publicacion());
        libroExistente.setGenero(libro.getGenero());
        libroExistente.setAutor_id(libro.getAutor_id());

        LibroEntity libroActualizado = repo.save(libroExistente);
        return convertirADTO(libroActualizado);
    }

    public boolean delete(Long id){
        try{
            LibroEntity objLibro = repo.findById(id).orElse(null);
            if(objLibro != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Libro no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el libro con ID: " + id + " para eliminar.", 1);
        }
    }

    public LibroEntity convertirAEntity(LibroDTO dto) {
        LibroEntity entity = new LibroEntity();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setIsbn(dto.getIsbn());
        entity.setAño_publicacion(dto.getAño_publicacion());
        entity.setGenero(dto.getGenero());
        entity.setAutor_id(dto.getAutor_id());
        return entity;
    }

    public LibroDTO convertirADTO(LibroEntity entity){
        LibroDTO dto = new LibroDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setIsbn(entity.getIsbn());
        dto.setAño_publicacion(entity.getAño_publicacion());
        dto.setGenero(entity.getGenero());
        dto.setAutor_id(entity.getAutor_id());
        return dto;
    }
}
