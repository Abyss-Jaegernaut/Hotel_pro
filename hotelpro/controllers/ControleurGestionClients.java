package hotelpro.controllers;

import hotelpro.ApplicationPrincipale;
import hotelpro.models.Client;
import hotelpro.utils.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class ControleurGestionClients {
    
    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, Integer> colonneId;
    @FXML private TableColumn<Client, String> colonnePrenom;
    @FXML private TableColumn<Client, String> colonneNom;
    @FXML private TableColumn<Client, String> colonneEmail;
    @FXML private TableColumn<Client, String> colonneTelephone;
    @FXML private TextField champPrenom;
    @FXML private TextField champNom;
    @FXML private TextField champEmail;
    @FXML private TextField champTelephone;
    @FXML private TextArea champAdresse;
    @FXML private TextField champRecherche;
    
    private ObservableList<Client> listeClients;
    
    @FXML
    public void initialize() {
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonnePrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colonneNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colonneEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colonneTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        
        chargerClients();
        
        tableClients.getSelectionModel().selectedItemProperty().addListener((obs, old, nouveau) -> {
            if (nouveau != null) {
                champPrenom.setText(nouveau.getPrenom());
                champNom.setText(nouveau.getNom());
                champEmail.setText(nouveau.getEmail());
                champTelephone.setText(nouveau.getTelephone());
                champAdresse.setText(nouveau.getAdresse());
            }
        });
        
        champRecherche.textProperty().addListener((obs, old, nouveau) -> rechercherClients(nouveau));
    }
    
    private void chargerClients() {
        try {
            listeClients = FXCollections.observableArrayList(ClientDAO.obtenirTous());
            tableClients.setItems(listeClients);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Erreur lors du chargement des clients");
        }
    }
    
    private void rechercherClients(String recherche) {
        if (recherche == null || recherche.isEmpty()) {
            tableClients.setItems(listeClients);
        } else {
            try {
                ObservableList<Client> resultat = FXCollections.observableArrayList(ClientDAO.rechercher(recherche));
                tableClients.setItems(resultat);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void ajouterClient() {
        if (champPrenom.getText().isEmpty() || champNom.getText().isEmpty()) {
            afficherAlerte("Erreur", "Le prénom et le nom sont obligatoires");
            return;
        }
        
        try {
            Client nouveauClient = new Client(0, champPrenom.getText(), champNom.getText(),
                champEmail.getText(), champTelephone.getText(), champAdresse.getText());
            int id = ClientDAO.ajouter(nouveauClient);
            nouveauClient.setId(id);
            listeClients.add(nouveauClient);
            viderChamps();
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Erreur lors de l'ajout du client");
        }
    }
    
    @FXML
    private void modifierClient() {
        Client selection = tableClients.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                selection.setPrenom(champPrenom.getText());
                selection.setNom(champNom.getText());
                selection.setEmail(champEmail.getText());
                selection.setTelephone(champTelephone.getText());
                selection.setAdresse(champAdresse.getText());
                ClientDAO.modifier(selection);
                tableClients.refresh();
                viderChamps();
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la modification");
            }
        }
    }
    
    @FXML
    private void supprimerClient() {
        Client selection = tableClients.getSelectionModel().getSelectedItem();
        if (selection != null) {
            try {
                ClientDAO.supprimer(selection.getId());
                listeClients.remove(selection);
                viderChamps();
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur", "Erreur lors de la suppression");
            }
        }
    }
    
    private void viderChamps() {
        champPrenom.clear();
        champNom.clear();
        champEmail.clear();
        champTelephone.clear();
        champAdresse.clear();
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
