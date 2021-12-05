package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;
//O serviço proposto pela classe DepartmentService tem como objetivo listar todos os departamentos existentes
public class DepartmentService {

	public List<Department> FindAll(){
		//MOCK (Dados de mentira) dos dados
		List<Department> list = new ArrayList<>();
		list.add(new Department(1,"Books"));
		list.add(new Department(2,"Computers"));
		list.add(new Department(3,"Electronics"));
		return list;
	}
}
