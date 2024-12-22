// src/utils/phoneValidation.ts

export const validatePhoneNumber = (_, value) => {
    // Vérification des prefixes autorisés
    const allowedPrefixes = ['032', '033', '034', '038', '037'];
    
    // Vérification si le numéro existe
    if (!value) {
      return Promise.reject(new Error('Veuillez saisir votre numéro de téléphone'));
    }
    
    // Suppression des espaces et des caractères non numériques
    const cleanedNumber = value.replace(/\s+/g, '').replace(/\D/g, '');
    
    // Vérification de la longueur (10 caractères)
    if (cleanedNumber.length !== 10) {
      return Promise.reject(new Error('Le numéro doit contenir exactement 10 chiffres'));
    }
    
    // Vérification du préfixe
    const prefix = cleanedNumber.slice(0, 3);
    if (!allowedPrefixes.includes(prefix)) {
      return Promise.reject(new Error('Préfixe de téléphone non valide. Utilisez 032, 033, 034, 038 ou 037'));
    }
    
    return Promise.resolve();
  };