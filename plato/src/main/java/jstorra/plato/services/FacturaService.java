package jstorra.plato.services;

import jstorra.plato.Exceptions.ClienteNotFoundException;
import jstorra.plato.Exceptions.FacturaNotFoundException;
import jstorra.plato.Exceptions.IdInvalidFormatException;
import jstorra.plato.Exceptions.PlatoNotFoundException;
import jstorra.plato.models.Cliente;
import jstorra.plato.models.Factura;
import jstorra.plato.models.Plato;
import jstorra.plato.models.dto.FacturaDTO;
import jstorra.plato.repositories.ClienteRepository;
import jstorra.plato.repositories.FacturaRepository;
import jstorra.plato.repositories.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final PlatoRepository platoRepository;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository, ClienteRepository clienteRepository, PlatoRepository platoRepository) {
        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
        this.platoRepository = platoRepository;
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
        factura.setFechaCompra(new Timestamp(System.currentTimeMillis()));
        facturaRepository.save(factura);
        return ResponseEntity.ok(factura);
    }

    public ResponseEntity<Factura> updateFactura(Object id, FacturaDTO facturaDTO) {
        try {
            Long parseId = Long.valueOf(id.toString());
            Factura facturaToUpdate = facturaRepository.findById(parseId)
                    .orElseThrow(() -> new FacturaNotFoundException("La factura con ID " + id + " no existe."));

            facturaToUpdate.setTotal(facturaDTO.getTotal());
            facturaToUpdate.setTipoPago(facturaDTO.getTipoPago());

            Cliente cliente = clienteRepository.findById(facturaDTO.getClienteId())
                    .orElseThrow(() -> new ClienteNotFoundException("El cliente con ID " + facturaDTO.getClienteId() + " no existe."));

            Plato plato = platoRepository.findById(facturaDTO.getPlatoId())
                    .orElseThrow(() -> new PlatoNotFoundException("El plato con ID " + facturaDTO.getPlatoId() + " no existe."));

            facturaToUpdate.setPlato(plato);
            facturaToUpdate.setCliente(cliente);
            facturaRepository.save(facturaToUpdate);

            return ResponseEntity.ok(facturaToUpdate);
        } catch (NumberFormatException e) {
            throw new IdInvalidFormatException("El ID " + id + " no cumple con el formato válido.");
        }
    }
}
