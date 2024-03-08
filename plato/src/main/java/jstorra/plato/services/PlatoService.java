package jstorra.plato.services;

import jstorra.plato.models.Plato;
import jstorra.plato.repositories.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoService {
    private final PlatoRepository platoRepository;

    @Autowired
    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    public List<Plato> getAllPlatos() {
        return platoRepository.findAll();
    }

    public Plato getPlatoById(Long id) {
        Optional<Plato> optionalPlato = platoRepository.findById(id);

        if (optionalPlato.isPresent()) {
            return optionalPlato.get();
        } else {
            throw new Error("Plato no encontrado con ID " + id);
        }
    }

    public void guardarPlato(Plato plato) {
        platoRepository.save(plato);
    }

    public void actualizarPlato(Plato plato) {
        Optional<Plato> optionalPlato = platoRepository.findById(plato.getId());

        if (optionalPlato.isPresent()) {
            Plato platoToUpdate = optionalPlato.get();
            platoToUpdate.setNombre(plato.getNombre());
            platoToUpdate.setDescripcion(plato.getDescripcion());
            platoToUpdate.setPrecio(plato.getPrecio());
            platoToUpdate.setImagen(plato.getImagen());

            platoRepository.save(platoToUpdate);
        } else {
            throw new Error("Plato no encontrado con ID " + plato.getId());
        }
    }

    public void eliminarPlato(Long id) {
        Optional<Plato> optionalPlato = platoRepository.findById(id);

        if (optionalPlato.isPresent()){
            platoRepository.deleteById(id);
        } else {
            throw new Error("Plato no encontrado con ID " + id);
        }
    }
}