package small_management_program.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import small_management_program.controller.ControllerFacade;
import small_management_program.model.DatabaseException;
import small_management_program.view.left.TreeViewObserver;
import small_management_program.view.right.ChoiceBoxTableView;
import small_management_program.view.stages.administrator.StageAddAdministrator;
import small_management_program.view.stages.administrator.StageModifyAdministrator;
import small_management_program.view.stages.billing.StageAddBilling;
import small_management_program.view.stages.billingperiod.StageModifyBillingPeriod;
import small_management_program.view.stages.condo.StageAddCondo;
import small_management_program.view.stages.condo.StageModifyCondo;
import small_management_program.view.graphicutilities.GraphicUtilities;
import small_management_program.view.stages.billingperiod.StageAddBillingPeriod;
import small_management_program.view.stages.StageConfigDatabase;
import small_management_program.view.left.ChoiceBoxTreeView;


public class View extends Application {

    private ControllerFacade controller;
    private GraphicUtilities graphicUtilities;
    private ChoiceBoxTreeView choiceBoxTreeView;
    private TreeViewObserver treeViewObserver;
    private VBox vBoxRight;

    public static View instance;

    public View(){
        this.controller = ControllerFacade.getInstance();
        this.graphicUtilities = GraphicUtilities.getInstance();
        this.controller.connectWithMemorizedCredential();
        if(instance == null){
            instance = this;
        }
    }

    public static View getInstance(){
        return instance;
    }

    private MenuBar getMenu() {
        MenuBar menu = new MenuBar();

        Menu file = new Menu("File");
        Menu modify = new Menu("Modifica");
        Menu options = new Menu("Impostazioni");
        Menu info = new Menu("?");

        //Opzioni Database

        menu.getMenus().addAll(file, modify, options, info);

        MenuItem exit = new MenuItem("Esci");
        exit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        file.getItems().add(new SeparatorMenuItem());
        file.getItems().add(exit);
        file.setOnAction(event -> Platform.exit());

        MenuItem databaseSettings = new MenuItem("Database");
        databaseSettings.setGraphic(new ImageView(this.getClass().getResource("/images/icons/database.png").toString()));
        options.getItems().add(databaseSettings);
        databaseSettings.addEventHandler(ActionEvent.ACTION, event -> {
            StageConfigDatabase st = new StageConfigDatabase();
            st.showAndWait();
            event.consume();
        });

        modify.getItems().add(getCondoMenu());

        modify.getItems().add(getAdministratorMenu());

        modify.getItems().add(getBillingMenu());

        //modify.getItems().add(getBillingPeriodMenu());

        return menu;
    }

    private Menu getBillingPeriodMenu(){
        Menu billingPeriodMenu = new Menu("Periodi di fatturazione");
        billingPeriodMenu.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar.png").toString()));

        MenuItem addBillingPeriod = new MenuItem("Aggiungi periodo di fatturazione");
        addBillingPeriod.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar-plus.png").toString()));
        billingPeriodMenu.getItems().add(addBillingPeriod);
        EventHandler<ActionEvent> openStageAddBillingPeriod = event -> {
            try{
                StageAddBillingPeriod stage = new StageAddBillingPeriod(this);
                stage.showAndWait();
            }
            catch (DatabaseException databaseException){
                this.graphicUtilities.showAlertError(databaseException);
            }
            event.consume();
        };
        addBillingPeriod.addEventHandler(ActionEvent.ANY, openStageAddBillingPeriod);

        MenuItem modifyBillingPeriod = new MenuItem("Modifica periodo di fatturazione");
        modifyBillingPeriod.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar-edit.png").toString()));
        billingPeriodMenu.getItems().add(modifyBillingPeriod);
        modifyBillingPeriod.addEventHandler(ActionEvent.ANY, event -> {
            try {
                StageModifyBillingPeriod stage = new StageModifyBillingPeriod(this);
                stage.showAndWait();
            }
            catch (DatabaseException exception){
                graphicUtilities.showAlertError(exception);
            }

            event.consume();
        });

        return billingPeriodMenu;
    }

