// utils/cartUtils.js

export const addToCart = (cart, product) => {
    const existingProduct = cart.find((item) => item.id === product.id);
    if (existingProduct) {
      return cart.map((item) =>
        item.id === product.id
          ? { ...item, quantity: item.quantity + 1 }
          : item
      );
    } else {
      return [...cart, { ...product, quantity: 1 }];
    }
  };
  
  export const removeFromCart = (cart, productId) => {
    return cart.filter((item) => item.id !== productId);
  };
  
  export const updateQuantity = (cart, productId, quantity) => {
    if (quantity < 1) return cart;
    return cart.map((item) =>
      item.id === productId ? { ...item, quantity } : item
    );
  };
  
  export const getTotal = (cart) => {
    return cart.reduce((total, item) => total + item.quantity * item.prix, 0);
  };
  