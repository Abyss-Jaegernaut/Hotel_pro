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

public class ControleurGestionFacturation {
    
    @FXML
    private TableView<Facture> tableFactures;
    
    @FXML
    private TableColumn<Facture, Integer> colonneId;
    
    @FXML
    private TableColumn<Facture, String> colonneClient;
    
    @FXML
    private TableColumn<Facture, Double> colonneFrais;
    
    @FXML
    private TableColumn<Facture, Double> colonneTaxes;
    
    @FXML
    private TableColumn<Facture, Double> colonneExtras;
    
    @FXML
    private TableColumn<Facture, Double> colonneTotal;
    
    @FXML
    private TableColumn<Facture, MethodePaiement> colonneMethode;
    
    @FXML
    private TableColumn<Facture, Boolean> colonnePayee;
    
    @FXML
    private ComboBox<Reservation> comboReservation;
    
    @FXML
    private TextField champExtras;
    
    @FXML
    private ComboBox<MethodePaiement> comboMethode;
    
    @FXML
    private CheckBox checkPayee;
    
    @FXML
    private Label labelTotal;
    
    private ObservableList<Facture> listeFactures;
    
    @FXML
    public void initialize() {
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneClient.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getReservation().getClient().getNomComplet()
            )
        );
        colonneFrais.setCellValueFactory(new PropertyValueFactory<>("fraisChambre"));
        colonneTaxes.setCellValueFactory(new PropertyValueFactory<>("taxes"));
        colonneExtras.setCellValueFactory(new PropertyValueFactory<>("extras"));
        colonneTotal.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        colonneMethode.setCellValueFactory(new PropertyValueFactory<>("methodePaiement"));
        colonnePayee.setCellValueFactory(new PropertyValueFactory<>("estPayee"));
        
        // Formatter les colonnes monétaires
        colonneFrais.setCellFactory(col -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double montant, boolean empty) {
                super.updateItem(montant, empty);
                setText(empty || montant == null ? null : String.format("%.2f €", montant));
            }
        });
        
        colonneTaxes.setCellFactory(col -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double montant, boolean empty) {
                super.updateItem(montant, empty);
                setText(empty || montant == null ? null : String.format("%.2f €", montant));
            }
        });
        
        colonneExtras.setCellFactory(col -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double montant, boolean empty) {
                super.updateItem(montant, empty);
                setText(empty || montant == null ? null : String.format("%.2f €", montant));
            }
        });
        
        colonneTotal.setCellFactory(col -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double montant, boolean empty) {
                super.updateItem(montant, empty);
                if (empty || montant == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("%.2f €", montant));
                    setStyle("-fx-font-weight: 700; -fx-text-fill: #6366F1;");
                }
            }
        });
        
        // Styliser la colonne payée
        colonnePayee.setCellFactory(col -> new TableCell<Facture, Boolean>() {
            @Override
            protected void updateItem(Boolean payee, boolean empty) {
                super.updateItem(payee, empty);
                if (empty || payee == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(payee ? "✓ Payée" : "✗ Non payée");
                    String color = payee ? "#10B981" : "#EF4444";
                    setStyle("-fx-text-fill: " + color + "; -fx-font-weight: 600;");
                }
            }
        });
        
        // Initialiser les ComboBox
        try {
            comboReservation.setItems(FXCollections.observableArrayList(ReservationDAO.obtenirToutes()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboMethode.setItems(FXCollections.observableArrayList(MethodePaiement.values()));
        
        comboReservation.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                setText(empty || reservation == null ? null : 
                    "Rés. #" + reservation.getId() + " - " + reservation.getClient().getNomComplet());
            }
        });
        comboReservation.setButtonCell(new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                setText(empty || reservation == null ? null : 
                    "Rés. #" + reservation.getId() + " - " + reservation.getClient().getNomComplet());
            }
        });
        
        // Listener pour calculer le total
        champExtras.textProperty().addListener((obs, old, nouveau) -> calculerTotal());
        comboReservation.valueProperty().addListener((obs, old, nouveau) -> calculerTotal());
        
        chargerFactures();
        
        tableFactures.getSelectionModel().selectedItemProperty().addListener((obs, old, nouveau) -> {
            if (nouveau != null) {
                comboReservation.setValue(nouveau.getReservation());
                champExtras.setText(String.valueOf(nouveau.getExtras()));
                comboMethode.setValue(nouveau.getMethodePaiement());
                checkPayee.setSelected(nouveau.getEstPayee());
                labelTotal.setText(String.format("%.2f €", nouveau.getMontantTotal()));
            }
        });
    }
    
    private void chargerFactures() {
        try {
            listeFactures = FXCollections.observableArrayList(FactureDAO.obtenirToutes());
            tableFactures.setItems(listeFactures);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void calculerTotal() {
        if (comboReservation.getValue() != null) {
            try {
                double frais = comboReservation.getValue().getMontantTotal();
                double taxes = frais * 0.10;
                double extras = champExtras.getText().isEmpty() ? 0 : Double.parseDouble(champExtras.getText());
                double total = frais + taxes + extras;
                labelTotal.setText(String.format("%.2f €", total));
            } catch (NumberFormatException e) {
                labelTotal.setText("0.00 €");
            }
        }
    }
    
    @FXML
    private void genererFacture() {
        if (comboReservation.getValue() == null || comboMethode.getValue() == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner une réservation et une méthode de paiement");
            return;
        }
        
        try {
            double extras = champExtras.getText().isEmpty() ? 0 : Double.parseDouble(champExtras.getText());
            
            Facture nouvelleFacture = new Facture(
                0,
                comboReservation.getValue(),
                extras,
                comboMethode.getValue()
            );
            nouvelleFacture.setEstPayee(checkPayee.isSelected());
            
            FactureDAO.ajouter(nouvelleFacture);
            chargerFactures();
            viderChamps();
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Montant extras invalide");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Erreur lors de la génération de la facture");
        }
    }
    
    @FXML
    private void modifierFacture() {
        Facture selection = tableFactures.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                double extras = champExtras.getText().isEmpty() ? 0 : Double.parseDouble(champExtras.getText());
                selection.setExtras(extras);
                selection.setMethodePaiement(comboMethode.getValue());
                selection.setEstPayee(checkPayee.isSelected());
                FactureDAO.modifier(selection);
                tableFactures.refresh();
                viderChamps();
            } catch (NumberFormatException e) {
                afficherAlerte("Erreur", "Valeur invalide");
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la modification");
            }
        }
    }
    
    @FXML
    private void supprimerFacture() {
        Facture selection = tableFactures.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                FactureDAO.supprimer(selection.getId());
                listeFactures.remove(selection);
                viderChamps();
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la suppression");
            }
        }
    }
    
    private void viderChamps() {
        comboReservation.setValue(null);
        champExtras.clear();
        comboMethode.setValue(null);
        checkPayee.setSelected(false);
        labelTotal.setText("0.00 €");
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
