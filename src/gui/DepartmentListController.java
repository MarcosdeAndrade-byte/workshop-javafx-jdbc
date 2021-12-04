package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable{
    
	//Refer�cia para os controles
	@FXML
	private TableView<Department> tableViewDepartment;
	
	//                    Entidade,Tipo
	@FXML
	private TableColumn<Department,Integer> tableColumnId;
	
    //                    Entidade,Tipo
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	// A��es
	@FXML
	public void onBtNewAction() {
		System.out.println("OnBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		 initializeNodes();
	}
	
	//Apenas declarar as vari�veis referentes as linhas e colunas da tabela n�o s�o o suficiente para sua manipula��o e funcionamento
	//Por isso criamos um m�todo para Settar as informa��es
	private void initializeNodes(){
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("name"));
	    
		//Bloco de c�digo feito para ajustar a tabela na aplica��o
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	    // prefHeightProperty() : Propriedade para substituir a altura preferencial calculada do controle.
	}
}
