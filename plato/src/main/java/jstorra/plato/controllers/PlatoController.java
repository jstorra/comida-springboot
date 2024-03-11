package jstorra.plato.controllers;

import jstorra.plato.models.Plato;
import jstorra.plato.services.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PlatoController {
    private final PlatoService platoService;

    @Autowired
    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping
    public List<Plato> getAllPlatos() {
        return platoService.getAllPlatos();
    }

    @GetMapping("/get/{id}")
    public Plato getPlatoById(@PathVariable Long id) {
        return platoService.getPlatoById(id);
    }

    @PostMapping("/save")
    public void guardarPlato(@RequestBody Plato plato) {
        platoService.guardarPlato(plato);
    }

    @PutMapping("/update")
    public void actualizarPlato(@RequestBody Plato plato) {
        platoService.actualizarPlato(plato);
    }

    @DeleteMapping("/delete/{id}")
    public void eliminarPlato(@PathVariable Long id) {
        platoService.eliminarPlato(id);
    }
}
