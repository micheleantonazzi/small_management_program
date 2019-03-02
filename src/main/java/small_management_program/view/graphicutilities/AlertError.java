package small_management_program.view.graphicutilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import small_management_program.view.graphicutilities.GraphicUtilities;

public class AlertError extends Alert {
    public AlertError(String title, String message){
        super(AlertType.NONE);
        setTitle(title);
        setHeaderText(message);
        ImageView imageView = new ImageView(this.getClass().getResource("/images/icon-error.png").toString());
        imageView.setFitHeight(GraphicUtilities.getInstance().getScreenWidth() / 19);
        imageView.setFitWidth(GraphicUtilities.getInstance().getScreenWidth() / 19);
        setGraphic(imageView);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
    }
}
