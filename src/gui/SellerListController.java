package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	// Referêcia para os controles da tela de departamento
	@FXML
	private TableView<Seller> tableViewSeller;

	// Entidade,Tipo (Coluna Id da tela de departamento)
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	// Entidade,Tipo (Coluna Name da tela de departamento)
	@FXML
	private TableColumn<Seller, String> tableColumnName;
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	// Botão da tela de departamento
	@FXML
	private Button btNew;

	// Variável criada para receber os departamentos
	private ObservableList<Seller> obsList;

	// Ações dos controles da tela de departamento
	// Método criado para pegar o click e gerar uma janela a partir dele
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		// Na hora de criar uma tela de diálogo passamos como argumento um objeto,uma
		// referêcia para nossa tela e a cena pai
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	// Dependêcia
	private SellerService service;

	// Injeção de dependêcia/Inversão de controle
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	// Apenas declarar as variáveis referentes as linhas e colunas da tabela não são
	// o suficiente para sua manipulação e funcionamento Por isso criamos um método
	// para Settar as informações
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate,"dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary,2);
		
		// Bloco de código feito para ajustar a tabela na aplicação
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}

	// Método responsável por acessar os serviços, carregar os departamentos e
	// atribuit os departamentos a lista ObservableLis
	public void updateTableView() {
		// Estrutura condicional para proteger a aplicação de uma possível não injeção
		// de dependêcia
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		// List recebe a lista de departamentos da classe SellerService
		List<Seller> list = service.FindAll();
		// Convertemos nossa lista de departamnetos em uma obsList
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	// No argumento colocamos o Stage que criou a janela de diálogo
	// Quando criamos uma janela de diálogo temos que informar que à criou
	// Adicionamos um
	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		/*try {
			// Carrega uma hierarquia de objeto de um documento XML.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			// Pegamos a nossa caixa de diálogo e passamos um objeto vazio para ela
			SellerFormController controller = loader.getController();
			controller.setSeller(obj);
			controller.setSellerService(new SellerService());
			controller.subscribeDataChangeListerner(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			// Método para modificar o título da cena
			dialogStage.setTitle("Enter Seller data");
			// Uma nova cena requer um no painel
			dialogStage.setScene(new Scene(pane));
			// Método para proibir a tela de se adaptar a largura padrão
			dialogStage.setResizable(false);
			// Método para informar o pai da cena
			dialogStage.initOwner(parentStage);
			// Método para proibir interação com outras telas
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("Io Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}*/
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
