package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
//O serviço proposto pela classe DepartmentService tem como objetivo listar todos os departamentos existentes através do banco de dados
public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	public List<Department> FindAll(){
		return dao.findAll();
	}
}
