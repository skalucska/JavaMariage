package com.example.mariage;

import java.lang.module.ResolutionException;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@OpenAPIDefinition(info = @Info(title = "Swagger des invités de mariage", version = "1", description = "test"))
public class InviteController {

    @Autowired
    private InviteRepository repository;

    @GetMapping(value = "/saveValue")
    public String getDate() {
        // saveInvite();
        System.out.println("invité ajouté");
        return new Date().toString();
    }

    @GetMapping(value = "/invites")
    @Operation(summary = "récupération des invités", description = "récupération de tout les invités du mariage avec leurs disponibilité et leurs plats choisis", responses = @ApiResponse(responseCode = "200", description = "valide"))
    @Schema(description = "UUID", example = "29F44D73-B94A-4260-A73D-E6A94A766906")
    public ResponseEntity<Iterable<Invite>> getInvites() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/invite/{inviteId}")
    public Optional<Invite> getInvite(String id,
            @Valid @RequestBody Invite invite) throws ResolutionException {
        return repository.findById(id);
    }

    @PostMapping(value = "deleteInviteBy")
    public void postDeleteInviteById(String id) {
        repository.deleteById(id);
    }

    @PostMapping("/updateInvite/{id}")
    @Operation(summary = "Mise à jour d'un invités", description = "Lors de l'execution, on met a jour un invité par le biais de son id, des données de la bdd ", responses = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Invite> updateInvite(String id,
            @Valid @RequestBody Invite invite) throws ResolutionException {
        repository.findById(id);

        invite.setNom(invite.getNom());
        invite.setPrenom(invite.getPrenom());
        final Invite updatedInvite = repository.save(invite);
        return ResponseEntity.ok(updatedInvite);
    }

    public void saveInvite() {
        // save a couple of customers
        repository.save(new Invite("Bond", "James", "boeuf", false, false, true));
        repository.save(new Invite("Cameron", "James", "vegan", false, false, true));
        repository.save(new Invite("Royal", "Michel", "", true, false, false));
        repository.save(new Invite("Hannibal Smith", "John", "", false, true, false));
        repository.save(new Invite("McClane", "John", "poisson", false, false, true));
        repository.save(new Invite("Douglas", "Micheal", "boeuf", true, true, true));
        repository.save(new Invite("Estwood", "Clint", "vegan", true, false, true));
    }

    public void showInvite() {
        System.out.println("Liste des invités et leurs choix");
        System.out.println("--------------------------------");

        for (Invite invite : repository.findAll()) {
            System.out.println(invite);
        }
    }

    public void StatInvite() {
        // nb invite ?
        // nb plat / nb poisson ,nb boeuf, nb vegan
        // nb au repas
        // nb ceremonie
        // nb vin
    }

    public void deleteInvite() {
        repository.deleteAll();
    }

    public void deleteInviteById(String id) {
        repository.deleteById(id);
    }

}
