import React from 'react';
import { Form, Input, Button } from 'antd';
import { validatePhoneNumber } from '../../utils/formValidation';
const ProprietaireForm = () => {
  
  const onFinish = (values) => {
    // Ici vous pouvez ajouter la logique de soumission du formulaire
    console.log('Valeurs du formulaire :', values);
  };

  return (
    <Form 
      layout="vertical" 
      onFinish={onFinish}
    >
      <Form.Item 
        label="Numéro de téléphone" 
        name="phoneNumber"
        rules={[
          {
            required: true,
            validator: validatePhoneNumber
          }
        ]}
      >
        <Input 
          placeholder="Entrer votre numéro de téléphone" 
          maxLength={10}
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Soumettre
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ProprietaireForm;