package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener {

	// Refer�cia para os controles da tela de departamento
	@FXML
	private TableView<Department> tableViewDepartment;

	// Entidade,Tipo (Coluna Id da tela de departamento)
	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	// Entidade,Tipo (Coluna Name da tela de departamento)
	@FXML
	private TableColumn<Department, String> tableColumnName;

	//Bot�o da tela de departamento
	@FXML
	private Button btNew;
	
	//Vari�vel criada para receber os departamentos
	private ObservableList<Department> obsList;

	// A��es dos controles da tela de departamento
	//M�todo criado para pegar o click e gerar uma janela a partir dele
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		//Na hora de criar uma tela de di�logo passamos como argumento um objeto,uma refer�cia para nossa tela e a cena pai
		createDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	//Depend�cia
	private DepartmentService service;

	// Inje��o de depend�cia/Invers�o de controle
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
     
	// Apenas declarar as vari�veis referentes as linhas e colunas da tabela n�o s�o o suficiente para sua manipula��o e funcionamento Por isso criamos um m�todo para Settar as informa��es
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		// Bloco de c�digo feito para ajustar a tabela na aplica��o
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	// M�todo respons�vel por acessar os servi�os, carregar os departamentos e atribuit os departamentos a lista ObservableLis
	public void updateTableView() {
		// Estrutura condicional para proteger a aplica��o de uma poss�vel n�o inje��o de depend�cia
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		// List recebe a lista de departamentos da classe DepartmentService
		List<Department> list = service.FindAll();
		//Convertemos nossa lista de departamnetos em uma obsList
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	//No argumento colocamos o Stage que criou a janela de di�logo
	//Quando criamos uma janela de di�logo temos que informar que � criou
	//Adicionamos um
	private void createDialogForm(Department obj,String absoluteName,Stage parentStage) {
		try {
			//Carrega uma hierarquia de objeto de um documento XML.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//Pegamos a nossa caixa de di�logo e passamos um objeto vazio para ela
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());
			controller.subscribeDataChangeListerner(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			//M�todo para modificar o t�tulo da cena
			dialogStage.setTitle("Enter Department data");
			//Uma nova cena requer um no painel
			dialogStage.setScene(new Scene(pane));
			//M�todo para proibir a tela de se adaptar a largura padr�o
			dialogStage.setResizable(false);
			//M�todo para informar o pai da cena
			dialogStage.initOwner(parentStage);
			//M�todo para proibir intera��o com outras telas
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}catch(IOException e) {
			Alerts.showAlert("Io Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}
}
