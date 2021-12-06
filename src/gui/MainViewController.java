package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	// Controles
	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemSellerDepartment;

	@FXML
	private MenuItem menuItemAbout;

	// Eventos
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("OnMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml",(DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemAbout() {
		// Utilizamos o método loadView para carregar as telas
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	// Método para carregar as telas 
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
            //Carrega uma hierarquia de objeto de um documento XML.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			// VBox da janela principal(Podemos selecionar as estruturas XML através do
			// método root)
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			// A variável mainMenu recebe os filhos(O menu da tela principal)
			Node mainMenu = mainVBox.getChildren().get(0);
			// Vamos limpar todos os filhos da janela principal(Para colocar os novos)
			mainVBox.getChildren().clear();

			// Vamos inserir novamente o menu na tela
			mainVBox.getChildren().add(mainMenu);
			// Vamos adicionar o os filhos do newVBox na janela principal
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//
			T controller = loader.getController();
			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
