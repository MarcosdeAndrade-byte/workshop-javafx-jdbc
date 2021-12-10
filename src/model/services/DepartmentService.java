package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
//Classe responsável pelos serviços
public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	//Método para econtrar todos os departamentos do banco
	public List<Department> FindAll(){
		return dao.findAll();
	}
	
	//Método para salvar ou fazer update
	public void saveOrUpdate(Department obj) {
		//Se o Id for igual a null inserimos o departamento
		if(obj.getId() == null) {
			dao.insert(obj);
		}else {
			//Se o Id existir apenas fazemos o update
			dao.update(obj);
		}
	}
	
	//Método responsável por remover departamentos do banco de dados
	public void remove(Department obj) {	
		dao.deleteById(obj.getId());
	}
}
