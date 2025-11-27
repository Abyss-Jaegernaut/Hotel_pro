package hotelpro.controllers;

import hotelpro.ApplicationPrincipale;
import hotelpro.models.*;
import hotelpro.utils.ChambreDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class ControleurGestionChambres {
    
    @FXML private TableView<Chambre> tableChambres;
    @FXML private TableColumn<Chambre, Integer> colonneNumero;
    @FXML private TableColumn<Chambre, TypeChambre> colonneType;
    @FXML private TableColumn<Chambre, StatutChambre> colonneStatut;
    @FXML private TableColumn<Chambre, Double> colonnePrix;
    @FXML private TextField champNumero;
    @FXML private ComboBox<TypeChambre> comboType;
    @FXML private ComboBox<StatutChambre> comboStatut;
    @FXML private TextField champPrix;
    
    private ObservableList<Chambre> listeChambres;
    
    @FXML
    public void initialize() {
        colonneNumero.setCellValueFactory(new PropertyValueFactory<>("numeroChambre"));
        colonneType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colonneStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colonnePrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        
        colonnePrix.setCellFactory(col -> new TableCell<Chambre, Double>() {
            @Override
            protected void updateItem(Double prix, boolean empty) {
                super.updateItem(prix, empty);
                setText(empty || prix == null ? null : String.format("%.2f €", prix));
            }
        });
        
        colonneStatut.setCellFactory(col -> new TableCell<Chambre, StatutChambre>() {
            @Override
            protected void updateItem(StatutChambre statut, boolean empty) {
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
        
        comboType.setItems(FXCollections.observableArrayList(TypeChambre.values()));
        comboStatut.setItems(FXCollections.observableArrayList(StatutChambre.values()));
        
        chargerChambres();
        
        tableChambres.getSelectionModel().selectedItemProperty().addListener((obs, old, nouveau) -> {
            if (nouveau != null) {
                champNumero.setText(String.valueOf(nouveau.getNumeroChambre()));
                comboType.setValue(nouveau.getType());
                comboStatut.setValue(nouveau.getStatut());
                champPrix.setText(String.valueOf(nouveau.getPrix()));
            }
        });
    }
    
    private void chargerChambres() {
        try {
            listeChambres = FXCollections.observableArrayList(ChambreDAO.obtenirToutes());
            tableChambres.setItems(listeChambres);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Erreur lors du chargement des chambres");
        }
    }
    
    @FXML
    private void ajouterChambre() {
        try {
            int numero = Integer.parseInt(champNumero.getText());
            TypeChambre type = comboType.getValue();
            StatutChambre statut = comboStatut.getValue();
            
            if (type == null || statut == null) {
                afficherAlerte("Erreur", "Veuillez remplir tous les champs");
                return;
            }
            
            Chambre nouvelleChambre = new Chambre(numero, type, statut);
            ChambreDAO.ajouter(nouvelleChambre);
            listeChambres.add(nouvelleChambre);
            viderChamps();
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Numéro de chambre invalide");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Erreur lors de l'ajout de la chambre");
        }
    }
    
    @FXML
    private void modifierChambre() {
        Chambre selection = tableChambres.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                selection.setType(comboType.getValue());
                selection.setStatut(comboStatut.getValue());
                if (!champPrix.getText().isEmpty()) {
                    selection.setPrix(Double.parseDouble(champPrix.getText()));
                }
                ChambreDAO.modifier(selection);
                tableChambres.refresh();
                viderChamps();
            } catch (NumberFormatException e) {
                afficherAlerte("Erreur", "Valeurs invalides");
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la modification");
            }
        }
    }
    
    @FXML
    private void supprimerChambre() {
        Chambre selection = tableChambres.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                ChambreDAO.supprimer(selection.getNumeroChambre());
                listeChambres.remove(selection);
                viderChamps();
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la suppression");
            }
        }
    }
    
    private void viderChamps() {
        champNumero.clear();
        comboType.setValue(null);
        comboStatut.setValue(null);
        champPrix.clear();
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
