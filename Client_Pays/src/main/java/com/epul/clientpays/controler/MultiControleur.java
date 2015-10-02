package com.epul.clientpays.controler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epul.clientpays.model.Pays;
import com.epul.clientpays.tools.EnvoiMessageSOAP;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MultiControleur {

	private static final Logger logger = LoggerFactory
			.getLogger(MultiControleur.class);

	private static String operation = "getPays";
	private static String destenvoi = "http://localhost:8080/WSPays/services/PaysService";
	private static String destination = "http://pays"; // Nom du package

	private static String pays = "France";

	private static EnvoiMessageSOAP unAppel = new EnvoiMessageSOAP();

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate);
		
		// Recuperation liste pays
		try {
			operation = "getListPays";
			unAppel.connexion();
			unAppel.creationMessage(operation, pays, destination);
			ArrayList<Pays> listPaysRecept = (ArrayList<Pays>) unAppel
					.EmmissionReception(destenvoi, pays);
			
			model.addAttribute("listPays", listPaysRecept);
			
		} catch (Exception e) {
			model.addAttribute("MesErreurs", e.getMessage());
			e.printStackTrace();
		}
		
		return "home";
	}

	/**
	 * Modifier Jouet
	 */
	@RequestMapping(value = "home.htm")
	public ModelAndView modifierJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String destinationPage = "Erreur";
		
		String paysSelect = request.getParameter("country");
		request.setAttribute("paysSelect",paysSelect);
		
		// Recuperation liste pays & info sur pays select
		try {
			operation = "getListPays";
			unAppel.connexion();
			unAppel.creationMessage(operation, pays, destination);
			ArrayList<Pays> listPaysRecept = (ArrayList<Pays>) unAppel
					.EmmissionReception(destenvoi, pays);
			
			request.setAttribute("listPays", listPaysRecept);
			
			operation = "getPays";
			unAppel.connexion();
			unAppel.creationMessage(operation, pays, destination);
			ArrayList<Pays> paysRecept = (ArrayList<Pays>) unAppel
					.EmmissionReception(destenvoi, pays);
			
			request.setAttribute("pays", paysRecept.get(0));
			
		} catch (Exception e) {
			request.setAttribute("MesErreurs", e.getMessage());
			e.printStackTrace();
		}

		destinationPage = "/home";

		return new ModelAndView(destinationPage);
	}

}
