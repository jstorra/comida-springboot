package jstorra.plato.services;

import jstorra.plato.Exceptions.FacturaNotFoundException;
import jstorra.plato.Exceptions.IdInvalidFormatException;
import jstorra.plato.models.Factura;
import jstorra.plato.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    public ResponseEntity<Factura> getFacturaById(Object id) {
        try {
            Long parseId = Long.valueOf(id.toString());
            Factura factura = facturaRepository.findById(parseId).orElse(null);
            if (factura == null)
                throw new FacturaNotFoundException("La factura con ID " + id + " no existe.");
            return ResponseEntity.ok(factura);
        } catch (NumberFormatException e) {
            throw new IdInvalidFormatException("El ID " + id + " no cumple con el formato válido.");
        }
    }

    public ResponseEntity<Factura> saveFactura(Factura factura) {
        facturaRepository.save(factura);
        return ResponseEntity.ok(factura);
    }
}
