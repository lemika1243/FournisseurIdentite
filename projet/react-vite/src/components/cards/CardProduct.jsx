import React from 'react';
import { Button, Card, Descriptions } from 'antd';

const { Meta } = Card;

const CardProduct = ({ info, addToCart }) => (
  <Card
    style={{
      width: '25vw',
    }}
    cover={
      <img
        alt="example"
        src={info.image ? info.image : 'https://placehold.co/240x150'}
      />
    }
    actions={[
      <Button type="primary" key="add-to-cart" onClick={addToCart}>
        Ajouter au panier
      </Button>,
      <Button type="default" key="view-details">
        Voir détails
      </Button>,
    ]}
  >
    <Meta
      title='Titre du card' // Remplacer "Titre de la carte" par le nom du produit
      description={
        <Descriptions column={1}>
          <Descriptions.Item label="Description">
            Ce produit est parfait pour vos besoins
          </Descriptions.Item>
          <Descriptions.Item label="Nom">{info.nom}</Descriptions.Item>
          <Descriptions.Item label="Prix">{info.prix} €</Descriptions.Item>
        </Descriptions>
      }
    />
  </Card>
);

export default CardProduct;
