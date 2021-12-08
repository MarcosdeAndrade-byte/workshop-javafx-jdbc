package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	//M�todo para pegar o Stage do controle que sofreu algum evento
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	//M�todo para transformar uma String em Integer
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return null;
		}
	}
}
