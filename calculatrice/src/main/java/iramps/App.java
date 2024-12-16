package iramps;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    //ajout des quelques classes qui seront utilisées pour les calculs à venir
    private double premierOperant = 0;
    private String operateur ="";
    private boolean chiffreDejaPresents = true; 

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Calculatrice Desmecht Denys");

        TextField result = new TextField();
        result.setEditable(false);
        result.setAlignment(Pos.CENTER_RIGHT);
        result.setPrefHeight(50);
        result.setMaxWidth(200);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPrefWidth(300);


        Button neuf = new Button("9");
        Button huit = new Button("8");
        Button sept = new Button("7");
        Button diviser = new Button("/");
        
        Button six = new Button("6");
        Button cinq = new Button("5");
        Button quatre = new Button("4");        
        Button addition = new Button("+");
        
        Button trois = new Button("3");
        Button deux = new Button("2");
        Button un = new Button("1");
        Button egal = new Button("=");
        Button soustraction = new Button("-");
        
        Button zero = new Button("0");
        Button décimale = new Button(".");
        Button clearAll = new Button("CE");

        //usage d'une fonction pour donner la même valeur à tout mes boutons et rendre l'affichage + clair 
        Button[] BenjaminButton = {neuf,huit,sept,six,cinq,quatre,trois,deux,un,zero,décimale,addition,soustraction,diviser,egal,clearAll};

        for(Button btn : BenjaminButton)
        {
            btn.setPrefSize(40, 40);
        }
        
        //NOTE : Comportements des boutons ici
        neuf.setOnAction(e -> handleNumberInput(neuf, result));
        huit.setOnAction(e -> handleNumberInput(huit, result));
        sept.setOnAction(e -> handleNumberInput(sept, result));
        diviser.setOnAction(e -> handleOperatorInput("/", result));
    
        six.setOnAction(e -> handleNumberInput(six, result));
        cinq.setOnAction(e -> handleNumberInput(cinq, result));
        quatre.setOnAction(e -> handleNumberInput(quatre, result));
        addition.setOnAction(e -> handleOperatorInput("+", result));
    
        trois.setOnAction(e -> handleNumberInput(trois, result));
        deux.setOnAction(e -> handleNumberInput(deux, result));
        un.setOnAction(e -> handleNumberInput(un, result));
        soustraction.setOnAction(e -> handleOperatorInput("-", result));
    
        zero.setOnAction(e -> handleNumberInput(zero, result));
        egal.setOnAction(e -> handleEquals(result));
        décimale.setOnAction(e -> handleDecimal(result));

        clearAll.setOnAction(e -> {
            result.clear();
            premierOperant = 0;
            operateur = "";
            chiffreDejaPresents = true;
        });


        //Je place les boutons dans ma grille "grid" 
        grid.addRow(0, neuf,huit,sept,diviser);
        grid.addRow(1,six, cinq,quatre,addition);
        grid.addRow(2, trois,deux,un,soustraction);
        grid.addRow(3,zero, décimale,egal,clearAll);

        //Layout principal avec grid et alignement des elements , chipotage esthetique 
        VBox root = new VBox(20,result,grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));

        stage.setScene(new Scene(root,320,400));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    //fonction pour comportement de mes boutons numériques et operateurs , affichage
    private void handleNumberInput(Button button, TextField result) {
        if (chiffreDejaPresents) {
            chiffreDejaPresents = false; // Réinitialise pour saisir un nouveau chiffre , sinon il le reset de base
        }
        result.setText(result.getText() + button.getText()); // Ajoute le chiffre saisi, le deuxième operant
    }
    

    private void handleOperatorInput(String op, TextField result) {
        premierOperant = Double.parseDouble(result.getText()); // Stocke le premier opérande
        operateur = op; // Stocke l'opérateur
        result.setText(result.getText() + " " + op + " "); // Ajoute l'opération dans le champ
        chiffreDejaPresents = true; // Prépare pour le prochain nombre
    }
    
    
    private void handleDecimal(TextField result) {
        if (!result.getText().contains(".")) { // Vérifie si le point n'existe pas déjà
            result.setText(result.getText() + "."); // Ajoute un point décimal
            chiffreDejaPresents = false; // Permet de continuer à saisir le nombre
        }
    }
    

    private void handleEquals(TextField result) {
        try {
            String currentText = result.getText();
            double secondOperand = Double.parseDouble(currentText.split(" ")[2]); // Récupère le deuxième opérande
            double calculationResult;
    
            switch (operateur) {
                case "+":
                    calculationResult = premierOperant + secondOperand;
                    break;
                case "-":
                    calculationResult = premierOperant - secondOperand;
                    break;
                case "*":
                    calculationResult = premierOperant * secondOperand;
                    break;
                case "/":
                    if (secondOperand == 0) {
                        result.setText("Erreur FATALE BOUUUUM !"); // Gestion division par zéro
                        return;
                    }
                    calculationResult = premierOperant / secondOperand;
                    break;
                default:
                    return; // Aucun opérateur valide
            }
    
            result.setText(String.valueOf(calculationResult)); // Affiche le résultat final
            chiffreDejaPresents = true; // Réinitialise pour le prochain calcul
        } catch (Exception e) {
            result.setText("Erreur d'entrée"); // Gestion des erreurs si l'utilisateur appuie trop tôt sur `=`
        }
    }
    
    
    

}