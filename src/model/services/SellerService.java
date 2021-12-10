package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;
//Classe respons�vel pelos servi�os
public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();

	//M�todo para econtrar todos os departamentos do banco
	public List<Seller> FindAll(){
		return dao.findAll();
	}
	
	//M�todo para salvar ou fazer update
	public void saveOrUpdate(Seller obj) {
		//Se o Id for igual a null inserimos o departamento
		if(obj.getId() == null) {
			dao.insert(obj);
		}else {
			//Se o Id existir apenas fazemos o update
			dao.update(obj);
		}
	}
	
	//M�todo respons�vel por remover departamentos do banco de dados
	public void remove(Seller obj) {	
		dao.deleteById(obj.getId());
	}
}
