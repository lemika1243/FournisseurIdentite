import React from 'react';
import { List, Button, InputNumber, Typography, Avatar } from 'antd';

const { Title } = Typography;

const Panier = ({ cart, updateQuantity, removeFromCart, getTotal }) => {
  return (
    <div style={{ width: '100%' }}>
      <Title level={3}>Panier</Title>
      <List
        bordered
        dataSource={cart}
        renderItem={(item) => (
          <List.Item
            actions={[
              <InputNumber
                min={1}
                value={item.quantity}
                onChange={(value) => updateQuantity(item.id, value)}
              />,
              <Button type="link" danger onClick={() => removeFromCart(item.id)}>
                Supprimer
              </Button>,
            ]}
          >
            <List.Item.Meta
              avatar={<Avatar src={item.image} />}
              title={item.nom}
              description={`Prix: ${item.prix}€`}
            />
            Sous-total: {item.quantity * item.prix}€
          </List.Item>
        )}
      />
      <div style={{ marginTop: '1rem', textAlign: 'right' }}>
        <Title level={4}>Total: {getTotal()}€</Title>
        <Button type="primary" disabled={!cart.length}>
          Confirmer le panier
        </Button>
      </div>
    </div>
  );
};

export default Panier;
