package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.NoDeptResult;
import fr.diginamic.recensement.exceptions.NotANumberException;
import fr.diginamic.recensement.exceptions.NotCoherentLimitException;
import fr.diginamic.recensement.exceptions.NotPositiveNumberException;
import fr.diginamic.recensement.exceptions.RecensementException;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws RecensementException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();

		// Gestion des exceptions
		// Une méthode de faire parmi d'autres
		if (!NumberUtils.isCreatable(saisieMin) || saisieMin.contains(".")) {
			throw new NotANumberException("Attention ! '" + saisieMin + "' n'est pas un entier.");
		}

		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();

		if (!NumberUtils.isCreatable(saisieMax) || saisieMax.contains(".")) {
			throw new NotANumberException("Attention ! '" + saisieMax + "' n'est pas un entier.");
		}

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;

		if (min < 0) {
			throw new NotPositiveNumberException(min + "... Votre population minimum doit être positive.");
		}
		if (max < 0) {
			throw new NotPositiveNumberException(max + "... Votre population maximum doit être positive.");
		}
		if (min > max) {
			throw new NotCoherentLimitException("Votre borne inférieure dépasse votre borne supérieure");
		}

		List<Ville> villes = rec.getVilles();
		boolean thereIsAResult = false;
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
				thereIsAResult = !thereIsAResult;
			}
		}
		if (!thereIsAResult) {
			throw new NoDeptResult("Aucun département sous le code '" + choix + "'");
		}
	}

}
