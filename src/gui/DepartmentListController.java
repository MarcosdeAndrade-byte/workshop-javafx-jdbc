package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

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
	@FXML
	public void onBtNewAction() {
		System.out.println("OnBtNewAction");
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
}
