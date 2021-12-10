package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

	// Código para gerar um alerta
	public static void showAlert(String title, String header, String content, AlertType type) {
		// Instânciamos um alert e modificamos suas propriedades
		Alert alert = new Alert(type);
		// Setamos o title,header e content do nosso alerta
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		// Mostramos a tela para o usuário
		alert.show();
	}

	public static Optional<ButtonType> showConfirmation(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		return alert.showAndWait();
	}
}