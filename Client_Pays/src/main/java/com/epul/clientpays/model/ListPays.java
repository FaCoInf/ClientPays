package com.epul.clientpays.model;

import java.util.ArrayList;

public class ListPays {
	private ArrayList<Pays> listpays;

	public ListPays(ArrayList<Pays> list) {
		this.listpays = list;
	}

	public ArrayList<Pays> getListpays() {
		return listpays;
	}

	public void setListpays(ArrayList<Pays> listpays) {
		this.listpays = listpays;
	}

	public void addPays(Pays p){
		listpays.add(p);
	}
}
