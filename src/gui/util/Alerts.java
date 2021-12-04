package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	//C�digo para gerar um alerta
	public static void showAlert(String title, String header, String content, AlertType type) {
		//Inst�nciamos um alert e modificamos suas propriedades
		Alert alert = new Alert(type);
		//Setamos o title,header e content do nosso alerta
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		//Mostramos a tela para o usu�rio
		alert.show();
	}
}