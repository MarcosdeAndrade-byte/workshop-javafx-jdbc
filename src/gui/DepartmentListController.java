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

	// Referêcia para os controles da tela de departamento
	@FXML
	private TableView<Department> tableViewDepartment;

	// Entidade,Tipo (Coluna Id da tela de departamento)
	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	// Entidade,Tipo (Coluna Name da tela de departamento)
	@FXML
	private TableColumn<Department, String> tableColumnName;

	//Botão da tela de departamento
	@FXML
	private Button btNew;
	
	//Variável criada para receber os departamentos
	private ObservableList<Department> obsList;

	// Ações dos controles da tela de departamento
	//Método criado para pegar o click e gerar uma janela a partir dele
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		//Na hora de criar uma tela de diálogo passamos como argumento um objeto,uma referêcia para nossa tela e a cena pai
		createDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	//Dependêcia
	private DepartmentService service;

	// Injeção de dependêcia/Inversão de controle
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
     
	// Apenas declarar as variáveis referentes as linhas e colunas da tabela não são o suficiente para sua manipulação e funcionamento Por isso criamos um método para Settar as informações
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		// Bloco de código feito para ajustar a tabela na aplicação
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	// Método responsável por acessar os serviços, carregar os departamentos e atribuit os departamentos a lista ObservableLis
	public void updateTableView() {
		// Estrutura condicional para proteger a aplicação de uma possível não injeção de dependêcia
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		// List recebe a lista de departamentos da classe DepartmentService
		List<Department> list = service.FindAll();
		//Convertemos nossa lista de departamnetos em uma obsList
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	//No argumento colocamos o Stage que criou a janela de diálogo
	//Quando criamos uma janela de diálogo temos que informar que à criou
	//Adicionamos um
	private void createDialogForm(Department obj,String absoluteName,Stage parentStage) {
		try {
			//Carrega uma hierarquia de objeto de um documento XML.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//Pegamos a nossa caixa de diálogo e passamos um objeto vazio para ela
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());
			controller.subscribeDataChangeListerner(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			//Método para modificar o título da cena
			dialogStage.setTitle("Enter Department data");
			//Uma nova cena requer um no painel
			dialogStage.setScene(new Scene(pane));
			//Método para proibir a tela de se adaptar a largura padrão
			dialogStage.setResizable(false);
			//Método para informar o pai da cena
			dialogStage.initOwner(parentStage);
			//Método para proibir interação com outras telas
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
