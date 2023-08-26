package app.project.ProjectSI.controller;

import app.project.ProjectSI.model.MensajeMuro;
import app.project.ProjectSI.request.MensajeDTO;
import app.project.ProjectSI.service.MensajeMuroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/mensajeMuro")
public class MensajeMuroController {
    private final MensajeMuroService muroService;

    @Autowired
    public MensajeMuroController(MensajeMuroService muroService) {
        this.muroService = muroService;
    }

    //NOTE: crear un mensaje en el muro.
    @PostMapping("/create")
    public ResponseEntity<MensajeDTO> create_mensajeMuro_controller(@Valid @RequestBody MensajeMuro mensajeMuro){
        return ResponseEntity.ok(muroService.create_mensajeMuro_service(mensajeMuro));
    }

    //NOTE: modificar mi mensaje en el muro.
    @PutMapping("/update/{id}")
    public ResponseEntity<MensajeDTO> update_mensajeMuro_controller(@PathVariable Long id, @Valid @RequestBody MensajeMuro mensajeMuro){
        return ResponseEntity.ok(muroService.update_mensajeMuro_service(id, mensajeMuro));
    }

    //NOTE: eliminar mi mensaje en el muro.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete_mensajeMuro_controller(@PathVariable Long id){
        return ResponseEntity.ok(muroService.delete_mensajeMuro_service(id));
    }

    //NOTE: obtener un mensaje publicado en el muro.
    @GetMapping("/get/mensaje/{id}")
    public ResponseEntity<MensajeDTO> get_mensajeMuro_controller(@PathVariable Long id){
        return ResponseEntity.ok(muroService.get_MensajeMuro_service(id));
    }

    //NOTE: obtener mis mensaje publicados en el muro.
    @GetMapping("/get/")
    public ResponseEntity<Set<MensajeDTO>> get_mensajesMuro_controller(){
        return ResponseEntity.ok(muroService.get_MensajesMuro_service());
    }

    //NOTE: obtener todos los mensajes publicados en el muro de todos los usuarios a los que sigo y los mios.
    @GetMapping("/get/all")
    public ResponseEntity<Set<MensajeDTO>> get_all_mensajeMuro_controller(){
        return ResponseEntity.ok(muroService.get_all_MensajeMuro_service());
    }
}
