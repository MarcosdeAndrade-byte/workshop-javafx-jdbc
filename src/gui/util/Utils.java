package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	//Método para pegar o Stage do controle que sofreu algum evento
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	//Método para transformar uma String em Integer
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return null;
		}
	}
}
