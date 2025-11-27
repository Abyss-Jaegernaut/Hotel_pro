package hotelpro.controllers;

import hotelpro.ApplicationPrincipale;
import hotelpro.models.*;
import hotelpro.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControleurGestionReservations {
    
    @FXML
    private TableView<Reservation> tableReservations;
    
    @FXML
    private TableColumn<Reservation, Integer> colonneId;
    
    @FXML
    private TableColumn<Reservation, String> colonneClient;
    
    @FXML
    private TableColumn<Reservation, Integer> colonneChambre;
    
    @FXML
    private TableColumn<Reservation, LocalDate> colonneArrivee;
    
    @FXML
    private TableColumn<Reservation, LocalDate> colonneDepart;
    
    @FXML
    private TableColumn<Reservation, StatutReservation> colonneStatut;
    
    @FXML
    private TableColumn<Reservation, Double> colonneMontant;
    
    @FXML
    private ComboBox<Client> comboClient;
    
    @FXML
    private ComboBox<Chambre> comboChambre;
    
    @FXML
    private DatePicker dateArrivee;
    
    @FXML
    private DatePicker dateDepart;
    
    @FXML
    private ComboBox<StatutReservation> comboStatut;
    
    @FXML
    private Label labelMontant;
    
    private ObservableList<Reservation> listeReservations;
    
    @FXML
    public void initialize() {
        // Configurer les colonnes
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneClient.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getClient().getNomComplet()
            )
        );
        colonneChambre.setCellValueFactory(cellData -> 
            cellData.getValue().getChambre().numeroChambreProperty().asObject()
        );
        colonneArrivee.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));
        colonneDepart.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        colonneStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colonneMontant.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        
        // Formatter le montant
        colonneMontant.setCellFactory(col -> new TableCell<Reservation, Double>() {
            @Override
            protected void updateItem(Double montant, boolean empty) {
                super.updateItem(montant, empty);
                setText(empty || montant == null ? null : String.format("%.2f €", montant));
            }
        });
        
        // Styliser le statut
        colonneStatut.setCellFactory(col -> new TableCell<Reservation, StatutReservation>() {
            @Override
            protected void updateItem(StatutReservation statut, boolean empty) {
                super.updateItem(statut, empty);
                if (empty || statut == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(statut.getNomAffichage());
                    setStyle("-fx-background-color: " + statut.getCouleur() + "20; -fx-text-fill: " + statut.getCouleur() + "; -fx-background-radius: 12px; -fx-alignment: center; -fx-font-weight: 600;");
                }
            }
        });
        
        // Initialiser les ComboBox
        try {
            comboClient.setItems(FXCollections.observableArrayList(ClientDAO.obtenirTous()));
            comboChambre.setItems(FXCollections.observableArrayList(
                ChambreDAO.obtenirToutes().stream()
                    .filter(c -> c.getStatut() == StatutChambre.DISPONIBLE)
                    .toList()
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboStatut.setItems(FXCollections.observableArrayList(StatutReservation.values()));
        
        // Personnaliser l'affichage dans les ComboBox
        comboClient.setCellFactory(param -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                setText(empty || client == null ? null : client.getNomComplet());
            }
        });
        comboClient.setButtonCell(new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                setText(empty || client == null ? null : client.getNomComplet());
            }
        });
        
        comboChambre.setCellFactory(param -> new ListCell<Chambre>() {
            @Override
            protected void updateItem(Chambre chambre, boolean empty) {
                super.updateItem(chambre, empty);
                setText(empty || chambre == null ? null : 
                    "Ch. " + chambre.getNumeroChambre() + " - " + chambre.getType().getNomAffichage());
            }
        });
        comboChambre.setButtonCell(new ListCell<Chambre>() {
            @Override
            protected void updateItem(Chambre chambre, boolean empty) {
                super.updateItem(chambre, empty);
                setText(empty || chambre == null ? null : 
                    "Ch. " + chambre.getNumeroChambre() + " - " + chambre.getType().getNomAffichage());
            }
        });
        
        // Listener pour calculer le montant
        dateArrivee.valueProperty().addListener((obs, old, nouveau) -> calculerMontant());
        dateDepart.valueProperty().addListener((obs, old, nouveau) -> calculerMontant());
        comboChambre.valueProperty().addListener((obs, old, nouveau) -> calculerMontant());
        
        chargerReservations();
        
        tableReservations.getSelectionModel().selectedItemProperty().addListener((obs, old, nouveau) -> {
            if (nouveau != null) {
                comboClient.setValue(nouveau.getClient());
                comboChambre.setValue(nouveau.getChambre());
                dateArrivee.setValue(nouveau.getDateArrivee());
                dateDepart.setValue(nouveau.getDateDepart());
                comboStatut.setValue(nouveau.getStatut());
                labelMontant.setText(String.format("%.2f €", nouveau.getMontantTotal()));
            }
        });
    }
    
    private void chargerReservations() {
        try {
            listeReservations = FXCollections.observableArrayList(ReservationDAO.obtenirToutes());
            tableReservations.setItems(listeReservations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void calculerMontant() {
        if (dateArrivee.getValue() != null && dateDepart.getValue() != null && comboChambre.getValue() != null) {
            long jours = java.time.temporal.ChronoUnit.DAYS.between(dateArrivee.getValue(), dateDepart.getValue());
            double montant = jours * comboChambre.getValue().getPrix();
            labelMontant.setText(String.format("%.2f €", montant));
        }
    }
    
    @FXML
    private void ajouterReservation() {
        if (comboClient.getValue() == null || comboChambre.getValue() == null || 
            dateArrivee.getValue() == null || dateDepart.getValue() == null) {
            afficherAlerte("Erreur", "Veuillez remplir tous les champs");
            return;
        }
        
        try {
            Reservation nouvelleReservation = new Reservation(
                0,
                comboClient.getValue(),
                comboChambre.getValue(),
                dateArrivee.getValue(),
                dateDepart.getValue(),
                comboStatut.getValue() != null ? comboStatut.getValue() : StatutReservation.EN_ATTENTE
            );
            
            int id = ReservationDAO.ajouter(nouvelleReservation);
            // Recharger pour avoir l'objet complet avec ID
            chargerReservations();
            viderChamps();
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Erreur lors de l'ajout de la réservation");
        }
    }
    
    @FXML
    private void modifierReservation() {
        Reservation selection = tableReservations.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                selection.setClient(comboClient.getValue());
                selection.setChambre(comboChambre.getValue());
                selection.setDateArrivee(dateArrivee.getValue());
                selection.setDateDepart(dateDepart.getValue());
                selection.setStatut(comboStatut.getValue());
                ReservationDAO.modifier(selection);
                tableReservations.refresh();
                viderChamps();
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la modification");
            }
        }
    }
    
    @FXML
    private void supprimerReservation() {
        Reservation selection = tableReservations.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                ReservationDAO.supprimer(selection.getId());
                listeReservations.remove(selection);
                viderChamps();
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la suppression");
            }
        }
    }
    
    private void viderChamps() {
        comboClient.setValue(null);
        comboChambre.setValue(null);
        dateArrivee.setValue(null);
        dateDepart.setValue(null);
        comboStatut.setValue(null);
        labelMontant.setText("0.00 €");
    }
    
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void retourTableauDeBord() {
        try {
            ApplicationPrincipale.changerScene("views/tableau-de-bord.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
