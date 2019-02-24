package small_management_program.view.right.condos;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.image.ImageView;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.condo.CondoDelete;
import small_management_program.model.DatabaseException;
import small_management_program.model.databaseclasses.CondoRepresentation;
import small_management_program.view.graphicutilities.GraphicUtilities;
import small_management_program.view.stages.condo.StageModifyCondo;

public class ContextMenuTableViewCondos extends ContextMenu {

    TableRow<CondoRepresentation> row;

    MenuItem menuItemModifyCondo;
    MenuItem menuItemDeleteCondo;

    public ContextMenuTableViewCondos(TableRow<CondoRepresentation> row){

        this.row = row;

        this.getItems().addAll(this.getMenuItemModifyCondo(), this.getMenuItemDeleteCondo());
    }

    private MenuItem getMenuItemModifyCondo(){

        this.menuItemModifyCondo = new MenuItem("Modifica condominio");
        this.menuItemModifyCondo.setGraphic(new ImageView(this.getClass().getResource("/images/icons/pencil.png").toString()));
        this.menuItemModifyCondo.setOnAction(event -> {
            event.consume();
            try {
                new StageModifyCondo(row.getItem().getId()).showAndWait();
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        return this.menuItemModifyCondo;
    }

    private MenuItem getMenuItemDeleteCondo(){
        this.menuItemDeleteCondo = new MenuItem("Elimina condominio");
        this.menuItemDeleteCondo.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));

        this.menuItemDeleteCondo.setOnAction(event -> {
            event.consume();
            if(GraphicUtilities.getInstance().showAlertConfirmationDelete("Elimina condominio",
                    "Attenzione, sei sicuro di voler eliminare questo condominio?")){
                try {
                    Query condoDelete = new CondoDelete(this.row.getItem().getId());
                    ControllerFacade.getInstance().executeQuery(condoDelete);
                    ControllerFacade.getInstance().updateAll();
                }
                catch (DatabaseException exception){
                    GraphicUtilities.getInstance().showAlertError(exception);
                }
            }
        });

        return this.menuItemDeleteCondo;
    }
}