    private Menu getAdministratorMenu(){
        Menu administratorMenu = new Menu("Amministratori");
        administratorMenu.setGraphic(new ImageView(this.getClass().getResource("/images/icons/account.png").toString()));

        MenuItem itemAddAdministrator = new MenuItem("Aggiungi amministratore");
        itemAddAdministrator.setGraphic(new ImageView(this.getClass().getResource("/images/icons/account-plus.png").toString()));
        administratorMenu.getItems().add(itemAddAdministrator);
        itemAddAdministrator.setOnAction(event -> {
            new StageAddAdministrator().showAndWait();
            event.consume();
        });

        MenuItem itemModifyAdministrator = new MenuItem("Modifica amministratori");
        itemModifyAdministrator.setGraphic(new ImageView(this.getClass().getResource("/images/icons/account-edit.png").toString()));
        administratorMenu.getItems().add(itemModifyAdministrator);
        itemModifyAdministrator.setOnAction(event -> {
            try{
                new StageModifyAdministrator().showAndWait();
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
            event.consume();
        });

        return administratorMenu;
    }

    private Menu getCondoMenu(){
        Menu condoMenu = new Menu("Condimini");
        condoMenu.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home.png").toString()));

        MenuItem itemAddCondo = new MenuItem("Aggiungi condominio");
        itemAddCondo.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-plus.png").toString()));
        condoMenu.getItems().add(itemAddCondo);
        itemAddCondo.setOnAction(event -> {
            event.consume();
            try{
                new StageAddCondo().showAndWait();
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }

        });

        MenuItem itemModifyCondo = new MenuItem("Modifica condomini");
        itemModifyCondo.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-alert.png").toString()));
        condoMenu.getItems().add(itemModifyCondo);
        itemModifyCondo.setOnAction(event -> {
            event.consume();
            try{
                new StageModifyCondo().showAndWait();
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        return condoMenu;
    }

    private Menu getBillingMenu(){
        Menu menuBilling = new Menu("Fatturato");

        menuBilling.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card.png").toString()));

        MenuItem itemAddBilling = new MenuItem("Aggiungi fatturato");
        itemAddBilling.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-plus.png").toString()));
        menuBilling.getItems().add(itemAddBilling);
        itemAddBilling.setOnAction(event -> {
            event.consume();
            try{
                new StageAddBilling(StageAddBilling.ADD).showAndWait();
            }
            catch (DatabaseException exception){
                this.graphicUtilities.showAlertError(exception);
            }
        });

        MenuItem itemModifyBilling = new MenuItem("Modifica fatturato");
        itemModifyBilling.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-settings.png").toString()));
        menuBilling.getItems().add(itemModifyBilling);
        itemModifyBilling.setOnAction(event -> {
            event.consume();
            try {
                new StageAddBilling(StageAddBilling.MODIFY).showAndWait();
            } catch (DatabaseException exception) {
                this.graphicUtilities.showAlertError(exception);
            }
        });

        return menuBilling;
    }

    private VBox getLeft(){
        VBox verticalBox = new VBox();

        verticalBox.setPadding(new Insets(25, 0, 25, 25));
        verticalBox.setSpacing(10);
        this.choiceBoxTreeView = new ChoiceBoxTreeView();
        verticalBox.getChildren().add(this.choiceBoxTreeView);

        this.treeViewObserver = new TreeViewObserver();
        this.controller.treeViewSubjectAttach(this.treeViewObserver);
        verticalBox.getChildren().add(this.treeViewObserver);
        verticalBox.setVgrow(this.treeViewObserver, Priority.ALWAYS);

        return verticalBox;
    }

    private VBox getRight(){
        this.vBoxRight = new VBox();
        this.vBoxRight.setPadding(new Insets(25, 25, 25, 25));
        this.vBoxRight.setSpacing(10);

        HBox hBoxTopRight = new HBox();
        hBoxTopRight.setSpacing(10);
        this.vBoxRight.getChildren().addAll(hBoxTopRight);

        ChoiceBoxTableView choiceBoxTableView = new ChoiceBoxTableView(this);
        hBoxTopRight.getChildren().add(choiceBoxTableView);

        return this.vBoxRight;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootPane = new BorderPane();

        MenuBar menu = getMenu();
        rootPane.setTop(menu);

        GridPane centerPane = this.graphicUtilities.getEquivalentGridPane(2,1, new int[] {20}, new int[] {});
        rootPane.setCenter(centerPane);
        centerPane.add(getLeft(), 0, 0);
        centerPane.add(getRight(), 1, 0);

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setRightComponents(TableView tableView, HBox hBoxActions) {

        //Aggiungo l'HBox delle azioni corrispondente

        HBox hBoxTopRight = (HBox) this.vBoxRight.getChildren().get(0);
        if(hBoxTopRight.getChildren().size() > 1){
            hBoxTopRight.getChildren().remove(1);
        }

        HBox.setHgrow(hBoxActions, Priority.ALWAYS);
        hBoxTopRight.getChildren().add(1, hBoxActions);

        //Aggiungo la tabella

        if(this.vBoxRight.getChildren().size() == 2){
            this.vBoxRight.getChildren().remove(1);
        }
        this.vBoxRight.getChildren().add(tableView);
        this.vBoxRight.setVgrow(tableView, Priority.ALWAYS);
    }

    public void setTreeViewItemSelected(){
        //Questo metodo serve per aggionare la tabella quando viene selezionata o cambiata
        if(this.treeViewObserver.getSelectionModel().getSelectedItem() == null)
            this.treeViewObserver.getSelectionModel().selectFirst();
        else {
            this.controller.treeViewSubjectDetach(this.treeViewObserver);
            int select = this.treeViewObserver.getSelectionModel().getSelectedIndex();
            this.treeViewObserver.getSelectionModel().selectFirst();
            this.treeViewObserver.getSelectionModel().selectLast();
            this.controller.treeViewSubjectAttach(this.treeViewObserver);
            this.treeViewObserver.getSelectionModel().select(select);
        }
    }
}
