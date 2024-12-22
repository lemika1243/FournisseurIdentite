import axios from 'axios';



/**
 * Authentifie un client.
 * @param {Object} payload - Données pour la connexion (email).
 * @returns {Promise<Object>} - Données de la réponse du serveur.
 */
export const loginClient = async (payload) => {
  try {
    const response = await axios.post('/client/login', payload);
    if (response.data.status === 'success') {
      return response.data.data;
    } else if (response.data.status === 'error') {
      throw new Error(response.data.error.message);
    } else {
      throw new Error('Réponse inattendue du serveur.');
    }
  } catch (error) {
    handleRequestError(error);
  }
};

/**
 * Récupère les loyers filtrés par date.
 * @param {Object} filter - Filtre contenant `startDate` et `endDate`.
 * @returns {Promise<Object>} - Données des loyers payés et impayés.
 */
export const fetchFilteredLoyer = async (filter) => {
  try {
    const { startDate, endDate } = filter;
    const response = await axios.get(`/client/loyer?date_debut=${startDate}&date_fin=${endDate}`);
    if (response.data.status === 'success') {
      return response.data.data;
    } else if (response.data.status === 'error') {
      throw new Error(response.data.error.message);
    } else {
      throw new Error('Réponse inattendue du serveur.');
    }
  } catch (error) {
    handleRequestError(error);
  }
};

/**
 * Gestion des erreurs de requête.
 * @param {Error} error - Erreur levée lors de la requête.
 * @throws {Error} - Relance l'erreur avec un message approprié.
 */
const handleRequestError = (error) => {
  if (error.response && error.response.data && error.response.data.error.message) {
    throw new Error(error.response.data.error.message);
  } else if (error.response && error.response.status === 401) {
    // Redirection si non autorisé
    window.location.href = '/';
  } else {
    throw new Error('Une erreur inattendue est survenue.');
  }
};






