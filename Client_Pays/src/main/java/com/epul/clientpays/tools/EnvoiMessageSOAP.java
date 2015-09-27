package com.epul.clientpays.tools;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.epul.clientpays.model.Pays;

public class EnvoiMessageSOAP {

	private SOAPConnection connection;
	private SOAPConnectionFactory soapConnFactory;
	private MessageFactory messageFactory;
	private SOAPMessage message;
	private SOAPPart soapPart;
	private SOAPEnvelope envelope;
	private SOAPBody body;
	private SOAPElement bodyElement;
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private Source sourceContent;

	// fonction connexion

	// on construit une connexion
	public void connexion() {
		try {

			soapConnFactory = SOAPConnectionFactory.newInstance();
			connection = soapConnFactory.createConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Création de l’objet message
	// On construit les différentes parties du message SOAP
	// Il est possible de créer le message à partir d’un fichier externe.
	// Création de l’objet message
	// On construit les différentes parties du message SOAP
	// Il est possible de créer le message à partir d’un fichier externe.
	public void creationMessage(String operation, String pays,
			String destination) {
		try {
			messageFactory = MessageFactory.newInstance();
			message = messageFactory.createMessage();
			soapPart = message.getSOAPPart();
			envelope = soapPart.getEnvelope();
			body = envelope.getBody();
			// On crée l'élément principal et le namespace'
			QName bodyName = new QName(destination, operation, "m");
			// On crée l’enveloppe
			bodyElement = body.addBodyElement(bodyName);
			// On passe les paramêtres
			QName qn1 = new QName("pays");
			bodyElement.addChildElement(qn1).addTextNode(pays);

			// On sauve le message
			message.saveChanges();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Envoi du message
	// dans le cas d’un message synchrone, l’envoi et la réception s’effectuent
	// en une seule étape.
	// Envoi du message
	// dans le cas d’un message synchrone, l’envoi et la réception s’effectuent
	// en une seule étape.
	public Object EmmissionReception(String destination, String pays)
	// Les paramètres float a, float b et int oper
	// ne servent que pour l’affichage des résultats
	// Le message à émettre contient :
	// - le nom du package
	// - le nom de la méthode et ses paramètres
	{
		try {
			// On contrôle l'entrée
			System.out.println("\nENVOI:\n");
			message.writeTo(System.out);
			System.out.println();
			// On envoie le message et on attend la réponse
			// On définit la destination
			// On envoie le message
			SOAPMessage reply = connection.call(message, destination);

			// traitement de la réponse
			// On contrôle la sortie
			System.out.println("\nREQUEST:\n");
			soapPart = reply.getSOAPPart();
			envelope = soapPart.getEnvelope();
			body = envelope.getBody();
			// on examine les éléments renvoyés dans une liste
			Iterator iter = body.getChildElements();

			// nb Pays
			ArrayList<Pays> listPays = new ArrayList<Pays>();
			Node resultOuter = ((Node) iter.next()).getFirstChild();
			
			boolean isListPays = false;
			
			// getListPays
			// nb element pays
			for (int i = 0; i < resultOuter.getChildNodes().getLength(); i++) {
				Node item = resultOuter.getChildNodes().item(i);
				
				if (item.getLocalName().equals("listpays")) {
					isListPays = true;
					int j;
					Pays tmpPays = new Pays();
					for (j = 0; j < item.getChildNodes().getLength(); j++) {
						Node item2 = item.getChildNodes().item(j);
						
						// getPays
						switch (item2.getLocalName()) {
						case "nom":
							tmpPays.setNom(item2.getFirstChild().toString());
							break;

						case "nomCapital":
							tmpPays.setNomCapital(item2.getFirstChild().toString());
							break;

						case "nbHabitants":
							tmpPays.setNbHabitants(Integer.parseInt(item2
									.getFirstChild().toString()));
							break;

						default:
							break;
						}
						// System.out
						// .println("	Node :"
						// + resultOuter.getChildNodes().item(i)
						// .toString() + " .");
						System.out.println("	Node :" + item2.toString() + " .");
					}
					listPays.add(tmpPays);
				}
				if(isListPays == false)
					break;
			}

			//getPays
			if(isListPays == false) {
				Pays tmpPays = new Pays();
					for (int i = 0; i < resultOuter.getChildNodes().getLength(); i++) {
						Node item = resultOuter.getChildNodes().item(i);
					
					// getPays
					switch (item.getLocalName()) {
					case "nom":
						tmpPays.setNom(item.getFirstChild().toString());
						break;

					case "nomCapital":
						tmpPays.setNomCapital(item.getFirstChild().toString());
						break;

					case "nbHabitants":
						tmpPays.setNbHabitants(Integer.parseInt(item
								.getFirstChild().toString()));
						break;

					default:
						break;
					}
					// System.out
					// .println("	Node :"
					// + resultOuter.getChildNodes().item(i)
					// .toString() + " .");
					System.out.println("	Node :" + item.toString() + " .");
				}
					listPays.add(tmpPays);
			}

			// Node result = resultOuter.getFirstChild();

			// on affiche le résultat
			// System.out.println("pays :" + result.toString()+ " .");

			// on crée le transformeur pour visualiser le message
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			// On extrait le contenu du corps BODY
			sourceContent = reply.getSOAPPart().getContent();
			// Sortie de la transformation
			StreamResult unresult = new StreamResult(System.out);
			transformer.transform(sourceContent, unresult);
			System.out.println();
			// on ferme la connexion
			connection.close();

			// return result.getNodeValue();
			// return result;
			return listPays;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}
}