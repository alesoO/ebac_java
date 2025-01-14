package br.com.app;

import br.com.app.dao.*;
import br.com.app.domain.Client;

import javax.swing.*;

public class App {

	private static ClientDAO ClientDAO;
	
	public static void main(String args[]) {
		ClientDAO = new ClientMapDAO();
		
		String option = JOptionPane.showInputDialog(null,
				"Digite 1 para cadastro, 2 para consultar, 3 para exclusão, 4 para alteração ou 5 para sair",
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
		
		while (!isOptionValid(option)) {
			if ("".equals(option)) {
				exit();
			}
			option = JOptionPane.showInputDialog(null,
					"Opção inválida digite 1 para cadastro, 2 para consulta, 3 para cadastro, 4 para alteração ou 5 para sair",
	                "Green dinner", JOptionPane.INFORMATION_MESSAGE);
		}
		
		while (isOptionValid(option)) {
			if(isOptionExit(option)) {
				exit();
			} else if(isAdd(option)) {
				String data = JOptionPane.showInputDialog(null,
						"Digite os dados do cliente separados por vígula, conforme exemplo: Nome, CPF, Telefone, Endereço, Número, Cidade e Estado",
                        "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                add(data);
			} else if(isShow(option)) {
				String data = JOptionPane.showInputDialog(null,
                        "Digite o cpf",
                        "Consultar", JOptionPane.INFORMATION_MESSAGE);

				show(data);
			}
			
			option = JOptionPane.showInputDialog(null,
                    "Digite 1 para cadastro, 2 para consulta, 3 para exclusão, 4 para alteração ou 5 para sair",
                    "Green dinner", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private static void show (String data) {
		Client client = ClientDAO.show(Long.parseLong(data));
		if (client != null) {
			JOptionPane.showMessageDialog(null, "Cliente encontrado: " + client.toString(), "Sucesso",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado: ", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
        }
	}
	
	private static boolean isShow(String option) {
		if("2".equals(option)) {
			return true;
		}
		return false;
	}
	
	private static void add(String data) {
		String[] dataSplit = data.split(",");
		Client client = new Client(dataSplit[0], dataSplit[1], dataSplit[2], dataSplit[3], dataSplit[4], dataSplit[5], dataSplit[6]);
		Boolean isAdded = ClientDAO.add(client);
		if(isAdded) {
			JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso ", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente já se encontra cadastrado", "Erro",JOptionPane.INFORMATION_MESSAGE);
        }
	}
	
	private static boolean isAdd(String option) {
		if("1".equals(option)) {
			return true;
		}
		return false;
	}
	
	private static boolean isOptionExit(String option) {
		if("5".equals(option)) {
			return true;
		}
		return false;
	}
	
	public static void exit() {
		JOptionPane.showMessageDialog(null, "Até logo: ", "Sair",JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
	}
	
	private static boolean isOptionValid(String option) {
		if ("1".equals(option) || "2".equals(option) || "3".equals(option) || "4".equals(option) || "5".equals(option)) {
			return true;
		}
		return false;
	}
}
