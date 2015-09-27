package com.epul.clientpays.controler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epul.clientpays.model.Pays;
import com.epul.clientpays.tools.EnvoiMessageSOAP;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MultiControleur {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiControleur.class);
	
	private static String operation = "getPays";
	private static String destenvoi = "http://localhost:8080/WSPays/services/PaysService";
	private static String destination = "http://pays"; // Nom du package

	private static String pays ="France";

	private static EnvoiMessageSOAP unAppel = new EnvoiMessageSOAP();
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		try {
			operation = "getPays";
			unAppel.connexion();
			unAppel.creationMessage(operation, pays, destination);
//			model.addAttribute("pays",  unAppel.EmmissionReception(destenvoi, pays).toString());
			ArrayList<Pays> paysRecept = (ArrayList<Pays>) unAppel.EmmissionReception(destenvoi, pays);
			model.addAttribute("pays",  paysRecept.get(0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			operation = "getListPays";
			unAppel.connexion();
			unAppel.creationMessage(operation, pays, destination);
			ArrayList<Pays> listPaysRecept = (ArrayList<Pays>) unAppel.EmmissionReception(destenvoi, pays);
			model.addAttribute("listPays",  listPaysRecept);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
