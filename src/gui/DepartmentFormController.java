package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	
	//Dependência para o departamento (Dentro do nosso formulário poderemos adicionar e fazer o UpDate)
    //Uma entidade relacionada ao formulário
	private Department entity;
	
	private DepartmentService service;
	
    //Com a implementação da interface as classes podem se inscrever para receber o evento da classe
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	//Controles
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	//Instância do departamento
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	//Instância do DepartmentService
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	//
	public void subscribeDataChangeListerner(DataChangeListener listerner) {
		dataChangeListeners.add(listerner);
	}
	
	//ação que permite o botão salvar ou fazer um UpDate
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		//Exceção para avisar ao programador que esqueceu a dependência
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			//Salvamos os dados digitados na aplicação na variável entity
		    entity = getFormData();
		    //Usamos o serviço para fazer o save ou Update
		    service.saveOrUpdate(entity);
		    //Utilizamos o método currentStage para fechar a cena quando uma ação ocorrer
		    notifyDataChangeListeners();
		    Utils.currentStage(event).close();
		}catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	//Método responsável por pegar as informações nas caixas de texto da aplicação
	private Department getFormData() {
		//Criamos uma variável do tipo departamento
		Department obj = new Department();
		//Passamos o Id de String para Integer usando o método tryParseToInt e guardamos no nosso objeto
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		//Pegamos o nome (Que já é do tipo String)
		obj.setName(txtName.getText());
		//retornamos o objeto
		return obj;
	}

	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	//Método para pegar os dados do departamento e jogar nas caixas de texto
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		//Pelo fato de termos usado uma caixa de texto, vamos ter que converter o Id para String
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
