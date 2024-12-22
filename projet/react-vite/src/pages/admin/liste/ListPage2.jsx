import React,{useState} from 'react';
import DataTableWithAction from '../../../components/tables/DataTableWithAction';
import Bread from '../../../components/breadcrumb/Bread';
import CardProduct from '../../../components/cards/CardProduct';
import Panier from '../../../components/panier/Panier';
import { addToCart, removeFromCart, updateQuantity, getTotal } from '../../../utils/cartUtils';
import { Row } from 'antd';
const ListPage2 = () =>{
 const [cart, setCart] = useState([]);

  const handleAddToCart = (product) => {
    setCart((prevCart) => addToCart(prevCart, product));
  };

  const handleRemoveFromCart = (productId) => {
    setCart((prevCart) => removeFromCart(prevCart, productId));
  };

  const handleUpdateQuantity = (productId, quantity) => {
    setCart((prevCart) => updateQuantity(prevCart, productId, quantity));
  };
  const product = {
    id: 1,
    nom: 'Produit',
    prix: 4000,
    image: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
  };
  const product2 = {
    id: 2,
    nom: 'Produit 2',
    prix: 8000,
    image: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
  };
    return(
     <div>
        <Bread user={'Administrateur / Liste'} page={'card'} />
        <Row>
            <Panier
            cart={cart}
            updateQuantity={handleUpdateQuantity}
            removeFromCart={handleRemoveFromCart}
            getTotal={() => getTotal(cart)}
            />
        </Row>
        <Row>
            <CardProduct info={product} addToCart={() => handleAddToCart(product)} />
            <CardProduct info={product2} addToCart={() => handleAddToCart(product2)} />
        </Row>
     </div>
      

      
    
        
    );
}
export default ListPage2;