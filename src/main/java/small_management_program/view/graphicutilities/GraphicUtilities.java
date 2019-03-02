package small_management_program.view.graphicutilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import small_management_program.controller.DuplicateMap;
import small_management_program.model.database.DatabaseException;

import java.util.Iterator;
import java.util.Set;

/** This class creates specific JavaFX graphic objects.
 *  It's a collection of static method and each method returns a specific graphic object.
 * @author Michele Antonazzi
 */
public class GraphicUtilities {

    private static GraphicUtilities instance;

    private GraphicUtilities(){}

    public static GraphicUtilities getInstance(){
        if (instance == null)
            instance = new GraphicUtilities();
        return instance;
    }


    /** This method creates a GridPane with a specific number of rows and columns.
     * All lines have the same size and all columns have the same size.
     * @param columns The number of columns.
     * @param rows The number of rows.
     * @return a GridPane with the number of rows and columns specified.
     */

    public GridPane getEquivalentGridPane(int columns, int rows) {
        return getEquivalentGridPane(columns, rows, new int[0], new int[0]);
    }

    /** This method creates a GridPane with a specific number of rows and columns.
     * In the arrays are stored the dimensions (in percentage) of first N rows and N columns.
     * @param columns The number of columns.
     * @param rows The number of rows.
     * @param percentOfColumns Contains the dimensions of the first n columns.
     * @return a GridPane with the number of rows and columns specified.
     */

    public GridPane getEquivalentGridPane(int columns, int rows, int percentOfColumns[]){
        return this.getEquivalentGridPane(columns, rows, percentOfColumns, new int[]{});
    }

    public GridPane getEquivalentGridPane(int columns, int rows, int percentOfColumns[], int percentOfRows[]){
        GridPane gridPane = new GridPane();
        if(rows > 0) {
            for(int i = 0; i < rows; ++i){
                RowConstraints rowConstraints = new RowConstraints();
                if(i < percentOfRows.length)
                    rowConstraints.setPercentHeight(percentOfRows[i]);
                rowConstraints.setFillHeight(true);
                rowConstraints.setVgrow(Priority.ALWAYS);
                gridPane.getRowConstraints().add(rowConstraints);
            }
        }
        if(columns > 0) {
            for(int i = 0; i < columns; ++i){
                ColumnConstraints columnConstraints = new ColumnConstraints();
                if(i < percentOfColumns.length)
                    columnConstraints.setPercentWidth(percentOfColumns[i]);
                columnConstraints.setFillWidth(true);
                columnConstraints.setHgrow(Priority.ALWAYS);
                gridPane.getColumnConstraints().add(columnConstraints);
            }
        }
        return gridPane;
    }

    public double getScreenWidth(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getWidth();
    }

    public double getScreenHeight(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getHeight();
    }

    public void showAlertSuccess(String title, String message){
        new AlertSuccess(title, message).showAndWait();
    }

    public void showAlertError(DatabaseException exception){
        showAlertError(exception.getTitle(), exception.getMessage());
    }

    public void showAlertError(String title, String message){
        new AlertError(title, message).showAndWait();
    }

    /*public ObservableList<ChoiceBoxItemId> getCondosObservableList(DuplicateMap<Integer, String> condos){
        ObservableList<ChoiceBoxItemId> condosItems = FXCollections.observableArrayList();
        Set<Integer> ids = condos.keySet();
        for(Iterator<Integer> it = ids.iterator(); it.hasNext();){
            int id = it.next();
            ChoiceBoxItemId item = new ChoiceBoxItemId(id, id +  " - " + condos.get(id, 3));
            condosItems.add(item);
        }
        return condosItems;
    }
    */

    public boolean showAlertConfirmationDelete(String title, String header){
        return this.showAlertConfirmation(title, header, "Elimina");
    }

    public boolean showAlertConfirmationSave(String title, String header){
        return this.showAlertConfirmation(title, header, "Salva");
    }

    private boolean showAlertConfirmation(String title, String header, String textButtonConfirmation){
        //Mostro prima un alert per chiedere all'utente di eliminare o meno il condominio

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);

        //Setto i bottoni

        ButtonType buttonConfirm = new ButtonType(textButtonConfirmation);
        ButtonType buttonCancel = new ButtonType("Annulla");
        alert.getButtonTypes().setAll(buttonCancel, buttonConfirm);

        return alert.showAndWait().get() == buttonConfirm;
    }
}
