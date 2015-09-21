package com.epul.clientpays.controler;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epul.clientpays.tools.EnvoiMessageSOAP;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MultiControleur {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiControleur.class);
	
	private static String operation = "getPays";
	private static String destenvoi = "http://localhost:8080/WebServicePays/services/Pays";
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
			unAppel.connexion();
			unAppel.creationMessage(operation, pays, destination);
			unAppel.EmmissionReception(destenvoi, pays);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String paysString = pays;
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("pays",  paysString);
		
		return "home";
	}
	
}
